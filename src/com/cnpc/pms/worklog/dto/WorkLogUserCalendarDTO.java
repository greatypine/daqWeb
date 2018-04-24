package com.cnpc.pms.worklog.dto;

import java.util.Date;

public class WorkLogUserCalendarDTO {
	private Long id;
	/**
	 * 人员Id
	 */

	private Long personId;
	/**
	 * 日历日期
	 */

	private Date calendarDate;
	/**
	 * 是否工作日：1是,0否
	 */

	private Long workDayState;
	/**
	 * 上报状态：1为填写了工作日誌(上报),0为没有填写工作日誌（未上报）
	 */
	private Long recordState;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}

	public Long getWorkDayState() {
		return workDayState;
	}

	public void setWorkDayState(Long workDayState) {
		this.workDayState = workDayState;
	}

	public Long getRecordState() {
		return recordState;
	}

	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}

}
