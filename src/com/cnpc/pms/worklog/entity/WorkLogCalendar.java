package com.cnpc.pms.worklog.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.PMSEntity;

/**
 * 工作日志部分使用的系统日志表,自动初始化10年的日期数据(2013-2022)
 * 这张表的ID号应该是连续的才可以。
 * @author liujunsong
 *
 */
@Entity
@Table(name = "TB_Worklog_Calendar")
public class WorkLogCalendar extends PMSEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6924057138768534001L;
	/**
	 * 日期
	 */
	@Column(name = "workday")
	private Date workday;
	/**
	 * 类型
	 */
	@Column(name = "type", length = 50)
	private String type;
	/**
	 * 备注
	 */
	@Column(name = "memo", length = 50)
	private String memo;
	/**
	 * 是否工作日，0否，1是
	 */
	@Column(name = "isworkday")
	private Long isworkday;
	/**
	 * 星期几,"1"代表星期一,"2"代表星期二,"3"代表星期三,"4"代表星期四,"5"代表星期五,"6"代表星期六,"7"代表星期天
	 */
	@Column(name = "days", length = 50)
	private String days;

	/**
	 * 本周开始日期
	 */
	private Date weekBegin;
	
	/**
	 * 本月开始日期
	 */
	private Date monthBegin;
	
	/**
	 * 本年开始日期
	 */
	private Date yearBegin;
	
	public Date getWeekBegin() {
		return weekBegin;
	}

	public void setWeekBegin(Date weekBegin) {
		this.weekBegin = weekBegin;
	}

	public Date getMonthBegin() {
		return monthBegin;
	}

	public void setMonthBegin(Date monthBegin) {
		this.monthBegin = monthBegin;
	}

	public Date getYearBegin() {
		return yearBegin;
	}

	public void setYearBegin(Date yearBegin) {
		this.yearBegin = yearBegin;
	}

	public Date getWorkday() {
		return workday;
	}

	public void setWorkday(Date workday) {
		this.workday = workday;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
