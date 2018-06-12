package com.cnpc.pms.shortMessage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 回复消息关联动作
 * @author gbl
 *
 */
@Entity
@Table(name="t_message_type")
public class MessageType extends DataEntity{
	
	@Column(name="type")
	private String type;//短信类型（数据字典）
	
	
	
	@Column(name="remark")
	private String remark;//解释回复内容需要执行的动作

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
