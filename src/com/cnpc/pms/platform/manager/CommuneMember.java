package com.cnpc.pms.platform.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.MemberDataDto;

import java.util.Map;


/**
 * @author wuxinxin
 * 社员相关操作
 * 2018-05-17
 */
public interface CommuneMember extends IManager{

	/**
	 * 查询社员属性信息
	 * @return
	 */
	public Map<String, Object> selectAllCm(String dd);
	/**
	 * 查询社员属性信息
	 * @return
	 */
	public Map<String, Object> selectMeSum(String dd);
	/**
	 * 查询社员订单信息
	 * @return
	 */
	public Map<String, Object> selectMeOrder(String dd);
	/**
	 * 按城市查询社员信息
	 */
	public Map<String, Object> selectMeCityOrder(String cityCode);
	/**
	 * 查询城市信息
	 */
	public Map<String, Object> getCityCode(String cityCode);
	
	/**
	 * 查询城市id
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCitys(String dd);
	/**
	 * 查询当日成交额
	 * @author wuxinxin
	 */
	public Map<String, Object> selectDayAllCm(String dd);
	/**
	 * 查询页面展示基本数据
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmBaseInfo(String dd);
	/**
	 * 查询社员增长情况
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmAddInfo(String dd);
	/**
	 * 客单价信息饼图
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmDealInfo(String dd);
	/**
	 * 社员7日成交信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmVolInfo(String dd);
	/**
	 * 社员属性信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmMemberInfo(String dd);
	/**
	 * 社员注册信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmRegistInfo(String dd);
	/**
	 * 社员注册信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectRebateInfo(String dd);
	/**
	 * 安心合作社订单信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectArtelOrderInfo(String dd);
	/**
	 * 安心合作社7大场景信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectArtelSceneInfo(String dd);
	/**
	 * 安心合作社完成订单/取消订单走势，订单均价走势
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectArtelOrderGmvInfo(String dd);
	/**
	 * 安心合作社城市成交额信息
	 * 
	 * @author wuxinxin
	 */
	public Map<String, Object> selectArtelCityGmvInfo(String dd);
	/**
	 * 安心合作社排行榜
	 *
	 * @author wuxinxin
	 */
	public Map<String, Object> selectArtelRankingInfo(String dd);
	/**
	 * 社员开卡信息
	 *
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmOpenInfo(String dd);
    /**
	 * 大客户社员统计
	 *
	 * @author wuxinxin
	 */
	public Map<String, Object> selectCmBigInfo(String dd);

	/**
	 * 社员档案查询列表
	 * @author chenchuang
	 */
	public Map<String, Object> queryMemberDataList(MemberDataDto memberDataDto, PageInfo pageInfo);

	/**
	 * 社员档案导出
	 * @author chenchuang
	 */
	public Map<String, Object> exportMemeData(MemberDataDto memberDataDto);

}