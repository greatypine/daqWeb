package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name="t_message_action")
public class MessageAction extends DataEntity{
	
	@Column(name="messageTypeCode")
	private String messageTypeCode;
	
	@Column(name="actionCode")
	private String actionCode;
	

	
	@Column(name="remark")
	private String remark;

	

	public String getMessageTypeCode() {
		return messageTypeCode;
	}

	public void setMessageTypeCode(String messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}
