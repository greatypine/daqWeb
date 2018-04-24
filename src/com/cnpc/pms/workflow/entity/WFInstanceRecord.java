package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程实例执行记录实体
 * 
 * @author jrn
 * 
 */
@Entity
@Table(name = "WF_InstanceRecord")

public class WFInstanceRecord extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1093231119880921578L;

	/**
	 * 流程实例Id
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;

	/**
	 * 操作类型1代办 0 非代办
	 */
	@Column(name = "operType")
	private Long operType;

	/**
     * 
     */
	@Column(name = "moduleId")
	private Long moduleId;

	/**
	 * 业务表单Id
	 */
	@Column(name = "sheetId")
	private Long sheetId;

	/**
	 * 操作序号,同一流程实例序号，从1开始
	 */
	@Column(name = "operNo")
	private Long operNo;

	/**
	 * 操作时间
	 */
	@Column(name = "operTime")
	private Date operTime;

	/**
	 * 待操作人
	 */
	@Column(name = "toOperId")
	private Long toOperId;

	/**
	 * 待操作人名称
	 */
	@Column(length = 50, name = "toOperName")
	private String toOperName;

	/**
	 * 实际操作人
	 */
	@Column(name = "operId")
	private Long operId;

	/**
	 * 操作人名称
	 */
	@Column(length = 50, name = "operName")
	private String operName;

	/**
	 * 操作描述
	 */
	@Column(length = 200, name = "operMemo")
	private String operMemo;

	/**
	 * 操作步骤Id
	 */
	@Column(name = "stepId")
	private Long stepId;

	/**
	 * 下一操作步骤Id
	 */
	@Column(name = "nextStepId")
	private Long nextStepId;

	/**
	 * 通过与否1Yes 0No
	 */
	@Column(name = "isPassed")
	private Long isPassed;

	/**
	 * 通过类型,0 未通过 1 正常通过 2 强制通过
	 */
	@Column(name = "passType")
	private Long passType;

	/**
	 * 不通过类型,0 通过 1 正常退回 2 强制退回
	 */
	@Column(name = "returnType")
	private Long returnType;

	/**
	 * 审批意见
	 */
	@Column(length = 2000, name = "memo")
	private String memo;

	/**
	 * 流程抄送人员,在流程抄送以后执行
	 */
	@Column(length = 200,name="copyPersonNames")
	private String copyPeronNames;
	
	public String getCopyPeronNames() {
		return copyPeronNames;
	}

	public void setCopyPeronNames(String copyPeronNames) {
		this.copyPeronNames = copyPeronNames;
	}

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Long getOperType() {
		return operType;
	}

	public void setOperType(Long operType) {
		this.operType = operType;
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

	public Long getOperNo() {
		return operNo;
	}

	public void setOperNo(Long operNo) {
		this.operNo = operNo;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Long getToOperId() {
		return toOperId;
	}

	public void setToOperId(Long toOperId) {
		this.toOperId = toOperId;
	}

	public String getToOperName() {
		return toOperName;
	}

	public void setToOperName(String toOperName) {
		this.toOperName = toOperName;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getNextStepId() {
		return nextStepId;
	}

	public void setNextStepId(Long nextStepId) {
		this.nextStepId = nextStepId;
	}

	public Long getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Long isPassed) {
		this.isPassed = isPassed;
	}

	public Long getPassType() {
		return passType;
	}

	public void setPassType(Long passType) {
		this.passType = passType;
	}

	public Long getReturnType() {
		return returnType;
	}

	public void setReturnType(Long returnType) {
		this.returnType = returnType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
