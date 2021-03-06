package com.cnpc.pms.dynamic.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.dao.UserOperationStatDao;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.UserOperationStatDto;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserOperationStatDaoImpl extends BaseDAOHibernate implements UserOperationStatDao {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryEmployeeMoreStat(UserOperationStatDto userOperationStatDto, PageInfo pageInfo, String timeFlag) {
		String sql="select IFNULL(store_city_name,'') as city_name,IFNULL(store_code,'') as store_code,IFNULL(store_name,'') as store_name,IFNULL(info_employee_a_no,'') as employee_a_no,count(distinct customer_id) cus_count,"
				+ "count(distinct(case when customer_isnew_flag !='-1' then customer_id end)) as new_cus_count from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where info_employee_a_no is not null and info_employee_a_no !='' ";
		
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + userOperationStatDto.getBeginDate() + " 00:00:00' and '"
					+ userOperationStatDto.getEndDate() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY info_employee_a_no order by store_code";

		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";
		
		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + startData + "," + recordsPerPage;

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportEmployeeMoreStat(UserOperationStatDto userOperationStatDto,String timeFlag){
		String sql="select IFNULL(store_city_name,'') as city_name,IFNULL(store_code,'') as store_code,IFNULL(store_name,'') as store_name,IFNULL(info_employee_a_no,'') as employee_a_no,count(distinct customer_id) cus_count,"
				+ "count(distinct(case when customer_isnew_flag !='-1' then customer_id end)) as new_cus_count from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where info_employee_a_no is not null and info_employee_a_no !='' ";
		
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + userOperationStatDto.getBeginDate() + " 00:00:00' and '"
					+ userOperationStatDto.getEndDate() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY info_employee_a_no order by store_code";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryEmployeeAreamoreStat(UserOperationStatDto userOperationStatDto, PageInfo pageInfo, String timeFlag) {
		String sql="select IFNULL(store_city_name,'') as city_name,IFNULL(store_code,'') as store_code,IFNULL(store_name,'') as store_name,IFNULL(info_employee_a_no,'') as employee_a_no,"
				+ "IFNULL(area_code,'无') as area_code,count(distinct customer_id) cus_count,"
				+ "count(distinct(case when customer_isnew_flag !='-1' then customer_id end)) as new_cus_count from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where info_employee_a_no is not null and info_employee_a_no !='' ";
		
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + userOperationStatDto.getBeginDate() + " 00:00:00' and '"
					+ userOperationStatDto.getEndDate() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY area_code order by store_code";

		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";
		
		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + startData + "," + recordsPerPage;

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportEmployeeAreamoreStat(UserOperationStatDto userOperationStatDto,String timeFlag){
		String sql="select IFNULL(store_city_name,'') as city_name,IFNULL(store_code,'') as store_code,IFNULL(store_name,'') as store_name,IFNULL(info_employee_a_no,'') as employee_a_no,"
				+ "IFNULL(area_code,'无') as area_code,count(distinct customer_id) cus_count,"
				+ "count(distinct(case when customer_isnew_flag !='-1' then customer_id end)) as new_cus_count from ";
		
		if (MassOrderDto.TimeFlag.CUR_DAY.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_daily a ";
		} else if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where info_employee_a_no is not null and info_employee_a_no !='' ";
		
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and (a.sign_time between '" + userOperationStatDto.getBeginDate() + " 00:00:00' and '"
					+ userOperationStatDto.getEndDate() + " 23:59:59')";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY area_code order by store_code";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryNewCusStat(UserOperationStatDto userOperationStatDto,PageInfo pageInfo){

		String sqlTempA = " and customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sqlTempB = " GROUP BY a.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempB = sqlTempB + ",a.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempB = sqlTempB + ",a.area_code ";
		}

		String sqlTempC = " GROUP BY buf.cityno ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempC = sqlTempC + ",buf.storeno ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempC = sqlTempC + ",but.area_no ";
		}

		String sql = "select max(aa.city_name) as city_name,max(aa.store_name) as store_name,max(aa.store_code) as store_code, max(IFNULL(aa.area_code,'')) AS area_code," +
				"max(total_count) as total_count, max(new_count) as new_count, max(new_10_count) as new_10_count, max(new_20_count) as new_20_count from (";

		sql = sql + "select max(buf.city_name) as city_name,buf.cityno as store_city_code,max(ts.name) as store_name,max(buf.storeno) as store_code,max(but.area_no) as area_code," +
				"count(distinct(buf.customer_id)) as total_count,0 as new_count, 0 as new_10_count, 0 as new_20_count FROM gabase.b_user_first_order_store buf " +
				"left join gemini.t_store ts on buf.storeno=ts.code left join gabase.b_user_tiny but on buf.customer_id=but.customer_id and buf.storeno=but.storeno " +
				"where buf.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempC;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,count(DISTINCT a.customer_id) new_count, 0 as new_10_count, 0 as new_20_count FROM  df_mass_order_total a " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and a.customer_isnew_flag >='0' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as new_count,count(DISTINCT a.customer_id) new_10_count, 0 as new_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and customer_isnew_flag >= '10' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as new_count, 0 as new_10_count, count(DISTINCT a.customer_id) new_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and customer_isnew_flag >= '20' " + sqlTempA + sqlTempB;

		sql = sql + ") aa where 1=1 ";

		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and aa.city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and aa.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getAreaCode())){
			sql = sql + " and aa.area_code ='" + userOperationStatDto.getAreaCode().trim()+ "'";
		}
		sql = sql + " GROUP BY aa.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sql = sql + ",aa.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sql = sql + ",aa.area_code ";
		}

		sql = sql + " ORDER BY aa.store_city_code ";

		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

		String total = "0";
		List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
		if(resultCount !=null && resultCount.size()>0 ){
			total = String.valueOf(resultCount.get(0).get("total"));

		}

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));
		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportNewCusStat(UserOperationStatDto userOperationStatDto){
		String sqlTempA = " and customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sqlTempB = " GROUP BY a.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempB = sqlTempB + ",a.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempB = sqlTempB + ",a.area_code ";
		}

		String sqlTempC = " GROUP BY buf.cityno ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempC = sqlTempC + ",buf.storeno ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempC = sqlTempC + ",but.area_no ";
		}

		String sql = "select max(aa.city_name) as city_name,max(aa.store_name) as store_name,max(aa.store_code) as store_code, max(IFNULL(aa.area_code,'')) AS area_code," +
				"max(total_count) as total_count, max(new_count) as new_count, max(new_10_count) as new_10_count, max(new_20_count) as new_20_count from (";

		sql = sql + "select max(buf.city_name) as city_name,buf.cityno as store_city_code,max(ts.name) as store_name,max(buf.storeno) as store_code,max(but.area_no) as area_code," +
				"count(distinct(buf.customer_id)) as total_count,0 as new_count, 0 as new_10_count, 0 as new_20_count FROM gabase.b_user_first_order_store buf " +
				"left join gemini.t_store ts on buf.storeno=ts.code left join gabase.b_user_tiny but on buf.customer_id=but.customer_id and buf.storeno=but.storeno " +
				"where buf.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempC;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,count(DISTINCT a.customer_id) new_count, 0 as new_10_count, 0 as new_20_count FROM  df_mass_order_total a " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and a.customer_isnew_flag >='0' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as new_count,count(DISTINCT a.customer_id) new_10_count, 0 as new_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and customer_isnew_flag >= '10' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as new_count, 0 as new_10_count, count(DISTINCT a.customer_id) new_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and customer_isnew_flag >= '20' " + sqlTempA + sqlTempB;

		sql = sql + ") aa where 1=1 ";

		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and aa.city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and aa.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getAreaCode())){
			sql = sql + " and aa.area_code ='" + userOperationStatDto.getAreaCode().trim()+ "'";
		}
		sql = sql + " GROUP BY aa.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sql = sql + ",aa.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sql = sql + ",aa.area_code ";
		}

		sql = sql + " ORDER BY aa.store_city_code ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryPayCusStat(UserOperationStatDto userOperationStatDto,PageInfo pageInfo){

		String sqlTempA = " and customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sqlTempB = " GROUP BY a.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempB = sqlTempB + ",a.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempB = sqlTempB + ",a.area_code ";
		}

		String sqlTempC = " GROUP BY buf.cityno ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempC = sqlTempC + ",buf.storeno ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempC = sqlTempC + ",but.area_no ";
		}

		String sql = "select max(aa.city_name) as city_name,max(aa.store_name) as store_name,max(aa.store_code) as store_code, max(IFNULL(aa.area_code,'')) AS area_code," +
				"max(total_count) as total_count, max(pay_count) as pay_count, max(pay_10_count) as pay_10_count, max(pay_20_count) as pay_20_count from (";

		sql = sql + "select max(buf.city_name) as city_name,buf.cityno as store_city_code,max(ts.name) as store_name,max(buf.storeno) as store_code,max(but.area_no) as area_code," +
				"count(distinct(buf.customer_id)) as total_count,0 as pay_count, 0 as pay_10_count, 0 as pay_20_count FROM gabase.b_user_first_order_store buf " +
				"left join gemini.t_store ts on buf.storeno=ts.code left join gabase.b_user_tiny but on buf.customer_id=but.customer_id and buf.storeno=but.storeno " +
				"where buf.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempC;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,count(DISTINCT a.customer_id) pay_count, 0 as pay_10_count, 0 as pay_20_count FROM  df_mass_order_total a " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as pay_count,count(DISTINCT a.customer_id) pay_10_count, 0 as pay_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and trading_price > 10 " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as pay_count, 0 as pay_10_count, count(DISTINCT a.customer_id) pay_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and trading_price > 20 " + sqlTempA + sqlTempB;

		sql = sql + ") aa where 1=1 ";

		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and aa.city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and aa.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
        if(StringUtils.isNotEmpty(userOperationStatDto.getAreaCode())){
            sql = sql + " and aa.area_code ='" + userOperationStatDto.getAreaCode().trim()+ "'";
        }
		sql = sql + " GROUP BY aa.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sql = sql + ",aa.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sql = sql + ",aa.area_code ";
		}

		sql = sql + " ORDER BY aa.store_city_code ";

		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

		String total = "0";
		List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
		if(resultCount !=null && resultCount.size()>0 ){
			total = String.valueOf(resultCount.get(0).get("total"));

		}

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));
		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportPayCusStat(UserOperationStatDto userOperationStatDto){

		String sqlTempA = " and customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";

		String sqlTempB = " GROUP BY a.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempB = sqlTempB + ",a.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempB = sqlTempB + ",a.area_code ";
		}

		String sqlTempC = " GROUP BY buf.cityno ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sqlTempC = sqlTempC + ",buf.storeno ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sqlTempC = sqlTempC + ",but.area_no ";
		}

		String sql = "select max(aa.city_name) as city_name,max(aa.store_name) as store_name,max(aa.store_code) as store_code, max(IFNULL(aa.area_code,'')) AS area_code," +
				"max(total_count) as total_count, max(pay_count) as pay_count, max(pay_10_count) as pay_10_count, max(pay_20_count) as pay_20_count from (";

		sql = sql + "select max(buf.city_name) as city_name,buf.cityno as store_city_code,max(ts.name) as store_name,max(buf.storeno) as store_code,max(but.area_no) as area_code," +
				"count(distinct(buf.customer_id)) as total_count,0 as pay_count, 0 as pay_10_count, 0 as pay_20_count FROM gabase.b_user_first_order_store buf " +
				"left join gemini.t_store ts on buf.storeno=ts.code left join gabase.b_user_tiny but on buf.customer_id=but.customer_id and buf.storeno=but.storeno " +
				"where buf.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempC;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,count(DISTINCT a.customer_id) pay_count, 0 as pay_10_count, 0 as pay_20_count FROM  df_mass_order_total a " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as pay_count,count(DISTINCT a.customer_id) pay_10_count, 0 as pay_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and trading_price > 10 " + sqlTempA + sqlTempB;

		sql = sql + " union all ";

		sql = sql + "SELECT max(a.store_city_name) as city_name,max(a.store_city_code) as store_city_code, max(a.store_name) as store_name,max(a.store_code) as store_code, max(IFNULL(a.area_code,'')) AS area_code," +
				"0 as total_count,0 as pay_count, 0 as pay_10_count, count(DISTINCT a.customer_id) pay_20_count FROM  df_mass_order_total a  " +
				"where a.sign_time >= '" + userOperationStatDto.getBeginDate() + "' and  a.sign_time <='"+userOperationStatDto.getEndDate() + "' and trading_price > 20 " + sqlTempA + sqlTempB;

		sql = sql + ") aa where 1=1 ";

		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and aa.city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and aa.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getAreaCode())){
			sql = sql + " and aa.area_code ='" + userOperationStatDto.getAreaCode().trim()+ "'";
		}
		sql = sql + " GROUP BY aa.store_city_code ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchStoreStr())){
			sql = sql + ",aa.store_code ";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getSearchAreaStr())){
			sql = sql + ",aa.area_code ";
		}

		sql = sql + " ORDER BY aa.store_city_code ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryAreaInfoByCode(String area_code) {
		String sql = "select ifnull(ta.name,'') as area_name, ta.area_no from t_area ta where 1=1 ";
		
		if(StringUtils.isNotEmpty(area_code)){
			sql = sql + " and ta.area_no='"+area_code+"' ";
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
	public Map<String, Object> queryCustomerStatBycity(String city_id,String curMonthFirst){
		String sql = "SELECT IFNULL(SUM(dcm.new_count), 0) AS cur_month_new_cus_count FROM ds_cusum_month dcm WHERE dcm.order_ym = '"+curMonthFirst+"' AND dcm.city_id="+city_id;
		
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
	public Map<String, Object> queryPayBasicStat(UserOperationStatDto userOperationStatDto){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
		
		String sql = "select";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " IFNULL(city_name,'"+userOperationStatDto.getCityName()+"') as city_name,";
		}
		sql = sql + " IFNULL(CONCAT(order_ym,''),"+userOperationStatDto.getBeginDate()+") as order_ym,IFNULL(sum(trading_price),0) as trading_price ,"
				+ "IFNULL(sum(customers),0) as customers from (select ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " city_name,";
		}
		sql = sql +  " order_ym,sum(trading_price) as trading_price,sum(customers) as customers from (select  dt.order_ym,";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " dt.city_name,";
		}		
		if("newpay".equals(userOperationStatDto.getPaytype())){
			sql = sql + "(case when order_month_count=1 then dt.trading_price end) trading_price , count(DISTINCT (case when order_month_count=1 then dt.customer_id end )) customers";	
		}else{
			sql = sql + "(case when order_month_count>=1 then dt.trading_price end) trading_price , count(DISTINCT (case when order_month_count>=1 then dt.customer_id end )) customers";	
		}
		sql = sql + " from df_customer_order_month_trade_new dt where dt.order_ym='" + userOperationStatDto.getBeginDate()+"' ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and dt.city_name='"+userOperationStatDto.getCityName()+"' group by city_name, order_ym,customer_id) a group by city_name,order_ym ) aa ";
		}else{
			sql = sql + " group by order_ym,customer_id) a group by order_ym ) aa ";
		}
		
		Session session1 = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery query = session1.createSQLQuery(sql);
			tempList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	        if(tempList!=null&&tempList.size()>0){
	        	result =  tempList.get(0);
		     }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session1.close();
		}
		
		String sqlCompare = "";
		if(StringUtils.isNotEmpty(userOperationStatDto.getDimension())){
			int demension = Integer.valueOf(userOperationStatDto.getDimension());
			String nextMonth = "";
			for (int i = 1; i <= demension; i++) {
				if(StringUtils.isNotEmpty(sqlCompare)){
					nextMonth = DateUtils.getNextMonthDate(nextMonth,"yyyyMM");
					sqlCompare = sqlCompare + " UNION ALL select ";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " IFNULL(city_name,'') as city_name,";
					}
					sqlCompare = sqlCompare + " IFNULL(CONCAT(order_ym,''),"+nextMonth+") as order_ym,IFNULL(sum(trading_price),0) as trading_price ,"
							+ "IFNULL(sum(customers),0) as customers from (select ";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " city_name,";
					}
					sqlCompare = sqlCompare + " order_ym,sum(trading_price) as trading_price,sum(customers) as customers from (select dt.order_ym,";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " dt.city_name,";
					}
					if("newpay".equals(userOperationStatDto.getPaytype())){
						sqlCompare = sqlCompare + "(case when order_month_count="+(i+1)+" then dt.trading_price end) trading_price , count(DISTINCT (case when order_month_count="+(i+1)+" then dt.customer_id end )) customers";
					}else{
						sqlCompare = sqlCompare + "(case when order_month_count>="+(i+1)+" then dt.trading_price end) trading_price , count(DISTINCT (case when order_month_count>="+(i+1)+" then dt.customer_id end )) customers";
					}
					sqlCompare = sqlCompare + " from df_customer_order_month_trade_new dt where dt.order_ym='"+nextMonth+"' ";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " and dt.city_name='"+userOperationStatDto.getCityName()+"' group by city_name, order_ym,customer_id) a"+i + " group by city_name,order_ym ) aa"+i;
					}else{
						sqlCompare = sqlCompare + " group by order_ym,customer_id) a"+i + " group by order_ym ) aa"+i;
					}
				}else{
					nextMonth = DateUtils.getNextMonthDate(userOperationStatDto.getBeginDate(),"yyyyMM");
					sqlCompare = sqlCompare + "select";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " IFNULL(city_name,'') as city_name,";
					}
					sqlCompare = sqlCompare + " IFNULL(CONCAT(order_ym,''),"+nextMonth+") as order_ym,IFNULL(sum(trading_price),0) as trading_price ,"
							+ "IFNULL(sum(customers),0) as customers from (select ";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " city_name,";
					}
					sqlCompare = sqlCompare + " order_ym,sum(trading_price) as trading_price,sum(customers) as customers from (select dt.order_ym,";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " dt.city_name,";
					}
					if("newpay".equals(userOperationStatDto.getPaytype())){
						sqlCompare = sqlCompare + "(case when order_month_count="+(i+1)+" then dt.trading_price end) trading_price ,count(DISTINCT (case when order_month_count="+(i+1)+" then dt.customer_id end )) customers";
					}else{
						sqlCompare = sqlCompare + "(case when order_month_count>="+(i+1)+" then dt.trading_price end) trading_price ,count(DISTINCT (case when order_month_count>="+(i+1)+" then dt.customer_id end )) customers";
					}
					sqlCompare = sqlCompare + " from df_customer_order_month_trade_new dt where dt.order_ym='"+nextMonth+"' ";
					if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
						sqlCompare = sqlCompare + " and dt.city_name='"+userOperationStatDto.getCityName()+"' group by city_name, order_ym,customer_id) a"+i + " group by city_name,order_ym ) aa"+i;
					}else{
						sqlCompare = sqlCompare + " group by order_ym,customer_id) a"+i + " group by order_ym ) aa"+i;
					}
				}
			}
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Session session2 = getHibernateTemplate().getSessionFactory().openSession();
		try {
			SQLQuery query = session2.createSQLQuery(sqlCompare);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			result.put("comparelist", list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session2.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryEffectCusStat(UserOperationStatDto userOperationStatDto,PageInfo pageInfo,String timeFlag){
		String sql = "SELECT a.store_city_name as city_name, a.store_name, IFNULL(a.area_code,'') AS area_code,count(DISTINCT customer_id) pay_count FROM ";
		
		if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and DATE_FORMAT(a.sign_time,'%Y-%m') = '" + userOperationStatDto.getBeginDate() + "'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		sql = sql + " and a.customer_id in  (select customer_id from ";
		if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		sql = sql + " where customer_id not like 'fakecustomer%' ";
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and DATE_FORMAT(sign_time,'%Y-%m') = '" + userOperationStatDto.getBeginDate() + "'";
		}
		sql = sql +  "group by customer_id HAVING count(1)>=2) GROUP BY a.store_id,a.area_code ";
		
		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";
		
		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + startData + "," + recordsPerPage;

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> exportEffectCusStat(UserOperationStatDto userOperationStatDto,String timeFlag){
		String sql = "SELECT a.store_city_name as city_name, a.store_name, IFNULL(a.area_code,'') AS area_code,count(DISTINCT customer_id) pay_count FROM ";
		
		if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		
		sql = sql + " where customer_id not like 'fakecustomer%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' and a.store_name NOT LIKE '%测试%' and a.store_white!='QA' AND a.store_status =0 ";
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and DATE_FORMAT(a.sign_time,'%Y-%m') = '" + userOperationStatDto.getBeginDate() + "'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + " and a.store_city_name like '%" + userOperationStatDto.getCityName().trim() + "%'";
		}
		if(StringUtils.isNotEmpty(userOperationStatDto.getStoreNo())){
			sql = sql + " and a.store_code ='" + userOperationStatDto.getStoreNo().trim()+ "'";
		}
		sql = sql + " and a.customer_id in  (select customer_id from ";
		if (MassOrderDto.TimeFlag.LATEST_MONTH.code.equals(timeFlag)) {
			sql = sql + " df_mass_order_monthly a ";
		} else {
			sql = sql + " df_mass_order_total a ";
		}
		sql = sql + " where customer_id not like 'fakecustomer%' ";
		if (StringUtils.isNotEmpty(userOperationStatDto.getBeginDate())) {
			sql = sql + " and DATE_FORMAT(sign_time,'%Y-%m') = '" + userOperationStatDto.getBeginDate() + "'";
		}
		sql = sql +  "group by customer_id HAVING count(1)>=2) GROUP BY a.store_id,a.area_code ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	public Map<String, Object> queryRegistCusStat(UserOperationStatDto userOperationStatDto, PageInfo pageInfo){

		String sql = "SELECT IFNULL(SUM(CASE WHEN strleft(tc.create_time,10)<='"+userOperationStatDto.getEndDate()+"' THEN 1 end),0) as total_cus, "
				+ "IFNULL(SUM(CASE WHEN strleft(tc.create_time,10)<='"+userOperationStatDto.getEndDate()+"' AND strleft(tc.create_time,10)>='"+userOperationStatDto.getBeginDate()+"' THEN 1 end),0) as new_cus, "
				+ "ifnull(min(td.cityno),'无') AS cityno,ifnull(min(td.cityname),'无') AS cityname FROM gemini.t_customer tc LEFT JOIN t_dist_citycode td ON LPAD(tc.city_code, 4, '0') = td.cityno where 1=1 ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + "and td.cityname like '%"+userOperationStatDto.getCityName()+"%' ";
		}
		sql = sql + " GROUP BY td.cityno order by td.cityno asc ";

		String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

		int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
		int recordsPerPage = pageInfo.getRecordsPerPage();
		sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

		String total = "0";
		List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
		if(resultCount !=null && resultCount.size()>0 ){
			total = String.valueOf(resultCount.get(0).get("total"));
		}

		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));
		Map<String, Object> map_result = new HashMap<String, Object>();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
	}

	public List<Map<String, Object>> exportRegistCusStat(UserOperationStatDto userOperationStatDto){
		String sql = "SELECT IFNULL(SUM(CASE WHEN strleft(tc.create_time,10)<='"+userOperationStatDto.getEndDate()+"' THEN 1 end),0) as total_cus, "
				+ "IFNULL(SUM(CASE WHEN strleft(tc.create_time,10)<='"+userOperationStatDto.getEndDate()+"' AND strleft(tc.create_time,10)>='"+userOperationStatDto.getBeginDate()+"' THEN 1 end),0) as new_cus, "
				+ "ifnull(min(td.cityno),'无') AS cityno,ifnull(min(td.cityname),'无') AS cityname FROM gemini.t_customer tc LEFT JOIN t_dist_citycode td ON LPAD(tc.city_code, 4, '0') = td.cityno where 1=1 ";
		if(StringUtils.isNotEmpty(userOperationStatDto.getCityName())){
			sql = sql + "and td.cityname like '%"+userOperationStatDto.getCityName()+"%' ";
		}
		sql = sql + " GROUP BY td.cityno order by td.cityno asc ";
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
}
