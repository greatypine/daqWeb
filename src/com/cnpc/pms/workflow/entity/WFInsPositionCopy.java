package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF_InsPositionCopy")

public class WFInsPositionCopy extends PMSAuditEntity {

	private static final long serialVersionUID = 5620848061188972851L;

	/**
	 * 实例步骤Id
	 */
	@Column(name = "flowInstanceStepsId")
	private Long flowInstanceStepsId;

	/**
	 * 岗位Id
	 */
	@Column(name = "positionId")
	private Long positionId;

	/**
	 * 抄送岗位名称
	 */
	@Column(name = "positionName",length=100)
	private String positionName;
	
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * 是否限定本所,1限定，0不限定
	 */
	@Column(name = "isSameOrg")
	private Long isSameOrg;

	public Long getFlowInstanceStepsId() {
		return flowInstanceStepsId;
	}

	public void setFlowInstanceStepsId(Long flowInstanceStepsId) {
		this.flowInstanceStepsId = flowInstanceStepsId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getIsSameOrg() {
		return isSameOrg;
	}

	public void setIsSameOrg(Long isSameOrg) {
		this.isSameOrg = isSameOrg;
	}
}
