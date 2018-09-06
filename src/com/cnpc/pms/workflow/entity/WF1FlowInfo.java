package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;


/**
 * WF1_FlowInfo是代表原来一期的流程执行记录，用以进行流程数据迁移使用。
 * 这张表的数据来自一期的流程执行记录
 */
public class WF1FlowInfo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2137509367907881409L;
	/**
	 * 一期Id号
	 */
	@Column(name = "flId")
	private Long flId;

	/**
	 * 表名
	 */
	@Column(name="rTableName")
	private String rTableName;

	/**
	 * 流程模版Id
	 */
	@Column(name="workFlowId")
	private Long workFlowId;
	
	/**
	 * 记录号
	 */
	@Column(name="rId")
	private Long rId;
	/**
	 * 当前步骤号
	 */
	@Column(name="stepId")
	private Long stepId;
	
	/**
	 * 上一步骤号
	 */
	@Column(name="preStepId")
	private Long preStepId;
	
	/**
	 * 步骤名称
	 */
	@Column(name="stepDes")
	private String stepDes;
	
	/**
	 * 来自人员
	 */
	@Column(name="fromEmpName")
	private String fromEmpName;
	
	/**
	 * 来源人员Id
	 */
	@Column(name="fromEmpId")
	private Long fromEmpId;
	
	/**
	 * 执行人员
	 */
	@Column(name="toEmpName")
	private String toEmpName;
	
	/**
	 * 执行人员Id
	 */
	@Column(name="toEmpId")
	private Long toEmpId;
	
	/**
	 * 创建日期
	 */
	@Column(name="createDate1")
	private Date createDate1;
	
	/**
	 * 执行日期
	 */
	@Column(name="checkDate")
	private Date checkDate;
	
	/**
	 * 意见
	 */
	@Column(name="optionDes")
	private String optionDes;
	
	/**
	 * 是否返回步骤
	 */
	@Column(name="isReturnStep")
	private Long isReturnStep;
	
	/**
	 * 是否完成
	 */
	@Column(name="isDone")
	private Long isDone;
	
	/**
	 * 是否通过
	 */
	@Column(name="isPass")
	private Long isPass;

	public Long getFlId() {
		return flId;
	}

	public void setFlId(Long flId) {
		this.flId = flId;
	}

	public String getrTableName() {
		return rTableName;
	}

	public void setrTableName(String rTableName) {
		this.rTableName = rTableName;
	}

	public Long getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(Long workFlowId) {
		this.workFlowId = workFlowId;
	}

	public Long getrId() {
		return rId;
	}

	public void setrId(Long rId) {
		this.rId = rId;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getPreStepId() {
		return preStepId;
	}

	public void setPreStepId(Long preStepId) {
		this.preStepId = preStepId;
	}

	public String getStepDes() {
		return stepDes;
	}

	public void setStepDes(String stepDes) {
		this.stepDes = stepDes;
	}

	public String getFromEmpName() {
		return fromEmpName;
	}

	public void setFromEmpName(String fromEmpName) {
		this.fromEmpName = fromEmpName;
	}

	public Long getFromEmpId() {
		return fromEmpId;
	}

	public void setFromEmpId(Long fromEmpId) {
		this.fromEmpId = fromEmpId;
	}

	public String getToEmpName() {
		return toEmpName;
	}

	public void setToEmpName(String toEmpName) {
		this.toEmpName = toEmpName;
	}

	public Long getToEmpId() {
		return toEmpId;
	}

	public void setToEmpId(Long toEmpId) {
		this.toEmpId = toEmpId;
	}

	public Date getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(Date createDate1) {
		this.createDate1 = createDate1;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getOptionDes() {
		return optionDes;
	}

	public void setOptionDes(String optionDes) {
		this.optionDes = optionDes;
	}

	public Long getIsReturnStep() {
		return isReturnStep;
	}

	public void setIsReturnStep(Long isReturnStep) {
		this.isReturnStep = isReturnStep;
	}

	public Long getIsDone() {
		return isDone;
	}

	public void setIsDone(Long isDone) {
		this.isDone = isDone;
	}

	public Long getIsPass() {
		return isPass;
	}

	public void setIsPass(Long isPass) {
		this.isPass = isPass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
