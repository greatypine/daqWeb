package com.cnpc.pms.costStatistics.dto;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.dto
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/9/17 19:23
 */
public class CostDto {

    private Long cityId;
    private String  storeNo;
    private Long storeId;
    private String  StoreName;
    private Integer year;
    private Integer month;
    private Long userId;
    private String role;//总部、城市
    private String rDate;


    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }
}
