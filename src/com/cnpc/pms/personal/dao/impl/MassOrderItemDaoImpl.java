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
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto, PageInfo pageInfo,String timeFlag) {
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
		String sqlTableMass = "";
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_daily tor ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_monthly tor ";
		} else {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_total tor ";
		}
		String selectQuery = "IFNULL(toip.order_sn,'') AS order_sn, IFNULL(tor.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, " +
				"IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code,IFNULL(tor.info_village_code,'') as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.order_create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(tor.customer_name,'') AS customer_name, IFNULL(tor.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(tor.addr_address,'') AS order_address, IFNULL(tor.addr_mobilephone,'') AS order_mobilephone, IFNULL(tor.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price,toip.quantity AS quantity, toip.cost_price AS cost_price, toip.order_create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.order_signed_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(tor.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.order_cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time, IFNULL(tor.employee_name,'') AS employee_name,IFNULL(tor.employee_phone,'') AS employee_phone,IFNULL(tor.department_name,'') AS dep_name, " +
				"IFNULL(tor.channel_name,'') AS channel_name, IFNULL(toc.contents,'') AS order_contents," +
				" IFNULL(tor.store_name,'') AS store_name, IFNULL(tor.store_code,'') AS store_code,IFNULL(tor.store_city_name,'') as store_city_name,IFNULL(tor.area_code,'') as area_code,IFNULL(tor.info_employee_a_no,'') as info_employee_a_no, IFNULL(tor.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toc.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end, toc.star_level AS " +
				"star_level";
		String sqlA = "SELECT " +selectQuery+" from datacube_kudu.t_order_item_pro toip left join "+sqlTableMass+" on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment_dr toc ON toc.order_id = tor.id and toc.eshop_pro_id = toip.eshop_pro_id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		String sqlB = "SELECT count(1) as count_ "+" from datacube_kudu.t_order_item_pro toip left join "+sqlTableMass+" on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment_dr toc ON toc.order_id = tor.id and toc.eshop_pro_id = toip.eshop_pro_id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and toip.order_sn = '" + order_sn.trim() + "' ";
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
				whereStr = whereStr + " and toc.contents <>''  ";
			}else if("3".equals(comment_Flag)){
				whereStr = whereStr + " and (toc.contents ='' or toc.contents is null)  ";
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
		String sqlTableMass = "";
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_daily tor ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_monthly tor ";
		} else {
			sqlTableMass = sqlTableMass + " daqWeb.df_mass_order_total tor ";
		}
		String selectQuery = "IFNULL(toip.order_sn,'') AS order_sn, IFNULL(tor.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, " +
				"IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code,IFNULL(tor.info_village_code,'') as village_code,CASE toip.order_source WHEN 'app' THEN 'APP' WHEN " +
				"'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' WHEN 'pad' THEN " +
				"'智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' " +
				"WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source, IFNULL(from_unixtime(unix_timestamp(toip.order_create_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"AS create_time, IFNULL(toip.eshop_pro_id,'') AS product_id, IFNULL(toip.eshop_pro_name,'') AS product_name, IFNULL(toip.eshop_id,'') AS " +
				"eshop_id, IFNULL(toip.eshop_name,'') AS eshop_name, IFNULL(tor.customer_name,'') AS customer_name, IFNULL(tor.customer_mobile_phone,'') AS customer_mobilephone," +
				"IFNULL(tor.addr_address,'') AS order_address, IFNULL(tor.addr_mobilephone,'') AS order_mobilephone, IFNULL(tor.addr_name,'') AS order_customer_name," +
				"IFNULL(toip.unit,'') AS unit, toip.unit_price AS original_price,IFNULL(toip.quantity,0) AS quantity, toip.cost_price AS cost_price, toip.order_create_time AS order_create_time," +
				"IFNULL(from_unixtime(unix_timestamp(toip.order_signed_time),'yyyy-MM-dd HH:mm:ss'),'') AS df_signed_time,IFNULL(from_unixtime(unix_timestamp(tor.appointment_start_time),'yyyy-MM-dd HH:mm:ss'),'') " +
				"as appointment_start_time, from_unixtime(unix_timestamp(toip.order_cancel_time),'yyyy-MM-dd HH:mm:ss') AS order_cancel_time,IFNULL(from_unixtime(unix_timestamp(toip.order_commented_time),'yyyy-MM-dd HH:mm:ss'),'') AS order_commented_time, IFNULL(tor.employee_name,'') AS employee_name,IFNULL(tor.employee_phone,'') AS employee_phone,IFNULL(tor.department_name,'') AS dep_name, " +
				"IFNULL(tor.channel_name,'') AS channel_name, IFNULL(toc.contents,'') AS order_contents,toc.star_level AS star_level,toec.star_level_1 AS star_level_1,toec.star_level_2 AS star_level_2,toac.days AS next_days,IFNULL(toac.contents,'') AS next_contents," +
				" IFNULL(tor.store_name,'') AS store_name, IFNULL(tor.store_code,'') AS store_code,IFNULL(tor.store_city_name,'') as store_city_name,IFNULL(tor.area_code,'') AS area_code,IFNULL(tor.info_employee_a_no,'') as info_employee_a_no, IFNULL(tor.normal_store_id,'') AS store_id, IFNULL(toip.store_city_code,'') AS store_city_code," +
				" CASE toc.rate WHEN 'good' THEN '好' WHEN 'normal' THEN '普通' WHEN 'bad' THEN '差' ELSE '' END AS order_content_end ";
		String sqlA = "SELECT " +selectQuery+" from datacube_kudu.t_order_item_pro toip left join "+sqlTableMass+" on tor.id = toip.order_id LEFT JOIN gemini.t_order_comment_dr toc ON toc.order_id = tor.id and toc.eshop_pro_id = toip.eshop_pro_id LEFT JOIN gemini.t_order_additional_comment toac ON toac.order_id = toip.order_id and toac.eshop_pro_id = toip.eshop_pro_id "
				+ " LEFT JOIN gemini.t_order_eshop_comment_dr toec ON toec.order_id = toip.order_id LEFT JOIN daqWeb.t_area ta ON tor.area_code = ta.area_no LEFT JOIN daqWeb.tiny_village_code vc ON tor.info_village_code = vc. CODE where 1=1  ";
		if(StringUtils.isNotEmpty(order_sn)){
			whereStr = whereStr + " and toip.order_sn  = '" + order_sn.trim() + "' ";
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
				whereStr = whereStr + " and toc.contents <>''  ";
			}else if("3".equals(comment_Flag)){
				whereStr = whereStr + " and (toc.contents ='' or toc.contents is null)  ";
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
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String timeFlag) {
		String sql = "SELECT IFNULL(a.area_code,'') AS area_code, IFNULL(a.store_name,'') AS store_name, IFNULL(vc.tiny_village_name,'') AS village_name, "
				+ "IFNULL(ta.`name`,'') AS area_name, vc.tiny_village_id as village_id, vc.code as village_code FROM ";
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}		
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
		+ "sum(ifnull(dbaosun.count_money, 0)) AS baosun,sum(ifnull(dpankui.count_money, 0)) AS pankui from ( "
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
		//盘亏
		sql = sql + "left join (select count_money,city_code,create_date,num  from (select ifnull(dround(sum(pankui.count_money),2),0) as count_money,ts.city_code,"
				+ "pankui.create_date,ROW_NUMBER() OVER(PARTITION BY city_code ORDER BY create_date DESC) as num from df_pankui_baosun_info pankui "
				+ "join gemini.t_store ts  on pankui.store_code=ts.code where pankui.count_type='1' and pankui.count_month='"+beginDate+"' "
				+ "group by ts.city_code,pankui.create_date ) aa having num=1) dpankui on aa.store_city_code=dpankui.city_code ";
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
	public Map<String, Object> getProfitRangeForWeek(DynamicDto dynamicDto,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		String beginDate = dynamicDto.getBeginDate();
		String endDate = dynamicDto.getEndDate();
		String sql = "select sum(ifnull(aa.platform_profit,0)) AS platform_profit,sum(ifnull(aa.ims_profit,0)) AS ims_profit,sum(ifnull(aa.order_fee,0)) "
		+ "AS order_fee,sum(ifnull(aa.total_profit,0)) AS total_profit, sum(ifnull(dd.return_profit, 0)) AS return_profit,from_unixtime(unix_timestamp(aa.sign_time),'yyyy-MM-dd') AS week_date "
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
		String sql = "SELECT count(DISTINCT(case when trading_price > 10 and (date_format(a.sign_time, '%Y-%m') = '"+userOperationStatDto.getBeginDate()+"') then customer_id end)) pay_10_count FROM ";
		
			sql = sql + " df_mass_order_monthly a ";
		
		sql = sql + " where customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
        if(StringUtils.isNotEmpty(userOperationStatDto.getEmployeeNo())){
            sql = sql + " and a.employee_no ='" + userOperationStatDto.getEmployeeNo().trim()+ "'";
        }
		sql = sql + " GROUP BY a.employee_no ";
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
}
