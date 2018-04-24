package com.cnpc.pms.workflow.dto;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 获取当前用户所有可操作的Sheetid及对应URL列表的调用DTO对象<br>
 * 用于批量审批时过滤数据之用
 * 
 * @author liujunsong
 * 
 */
public class WFDoneParam extends PMSDTO {
	/**
	 * 当前用户Id
	 */
	private Long userId;
	/**
	 * 已办的类型,参见字典表WF_DONE_TYPE的定义0,1,2,3,4有效,-1也有效
	 */
	private Long doneType;
	/**
	 * 要调用的模块的Code编号
	 */
	private String moduleCode;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDoneType() {
		return doneType;
	}

	public void setDoneType(Long doneType) {
		this.doneType = doneType;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

}
