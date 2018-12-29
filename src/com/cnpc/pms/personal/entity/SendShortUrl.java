package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_send_shorturl")
public class SendShortUrl extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(length = 25, name = "channelname")
	private String channelname;
	
	@Column(length = 64, name = "channelid")
	private String channelid;
	
	@Column(length = 25, name = "phone")
	private String phone;
	
	@Column(length = 25, name = "senddate")
	private String senddate;

	//未发送  、已发送、已读
	@Column(length = 25, name = "sendstatus")
	private String sendstatus;
	
	@Column(length = 128, name = "shorturl")
	private String shorturl;
	
	
	
	
	@Column(length = 25, name = "storename")
	private String storename;
	
	@Column(length = 64, name = "platformid")
	private String platformid;

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSenddate() {
		return senddate;
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}

	public String getShorturl() {
		return shorturl;
	}

	public void setShorturl(String shorturl) {
		this.shorturl = shorturl;
	}

	public String getChannelname() {
		return channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getPlatformid() {
		return platformid;
	}

	public void setPlatformid(String platformid) {
		this.platformid = platformid;
	}
	

	
	
	
	
}
