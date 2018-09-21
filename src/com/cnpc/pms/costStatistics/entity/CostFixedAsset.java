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

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="amortize_money",columnDefinition="decimal(10,2) COMMENT '月摊销'")
    private Double amortizeMoney;

    @Column(name="total",columnDefinition="decimal(10,2) COMMENT '总资产'")
    private Double total;

    @Column(name="aio",columnDefinition="decimal(10,2) COMMENT '多功能一体机'")
    private Double aio;

    @Column(name="mobile_phone",columnDefinition="decimal(10,2) COMMENT '手机'")
    private Double mobilePhone;

    @Column(name="iPad",columnDefinition="decimal(10,2) COMMENT 'IPADmini'")
    private Double iPad;

    @Column(name="cash_register",columnDefinition="decimal(10,2) COMMENT '收银机'")
    private Double cashRegister;

    @Column(name="computer",columnDefinition="decimal(10,2) COMMENT '电脑'")
    private Double computer;

    @Column(name="scanner_gun",columnDefinition="decimal(10,2) COMMENT '扫描仪'")
    private Double scannerGun;

    @Column(name="electronics_total",columnDefinition="decimal(10,2) COMMENT '电子类合计'")
    private Double electronicsTotal;

    @Column(name="electronics_amortize",columnDefinition="decimal(10,2) COMMENT '电子类摊销'")
    private Double electronicsAmortize;

    @Column(name="electric_cars",columnDefinition="decimal(10,2) COMMENT '电动车'")
    private Double electricCars;

    @Column(name="electric_cars_amortize",columnDefinition="decimal(10,2) COMMENT '电动车摊销'")
    private Double electricCarsAmortize;

    @Column(name="cold_chain",columnDefinition="decimal(10,2) COMMENT '卖场冷链设备'")
    private Double coldChain;

    @Column(name="safe_box",columnDefinition="decimal(10,2) COMMENT '保险柜'")
    private Double safeBox;

    @Column(name="capsule_goods_shelf",columnDefinition="decimal(10,2) COMMENT '微仓货架'")
    private Double  capsuleGoodsShelf;

    @Column(name="shopping_goods_shelf",columnDefinition="decimal(10,2) COMMENT '卖场货架'")
    private Double  shoppingGoodsShelf;

    @Column(name="machine_total",columnDefinition="decimal(10,2) COMMENT '机器设备合计'")
    private Double  machineTotal;

    @Column(name="machine_amortize",columnDefinition="decimal(10,2) COMMENT '机器设备摊销'")
    private Double  machineAmortize;

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }

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

    public Double getAmortizeMoney() {
        return amortizeMoney;
    }

    public void setAmortizeMoney(Double amortizeMoney) {
        this.amortizeMoney = amortizeMoney;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAio() {
        return aio;
    }

    public void setAio(Double aio) {
        this.aio = aio;
    }

    public Double getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Double mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Double getiPad() {
        return iPad;
    }

    public void setiPad(Double iPad) {
        this.iPad = iPad;
    }

    public Double getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(Double cashRegister) {
        this.cashRegister = cashRegister;
    }

    public Double getComputer() {
        return computer;
    }

    public void setComputer(Double computer) {
        this.computer = computer;
    }

    public Double getScannerGun() {
        return scannerGun;
    }

    public void setScannerGun(Double scannerGun) {
        this.scannerGun = scannerGun;
    }

    public Double getElectronicsTotal() {
        return electronicsTotal;
    }

    public void setElectronicsTotal(Double electronicsTotal) {
        this.electronicsTotal = electronicsTotal;
    }

    public Double getElectronicsAmortize() {
        return electronicsAmortize;
    }

    public void setElectronicsAmortize(Double electronicsAmortize) {
        this.electronicsAmortize = electronicsAmortize;
    }

    public Double getElectricCars() {
        return electricCars;
    }

    public void setElectricCars(Double electricCars) {
        this.electricCars = electricCars;
    }

    public Double getElectricCarsAmortize() {
        return electricCarsAmortize;
    }

    public void setElectricCarsAmortize(Double electricCarsAmortize) {
        this.electricCarsAmortize = electricCarsAmortize;
    }

    public Double getColdChain() {
        return coldChain;
    }

    public void setColdChain(Double coldChain) {
        this.coldChain = coldChain;
    }

    public Double getSafeBox() {
        return safeBox;
    }

    public void setSafeBox(Double safeBox) {
        this.safeBox = safeBox;
    }

    public Double getCapsuleGoodsShelf() {
        return capsuleGoodsShelf;
    }

    public void setCapsuleGoodsShelf(Double capsuleGoodsShelf) {
        this.capsuleGoodsShelf = capsuleGoodsShelf;
    }

    public Double getShoppingGoodsShelf() {
        return shoppingGoodsShelf;
    }

    public void setShoppingGoodsShelf(Double shoppingGoodsShelf) {
        this.shoppingGoodsShelf = shoppingGoodsShelf;
    }

    public Double getMachineTotal() {
        return machineTotal;
    }

    public void setMachineTotal(Double machineTotal) {
        this.machineTotal = machineTotal;
    }

    public Double getMachineAmortize() {
        return machineAmortize;
    }

    public void setMachineAmortize(Double machineAmortize) {
        this.machineAmortize = machineAmortize;
    }
}
