package com.cnpc.pms.personal.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.EmployeeMoreInfo;

public interface EmployeeMoreInfoManager  extends IManager {
	
	/**
	 * 
	 * TODO  查询国安侠当天位置，并且求距离
	 * 2018年7月9日
	 * @author gaoll
	 * @param list
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

}
