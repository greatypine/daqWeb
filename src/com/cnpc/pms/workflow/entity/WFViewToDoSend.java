package com.cnpc.pms.workflow.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;


public class WFViewToDoSend implements IEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private Long id;
	@Column(name="username",length=50)
	private String username;
	@Column(name="phone",length=50)
	private String phone;
	@Column(name="email",length=50)
	private String email;
	@Column(name="todocounts")
	private BigDecimal todocounts;
	@Column(name="issend")
	private Integer issend;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getTodocounts() {
		return todocounts;
	}
	public void setTodocounts(BigDecimal todocounts) {
		this.todocounts = todocounts;
	}
	public Integer getIssend() {
		return issend;
	}
	public void setIssend(Integer issend) {
		this.issend = issend;
	}
	

}
