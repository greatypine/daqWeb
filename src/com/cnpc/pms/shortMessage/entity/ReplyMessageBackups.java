package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;
@Table(name="t_reply_message_backups")
@Entity
public class ReplyMessageBackups extends DataEntity{
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="msgContent")
	private String msgContent;
	
	@Column(name="spNumber")
	private String spNumber;
	
	@Column(name="error")
	private String error;
	
	@Column(name="remoteIP")
	private String remoteIP;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getSpNumber() {
		return spNumber;
	}
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	
	
	
}
