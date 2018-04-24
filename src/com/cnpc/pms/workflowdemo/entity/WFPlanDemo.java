package com.cnpc.pms.workflowdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF_PlanDemo")
public class WFPlanDemo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7680417816673759627L;
	/**
	 * 用于在前台记录本表单的Id
	 */
	@Column(name = "sheetId")
	private Long sheetId;
	/**
	 * 项目名称
	 */
	@Column(length = 50, name = "projectName")
	private String projectName;
	/**
	 * 项目来源
	 */
	@Column(length = 50, name = "projectSource")
	private String projectSource;
	/**
	 * 项目类型
	 */
	@Column(length = 50, name = "projectType")
	private String projectType;
	/**
	 * 项目经理
	 */
	@Column(length = 50, name = "projectManager")
	private String projectManager;
	/**
	 * 专业分类
	 */
	@Column(length = 50, name = "professionalType")
	private String professionalType;
	/**
	 * 课题名称
	 */
	@Column(length = 50, name = "topicTitle")
	private String topicTitle;
	/**
	 * 课题长
	 */
	@Column(length = 50, name = "topicLeader")
	private String topicLeader;
	/**
	 * 年度经费
	 */
	@Column(name = "annualfunding")
	private Long annualfunding;
	/**
	 * 总经费
	 */
	@Column(name = "totalfunding")
	private Long totalfunding;
	/**
	 * 参加单位
	 */
	@Column(length = 50, name = "partiUnits")
	private String partiUnits;
	/**
	 * 开始时间
	 */
	@Column(name = "startDate")
	private Date startDate;
	/**
	 * 结束时间
	 */
	@Column(name = "endDate")
	private Date endDate;
	/**
	 * 填报单位名称
	 */
	@Column(length = 50, name = "orgName")
	private String orgName;
	/**
	 * 隶属部门Id
	 */
	@Column(name = "deptId")
	private Long deptId;
	/**
	 * 业务木块Id
	 */
	@Column(length = 50, name = "moduleCode")
	private String moduleCode;
	/**
	 * 备注
	 */
	@Column(length = 50, name = "remark")
	private String remark;
	/**
	 * 填报人
	 */
	@Column(length = 50, name = "informant")
	private String informant;
	/**
	 * tbdw
	 */
	@Column(name = "tbdw")
	private Long tbdw;
	/**
	 * tjlx
	 */
	@Column(name = "tjlx")
	private Long tjlx;

	public Long getTbdw() {
		return tbdw;
	}

	public void setTbdw(Long tbdw) {
		this.tbdw = tbdw;
	}

	public Long getTjlx() {
		return tjlx;
	}

	public void setTjlx(Long tjlx) {
		this.tjlx = tjlx;
	}

	/**
	 * 可选启动流程ID(测试用)
	 */
	@Column(name = "flowId")
	private Long flowId;

	public Long getSheetId() {
		return sheetId;
	}

	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getProfessionalType() {
		return professionalType;
	}

	public void setProfessionalType(String professionalType) {
		this.professionalType = professionalType;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getTopicLeader() {
		return topicLeader;
	}

	public void setTopicLeader(String topicLeader) {
		this.topicLeader = topicLeader;
	}

	public Long getAnnualfunding() {
		return annualfunding;
	}

	public void setAnnualfunding(Long annualfunding) {
		this.annualfunding = annualfunding;
	}

	public Long getTotalfunding() {
		return totalfunding;
	}

	public void setTotalfunding(Long totalfunding) {
		this.totalfunding = totalfunding;
	}

	public String getPartiUnits() {
		return partiUnits;
	}

	public void setPartiUnits(String partiUnits) {
		this.partiUnits = partiUnits;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setInformant(String informant) {
		this.informant = informant;
	}

	public String getInformant() {
		return informant;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Long getFlowId() {
		return flowId;
	}

	/**
	 * @param moduleCode
	 *            the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

}
