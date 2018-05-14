package com.cnpc.pms.festival.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

/**
 * @Function：节日 518 数据大屏
 * @author：chenchuang
 * @date:2018年3月21日 下午1:36:55
 *
 * @version V1.0
 */
public interface FestivalStatManager extends IManager {

	/**
	 * E店销售额排名
	 */
	public List<Map<String, Object>> eshopRanking(String dateTime);
	
	/**
	 * 统计销售总额及消费用户数
	 */
	public Map<String, Object> queryTurnoverAndUser(String dateTime);
	
	/**
	 * 统计新用户数
	 */
	public Map<String, Object> queryNewCusUser(String dateTime);
	
	/**
	 * 城市营业额排名
	 */
	public List<Map<String, Object>> cityRanking(String dateTime);
	
	/**
	 * 分时走势营业额统计
	 */
	public List<Map<String, Object>> queryTurnoverByHour(String dateTime);
	
	/**
	 * 分时走势用户数统计
	 */
	public List<Map<String, Object>> queryUserByHour(String dateTime);
	
}
