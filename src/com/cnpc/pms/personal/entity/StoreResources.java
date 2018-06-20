package com.cnpc.pms.personal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "t_store_resources")
public class StoreResources extends DataEntity {
	/**
	 * 城市
	 */
	@Column(name = "cityname",columnDefinition="varchar(45) COMMENT '城市'")
	private String cityname;
	
	/**
	 * 室内主屏幕数量
	 */
	@Column(name = "home_screen",columnDefinition="int(12) COMMENT '室内主屏幕数量'")
	private Integer home_screen;
	
	/**
	 * 室内辅屏
	 */
	@Column(name = "home_auxiliary_screen",columnDefinition="int(12) COMMENT '室内辅屏'")
	private Integer home_auxiliary_screen;
	
	/**
	 * 室外电子竖屏
	 */
	@Column(name = "outdoor_electronic_screen",columnDefinition="int(12) COMMENT '室外电子竖屏'")
	private Integer outdoor_electronic_screen;
	
	/**
	 * 易拉宝
	 */
	@Column(name = "roll_up",columnDefinition="int(12) COMMENT '易拉宝'")
	private Integer roll_up;
	
	/**
	 * 海报
	 */
	@Column(name = "posters",columnDefinition="int(12) COMMENT '海报'")
	private Integer posters;
	
	/**
	 * 中央堆头
	 */
	@Column(name = "central_pile_head",columnDefinition="int(12) COMMENT '中央堆头'")
	private Integer central_pile_head;
	
	/**
	 * 地面堆头
	 */
	@Column(name = "floor_pile_head",columnDefinition="int(12) COMMENT '地面堆头'")
	private Integer floor_pile_head;
	
	/**
	 * 室内互动面积
	 */
	@Column(name = "indoor_interactive_area",columnDefinition="int(12) COMMENT '室内互动面积'")
	private Integer indoor_interactive_area;
	
	/**
	 * save_type: 0:店内资源统计，1：户外场地资源
	 */
	@Column(name = "save_type",columnDefinition="int(12) COMMENT '0:店内资源统计，1：户外场地资源'")
	private Integer save_type;
	
	/**
	 * 关联社区数量
	 */
	@Column(name = "associated_community_count",columnDefinition="int(12) COMMENT '关联社区数量'")
	private Integer associated_community_count;
	
	/**
	 * 框架数量
	 */
	@Column(name = "frame_number", columnDefinition="int(12) COMMENT '框架数量'")
	private Integer frame_number;
	/**
	 * 开放社区数量
	 */
	@Column(name = "open_community_count",columnDefinition="int(12) COMMENT '开放社区数量'")
	private Integer open_community_count;
	/**
	 * 封闭社区数量
	 */
	@Column(name = "closed_community_count",columnDefinition="int(12) COMMENT '封闭社区数量'")
	private Integer closed_community_count;
	/**
	 * 社区活动区数量或物业活动区数
	 */
	@Column(name = "activity_area_count",columnDefinition="int(12) COMMENT '社区活动区数量或物业活动区数'")
	private Integer activity_area_count;
	/**
	 * 收费场地数量
	 */
	@Column(name = "charge_for_site_count",columnDefinition="int(12) COMMENT '收费场地数量'")
	private Integer charge_for_site_count;
	
	/**
	 * 免费场地数量
	 */
	@Column(name = "free_for_site_count",columnDefinition="int(12) COMMENT '免费场地数量'")
	private Integer free_for_site_count;
	
	/**
	 * 场地合计面积（平米）
	 */
	@Column(name = "site_area",columnDefinition="int(12) COMMENT '场地合计面积（平米）'")
	private Integer site_area;
	/**
	 * 关联社区街道数量
	 */
	@Column(name = "associated_community_town_count",columnDefinition="int(12) COMMENT '关联社区街道数量'")
	private Integer associated_community_town_count;
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public Integer getHome_screen() {
		return home_screen;
	}
	public void setHome_screen(Integer home_screen) {
		this.home_screen = home_screen;
	}
	public Integer getHome_auxiliary_screen() {
		return home_auxiliary_screen;
	}
	public void setHome_auxiliary_screen(Integer home_auxiliary_screen) {
		this.home_auxiliary_screen = home_auxiliary_screen;
	}
	public Integer getOutdoor_electronic_screen() {
		return outdoor_electronic_screen;
	}
	public void setOutdoor_electronic_screen(Integer outdoor_electronic_screen) {
		this.outdoor_electronic_screen = outdoor_electronic_screen;
	}
	public Integer getRoll_up() {
		return roll_up;
	}
	public void setRoll_up(Integer roll_up) {
		this.roll_up = roll_up;
	}
	public Integer getPosters() {
		return posters;
	}
	public void setPosters(Integer posters) {
		this.posters = posters;
	}
	public Integer getCentral_pile_head() {
		return central_pile_head;
	}
	public void setCentral_pile_head(Integer central_pile_head) {
		this.central_pile_head = central_pile_head;
	}
	public Integer getFloor_pile_head() {
		return floor_pile_head;
	}
	public void setFloor_pile_head(Integer floor_pile_head) {
		this.floor_pile_head = floor_pile_head;
	}
	public Integer getIndoor_interactive_area() {
		return indoor_interactive_area;
	}
	public void setIndoor_interactive_area(Integer indoor_interactive_area) {
		this.indoor_interactive_area = indoor_interactive_area;
	}
	public Integer getSave_type() {
		return save_type;
	}
	public void setSave_type(Integer save_type) {
		this.save_type = save_type;
	}
	public Integer getAssociated_community_count() {
		return associated_community_count;
	}
	public void setAssociated_community_count(Integer associated_community_count) {
		this.associated_community_count = associated_community_count;
	}
	public Integer getFrame_number() {
		return frame_number;
	}
	public void setFrame_number(Integer frame_number) {
		this.frame_number = frame_number;
	}
	public Integer getOpen_community_count() {
		return open_community_count;
	}
	public void setOpen_community_count(Integer open_community_count) {
		this.open_community_count = open_community_count;
	}
	public Integer getClosed_community_count() {
		return closed_community_count;
	}
	public void setClosed_community_count(Integer closed_community_count) {
		this.closed_community_count = closed_community_count;
	}
	public Integer getActivity_area_count() {
		return activity_area_count;
	}
	public void setActivity_area_count(Integer activity_area_count) {
		this.activity_area_count = activity_area_count;
	}
	public Integer getCharge_for_site_count() {
		return charge_for_site_count;
	}
	public void setCharge_for_site_count(Integer charge_for_site_count) {
		this.charge_for_site_count = charge_for_site_count;
	}
	public Integer getSite_area() {
		return site_area;
	}
	public void setSite_area(Integer site_area) {
		this.site_area = site_area;
	}
	public Integer getAssociated_community_town_count() {
		return associated_community_town_count;
	}
	public void setAssociated_community_town_count(Integer associated_community_town_count) {
		this.associated_community_town_count = associated_community_town_count;
	}
	public Integer getFree_for_site_count() {
		return free_for_site_count;
	}
	public void setFree_for_site_count(Integer free_for_site_count) {
		this.free_for_site_count = free_for_site_count;
	}
	
	
	
}
