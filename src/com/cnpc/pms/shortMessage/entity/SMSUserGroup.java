package com.cnpc.pms.shortMessage.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.shortMessage.dto.UserGroupUserDto;
@Entity
@Table(name="t_sms_usergroup")
public class SMSUserGroup extends DataEntity{
	@Column(name="name",length=50)
	private String name;//用户组名称
	
	@Column(name="code",length=50)
	private String code;//用户组编号
	
	@Column(name="type",length=20)
	private String type;//用户组类型 ：线下员工用户组 offline  线上员工用户组online  客户用户组customer
    
	@Transient
	private List<UserGroupUserDto> list;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<UserGroupUserDto> getList() {
		return list;
	}

	public void setList(List<UserGroupUserDto> list) {
		this.list = list;
	}
	
	
}
