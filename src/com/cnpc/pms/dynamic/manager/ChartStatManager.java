package com.cnpc.pms.dynamic.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.dynamic.entity.ChartStatDto;

import java.util.List;
import java.util.Map;

/**
 * @Function：K线图
 * @author：chenchuang
 * @date:2018年3月21日 下午1:36:55
 *
 * @version V1.0
 */
public interface ChartStatManager extends IManager {
	
	/**------------------GMV数据分析相关接口-------------------------------*/
	
	/**
	 * 查询包含门店的城市列表
	 */
	public List<Map<String, Object>> queryContainsStoreDistCityList();
	
	/**
	 * 查询所有的事业群
	 */
	public List<Map<String, Object>> queryAllDept();
	
	/**
	 * 查询所有的频道
	 */
	public List<Map<String, Object>> queryAllChannel();
	
	/**
	 * 根据事业群查询频道
	 */
	public List<Map<String, Object>> findChannelByDept(String deptId);
	
	/**
	 * 实时查询当天的营业额
	 */
	public Map<String, Object> queryDayTurnover(ChartStatDto csd); 
	
	/**
	 * 统计当月累计GMV
	 */
	public Map<String, Object> queryMonthTurnover(ChartStatDto csd);
	
	/**
	 * 统计线上线下占比
	 */
	public Map<String, Object> queryOnlineOfflineTurnover(ChartStatDto csd);
	
	/**
	 * 查询分时营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByHour(ChartStatDto csd);
	
	/**
	 * 查询日营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByDay(ChartStatDto csd);
	
	/**
	 * 获取当年的周，从周日开始
	 */
	public List<String> getDateByWeek();
	
	/**
	 * 查询周营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByWeek(ChartStatDto csd);
	
	/**
	 * 查询月营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByMonth(ChartStatDto csd);
	
	/**
	 * 查询营业额目标值K点
	 */
	public List<Map<String, Object>> queryTargetByMonth(ChartStatDto csd);
	
	/**
	 * 查询GMV散点图
	 */
	public List<Map<String, Object>> queryDataOfScatterplot(ChartStatDto csd);

	/**
	 * 数据导出
	 */
	public Map<String, Object> exportTurnover(ChartStatDto csd);

	/**------------------用户数据分析相关接口-------------------------------*/
	
	/**
	 * 查询当日累计用户数
	 */
	public Map<String, Object> queryDayUser(ChartStatDto csd);
	
	/**
	 * 查询分时用户量K点
	 */
	public List<Map<String, Object>> queryUserByHour(ChartStatDto csd);
	
	/**
	 * 查询日用户量K点
	 */
	public List<Map<String, Object>> queryUserByDay(ChartStatDto csd);
	
	/**
	 * 查询周用户量K点
	 */
	public List<Map<String, Object>> queryUserByWeek(ChartStatDto csd);
	
	/**
	 * 查询月用户量K点
	 */
	public List<Map<String, Object>> queryUserByMonth(ChartStatDto csd);
	
	/**
	 * 查询当月线上线下用户数
	 */
	public Map<String, Object> queryOnlineOfflineUser(ChartStatDto csd);
	
	/**
	 * 查询User散点图
	 */
	public List<Map<String, Object>> querUserOfScatterplot(ChartStatDto csd);
	
	/**
	 * 查询当月累计用户数
	 */
	public Map<String, Object> queryCurMonthUser(ChartStatDto csd);
}
