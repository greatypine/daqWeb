package com.cnpc.pms.worklog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;
@Entity
@Table(name = "view_worklog_assess")

public class WorkLogViewAssess implements IEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4492233660164522172L;
	@Id
	@Column(name = "id")
	private Long id;
	/**
	 * 用户名
	 */
	@Column(length=50,name="userName")
	private String userName;
	@Column(name="personid")
	private Long personid;
	/**
	 * 组织机构名
	 */
	@Column(length=50,name="name3")
	private String orgName;
	/**
	 * 职位
	 */
	@Column(length=50,name="posName")
	private String posName;
	/**
	 * 实报份数
	 */
	@Column(name="realReport")
	private Long realReport;
	/**
	 * 应报份数
	 */
	@Column(name="shouldReport")
	private Long shouldReport;
	/**
	 * scoreA
	 */
	@Column(length=50,name="scoreA")
	private String scoreA;
	/**
	 * scoreB
	 */
	@Column(length=50,name="scoreB")
	private String scoreB;
	/**
	 * scoreC
	 */
	@Column(length=50,name="scoreC")
	private String scoreC;
	/**
	 * scoreD
	 */
	@Column(length=50,name="scoreD")
	private String scoreD;
	/**
	 * scoreF
	 */
	@Column(length=50,name="scoreF")
	private String scoreF;
	/**
	 * 是否完成
	 */
	@Column(length=50,name="isCompleted")
	private String isCompleted;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getPosName() {
		return posName;
	}
	public void setPosName(String posName) {
		this.posName = posName;
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
	public String getScoreA() {
		return scoreA;
	}
	public void setScoreA(String scoreA) {
		this.scoreA = scoreA;
	}
	public String getScoreB() {
		return scoreB;
	}
	public void setScoreB(String scoreB) {
		this.scoreB = scoreB;
	}
	public String getScoreC() {
		return scoreC;
	}
	public void setScoreC(String scoreC) {
		this.scoreC = scoreC;
	}
	public String getScoreD() {
		return scoreD;
	}
	public void setScoreD(String scoreD) {
		this.scoreD = scoreD;
	}
	public String getScoreF() {
		return scoreF;
	}
	public void setScoreF(String scoreF) {
		this.scoreF = scoreF;
	}
	public String getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(String isCompleted) {
		this.isCompleted = isCompleted;
	}
	public void setPersonid(Long personid) {
		this.personid = personid;
	}
	public Long getPersonid() {
		return personid;
	}
	
}
