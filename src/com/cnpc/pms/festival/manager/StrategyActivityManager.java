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
	 * 统计221商品类/服务类/团购GMV
	 * @param dept_id:1商品类，2服务类，3团购类
	 */
	public Map<String, Object> queryStrategyGMV(String dept_id,String store_no,String start_time,String end_time);
	
	/**
	 * 统计非221GMV，按事业群分组
	 */
	public List<Map<String, Object>> queryUnStrategyGMV(String start_time,String end_time);
	
	/**
	 * 查询GMV走势
	 */
	public List<Map<String, Object>> queryGmvTrend();
	
	/**
	 * 统计221新增社员数
	 */
	public Map<String, Object> queryNewMember(String store_no,String start_time,String end_time);
	
	/**
	 * 统计221总社员数
	 */
	public Map<String, Object> queryTotalMember();
	
	/**
	 * 统计221单店完成情况
	 */
	public List<Map<String, Object>> queryStoreCompleteInfo(String store_no);

	/**
	 * 查询221门店GMV散点图
	 */
	public List<Map<String, Object>> queryDataOfScatterplot();
	
	/**
	 * 查询昨日门店GMV排名
	 * @param dept_id:1商品类，2服务类，3团购类
	 * @return
	 */
	public List<Map<String, Object>> queryYestodayGmvRanking(String dept_id,String order_by);
	
	/**
	 * 查询昨日门店社员排名
	 */
	public List<Map<String, Object>> queryYestodayMemberRanking(String order_by);
	
	/**
	 * 查询商品类/服务类/团购门店排名
	 * @param dept_id:1商品类，2服务类，3团购类
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
	 * @param dept_id:1商品类，2服务类，3团购类
	 */
	public List<Map<String, Object>> queryProductRanking(String dept_id,String store_no);
	
	/**
	 * 统计211分类GMV   1：商品    2.服务       3.团购
	 * 
	 */
	public Map<String, Object> queryTypeGmv();
}
