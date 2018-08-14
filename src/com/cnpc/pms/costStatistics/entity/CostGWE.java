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

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private Integer year;

    @Column(name="month",columnDefinition="int(4) COMMENT '月份'")
    private Integer month;

    @Column(name="gas_fee",columnDefinition="decimal(10,2) COMMENT '燃气费'")
    private double gasFee;

    @Column(name="water_fee",columnDefinition="decimal(10,2) COMMENT '水费'")
    private double waterFee;

    @Column(name="electricity_fee",columnDefinition="decimal(10,2) COMMENT '电费'")
    private double electricityFee;

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

    public double getGasFee() {
        return gasFee;
    }

    public void setGasFee(double gasFee) {
        this.gasFee = gasFee;
    }

    public double getWaterFee() {
        return waterFee;
    }

    public void setWaterFee(double waterFee) {
        this.waterFee = waterFee;
    }

    public double getElectricityFee() {
        return electricityFee;
    }

    public void setElectricityFee(double electricityFee) {
        this.electricityFee = electricityFee;
    }
}
