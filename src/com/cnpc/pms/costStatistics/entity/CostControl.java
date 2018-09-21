package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description: 成本控制总表
 * @Author: gbl
 * @CreateDate: 2018/9/14 16:05
 */
@Entity
@Table(name="t_cost_control")
public class CostControl extends DataEntity {

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;//城市名称
    @Column(name="cityCode",columnDefinition="varchar(100) COMMENT '城市编号'")
    private String cityCode;//城市编号
    @Column(name="cityId",columnDefinition="bigint(100) COMMENT '城市ID'")
    private Long cityId;//城市Id t_dist_citycode表主键ID
    @Column(name="year",columnDefinition="int(10) COMMENT '年'")
    private Integer year;//年
    @Column(name="month",columnDefinition="int(10) COMMENT '月'")
    private Integer month;//月
    @Column(name="labor",columnDefinition="int(10) COMMENT '人工成本标识'")
    private Integer labor;//人工成本标识
    @Column(name="rent",columnDefinition="int(10) COMMENT '租金成本标识'")
    private Integer rent;//租金成本标识
    @Column(name="renovation",columnDefinition="int(10) COMMENT '装修摊销标识'")
    private Integer renovation;//装修摊销标识
    @Column(name="fixedAsset",columnDefinition="int(10) COMMENT '固定资产标识'")
    private Integer fixedAsset;//固定资产标识
    @Column(name="gwe",columnDefinition="int(10) COMMENT '水电费标识'")
    private Integer gwe;//水电费标识
    @Column(name="operation",columnDefinition="int(10) COMMENT '日常运营成本标识'")
    private Integer operation;//日常运营成本标识
    @Column(name="code",columnDefinition="int(20) COMMENT '编号'")
    private String code;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public Integer getLabor() {
        return labor;
    }

    public void setLabor(Integer labor) {
        this.labor = labor;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getRenovation() {
        return renovation;
    }

    public void setRenovation(Integer renovation) {
        this.renovation = renovation;
    }

    public Integer getFixedAsset() {
        return fixedAsset;
    }

    public void setFixedAsset(Integer fixedAsset) {
        this.fixedAsset = fixedAsset;
    }

    public Integer getGwe() {
        return gwe;
    }

    public void setGwe(Integer gwe) {
        this.gwe = gwe;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
