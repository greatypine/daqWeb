package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.entity.Storexpand;

public interface StorexpandDao extends IDAO{
	/**
	 * 获取目标值录入分页列表
	 * @param where
	 * @param pageInfo
	 * @return
	 */
	List<Map<String, Object>> getStorexpandList(String where, PageInfo pageInfo);
	
	/**
	 * 某年签约数量，上会通过数据
	 * 2018年4月11
	 * @author gll
	 * @param year
	 * @return
	 */
	List<Map<String, Object>> getContractAndthroughByYear(String year);
	/**
	 * 近六周各城市签约数量
	 * 2018年4月11
	 * @author gll
	 * @return
	 */
	List<Map<String, Object>> getThroughByWeek();
	/**
	 * 查询年份合作店和自营店目标值
	 * @param cityname
	 * @return
	 */
	Map<String, Object> getTaskQuantityExist(String cityname);

	/**
	 * 查询是否录入过信息
	 * @param cityname
	 * @param statistics
	 * @return
	 */
	Map<String, Object> getStatisticsExist(String statistics,String cityname);
	
	
	Storexpand getStorexpandById(Long id);
}
