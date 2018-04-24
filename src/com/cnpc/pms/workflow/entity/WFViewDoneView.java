/**
 * 
 */
package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

/**
 * 工作流已办事项DTO
 * 
 * @author jrn
 * 
 */

@Entity
@Table(name = "view_wf_doneview_alltype")

public class WFViewDoneView implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548259149434703021L;
	@Id
	@Column(name = "id")
	private Long id;

	/**
	 * 已办类型，0流程，1抄送，2委托，4代办，3，业务待办
	 */
	@Column(name = "type")
	private Long type;

	/**
	 * 流程实例Id
	 */
	@Column(name = "flowInstanceId")
	private String flowInstanceId;

	/**
	 * 用户ID
	 */
	@Column(name = "userId")
	private Long userId;
	/**
	 * 用户名称
	 */
	@Column(name = "userName")
	private String userName;

	/**
	 * 上次提交时间
	 */
	@Column(name = "operTime")
	private Date operTime;

	/**
	 * 业务表单ID
	 */
	@Column(name = "sheetId")
	private Long sheetId;

	/**
	 * 业务模块ID
	 */
	@Column(name = "moduleId")
	private Long moduleId;

	/**
	 * 业务模块Code
	 */
	@Column(name = "moduleCode")
	private String moduleCode;

	/**
	 * 业务模块名称
	 */
	@Column(name = "moduleName")
	private String moduleName;

	/**
	 * 查看的访问url
	 */
	@Column(name = "moduleUrl")
	private String moduleUrl;
	
	/**
	 * 流程的审批状态0执行中1完成
	 */
	@Column(name="state")
	private Long state;

	/**
	 * 流程的启动部门，如果是消息传送，则是null
	 */
	@Column(name="orgId")
	private Long orgId;
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(String flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
