package com.cnpc.pms.shortMessage.dto;

import javax.persistence.Column;

public class ReplyMessageDto {
	
	private String content;//回复内容

	private String phone;//回复电话
	
	private String spNumber;
	
	private String messageType;//短信类型
	
	private String actionCode;//短信执行动作
	
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

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	
	
}
