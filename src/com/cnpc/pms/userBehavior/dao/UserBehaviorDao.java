
package com.cnpc.pms.userBehavior.dao;

import com.cnpc.pms.base.dao.IDAO;

import java.util.List;
import java.util.Map;

/**
 * 用户行为dao
 * 
 * @author shijunhui 2018年9月25日
 */
public interface UserBehaviorDao extends IDAO {

	/**
	 * TODO 查询用户行为
	 * 
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getUserBehaviorList(String startTime,String endTime);

	/**
	 * TODO 按频道查询
	 *
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getByChannelList(String startTime,String endTime);

	/**
	 * TODO 按城市查询
	 * 
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getByCityList(String startTime,String endTime);

	/**
	 * TODO 总次数分析
	 *
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getUserShoppingCartList(String startTime,String endTime);

	/**
	 * TODO 平均次数行为
	 *
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getUserShoppingCartAvg(String startTime,String endTime);
	/**
	 * TODO 购物分类详情
	 *
	 * @author shijunhui
	 */
	public List<Map<String, Object>> getUserShoppingCartDetails(String startTime,String endTime);

}
