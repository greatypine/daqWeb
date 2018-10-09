package com.cnpc.pms.personal.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.utils.ImpalaUtil;

/**
 * @Function：清洗出的订单详情Dao实现
 * @author：zhangli
 * @date:2018年9月20日 下午17:57:54
 *
 * @version V1.0
 */
public class MassOrderItemDaoImpl extends BaseDAOHibernate implements MassOrderItemDao {
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryCitynoByCode(String cityCode){
		String sql = "SELECT cityno FROM t_dist_citycode WHERE 1=1 ";
		if(StringUtils.isNotEmpty(cityCode)){
			sql = sql + " AND citycode='"+cityCode+"' ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryEmployeeBySN(String order_sn) {
		String sql = "SELECT IFNULL(a.employee_name,'') AS employee_name, IFNULL(a.employee_phone,'') AS employee_phone, a.info_employee_a_no, IFNULL(th.`name`,'') AS employee_a_name, IFNULL(th.phone,'') as employee_a_phone FROM ";
		sql = sql + " daqWeb.df_mass_order_monthly a ";
		sql = sql + "LEFT JOIN daqWeb.t_humanresources th ON a.info_employee_a_no = th.employee_no WHERE	1 = 1 ";
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		Map<String, Object> order_obj = null;
		if (StringUtils.isNotEmpty(order_sn)) {
			sql = sql + " AND a.order_sn = '" + order_sn + "'";
		}
		try{
			lst_data = ImpalaUtil.execute(sql);
			// 获得查询数据
			if (lst_data != null && lst_data.size() > 0) {
				order_obj = (Map<String, Object>) lst_data.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return order_obj;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto, PageInfo pageInfo) {
		String order_sn = massOrderDto.getOrder_sn();
		String begin_date = massOrderDto.getBeginDate();
		String end_date = massOrderDto.getEndDate();
		if(StringUtils.isNotEmpty(begin_date)){
			begin_date = begin_date.replace("/", "-")+" 00:00:00";
		}
		if(StringUtils.isNotEmpty(end_date)){
			end_date = end_date.replace("/", "-")+" 23:59:59";
		}
		//String begin_date = "2018-09-24 00:00:00";
		//String end_date = "2018-09-24 23:59:59";
		String eshop_id = massOrderDto.getEshop_id();
		String eshop_name = massOrderDto.getEshop_name();
		String product_id = massOrderDto.getProduct_id();
		String product_name = massOrderDto.getProduct_name();
		String customer_phone = massOrderDto.getCustomer_phone();//下单客户电话
		String customer_name = massOrderDto.getCustomer_name();//下单客户姓名
		String addr_customer_name = massOrderDto.getAddr_customer_name();//签收客户姓名
		String addr_customer_phone = massOrderDto.getAddr_customer_phone();//签收客户电话
		String order_source = massOrderDto.getOrder_source();//订单来源
		String comment_Flag = massOrderDto.getCommentFlag();//评论类型 有 无 全部
		String sort_tag = massOrderDto.getSort_tag();
		String city_code = massOrderDto.getCity_name();
		String store_code = massOrderDto.getStore_no();
		String department_name = massOrderDto.getDepartment_name();
		String channel_name = massOrderDto.getChannel_name();
		String whereStr = "";
		String selectQuery = "IFNULL(tor.order_sn,'') AS order_sn, IFNULL(tor.area_code,'') AS area_code, IFNULL(tor.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, " +
				"IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code,IFNULL(tor.info_village_code,'') as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.order_create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(tor.customer_name,'') AS customer_name, IFNULL(tor.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(tor.addr_address,'') AS order_address, IFNULL(tor.addr_mobilephone,'') AS order_mobilephone, IFNULL(tor.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price, toip.cost_price AS cost_price, toip.order_create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.order_signed_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(tor.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.order_cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time, IFNULL(tor.employee_name,'') AS employee_name,IFNULL(tor.employee_phone,'') AS employee_phone,IFNULL(tor.department_name,'') AS dep_name, " +
				"IFNULL(tor.channel_name,'') AS channel_name, IFNULL(toip.contents,'') AS order_contents," +
				" IFNULL(tor.store_name,'') AS store_name, IFNULL(tor.store_code,'') AS store_code,IFNULL(tor.store_city_name,'') as store_city_name,IFNULL(tor.area_code,''),IFNULL(tor.info_employee_a_no,'') as info_employee_a_no, IFNULL(tor.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toc.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end, toc.star_level AS " +
				"star_level";
		String sqlA = "SELECT " +selectQuery+" from gemini.t_order_item_pro toip left join daqWeb.df_mass_order_monthly tor on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment toc ON toc.order_id = tor.id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		String sqlB = "SELECT count(1) as count_ "+" from gemini.t_order_item_pro toip left join daqWeb.df_mass_order_monthly tor on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment toc ON toc.order_id = tor.id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and tor.order_sn  like '%" + order_sn.trim() + "%'";
		}
		
		if(StringUtils.isNotEmpty(begin_date)){
			whereStr = whereStr + " and toip.order_signed_time >= '" + begin_date.trim()+"' ";
		}
		if(StringUtils.isNotEmpty(end_date)){
			whereStr = whereStr + " and toip.order_signed_time <= '" + end_date.trim()+"' ";
		}
		
		if(StringUtils.isNotEmpty(eshop_id)){
			whereStr = whereStr + " and toip.eshop_id = '" + eshop_id.trim() + "'";
		}
		if(StringUtils.isNotEmpty(eshop_name)){
			whereStr = whereStr + " and toip.eshop_name like '%" + eshop_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(product_id)){
			whereStr = whereStr + " and toip.eshop_pro_id = '" + product_id.trim() + "'";
		}
		if(StringUtils.isNotEmpty(product_name)){
			whereStr = whereStr + " and toip.eshop_pro_name like '%" + product_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_name)){
			whereStr = whereStr + " and tor.customer_name like '%" + customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_phone)){
			whereStr = whereStr + " and tor.customer_mobile_phone = '" + customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(addr_customer_name)){
			whereStr = whereStr + " and tor.addr_name like '%" + addr_customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(addr_customer_phone)){
			whereStr = whereStr + " and tor.addr_mobilephone = '" + addr_customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(store_code)){
			whereStr = whereStr + " and tor.store_code = '" + store_code.trim() + "'";
		}
		if(StringUtils.isNotEmpty(department_name)){
			whereStr = whereStr + " and tor.department_name = '" + department_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(channel_name)){
			whereStr = whereStr + " and tor.channel_name = '" + channel_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(city_code)){
			if(city_code.startsWith("00")){
				city_code = city_code.substring(1,city_code.length());
			}
			whereStr = whereStr + " and toip.store_city_code = '" + city_code.trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			whereStr = whereStr + " and tor.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			whereStr = whereStr + " and tor.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			whereStr = whereStr + " and tor.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			whereStr = whereStr + " and tor.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			whereStr = whereStr + " and tor.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			whereStr = whereStr
					+ " and tor.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			whereStr = whereStr
					+ " and tor.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			whereStr = whereStr + " and tor.area_code in (select area_no from daqWeb.t_area ta where ta.name like '%"
					+ massOrderDto.getArea_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			whereStr = whereStr
					+ " and tor.info_village_code in (select vc.code from daqWeb.tiny_village_code vc where vc.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%') ";
		}
		if(StringUtils.isNotEmpty(comment_Flag)){
			if("1".equals(comment_Flag)){
				whereStr = whereStr + " and toip.contents <>''  ";
			}else if("3".equals(comment_Flag)){
				whereStr = whereStr + " and (toip.contents ='' or toip.contents is null)  ";
			}
		}
		if(StringUtils.isNotEmpty(order_source)){
			String ordersources[] = order_source.split(",");
			whereStr = whereStr + " and (";
			for (int i = 0; i < ordersources.length; i++) {
				if (i == ordersources.length - 1) {
					whereStr = whereStr + " toip.order_source = '" + ordersources[i].trim() + "')";
				} else {
					whereStr = whereStr + " toip.order_source = '" + ordersources[i].trim() + "' or ";
				}
			}
		}
		sqlB = sqlB+whereStr;
		if(StringUtils.isNotEmpty(sort_tag)){
			if("ASC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.order_signed_time " + sort_tag.trim() + " ";
			}else if("DESC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.order_signed_time " + sort_tag.trim() + " ";
			}
		}else{
			whereStr = whereStr + " order by toip.order_signed_time  DESC ";
		}
		//sqlA = sqlA+" limit 10 ";
		//sqlA = " select * from t_order_item_pro ";
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
        try{
        	lst_data_count=ImpalaUtil.execute(sqlB);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sqlA = sqlA+whereStr+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sqlA = sqlA+whereStr+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.execute(sqlA);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			
			//Integer count_ = 10;
			Integer total_pages = (count_ - 1) / pageInfo.getRecordsPerPage() + 1;
			pageInfo.setTotalRecords(count_);
			pageInfo.setRecordsPerPage(pageInfo.getRecordsPerPage());
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("data", lst_data);
		return map_result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> exportOrder(MassOrderItemDto massOrderDto) {
		String order_sn = massOrderDto.getOrder_sn();
		String begin_date = massOrderDto.getBeginDate();
		String end_date = massOrderDto.getEndDate();
		if(StringUtils.isNotEmpty(begin_date)){
			begin_date = begin_date.replace("/", "-")+" 00:00:00";
		}
		if(StringUtils.isNotEmpty(end_date)){
			end_date = end_date.replace("/", "-")+" 23:59:59";
		}
		//String begin_date = "2018-09-24 00:00:00";
		//String end_date = "2018-09-24 23:59:59";
		String eshop_id = massOrderDto.getEshop_id();
		String eshop_name = massOrderDto.getEshop_name();
		String product_id = massOrderDto.getProduct_id();
		String product_name = massOrderDto.getProduct_name();
		String customer_phone = massOrderDto.getCustomer_phone();//下单客户电话
		String customer_name = massOrderDto.getCustomer_name();//下单客户姓名
		String addr_customer_name = massOrderDto.getAddr_customer_name();//签收客户姓名
		String addr_customer_phone = massOrderDto.getAddr_customer_phone();//签收客户电话
		String order_source = massOrderDto.getOrder_source();//订单来源
		String comment_Flag = massOrderDto.getCommentFlag();//评论类型 有 无 全部
		String sort_tag = massOrderDto.getSort_tag();
		String city_code = massOrderDto.getCity_name();
		String store_code = massOrderDto.getStore_no();
		String department_name = massOrderDto.getDepartment_name();
		String channel_name = massOrderDto.getChannel_name();
		String whereStr = "";
		String selectQuery = "IFNULL(tor.order_sn,'') AS order_sn, IFNULL(tor.area_code,'') AS area_code, IFNULL(tor.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, " +
				"IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code,IFNULL(tor.info_village_code,'') as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.order_create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(tor.customer_name,'') AS customer_name, IFNULL(tor.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(tor.addr_address,'') AS order_address, IFNULL(tor.addr_mobilephone,'') AS order_mobilephone, IFNULL(tor.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price, toip.cost_price AS cost_price, toip.order_create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.order_signed_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(tor.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.order_cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time, IFNULL(tor.employee_name,'') AS employee_name,IFNULL(tor.employee_phone,'') AS employee_phone,IFNULL(tor.department_name,'') AS dep_name, " +
				"IFNULL(tor.channel_name,'') AS channel_name, IFNULL(toip.contents,'') AS order_contents," +
				" IFNULL(tor.store_name,'') AS store_name, IFNULL(tor.store_code,'') AS store_code,IFNULL(tor.store_city_name,'') as store_city_name,IFNULL(tor.area_code,''),IFNULL(tor.info_employee_a_no,'') as info_employee_a_no, IFNULL(tor.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toc.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end, toc.star_level AS " +
				"star_level";
		String sqlA = "SELECT " +selectQuery+" from gemini.t_order_item_pro toip left join daqWeb.df_mass_order_monthly tor on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment toc ON toc.order_id = tor.id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and tor.order_sn  like '%" + order_sn.trim() + "%'";
		}
		
		if(StringUtils.isNotEmpty(begin_date)){
			whereStr = whereStr + " and toip.order_signed_time >= '" + begin_date.trim()+"' ";
		}
		if(StringUtils.isNotEmpty(end_date)){
			whereStr = whereStr + " and toip.order_signed_time <= '" + end_date.trim()+"' ";
		}
		
		if(StringUtils.isNotEmpty(eshop_id)){
			whereStr = whereStr + " and toip.eshop_id = '" + eshop_id.trim() + "'";
		}
		if(StringUtils.isNotEmpty(eshop_name)){
			whereStr = whereStr + " and toip.eshop_name like '%" + eshop_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(product_id)){
			whereStr = whereStr + " and toip.eshop_pro_id = '" + product_id.trim() + "'";
		}
		if(StringUtils.isNotEmpty(product_name)){
			whereStr = whereStr + " and toip.eshop_pro_name like '%" + product_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_name)){
			whereStr = whereStr + " and tor.customer_name like '%" + customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_phone)){
			whereStr = whereStr + " and tor.customer_mobile_phone = '" + customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(addr_customer_name)){
			whereStr = whereStr + " and tor.addr_name like '%" + addr_customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(addr_customer_phone)){
			whereStr = whereStr + " and tor.addr_mobilephone = '" + addr_customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(store_code)){
			whereStr = whereStr + " and tor.store_code = '" + store_code.trim() + "'";
		}
		if(StringUtils.isNotEmpty(department_name)){
			whereStr = whereStr + " and tor.department_name = '" + department_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(channel_name)){
			whereStr = whereStr + " and tor.channel_name = '" + channel_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(city_code)){
			if(city_code.startsWith("00")){
				city_code = city_code.substring(1,city_code.length());
			}
			whereStr = whereStr + " and toip.store_city_code = '" + city_code.trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			whereStr = whereStr + " and tor.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			whereStr = whereStr + " and tor.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			whereStr = whereStr + " and tor.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			whereStr = whereStr + " and tor.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			whereStr = whereStr + " and tor.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			whereStr = whereStr
					+ " and tor.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			whereStr = whereStr
					+ " and tor.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			whereStr = whereStr + " and tor.area_code in (select area_no from daqWeb.t_area ta where ta.name like '%"
					+ massOrderDto.getArea_name().trim() + "%') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			whereStr = whereStr
					+ " and tor.info_village_code in (select vc.code from daqWeb.tiny_village_code vc where vc.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%') ";
		}
		if(StringUtils.isNotEmpty(comment_Flag)){
			if("1".equals(comment_Flag)){
				whereStr = whereStr + " and toip.contents <>''  ";
			}else if("3".equals(comment_Flag)){
				whereStr = whereStr + " and (toip.contents ='' or toip.contents is null)  ";
			}
		}
		if(StringUtils.isNotEmpty(order_source)){
			String ordersources[] = order_source.split(",");
			whereStr = whereStr + " and (";
			for (int i = 0; i < ordersources.length; i++) {
				if (i == ordersources.length - 1) {
					whereStr = whereStr + " toip.order_source = '" + ordersources[i].trim() + "')";
				} else {
					whereStr = whereStr + " toip.order_source = '" + ordersources[i].trim() + "' or ";
				}
			}
		}
		if(StringUtils.isNotEmpty(sort_tag)){
			if("ASC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.order_signed_time " + sort_tag.trim() + " ";
			}else if("DESC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.order_signed_time " + sort_tag.trim() + " ";
			}
		}else{
			whereStr = whereStr + " order by toip.order_signed_time  DESC ";
		}
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        try{
        	sqlA = sqlA+whereStr;
        	lst_data=ImpalaUtil.execute(sqlA);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }

		return lst_result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPlatformidByCode(String storeno){
		String sql = "select t.platformid from t_store t where 1=1 ";
		if(StringUtils.isNotEmpty(storeno)){
			sql = sql + " AND t.storeno='"+storeno+"' ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn) {
		String sql = "SELECT IFNULL(a.area_code,'') AS area_code, IFNULL(a.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, "
				+ "IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code FROM ";
		sql = sql + " df_mass_order_monthly a ";
		sql = sql + "LEFT JOIN t_area ta ON a.area_code = ta.area_no LEFT JOIN tiny_village_code vc ON a.info_village_code = vc. CODE WHERE 1=1 ";

		if (StringUtils.isNotEmpty(area_code) && !area_code.equals("null")) {
			sql = sql + " AND a.area_code = '" + area_code + "'";
		}
		if (StringUtils.isNotEmpty(order_sn)) {
			sql = sql + " AND a.order_sn = '" + order_sn + "'";
		}

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}
	/*	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn) {
		String sql = "SELECT IFNULL(a.area_code,'') AS area_code, IFNULL(a.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, "
				+ "IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code FROM ";
		sql = sql + " daqWeb.df_mass_order_monthly a ";
		sql = sql + "LEFT JOIN t_area ta ON a.area_code = ta.area_no LEFT JOIN tiny_village_code vc ON a.info_village_code = vc. CODE WHERE 1=1 ";

		if (StringUtils.isNotEmpty(area_code) && !area_code.equals("null")) {
			sql = sql + " AND a.area_code = '" + area_code + "'";
		}
		if (StringUtils.isNotEmpty(order_sn)) {
			sql = sql + " AND a.order_sn = '" + order_sn + "'";
		}
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		lst_data=ImpalaUtil.execute(sql);
		Map<String,Object> lst_bj = lst_data.get(0);
		return lst_bj;
	}*/
}
