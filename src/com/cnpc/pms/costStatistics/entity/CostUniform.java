package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:工服年费
 * @Author: gbl
 * @CreateDate: 2018/9/27 9:13
 */
@Entity
@Table(name="t_cost_uniform")
public class CostUniform extends DataEntity {

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private int year;

    @Column(name="uniform_charge",columnDefinition="decimal(20,2) COMMENT '工服年费'")
    private Double uniformCharge;

    @Column(name="uniform_amortize",columnDefinition="decimal(20,2) COMMENT '工服摊销'")
    private Double uniformAmortize;


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

    public Double getUniformCharge() {
        return uniformCharge;
    }

    public void setUniformCharge(Double uniformCharge) {
        this.uniformCharge = uniformCharge;
    }

    public Double getUniformAmortize() {
        return uniformAmortize;
    }

    public void setUniformAmortize(Double uniformAmortize) {
        this.uniformAmortize = uniformAmortize;
    }
}
