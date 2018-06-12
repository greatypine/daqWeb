package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 短信
 * @author gbl
 *
 */
@Entity
@Table(name="t_short_message")
public class ShortMessage extends DataEntity{
	
	@Column(name="title",length=20)
	private String  title;//标题
	
	@Column(name="content",length=200) 
	private String content;//内容
	
	@Column(name="code",length=20)
	private String code;//短信编号
	
	@Column(name="signature",length=10)
	private String signature;//签名
	
	@Column(name="userGroupId")
	private long userGroupId;//用户组ID
	
	@Column(name="userGroupCode")
	private String userGroupCode;//用户组Code
	
	@Column(name="checkStatus",length=2)
	private int checkStatus;//预留审批状态
   
	@Column(name="type",length=10)
	private String type;//短信类型(数据字典维护)
    
    
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
