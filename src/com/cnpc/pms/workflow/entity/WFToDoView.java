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
 * 工作流待办事项DTO
 * 
 * @author jrn
 * 
 */

// @Table(name = "WF_view_NEWToDoView")原来指向该视图，现在先用上面的测试，待以后删除

public class WFToDoView implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7548259149434703021L;
	@Id
	@Column(name = "id")
	private Long id;
	/**
	 * 用户ID
	 */
	@Column(name = "userId")
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 是否是同一部门
	 */
	private Long isSameOrg;
	/**
	 * 用户机构
	 */
	private Long userOrgId;
	/**
	 * 当前步骤ID
	 */

	@Column(name = "currentStepId")
	private Long currentStepId;

	private String currentStepName;
	/**
	 * 流程所在机构
	 */

	@Column(name = "orgId")
	private Long orgId;
	private String orgName;
	/**
	 * 上次提交时间
	 */
	@Column(name = "lastStepDate")
	private Date lastStepDate;

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
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 业务表单ID
	 */
	@Column(name = "sheetID")
	private Long sheetId;

	/**
	 * 业务表单名称
	 */
	@Column(name = "sheetName")
	private String sheetName;

	/**
	 * 处理岗位Id
	 */
	@Column(name = "posid")
	private Long posid;
	private String posName;

	private String flowInstanceId;

	/**
	 * 待办类型，0个人待办，1抄送待办
	 */
	@Column(name = "todotype")
	private Long toDoType;

	/**
	 * 待办类型名称，0个人待办，1抄送待办,2.委托待办, 3.业务待办 ,4.退回待办
	 */
	@Column(name = "todotypename")
	private String toDoTypeName;

	/**
	 * 发起人姓名(此字段已经废弃,实际程序中不再使用)
	 */
	@Column(name = "personName")
	private String personName;

	/**
	 * 是否打回，0否，1是
	 */
	@Column(name = "isBack")
	private String isBack;

	/**
	 * 业务模块访问url
	 */
	@Column(name = "moduleUrl")
	private String moduleUrl;
	@Column(length = 50, name = "owner")
	private String owner;
	@Column(name = "ownerId")
	private Long ownerId;
	
	/**
	 * 抄送消息专用,抄送消息Id
	 */
	@Column(name = "copyId")
	private Long copyId;

	public Long getCopyId() {
		return copyId;
	}

	public void setCopyId(Long copyId) {
		this.copyId = copyId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(String flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(Long currentStepId) {
		this.currentStepId = currentStepId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getLastStepDate() {
		return lastStepDate;
	}

	public void setLastStepDate(Date lastStepDate) {
		this.lastStepDate = lastStepDate;
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

	public Long getPosid() {
		return posid;
	}

	public void setPosid(Long posid) {
		this.posid = posid;
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

	public Long getIsSameOrg() {
		return isSameOrg;
	}

	public void setIsSameOrg(Long isSameOrg) {
		this.isSameOrg = isSameOrg;
	}

	public Long getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public String getCurrentStepName() {
		return currentStepName;
	}

	public void setCurrentStepName(String currentStepName) {
		this.currentStepName = currentStepName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setToDoType(Long toDoType) {
		this.toDoType = toDoType;
	}

	public Long getToDoType() {
		return toDoType;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setToDoTypeName(String toDoTypeName) {
		this.toDoTypeName = toDoTypeName;
	}

	public String getToDoTypeName() {
		return toDoTypeName;
	}
}
