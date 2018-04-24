package com.cnpc.pms.worklog.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.worklog.entity.WorkLogCalendar;

public interface WorkLogCalendarDAO extends IDAO {

	public Boolean getYearExits(String Date, Long days);

	public WorkLogCalendar getWorkLogCalendar(String date);

	/**
	 * 初始化用户日历数据
	 * <li>id = userid * 10000 + calendar.id,保证id是不重复的
	 * <li>workdaystate 是否工作日
	 * <li>利用Tb_bizbase_user表和Tb_Worklog_Calendar两张表交叉来计算得到数据.
	 * @param d1 开始日期，格式yyyy-mm-dd
	 * @param d2 结束日期，格式yyyy-mm-dd
	 */
	public void formatCaleState(String d1, String d2);
	public void formatPersonCaleState(Long userId,String beginDate,String endDate);
	/**
	 * 检测是否初始化日历
	 */
	public Boolean checkExistCalendar();
}
