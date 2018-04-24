package com.cnpc.pms.worklog.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工作日志统计DTO
 * @author czq
 *
 */
public class WorkLogStatisticsDTO {
	private Long id;
	/**
	 * 序号
	 */
	private String nums;
	/**
	 * 机构/单位Id
	 */
	private Long orgId;
	/**
	 * 机构/单位名称
	 */
	private String orgName;
	/**
	 * 实报份数
	 */
	private Long realReport;
	/**
	 * 补报份数
	 */
	private Long delayReport;
	/**
	 * 应报份数
	 */
	private Long shouldReport;
	/**
	 * 上报率
	 */
	private String percentage;
	/**
	 * 及时率
	 */
	private String ontime;
	/**
	 * 用功类型
	 */
	private Integer jobType;
	public Long getDelayReport() {
		return delayReport;
	}
	public void setDelayReport(Long delayReport) {
		this.delayReport = delayReport;
	}
	public String getOntime() {
		return ontime;
	}
	public void setOntime(String ontime) {
		this.ontime = ontime;
	}
	/**
	 * 累计工作时间
	 */
	private  BigDecimal totalHours;
	/**
	 * 开始时间
	 */
	private Date beginDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getRealReport() {
		return realReport;
	}
	public void setRealReport(Long realReport) {
		this.realReport = realReport;
	}
	public Long getShouldReport() {
		return shouldReport;
	}
	public void setShouldReport(Long shouldReport) {
		this.shouldReport = shouldReport;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public BigDecimal getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(BigDecimal totalHours) {
		this.totalHours = totalHours;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setJobType(Integer jobType) {
		this.jobType = jobType;
	}
	public Integer getJobType() {
		return jobType;
	}

}
