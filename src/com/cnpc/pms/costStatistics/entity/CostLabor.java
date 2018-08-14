package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description: 人工成本
 * @Author: gbl
 * @CreateDate: 2018/8/10 15:25
 */
@Entity
@Table(name="t_cost_labor")
public class CostLabor extends DataEntity {

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private int year;

    @Column(name="month",columnDefinition="int(4) COMMENT '月份'")
    private int month;

    @Column(name="emolument",columnDefinition="decimal(20,2) COMMENT '员工薪酬'")
    private double emolument;

    @Column(name="uniform_charge",columnDefinition="decimal(20,2) COMMENT '工服年费'")
    private double uniformCharge;

    @Column(name="uniform_amortize",columnDefinition="decimal(20,2) COMMENT '工服摊销'")
    private double uniformAmortize;

    @Column(name="accommodation",columnDefinition="decimal(20,2) COMMENT '住宿星店房租'")
    private double accommodation;

    @Column(name="subtotal",columnDefinition="decimal(20,2) COMMENT '小计'")
    private double subtotal;


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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getEmolument() {
        return emolument;
    }

    public void setEmolument(double emolument) {
        this.emolument = emolument;
    }

    public double getUniformAmortize() {
        return uniformAmortize;
    }

    public void setUniformAmortize(double uniformAmortize) {
        this.uniformAmortize = uniformAmortize;
    }

    public double getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(double accommodation) {
        this.accommodation = accommodation;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getUniformCharge() {
        return uniformCharge;
    }

    public void setUniformCharge(double uniformCharge) {
        this.uniformCharge = uniformCharge;
    }
}
