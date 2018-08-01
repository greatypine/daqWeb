package com.cnpc.pms.communeMember.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.platform.entity.ChartMemberDto;

public interface ChartMemberDao extends IDAO{
	
	public List<Map<String, Object>> queryContainsStoreDistCityList();

	public Map<String, Object> queryDayTurnover(ChartMemberDto csd); 
	
	public List<Map<String, Object>> queryTurnoverByHour(ChartMemberDto csd);
	
	public List<Map<String, Object>> queryTurnoverByDay(ChartMemberDto csd);
	
	public List<Map<String, Object>> queryTurnoverByWeek(ChartMemberDto csd);
	
	public List<Map<String, Object>> queryTurnoverByMonth(ChartMemberDto csd);
	
	public List<Map<String, Object>> queryDataOfScatterplot(ChartMemberDto csd);
}
