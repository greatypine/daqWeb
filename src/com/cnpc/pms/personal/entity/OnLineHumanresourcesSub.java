package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_online_humanresources_sub")
public class OnLineHumanresourcesSub extends DataEntity{
	
	@Column(name="online_id")
	private Long online_id;
	
	
	//事业群ID
	@Column(length = 65, name = "careerid")
	private String careerid;
	//事业群名称
	@Column(length = 65, name = "careername")
	private String careername;
	
	//频道ID
	@Column(length = 65, name = "channelid")
	private String channelid;
	//频道名称
	@Column(length = 65, name = "channelname")
	private String channelname;
	
	public Long getOnline_id() {
		return online_id;
	}
	public void setOnline_id(Long online_id) {
		this.online_id = online_id;
	}
	public String getCareerid() {
		return careerid;
	}
	public void setCareerid(String careerid) {
		this.careerid = careerid;
	}
	public String getCareername() {
		return careername;
	}
	public void setCareername(String careername) {
		this.careername = careername;
	}
	public String getChannelid() {
		return channelid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	
	
	
	
}
