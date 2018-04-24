package com.cnpc.pms.worklog.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.entity.WorkLogCalendar;

/**
 * 
 * @author liujunsong
 *
 */
public interface WorkLogCalendarManager extends IManager {
	/**
	 * 增加一条系统日期
	 * @param obj
	 */
	public void addWorkLogCalendar(WorkLogCalendar obj);

	/**
	 * 按照Id获取日历对象
	 * <li>节假日设置功能使用
	 * @param id
	 * @return
	 */
	public WorkLogCalendar getWorkLogCalendar(Long id);

	/**
	 * 保存日历对象，系统节假日设置时使用，
	 * <li>保存日历对象时要同步修改对应的用户日历是否工作日的设置
	 * @param obj
	 */
	public void saveWorkLogCalendar(WorkLogCalendar obj);
	/**
	 * 初始化本年用户日历，初始化Tb_WorkLog_User_Calendar表
	 * @return
	 */
	public Boolean formatWorkLogCalendar();
	
	/**
	 * 初始化明年用户日历，初始化Tb_WorkLog_User_Calendar表
	 * @return
	 */
	public Boolean formatNextWorkLogCalendar();
	/**
	 * 新增用户时调用此接口，初始化改用户的用户日历表
	 * @return
	 */
	public Boolean formatPersonWorkLogCalendar(User user);

	/**
	 * 按照日期获取日历对象（节假日设置时使用)
	 * @param date
	 * @return
	 */
	public WorkLogCalendar getwWorkLogCalendarByDate(String date);

	/**
	 * 初始化10年的数据,2013-2022年，此方法在数据初始化时进行调用
	 * @return
	 */
	public Boolean updateFormatTenYears();
	/**
	 * 检测是否初始化日历
	 * @return
	 */
	public Boolean checkExistCalendar();
	public void updatRefreshAllWorkDayState(String logDate);
}
