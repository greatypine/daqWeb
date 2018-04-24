package com.cnpc.pms.workflowdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF_SendInfoDemo")
public class WFSendInfoDemo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3099814971168501251L;
	/**
	 * Demo表单名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * Demo信息
	 */
	@Column(name = "infoMessage")
	private String infoMessage;
	/**
	 * 模块编号
	 */
	@Column(name = "moduleCode")
	private String moduleCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}
}
