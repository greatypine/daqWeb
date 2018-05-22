package com.cnpc.pms.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "t_store_cover_person")
public class StoreCoverPerson extends DataEntity {
	/**
	 * 城市
	 */
	@Column(name = "cityname",length = 45)
	private String cityname;
	
	/**
	 * 社区关键人数量
	 */
	@Column(name = "community_person", length = 12)
	private Integer community_person;
	
	/**
	 * 店均社区关键人数量
	 */
	@Column(name = "avg_community_person", length = 12)
	private Integer avg_community_person;
	
	/**
	 * 政府从业人员
	 */
	@Column(name = "civil_servants", length = 12)
	private Integer civil_servants;
	
	/**
	 * 普通居民
	 */
	@Column(name = "general_person", length = 12)
	private Integer general_person;
	
	/**
	 * 民间组织人员
	 */
	@Column(name = "folk_organization", length = 12)
	private Integer folk_organization;
	
	/**
	 * 社区商户
	 */
	@Column(name = "community_businesses", length = 12)
	private Integer community_businesses;
	
	/**
	 * 微信群数量
	 */
	@Column(name = "wechant_crowd", length = 12)
	private Integer wechant_crowd;
	
	/**
	 * 店均微信群数量
	 */
	@Column(name = "avg_wechant_crowd", length = 12)
	private Integer avg_wechant_crowd;
	
	/**
	 * 微信群内客户容量
	 */
	@Column(name = "crowd_persons_count", length = 12)
	private Integer crowd_persons_count;
	
	
	/**
	 * 单个微信群人数
	 */
	@Column(name = "crowd_person_count", length = 12)
	private Integer crowd_person_count;
	
	/**
	 * 与门店人员有互动的客户数量
	 */
	@Column(name = "interactive_person_count", length = 12)
	private Integer interactive_person_count;
	
	/**
	 * 单个微信群互动人数
	 */
	@Column(name = "interactive_person_count_store", length = 12)
	private Integer interactive_person_count_store;
	
	/**
	 * 微信互动活跃人群占比
	 */
	@Column(name = "wechant_accounted_for_crowd", length = 12)
	private Integer wechant_accounted_for_crowd;

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Integer getCommunity_person() {
		return community_person;
	}

	public void setCommunity_person(Integer community_person) {
		this.community_person = community_person;
	}

	public Integer getAvg_community_person() {
		return avg_community_person;
	}

	public void setAvg_community_person(Integer avg_community_person) {
		this.avg_community_person = avg_community_person;
	}

	public Integer getCivil_servants() {
		return civil_servants;
	}

	public void setCivil_servants(Integer civil_servants) {
		this.civil_servants = civil_servants;
	}

	public Integer getGeneral_person() {
		return general_person;
	}

	public void setGeneral_person(Integer general_person) {
		this.general_person = general_person;
	}

	public Integer getFolk_organization() {
		return folk_organization;
	}

	public void setFolk_organization(Integer folk_organization) {
		this.folk_organization = folk_organization;
	}

	public Integer getCommunity_businesses() {
		return community_businesses;
	}

	public void setCommunity_businesses(Integer community_businesses) {
		this.community_businesses = community_businesses;
	}

	public Integer getWechant_crowd() {
		return wechant_crowd;
	}

	public void setWechant_crowd(Integer wechant_crowd) {
		this.wechant_crowd = wechant_crowd;
	}

	public Integer getAvg_wechant_crowd() {
		return avg_wechant_crowd;
	}

	public void setAvg_wechant_crowd(Integer avg_wechant_crowd) {
		this.avg_wechant_crowd = avg_wechant_crowd;
	}

	public Integer getCrowd_persons_count() {
		return crowd_persons_count;
	}

	public void setCrowd_persons_count(Integer crowd_persons_count) {
		this.crowd_persons_count = crowd_persons_count;
	}

	public Integer getCrowd_person_count() {
		return crowd_person_count;
	}

	public void setCrowd_person_count(Integer crowd_person_count) {
		this.crowd_person_count = crowd_person_count;
	}

	public Integer getInteractive_person_count() {
		return interactive_person_count;
	}

	public void setInteractive_person_count(Integer interactive_person_count) {
		this.interactive_person_count = interactive_person_count;
	}

	public Integer getInteractive_person_count_store() {
		return interactive_person_count_store;
	}

	public void setInteractive_person_count_store(Integer interactive_person_count_store) {
		this.interactive_person_count_store = interactive_person_count_store;
	}

	public Integer getWechant_accounted_for_crowd() {
		return wechant_accounted_for_crowd;
	}

	public void setWechant_accounted_for_crowd(Integer wechant_accounted_for_crowd) {
		this.wechant_accounted_for_crowd = wechant_accounted_for_crowd;
	}
	
	
	
	
}
