package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;


public class WFCopyInfo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2137509367907881409L;
	/**
	 * 流程实例表Id
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;
	/**
	 * 发送人Id
	 */
	@Column(name = "sendId")
	private Long sendId;
	/**
	 * 接收人Id
	 */
	@Column(name = "receiverId")
	private Long receiverId;
	/**
	 * 消息内容
	 */
	@Column(name = "memo")
	private String memo;
	/**
	 * 业务模块Id
	 */
	@Column(name = "moduleId")
	private Long moduleId;
	/**
	 * 表单Id
	 */
	@Column(name = "sheetId")
	private Long sheetId;
	/**
	 * 消息状态1已读0未读
	 */
	@Column(name = "state")
	private Long state;

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Long getSendId() {
		return sendId;
	}

	public void setSendId(Long sendId) {
		this.sendId = sendId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
