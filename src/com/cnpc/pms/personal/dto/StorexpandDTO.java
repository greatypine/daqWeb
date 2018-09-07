package com.cnpc.pms.personal.dto;


public class StorexpandDTO {
	private Long id;
	/**
	 * 每周，勘察商铺数量
	 */
	private Integer survey_quantity;
	/**
	 * 每周，完成签约数量
	 */
	private Integer contract_quantity;
	/**
	 * 每周，总部上会通过数量
	 */
	private Integer through_quantity;
	/**
	 * 指标类型
	 */
	private String type;
	/**
	 * 统计周期
	 */
	private String period_type;
	/**
	 * 城市名称
	 */
	private String cityname;
	/**
	 * 城市编号
	 */
	private String cityno;

	private String statistical_time_period;
	private String start_time;
	private String end_time;
	
	private String caozuo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatistical_time_period() {
		return statistical_time_period;
	}

	public void setStatistical_time_period(String statistical_time_period) {
		this.statistical_time_period = statistical_time_period;
	}
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
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
