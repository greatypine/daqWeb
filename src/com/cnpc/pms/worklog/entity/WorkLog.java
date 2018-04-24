package com.cnpc.pms.worklog.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 个人工作日志Entity
 * 
 * @author CZQ
 * 
 */
@Entity
@Table(name = "tb_worklog")

public class WorkLog extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865069518900425311L;

	/** 休假ID */
	@Column(name = "leaveId")
	private Long leaveId;

	/** 人员ID */
	@Column(name = "userId")
	private Long userId;

	/** 日期 ,必须输入 */
	@Column(name = "logDate", nullable = false)
	private Date logDate;

	/** 工作日志类型 ，必须选择,现已废弃 */
	@Column(name = "workLogType", length = 100)
	private String workLogType;

	/** 工作/休假类型 ，必须选择,现在仅使用工作日志类型 */
	@Column(name = "holidayType", length = 100)
	private String holidayType;

	/** 会议名称 ，当工作日志类型为“开会”时必须选择 */
	@Column(name = "meeting", length = 200)
	private String meeting;

	/** 工作项目，（非必须，100个汉字） */
	@Column(name = "workProject", length = 200)
	private String workProject;
	/** 工作项目Id，非必须 */
	@Column(name = "workProjectId", length = 100)
	private String workProjectId;

	/** 小时数，必须，一天之内小时数合计不得超过24小时 */
	@Column(name = "hours")
	private BigDecimal hours;

	/** 工作内容（500个汉字，必须） */
	@Column(name = "workContent", length = 1000)
	private String workContent;

	/** 工作成果（500个汉字，必须） */
	@Column(name = "workResult", length = 1000)
	private String workResult;

	/** 其它学科（500个汉字，非必须） */
	@Column(name = "other", length = 1000)
	private String other;

	/** 支持建议 （500个汉字，非必须） */
	@Column(name = "support", length = 1000)
	private String support;

	/**
	 * 是否销假 0：未销假；1：已销假,这一字段已经废弃
	 */
	@Column(name = "leaveState")
	private Integer leaveState;

	/**
	 * 状态：1：可用；0：不可用,这一字段已经废弃
	 */
	@Column(name = "enableStatus")
	private Integer enableStatus;

	/**
	 * 日志填写日期，必须项
	 */
	@Column(name = "inputDate")
	private Date inputDate;

	/** 日志抄送 ,存储一个以逗分割的抄送人的Id,这一字段已经废弃 */
	@Column(name = "toUserEmail")
	private String toUserEmail;
	/** 日志抄送 ,存储一个以逗号分割的抄送人的名称,这一字段已经废弃 */
	@Column(name = "toUserName")
	private String toUserName;
	/** 日志抄送 ,存储一个以逗号分割的抄送人的code,这一字段已经废弃 */
	@Column(name = "toUserCode")
	private String toUserCode;

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getWorkLogType() {
		return workLogType;
	}

	public void setWorkLogType(String workLogType) {
		this.workLogType = workLogType;
	}

	public String getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}

	public String getMeeting() {
		return meeting;
	}

	public void setMeeting(String meeting) {
		this.meeting = meeting;
	}

	public String getWorkProject() {
		return workProject;
	}

	public void setWorkProject(String workProject) {
		this.workProject = workProject;
	}

	public BigDecimal getHours() {
		return hours;
	}

	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getWorkResult() {
		return workResult;
	}

	public void setWorkResult(String workResult) {
		this.workResult = workResult;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public Integer getLeaveState() {
		return leaveState;
	}

	public void setLeaveState(Integer leaveState) {
		this.leaveState = leaveState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param enableStatus
	 *            the enableStatus to set
	 */
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}

	/**
	 * @return the enableStatus
	 */
	public Integer getEnableStatus() {
		return enableStatus;
	}

	public void setWorkProjectId(String workProjectId) {
		this.workProjectId = workProjectId;
	}

	public String getWorkProjectId() {
		return workProjectId;
	}

	public void setToUserEmail(String toUserEmail) {
		this.toUserEmail = toUserEmail;
	}

	public String getToUserEmail() {
		return toUserEmail;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserCode(String toUserCode) {
		this.toUserCode = toUserCode;
	}

	public String getToUserCode() {
		return toUserCode;
	}

}
