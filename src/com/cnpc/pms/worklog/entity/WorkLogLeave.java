package com.cnpc.pms.worklog.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;

/**
 * 休假Entity
 * 
 * @author ws
 * 
 */
@Entity
@Table(name = "tb_worklog_leave")

public class WorkLogLeave extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6419458904334005293L;

	/** 休假人 */
	
	@Column(name = "leaveUserId")
	private Long leaveUserId;
	
	@Column(name="leaveUserName",length=50)
	private String leaveUserName;
	
	
	/** 填写人 */
	@Column(name = "recordUserId")
	private Long recordUserId;
	
	@Column(name="recordUserName",length=50)
	private String recordUserName;

	/** 开始日期 ，必须*/
	@Column(name = "beginDate")
	private Date beginDate;

	/** 结束日期 ，必须*/
	@Column(name = "endDate")
	private Date endDate;

	/** 会议名称，外出开会时必须 */
	@Column(name = "meeting", length = 200)
	private String meeting;

	/** 休假类型，必须 */
	@Column(name = "leaveType")
	private String leaveType;

	/** 备注，非必须 */
	@Column(name = "remark", length = 500)
	private String remark;
	@Column(name = "historyDate")
	/** 历史日期，在销假时自动记录原来设定的出差休假结束日期 */
	private Date historyDate;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 销假状态 0：未销假；1： 已销假，必须
	 */
	@Column(name = "leaveState")
	private Integer leaveState;

	/** 状态：1：可用；0：不可用 */
	@Column(name = "enableStatus")
	private Integer enableStatus;
	@Column(name = "orgCode",length=50)
	private String orgCode;

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

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setHistoryDate(Date historyDate) {
		this.historyDate = historyDate;
	}

	public Date getHistoryDate() {
		return historyDate;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public Long getLeaveUserId() {
		return leaveUserId;
	}

	public void setLeaveUserId(Long leaveUserId) {
		this.leaveUserId = leaveUserId;
	}

	public String getLeaveUserName() {
		return leaveUserName;
	}

	public void setLeaveUserName(String leaveUserName) {
		this.leaveUserName = leaveUserName;
	}

	public Long getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(Long recordUserId) {
		this.recordUserId = recordUserId;
	}

	public String getRecordUserName() {
		return recordUserName;
	}

	public void setRecordUserName(String recordUserName) {
		this.recordUserName = recordUserName;
	}
	
}
