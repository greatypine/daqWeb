package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "wf_module")


public class WFModule extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 586146968413854132L;
	/**
	 * 业务模块编码
	 */
	@Column(length = 50, name = "moduleCode")
	private String moduleCode;
	/**
	 * 业务模块名称
	 */
	@Column(length = 50, name = "name")
	private String name;

	/**
	 * 业务模块分类，参见数据字典DICT_WF_MODULE_TYPE
	 */
	@Column(length = 50, name = "moduleType")
	private String moduleType;

	/**
	 * 是否是流程类的模块1是0否
	 */
	@Column(name = "isWF")
	private Long isWF;

	/**
	 * 待办的查询访问URL.
	 */
	@Column(name = "urlStr")
	private String urlStr;

	/**
	 * 已办的查询用URL
	 */
	@Column(name = "urlStr2")
	private String urlStr2;
	
	/**
	 * 流程办结查询用的URL,这里只查询审批流程中审批通过的流程
	 */
	@Column(name="finishedUrl")
	private String finishedUrl;



	public String getFinishedUrl() {
		return finishedUrl;
	}

	public void setFinishedUrl(String finishedUrl) {
		this.finishedUrl = finishedUrl;
	}

	public String getUrlStr2() {
		return urlStr2;
	}

	public void setUrlStr2(String urlStr2) {
		this.urlStr2 = urlStr2;
	}

	public Long getIsWF() {
		return isWF;
	}

	public void setIsWF(Long isWF) {
		this.isWF = isWF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public String getUrlStr() {
		return urlStr;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
}
