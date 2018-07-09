package com.cnpc.pms.festival.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

/**
 * @Function:221战略活动
 * @author:chenchuang
 * @date:2018年7月4日下午1:53:15  
 *
 * @version V1.0
 */
public interface StrategyActivityManager extends IManager{
	
	/**
	 * 统计221GMV/优易GMV/家务事GMV
	 */
	public Map<String, Object> queryStrategyGMV(String dept_id);
	
	/**
	 * 查询GMV走势
	 */
	public List<Map<String, Object>> queryGmvTrend();
	
	/**
	 * 统计221新增社员数
	 */
	public Map<String, Object> queryNewMember();
	
	/**
	 * 统计221总社员数
	 */
	public Map<String, Object> queryTotalMember();

	/**
	 * 查询221门店GMV散点图
	 */
	public List<Map<String, Object>> queryDataOfScatterplot();
	
	/**
	 * 查询商品类/服务类占比
	 */
	public List<Map<String, Object>> queryDataOfPercent();
	
	/**
	 * 查询优易、家务事、团购门店排名
	 */
	public List<Map<String, Object>> queryStoreRanking(String dept_id);
	
	/**
	 * 查询会员7日增长数
	 */
	public List<Map<String, Object>> queryMemberTrend();
	
	/**
	 * 查询221涵盖门店
	 */
	public List<Map<String, Object>> queryActivityScope();
	
	/**
	 * 查询商品类/服务类排名
	 */
	public List<Map<String, Object>> queryProductRanking(String product_type,String store_no);
}
