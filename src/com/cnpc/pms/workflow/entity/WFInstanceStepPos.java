package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 实例步骤岗位对应实体
 * 
 * @author jrn
 * 
 */
@Entity
@Table(name = "WF_InstanceStepToPos")

public class WFInstanceStepPos extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1594504054134224267L;
	/**
	 * 步骤Id,对应实例步骤表
	 */
	@Column(name = "stepId")
	private Long stepId;
	/**
	 * 岗位Id,对应岗位表
	 */
	@Column(name = "posId")
	private Long posId;

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

}
