package com.cnpc.pms.userBehavior.manager;

import com.cnpc.pms.base.manager.IManager;

import java.util.Map;


/**
 * @author shijunhui
 * 用户行为分析
 * 2018-09-25
 */
public interface UserBehavior extends IManager{

	/**
	 * //按时间查询总的APP 访问人数  购物车人数  下单人数  支付人数
	 * @return
	 */
	public Map<String, Object> selectUserBrhaviorList(String startTime,String endTime);
	/**
	 * 按城市分类查询分析
	 * @return
	 */
	public Map<String, Object> selectByCityList(String startTime,String endTime);
	/**
	 * 按频道分类查询分析
	 * @return
	 */
	public Map<String, Object> selectByChannel(String startTime,String endTime);
	/**
	 * 用户购物行为点击总次数分析
	 */
	public Map<String, Object> selectUserShoppingCart(String startTime,String endTime);
	/**
	 * 用户购物平均行为点击次数分析
	 */
	public Map<String, Object> selectUserShoppingCartAvg(String startTime,String endTime);

	/**
	 * 用户购物具体详情分类
	 * @return
	 */
	public Map<String, Object> selectUserShoppingCartDetails(String startTime,String endTime);
	

}