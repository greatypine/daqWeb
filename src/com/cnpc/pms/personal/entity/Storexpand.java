package com.cnpc.pms.personal.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;

@Entity
@Table(name = "di_storexpand")
public class Storexpand extends DataEntity {
	/**
	 * 每周，勘察商铺数量
	 */
	@Column(name = "survey_quantity")
	private Integer survey_quantity;
	/**
	 * 每周，完成签约数量
	 */
	@Column(name = "contract_quantity")
	private Integer contract_quantity;
	/**
	 * 每周，总部上会通过数量
	 */
	@Column(name = "through_quantity")
	private Integer through_quantity;
	/**
	 * 合作店任务目标(每年)
	 */
	@Column(name = "cooperative_task")
	private Integer cooperative_task;
	/**
	 * 自营店任务目标(每年)
	 */
	@Column(name = "self_support_task")
	private Integer self_support_task;
	/**
	 * 城市名称
	 */
	@Column(length = 45, name = "cityname")
	private String cityname;
	/**
	 * 城市编号
	 */
	@Column(length = 45, name = "cityno")
	private String cityno;

	@Column(length = 255, name = "statistical_time_period")
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

	public void setThrough_quantity(Integer through_quantity) {
		this.through_quantity = through_quantity;
	}

	public Integer getCooperative_task() {
		return cooperative_task;
	}

	public void setCooperative_task(Integer cooperative_task) {
		this.cooperative_task = cooperative_task;
	}

	public Integer getSelf_support_task() {
		return self_support_task;
	}

	public void setSelf_support_task(Integer self_support_task) {
		this.self_support_task = self_support_task;
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
