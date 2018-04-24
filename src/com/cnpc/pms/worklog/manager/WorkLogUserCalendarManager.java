package com.cnpc.pms.worklog.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.worklog.dto.WorkLogUserCalendarDTO;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;

public interface WorkLogUserCalendarManager extends IManager {
	/**
	 * 增加一条用户日历记录（无调用者）
	 * 
	 * @param obj
	 *            用户日历对象
	 */
	public void addWorkLogPersonCaleState(WorkLogUserCalendar obj);

	/**
	 * 按照Id来检索用户日历记录（无调用者）
	 * 
	 * @param id
	 * @return
	 */
	public WorkLogUserCalendar getWorkLogPersonCaleState(Long id);

	/**
	 * 保存用户日历记录（无调用者）
	 * 
	 * @param obj
	 * @return
	 */
	public WorkLogUserCalendar saveWorkLogPersonCaleState(
			WorkLogUserCalendar obj);

	/**
	 * 更新用户日历记录(无调用者)
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean updateWorkLogUserCalendar(WorkLogUserCalendarDTO obj);

	/**
	 * 获取日历的提交状态<br/>
	 * 根据用户Id(从seesion里面取)和日历日期获取到用户日历表里面相应的recordstate状态
	 * 
	 * @param logDate
	 *            日历日期,格式yyyy-mm-dd
	 * @return 当前用户的指定日期的提交状态
	 */
	public String getCurrentUserRecordState(String logDate);
	
	/**
	 * 根据用户Id和日历日期获取用户日历表中的RecordState状态
	 * @param userId
	 * @param logDate
	 * @return
	 */
	public String getUserRecordState(Long userId,String logDate);
	
	/**
	 * 初始化用户Id对应年份的用户日历表
	 * @param userId
	 * @param logDate
	 * @return
	 */
	public String initUserCanlendar(Long userId, String logDate);
	
	/**
	 * 初始化当前用户的对应年份的用户日历表
	 * @param logDate
	 * @return
	 */
	public String initCurrentUserCanlendar( String logDate);
}
