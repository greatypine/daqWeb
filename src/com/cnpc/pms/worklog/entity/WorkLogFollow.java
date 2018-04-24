package com.cnpc.pms.worklog.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 关注Entity
 * 
 * @author CZQ
 * 
 */
@Entity
@Table(name = "tb_worklog_follow")

public class WorkLogFollow extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1865069518900425311L;

	/** 人员ID */
	@Column(name = "userId")
	private Long userId;
	
	/**
	 * 关注人Id
	 */
	@Column(name = "followId")
	private Long followId;
	
	/**
	 * 关注人名称
	 */
	@Column(name="followName")
	private String followName;
	/**
	 * 关注人职位
	 */
	@Column(name="posName")
	private String posName;
	/**
	 * 关注人所在科室
	 */
	@Column(name="orgName")
	private String orgName;
	/**
	 * 关注人所在所
	 */
	@Column(name="suoName")
	private String suoName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFollowId() {
		return followId;
	}

	public void setFollowId(Long followId) {
		this.followId = followId;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosName() {
		return posName;
	}
	
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setSuoName(String suoName) {
		this.suoName = suoName;
	}

	public String getSuoName() {
		return suoName;
	}

}