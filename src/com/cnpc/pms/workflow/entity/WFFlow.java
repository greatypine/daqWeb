package com.cnpc.pms.workflow.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;
import com.cnpc.pms.workflow.util.WFStepTypeConst;

/**
 * 对应流程定义表
 * 
 * @author liujunsong
 * 
 */
@Entity
@Table(name = "WF_Flow")

public class WFFlow extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1303402699476577843L;
	/**
	 * 对应最初版本流程Id
	 */
	@Column(name = "baseFlowId")
	private Long baseFlowId;
	/**
	 * 流程名称
	 */
	@Column(length = 50, name = "flowName")
	private String flowName;
	/**
	 * 流程版本号
	 */
	@Column(name = "flowVersion")
	private Long flowVersion;
	/**
	 * 创建人id
	 */
	@Column(name = "creator")
	private Long creator;
	/**
	 * 创建日期
	 */
	@Column(name = "createTime")
	private Date createTime;
	/**
	 * 版本说明
	 */
	@Column(length = 50, name = "memo")
	private String memo;
	/**
	 * 状态1可用 0不可用
	 */
	@Column(name = "state")
	private Long state;

	/**
	 * 业务模块ID
	 * 
	 * @return
	 */
	@Column(name = "moduleId")
	private Long moduleId;

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getBaseFlowId() {
		return baseFlowId;
	}

	public void setBaseFlowId(Long baseFlowId) {
		this.baseFlowId = baseFlowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Long getFlowVersion() {
		return flowVersion;
	}

	public void setFlowVersion(Long flowVersion) {
		this.flowVersion = flowVersion;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 设置到流程参数表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "FlowId")
	Set<WFFlowVariable> varSet;

	public Set<WFFlowVariable> getVarSet() {
		return varSet;
	}

	public void setVarSet(Set<WFFlowVariable> varSet) {
		this.varSet = varSet;
	}

	// 设置到流程转换表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "FlowId")
	Set<WFFlowTransition> transitionSet;

	public Set<WFFlowTransition> getTransitionSet() {
		return transitionSet;
	}

	public void setTransitionSet(Set<WFFlowTransition> transitionSet) {
		this.transitionSet = transitionSet;
	}

	// 设置到步骤信息表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "FlowId")
	Set<WFFlowStep> varStep;

	public Set<WFFlowStep> getVarStep() {
		return varStep;
	}

	public void setVarStep(Set<WFFlowStep> varStep) {
		this.varStep = varStep;
	}

	// 设置到流程部门对应表的一对多关系
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "FlowId")
	Set<WFFlowOrg> varFlowToOrg;

	public Set<WFFlowOrg> getVarFlowToOrg() {
		return varFlowToOrg;
	}

	public void setVarFlowToOrg(Set<WFFlowOrg> varFlowToOrg) {
		this.varFlowToOrg = varFlowToOrg;
	}

	// 计算节点中开始节点的数量
	// 用来判断流程定义的开始节点是否合法
	public Long getStartStepCount() {
		List<WFFlowStep> stepListTemp = new ArrayList<WFFlowStep>(this
				.getVarStep());
		if (stepListTemp == null || stepListTemp.size() == 0) {
			return 0L;
		}
		Long icount=0L;
		for (WFFlowStep ws : stepListTemp) {
			if (ws.getStepType().equals(WFStepTypeConst.BEGIN)) {
				icount++;
			}
		}
		return icount;
	}
	
	// 计算节点中结束节点的数量
	// 用来判断流程定义的开始节点是否合法
	public Long getEndStepCount() {
		List<WFFlowStep> stepListTemp = new ArrayList<WFFlowStep>(this
				.getVarStep());
		if (stepListTemp == null || stepListTemp.size() == 0) {
			return 0L;
		}
		Long icount=0L;
		for (WFFlowStep ws : stepListTemp) {
			if (ws.getStepType().equals(WFStepTypeConst.END)) {
				icount++;
			}
		}
		return icount;
	}
}
