package com.cnpc.pms.platform.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.dynamic.entity.DynamicDto;
/**
 * @Function:总部首页
 * @author:zhangli
 */
public interface CommunityMembersManager extends IManager{
	/**
	 *查询全国/省/市的新增社员人数(当月),社员总数
	 * @param dynamicDto
	 * @return
	 */
	Map<String, Object> getNewMembersCount(DynamicDto dynamicDto);
	/**
	 * 查询全国/省/市的(新增社员人数/社员总数)(近7日)
	 * @param dynamicDto
	 * @return
	 */
	Map<String, Object> getMembersWeekCount(DynamicDto dynamicDto);
	/**
	 * 获取城市中门店当月每天GMV
	 * @param dynamicDto
	 * @return
	 */
	Map<String, Object> getDayGMVOfMonthForCityStore(DynamicDto dynamicDto);
	/**
	 * 通过storeId查询店事业群对应的GMV(每日)
	 * @param storeId
	 * @return
	 */
	Map<String, Object> getDepDayGMVOfMonthForStore(String storeId);
	/**
	 * 查询每周221GMV
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> queryTwoTwoOneGMVByWeek(DynamicDto dynamicDto);
	/**
	 * 查询每日221GMV
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> queryTwoTwoOneGMVByDay(DynamicDto dynamicDto);
	/**
	 * 查询每时221GMV
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> queryTwoTwoOneGMVByHour(DynamicDto dynamicDto);
	/**
	 * 查询门店221散点图
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> queryDataOfScatterplot(DynamicDto dynamicDto);
	/**
	 * 查询221门店个数
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> queryTwoTwoOneStoreCount(DynamicDto dynamicDto);
}
