package com.cnpc.pms.worklog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 工作日志打分记录表
 * 
 * @author liujunsong
 * 
 */
@Entity
@Table(name = "tb_worklog_Assess")

public class WorkLogAssess extends PMSAuditEntity {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2448166467698619172L;

	// 第一部分,字段定义
	/** 操作人ID */
	@Column(name = "operId")
	private Long operId;

	/**
	 * 抽取日期(日志日期)
	 */
	@Column(name = "selectDate")
	private Date selectDate;

	/** 工作人员ID */
	@Column(name = "personId")
	private Long personId;

	/** 打分记录 */
	@Column(length = 1, name = "score")
	private String score;

	/** 打分状态1已打分0未打分 */
	@Column(length = 1, name = "state")
	private String state;

	// 第二部分,GetSet方法定义
	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Date getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
