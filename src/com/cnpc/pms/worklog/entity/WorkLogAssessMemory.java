package com.cnpc.pms.worklog.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 工作日志打分机构记忆表,记录上次打分的选择机构信息
 * 
 * @author liujunsong
 * 
 */
@Entity
@Table(name = "tb_worklog_Assess_Memory")

public class WorkLogAssessMemory extends PMSAuditEntity {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2448166467698619172L;

	// 第一部分,字段定义
	/** 操作人ID */
	@Column(name = "operId")
	private Long operId;

	/**
	 * 上次选定的机构的Id列表,用逗号进行分割
	 */
	@Column(name="selectOrgIds",length=200)
	private String selectOrgIds;

	/**
	 * 上次选定的机构的名称列表,用逗号进行分割
	 */
	@Column(name="selectOrgNames",length=200)
	private String selectOrgNames;
	
	/**
	 * 上次选定的机构的代码Code的列表,用逗号进行分割
	 */
	@Column(name="selectOrgCodes",length=200)
	private String selectOrgCodes;
	
	public String getSelectOrgIds() {
		return selectOrgIds;
	}

	public void setSelectOrgIds(String selectOrgIds) {
		this.selectOrgIds = selectOrgIds;
	}

	public String getSelectOrgNames() {
		return selectOrgNames;
	}

	public void setSelectOrgNames(String selectOrgNames) {
		this.selectOrgNames = selectOrgNames;
	}

	public String getSelectOrgCodes() {
		return selectOrgCodes;
	}

	public void setSelectOrgCodes(String selectOrgCodes) {
		this.selectOrgCodes = selectOrgCodes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 第二部分,GetSet方法定义
	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}



}
