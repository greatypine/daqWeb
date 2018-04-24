package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程步骤定义的步骤岗位对应
 * @author liujunsong
 *
 */
@Entity
@Table(name = "WF_FlowStepPositionCopy")

public class WFFlowStepPositionCopy extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -210121749654841871L;
	/**
	 * 流程步骤表Id
	 */
	@Column(name = "flowStepsId")
	private Long flowStepsId;
	/**
	 * 抄送岗位Id
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

	public Long getFlowStepsId() {
		return flowStepsId;
	}

	public void setFlowStepsId(Long flowStepsId) {
		this.flowStepsId = flowStepsId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
