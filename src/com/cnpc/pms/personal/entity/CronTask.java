package com.cnpc.pms.personal.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "ds_tasklist")
public class CronTask extends DataEntity {


	@Column(name = "tasksn")
	private String tasksn;

	@Column(name = "classify")
	private String classify;

	@Column(name = "description")
	private String description;

	@Column(name = "runtime")
	private String runtime;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "datatable")
	private String datatable;

	@Column(name = "taskmethod")
	private String taskmethod;

	@Transient
	private String caozuo;

	public String getTasksn() {
		return tasksn;
	}

	public void setTasksn(String tasksn) {
		this.tasksn = tasksn;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDatatable() {
		return datatable;
	}

	public void setDatatable(String datatable) {
		this.datatable = datatable;
	}

	public String getTaskmethod() {
		return taskmethod;
	}

	public void setTaskmethod(String taskmethod) {
		this.taskmethod = taskmethod;
	}
	
	public String getCaozuo() {
		return caozuo;
	}
	public void setCaozuo(String caozuo) {
		this.caozuo = caozuo;
	}	
}
