/**
 * 
 */
package com.cnpc.pms.workflow.dto;

import java.util.Map;

import javax.persistence.Column;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 工作流流程抄送的时候调用的接口参数
 * 
 * @author jrn
 * 
 */
public class WFCopyParam extends PMSDTO {

	/**
	 * 流程实例表Id
	 */
	private Long flowInstanceId;
	/**
	 * 发送人Id
	 */
	private Long sendId;

	/**
	 * 模块Id
	 */
	private Long moduleId;

	/**
	 * 多个接收人的Id列表,逗号分割
	 */
	private String receiverIds;
	/**
	 * 多个接收人的Name列表,逗号分割
	 */
	private String receiverNames;

	private Long sheetId;

	private String sheetName;

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

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

	public String getReceiverIds() {
		return receiverIds;
	}

	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}

	public String getReceiverNames() {
		return receiverNames;
	}

	public void setReceiverNames(String receiverNames) {
		this.receiverNames = receiverNames;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
}
