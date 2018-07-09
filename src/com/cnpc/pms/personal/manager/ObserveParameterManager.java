package com.cnpc.pms.personal.manager;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.entity.ObserveParameter;

public interface ObserveParameterManager extends IManager{
	
	/**
	 * 保存明查台账信息
	 * 2018年06月27日
	 * @author gaoll
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return
	 */
	public Map<String, Object> saveObserveParameter(ObserveParameter param1,ObserveParameter param2,ObserveParameter param3);
	
	/**
	 * 查询明查台账信息
	 * 2018年06月27日
	 * @author gaoll
	 * @param conditions
	 * @return
	 */
	public Map<String,Object> queryObserveParameter(QueryConditions conditions);
	
	/**
	 * 按门店id,明查月份查询台账信息
	 * 2018年06月27日
	 * @author gaoll
	 * @param store_id
	 * @param observe_month
	 * @return
	 */
	public Map<String,Object> queryExitObserveParameter(String store_id,String observe_month);

	/**
	 * 按门店id,明查月份查询台账信息
	 * 2018年06月27日
	 * @author gaoll
	 * @param store_id
	 * @param observemonth
	 * @return
	 */
	List<?> getObserveParameterByStoreAndMonth(String storeid,String observemonth);
	
	/**
	 * 按城市查询明查台账汇总
	 * 2018年6月27
	 * @author gaoll
	 * @param cityname
	 * @return
	 */
	public Map<String,Object> queryObserveParameterSummaryByCity(String cityname);

	public Map<String,Object> exportObserveParamterSummary(String cityname);
	
	public Map<String,Object> exportObserveParamter(String cityname);
	

}
