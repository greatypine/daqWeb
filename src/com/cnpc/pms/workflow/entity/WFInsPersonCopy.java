package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF_InsPersonCopy")

public class WFInsPersonCopy extends PMSAuditEntity {

	private static final long serialVersionUID = -5073171700785559412L;
	/**
	 * 实例步骤Id
	 */
	@Column(name = "flowInstanceStepsId")
	private Long flowInstanceStepsId;

	/**
	 * 人员Id
	 */
	@Column(name = "receiverId")
	private Long receiverId;
	
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

	public Long getFlowInstanceStepsId() {
		return flowInstanceStepsId;
	}

	public void setFlowInstanceStepsId(Long flowInstanceStepsId) {
		this.flowInstanceStepsId = flowInstanceStepsId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getIsSameOrg() {
		return isSameOrg;
	}

	public void setIsSameOrg(Long isSameOrg) {
		this.isSameOrg = isSameOrg;
	}
}
