package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;


public class WFSendInfo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764419897936521155L;
	/**
	 * 模块ID
	 */
	@Column(name = "moduleId")
	private Long moduleId;
	/**
	 * 下达实例挂载的表单名称
	 */
	@Column(name = "sheetName",length=500)
	private String sheetName;
	/**
	 * 下达实例挂载的表单ID
	 */
	@Column(name = "sheetId")
	private Long sheetId;
	/**
	 * 下达人ID
	 */
	@Column(name = "sendId")
	private Long sendId;
	/**
	 * 接收人ID
	 */
	@Column(name = "receiveId")
	private Long receiveId;
	/**
	 * 下达状态 0待处理 1已下达 2已结束
	 */
	@Column(name = "state")
	private Long state;
	/**
	 * 下达时间
	 */
	@Column(name = "sendTime")
	private Date sendTime;
	/**
	 * 处理时间
	 */
	@Column(name = "resolvedTime")
	private Date resolvedTime;
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public Long getSendId() {
		return sendId;
	}

	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}

	public Long getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getResolvedTime() {
		return resolvedTime;
	}

	public void setResolvedTime(Date resolvedTime) {
		this.resolvedTime = resolvedTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getModuleId() {
		return moduleId;
	}

}
