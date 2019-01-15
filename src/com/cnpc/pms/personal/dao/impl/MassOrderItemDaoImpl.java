package com.cnpc.pms.personal.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.utils.ImpalaUtil;

import org.hibernate.type.Type;

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
			lst_data = ImpalaUtil.executeGuoan(sql);
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
		String selectQuery = "IFNULL(toip.order_sn,'') AS order_sn, IFNULL(toip.store_name,'') AS store_name, IFNULL(toip.tiny_village_name,'') AS village_name, " +
				"IFNULL(toip.area_name,'') AS area_name, toip.tiny_village_id as village_id, toip.info_village_code as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(toip.customer_name,'') AS customer_name, IFNULL(toip.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(toip.addr_address,'') AS order_address, IFNULL(toip.addr_mobilephone,'') AS order_mobilephone, IFNULL(toip.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price,toip.quantity AS quantity, toip.cost_price AS cost_price, toip.create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.sign_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(toip.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time, IFNULL(toip.employee_name,'') AS employee_name,IFNULL(toip.employee_phone,'') AS employee_phone,IFNULL(toip.department_name,'') AS dep_name, " +
				"IFNULL(toip.channel_name,'') AS channel_name, IFNULL(toip.contents,'') AS order_contents," +
				" IFNULL(toip.store_name,'') AS store_name, IFNULL(toip.store_code,'') AS store_code,IFNULL(toip.store_city_name,'') as store_city_name,IFNULL(toip.area_code,'') as area_code,IFNULL(toip.info_employee_a_no,'') as info_employee_a_no, IFNULL(toip.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toip.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end, toip.star_level AS " +
				"star_level";
		String sqlA = "SELECT " +selectQuery+" from gabase.b_item_pro_total toip where 1=1  ";
		String sqlB = "SELECT count(1) as count_ from gabase.b_item_pro_total toip where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and toip.order_sn = '" + order_sn.trim() + "' ";
		}
		
		if(StringUtils.isNotEmpty(begin_date)){
			whereStr = whereStr + " and toip.sign_time >= '" + begin_date.trim()+"' ";
		}
		if(StringUtils.isNotEmpty(end_date)){
			whereStr = whereStr + " and toip.sign_time <= '" + end_date.trim()+"' ";
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
			whereStr = whereStr + " and toip.customer_name like '%" + customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_phone)){
			whereStr = whereStr + " and toip.customer_mobile_phone = '" + customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(addr_customer_name)){
			whereStr = whereStr + " and toip.addr_name like '%" + addr_customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(addr_customer_phone)){
			whereStr = whereStr + " and toip.addr_mobilephone = '" + addr_customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(store_code)){
			whereStr = whereStr + " and toip.store_code = '" + store_code.trim() + "'";
		}
		if(StringUtils.isNotEmpty(department_name)){
			whereStr = whereStr + " and toip.department_name = '" + department_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(channel_name)){
			whereStr = whereStr + " and toip.channel_name = '" + channel_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(city_code)){
			if(city_code.startsWith("00")){
				city_code = city_code.substring(1,city_code.length());
			}
			whereStr = whereStr + " and toip.store_city_code = '" + city_code.trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			whereStr = whereStr + " and toip.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			whereStr = whereStr + " and toip.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			whereStr = whereStr + " and toip.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			whereStr = whereStr + " and toip.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			whereStr = whereStr + " and toip.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			whereStr = whereStr
					+ " and toip.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			whereStr = whereStr
					+ " and toip.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			whereStr = whereStr + " and toip.area_name like '%"
					+ massOrderDto.getArea_name().trim() + "%' ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			whereStr = whereStr
					+ " and toip.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%' ";
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
				whereStr = whereStr + " order by toip.sign_time " + sort_tag.trim() + " ";
			}else if("DESC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.sign_time " + sort_tag.trim() + " ";
			}
		}else{
			whereStr = whereStr + " order by toip.sign_time  DESC ";
		}
		//sqlA = sqlA+" limit 10 ";
		//sqlA = " select * from t_order_item_pro ";
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
        try{
        	lst_data_count=ImpalaUtil.executeGuoan(sqlB);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sqlA = sqlA+whereStr+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sqlA = sqlA+whereStr+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.executeGuoan(sqlA);
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
	public List<Map<String, Object>> exportOrder(MassOrderItemDto massOrderDto,String timeFlag) {
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
		String selectQuery = "IFNULL(toip.order_sn,'') AS order_sn, IFNULL(toip.store_name,'') AS store_name, IFNULL(toip.tiny_village_name,'') AS village_name, " +
				"IFNULL(toip.area_name,'') AS area_name, toip.tiny_village_id as village_id,IFNULL(toip.info_village_code,'') as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(toip.customer_name,'') AS customer_name, IFNULL(toip.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(toip.addr_address,'') AS order_address, IFNULL(toip.addr_mobilephone,'') AS order_mobilephone, IFNULL(toip.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price,IFNULL(toip.quantity,0) AS quantity, toip.cost_price AS cost_price, toip.create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.sign_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(toip.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time,IFNULL(from_unixtime(unix_timestamp(toip.commented_time),'yyyy-MM-dd HH:mm:ss'),'') AS order_commented_time, IFNULL(toip.employee_name,'') AS employee_name,IFNULL(toip.employee_phone,'') AS employee_phone,IFNULL(toip.department_name,'') AS dep_name, " +
				"IFNULL(toip.channel_name,'') AS channel_name, IFNULL(toip.contents,'') AS order_contents,toip.star_level AS star_level,toip.star_level_1 AS star_level_1,toip.star_level_2 AS star_level_2,toip.next_days AS next_days,IFNULL(toip.next_contents,'') AS next_contents," +
				" IFNULL(toip.store_name,'') AS store_name, IFNULL(toip.store_code,'') AS store_code,IFNULL(toip.store_city_name,'') as store_city_name,IFNULL(toip.area_code,'') AS area_code,IFNULL(toip.info_employee_a_no,'') as info_employee_a_no, IFNULL(toip.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toip.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end ";
		String sqlA = "SELECT " +selectQuery+" from gabase.b_item_pro_total toip where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and toip.order_sn  = '" + order_sn.trim() + "' ";
		}
		
		if(StringUtils.isNotEmpty(begin_date)){
			whereStr = whereStr + " and toip.sign_time >= '" + begin_date.trim()+"' ";
		}
		if(StringUtils.isNotEmpty(end_date)){
			whereStr = whereStr + " and toip.sign_time <= '" + end_date.trim()+"' ";
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
			whereStr = whereStr + " and toip.customer_name like '%" + customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(customer_phone)){
			whereStr = whereStr + " and toip.customer_mobile_phone = '" + customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(addr_customer_name)){
			whereStr = whereStr + " and toip.addr_name like '%" + addr_customer_name.trim() + "%'";
		}
		if(StringUtils.isNotEmpty(addr_customer_phone)){
			whereStr = whereStr + " and toip.addr_mobilephone = '" + addr_customer_phone.trim() + "'";
		}
		if(StringUtils.isNotEmpty(store_code)){
			whereStr = whereStr + " and toip.store_code = '" + store_code.trim() + "'";
		}
		if(StringUtils.isNotEmpty(department_name)){
			whereStr = whereStr + " and toip.department_name = '" + department_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(channel_name)){
			whereStr = whereStr + " and toip.channel_name = '" + channel_name.trim() + "'";
		}
		if(StringUtils.isNotEmpty(city_code)){
			if(city_code.startsWith("00")){
				city_code = city_code.substring(1,city_code.length());
			}
			whereStr = whereStr + " and toip.store_city_code = '" + city_code.trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_no())) {
			whereStr = whereStr + " and toip.employee_no ='" + massOrderDto.getEmployee_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_mobile())) {
			whereStr = whereStr + " and toip.employee_phone ='" + massOrderDto.getEmployee_mobile().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no())) {
			whereStr = whereStr + " and toip.info_employee_a_no ='" + massOrderDto.getEmployee_a_no().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_code())) {
			whereStr = whereStr + " and toip.area_code ='" + massOrderDto.getArea_code().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_no_a())) {
			whereStr = whereStr + " and toip.info_employee_a_no ='" + massOrderDto.getEmployee_a_no_a().trim() + "'";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone())) {
			whereStr = whereStr
					+ " and toip.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getEmployee_a_phone_a())) {
			whereStr = whereStr
					+ " and toip.info_employee_a_no in (select employee_no from daqWeb.t_humanresources th where th.phone = '"
					+ massOrderDto.getEmployee_a_phone_a().trim() + "') ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getArea_name())) {
			whereStr = whereStr + " and toip.area_name like '%"
					+ massOrderDto.getArea_name().trim() + "%' ";
		}
		if (StringUtils.isNotEmpty(massOrderDto.getVillage_name())) {
			whereStr = whereStr
					+ " and toip.tiny_village_name like '%"
					+ massOrderDto.getVillage_name().trim() + "%' ";
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
				whereStr = whereStr + " order by toip.sign_time " + sort_tag.trim() + " ";
			}else if("DESC".equals(sort_tag)){
				whereStr = whereStr + " order by toip.sign_time " + sort_tag.trim() + " ";
			}
		}else{
			whereStr = whereStr + " order by toip.sign_time  DESC ";
		}
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        try{
        	sqlA = sqlA+whereStr;
        	lst_data=ImpalaUtil.executeGuoan(sqlA);
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
	@Override
	public Map<String, Object> queryDailyprofit(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO) {
		String beginDate = dd.getBeginDate();
		String endDate = dd.getEndDate();
		String dateStr = "";
		String provinceStr = "";
		String cityStr = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			cityStr+=" and ds.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			provinceStr+=" and ds.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		if(beginDate!=null&&endDate!=null&&!"".equals(beginDate)&&!"".equals(endDate)){
			dateStr = " WHERE ds.sign_time BETWEEN '"+beginDate+" 00:00:00' and '"+endDate+" 23:59:59' ";
		}
		String sql = "SELECT IFNULL(FLOOR(SUM(ds.order_profit)),0) AS order_profit FROM df_mass_order_daily ds "+dateStr+provinceStr+cityStr;
		List<Map<String, Object>> lst_data = null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		/*List<Map<String,Object>> lst_data=ImpalaUtil.executeGuoan(sql);
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("gmv", lst_data);
		return map_result;*/
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
   	 	lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
   	 	map_result.put("gmv", lst_data);
		return map_result;
	}
	@Override
	public Map<String, Object> queryMonthprofit(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO) {
		String sql = "select sum(ifnull(aa.platform_profit,0)) AS platform_profit,sum(ifnull(aa.ims_profit,0)) AS ims_profit,sum(ifnull(aa.order_fee,0)) "
		+ "AS order_fee,sum(ifnull(aa.total_profit,0)) AS total_profit, sum(ifnull(dd.return_profit, 0)) AS return_profit,"
		+ "sum(ifnull(dbaosun.count_money, 0)) AS baosun from ( "
		+ "select min(dot.store_city_name) as city_name,min(dot.store_city_code) as store_city_code,min(dot.store_province_code) as store_province_code,min(dot.store_name) as store_name,ifnull(min(dot.store_code),'') as store_code,"
		+ "ifnull(min(dot.department_name),'无') as department_name,min(dot.channel_name) as channel_name,"
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='no' then dot.order_profit else 0 end),2),0) as platform_profit, "
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='yes' then dot.order_profit else 0 end),2),0) as ims_profit,"
		+ "ifnull(dround(sum(case when dot.order_tag4 is null then dot.platform_price else 0 end),2),0) as order_fee,"
		+ "ifnull(dround(sum(dot.order_profit),2),0) as total_profit from df_mass_order_total dot,t_dist_citycode tdc,gemini.t_department_channel dc "
		+ "where LPAD(dot.store_city_code, 4, '0')=tdc.cityno  and dc.id=dot.bussiness_group_id and dc.level=1 and dc.name not like '%测试%' ";
		String beginDate = dynamicDto.getBeginDate().substring(0, dynamicDto.getBeginDate().lastIndexOf("-"));
		if(StringUtils.isNotEmpty(dynamicDto.getBeginDate())){
			sql = sql + "and strleft(dot.sign_time,7)='"+beginDate+"' ";
		}
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			sql = sql + " and dot.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			sql = sql + " and dot.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
			Map<String,Object> position_obj = queryPlatformidByCode(dynamicDto.getStoreNo());
			if (position_obj != null) {
				sql = sql + " and (dot.store_code ='" + dynamicDto.getStoreNo().trim()+ "' or dot.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and dot.store_code ='" + dynamicDto.getStoreNo().trim()+ "'";
			}
		}
		sql = sql + "group by dot.store_city_code order by dot.store_city_code";

		//报损
		sql = sql + ") aa left join (select count_money,city_code,create_date,num  from (select ifnull(dround(sum(baosun.count_money),2),0) as count_money,ts.city_code,"
				+ "baosun.create_date,ROW_NUMBER() OVER(PARTITION BY city_code ORDER BY create_date DESC) as num from df_pankui_baosun_info baosun "
				+ "join gemini.t_store ts  on baosun.store_code=ts.code where baosun.count_type='0' and baosun.count_month='"+beginDate+"' "
				+ "group by ts.city_code,baosun.create_date ) aa having num=1) dbaosun on aa.store_city_code=dbaosun.city_code ";
		//退款
		sql = sql + "left join (select ifnull(dround(sum(order_profit),2),0)  as return_profit ,store_city_code from df_mass_order_total where strleft(return_time,7)='"+beginDate+"' group by store_city_code) dd on aa.store_city_code=dd.store_city_code ";



		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("gmv", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryYesterdayprofit(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO) {
		String sql = "select sum(ifnull(aa.platform_profit,0)) AS platform_profit,sum(ifnull(aa.ims_profit,0)) AS ims_profit,sum(ifnull(aa.order_fee,0)) "
		+ "AS order_fee,sum(ifnull(aa.total_profit,0)) AS total_profit, sum(ifnull(dd.return_profit, 0)) AS return_profit"
		+ " from (select min(dot.store_city_name) as city_name,min(dot.store_city_code) as store_city_code,min(dot.store_province_code) as store_province_code,min(dot.store_name) as store_name,ifnull(min(dot.store_code),'') as store_code,"
		+ "ifnull(min(dot.department_name),'无') as department_name,min(dot.channel_name) as channel_name,"
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='no' then dot.order_profit else 0 end),2),0) as platform_profit, "
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='yes' then dot.order_profit else 0 end),2),0) as ims_profit,"
		+ "ifnull(dround(sum(case when dot.order_tag4 is null then dot.platform_price else 0 end),2),0) as order_fee,"
		+ "ifnull(dround(sum(dot.order_profit),2),0) as total_profit from df_mass_order_total dot,t_dist_citycode tdc,gemini.t_department_channel dc "
		+ "where LPAD(dot.store_city_code, 4, '0')=tdc.cityno  and dc.id=dot.bussiness_group_id and dc.level=1 and dc.name not like '%测试%' ";
		String beginDate = dynamicDto.getBeginDate();
		if(StringUtils.isNotEmpty(dynamicDto.getBeginDate())){
			sql = sql + "and strleft(dot.sign_time,10)='"+beginDate+"' ";
		}
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			sql = sql + " and dot.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			sql = sql + " and dot.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
			Map<String,Object> position_obj = queryPlatformidByCode(dynamicDto.getStoreNo());
			if (position_obj != null) {
				sql = sql + " and (dot.store_code ='" + dynamicDto.getStoreNo().trim()+ "' or dot.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and dot.store_code ='" + dynamicDto.getStoreNo().trim()+ "'";
			}
		}
		sql = sql + "group by dot.store_city_code order by dot.store_city_code";

		//以日为单位不减报损和盘亏
		//退款
		sql = sql + ") aa left join (select ifnull(dround(sum(order_profit),2),0)  as return_profit ,store_city_code from df_mass_order_total where strleft(return_time,10)='"+beginDate+"' group by store_city_code) dd on aa.store_city_code=dd.store_city_code ";



		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("gmv", list);
		return map_result;
	}
	@Override
	public Map<String, Object> getOtherProfitRangeForWeek(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "select sum(ifnull(aa.platform_profit,0)) AS platform_profit,min(aa.store_city_code) AS store_city_code,sum(ifnull(aa.ims_profit,0)) AS ims_profit,sum(ifnull(aa.order_fee,0)) "
				+ "AS order_fee,sum(ifnull(aa.total_profit,0)) AS total_profit, sum(ifnull(dd.return_profit, 0)) AS return_profit,min(from_unixtime(unix_timestamp(aa.sign_time),'MM-dd')) AS week_date "
				+ " from (select min(dot.store_city_name) as city_name,min(dot.store_city_code) as store_city_code,min(dot.store_province_code) as store_province_code,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') as sign_time,"
				+ "min(dot.store_name) as store_name,ifnull(min(dot.store_code),'') as store_code,"
				+ "ifnull(min(dot.department_name),'无') as department_name,min(dot.channel_name) as channel_name,"
				+ "ifnull(dround(sum(case when dot.eshop_joint_ims='no' then dot.order_profit else 0 end),2),0) as platform_profit, "
				+ "ifnull(dround(sum(case when dot.eshop_joint_ims='yes' then dot.order_profit else 0 end),2),0) as ims_profit,"
				+ "ifnull(dround(sum(case when dot.order_tag4 is null then dot.platform_price else 0 end),2),0) as order_fee,"
				+ "ifnull(dround(sum(dot.order_profit),2),0) as total_profit from df_mass_order_total dot,t_dist_citycode tdc,gemini.t_department_channel dc "
				+ "where LPAD(dot.store_city_code, 4, '0')=tdc.cityno  and dc.id=dot.bussiness_group_id and dc.level=1 and dc.name not like '%测试%' "
				+ "AND strleft (dot.sign_time, 10) >= '"+beginDate+"' AND strleft (dot.sign_time, 10) <= '"+endDate+"'";
		String whereStr = " where 1=1 ";
		String groupStr = " GROUP BY from_unixtime(unix_timestamp(aa.sign_time),'yyyy-MM-dd'),aa.store_city_code ";
		whereStr +=  " and (aa.store_city_code = '010' or aa.store_city_code='022' or aa.store_city_code='021') ";
		sql = sql + "group by dot.store_city_code,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') order by dot.store_city_code";
		
		//以日为单位不减报损和盘亏
		//退款
		sql = sql + ") aa left join (select ifnull(dround(sum(order_profit),2),0)  as return_profit ,from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd') AS return_time2,"
				+ "store_city_code from df_mass_order_total where strleft (return_time, 10) >= '"+beginDate+"' and strleft (return_time, 10) <= '"+endDate+"'  group by store_city_code,"
				+ "from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd')) dd on aa.store_city_code=dd.store_city_code and aa.sign_time = dd.return_time2 ";
		
		sql = sql +whereStr+groupStr;
		
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> getProfitRangeForWeek(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "select sum(ifnull(aa.platform_profit,0)) AS platform_profit,sum(ifnull(aa.ims_profit,0)) AS ims_profit,sum(ifnull(aa.order_fee,0)) "
		+ "AS order_fee,sum(ifnull(aa.total_profit,0)) AS total_profit, sum(ifnull(dd.return_profit, 0)) AS return_profit,min(from_unixtime(unix_timestamp(aa.sign_time),'MM-dd')) AS week_date "
		+ " from (select min(dot.store_city_name) as city_name,min(dot.store_city_code) as store_city_code,min(dot.store_province_code) as store_province_code,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') as sign_time,"
		+ "min(dot.store_name) as store_name,ifnull(min(dot.store_code),'') as store_code,"
		+ "ifnull(min(dot.department_name),'无') as department_name,min(dot.channel_name) as channel_name,"
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='no' then dot.order_profit else 0 end),2),0) as platform_profit, "
		+ "ifnull(dround(sum(case when dot.eshop_joint_ims='yes' then dot.order_profit else 0 end),2),0) as ims_profit,"
		+ "ifnull(dround(sum(case when dot.order_tag4 is null then dot.platform_price else 0 end),2),0) as order_fee,"
		+ "ifnull(dround(sum(dot.order_profit),2),0) as total_profit from df_mass_order_total dot,t_dist_citycode tdc,gemini.t_department_channel dc "
		+ "where LPAD(dot.store_city_code, 4, '0')=tdc.cityno  and dc.id=dot.bussiness_group_id and dc.level=1 and dc.name not like '%测试%' "
		+ "AND strleft (dot.sign_time, 10) >= '"+beginDate+"' AND strleft (dot.sign_time, 10) <= '"+endDate+"'";
		String whereStr = " where 1=1 ";
		String groupStr = " GROUP BY from_unixtime(unix_timestamp(aa.sign_time),'yyyy-MM-dd') ";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr +=  " and aa.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and aa.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		sql = sql + "group by dot.store_city_code,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') order by dot.store_city_code";

		//以日为单位不减报损和盘亏
		//退款
		sql = sql + ") aa left join (select ifnull(dround(sum(order_profit),2),0)  as return_profit ,from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd') AS return_time2,"
				+ "store_city_code from df_mass_order_total where strleft (return_time, 10) >= '"+beginDate+"' and strleft (return_time, 10) <= '"+endDate+"'  group by store_city_code,"
				+ "from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd')) dd on aa.store_city_code=dd.store_city_code and aa.sign_time = dd.return_time2 ";

		sql = sql +whereStr+groupStr;

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		return map_result;
	}

	@Override
	public void updataReport(Long id , String url) {
		String sql = " UPDATE t_report_filedown set mark_1 = '1',url =? where id =? ";
		Session session = this.getSession();
		session.createSQLQuery(sql).setParameters(
				new Object[] {url, id }, new Type[] { Hibernate.STRING,Hibernate.LONG })
				.executeUpdate();
		session.close();

	}
	@Override
	public Map<String, Object> queryAreaUserByAreaCode(DynamicDto userOperationStatDto) {
		String sql = "SELECT SUM(cusnum_ten) AS pay_10_count FROM ds_pes_customer_employee_month dpce WHERE 1=1 and dpce.year='"+userOperationStatDto.getYear()+"' and dpce.month='"+userOperationStatDto.getMonth()+"' ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getEmployeeNo())){
			sql = sql + " and dpce.employeeno ='" + userOperationStatDto.getEmployeeNo().trim()+ "'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryAreaUserByStoreNo(DynamicDto userOperationStatDto) {
		String sql = "SELECT count(DISTINCT(case when trading_price > 10 and (date_format(a.sign_time, '%Y-%m') = '"+userOperationStatDto.getBeginDate()+"') then customer_id end)) pay_10_count FROM ";
		
			sql = sql + " df_mass_order_monthly a ";
		
		sql = sql + " where customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
        if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
            sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
        }
		sql = sql + " GROUP BY a.store_code ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryAreaOpenCardByAreaCode(DynamicDto userOperationStatDto) {
		String employessNo = userOperationStatDto.getEmployeeNo();
		String begiDate = userOperationStatDto.getBeginDate();
		String whereStr = "";
		String dateStr = "";
		if(StringUtils.isNotEmpty(employessNo)){
			whereStr += " and t.employee_no='"+employessNo+"'";
		}
		if(StringUtils.isNotEmpty(begiDate)){
			dateStr+=" and DATE_FORMAT(opencard_time,'%Y-%m')='"+begiDate+"' ";
		}
		String sql = "select a.name,a.phone,a.employee_no,a.inviteCode,b.total as inviteCount from (select t.name,t.phone,t.employee_no,"
				+ "t.inviteCode from t_humanresources t where  t.inviteCode is not null and t.inviteCode!='' "+whereStr+") a INNER JOIN (select inviteCode,COUNT(1) as total from df_user_member where "
				+ "invitecode REGEXP  '^[0-9]{6}$' and  customer_id not in (select customer_id from df_member_whitelist) "
				+dateStr+" GROUP BY inviteCode) b on a.inviteCode = b.inviteCode ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryAreaOpenCardByStoreKeeperNo(DynamicDto userOperationStatDto,String employee_no) {
		String begiDate = userOperationStatDto.getBeginDate();
		String whereStr = "";
		String dateStr = "";
		if(StringUtils.isNotEmpty(employee_no)){
			whereStr += " and t.employee_no='"+employee_no+"'";
		}
		if(StringUtils.isNotEmpty(begiDate)){
			dateStr+=" and DATE_FORMAT(opencard_time,'%Y-%m')='"+begiDate+"' ";
		}
		String sql = "select a.name,a.phone,a.employee_no,a.inviteCode,b.total as inviteCount from (select t.name,t.phone,t.employee_no,"
				+ "t.inviteCode from t_storekeeper t where  t.inviteCode is not null and t.inviteCode!='' "+whereStr+") a INNER JOIN (select inviteCode,COUNT(1) as total from df_user_member where "
				+ "invitecode REGEXP  '^[0-9]{6}$' and  customer_id not in (select customer_id from df_member_whitelist) "
				+dateStr+" GROUP BY inviteCode) b on a.inviteCode = b.inviteCode ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryRecommendUser(PageInfo pageInfo, String employee_no) {
		String whereStr = "";
		if(StringUtils.isNotEmpty(employee_no)){
			whereStr+="where but.employee_a_no = '" +employee_no+ "'";
		}
		String sqlA = "SELECT a.customerId, but.mobilephone, but.area_no, but.employee_a_no, COALESCE(b.item1, '') item1, "
				+ "COALESCE (c.item2, '') item2, COALESCE (d.item3, '') item3 FROM ( SELECT DISTINCT a.customer_id customerId, "
				+ "COALESCE ( split_part (b.tag_level4_id, ',', 1), '') item1, COALESCE ( substring( split_part (b.tag_level4_id, ',', 2),"
				+ " locate( ',', split_part (b.tag_level4_id, ',', 2) ) + 1 ), '' ) item2, COALESCE ( substring( split_part (b.tag_level4_id, ',', 3),"
				+ " locate( split_part ( split_part (b.tag_level4_id, ',', 3), ',', 2 ), split_part (b.tag_level4_id, ',', 3) ) + "
				+ "length( split_part ( split_part (b.tag_level4_id, ',', 3), ',', 2 ) ) + 1 ), '' ) item3 FROM "
				+ "gabase.t_statis_cust_operate_classify a LEFT JOIN gabase.t_recomm_num_result b ON a.customer_id = b.customer_id WHERE "
				+ "1 = 1 AND level_num = 1 ORDER BY a.customer_id ) a LEFT JOIN ( SELECT tag_level4_id id, tag_level4_name item1 FROM "
				+ "gabase.f_skuclass GROUP BY tag_level4_id, tag_level4_name ) b ON COALESCE (a.item1, '$') = b.id LEFT JOIN "
				+ "( SELECT tag_level4_id id, tag_level4_name item2 FROM gabase.f_skuclass GROUP BY tag_level4_id, tag_level4_name ) c "
				+ "ON COALESCE (a.item2, '$') = c.id LEFT JOIN ( SELECT tag_level4_id id, tag_level4_name item3 FROM gabase.f_skuclass "
				+ "GROUP BY tag_level4_id, tag_level4_name ) d ON COALESCE (a.item3, '$') = d.id LEFT JOIN gabase.b_user_tiny but ON "
				+ "a.customerId = but.customer_id "+whereStr+" ORDER BY a.customerId ";
		String sqlB = "SELECT count(1) as count_ from ( "+sqlA+" ) ttt ";
		
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
        try{
        	lst_data_count=ImpalaUtil.executeGuoan(sqlB);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sqlA = sqlA+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sqlA = sqlA+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.executeGuoan(sqlA);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (count_ - 1) / pageInfo.getRecordsPerPage() + 1;
			pageInfo.setTotalRecords(count_);
			pageInfo.setRecordsPerPage(pageInfo.getRecordsPerPage());
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("data", lst_data);
		return map_result;
	}
	@Override
	public List<Map<String, Object>> findAllStore() {
		String sql = "SELECT t.name as name,t.storeno as storeno from t_store t where t.flag='0' ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	@Override
	public Map<String, Object> queryDayGMVUserMemberProfit(DynamicDto dynamicDto,String cityNO,PageInfo pageInfo) {
		String beginDate = dynamicDto.getBeginDate();
		String sql = "select city_name,consumer,addconsumer,sumconsumer,member,addmember,summember from daqweb.dops_consumer_city_daily where 1=1 ";
		String whereStr = "";
		if(StringUtils.isNotEmpty(cityNO)){
			if(cityNO.startsWith("00")){
				cityNO = cityNO.substring(1,cityNO.length());
			}
			whereStr +=  " and city_code='"+cityNO.trim()+"' ";
		}
		/*if(StringUtils.isNotEmpty(beginDate)){
			whereStr += "and create_date = '" + beginDate.trim()+"' ";
		}*/
		sql=sql+whereStr+" ORDER BY city_code ";
		String sqlB = "SELECT count(1) as count_ from ( "+sql+" ) ttt ";
		 List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
		try{
        	lst_data_count=ImpalaUtil.executeGuoan(sqlB);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sql = sql+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sql = sql+" limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.executeGuoan(sql);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (count_ - 1) / pageInfo.getRecordsPerPage() + 1;
			pageInfo.setTotalRecords(count_);
			pageInfo.setRecordsPerPage(pageInfo.getRecordsPerPage());
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("data", lst_data);
		return map_result;
	}
	@Override
	public Map<String, Object> getProfitRangeForStoreWeek(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "SELECT aa.*,ifnull(ss.gmv_price, 0) AS gmv_price,ifnull(dd.return_profit, 0) AS return_profit FROM( SELECT tab3.*,tdc.id as cityId FROM ( SELECT tab2.*, ts.city_name AS city_name, ts.cityno AS store_city_code, "
				+ "ts. NAME AS store_name, ts.storeno AS store_code,tcs.customer_count AS customer_count  FROM ( SELECT store_id, min(store_province_code) AS store_province_code, "
				+ "order_sign_date AS order_sign_date, sum(platform_profit) AS platform_profit, sum(ims_profit) AS ims_profit, sum(order_fee) AS order_fee, sum(total_profit) AS total_profit FROM "
				+ "( SELECT dot.real_store_id AS store_id, min(dot.store_province_code) AS store_province_code, min(strleft (dot.sign_time, 10)) "
				+ "AS order_sign_date, ifnull( dround ( sum( CASE WHEN dot.eshop_joint_ims = 'no' THEN dot.order_profit ELSE 0 END), 2 ), 0 ) AS platform_profit, ifnull( dround ( sum( CASE WHEN "
				+ "dot.eshop_joint_ims = 'yes' THEN dot.order_profit ELSE 0 END ), 2 ), 0 ) AS ims_profit, ifnull( dround ( sum( CASE WHEN dot.order_tag4 IS NULL THEN dot.platform_price ELSE 0 END ), 2 ), 0 ) "
				+ "AS order_fee, ifnull( dround (sum(dot.order_profit), 2), 0 ) AS total_profit FROM daqWeb.df_mass_order_monthly dot WHERE strleft (dot.sign_time, 10) >= '"+beginDate+"' AND strleft (dot.sign_time, 10) <='"+endDate+"' "
				+ "GROUP BY dot.real_store_id,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') ) tab1 GROUP BY store_id,order_sign_date ) tab2 LEFT JOIN daqWeb.t_store ts ON tab2.store_id = ts.id "
				+ "left join( SELECT IFNULL( count(DISTINCT(customer_id)), 0) AS customer_count, store_name, strleft (sign_time, 10) AS tcs_sign_time FROM daqweb.df_mass_order_monthly GROUP BY strleft (sign_time, 10), store_name ) tcs on tcs.tcs_sign_time = tab2.order_sign_date and ts.name=tcs.store_name ) tab3 LEFT JOIN daqWeb.t_dist_citycode "
				+ "tdc ON tab3.store_city_code = tdc.cityno";
		String whereStr = " WHERE 1 = 1 ";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			whereStr +=  " and tab3.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and tab3.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		sql= sql+whereStr+ ") aa ";
		//以日为单位不减报损和盘亏
		//退款
		sql = sql+ "LEFT JOIN ( SELECT ifnull( dround (sum(order_profit), 2), 0 ) AS return_profit, real_store_id AS store_id, min(strleft (return_time, 10)) "
		+ "as order_return_date FROM daqWeb.df_mass_order_monthly WHERE strleft (return_time, 10) >= '"+beginDate+"' AND strleft (return_time, 10) <='"+endDate+"' GROUP BY real_store_id,from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd') ) "
		+ "dd ON aa.store_id = dd.store_id and aa.order_sign_date = dd.order_return_date ";
		//查询GMV去除仓店
		sql = sql+"LEFT JOIN( SELECT real_store_id AS store_id, min(strleft(sign_time, 10)) AS order_sign_date, SUM(IFNULL(gmv_price, 0)) AS gmv_price FROM daqWeb.df_mass_order_monthly WHERE strleft (sign_time, 10) >= '"+beginDate+"' AND "
				+ "strleft (sign_time, 10) <= '"+endDate+"' GROUP BY real_store_id, from_unixtime( unix_timestamp(sign_time), 'yyyy-MM-dd') ) ss on ss.order_sign_date = aa.order_sign_date and ss.store_id=aa.store_id ";
		
		sql = sql+" ORDER BY aa.order_sign_date";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> getProfitYesterdayRangeForStoreWeek(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String sql = "SELECT tt.* from (SELECT aa.city_name as city_name,aa.store_city_code as store_city_code,aa.store_name as store_name,aa.store_code as store_code,"
				+ "ROUND((ifnull(aa.total_profit,0)-ifnull(aa.order_fee,0)-ifnull(dd.return_profit, 0)),2) as maoli  FROM( SELECT tab3.*,tdc.id as cityId FROM ( SELECT tab2.*, ts.city_name AS city_name, ts.cityno AS store_city_code, "
				+ "ts. NAME AS store_name, ts.storeno AS store_code FROM ( SELECT store_id, min(store_province_code) AS store_province_code, "
				+ "order_sign_date AS order_sign_date, sum(platform_profit) AS platform_profit, sum(ims_profit) AS ims_profit, sum(order_fee) AS order_fee, sum(total_profit) AS total_profit FROM "
				+ "( SELECT dot.real_store_id AS store_id, min(dot.store_province_code) AS store_province_code, min(strleft (dot.sign_time, 10)) "
				+ "AS order_sign_date, ifnull( dround ( sum( CASE WHEN dot.eshop_joint_ims = 'no' THEN dot.order_profit ELSE 0 END), 2 ), 0 ) AS platform_profit, ifnull( dround ( sum( CASE WHEN "
				+ "dot.eshop_joint_ims = 'yes' THEN dot.order_profit ELSE 0 END ), 2 ), 0 ) AS ims_profit, ifnull( dround ( sum( CASE WHEN dot.order_tag4 IS NULL THEN dot.platform_price ELSE 0 END ), 2 ), 0 ) "
				+ "AS order_fee, ifnull( dround (sum(dot.order_profit), 2), 0 ) AS total_profit FROM daqWeb.df_mass_order_monthly dot WHERE strleft (dot.sign_time, 10) = '"+beginDate+"' "
				+ "GROUP BY dot.real_store_id,from_unixtime(unix_timestamp(dot.sign_time),'yyyy-MM-dd') ) tab1 GROUP BY store_id,order_sign_date ) tab2 LEFT JOIN daqWeb.t_store ts ON tab2.store_id = ts.id "
				+ " ) tab3 LEFT JOIN daqWeb.t_dist_citycode "
				+ "tdc ON tab3.store_city_code = tdc.cityno";
		String whereStr = " WHERE 1 = 1 ";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			whereStr +=  " and tab3.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and tab3.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		sql= sql+whereStr+ ") aa ";
		//以日为单位不减报损和盘亏
		//退款
		sql = sql+ "LEFT JOIN ( SELECT ifnull( dround (sum(order_profit), 2), 0 ) AS return_profit, real_store_id AS store_id, min(strleft (return_time, 10)) "
				+ "as order_return_date FROM daqWeb.df_mass_order_monthly WHERE strleft (return_time, 10) = '"+beginDate+"' GROUP BY real_store_id,from_unixtime(unix_timestamp(return_time),'yyyy-MM-dd') ) "
				+ "dd ON aa.store_id = dd.store_id and aa.order_sign_date = dd.order_return_date ";
		
		sql = sql+" ORDER BY aa.order_sign_date ) tt order by tt.maoli desc ";
		
		
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> queryprofitForStoreIntervalDay(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "SELECT tt.* from (SELECT aa.city_name as city_name,aa.store_city_code as store_city_code,aa.store_name as store_name,aa.store_code as store_code,"
				+ "ROUND((ifnull(aa.total_profit,0)-ifnull(aa.order_fee,0)-ifnull(dd.return_profit, 0)),2) as maoli  FROM( SELECT tab3.*,tdc.id as cityId FROM ( SELECT tab2.*, ts.city_name AS city_name, ts.cityno AS store_city_code, "
				+ "ts. NAME AS store_name, ts.storeno AS store_code FROM ( SELECT store_id, min(store_province_code) AS store_province_code, "
				+ " sum(platform_profit) AS platform_profit, sum(ims_profit) AS ims_profit, sum(order_fee) AS order_fee, sum(total_profit) AS total_profit FROM "
				+ "( SELECT dot.real_store_id AS store_id, min(dot.store_province_code) AS store_province_code, "
				+ " ifnull( dround ( sum( CASE WHEN dot.eshop_joint_ims = 'no' THEN dot.order_profit ELSE 0 END), 2 ), 0 ) AS platform_profit, ifnull( dround ( sum( CASE WHEN "
				+ "dot.eshop_joint_ims = 'yes' THEN dot.order_profit ELSE 0 END ), 2 ), 0 ) AS ims_profit, ifnull( dround ( sum( CASE WHEN dot.order_tag4 IS NULL THEN dot.platform_price ELSE 0 END ), 2 ), 0 ) "
				+ "AS order_fee, ifnull( dround (sum(dot.order_profit), 2), 0 ) AS total_profit FROM daqWeb.df_mass_order_monthly dot WHERE strleft (dot.sign_time, 10) >= '"+beginDate+"' and strleft (dot.sign_time, 10)<='"+endDate
				+ "' GROUP BY dot.real_store_id ) tab1 GROUP BY store_id ) tab2 LEFT JOIN daqWeb.t_store ts ON tab2.store_id = ts.id "
				+ " ) tab3 LEFT JOIN daqWeb.t_dist_citycode "
				+ "tdc ON tab3.store_city_code = tdc.cityno";
		String whereStr = " WHERE 1 = 1 ";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			whereStr +=  " and tab3.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and tab3.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		sql= sql+whereStr+ ") aa ";
		//以日为单位不减报损和盘亏
		//退款
		sql = sql+ "LEFT JOIN ( SELECT ifnull( dround (sum(order_profit), 2), 0 ) AS return_profit, real_store_id AS store_id FROM daqWeb.df_mass_order_monthly WHERE strleft (return_time, 10) >= '"+beginDate+"' and strleft (return_time, 10)<='"+endDate+"' GROUP BY real_store_id "
		+ " ) dd ON aa.store_id = dd.store_id ";
		
		sql = sql+" ) tt order by tt.maoli desc ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		return map_result;
	}
	@Override
	public Map<String, Object> getYesterdayStoreProduct(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String count_sql = "";
		String sql_1 = "";
		String sql_2 = "";
		String sql = "SELECT ifnull((ss.rows1-tt.rows2),0) as rank,tt.store_name as store_name,tt.storeno as storeno,tt.product_gmv as product_gmv,tt.product_name as product_name FROM (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_gmv,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows2 FROM gabase.b_item_pro_total bipo WHERE strleft (bipo.sign_time, 10)='"
				+ beginDate+"' ";
		
		String whereStr = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr +=  " and bipo.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and bipo.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		whereStr+=" and bipo.store_name not like '%企业购%'";
		sql = sql+whereStr;
		sql = sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code) tt ";
		//前天门店商品销售
		sql=sql+ "LEFT JOIN (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_count,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows1 FROM gabase.b_item_pro_total bipo WHERE "
				+ "strleft (bipo.sign_time, 10) = '"+endDate+"' ";
		sql = sql+whereStr;		
		sql=sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code ) ss on tt.storeno = ss.storeno and tt.product_id=ss.product_id ";
		count_sql = "select count(1) as count_ from ("+sql+") dd where dd.product_gmv>0 ";
		List<Map<String,Object>> list_count = ImpalaUtil.executeGuoan(count_sql);
		Integer count = Integer.parseInt(String.valueOf(list_count.get(0).get("count_")));
		sql_1 = "select ff.* from ("+sql+") ff where ff.product_gmv>0 order by ff.product_gmv desc limit "+5+" offset 0";
		sql_2 = "select ff.* from ("+sql+") ff where ff.product_gmv>0 order by ff.product_gmv desc limit "+5+" offset "+(count-5);
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql_1);
		List<Map<String,Object>> list_ = ImpalaUtil.executeGuoan(sql_2);
		
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		map_result.put("lst_data2", list_);
		map_result.put("count", count);
		return map_result;
	}
	@Override
	public Map<String, Object> getStoreProductIntervalDay(DynamicDto dynamicDto,DynamicDto dynamicDto2,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String beginDate2 = dynamicDto2.getBeginDate();
		String endDate2 = dynamicDto2.getEndDate();
		String count_sql = "";
		String sql_1 = "";
		String sql_2 = "";
		String sql = "SELECT ifnull((ss.rows1-tt.rows2),0) as rank,tt.store_name as store_name,tt.storeno as storeno,tt.product_gmv as product_gmv,tt.product_name as product_name FROM (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_gmv,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows2 FROM gabase.b_item_pro_total bipo WHERE strleft (bipo.sign_time, 10)>='"
				+ beginDate+"' and strleft (bipo.sign_time, 10)<='"+endDate+"' ";
		
		String whereStr = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr +=  " and bipo.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and bipo.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		whereStr+=" and bipo.store_name not like '%企业购%'";
		sql = sql+whereStr;
		sql = sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code) tt ";
		//前天门店商品销售
		sql=sql+ "LEFT JOIN (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_count,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows1 FROM gabase.b_item_pro_total bipo WHERE "
				+ "strleft (bipo.sign_time, 10) >= '"+beginDate2+"' and strleft (bipo.sign_time, 10)<='"+endDate2+"' ";
		sql = sql+whereStr;		
		sql=sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code ) ss on tt.storeno = ss.storeno and tt.product_id=ss.product_id ";
		count_sql = "select count(1) as count_ from ("+sql+") dd where dd.product_gmv>0 ";
		List<Map<String,Object>> list_count = ImpalaUtil.executeGuoan(count_sql);
		Integer count = Integer.parseInt(String.valueOf(list_count.get(0).get("count_")));
		sql_1 = "select ff.* from ("+sql+") ff where ff.product_gmv>0 order by ff.product_gmv desc limit "+5+" offset 0";
		sql_2 = "select ff.* from ("+sql+") ff where ff.product_gmv>0 order by ff.product_gmv desc limit "+5+" offset "+(count-5);
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql_1);
		List<Map<String,Object>> list_ = ImpalaUtil.executeGuoan(sql_2);
		
		Map<String, Object> map_result = new HashMap<String, Object>();
		map_result.put("lst_data", list);
		map_result.put("lst_data2", list_);
		map_result.put("count", count);
		return map_result;
	}
	@Override
	public Map<String, Object> getProductYesteryRank(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO, PageInfo pageInfo) {
		String whereStr = "";
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "SELECT ifnull((ss.rows1-tt.rows2),0) as rank,tt.store_name as store_name,tt.storeno as storeno,tt.product_gmv as product_gmv,tt.product_name as product_name FROM (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_gmv,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows2 FROM gabase.b_item_pro_total bipo WHERE strleft (bipo.sign_time, 10)='"
				+ beginDate+"' ";;
		String sqlA = "";
		String count_sql = "";
		String searchStr = "";
		String productName = dynamicDto.getSearchstr();
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr +=  " and bipo.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and bipo.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		whereStr+=" and bipo.store_name not like '%企业购%'";
		sql = sql+whereStr;
		sql = sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code) tt ";
		//前天门店商品销售
		sql=sql+ "LEFT JOIN (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_count,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows1 FROM gabase.b_item_pro_total bipo WHERE "
				+ "strleft (bipo.sign_time, 10) = '"+endDate+"' ";
		sql = sql+whereStr;		
		sql=sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code ) ss on tt.storeno = ss.storeno and tt.product_id=ss.product_id ";
		if(StringUtils.isNotEmpty(productName)){
			searchStr = " where ff.product_name like '%"+productName+"%'";
		}
		count_sql = "select count(1) as count_ from ("+sql+") ff "+searchStr;
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
        try{
        	lst_data_count=ImpalaUtil.executeGuoan(count_sql);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sqlA = "select ff.* from ("+sql+") ff "+searchStr+" order by ff.product_gmv desc limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sqlA = "select ff.* from ("+sql+") ff "+searchStr+" order by ff.product_gmv desc limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.executeGuoan(sqlA);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (count_ - 1) / pageInfo.getRecordsPerPage() + 1;
			pageInfo.setTotalRecords(count_);
			pageInfo.setRecordsPerPage(pageInfo.getRecordsPerPage());
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("data", lst_data);
		return map_result;
	}
	@Override
	public Map<String, Object> getStoreProductIntervalDay(DynamicDto dynamicDto,DynamicDto dynamicDto2, List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO, PageInfo pageInfo) {
		String whereStr = "";
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String beginDate2 = dynamicDto2.getBeginDate();
		String endDate2 = dynamicDto2.getEndDate();
		String sql = "SELECT ifnull((ss.rows1-tt.rows2),0) as rank,tt.store_name as store_name,tt.storeno as storeno,tt.product_gmv as product_gmv,tt.product_name as product_name FROM (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_gmv,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows2 FROM gabase.b_item_pro_total bipo WHERE strleft (bipo.sign_time, 10)>='"
				+ beginDate+"' and  strleft (bipo.sign_time, 10)<='"+endDate+"' ";
		String sqlA = "";
		String count_sql = "";
		String searchStr = "";
		String productName = dynamicDto.getSearchstr();
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr +=  " and bipo.store_city_code='"+cityNo+"' ";
			
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr += " and bipo.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		whereStr+=" and bipo.store_name not like '%企业购%'";
		sql = sql+whereStr;
		sql = sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code) tt ";
		//前天门店商品销售
		sql=sql+ "LEFT JOIN (SELECT min(bipo.store_name) AS store_name,min(bipo.store_code) AS storeno,"
				+ "sum(ifnull(bipo.quantity,0)) AS product_count,min(bipo.eshop_pro_name) AS product_name,min(bipo.eshop_pro_id) AS product_id,"
				+ "row_number() OVER(ORDER BY sum(ifnull(bipo.quantity,0)) desc) AS rows1 FROM gabase.b_item_pro_total bipo WHERE "
				+ "strleft (bipo.sign_time, 10) >= '"+beginDate2+"' and strleft (bipo.sign_time, 10) <= '"+endDate2+"' ";
		sql = sql+whereStr;		
		sql=sql+" GROUP BY bipo.eshop_pro_id,bipo.store_code ) ss on tt.storeno = ss.storeno and tt.product_id=ss.product_id ";
		if(StringUtils.isNotEmpty(productName)){
			searchStr = " where ff.product_name like '%"+productName+"%'";
		}
		count_sql = "select count(1) as count_ from ("+sql+") ff "+searchStr;
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> lst_data_count = new ArrayList<Map<String,Object>>();
        Integer count_ = 0;
        try{
        	lst_data_count=ImpalaUtil.executeGuoan(count_sql);
        	count_ = Integer.parseInt(lst_data_count.get(0).get("count_").toString());
        	if(pageInfo.getCurrentPage()==1){
        		sqlA = "select ff.* from ("+sql+") ff "+searchStr+" order by ff.product_gmv desc limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage());
        	}else{
        		sqlA = "select ff.* from ("+sql+") ff "+searchStr+" order by ff.product_gmv desc limit "+pageInfo.getRecordsPerPage()+" offset "+((pageInfo.getCurrentPage()-1)*pageInfo.getRecordsPerPage()+1);
        	}
        	lst_data=ImpalaUtil.executeGuoan(sqlA);
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        }
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (count_ - 1) / pageInfo.getRecordsPerPage() + 1;
			pageInfo.setTotalRecords(count_);
			pageInfo.setRecordsPerPage(pageInfo.getRecordsPerPage());
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("data", lst_data);
		return map_result;
	}
	@Override
	public Map<String, Object> getStoreYesterdayMember(DynamicDto dynamicDto,List<Map<String, Object>> cityNO, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String condition_sql = "";
		String sql = "";
		String proviceStr = "";
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String proviceId = dynamicDto.getProvinceId();
		if(dynamicDto.getStoreId()==null||"".equals(dynamicDto.getStoreId())){
			if(cityNO!=null&&cityNO.size()>0){
				String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
				condition_sql = " and lpad(member.regist_cityno,4,'0') = '"+cityNo+"' ";
				
			}
			if(StringUtils.isNotEmpty(proviceId)){
				proviceStr += " and ts.province_id='"+proviceId+"'";
			}
			sql = "SELECT ifnull(ts.storeno, '无') AS storeno,ifnull(ts. NAME, '无') AS store_name,ifnull(ts.city_name, '无') AS city_name,"
					+ "sum(dutm.nowcount) AS nowcount,ts.province_id as province_id FROM (SELECT regist_storeid,regist_cityno,SUM("
					+ "CASE WHEN member.opencard_time BETWEEN '"+beginDate+" 00:00:00' AND '"+endDate+" 23:59:59' THEN 1 ELSE 0 END) AS nowcount "
					+ "FROM df_user_member member WHERE 1=1 "+condition_sql+" GROUP BY member.regist_storeid) dutm "
					+ "LEFT JOIN t_store ts ON (dutm.regist_storeid = ts.platformid) INNER JOIN t_dist_citycode city ON "
					+ "( lpad(dutm.regist_cityno, 4, '0') = city.cityno) where 1=1 and ts.storeno IS NOT null "+proviceStr+" GROUP BY ts.storeno ORDER BY "
					+ "dutm.nowcount desc ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}
	@Override
	public Map<String, Object> getStoreMemberIntervalDay(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO,PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String condition_sql = "";
		String sql = "";
		String proviceStr = "";
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String proviceId = dynamicDto.getProvinceId();
		if(dynamicDto.getStoreId()==null||"".equals(dynamicDto.getStoreId())){
			if(cityNO!=null&&cityNO.size()>0){
				String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
				condition_sql = " and lpad(member.regist_cityno,4,'0') = '"+cityNo+"' ";
				
			}
			if(StringUtils.isNotEmpty(proviceId)){
				proviceStr += " and ts.province_id='"+proviceId+"'";
			}
			sql = "SELECT ifnull(ts.storeno, '无') AS storeno,ifnull(ts. NAME, '无') AS store_name,ifnull(ts.city_name, '无') AS city_name,"
					+ "sum(dutm.nowcount) AS nowcount,ts.province_id as province_id FROM (SELECT regist_storeid,regist_cityno,SUM("
					+ "CASE WHEN member.opencard_time BETWEEN '"+beginDate+" 00:00:00' AND '"+endDate+" 23:59:59' THEN 1 ELSE 0 END) AS nowcount "
					+ "FROM df_user_member member WHERE 1=1 "+condition_sql+" GROUP BY member.regist_storeid) dutm "
					+ "LEFT JOIN t_store ts ON (dutm.regist_storeid = ts.platformid) INNER JOIN t_dist_citycode city ON "
					+ "( lpad(dutm.regist_cityno, 4, '0') = city.cityno) where 1=1 and ts.storeno IS NOT null "+proviceStr+" GROUP BY ts.storeno ORDER BY "
					+ "dutm.nowcount desc ";
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}
}
