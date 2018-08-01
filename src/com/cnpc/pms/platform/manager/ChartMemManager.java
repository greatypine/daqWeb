package com.cnpc.pms.platform.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.DistCityCode;
import com.cnpc.pms.platform.entity.ChartMemberDto;

/**
 * @Function：K线图
 * @author：chenchuang
 * @date:2018年3月21日 下午1:36:55
 *
 * @version V1.0
 */
public interface ChartMemManager extends IManager {
	
	/**------------------GMV数据分析相关接口-------------------------------*/
	
	/**
	 * 查询包含社员的城市列表
	 */
	public List<Map<String, Object>> queryContainsStoreDistCityList();
	
	/**
	 * 实时查询当天的营业额
	 */
	public Map<String, Object> queryDayTurnover(ChartMemberDto csd); 
	
	/**
	 * 查询分时营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByHour(ChartMemberDto csd);
	
	/**
	 * 查询日营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByDay(ChartMemberDto csd);
	
	/**
	 * 获取当年的周，从周日开始
	 */
	public List<String> getDateByWeek();
	
	/**
	 * 查询周营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByWeek(ChartMemberDto csd);
	
	/**
	 * 查询月营业额K点
	 */
	public List<Map<String, Object>> queryTurnoverByMonth(ChartMemberDto csd);
	
	/**
	 * 查询GMV散点图
	 */
	public List<Map<String, Object>> queryDataOfScatterplot(ChartMemberDto csd);
	
	/**------------------用户数据分析相关接口-------------------------------*/
}
