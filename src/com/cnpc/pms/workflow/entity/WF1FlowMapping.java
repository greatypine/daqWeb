package com.cnpc.pms.workflow.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF1_FlowMapping")

/**
 * WF1_FlowMapping代表一期流程数据与二期的对应关系,手工维护
 */
public class WF1FlowMapping extends PMSAuditEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2137509367907881409L;

	/**
	 * 表名
	 */
	@Column(name = "rTableName")
	private String rTableName;

	/**
	 * 记录号
	 */
	@Column(name = "rId")
	private Long rId;
	/**
	 * 模块id
	 */
	@Column(name = "moduleId")
	private Long moduleId;

	/**
	 * sheetId
	 */
	@Column(name = "sheetId")
	private Long sheetId;

	/**
	 * sheetName
	 */
	@Column(name="sheetName")
	private String sheetName;


	/**
	 * 流程启动部门Id
	 */
	@Column(name = "deptId")
	private Long deptId;
	
	/**
	 * 流程实例号,只读显示
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;
	
	/**
	 * 执行结果提示信息(保留字段,待扩展用)
	 */
	@Column(name="memo")
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public String getrTableName() {
		return rTableName;
	}

	public void setrTableName(String rTableName) {
		this.rTableName = rTableName;
	}

	public Long getrId() {
		return rId;
	}

	public void setrId(Long rId) {
		this.rId = rId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
