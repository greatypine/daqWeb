package com.cnpc.pms.worklog.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.worklog.dto.WorkLogUserCalendarDTO;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;

public interface WorkLogUserCalendarDAO extends IDAO {
	/**
	 * 批量更新用户日历的工作日状态(已废弃)
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean updateWorkLogUserCalendar(WorkLogUserCalendarDTO obj);

	/**
	 * 根据用户ID和日历日期获取到用户日历表里面相应的recordState的状态 调用者: WorkLogManager
	 * 
	 * @param obj
	 * @return
	 */
	public WorkLogUserCalendar getRecordState(Long userId, String logdate);

	/**
	 * 单一用户的用户日历的初始化方法,一次初始化一年.<br/>
	 * 初始化时仅依据系统日历表的日期类型定义<br/>
	 * 
	 * @param userId
	 *            用户Id
	 * @param logDate
	 *            日历日期,按年来执行初始化
	 */
	public void initUserCanlendar(Long userId, String logDate);
	
	/**
	 * 根据某一天是否是工作日，更新对应用户日历表<br>
	 * 调用者：由节假日维护定义功能来负责调用。WorkLogCalendarManager来调用.
	 * @param logDate 日历日期
	 */
	public void refreshAllWorkDayState(String logDate);
	
	/**
	 * 根据某一天是否是工作日，更新对应用户日历表<br>
	 * 调用者：由出差维护功能来负责调用。WorkLogLeaveManager来调用.
	 * @param userId 用户Id
	 * @param logDate 日历日期
	 */
	public void refreshOneWorkDayState(Long userId,String logDate);
	

}
