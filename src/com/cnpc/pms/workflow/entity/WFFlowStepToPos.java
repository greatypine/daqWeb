package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 对应于某一步骤的岗位定义
 * @author liujunsong
 *
 */

public class WFFlowStepToPos extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7191603869694323880L;
	/**
	 * 步骤Id
	 */
	@Column(name = "stepId")
	private Long stepId;
	/**
	 * 岗位Id
	 */
	@Column(name = "posId")
	private Long posId;

	@Column(length = 50, name = "posName")
	private String posName;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosName() {
		return posName;
	}

}
