/**
 * gaobaolei
 */
package com.cnpc.pms.platform.dao;

import java.util.List;
import java.util.Map;

/**
 * @author gaobaolei
 * 
 */
public interface PlatformEmployeeDao {
	
	/**
	 * 
	 * TODO 根据daqweb 员工编号查询gemini 的员工 
	 * 2018年3月7日
	 * @author gaobaolei
	 * @param employeeNo
	 * @return
	 */
	public List<Map<String, Object>> getEmployeeByEmployeeNo(String employeeNo);
	
	/**
	 * 
	 * TODO 根据gemini 的员工 id查询daqweb的员工编号
	 * 2018年3月7日
	 * @author gaoll
	 * @param employeeNo
	 * @return
	 */
	public List<Map<String, Object>> getEmployeeByEmployeeId(String employeeNo);
}
