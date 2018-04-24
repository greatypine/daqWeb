package com.cnpc.pms.worklog.dto;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 新版本的用户查询的用户显示列表所用的用户DTO对象
 * @author liujunsong
 *
 */
public class WorkLogQueryUserDTO extends PMSDTO{
	
	private String orgName; //显示用机构名称,代表所名称
	private Long userId;	//显示用 用户Id,来自用户表
	private String userName; //显示用用户名称,来自用户表
	private String userZw; //用户的职务,来自用户表
	private String userZc; //用户的职称,来自用户表
	private Long recordState; //是否提交日志,来自用户日历表的统计1是 0否 null 否
	private String recordStateName; //显示
	private Long isFollow; //是否关注,来自用户日历表的统计1 是 0 否
	private String isFollowName; //是否关注名称
	
	public String getRecordStateName() {
		return recordStateName;
	}
	public void setRecordStateName(String recordStateName) {
		this.recordStateName = recordStateName;
	}
	public String getIsFollowName() {
		return isFollowName;
	}
	public void setIsFollowName(String isFollowName) {
		this.isFollowName = isFollowName;
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
	public String getUserZw() {
		return userZw;
	}
	public void setUserZw(String userZw) {
		this.userZw = userZw;
	}
	public String getUserZc() {
		return userZc;
	}
	public void setUserZc(String userZc) {
		this.userZc = userZc;
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
