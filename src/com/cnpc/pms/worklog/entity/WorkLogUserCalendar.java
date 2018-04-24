package com.cnpc.pms.worklog.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.PMSEntity;

/**
 * 这个Entity现在相当于用户日志的主表<br>
 * 公共信息存储在这个Entity里面<br>
 * 用户日志表是这张表的明细数据<br>
 * @author liujunsong
 *
 */
@Entity
@Table(name = "TB_WorkLog_User_Calendar")
public class WorkLogUserCalendar extends PMSEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426448545067271547L;
	/**
	 * 人员Id
	 */
	@Column(name = "personId")
	private Long personId;
	/**
	 * 日历日期
	 */
	@Column(name = "calendarDate")
	private Date calendarDate;
	/**
	 * 是否工作日：1是,0否
	 */
	@Column(name = "workDayState")
	private Long workDayState;
	
	/**
	 * 日志提交时间,如果未提交，则应当为null
	 */
	@Column(name="commitDate")
	private Date commitDate;
	
	/**
	 * 提交状态：1为提交了工作日誌,0或者null为没有提交工作日誌
	 */
	@Column(name = "recordState")
	private Long recordState;

	/**
	 * 是否及时上报 1是0否 null无意义
	 */
	@Column(name = "onTimeState")
	private Long onTimeState;
	
	/**
	 * 工作小时数，一天的工作小时数的合计
	 */
	@Column(name="workTime")
	private BigDecimal workTime;
	
	/**
	 * 选取的随机数,(已经废弃)
	 */
	@Column(name="selectRandom")
	private Long selectRandom;
	
	/**
	 * 人员是否可选,来自人员的选择表，用于做日志打分使用
	 */
	@Column(name="isSelect")
	private Long isSelect;
	
	//-------下面是3个和人员日志抄送相关的字段 -------------//
	/**
	 * 抄送人员Id列表
	 */
	@Column(name="toUserIds",length=200)
	private String toUserIds;
	
	/**
	 * 抄送人员名称列表
	 */
	@Column(name="toUserNames",length=200)
	private String toUserNames;
	/**
	 * 抄送人员Code列表
	 */
	@Column(name="toUserCodes",length=200)
	private String toUserCodes;
	
	public String getToUserIds() {
		return toUserIds;
	}

	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}

	public String getToUserNames() {
		return toUserNames;
	}

	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}

	public String getToUserCodes() {
		return toUserCodes;
	}

	public void setToUserCodes(String toUserCodes) {
		this.toUserCodes = toUserCodes;
	}

	public Long getSelectRandom() {
		return selectRandom;
	}

	public void setSelectRandom(Long selectRandom) {
		this.selectRandom = selectRandom;
	}

	public Long getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Long isSelect) {
		this.isSelect = isSelect;
	}

	public Long getOnTimeState() {
		return onTimeState;
	}

	public void setOnTimeState(Long onTimeState) {
		this.onTimeState = onTimeState;
	}

	public BigDecimal getWorkTime() {
		return workTime;
	}

	public void setWorkTime(BigDecimal workTime) {
		this.workTime = workTime;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
}
