package com.cnpc.pms.worklog.dto;

import com.cnpc.pms.base.dto.PMSDTO;

public class WorkLogStatByBranchOrgDTO extends PMSDTO {

	/**
	 * 组织机构Id
	 */
	private Long id;
	/**
	 * 组织机构名称
	 */
	private String name;
	/**
	 * 上报份数
	 */
	private Long recordState;
	/**
	 * 补报份数
	 */
	private Long outtimeState;
	/**
	 * 应报份数(工作日状态)
	 */
	private Long workdayState;
	/**
	 * 上报率
	 */
	private Double commitRate;
	/**
	 * 用于以百分数显示上报率
	 */
	private String commitPercenter;
	/**
	 * 及时率
	 */
	private Double onTimeRate;
	/**
	 * 用于以百分数显示及时率
	 */
	private String onTimePercenter;
	public String getCommitPercenter() {
		return commitPercenter;
	}
	public void setCommitPercenter(String commitPercenter) {
		this.commitPercenter = commitPercenter;
	}
	public String getOnTimePercenter() {
		return onTimePercenter;
	}
	public void setOnTimePercenter(String onTimePercenter) {
		this.onTimePercenter = onTimePercenter;
	}
	/**
	 * 工作小时数
	 */
	private Double hours;
	
	/**
	 * 构造函数
	 */
	public WorkLogStatByBranchOrgDTO(){
		this.recordState = 0L;
		this.outtimeState = 0L;
		this.workdayState = 0L;
		this.hours = 0.0D;
	}
	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Long getRecordState() {
		return recordState;
	}

	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}

	public Long getOuttimeState() {
		return outtimeState;
	}

	public void setOuttimeState(Long outtimeState) {
		this.outtimeState = outtimeState;
	}

	public Long getWorkdayState() {
		return workdayState;
	}

	public void setWorkdayState(Long workdayState) {
		this.workdayState = workdayState;
	}

	public Double getCommitRate() {
		return commitRate;
	}

	public void setCommitRate(Double commitRate) {
		this.commitRate = commitRate;
	}

	public Double getOnTimeRate() {
		return onTimeRate;
	}

	public void setOnTimeRate(Double onTimeRate) {
		this.onTimeRate = onTimeRate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



}
