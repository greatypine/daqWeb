package com.cnpc.pms.worklog.dto;

import java.util.Date;

import com.cnpc.pms.base.dto.PMSDTO;

public class WorkLogStaAndQueryDTO extends PMSDTO{
	private Long id;
	private Long currUserId;
	private Long userId;
	private Long operType;
	private Date logDate;
	private Long version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCurrUserId() {
		return currUserId;
	}
	public void setCurrUserId(Long currUserId) {
		this.currUserId = currUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOperType() {
		return operType;
	}
	public void setOperType(Long operType) {
		this.operType = operType;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	} 
}
