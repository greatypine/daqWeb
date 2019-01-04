package com.cnpc.pms.community.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.dynamic.entity.DynamicDto;

public interface CommunityMembersDao extends IDAO {
	/**
	 * 获取新注册社员(根据全国省市查询)-当月,社员总数
	 * author:zhangli
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> getNewMembersCount(DynamicDto dd,String flag);
	/**
	 * 获取近7日每日新开社员总数
	 * author:zhangli
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> getWeekMembersCount(DynamicDto dynamicDto);
	/**
	 * 获取近7日每日北京&上海&天津新开社员总数
	 * author:zhangli
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> getWeekOtherMembersCount(DynamicDto dynamicDto);
	/**
	 * 获取近7日每日的累计社员总数
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> getWeekTotalMembersCount(DynamicDto dynamicDto);
	/**
	 * 根据门店id获得该门店中事业群每月GMV
	 * @param storeId
	 * @return
	 */
	public List<Map<String, Object>> getDeptMonthDayGMV(String storeId);
	/**
	 * 查询221每周GMV
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> queryTwoTwoOneGMVByWeek(DynamicDto dynamicDto);
	/**
	 * 查询221每日GMV
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> queryTwoTwoOneGMVByDay(DynamicDto dynamicDto);
	/**
	 * 查询221每时GMV
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> queryTwoTwoOneGMVByHour(DynamicDto dynamicDto);
	/**
	 * 查询221门店散点图
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> queryDataOfScatterplot(DynamicDto dynamicDto);
	/**
	 * 查询221门店个数
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> queryTwoTwoOneStoreCount(DynamicDto dynamicDto);
}
