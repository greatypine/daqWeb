package com.cnpc.pms.personal.dto;

public class StoreDTO {
	private String id;
	private String name;
	private String cityname;
	private String store_id;
	private String employeeId;
	private String observe_month;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getObserve_month() {
		return observe_month;
	}

	public void setObserve_month(String observe_month) {
		this.observe_month = observe_month;
	}
}
