package com.cnpc.pms.platform.manager;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.cnpc.pms.base.manager.IManager;

import ar.com.fdvs.dj.domain.constants.Page;


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
	
}