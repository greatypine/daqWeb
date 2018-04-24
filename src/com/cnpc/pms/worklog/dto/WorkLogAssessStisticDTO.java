package com.cnpc.pms.worklog.dto;

public class WorkLogAssessStisticDTO {
	private Long id;
	private String nums;
	private Long userId;
	private String userName;
	private String orgName;
	private Long orgId;
	private String posName;
	private Long posId;
	private Long realReport;
	private Long shouldReport;
	private String scoreA;
	private String scoreB;
	private String scoreC;
	private String scoreD;
	private String scoreF;
	private String isCompleted;
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
	public void setPosId(Long posId) {
		this.posId = posId;
	}
	public Long getPosId() {
		return posId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getOrgId() {
		return orgId;
	}
	
}
