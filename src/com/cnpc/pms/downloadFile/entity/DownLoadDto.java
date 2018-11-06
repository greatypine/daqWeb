package com.cnpc.pms.downloadFile.entity;

/**
 * @Function：文件实体
 * @author：wuxinxin
 * @date:2018年4月4日 下午6:09:27
 *
 * @version V1.0
 */
public class DownLoadDto  {
//城市名
	String cityname;
	//城市ID
    String cityId;
    //用户ID
    String employeeId;
    //城市简称
	String cityCode;
	//事业群名称
	String groupname;
	//时间范畴：天，月
	String filetime;
	//标签  日报，月报，全国
	String target;

    public String getCityname() {
        return cityname;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getFiletime() {
        return filetime;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public void setFiletime(String filetime) {
        this.filetime = filetime;
    }

    public String getCityId() {
        return cityId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getTarget() {
        return target;
    }

    public void setCityId(String cityId) {

        this.cityId = cityId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
