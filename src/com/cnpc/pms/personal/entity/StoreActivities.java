package com.cnpc.pms.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
	
	
}
