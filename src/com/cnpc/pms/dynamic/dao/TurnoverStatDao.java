package com.cnpc.pms.dynamic.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.TurnoverStatDto;

import java.util.List;
import java.util.Map;

public interface TurnoverStatDao extends IDAO{

	
	public Map<String, Object> queryStoreStat(TurnoverStatDto storeStatDto,PageInfo pageInfo);
	
	public List<Map<String, Object>> exportStoreStat(TurnoverStatDto storeStatDto);
	
	public Map<String, Object> queryAreaStat(TurnoverStatDto storeStatDto,PageInfo pageInfo);
	
	public List<Map<String, Object>> exportAreaStat(TurnoverStatDto storeStatDto);
	
	public Map<String, Object> queryDeptStat(TurnoverStatDto storeStatDto,PageInfo pageInfo);
	
	public List<Map<String, Object>> exportDeptStat(TurnoverStatDto storeStatDto);
	
	public Map<String, Object> queryChannelStat(TurnoverStatDto storeStatDto,PageInfo pageInfo);
	
	public List<Map<String, Object>> exportChannelStat(TurnoverStatDto storeStatDto);
	
	public Map<String, Object> queryEshopStat(TurnoverStatDto storeStatDto,PageInfo pageInfo);
	
	public List<Map<String, Object>> exportEshopStat(TurnoverStatDto storeStatDto);

	public Map<String, Object> queryAreaByCode(String area_code);
	
	public Map<String, Object> queryEmployeeByNO(String employee_no);
	
}
