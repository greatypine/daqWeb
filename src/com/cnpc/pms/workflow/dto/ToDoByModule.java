package com.cnpc.pms.workflow.dto;

import java.util.Date;

import com.cnpc.pms.base.dto.PMSDTO;

public class ToDoByModule extends PMSDTO {

	/*
	 * 一级业务模块名称
	 */
	private String moduleLevel1Name;

	/*
	 * 业务模块id
	 */
	private Long moduleId;

	/*
	 * 二级业务模块名称
	 */
	private String moduleLevel2Name;

	/*
	 * 待办条数
	 */
	private Integer count;

	/*
	 * 待办跳转路径
	 */
	private String moduleUrl;
	
	/*
	 * 待办/已办时间
	 */
	private Date dotime;
	
	/*
	 * 所占百分比
	 */
	private String percentage;

	public String getModuleLevel1Name() {
		return moduleLevel1Name;
	}

	public void setModuleLevel1Name(String moduleLevel1Name) {
		this.moduleLevel1Name = moduleLevel1Name;
	}

	public String getModuleLevel2Name() {
		return moduleLevel2Name;
	}

	public void setModuleLevel2Name(String moduleLevel2Name) {
		this.moduleLevel2Name = moduleLevel2Name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setDotime(Date dotime) {
		this.dotime = dotime;
	}

	public Date getDotime() {
		return dotime;
	}
}
