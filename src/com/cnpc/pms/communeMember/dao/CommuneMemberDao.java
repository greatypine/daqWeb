
package com.cnpc.pms.communeMember.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;

/**
 * 社员操作dao
 * 
 * @author wuxinxin 2018年5月17日
 */
public interface CommuneMemberDao extends IDAO {

	/**
	 * TODO 社员商品成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmGoodsTurnover(String dd);

	/**
	 * TODO 获取男女比例
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmSexRatios(String dd);

	/**
	 * TODO 获取年龄段情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmAgeRatios(String dd);

	/**
	 * TODO 获取生日礼信息
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmBirthday(String dd);

	/**
	 * TODO 获取一段时间社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmAllGrow(String dd);

	/**
	 * TODO 获取一段时间新注册社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmNewGrow(String dd);

	/**
	 * TODO 获取一段时间老用户转社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmOldGrow(String dd);

	/**
	 * TODO 获取总社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getAllCount(String dd);

	/**
	 * TODO 获取老用户转社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getOldCount(String dd);

	/**
	 * TODO 获取新注册社员情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getNewCount(String dd);

	/**
	 * TODO 获取所有社员id
	 * 
	 * @author wuxinxin
	 */
	public String getAllCmIds(String dd);

	/**
	 * TODO 获取累计社员量
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getAllMembers(String dd);

	/**
	 * TODO 获取社员戶籍地址分布
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getMembersArea(String dd);

	/**
	 * TODO 获取社员量、成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmGoodsDealCount(String string);

	/**************************************************************
	 ******************** 第二版：社员注册信息统计*************************
	 **************************************************************/
	/**
	 * TODO 获取当月之前注册城市人数
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmRegistCity(String string);

	/**
	 * TODO 获取注册城市当月新增情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCmRegistMonthCity(String string);

	/**
	 * TODO 获取总注册城市情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getAllCmRegistCity(String string);

	/**
	 * TODO 获取最受欢迎商品
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getHotProduct(String string);

	/**
	 * TODO 获取无人问津商品
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCoolProduct(String string);

	/**
	 * TODO 获取动销数量
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getMovingPinCount(String string);
	/**
	 * TODO 获取e店前十销售情况
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getEshopSell(String string);
	/**
	 * TODO 获取e店社员、非社员总成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getAllEshopSum(String string);
	/**
	 * TODO  获取e店社员成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getYesEshopSum(String string);
	/**
	 * TODO  获取e店非社员成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getNoEshopSum(String string);
	/**
	 * TODO  获取当日订单量、成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getDayDealCount(String string);
	/**
	 * TODO  查询7日订单量、成交额
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getDay7DealCount(String string);
	/**
	 * TODO  查询当日注册社员数量
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getDayaddMemCount(String string);
   

}
