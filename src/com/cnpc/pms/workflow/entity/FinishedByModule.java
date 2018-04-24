package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

/**
 * @ClassName: FinishedByModule
 * @Description:TODO(办结事项)
 * @author zhaobinbin
 * @date 2013-12-3 下午04:13:26
 */
@Entity
@Table(name = "view_finishedbymodule")

public class FinishedByModule implements IEntity {
	private static final long serialVersionUID = -8049091322760607698L;

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
	/**
	 * 办结事项url
	 */
	@Column(name = "finishedUrl")
	private String finishedUrl;

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
	public String getFinishedUrl() {
		return finishedUrl;
	}

	public void setFinishedUrl(String finishedUrl) {
		this.finishedUrl = finishedUrl;
	}

}
