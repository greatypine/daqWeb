package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_dist_career")
public class DistCareer extends DataEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Column(name = "pk_userid")
	private Long pk_userid;
	
	/**
	 * 事业群名称
	 */
	@Column(length = 45, name = "careername")
	private String careername;
	
	/**
	 * 事业群代码
	 */
	@Column(length = 45, name = "careercode")
	private String careercode;
	
	@Transient
	private Long careerid;
	@Transient
	private String username;
	@Transient
	private String career1;
	@Transient
	private String career2;
	@Transient
	private String career3;
	@Transient
	private String career4;
	@Transient
	private String career5;
	@Transient
	private Boolean isSelectAll;

	public Long getPk_userid() {
		return pk_userid;
	}

	public void setPk_userid(Long pk_userid) {
		this.pk_userid = pk_userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getIsSelectAll() {
		return isSelectAll;
	}

	public void setIsSelectAll(Boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	public String getCareername() {
		return careername;
	}

	public void setCareername(String careername) {
		this.careername = careername;
	}

	public String getCareercode() {
		return careercode;
	}

	public void setCareercode(String careercode) {
		this.careercode = careercode;
	}

	public Long getCareerid() {
		return careerid;
	}

	public void setCareerid(Long careerid) {
		this.careerid = careerid;
	}

	public String getCareer1() {
		return career1;
	}

	public void setCareer1(String career1) {
		this.career1 = career1;
	}

	public String getCareer2() {
		return career2;
	}

	public void setCareer2(String career2) {
		this.career2 = career2;
	}

	public String getCareer3() {
		return career3;
	}

	public void setCareer3(String career3) {
		this.career3 = career3;
	}

	public String getCareer4() {
		return career4;
	}

	public void setCareer4(String career4) {
		this.career4 = career4;
	}

	public String getCareer5() {
		return career5;
	}

	public void setCareer5(String career5) {
		this.career5 = career5;
	}

	
}
