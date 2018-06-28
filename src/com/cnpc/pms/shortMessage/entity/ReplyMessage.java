package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;
/**
 * 短信回执
 * @author gbl
 *
 */
@Entity
@Table(name="t_reply_message")
public class ReplyMessage extends DataEntity{
	
	@Column(name="content")
	private String content;//回复内容
	
	@Column(name="phone")
	private String phone;//回复电话
	
	@Column(name="spNumber")
	private String spNumber;
	
	@Column(name="messageType")
	private String messageType;//短信类型
	
	@Column(name="remoteIP")
	private String remoteIP;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSpNumber() {
		return spNumber;
	}

	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	
	
	
}
