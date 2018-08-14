package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:装修摊销
 * @Author: gbl
 * @CreateDate: 2018/8/13 9:31
 */
@Entity
@Table(name="t_cost_renovation")
public class CostRenovation extends DataEntity {
    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="decoration_company",columnDefinition="varchar(200) COMMENT '装修公司'")
    private String decorationCompany ;

    @Column(name="structure_acreage",columnDefinition="decimal(10,2) COMMENT '建筑面积'")
    private double structureAcreage;

    @Column(name="renovation_unit_price",columnDefinition="decimal(10,2) COMMENT '装修单价'")
    private double renovationUnitPrice;


    @Column(name="business_screen",columnDefinition="decimal(10,2) COMMENT '商业展屏'")
    private double businessScreen;

    @Column(name="furniture",columnDefinition="decimal(10,2) COMMENT '家具'")
    private double furniture ;

    @Column(name="light_box",columnDefinition="decimal(10,2) COMMENT '标牌和灯箱'")
    private double lightBox ;

    @Column(name="process_manage",columnDefinition="decimal(10,2) COMMENT '过程管理'")
    private double processManage ;

    @Column(name="process_manage_surcharge",columnDefinition="decimal(10,2) COMMENT '过程管理额外'")
    private double processManageSurcharge ;

    @Column(name="air_conditioner",columnDefinition="decimal(10,2) COMMENT '空调设备'")
    private double airConditioner;

    @Column(name="air_conditioner_surcharge",columnDefinition="decimal(10,2) COMMENT '空调设备额外'")
    private double airConditionerSurcharge;

    @Column(name="design",columnDefinition="decimal(10,2) COMMENT '设计'")
    private double design;

    @Column(name="total",columnDefinition="decimal(10,2) COMMENT '单店总装修花销'")
    private double total;

    @Column(name="amortize_month",columnDefinition="int(2) COMMENT '摊销月数'")
    private Integer amortizeMonth;

    @Column(name="amortize_money",columnDefinition="decimal(10,2) COMMENT '摊销金额'")
    private double amortizeMoney;

    @Column(name="completed_date",columnDefinition="datetime(0,0) COMMENT '竣工日期'")
    private double completedDate;


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

    public String getDecorationCompany() {
        return decorationCompany;
    }

    public void setDecorationCompany(String decorationCompany) {
        this.decorationCompany = decorationCompany;
    }

    public double getStructureAcreage() {
        return structureAcreage;
    }

    public void setStructureAcreage(double structureAcreage) {
        this.structureAcreage = structureAcreage;
    }

    public double getRenovationUnitPrice() {
        return renovationUnitPrice;
    }

    public void setRenovationUnitPrice(double renovationUnitPrice) {
        this.renovationUnitPrice = renovationUnitPrice;
    }

    public double getBusinessScreen() {
        return businessScreen;
    }

    public void setBusinessScreen(double businessScreen) {
        this.businessScreen = businessScreen;
    }

    public double getFurniture() {
        return furniture;
    }

    public void setFurniture(double furniture) {
        this.furniture = furniture;
    }

    public double getLightBox() {
        return lightBox;
    }

    public void setLightBox(double lightBox) {
        this.lightBox = lightBox;
    }

    public double getProcessManage() {
        return processManage;
    }

    public void setProcessManage(double processManage) {
        this.processManage = processManage;
    }

    public double getProcessManageSurcharge() {
        return processManageSurcharge;
    }

    public void setProcessManageSurcharge(double processManageSurcharge) {
        this.processManageSurcharge = processManageSurcharge;
    }

    public double getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(double airConditioner) {
        this.airConditioner = airConditioner;
    }

    public double getAirConditionerSurcharge() {
        return airConditionerSurcharge;
    }

    public void setAirConditionerSurcharge(double airConditionerSurcharge) {
        this.airConditionerSurcharge = airConditionerSurcharge;
    }

    public double getDesign() {
        return design;
    }

    public void setDesign(double design) {
        this.design = design;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getAmortizeMonth() {
        return amortizeMonth;
    }

    public void setAmortizeMonth(Integer amortizeMonth) {
        this.amortizeMonth = amortizeMonth;
    }

    public double getAmortizeMoney() {
        return amortizeMoney;
    }

    public void setAmortizeMoney(double amortizeMoney) {
        this.amortizeMoney = amortizeMoney;
    }

    public double getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(double completedDate) {
        this.completedDate = completedDate;
    }
}
