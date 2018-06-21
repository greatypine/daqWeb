package com.cnpc.pms.shortMessage.dto;

import java.util.List;

public class MessageTypeDto {
	
	private String name;
	private String code;
	private String remark;
	private List<MessageActionDto> list;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<MessageActionDto> getList() {
		return list;
	}
	public void setList(List<MessageActionDto> list) {
		this.list = list;
	}
	
	
}	
