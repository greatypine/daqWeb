package com.cnpc.pms.worklog.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 工作日志DTO
 * 
 * @author ws
 * 
 */
public class WorkLogDTO extends PMSDTO {

	/** 日志ID */
	private Long id;

	/** 休假ID */
	private Long leaveId;

	/** 人员ID */
	private Long userId;

	/** 日期 */
	private Date logDate;

	/** 工作日志类型 */
	private String workLogType;

	/** 工作/休假类型 */
	private String holidayType;

	/** 会议名称 */
	private String meeting;

	/** 工作项目 */
	private String workProject;
	/** 工作项目 */
	private String workProjectId;

	/** 小时数 */
	private BigDecimal hours;

	/** 工作内容 */
	private String workContent;

	/** 工作成果 */
	private String workResult;

	/** 其它学科 */
	private String other;

	/** 支持建议 */
	private String support;

	/** 是否销假 */
	private Integer leaveState;

	/**
	 * 抄送人ID 以";"分割
	 */
	private String toUserId;

	/** 抄送人Id以","分割 */
	private String toUserEmail;
	/** 抄送人名称以","分割 */
	private String toUserName;
	/** 抄送人code以","分割 */
	private String toUserCode;
	/** 日志填写日期*/
	private Date inputDate;

	/** 状态：1：可用；0：不可用 */
	private Integer enableStatus;
	private Long version; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getToUserEmail() {
		return toUserEmail;
	}

	public void setToUserEmail(String toUserEmail) {
		this.toUserEmail = toUserEmail;
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

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
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
