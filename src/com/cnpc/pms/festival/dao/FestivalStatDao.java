package com.cnpc.pms.festival.dao;

import java.util.List;
import java.util.Map;

public interface FestivalStatDao {

	/**
	 * E店销售额排名
	 */
	public List<Map<String, Object>> eshopRanking(String dateTime);
	
	/**
	 * 统计销售总额及消费用户数
	 */
	public Map<String, Object> queryTurnoverAndUser(String dateTime);
	
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
