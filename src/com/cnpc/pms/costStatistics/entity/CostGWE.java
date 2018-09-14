package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:水电费、燃气费
 * @Author: gbl
 * @CreateDate: 2018/8/13 10:31
 */
@Entity
@Table(name="t_cost_gas_water_elec")
public class CostGWE extends DataEntity {


    @Column(name="controlCode",columnDefinition="int(20) COMMENT 't_cost_control code'")
    private String controlCode;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private Integer year;

    @Column(name="month",columnDefinition="int(4) COMMENT '月份'")
    private Integer month;

    @Column(name="gas_fee",columnDefinition="decimal(10,2) COMMENT '燃气费'")
    private Double gasFee;

    @Column(name="water_fee",columnDefinition="decimal(10,2) COMMENT '水费'")
    private Double waterFee;

    @Column(name="electricity_fee",columnDefinition="decimal(10,2) COMMENT '电费'")
    private Double electricityFee;

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

    public Double getGasFee() {
        return gasFee;
    }

    public void setGasFee(Double gasFee) {
        this.gasFee = gasFee;
    }

    public Double getWaterFee() {
        return waterFee;
    }

    public void setWaterFee(Double waterFee) {
        this.waterFee = waterFee;
    }

    public Double getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(Double electricityFee) {
        this.electricityFee = electricityFee;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }
}
