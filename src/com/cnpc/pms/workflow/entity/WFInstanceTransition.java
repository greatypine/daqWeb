package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 流程转换实例
 * 
 * @author jrn
 * 
 */
@Entity
@Table(name = "WF_InstanceTransition")

public class WFInstanceTransition extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4262760668401837369L;

	/**
	 * 开始节点,对应流程Intance步骤表
	 */
	@Column(name = "beginId")
	private Long beginId;

	/**
	 * 开始节点名称,对应流程步骤表,联动提取
	 */
	@Column(name="beginName",length=50)
	private String beginName;
	
	/**
	 * 转换序号,路径排序用
	 */
	@Column(name = "transitionNo")
	private Long transitionNo;

	/**
	 * 结束节点,对应流程Instance步骤表
	 */
	@Column(name = "endId")
	private Long endId;

	/**
	 * 结束节点名称
	 */
	@Column(name="endName",length=50)
	private String endName;
	
	public String getBeginName() {
		return beginName;
	}

	public void setBeginName(String beginName) {
		this.beginName = beginName;
	}

	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 是否默认转换,1是0否
	 */
	@Column(name = "isDefault")
	private Long isDefault;

	/**
	 * 条件表达式,节点转换的条件，当取默认转换时无效。DefaultValue=.
	 */
	@Column(length = 50, name = "condition")
	private String condition;

	/**
	 * 流程实例ID
	 */
	private Long flowInstanceId;

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Long getBeginId() {
		return beginId;
	}

	public void setBeginId(Long beginId) {
		this.beginId = beginId;
	}

	public Long getTransitionNo() {
		return transitionNo;
	}

	public void setTransitionNo(Long transitionNo) {
		this.transitionNo = transitionNo;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public Long getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Long isDefault) {
		this.isDefault = isDefault;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
