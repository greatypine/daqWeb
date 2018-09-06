package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;


public class WFDelegate extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7581134506573702871L;
	/**
	 * 委托人
	 */
	@Column(name = "owner")
	private long owner;
	/**
	 * 代理人
	 */
	@Column(name = "agent")
	private long agent;
	/**
	 * 代理开始日期
	 */
	@Column(name = "beginDate")
	private Date beginDate;
	/**
	 * 代理结束日期
	 */
	@Column(name = "endDate")
	private Date endDate;
	/**
	 * 停用标志，0不停用，1停用
	 */
	@Column(name = "isStop")
	private long isStop;

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public long getAgent() {
		return agent;
	}

	public void setAgent(long agent) {
		this.agent = agent;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getIsStop() {
		return isStop;
	}

	public void setIsStop(long isStop) {
		this.isStop = isStop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
