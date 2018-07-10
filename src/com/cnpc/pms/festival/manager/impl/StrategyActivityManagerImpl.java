package com.cnpc.pms.festival.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.dao.StrategyActivityDao;
import com.cnpc.pms.festival.manager.StrategyActivityManager;

public class StrategyActivityManagerImpl extends BizBaseCommonManager implements StrategyActivityManager {

	@Override
	public Map<String, Object> queryStrategyGMV(String dept_id,String start_time,String end_time) {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryStrategyGMV(dept_id,start_time,end_time);
    	return order_obj;
	}

	@Override
	public List<Map<String, Object>> queryGmvTrend(){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryGmvTrend();
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryNewMember() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryNewMember();
    	return order_obj;
	}
	
	@Override
	public Map<String, Object> queryTotalMember() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryTotalMember();
    	return order_obj;
	}

	@Override
	public List<Map<String, Object>> queryDataOfScatterplot() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryDataOfScatterplot();
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryDataOfPercent() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryDataOfPercent();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryStoreRanking(String dept_id){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryStoreRanking(dept_id);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryMemberTrend(){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryMemberTrend();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryActivityScope(){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryActivityScope();
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryProductRanking(String product_type,String store_no){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryProductRanking(product_type,store_no);
    	return lst_data;
	}
	
}
