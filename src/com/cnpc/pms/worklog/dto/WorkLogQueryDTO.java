package com.cnpc.pms.worklog.dto;

import com.cnpc.pms.base.dto.PMSDTO;

public class WorkLogQueryDTO extends PMSDTO{
	private Long id;
	/** 组织机构Id*/
	private Long orgId;
	/** 组织机构名称*/
	private String orgName;
	/** 人员Id*/
	private Long userId;
	/** 人员名称*/
	private String userName;
	/** 人员职称*/
	private String zc;
	/** 人员职务*/
	private String zw;
	/** 是否上报，1是，0否 */
	private Long  recordState;
	/** 是否关注，1是，0否 */
	private Long isFollow;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getZc() {
		return zc;
	}
	public void setZc(String zc) {
		this.zc = zc;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public Long getRecordState() {
		return recordState;
	}
	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}
	public Long getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(Long isFollow) {
		this.isFollow = isFollow;
	}

	
}
