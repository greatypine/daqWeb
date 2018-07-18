package com.cnpc.pms.dynamic.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;

public interface StrategyActivityDao extends IDAO{

	public Map<String, Object> queryStrategyGMV(String start_time,String end_time);
	
	public List<Map<String, Object>> queryUnStrategyGMV(String start_time,String end_time);
	
	public Map<String, Object> queryNewMember(String store_no,String start_time,String end_time);
	
	public Map<String, Object> queryTotalMember();
	
	public Map<String, Object> queryStoreUser(String store_no);
	
	public List<Map<String, Object>> queryStoreCompleteInfo(String store_no);

	public List<Map<String, Object>> queryDataOfScatterplot();
	
	public List<Map<String, Object>> queryYestodayGmvRanking(String dept_id,String order_by);
	
	public List<Map<String, Object>> queryYestodayMemberRanking(String order_by);
	
	public List<Map<String, Object>> queryStoreRanking(String dept_id);
	
	public List<Map<String, Object>> queryMemberTrend();
	
	public List<Map<String, Object>> queryActivityScope();
	
	public List<Map<String, Object>> queryProductRanking(String dept_id,String store_no);
	
	public List<Map<String, Object>> queryTypeGMV();
}
