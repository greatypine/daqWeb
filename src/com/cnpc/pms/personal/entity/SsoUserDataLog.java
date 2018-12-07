package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_ssouser_data_log")
public class SsoUserDataLog extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(length = 2000, name = "rcvsyncdata")
	private String rcvsyncdata;
	
	@Column(length = 2000, name = "sendsyncdata")
	private String sendsyncdata;

	
	public String getRcvsyncdata() {
		return rcvsyncdata;
	}

	public void setRcvsyncdata(String rcvsyncdata) {
		this.rcvsyncdata = rcvsyncdata;
	}

	public String getSendsyncdata() {
		return sendsyncdata;
	}

	public void setSendsyncdata(String sendsyncdata) {
		this.sendsyncdata = sendsyncdata;
	}

	
	
}
