package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name="t_message_action")
public class MessageAction extends DataEntity{
	
	@Column(name="messageType")
	private String messgeType;
	
	@Column(name="actionCode")
	private String actionCode;
	
	@Column(name="managerName")
	private String managerName;
	
	@Column(name="methodName")
	private String methodName;
	
	@Column(name="remark")
	private String remark;

	public String getMessgeType() {
		return messgeType;
	}

	public void setMessgeType(String messgeType) {
		this.messgeType = messgeType;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
}
