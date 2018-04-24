package com.cnpc.pms.workflow.entity;

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

/**
 * 流程步骤表
 * @author liujunsong
 *
 */
@Entity
@Table(name = "WF_FlowStep")

public class WFFlowStep extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2609743654201798134L;
	/**
	 * 流程Id
	 */
	@Column(name = "flowId")
	private Long flowId;
	/**
	 * 序号
	 */
	@Column(name = "stepNo")
	private Long stepNo;
	/**
	 * 步骤类型
	 */
	@Column(name = "stepType")
	private Long stepType;
	/**
	 * 步骤名称
	 */
	@Column(length = 50, name = "stepName")
	private String stepName;
	/**
	 * 是否本部门1Yes0No
	 */
	@Column(name = "isSameOrg")
	private Long isSameOrg;
	/**
	 * 是否可退回1Yes0No
	 */
	@Column(name = "isReturn")
	private Long isReturn;
	/**
	 * 是否可终止1Yes0No
	 */
	@Column(name = "isFinish")
	private Long isFinish;
	/**
	 * 是否可选择后续1Yes0No
	 */
	@Column(name = "isChoiseNext")
	private Long isChoiseNext;
	/**
	 * 是否可选择前面1Yes0No
	 */
	@Column(name = "isChoisePrev")
	private Long isChoisePrev;
	/**
	 * 表单是否要留痕1Yes0No
	 */

	@Column(name = "isAutoSave")
	private Long isAutoSave;
	/**
	 * 表单是否可编辑1Yes0No
	 */
	@Column(name = "isAllowEdit")
	private Long isAllowEdit;

	/**
	 * 是否催办1Yes0No
	 */
	@Column(name = "isPrompt")
	private Long isPrompt;

	/**
	 * 催办天数设置1Yes0No
	 */
	@Column(name = "promptDays")
	private Long promptDays;
	/**
	 * 外部java类
	 */
	@Column(length = 50, name = "remoteJava")
	private String remoteJava;
	/**
	 * 调用参数设置
	 */
	@Column(length = 50, name = "remoteArgs")
	private String remoteArgs;

	/**
	 * 可否自动审批
	 */
	@Column(length = 50, name = "isAutoPass")
	private Long isAutoPass;
	/**
	 * 自动审批天数（单位是天）
	 */
	@Column(length = 50, name = "autoPassDays")
	private Long autoPassDays;
	@Column(length = 50, name = "isNeedESign")
	private Long isNeedESign;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getStepNo() {
		return stepNo;
	}

	public void setStepNo(Long stepNo) {
		this.stepNo = stepNo;
	}

	public Long getStepType() {
		return stepType;
	}

	public void setStepType(Long stepType) {
		this.stepType = stepType;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public Long getIsSameOrg() {
		return isSameOrg;
	}

	public void setIsSameOrg(Long isSameOrg) {
		this.isSameOrg = isSameOrg;
	}

	public Long getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(Long isReturn) {
		this.isReturn = isReturn;
	}

	public Long getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Long isFinish) {
		this.isFinish = isFinish;
	}

	public Long getIsChoiseNext() {
		return isChoiseNext;
	}

	public void setIsChoiseNext(Long isChoiseNext) {
		this.isChoiseNext = isChoiseNext;
	}

	public Long getIsChoisePrev() {
		return isChoisePrev;
	}

	public void setIsChoisePrev(Long isChoisePrev) {
		this.isChoisePrev = isChoisePrev;
	}

	public Long getIsAutoSave() {
		return isAutoSave;
	}

	public void setIsAutoSave(Long isAutoSave) {
		this.isAutoSave = isAutoSave;
	}

	public Long getIsAllowEdit() {
		return isAllowEdit;
	}

	public void setIsAllowEdit(Long isAllowEdit) {
		this.isAllowEdit = isAllowEdit;
	}

	public Long getIsPrompt() {
		return isPrompt;
	}

	public void setIsPrompt(Long isPrompt) {
		this.isPrompt = isPrompt;
	}

	public Long getPromptDays() {
		return promptDays;
	}

	public void setPromptDays(Long promptDays) {
		this.promptDays = promptDays;
	}

	public String getRemoteJava() {
		return remoteJava;
	}

	public void setRemoteJava(String remoteJava) {
		this.remoteJava = remoteJava;
	}

	public String getRemoteArgs() {
		return remoteArgs;
	}

	public void setRemoteArgs(String remoteArgs) {
		this.remoteArgs = remoteArgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 设置到步骤岗位对应表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "stepId")
	Set<WFFlowStepToPos> varStepToPos;

	public Set<WFFlowStepToPos> getVarStepToPos() {
		return varStepToPos;
	}

	public void setVarStepToPos(Set<WFFlowStepToPos> varStepToPos) {
		this.varStepToPos = varStepToPos;
	}

	// 设置到流程转换表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "beginId")
	Set<WFFlowTransition> varFlowTransition;

	public Set<WFFlowTransition> getVarFlowTransition() {
		return varFlowTransition;
	}

	public void setVarFlowTransition(Set<WFFlowTransition> varFlowTransition) {
		this.varFlowTransition = varFlowTransition;
	}

	public void setAutoPassDays(Long autoPassDays) {
		this.autoPassDays = autoPassDays;
	}

	public Long getAutoPassDays() {
		return autoPassDays;
	}

	public void setIsAutoPass(Long isAutoPass) {
		this.isAutoPass = isAutoPass;
	}

	public Long getIsAutoPass() {
		return isAutoPass;
	}

	public void setIsNeedESign(Long isNeedESign) {
		this.isNeedESign = isNeedESign;
	}

	public Long getIsNeedESign() {
		return isNeedESign;
	}

}
