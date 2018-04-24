package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程步骤定义时的步骤人员抄送设置表
 * @author liujunsong
 *
 */
@Entity
@Table(name = "WF_FlowStepPersonCopy")

public class WFFlowStepPersonCopy extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2609620772416824812L;
	/**
	 * 流程步骤表Id
	 */
	@Column(name = "flowStepsId")
	private Long flowStepsId;
	/**
	 * 抄送人员Id
	 */
	@Column(name = "personId")
	private Long personId;
	/**
	 * 抄送人员姓名
	 */
	@Column(name = "personName",length=100)
	private String personName;
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * 是否限定本所,1限定，0不限定
	 */
	@Column(name = "isSameOrg")
	private Long isSameOrg;

	public Long getFlowStepsId() {
		return flowStepsId;
	}

	public void setFlowStepsId(Long flowStepsId) {
		this.flowStepsId = flowStepsId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getIsSameOrg() {
		return isSameOrg;
	}

	public void setIsSameOrg(Long isSameOrg) {
		this.isSameOrg = isSameOrg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
