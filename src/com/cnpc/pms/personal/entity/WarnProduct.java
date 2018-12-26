package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_warn_product")
public class WarnProduct extends DataEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(length = 128, name = "career_name")
	private String career_name;
	
	@Column(length = 128, name = "product_name")
	private String product_name;

	@Column(name="number")
	private Long number;
	

	public String getCareer_name() {
		return career_name;
	}

	public void setCareer_name(String career_name) {
		this.career_name = career_name;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	
	
	
}
