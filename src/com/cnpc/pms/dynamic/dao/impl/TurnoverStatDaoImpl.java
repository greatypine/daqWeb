package com.cnpc.pms.dynamic.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.dao.TurnoverStatDao;
import com.cnpc.pms.dynamic.entity.TurnoverStatDto;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnoverStatDaoImpl extends BaseDAOHibernate implements TurnoverStatDao {

	@SuppressWarnings("unchecked")
	public Map<String, Object> queryStoreStat(TurnoverStatDto storeStatDto,PageInfo pageInfo){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, "
				+ "IFNULL(dround(SUM( CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN	 gmv_price ELSE 0 END ),2),0) AS gmv_price_profit, "
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price_profit,"
				+ "IFNULL(SUM( CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS order_num_profit,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS return_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "')THEN 1 ELSE 0 END ),0) AS return_num_profit FROM df_mass_order_total a,t_store ts  ";

		sql = sql + " where a.real_store_id = ts.id AND a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno='" + storeStatDto.getStoreNo().trim()+ "' ";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		
		sql = sql + " GROUP BY a.real_store_id ORDER BY a.real_store_id ";

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
	public List<Map<String, Object>> exportStoreStat(TurnoverStatDto storeStatDto){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, "
				+ "IFNULL(dround(SUM( CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN	 gmv_price ELSE 0 END ),2),0) AS gmv_price_profit, "
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price_profit,"
				+ "IFNULL(SUM( CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS order_num_profit,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS return_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "')THEN 1 ELSE 0 END ),0) AS return_num_profit FROM df_mass_order_total a,t_store ts  ";

		sql = sql + " where a.real_store_id = ts.id AND a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno='" + storeStatDto.getStoreNo().trim()+ "' ";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		
		sql = sql + " GROUP BY a.real_store_id ORDER BY a.real_store_id ";
		
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object>  queryAreaStat(TurnoverStatDto storeStatDto,PageInfo pageInfo){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,  IFNULL(min(ts.storeno),'') as area_code, IFNULL(min(a.info_employee_a_no),'') as employee_a_no, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "+ "THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM(CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price_profit,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(dround(SUM(CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price_profit,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "	+ "THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "' THEN 1 ELSE 0 END),0) AS order_num_profit,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "')THEN 1 ELSE 0 END),0) AS return_num_profit FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";
		
		sql = sql + " where area_code is not null AND a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";

		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getAreaNo())){
			sql = sql + " and a.area_code ='" + storeStatDto.getAreaNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.area_code ORDER BY a.real_store_id,a.area_code ";

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
	public List<Map<String, Object>> exportAreaStat(TurnoverStatDto storeStatDto){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,  IFNULL(min(a.area_code),'') as area_code, IFNULL(min(a.info_employee_a_no),'') as employee_a_no, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "+ "THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM(CASE WHEN (loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price_profit,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(dround(SUM(CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price_profit,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "	+ "THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN loan_label !='4' AND strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "' THEN 1 ELSE 0 END),0) AS order_num_profit,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' and loan_label !='4' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "')THEN 1 ELSE 0 END),0) AS return_num_profit FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where area_code is not null AND a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA'  ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getAreaNo())){
			sql = sql + " and a.area_code ='" + storeStatDto.getAreaNo().trim()+ "'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.area_code ORDER BY a.real_store_id,a.area_code ";
		
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDeptStat(TurnoverStatDto storeStatDto,PageInfo pageInfo){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.department_name),'') as department_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) "	+ "AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getDeptName())){
			sql = sql + " and a.department_name like '%" + storeStatDto.getDeptName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.bussiness_group_id ORDER BY a.real_store_id,a.bussiness_group_id ";

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
	public List<Map<String, Object>> exportDeptStat(TurnoverStatDto storeStatDto){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.department_name),'') as department_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END ),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) "	+ "AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END ),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getDeptName())){
			sql = sql + " and a.department_name like '%" + storeStatDto.getDeptName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.bussiness_group_id ORDER BY a.real_store_id,a.bussiness_group_id ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryChannelStat(TurnoverStatDto storeStatDto,PageInfo pageInfo){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.channel_name),'') as channel_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "+ "THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getChannelName())){
			sql = sql + " and a.channel_name like '%" + storeStatDto.getChannelName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.channel_id ORDER BY a.real_store_id,a.channel_id ";

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
	public List<Map<String, Object>> exportChannelStat(TurnoverStatDto storeStatDto){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.channel_name),'') as channel_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') "+ "THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getChannelName())){
			sql = sql + " and a.channel_name like '%" + storeStatDto.getChannelName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.channel_id ORDER BY a.real_store_id,a.channel_id ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryEshopStat(TurnoverStatDto storeStatDto,PageInfo pageInfo){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.eshop_name),'') as eshop_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getEshopName())){
			sql = sql + " and a.eshop_name like '%" + storeStatDto.getEshopName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.eshop_id ORDER BY a.real_store_id,a.eshop_id ";

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
	public List<Map<String, Object>> exportEshopStat(TurnoverStatDto storeStatDto){
		String sql = "SELECT min(a.store_city_name) AS city_name, min(ts.name) as store_name,	min(ts.storeno) as store_code, IFNULL(min(a.eshop_name),'') as eshop_name, "
				+ "IFNULL(dround(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN gmv_price ELSE 0 END),2),0) AS gmv_price,"
				+ "IFNULL(dround(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN returned_amount ELSE 0 END),2),0) AS return_price,"
				+ "IFNULL(SUM(CASE WHEN (strleft(sign_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(sign_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS order_num,"
				+ "IFNULL(SUM( CASE WHEN (return_label='1' AND strleft(return_time,10) >= '" + storeStatDto.getBeginDate() + "' AND strleft(return_time,10) <= '" + storeStatDto.getEndDate() + "') THEN 1 ELSE 0 END),0) AS return_num FROM df_mass_order_total a "
				+ "left join t_store ts on a.real_store_id=ts.id ";

		sql = sql + " where a.store_white!='QA' AND a.store_status=0 AND a.store_name NOT LIKE '%测试%' and a.eshop_name NOT LIKE '%测试%' AND a.eshop_white!='QA' ";
		
		if(StringUtils.isNotEmpty(storeStatDto.getStoreNo())){
			sql = sql + " and ts.storeno ='" + storeStatDto.getStoreNo().trim()+ "'";
		}else{
			if(StringUtils.isNotEmpty(storeStatDto.getCityName())){
				sql = sql + " and a.store_city_name like '%" + storeStatDto.getCityName().trim() + "%'";
			}
		}
		if(StringUtils.isNotEmpty(storeStatDto.getEshopName())){
			sql = sql + " and a.eshop_name like '%" + storeStatDto.getEshopName().trim() + "%'";
		}
		
		sql = sql + " GROUP BY a.real_store_id,a.eshop_id ORDER BY a.real_store_id,a.eshop_id ";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryAreaByCode(String area_code) {
		String sql = "SELECT IFNULL(ta.`name`,'') AS area_name FROM t_area ta WHERE 1=1 ";

		if (StringUtils.isNotEmpty(area_code)) {
			sql = sql + " AND ta.area_no= '" + area_code + "'";
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
	public Map<String, Object> queryEmployeeByNO(String employee_no) {
		String sql = "SELECT IFNULL(th.`name`,'') AS employee_a_name, IFNULL(th.phone,'') as employee_a_phone FROM t_humanresources th WHERE 1 = 1 ";

		if (StringUtils.isNotEmpty(employee_no)) {
			sql = sql + " AND th.employee_no = '" + employee_no + "'";
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
	
}
