package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

@Entity
@Table(name = "view_donebymodule")

public class DoneByModule implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -183284001833220077L;

	@Id
	@Column(name = "id")
	private Long id;
	
	/*
	 * 业务模块名称
	 */
	@Column(name = "moduleName")
	private String moduleName;

	/*
	 * 业务模块id
	 */
	@Column(name = "moduleId")
	private Long moduleId;

	/*
	 * 业务类型名称
	 */
	@Column(name = "moduleType")
	private String moduleType;

	/*
	 * 待办条数
	 */
	@Column(name = "docount")
	private Integer docount;

	/*
	 * 待办跳转路径
	 */
	@Column(name = "moduleUrl")
	private String moduleUrl;
	
	/*
	 * 待办/已办时间
	 */
	@Column(name = "dotime")
	private Date dotime;
	
	@Column(name = "userId")
	private Long userId;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public Integer getDocount() {
		return docount;
	}

	public void setDocount(Integer docount) {
		this.docount = docount;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Date getDotime() {
		return dotime;
	}

	public void setDotime(Date dotime) {
		this.dotime = dotime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}
}
