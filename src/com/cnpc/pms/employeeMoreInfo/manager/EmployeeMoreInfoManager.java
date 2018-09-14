package com.cnpc.pms.employeeMoreInfo.manager;

import com.cnpc.pms.base.manager.IManager;

import java.util.List;
import java.util.Map;

public interface EmployeeMoreInfoManager extends IManager {
	
	/**
	 * 
	 * TODO  查询国安侠当天位置，并且求距离
	 * 2018年7月9日
	 * @author gaoll
	 * @return
	 */
	public void getEmployeePositionsDistance();
	
	/**
	 * 
	 * TODO  根据员工编号查询员工是否存在
	 * 2018年7月10日
	 * @author gaoll
	 * @param employeeNo
	 * @return
	 */
	public List<?> getEmployeeByEmployeeno(String employeeNo);
	
	/**
	 * 
	 * TODO  查询国安侠当天位置，并且求距离
	 * 2018年7月11日
	 * @author gaoll
	 * @param list
	 * @return
	 */
	public void getHistoryEmployeePositionsDistance();

	/**
	 * @Description  分析员工工龄
	 * @author gbl
	 * @date 2018/7/10 13:30
	 **/

	public void  analyzeEmployeeWorkingAge();


	/**
	 * @Description  查询国安侠总人数，及城市门店总数量
	 * @author gaoll
	 * @date 2018/9/11 11:30
	 **/
	public Map<String,Object> getEmployeeCount();

	/**
	 * @Description  查询国安侠人均基本信息，包含人均日工作时间
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgWorktime();

	/**
	 * @Description  查询国安侠人均基本信息，人均日单量
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgSendOrder();
	/**
	 * @Description  查询国安侠人均基本信息,人均里程数
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgMileAage();

	/**
	 * @Description  国安侠人均情况分析：人均日单量分布
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgSendOrderDistribution();

	/**
	 * @Description  国安侠人均情况分析：各人均月单量
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgSendOrderMonthTrend();

	/**
	 * @Description  国安侠人均情况分析：各城市人均单量
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getAvgSendOrderGroupByCity();

	/**
	 * @Description  门店国安侠编制状况分析：实时编制分布
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getEmpAvgAtAnalysis();

	/**
	 * @Description  门店国安侠编制状况分析：每月编制状况
	 * @author gaoll
	 * @date 2018/9/11 11:44
	 **/
	public Map<String,Object> getEmpAvgAtAnalysisMonth();
}
