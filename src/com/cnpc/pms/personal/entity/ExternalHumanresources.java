package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_external_humanresources")
public class ExternalHumanresources extends DataEntity{
	private static final long serialVersionUID = 1L;

	/**
	 * 城市 
	 */
	@Column(length = 65,name="citySelect")
	private String citySelect;
	
	/**
	 * 姓名
	 */
	@Column(length = 45, name = "name")
	private String name;
	
	
	/**
	 * 联系方式
	 */
	@Column(length = 65, name = "phone")
	private String phone;
	
	/**
	 * 身份证号
	 */
	@Column(length = 65, name = "cardnumber")
	private String cardnumber;
	
	
	/**
	 * 门店编号
	 */
	@Column(length = 45, name = "store_no")
	private String store_no;
	
	
	/**
	 * 门店名称
	 */
	@Column(length = 45, name = "store_name")
	private String store_name;
	
	
	/**
	 * 邀请码
	 */
	@Column(length = 65,name="inviteCode")
	private String inviteCode;

	

	public String getCitySelect() {
		return citySelect;
	}


	public void setCitySelect(String citySelect) {
		this.citySelect = citySelect;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getCardnumber() {
		return cardnumber;
	}


	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}


	public String getStore_no() {
		return store_no;
	}


	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}


	public String getStore_name() {
		return store_name;
	}


	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}


	public String getInviteCode() {
		return inviteCode;
	}


	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


	
	
	
	
}
