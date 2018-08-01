package com.cnpc.pms.platform.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.communeMember.dao.ChartMemberDao;
import com.cnpc.pms.platform.entity.ChartMemberDto;
import com.cnpc.pms.platform.manager.ChartMemManager;
import com.cnpc.pms.utils.DateUtils;

public class ChartMemManagerImpl extends BizBaseCommonManager implements ChartMemManager {

	@Override
	public List<Map<String, Object>> queryContainsStoreDistCityList(){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryContainsStoreDistCityList();
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryDayTurnover(ChartMemberDto csd) {
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		Map<String,Object> order_obj = chartMemberDao.queryDayTurnover(csd);
		return order_obj;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByHour(ChartMemberDto csd){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryTurnoverByHour(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByDay(ChartMemberDto csd){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryTurnoverByDay(csd);
    	return lst_data;
	}
	
	@Override
	public List<String> getDateByWeek(){
		return DateUtils.getMemDateByWeek();
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByWeek(ChartMemberDto csd){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryTurnoverByWeek(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByMonth(ChartMemberDto csd){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryTurnoverByMonth(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryDataOfScatterplot(ChartMemberDto csd){
		ChartMemberDao chartMemberDao = (ChartMemberDao)SpringHelper.getBean(ChartMemberDao.class.getName());
		List<Map<String, Object>> lst_data = chartMemberDao.queryDataOfScatterplot(csd);
    	return lst_data;
	}
	
	
}
