package com.cnpc.pms.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.AccessType;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "t_store_acivities")
public class StoreActivities extends DataEntity {
	/**
	 * 城市
	 */
	@Column(name = "cityname",length = 45)
	private String cityname;
	
	/**
	 * 文娱类活动
	 */
	@Column(name = "recreational_activities", length = 12)
	private Integer recreational_activities;
	
	/**
	 * 公益类活动
	 */
	@Column(name = "public_welfare_activities", length = 12)
	private Integer public_welfare_activities;
	
	/**
	 * 志愿者活动
	 */
	@Column(name = "volunteer_activity", length = 12)
	private Integer volunteer_activity;
	
	/**
	 * 活动小计
	 */
	@Column(name = "avtivities_count", length = 12)
	private Integer avtivities_count;
	
	/**
	 * 活动参与人数
	 */
	@Column(name = "numbers_of_activities", length = 12)
	private Integer numbers_of_activities;
	
	/**
	 * 单场活动人数
	 */
	@Column(name = "numbers_of_single_activitie", length = 12)
	private Integer numbers_of_single_activitie;
	
	/**
	 * 门店自主营销活动
	 */
	@Column(name = "store_independent_activitie", length = 12)
	private Integer store_independent_activitie;
	
	/**
	 * 活动参与人数
	 */
	@Column(name = "store_numbers_of_activities", length = 12)
	private Integer store_numbers_of_activities;
	
	
	/**
	 * 单场活动人数
	 */
	@Column(name = "store_numbers_of_single_activitie", length = 12)
	private Integer store_numbers_of_single_activitie;
	
	
	/**
	 * 门店自主营销活动金额小计
	 */
	@Column(name = "store_independent_activitie_price", length = 12)
	private Integer store_independent_activitie_price;
	
	/**
	 * 合计活动数量
	 */
	@Column(name = "total_activities_count", length = 12)
	private Integer total_activities_count;
	
	
	
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getRecreational_activities() {
		return recreational_activities;
	}

	public void setRecreational_activities(Integer recreational_activities) {
		this.recreational_activities = recreational_activities;
	}

	public Integer getPublic_welfare_activities() {
		return public_welfare_activities;
	}

	public void setPublic_welfare_activities(Integer public_welfare_activities) {
		this.public_welfare_activities = public_welfare_activities;
	}

	public Integer getVolunteer_activity() {
		return volunteer_activity;
	}

	public void setVolunteer_activity(Integer volunteer_activity) {
		this.volunteer_activity = volunteer_activity;
	}

	public Integer getAvtivities_count() {
		return avtivities_count;
	}

	public void setAvtivities_count(Integer avtivities_count) {
		this.avtivities_count = avtivities_count;
	}

	public Integer getNumbers_of_activities() {
		return numbers_of_activities;
	}

	public void setNumbers_of_activities(Integer numbers_of_activities) {
		this.numbers_of_activities = numbers_of_activities;
	}

	public Integer getNumbers_of_single_activitie() {
		return numbers_of_single_activitie;
	}

	public void setNumbers_of_single_activitie(Integer numbers_of_single_activitie) {
		this.numbers_of_single_activitie = numbers_of_single_activitie;
	}

	public Integer getStore_independent_activitie() {
		return store_independent_activitie;
	}

	public void setStore_independent_activitie(Integer store_independent_activitie) {
		this.store_independent_activitie = store_independent_activitie;
	}

	public Integer getStore_numbers_of_activities() {
		return store_numbers_of_activities;
	}

	public void setStore_numbers_of_activities(Integer store_numbers_of_activities) {
		this.store_numbers_of_activities = store_numbers_of_activities;
	}

	public Integer getStore_numbers_of_single_activitie() {
		return store_numbers_of_single_activitie;
	}

	public void setStore_numbers_of_single_activitie(Integer store_numbers_of_single_activitie) {
		this.store_numbers_of_single_activitie = store_numbers_of_single_activitie;
	}

	public Integer getStore_independent_activitie_price() {
		return store_independent_activitie_price;
	}

	public void setStore_independent_activitie_price(Integer store_independent_activitie_price) {
		this.store_independent_activitie_price = store_independent_activitie_price;
	}

	public Integer getTotal_activities_count() {
		return total_activities_count;
	}

	public void setTotal_activities_count(Integer total_activities_count) {
		this.total_activities_count = total_activities_count;
	}
	
	
	
	
}
