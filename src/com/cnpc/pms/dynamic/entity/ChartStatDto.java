package com.cnpc.pms.dynamic.entity;

/**
 * @Function：数据分析
 * @author：chenchuang
 * @date:2018年4月4日 下午6:09:27
 *
 * @version V1.0
 */
public class ChartStatDto {

	String storeno;
	String cityname;
	String deptname;
	String channelname;
	
	public String getStoreno() {
		return storeno;
	}
	public void setStoreno(String storeno) {
		this.storeno = storeno;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getChannelname() {
		return channelname;
	}
	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}
	
}
