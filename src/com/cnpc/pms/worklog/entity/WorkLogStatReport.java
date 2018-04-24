package com.cnpc.pms.worklog.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.PMSEntity;

/**
 * 
 * 用户日志的统计表,日志统计时除了按日以外,均访问这张数据表<br>
 * 
 * @author liujunsong
 * 
 */
@Entity
@Table(name = "TB_WorkLog_StatReport")
public class WorkLogStatReport extends PMSEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426448545067271547L;
	
	/**
	 * 统计类型 1分院统计 2 所统计 3人员统计
	 */
	@Column(name="statType")
	private String statType;
	
	/**
	 * 统计的日期范围类型 1 周统计 2 月统计 3年统计
	 */
	@Column(name="statDateType")
	private String statDateType;
	
	/**
	 * 统计时使用的用户用工类型，
	 */
	@Column(name="jobType")
	private Long jobType;
	/**
	 * 统计使用的Id,可能是分院Id,所Id,人员Id之一
	 */
	@Column(name = "statId")
	private Long statId;
	
	/**
	 * 统计使用的Name
	 */
	@Column(name="statName")
	private String statName;
	
	/**
	 * 开始日期
	 */
	@Column(name = "beginDate")
	private Date beginDate;


	/**
	 * 应报份数
	 */
	@Column(name = "workDayState")
	private Long workDayState;

	/**
	 * 实报份数
	 */
	@Column(name = "recordState")
	private Long recordState;

	/**
	 * 及时上报份数
	 */
	@Column(name = "onTimeState")
	private Long onTimeState;
	
	/**
	 * 补报份数
	 */
	@Column(name="noOntimeState")
	private Long noOntimeState;

	/**
	 * 上报率
	 */
	@Column(name="upRate")
	private Double upRate;
	
	/**
	 * 及时率
	 */
	@Column(name="onTimeRate")
	private Double onTimeRate;
	
	/**
	 * 工作小时数，一天的工作小时数的合计
	 */
	@Column(name = "hours")
	private BigDecimal Hours;
	

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public Long getStatId() {
		return statId;
	}

	public void setStatId(Long statId) {
		this.statId = statId;
	}

	public String getStatDateType() {
		return statDateType;
	}

	public void setStatDateType(String statDateType) {
		this.statDateType = statDateType;
	}


	public Long getNoOntimeState() {
		return noOntimeState;
	}

	public void setNoOntimeState(Long noOntimeState) {
		this.noOntimeState = noOntimeState;
	}

	public Double getUpRate() {
		return upRate;
	}

	public void setUpRate(Double upRate) {
		this.upRate = upRate;
	}

	public Double getOnTimeRate() {
		return onTimeRate;
	}

	public void setOnTimeRate(Double onTimeRate) {
		this.onTimeRate = onTimeRate;
	}



	public Long getOnTimeState() {
		return onTimeState;
	}

	public void setOnTimeState(Long onTimeState) {
		this.onTimeState = onTimeState;
	}


	public Long getWorkDayState() {
		return workDayState;
	}

	public void setWorkDayState(Long workDayState) {
		this.workDayState = workDayState;
	}

	public Long getRecordState() {
		return recordState;
	}

	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public BigDecimal getHours() {
		return Hours;
	}

	public void setHours(BigDecimal hours) {
		Hours = hours;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getStatName() {
		return statName;
	}

}
