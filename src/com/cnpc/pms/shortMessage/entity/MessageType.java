package com.cnpc.pms.shortMessage.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 回复消息关联动作
 * @author gbl
 *
 */
@Entity
@Table(name="t_message_type")
public class MessageType extends DataEntity{
	
	@Column(name="name")
	private String name;//短信类型名称
	
	@Column(name="code")
	private String code;//短信类型编码（数据字典）
	
	
	
	@Column(name="remark")
	private String remark;//解释回复内容需要执行的动作

	@Transient
	private List<MessageAction> childrens;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MessageAction> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<MessageAction> childrens) {
		this.childrens = childrens;
	}

	

	
	
	
}
