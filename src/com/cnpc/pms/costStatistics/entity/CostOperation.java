package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:运营成本
 * @Author: gbl
 * @CreateDate: 2018/8/13 10:38
 */
@Entity
@Table(name="t_cost_operation")
public class CostOperation extends DataEntity {

    @Column(name="controlCode",columnDefinition="int(20) COMMENT 't_cost_control code'")
    private String controlCode;

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year_charge",columnDefinition="decimal(10,2) COMMENT '运营费/年'")
    private Double yearCharge;

    @Column(name="month_charge",columnDefinition="decimal(10,2) COMMENT '运营费/月'")
    private Double monthCharge;

    @Column(name="daily_office",columnDefinition="decimal(10,2) COMMENT '日常办公'")
    private Double dailyOffice;

    @Column(name="rent",columnDefinition="decimal(10,2) COMMENT '仓储型星店房租'")
    private Double rent;

    @Column(name="barrelled_water",columnDefinition="decimal(10,2) COMMENT '桶装水'")
    private Double barrelledWater;

    @Column(name="store_insurance",columnDefinition="decimal(10,2) COMMENT '门店保险'")
    private Double storeInsurance;

    @Column(name="car_maintain",columnDefinition="decimal(10,2) COMMENT '电动车维修'")
    private Double carMaintain;

    @Column(name="shopping_bag",columnDefinition="decimal(10,2) COMMENT '购物袋'")
    private Double shoppingBag;

    @Column(name="garbage_bag",columnDefinition="decimal(10,2) COMMENT '垃圾袋'")
    private Double garbageBag;

    @Column(name="extinguisher",columnDefinition="decimal(10,2) COMMENT '灭火器'")
    private Double extinguisher;

    @Column(name="backpack",columnDefinition="decimal(10,2) COMMENT '背包、车载包'")
    private Double backpack;

    @Column(name="helmet",columnDefinition="decimal(10,2) COMMENT '头盔、护膝、手套'")
    private Double helmet;

    @Column(name="greenPlants",columnDefinition="decimal(10,2) COMMENT '绿植'")
    private Double greenPlants;

    @Column(name="tray",columnDefinition="decimal(10,2) COMMENT '托盘'")
    private Double tray;

    @Column(name="storage_materials",columnDefinition="decimal(10,2) COMMENT '仓储物资'")
    private Double StorageMaterials;

    @Column(name="activity_fee",columnDefinition="decimal(10,2) COMMENT '活动费'")
    private Double activityFee;

    @Column(name="decoration_maintain",columnDefinition="decimal(10,2) COMMENT '门店装修及维修费'")
    private Double decorationMaintain;

    @Column(name="year",columnDefinition="int(4) COMMENT '年'")
    private Integer year;

    @Column(name="month",columnDefinition="int(4) COMMENT '月'")
    private Integer month;



    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
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

    public Double getYearCharge() {
        return yearCharge;
    }

    public void setYearCharge(Double yearCharge) {
        this.yearCharge = yearCharge;
    }

    public Double getMonthCharge() {
        return monthCharge;
    }

    public void setMonthCharge(Double monthCharge) {
        this.monthCharge = monthCharge;
    }

    public Double getDailyOffice() {
        return dailyOffice;
    }

    public void setDailyOffice(Double dailyOffice) {
        this.dailyOffice = dailyOffice;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getBarrelledWater() {
        return barrelledWater;
    }

    public void setBarrelledWater(Double barrelledWater) {
        this.barrelledWater = barrelledWater;
    }

    public Double getStoreInsurance() {
        return storeInsurance;
    }

    public void setStoreInsurance(Double storeInsurance) {
        this.storeInsurance = storeInsurance;
    }

    public Double getCarMaintain() {
        return carMaintain;
    }

    public void setCarMaintain(Double carMaintain) {
        this.carMaintain = carMaintain;
    }

    public Double getShoppingBag() {
        return shoppingBag;
    }

    public void setShoppingBag(Double shoppingBag) {
        this.shoppingBag = shoppingBag;
    }

    public Double getGarbageBag() {
        return garbageBag;
    }

    public void setGarbageBag(Double garbageBag) {
        this.garbageBag = garbageBag;
    }

    public Double getExtinguisher() {
        return extinguisher;
    }

    public void setExtinguisher(Double extinguisher) {
        this.extinguisher = extinguisher;
    }

    public Double getBackpack() {
        return backpack;
    }

    public void setBackpack(Double backpack) {
        this.backpack = backpack;
    }

    public Double getHelmet() {
        return helmet;
    }

    public void setHelmet(Double helmet) {
        this.helmet = helmet;
    }

    public Double getGreenPlants() {
        return greenPlants;
    }

    public void setGreenPlants(Double greenPlants) {
        this.greenPlants = greenPlants;
    }

    public Double getTray() {
        return tray;
    }

    public void setTray(Double tray) {
        this.tray = tray;
    }

    public Double getStorageMaterials() {
        return StorageMaterials;
    }

    public void setStorageMaterials(Double storageMaterials) {
        StorageMaterials = storageMaterials;
    }

    public Double getActivityFee() {
        return activityFee;
    }

    public void setActivityFee(Double activityFee) {
        this.activityFee = activityFee;
    }

    public Double getDecorationMaintain() {
        return decorationMaintain;
    }

    public void setDecorationMaintain(Double decorationMaintain) {
        this.decorationMaintain = decorationMaintain;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
