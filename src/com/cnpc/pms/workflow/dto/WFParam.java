/**
 * 
 */
package com.cnpc.pms.workflow.dto;

import java.util.Map;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 启动流程或者提交流程时前后台交互参数
 * 
 * @author jrn
 * 
 */
public class WFParam extends PMSDTO {

	/**
	 * 流程模版ID
	 */
	private Long flowId;
	/**
	 * 流程实例ID
	 */
	private Long flowInstanceId;
	/**
	 * 流程启动部门
	 */
	/**
	 * 版本号
	 */
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private Long deptId;
	/**
	 * 实际操作人ID
	 */
	private Long operId;
	/**
	 * 待操作人ID
	 */
	private Long toOperId;
	/**
	 * 业务模块ID(调用时无效)
	 */
	private Long moduleId;
	/**
	 * 业务模块编号
	 */
	private String moduleCode;
	/**
	 * 操作表单ID
	 */
	private Long sheetId;
	/**
	 * 表单名称
	 */
	private String sheetName;
	/**
	 * 是否通过1 通过 0 拒绝,仅当opertype=0时有效
	 */
	private Long isPassed;
	/**
	 * 审批意见
	 */
	private String memo;
	/**
	 * 操作类型0正常审批1强制跳转2强制终止
	 */
	private Long operType;
	/**
	 * 强制跳转时选择的下一步操作节点Id,仅当操作类型operType=1时有效
	 */
	private Long nextStepId;
	/**
	 * 项目计划编号(应该已经废弃)
	 */
	private String tableJhbh;
	public Long getNextStepId() {
		return nextStepId;
	}

	public void setNextStepId(Long nextStepId) {
		this.nextStepId = nextStepId;
	}

	private Map<String, String> args;

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Long getToOperId() {
		return toOperId;
	}

	public void setToOperId(Long toOperId) {
		this.toOperId = toOperId;
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

	public Long getIsPassed() {
		return isPassed;
	}

	public void setIsPassed(Long isPassed) {
		this.isPassed = isPassed;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getOperType() {
		return operType;
	}

	public void setOperType(Long operType) {
		this.operType = operType;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param tableJhbh
	 *            the tableJhbh to set
	 */
	public void setTableJhbh(String tableJhbh) {
		this.tableJhbh = tableJhbh;
	}

	/**
	 * @return the tableJhbh
	 */
	public String getTableJhbh() {
		return tableJhbh;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleCode() {
		return moduleCode;
	}

}
