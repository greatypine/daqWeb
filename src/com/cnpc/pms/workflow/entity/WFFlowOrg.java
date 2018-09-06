package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程与可用所的对应关系表,如果是机关启动,则配置到指定机关来启动
 * @author liujunsong
 *
 */

public class WFFlowOrg extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 454339759843723007L;
	/**
	 * 流程id
	 */
	@Column(name = "flowId")
	private Long flowId;
	/**
	 * 部门id
	 */
	@Column(name = "orgId")
	private Long orgId;
	/**
	 * 部门名称
	 */
	@Column(length = 50, name = "orgName")
	private String orgName;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

}
