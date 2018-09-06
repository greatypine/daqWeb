package com.cnpc.pms.workflow.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;

/**
 * 流程实例
 * 
 * @author jrn
 * 
 */

public class WFFlowInstance extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2700802344703833478L;

	/**
	 * 流程Id
	 */
	@Column(name = "flowId")
	private Long flowId;

	/**
	 * 启动部门Id
	 */
	@Column(name = "orgId")
	private Long orgId;

	/**
	 * 启动人Id
	 */
	@Column(name = "personId")
	private Long personId;

	/**
	 * 启动时间
	 */
	@Column(name = "startDate")
	private Date startDate;

	/**
	 * 业务模块ID
	 */
	@Column(name = "moduleId")
	private Long moduleId;

	/**
	 * 业务表单id
	 */
	@Column(name = "sheetId")
	private Long sheetId;

	/**
	 * 业务表单名称
	 */
	@Column(name = "sheetName", length=500)
	private String sheetName;
	/**
	 * 项目计划编号
	 */
	@Column(name = "tableJhbh")
	private String tableJhbh;

	/**
	 * 上次提交时间
	 */
	@Column(name = "lastStepDate")
	private Date lastStepDate;
	/**
	 * 当前流程状态 0正常 1终止 2挂起
	 */
	@Column(name = "state")
	private Long state;

	/**
	 * 当前处理步骤,参照实例步骤表
	 */
	@Column(name = "currentStepId")
	private Long currentStepId;

	/**
	 * 待执行人ID
	 */
	@Column(name = "toDoOper")
	private Long toDoOper;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Date getLastStepDate() {
		return lastStepDate;
	}

	public void setLastStepDate(Date lastStepDate) {
		this.lastStepDate = lastStepDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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

	public Long getCurrentStepId() {
		return currentStepId;
	}

	public void setCurrentStepId(Long currentStepId) {
		this.currentStepId = currentStepId;
	}

	/**
	 * 配置与FlowInstanceVariable实体一对多关系
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowInstanceId")
	Set<WFInstanceVariable> varInstSet;

	/**
	 * 配置与StepInstance实体一对多关系
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowInstanceId")
	Set<WFStepInstance> stepInstSet;

	/**
	 * 配置与InstanceRecord实体一对多关系
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowInstanceId")
	Set<WFInstanceRecord> recordInstSet;

	/**
	 * 配置与ToDoMemo实体一对多关系
	 */
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "flowInstanceId")
	Set<WFToDoMemo> toDoInstMemoSet;

	public Set<WFInstanceVariable> getVarInstSet() {
		return varInstSet;
	}

	public void setVarInstSet(Set<WFInstanceVariable> varInstSet) {
		this.varInstSet = varInstSet;
	}

	public Set<WFStepInstance> getStepInstSet() {
		return stepInstSet;
	}

	public void setStepInstSet(Set<WFStepInstance> stepInstSet) {
		this.stepInstSet = stepInstSet;
	}

	public Set<WFInstanceRecord> getRecordInstSet() {
		return recordInstSet;
	}

	public void setRecordInstSet(Set<WFInstanceRecord> recordInstSet) {
		this.recordInstSet = recordInstSet;
	}

	public Set<WFToDoMemo> getToDoInstMemoSet() {
		return toDoInstMemoSet;
	}

	public void setToDoInstMemoSet(Set<WFToDoMemo> toDoInstMemoSet) {
		this.toDoInstMemoSet = toDoInstMemoSet;
	}

	public void setToDoOper(Long toDoOper) {
		this.toDoOper = toDoOper;
	}

	public Long getToDoOper() {
		return toDoOper;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param tableJhbh
	 *            the tableJhbh to set
	 */
	public void setTableJhbh(String tableJhbh) {
		this.tableJhbh = tableJhbh;
	}

	/**
	 * @return the tableJhbh
	 */
	public String getTableJhbh() {
		return tableJhbh;
	}

	/**
	 * 获取当前步骤
	 * @return
	 */
	public WFStepInstance getCurrentStep(){
		if(this.currentStepId==null){
			return null;
		}
		
		//从本地的Set里面查找对应的StepInstance
		WFStepInstanceManager manager1 = (WFStepInstanceManager) SpringHelper
		.getBean("WFStepInstanceManager");
		WFStepInstance step = manager1.readStepInstance(this.currentStepId);
		
		//工作流数据逻辑发生错误,需要系统管理员进行处理
		//这个很可能是程序内部的逻辑发生了错误
		//在设置CurrentStepId的时候出现了问题
		if(step==null){
			throw new WFException("工作流执行异常，FlowInstanceId="+this.getId());
		}
		return step;
	}
	
	/**
	 * 检索工作流当前步骤的类型
	 * @return
	 */
	public Long getCurrentStepType(){
		WFStepInstance step = getCurrentStep();
		if(step == null){
			return null;
		}else{
			return step.getStepType();
		}
	}
}
