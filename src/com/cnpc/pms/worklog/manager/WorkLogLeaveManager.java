package com.cnpc.pms.worklog.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.worklog.dto.WorkLogLeaveDTO;

public interface WorkLogLeaveManager extends IManager {


	/**
	 * 新建一条出差休假记录
	 * <li>插入出差休假表,一条记录
	 * <li>插入日志表,多条记录,记录其类型为出差休假
	 * <li>更新用户日历表,多条记录,设置WorkDayState=0
	 */
	public String saveLeave(WorkLogLeaveDTO workLogLeaveDTO);

	/**
	 *  根据主键获取对象 
	 */
	public WorkLogLeaveDTO getLeaveById(Long id);

	/**
	 * 逻辑删除，休假的删除不采用逻辑删除，采用物理删除
	 * 删除以后要更新用户统计表
	 * (此功能无效,出差休假不可删除)
	 */
	public void deleteLeaveById(Long id);
	/**
	 * 采用物理删除
	 */
	public void deleteLeaveByIdReal(Long id);

	/**
	 *  修改状态为已销假
	 *  <li>仅操作出差休假表
	 *  
	 */
	public void changeSickLeave(Long id);

	/**
	 * 出差休假的销假功能,出差休假记录仅可以修改一次
	 * <li>更新出差休假表
	 * <li>将工作日志中原来的出差休假记录删除,插入新的出差休假记录
	 * <li>对应更新用户日历表中的工作日状态设置
	 * @param workLogLeaveDTO
	 */
	public void changeSickLeaveByObj(WorkLogLeaveDTO workLogLeaveDTO);
	public String checkLeave(WorkLogLeaveDTO workLogLeaveDTO);
	/**
	 * 获取室主任所在室的code,或者科研秘书,所办主任所在所得code
	 */
	public String getUserOrgCode();
	
	public void updateRefreshOneWorkDayState(String logDate);

}
