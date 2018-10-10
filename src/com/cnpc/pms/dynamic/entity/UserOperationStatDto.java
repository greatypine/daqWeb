package com.cnpc.pms.dynamic.entity;

public class UserOperationStatDto {

	private String beginDate;
	private String endDate;
	private String cityName;
	private String storeNo;
	private String paytype;
	private String dimension;
	private String searchStoreStr;
	private String searchAreaStr;
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getSearchStoreStr() {
		return searchStoreStr;
	}

	public void setSearchStoreStr(String searchStoreStr) {
		this.searchStoreStr = searchStoreStr;
	}

	public String getSearchAreaStr() {
		return searchAreaStr;
	}

	public void setSearchAreaStr(String searchAreaStr) {
		this.searchAreaStr = searchAreaStr;
	}
}
