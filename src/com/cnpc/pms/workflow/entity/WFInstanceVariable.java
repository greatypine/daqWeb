package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程实例参数,修改其表名为WF_InstanceVariable
 * 
 * @author jrn
 * 
 */

public class WFInstanceVariable extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -311342578195625789L;

	/**
	 * 对应流程实例Id
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;

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
	 * 参数数据类型 S-String/N-Number
	 */
	@Column(length = 50, name = "variableType")
	private String variableType;

	/**
	 * 默认值
	 */
	@Column(length = 50, name = "defaultValue")
	private String defaultValue;

	/**
	 * 设置值
	 */
	@Column(length = 50, name = "value")
	private String value;

	/**
	 * 删除标志
	 */
	@Column(name = "isDel")
	private Long isDel;

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setIsDel(Long isDel) {
		this.isDel = isDel;
	}

	public Long getIsDel() {
		return isDel;
	}

}
