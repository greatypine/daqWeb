package com.cnpc.pms.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 更多员工信息
 * @author gbl
 *
 */
@Entity
@Table(name="t_employee_more_info")
public class EmployeeMoreInfo extends DataEntity{
	
	@Column(name="employeeNo")
	private String employeeNo;//员工编号
	
	@Column(name="workingAge_year")
	private Integer workingAge_year;//工龄 按年

	@Column(name="workingAge_year_precise")
	private String workingAge_year_precise;//工龄 例如 一年以上，两年以上

	@Column(name="workingAge_month")
	private Integer workingAge_month;//工龄 按月
	
	@Column(name="workingAge_day")
	private Integer workingAge_day;//工龄 按天
	
	@Column(name="moveDistance")
	private float moveDistance;//移动距离 /公里
	
	@Column(name="sendOrders")
	private Integer sendOrders;//送单量
	
	@Column(name="jobPost")
	private String jobPost;//工作岗位
	
	@Column(name="transferTime")
	private Date transferTime;//转岗时间
	
	@Column(name="storeNo")
	private String storeNo;//所在门店
	
	@Column(name="joinTime")
	private Date joinTime;//进店时间

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public Integer getWorkingAge_year() {
		return workingAge_year;
	}

	public void setWorkingAge_year(Integer workingAge_year) {
		this.workingAge_year = workingAge_year;
	}

	public Integer getWorkingAge_month() {
		return workingAge_month;
	}

	public void setWorkingAge_month(Integer workingAge_month) {
		this.workingAge_month = workingAge_month;
	}

	public Integer getWorkingAge_day() {
		return workingAge_day;
	}

	public void setWorkingAge_day(Integer workingAge_day) {
		this.workingAge_day = workingAge_day;
	}

	public float getMoveDistance() {
		return moveDistance;
	}

	public void setMoveDistance(float moveDistance) {
		this.moveDistance = moveDistance;
	}

	public Integer getSendOrders() {
		return sendOrders;
	}

	public void setSendOrders(Integer sendOrders) {
		this.sendOrders = sendOrders;
	}

	public String getJobPost() {
		return jobPost;
	}

	public void setJobPost(String jobPost) {
		this.jobPost = jobPost;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public String getWorkingAge_year_precise() {
		return workingAge_year_precise;
	}

	public void setWorkingAge_year_precise(String workingAge_year_precise) {
		this.workingAge_year_precise = workingAge_year_precise;
	}
}
