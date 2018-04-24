package com.cnpc.pms.worklog.dao;

import com.cnpc.pms.base.dao.IDAO;

public interface WorkLogLeaveDAO extends IDAO {
	public void updateWorkLogUserCalendar(String dates, Long userId);

	public void updateWorkLogUserCalendarBack(String dates, Long userId);
	//校验休假日期是否重复
	public String checkLeave(Long userId,String date1,String date2);
	/**
	 * 获取室主任所在室的Id,或者科研秘书,所办主任所在所得Id
	 */
	public Long getUserOrgId(Long userId);
}
