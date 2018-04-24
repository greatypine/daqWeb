package com.cnpc.pms.worklog.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;
@Entity
@Table(name = "view_worklog_assessPerStatis")

public class WorkLogViewAssessPersonStatis implements IEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9050016437884054224L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "personid")
	private Long personid;
	@Column(name = "logdate")
	private Date logdate;
	@Column(name = "score",length=50)
	private String score;
	@Column(name = "hours")
	private BigDecimal hours;
	@Column(name = "worktype",length=50)
	private String worktype;
	@Column(name = "workresult",length=50)
	private String workresult;
	@Column(name = "workcontent",length=50)
	private String workcontent;
	@Column(name = "other",length=50)
	private String other;
	@Column(name = "support",length=50)
	private String support;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPersonid() {
		return personid;
	}
	public void setPersonid(Long personid) {
		this.personid = personid;
	}
	public Date getLogdate() {
		return logdate;
	}
	public void setLogdate(Date logdate) {
		this.logdate = logdate;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public BigDecimal getHours() {
		return hours;
	}
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getWorkresult() {
		return workresult;
	}
	public void setWorkresult(String workresult) {
		this.workresult = workresult;
	}
	public String getWorkcontent() {
		return workcontent;
	}
	public void setWorkcontent(String workcontent) {
		this.workcontent = workcontent;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}

}
