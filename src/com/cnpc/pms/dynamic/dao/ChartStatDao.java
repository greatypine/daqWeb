package com.cnpc.pms.dynamic.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.dynamic.entity.ChartStatDto;

public interface ChartStatDao extends IDAO{

	public Map<String, Object> queryDayTurnover(ChartStatDto csd); 
	
	public Map<String, Object> queryMonthTurnover(ChartStatDto csd);
	
	public Map<String, Object> queryOnlineOfflineTurnover(ChartStatDto csd);
	
	public List<Map<String, Object>> queryTurnoverByHour(ChartStatDto csd);
	
	public List<Map<String, Object>> queryTurnoverByDay(ChartStatDto csd);
	
	public List<Map<String, Object>> queryTurnoverByWeek(ChartStatDto csd);
	
	public List<Map<String, Object>> queryTurnoverByMonth(ChartStatDto csd);
	
	public List<Map<String, Object>> queryDataOfScatterplot(ChartStatDto csd);
	
	public Map<String, Object> queryDayUser(ChartStatDto csd);
	
	public List<Map<String, Object>> queryUserByHour(ChartStatDto csd);
	
	public List<Map<String, Object>> queryUserByDay(ChartStatDto csd);
	
	public List<Map<String, Object>> queryUserByWeek(ChartStatDto csd);
	
	public List<Map<String, Object>> queryUserByMonth(ChartStatDto csd);
	
	public Map<String, Object> queryOnlineOfflineUser(ChartStatDto csd);
	
	public List<Map<String, Object>> querUserOfScatterplot(ChartStatDto csd);
	
	public Map<String, Object> queryCurMonthUser(ChartStatDto csd);
	
}
