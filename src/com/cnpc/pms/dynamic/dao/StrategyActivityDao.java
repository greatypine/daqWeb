package com.cnpc.pms.dynamic.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;

public interface StrategyActivityDao extends IDAO{

	public Map<String, Object> queryStrategyGMV(String dept_id);
	
	public List<Map<String, Object>> queryGmvTrend();
	
	public Map<String, Object> queryNewMember();
	
	public Map<String, Object> queryTotalMember();

	public List<Map<String, Object>> queryDataOfScatterplot();
	
	public List<Map<String, Object>> queryDataOfPercent();
	
	public List<Map<String, Object>> queryStoreRanking(String dept_id);
	
	public List<Map<String, Object>> queryMemberTrend();
	
	public List<Map<String, Object>> queryActivityScope();
	
	public List<Map<String, Object>> queryProductRanking(String product_type,String store_no);
}
