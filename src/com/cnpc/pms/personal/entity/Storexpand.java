package com.cnpc.pms.personal.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "df_bussiness_target")
public class Storexpand extends DataEntity {
	/**
	 * 每周，勘察商铺数量
	 */
	@Column(name = "param_first")
	private Integer survey_quantity;
	/**
	 * 每周，完成签约数量
	 */
	@Column(name = "param_second")
	private Integer contract_quantity;
	/**
	 * 每周，总部上会通过数量
	 */
	@Column(name = "param_third")
	private Integer through_quantity;
	/**
	 * 指标类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 统计周期
	 */
	@Column(name = "period_type")
	private String period_type;
	/**
	 * 城市名称
	 */
	@Column(length = 45, name = "city_name")
	private String cityname;
	/**
	 * 城市编号
	 */
	@Column(length = 45, name = "city_no")
	private String cityno;

	@Column(length = 255, name = "time_period")
	private String statistical_time_period;
	@Column(name = "start_time")
	private Date start_time;
	@Column(name = "end_time")
	private Date end_time;
	
	@Transient
	private String caozuo;
	public String getStatistical_time_period() {
		return statistical_time_period;
	}

	public void setStatistical_time_period(String statistical_time_period) {
		this.statistical_time_period = statistical_time_period;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getSurvey_quantity() {
		return survey_quantity;
	}

	public void setSurvey_quantity(Integer survey_quantity) {
		this.survey_quantity = survey_quantity;
	}

	public Integer getContract_quantity() {
		return contract_quantity;
	}

	public void setContract_quantity(Integer contract_quantity) {
		this.contract_quantity = contract_quantity;
	}

	public Integer getThrough_quantity() {
		return through_quantity;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPeriod_type() {
		return period_type;
	}

	public void setPeriod_type(String period_type) {
		this.period_type = period_type;
	}

	public void setThrough_quantity(Integer through_quantity) {
		this.through_quantity = through_quantity;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}
	public String getCaozuo() {
		return caozuo;
	}
	public void setCaozuo(String caozuo) {
		this.caozuo = caozuo;
	}
}
