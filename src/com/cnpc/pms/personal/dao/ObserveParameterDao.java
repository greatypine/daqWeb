package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;

public interface ObserveParameterDao extends IDAO{
	
	/**
	 * 分页查询明查台账信息
	 * 2018年6月27
	 * @author gaoll
	 * @param append_str
	 * @param pageInfo
	 * @return
	 */
	public List<Map<String,Object>> queryObserveParameterList(String append_str, PageInfo pageInfo);
	
	/**
	 * 按门店id,明查月份查询台账信息
	 * 2018年6月27
	 * @author gaoll
	 * @param store_id
	 * @param observe_month
	 * @return
	 */
	public List<Map<String,Object>> queryExitObserveParameter(String store_id,String observe_month,String save_type,String cityname,String employeeId);
	
	
	/**
	 * 按城市查询明查台账总月份数
	 * 2018年6月27
	 * @author gaoll
	 * @param cityname
	 * @return
	 */
	public List<String> queryObserveMonthByCity(String cityname,String store_id,String observe_month,String employeeId);
	
	
	/**
	 * 按城市查询明查台账汇总
	 * 2018年6月27
	 * @author gaoll
	 * @param cityname
	 * @return
	 */
	public List<Map<String,Object>> queryObserveParameterSummaryByCity(String cityname,String store_id,String observe_month,String employeeId);

}
