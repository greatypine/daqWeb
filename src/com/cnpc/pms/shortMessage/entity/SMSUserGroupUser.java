package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name="t_sms_usergroup_user")
public class SMSUserGroupUser extends  DataEntity{
	
	@Column(name="userName",length=20)
	private String userName;//用户名称
	
	@Column(name="userCode",length=20)
	private String userCode;//用户编号
	
	@Column(name="userGroupCode")
	private String userGroupCode;//用户组
	
	@Column(name="mobilePhone")
	private String mobilePhone;//电话

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	
	
	
}
