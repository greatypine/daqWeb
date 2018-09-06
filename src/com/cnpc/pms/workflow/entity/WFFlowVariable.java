package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程的参数定义表
 * @author liujunsong
 *
 */

public class WFFlowVariable extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5962231133768545042L;
	/**
	 * 对应流程id
	 */
	@Column(name = "flowId")
	private Long flowId;
	/**
	 * 参数代码
	 */
	@Column(length = 50, name = "variableCode")
	private String variableCode;
	/**
	 * 参数名称
	 */
	@Column(length = 50, name = "variableName")
	private String variableName;
	/**
	 * 参数数据类型
	 */
	@Column(length = 50, name = "variableType")
	private String variableType;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getVariableCode() {
		return variableCode;
	}

	public void setVariableCode(String variableCode) {
		this.variableCode = variableCode;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
