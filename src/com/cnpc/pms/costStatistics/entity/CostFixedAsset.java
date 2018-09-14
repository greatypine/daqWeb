package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:固定资产
 * @Author: gbl
 * @CreateDate: 2018/8/13 10:07
 */
@Entity
@Table(name="t_cost_fixed_asset")
public class CostFixedAsset extends DataEntity {

    @Column(name="controlCode",columnDefinition="int(20) COMMENT 't_cost_control code'")
    private String controlCode;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="amortize_money",columnDefinition="decimal(10,2) COMMENT '月摊销'")
    private double amortizeMoney;

    @Column(name="total",columnDefinition="decimal(10,2) COMMENT '总资产'")
    private double total;

    @Column(name="aio",columnDefinition="decimal(10,2) COMMENT '多功能一体机'")
    private double aio;

    @Column(name="mobile_phone",columnDefinition="decimal(10,2) COMMENT '手机'")
    private double mobilePhone;

    @Column(name="iPad",columnDefinition="decimal(10,2) COMMENT 'IPADmini'")
    private double iPad;

    @Column(name="cash_register",columnDefinition="decimal(10,2) COMMENT '收银机'")
    private double cashRegister;

    @Column(name="computer",columnDefinition="decimal(10,2) COMMENT '电脑'")
    private double computer;

    @Column(name="scanner_gun",columnDefinition="decimal(10,2) COMMENT '扫描仪'")
    private double scannerGun;

    @Column(name="electronics_total",columnDefinition="decimal(10,2) COMMENT '电子类合计'")
    private double electronicsTotal;

    @Column(name="electronics_amortize",columnDefinition="decimal(10,2) COMMENT '电子类摊销'")
    private double electronicsAmortize;

    @Column(name="electric_cars",columnDefinition="decimal(10,2) COMMENT '电动车'")
    private double electricCars;

    @Column(name="electric_cars_amortize",columnDefinition="decimal(10,2) COMMENT '电动车摊销'")
    private double electricCarsAmortize;

    @Column(name="cold_chain",columnDefinition="decimal(10,2) COMMENT '卖场冷链设备'")
    private double coldChain;

    @Column(name="safe_box",columnDefinition="decimal(10,2) COMMENT '保险柜'")
    private double safeBox;

    @Column(name="capsule_goods_shelf",columnDefinition="decimal(10,2) COMMENT '微仓货架'")
    private double  capsuleGoodsShelf;

    @Column(name="shopping_goods_shelf",columnDefinition="decimal(10,2) COMMENT '卖场货架'")
    private double  shoppingGoodsShelf;

    @Column(name="machine_total",columnDefinition="decimal(10,2) COMMENT '机器设备合计'")
    private double  machineTotal;

    @Column(name="machine_amortize",columnDefinition="decimal(10,2) COMMENT '机器设备摊销'")
    private double  machineAmortize;

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

    public double getAmortizeMoney() {
        return amortizeMoney;
    }

    public void setAmortizeMoney(double amortizeMoney) {
        this.amortizeMoney = amortizeMoney;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAio() {
        return aio;
    }

    public void setAio(double aio) {
        this.aio = aio;
    }

    public double getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(double mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public double getiPad() {
        return iPad;
    }

    public void setiPad(double iPad) {
        this.iPad = iPad;
    }

    public double getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(double cashRegister) {
        this.cashRegister = cashRegister;
    }

    public double getComputer() {
        return computer;
    }

    public void setComputer(double computer) {
        this.computer = computer;
    }

    public double getScannerGun() {
        return scannerGun;
    }

    public void setScannerGun(double scannerGun) {
        this.scannerGun = scannerGun;
    }

    public double getElectronicsTotal() {
        return electronicsTotal;
    }

    public void setElectronicsTotal(double electronicsTotal) {
        this.electronicsTotal = electronicsTotal;
    }

    public double getElectronicsAmortize() {
        return electronicsAmortize;
    }

    public void setElectronicsAmortize(double electronicsAmortize) {
        this.electronicsAmortize = electronicsAmortize;
    }

    public double getElectricCars() {
        return electricCars;
    }

    public void setElectricCars(double electricCars) {
        this.electricCars = electricCars;
    }

    public double getElectricCarsAmortize() {
        return electricCarsAmortize;
    }

    public void setElectricCarsAmortize(double electricCarsAmortize) {
        this.electricCarsAmortize = electricCarsAmortize;
    }

    public double getColdChain() {
        return coldChain;
    }

    public void setColdChain(double coldChain) {
        this.coldChain = coldChain;
    }

    public double getSafeBox() {
        return safeBox;
    }

    public void setSafeBox(double safeBox) {
        this.safeBox = safeBox;
    }

    public double getCapsuleGoodsShelf() {
        return capsuleGoodsShelf;
    }

    public void setCapsuleGoodsShelf(double capsuleGoodsShelf) {
        this.capsuleGoodsShelf = capsuleGoodsShelf;
    }

    public double getShoppingGoodsShelf() {
        return shoppingGoodsShelf;
    }

    public void setShoppingGoodsShelf(double shoppingGoodsShelf) {
        this.shoppingGoodsShelf = shoppingGoodsShelf;
    }

    public double getMachineTotal() {
        return machineTotal;
    }

    public void setMachineTotal(double machineTotal) {
        this.machineTotal = machineTotal;
    }

    public double getMachineAmortize() {
        return machineAmortize;
    }

    public void setMachineAmortize(double machineAmortize) {
        this.machineAmortize = machineAmortize;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }
}
