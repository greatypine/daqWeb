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

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="year_charge",columnDefinition="decimal(10,2) COMMENT '运营费/年'")
    private double yearCharge;

    @Column(name="month_charge",columnDefinition="decimal(10,2) COMMENT '运营费/月'")
    private double monthCharge;

    @Column(name="daily_office",columnDefinition="decimal(10,2) COMMENT '日常办公'")
    private double dailyOffice;

    @Column(name="rent",columnDefinition="decimal(10,2) COMMENT '仓储型星店房租'")
    private double rent;

    @Column(name="barrelled_water",columnDefinition="decimal(10,2) COMMENT '桶装水'")
    private double barrelledWater;

    @Column(name="store_insurance",columnDefinition="decimal(10,2) COMMENT '门店保险'")
    private double storeInsurance;

    @Column(name="car_maintain",columnDefinition="decimal(10,2) COMMENT '电动车维修'")
    private double carMaintain;

    @Column(name="shopping_bag",columnDefinition="decimal(10,2) COMMENT '购物袋'")
    private double shoppingBag;

    @Column(name="garbage_bag",columnDefinition="decimal(10,2) COMMENT '垃圾袋'")
    private double garbageBag;

    @Column(name="extinguisher",columnDefinition="decimal(10,2) COMMENT '灭火器'")
    private double extinguisher;

    @Column(name="backpack",columnDefinition="decimal(10,2) COMMENT '背包、车载包'")
    private double backpack;

    @Column(name="helmet",columnDefinition="decimal(10,2) COMMENT '头盔、护膝、手套'")
    private double helmet;

    @Column(name="greenPlants",columnDefinition="decimal(10,2) COMMENT '绿植'")
    private double greenPlants;

    @Column(name="tray",columnDefinition="decimal(10,2) COMMENT '托盘'")
    private double tray;

    @Column(name="storage_materials",columnDefinition="decimal(10,2) COMMENT '仓储物资'")
    private double StorageMaterials;

    @Column(name="activity_fee",columnDefinition="decimal(10,2) COMMENT '活动费'")
    private double activityFee;

    @Column(name="decoration_maintain",columnDefinition="decimal(10,2) COMMENT '门店装修及维修费'")
    private double decorationMaintain;

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

    public double getYearCharge() {
        return yearCharge;
    }

    public void setYearCharge(double yearCharge) {
        this.yearCharge = yearCharge;
    }

    public double getMonthCharge() {
        return monthCharge;
    }

    public void setMonthCharge(double monthCharge) {
        this.monthCharge = monthCharge;
    }

    public double getDailyOffice() {
        return dailyOffice;
    }

    public void setDailyOffice(double dailyOffice) {
        this.dailyOffice = dailyOffice;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getBarrelledWater() {
        return barrelledWater;
    }

    public void setBarrelledWater(double barrelledWater) {
        this.barrelledWater = barrelledWater;
    }

    public double getStoreInsurance() {
        return storeInsurance;
    }

    public void setStoreInsurance(double storeInsurance) {
        this.storeInsurance = storeInsurance;
    }

    public double getCarMaintain() {
        return carMaintain;
    }

    public void setCarMaintain(double carMaintain) {
        this.carMaintain = carMaintain;
    }

    public double getShoppingBag() {
        return shoppingBag;
    }

    public void setShoppingBag(double shoppingBag) {
        this.shoppingBag = shoppingBag;
    }

    public double getGarbageBag() {
        return garbageBag;
    }

    public void setGarbageBag(double garbageBag) {
        this.garbageBag = garbageBag;
    }

    public double getExtinguisher() {
        return extinguisher;
    }

    public void setExtinguisher(double extinguisher) {
        this.extinguisher = extinguisher;
    }

    public double getBackpack() {
        return backpack;
    }

    public void setBackpack(double backpack) {
        this.backpack = backpack;
    }

    public double getHelmet() {
        return helmet;
    }

    public void setHelmet(double helmet) {
        this.helmet = helmet;
    }

    public double getGreenPlants() {
        return greenPlants;
    }

    public void setGreenPlants(double greenPlants) {
        this.greenPlants = greenPlants;
    }

    public double getTray() {
        return tray;
    }

    public void setTray(double tray) {
        this.tray = tray;
    }

    public double getStorageMaterials() {
        return StorageMaterials;
    }

    public void setStorageMaterials(double storageMaterials) {
        StorageMaterials = storageMaterials;
    }

    public double getActivityFee() {
        return activityFee;
    }

    public void setActivityFee(double activityFee) {
        this.activityFee = activityFee;
    }

    public double getDecorationMaintain() {
        return decorationMaintain;
    }

    public void setDecorationMaintain(double decorationMaintain) {
        this.decorationMaintain = decorationMaintain;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }
}
