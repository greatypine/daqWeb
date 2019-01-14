package com.cnpc.pms.platform.dao.impl;

import com.cnpc.pms.base.dao.core.impl.DAORootHibernate;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.platform.dao.OrderAmountDao;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.entity.OrderAmountDto;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import java.util.*;


/**
 */
public class OrderAmountDaoImpl extends DAORootHibernate implements OrderAmountDao {

	private static Log logger = LogFactory.getLog(OrderAmountDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryOrderAmount(OrderAmountDto orderAmountDto, PageInfo pageInfo,String tableName) {
		// TODO Auto-generated method stub
		String lineSql="";
		String lineFalg="";
		String lineBool=""; 
		if ("underline".equals(orderAmountDto.getLineType())) {//线下
			
			lineFalg="线下";
			lineBool=" not in ";
		}else {//线上
			lineFalg="线上";
			lineBool=" in ";
		}
		String sql = "select * from (select '"+lineFalg+"' as linetype, a.success_time as success_time, CONCAT(a.id,'') as id, \r\n" + 
				"a.order_sn,a.eshop_name,a.insert_time as insert_time," + 
				"a.create_time,a.sign_time,a.return_time,\r\n" + 
				"a.appointment_start_time,IFNULL(a.trading_price,0) as trading_price,IFNULL(a.payable_price,0) as payable_price,\r\n" + 
				"IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,a.channel_name, " + 
				"a.department_name,a.store_name as store_name,\r\n" + 
				"a.contract_id,IFNULL(a.business_type,'') as business_type,\r\n" 
				+"IFNULL(a.order_profit,0) as order_profit,IFNULL(a.apportion_rebate,0) as apportion_rebate,\r\n" + 
				"IFNULL(a.platform_price,0) as apportion_coupon,IFNULL(a.cost_price,0) as cost_price,\r\n" + 
				"IFNULL(CASE a.contract_method WHEN 'price' THEN '从价' WHEN  'volume' THEN '从量' WHEN 'percent' THEN '从率' END,'') as contract_method  "
				+"from daqWeb."+tableName+" a where 1=1 ";
		String sqlCount="select * from (select a.order_sn as order_sn,a.insert_time as insert_time from daqWeb."+tableName+" a where 1=1 ";	
		String whereSql="";
		String whereDffSql="";
		
		if (StringUtils.isNotEmpty(orderAmountDto.getEshop_name())) {
			sql = sql + " and a.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
			sqlCount= sqlCount + " and a.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
			whereSql=" and df.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
			whereDffSql=" and dff.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getBeginDate())) {
			String beginDate = orderAmountDto.getBeginDate().replace("/", "-");
			String endDate = orderAmountDto.getEndDate().replace("/", "-");
			sql = sql + " and (a.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
			sqlCount = sqlCount + " and (a.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
			whereSql = whereSql + " and (df.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
			whereDffSql = whereDffSql + " and (dff.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(orderAmountDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(orderAmountDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
				sqlCount = sqlCount + " and (a.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
				whereSql = whereSql + " and (df.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or df.normal_store_id='"+(String) position_obj.get("platformid")+"')";
				whereDffSql = whereDffSql + " and (dff.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or dff.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
				sqlCount = sqlCount + " and a.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
				whereSql = whereSql + " and df.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
				whereDffSql = whereDffSql + " and dff.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getEshop_id())) {
			sql = sql + " and a.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
			sqlCount = sqlCount + " and a.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
			whereSql = whereSql + " and df.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
			whereDffSql = whereDffSql + " and dff.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getCity_name())) {
			sql = sql + " and lpad(a.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
			sqlCount = sqlCount + " and lpad(a.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
			whereSql = whereSql + " and lpad(df.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
			whereDffSql = whereDffSql + " and lpad(dff.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getDepartment_name())) {
			sql = sql + " and a.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			sqlCount = sqlCount + " and a.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			whereSql = whereSql + " and df.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			whereDffSql = whereDffSql + " and dff.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getChannel_name())) {
			sql = sql + " and a.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
			sqlCount = sqlCount + " and a.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
			whereSql = whereSql + " and df.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
			whereDffSql = whereDffSql + " and dff.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			sqlCount = sqlCount + " and a.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			whereSql = whereSql + " and df.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			whereDffSql = whereDffSql + " and dff.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			
		}
		lineSql=" and a.order_sn"+lineBool+"(select ord.order_sn as order_sn from gemini.t_settlement_item sett \r\n" + 
		" left join (select der.id as id,der.order_sn as order_sn from gemini.t_order der left join daqweb.df_mass_order_total df\r\n" 
		+"			on der.order_sn=df.order_sn where 1=1"+whereSql
		+"			) ord on sett.order_id=ord.id where order_sn is not null) ";
		

		sqlCount = sqlCount + lineSql+") tab where tab.insert_time in (select max(insert_time) from daqweb.df_mass_order_total dff where 1=1 "+whereDffSql+" group by order_sn)";
		
		String sql_count = "SELECT COUNT(1) as total FROM (" + sqlCount + ") T";
		List<Map<String,Object>> executeGuoan = ImpalaUtil.executeGuoan(sql_count);
		System.out.println(sql_count);
		String total="";
		if (executeGuoan!=null&&executeGuoan.size()>0) {
			 total = executeGuoan.get(0).get("total").toString();
		}
		pageInfo.setTotalRecords(Integer.valueOf(total));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		sql = sql + lineSql+" order by a.success_time desc "+"limit "+ recordsPerPage+" offset "+ startData+") tab where tab.insert_time in (select max(insert_time) from daqweb.df_mass_order_total dff where 1=1 "+whereDffSql+" group by order_sn)";	
		try {
			List<Map<String,Object>> lst_data = ImpalaUtil.executeGuoan(sql);
			System.out.println(sql);
			Map<String, Object> map_result = new HashMap<String, Object>();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("data", lst_data);
			map_result.put("total_pages", total_pages);
			return map_result;	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	@Override
	public List<Map<String, Object>> exportOrder(OrderAmountDto orderAmountDto, String tableName) {
		// TODO Auto-generated method stub      

		
		String lineSql="";
		String lineFalg="";
		String lineBool=""; 
		if ("underline".equals(orderAmountDto.getLineType())) {//线下
			
			lineFalg="线下";
			lineBool=" not in ";
		}else {//线上
			lineFalg="线上";
			lineBool=" in ";
		}
		String sql = "select * from (select '"+lineFalg+"' as linetype,a.insert_time as insert_time,IFNULL(a.store_city_name,'') as store_city_name,IFNULL(a.store_code,'') as store_code,IFNULL(a.info_village_code,'') as village_code,IFNULL(a.customer_id,'') as customer_id, a.success_time as success_time, CONCAT(a.id,'') as id," + 
				"a.order_sn,IFNULL(a.customer_mobile_phone,'') as customer_mobile_phone,IFNULL(a.employee_phone,'') as employee_phone,a.eshop_name,IFNULL(a.employee_name,'') as employee_name," + 
				"a.create_time,a.sign_time,IFNULL(a.return_time,'') as return_time," + 
				"IFNULL(a.appointment_start_time,'') as appointment_start_time,a.employee_no,IFNULL(a.trading_price,0) as trading_price,IFNULL(a.payable_price,0) as payable_price," + 
				"IFNULL(ROUND(a.gmv_price,2),0) as gmv_price,a.customer_name,IFNULL(a.addr_name,'') as addr_name," + 
				"IFNULL(a.addr_mobilephone,'') as addr_mobilephone,IFNULL(a.addr_address,'') as addr_address,a.channel_name," + 
				"a.department_name,a.store_name as store_name,IFNULL(a.area_code,'') as area_code,IFNULL(a.info_employee_a_no,'') as info_employee_a_no," + 
				"IFNULL(a.order_tag1,'') as order_tag1,IFNULL(a.order_tag2,'') as order_tag2," + 
				"IFNULL(a.order_tag3,'') as order_tag3," + 
				"a.contract_id,IFNULL(a.business_type,'') as business_type," + 
				"IFNULL(a.order_profit,0) as order_profit,IFNULL(a.apportion_rebate,0) as apportion_rebate," + 
				"IFNULL(a.platform_price,0) as apportion_coupon,IFNULL(a.cost_price,0) as cost_price," + 
				"IFNULL(CASE a.contract_method WHEN 'price' THEN '从价' WHEN 'volume' THEN '从量' WHEN 'percent' THEN '从率' END,'') as contract_method,IFNULL(a.order_tag4,'') as order_tag4," + 
				"CASE WHEN a.pubseas_label='1' THEN '是'  ELSE '否' END AS pubseas_label," + 
				"CASE WHEN a.abnormal_label='1' THEN '是'  ELSE '否' END AS abnormal_label,CASE WHEN a.return_label='1' THEN '是'  ELSE '否' END AS return_label," + 
				"CASE WHEN a.loan_label='1' THEN '是'  ELSE '否' END AS loan_label,CASE WHEN a.loan_label='3' THEN '是'  ELSE '否' END AS car_label," + 
				"CASE WHEN a.loan_label='4' THEN '是'  ELSE '否' END AS quick_label,CASE WHEN a.loan_label='5' THEN '是'  ELSE '否' END AS gift_label," + 
				"CASE WHEN a.order_tag4 is not null THEN '是'  ELSE '否' END AS a_fee_label," + 
				"CASE WHEN a.customer_isnew_flag='20' THEN '拉新20元' WHEN a.customer_isnew_flag='10' THEN '拉新10元' WHEN a.customer_isnew_flag='0' THEN '拉新'  ELSE '否' END AS customer_isnew_flag," + 
				"CASE WHEN a.order_tag1 like '%B%' THEN '是'  ELSE '否' END AS order_tag_b,CASE WHEN a.order_tag4='A3' THEN '是'  ELSE '否' END AS order_tag_k," + 
				"CASE WHEN a.order_tag1 like '%S%' THEN '是'  ELSE '否' END AS order_tag_s, CASE WHEN a.score is not null THEN '是' ELSE '否' END AS score," + 
				"CASE WHEN a.order_tag2 like '%1%' THEN '是'  ELSE '否' END AS order_tag_product, CASE WHEN a.order_tag2 like '%2%' THEN '是' ELSE '否' END AS order_tag_service," + 
				"CASE WHEN a.order_tag2 like '%3%' THEN '是'  ELSE '否' END AS order_tag_groupon," + 
				"CASE WHEN a.order_tag1 like '%M%' THEN '是'  ELSE '否' END AS order_tag_member," + 
				"CASE WHEN a.order_tag3='0' THEN '是'  ELSE '否' END AS no_cost_label," + 
				"CASE WHEN a.order_tag3='2' THEN '是'  ELSE '否' END AS pay_label," + 
				"CASE a.order_source WHEN 'app' THEN 'APP' WHEN 'callcenter' THEN '400客服' WHEN 'store' THEN '门店' WHEN 'wechat' THEN '微信' " + 
				"WHEN 'pad' THEN '智能终端' WHEN 'score' THEN '积分' WHEN 'web' THEN 'WEB' WHEN 'citic_vip_gift' THEN '中信vip礼品' WHEN 'tv' THEN '电视' WHEN 'microMarket' THEN '微超订单' ELSE '无' END AS order_source "
				+"from daqWeb."+tableName+" a where 1=1 ";
		String whereSql="";
		String whereDffSql="";
		
		if (StringUtils.isNotEmpty(orderAmountDto.getEshop_name())) {
			sql = sql + " and a.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
			whereSql=" and df.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
			whereDffSql=" and dff.eshop_name = '" + orderAmountDto.getEshop_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getBeginDate())) {
			String beginDate = orderAmountDto.getBeginDate().replace("/", "-");
			String endDate = orderAmountDto.getEndDate().replace("/", "-");
			sql = sql + " and (a.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
			whereSql = whereSql + " and (df.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
			whereDffSql = whereDffSql + " and (dff.success_time between '" + beginDate + " 00:00:00' and '"
					+ endDate + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(orderAmountDto.getStore_no())){
			Map<String,Object> position_obj = queryPlatformidByCode(orderAmountDto.getStore_no());
			if (position_obj != null) {
				sql = sql + " and (a.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or a.normal_store_id='"+(String) position_obj.get("platformid")+"')";
				whereSql = whereSql + " and (df.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or df.normal_store_id='"+(String) position_obj.get("platformid")+"')";
				whereDffSql = whereDffSql + " and (dff.store_code ='" + orderAmountDto.getStore_no().trim()+ "' or dff.normal_store_id='"+(String) position_obj.get("platformid")+"')";
			}else{
				sql = sql + " and a.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
				whereSql = whereSql + " and df.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
				whereDffSql = whereDffSql + " and dff.store_code ='" + orderAmountDto.getStore_no().trim()+ "'";
			}
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getEshop_id())) {
			sql = sql + " and a.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
			whereSql = whereSql + " and df.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
			whereDffSql = whereDffSql + " and dff.eshop_id ='" + orderAmountDto.getEshop_id().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getCity_name())) {
			sql = sql + " and lpad(a.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
			whereSql = whereSql + " and lpad(df.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
			whereDffSql = whereDffSql + " and lpad(dff.store_city_code,4,'0') = '" + orderAmountDto.getCity_name() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getDepartment_name())) {
			sql = sql + " and a.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			whereSql = whereSql + " and df.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			whereDffSql = whereDffSql + " and dff.department_name = '" + orderAmountDto.getDepartment_name().trim() + "'";
			
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getChannel_name())) {
			sql = sql + " and a.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
			whereSql = whereSql + " and df.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
			whereDffSql = whereDffSql + " and dff.channel_name = '" + orderAmountDto.getChannel_name().trim() + "'";
		}
		if (StringUtils.isNotEmpty(orderAmountDto.getOrder_sn())) {
			sql = sql + " and a.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			whereSql = whereSql + " and df.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			whereDffSql = whereDffSql + " and dff.order_sn ='" + orderAmountDto.getOrder_sn().trim() + "'";
			
		}
		lineSql=" and a.order_sn"+lineBool+"(select ord.order_sn as order_sn from gemini.t_settlement_item sett " + 
		" left join (select der.id as id,der.order_sn as order_sn from gemini.t_order der left join daqweb.df_mass_order_total df " 
		+"			on der.order_sn=df.order_sn where 1=1"+whereSql
		+"			) ord on sett.order_id=ord.id where order_sn is not null) ";
		
	
		sql = sql + lineSql+" order by a.success_time desc ) tab where tab.insert_time in (select max(insert_time) from daqweb.df_mass_order_total dff where 1=1 "+whereDffSql+" group by order_sn) order by tab.success_time desc";	
try {
			List<Map<String,Object>> lst_data = ImpalaUtil.executeGuoan(sql);
		
			return lst_data;	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Map<String, Object> queryPlatformidByCode(String storeno){
		String sql = "select t.platformid from t_store t where 1=1 ";
		if(StringUtils.isNotEmpty(storeno)){
			sql = sql + " AND t.storeno='"+storeno+"' ";
		}

		List<?> lst_data = new ArrayList<Map<String, Object>>();
		try{
			 lst_data = ImpalaUtil.executeGuoan(sql);
		}catch (Exception e){
			e.printStackTrace();
		}
		// 获得查询数据
		Map<String, Object> order_obj = null;

		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}
	public void updataReport(Long id , String url) {
		String sql = " UPDATE t_report_filedown set mark_1 = '1',url =? where id =? ";
		Session session = this.getSession();
		session.createSQLQuery(sql).setParameters(
				new Object[] {url, id }, new Type[] { Hibernate.STRING,Hibernate.LONG })
			.executeUpdate();
		session.close();
	}

}
