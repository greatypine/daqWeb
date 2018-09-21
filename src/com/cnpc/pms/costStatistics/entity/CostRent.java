package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:租金成本
 * @Author: gbl
 * @CreateDate: 2018/8/10 16:41
 */
@Entity
@Table(name="t_cost_rent")
public class CostRent extends DataEntity {

    @Column(name="controlCode",columnDefinition="int(20) COMMENT 't_cost_control code'")
    private String controlCode;

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="addr",columnDefinition="varchar(200) COMMENT '门店地址'")
    private String addr;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private Integer year;

    @Column(name="month",columnDefinition="int(4) COMMENT '月份'")
    private Integer month;

    @Column(name="property_fee_year",columnDefinition="decimal(10,2) COMMENT '物业费/年'")
    private Double propertyFeeYear;

    @Column(name="property_fee_month",columnDefinition="decimal(10,2) COMMENT '物业费/月'")
    private Double propertyFeeMonth;


    @Column(name="property_deadline",columnDefinition="int(2) COMMENT '物业期限/月 '")
    private Integer propertyDeadline;


    @Column(name="rental_month",columnDefinition="decimal(10,2) COMMENT '每月租金 '")
    private Double rentalMonth;

    @Column(name="cost_month",columnDefinition="decimal(10,2) COMMENT '每月费用 '")
    private Double costMonth;


    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getPropertyDeadline() {
        return propertyDeadline;
    }

    public void setPropertyDeadline(Integer propertyDeadline) {
        this.propertyDeadline = propertyDeadline;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

    public Double getPropertyFeeYear() {
        return propertyFeeYear;
    }

    public void setPropertyFeeYear(Double propertyFeeYear) {
        this.propertyFeeYear = propertyFeeYear;
    }

    public Double getPropertyFeeMonth() {
        return propertyFeeMonth;
    }

    public void setPropertyFeeMonth(Double propertyFeeMonth) {
        this.propertyFeeMonth = propertyFeeMonth;
    }

    public Double getRentalMonth() {
        return rentalMonth;
    }

    public void setRentalMonth(Double rentalMonth) {
        this.rentalMonth = rentalMonth;
    }

    public Double getCostMonth() {
        return costMonth;
    }

    public void setCostMonth(Double costMonth) {
        this.costMonth = costMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
