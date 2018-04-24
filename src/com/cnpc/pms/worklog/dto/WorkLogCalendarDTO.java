package com.cnpc.pms.worklog.dto;

import java.util.Date;
import com.cnpc.pms.base.dto.PMSDTO;

public class WorkLogCalendarDTO extends PMSDTO {
	private Long id;
	/**
	 * 日期
	 */
	private Date workday;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 是否节假日，0否，1是
	 */
	private Long isworkday;
	/**
	 * 星期几,"1"代表星期一,"2"代表星期二,"3"代表星期三,"4"代表星期四,"5"代表星期五,"6"代表星期六,"7"代表星期天
	 */
	private String days;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getIsworkday() {
		return isworkday;
	}

	public void setIsworkday(Long isworkday) {
		this.isworkday = isworkday;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	/**
	 * @param workday
	 *            the workday to set
	 */
	public void setWorkday(Date workday) {
		this.workday = workday;
	}

	/**
	 * @return the workday
	 */
	public Date getWorkday() {
		return workday;
	}

}
