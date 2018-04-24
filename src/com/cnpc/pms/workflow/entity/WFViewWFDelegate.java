/**
 * 
 */
package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

@Entity
@Table(name = "view_wf_delegate")

public class WFViewWFDelegate implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8872067956198621618L;
	@Id
	@Column(name = "id")
	private Long id;
	private Long owner;
	private String owenername;
	private Long agent;
	private String agentname;
	private Date begindate;
	private Date enddate;
	private Long isstop;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getOwenername() {
		return owenername;
	}

	public void setOwenername(String owenername) {
		this.owenername = owenername;
	}

	public Long getAgent() {
		return agent;
	}

	public void setAgent(Long agent) {
		this.agent = agent;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Long getIsstop() {
		return isstop;
	}

	public void setIsstop(Long isstop) {
		this.isstop = isstop;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
