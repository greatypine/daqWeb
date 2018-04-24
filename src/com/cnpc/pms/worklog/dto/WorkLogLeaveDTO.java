package com.cnpc.pms.worklog.dto;

import java.util.Date;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 休假DTO
 * 
 * @author ws
 * 
 */
public class WorkLogLeaveDTO extends PMSDTO {

	private Long id;
	/** 休假人ID */
	private Long leaveUserId;

	/** 休假人名称 */
	private String leaveUserName;

	/** 填写人ID */
	private Long recordUserId;

	/** 填写人名称 */
	private String recordUserName;

	/** 开始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;
	/** 填写日期 */
	private Date inputDate;

	/** 会议名称 */
	private String meeting;

	/** 备注 */
	private String remark;

	/** 休假类型 */
	private String leaveType;

	/**
	 * 销假状态 0：未销假；1： 已销假
	 */
	private Integer leaveState;

	/** 状态：1：可用；0：不可用 */
	private Integer enableStatus;
	private Long version;
	/** 历史日期，在销假时自动记录原来设定的出差休假结束日期 */
	private Date historyDate;
	public Integer getLeaveState() {
		return leaveState;
	}

	public void setLeaveState(Integer leaveState) {
		this.leaveState = leaveState;
	}

	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLeaveUserId() {
		return leaveUserId;
	}

	public void setLeaveUserId(Long leaveUserId) {
		this.leaveUserId = leaveUserId;
	}

	public Long getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(Long recordUserId) {
		this.recordUserId = recordUserId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveUserName() {
		return leaveUserName;
	}

	public void setLeaveUserName(String leaveUserName) {
		this.leaveUserName = leaveUserName;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setHistoryDate(Date historyDate) {
		this.historyDate = historyDate;
	}

	public Date getHistoryDate() {
		return historyDate;
	}

}
