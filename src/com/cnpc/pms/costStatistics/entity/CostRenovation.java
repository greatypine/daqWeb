package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name="controlCode",columnDefinition="int(20) COMMENT 't_cost_control code'")
    private String controlCode;

    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="decoration_company",columnDefinition="varchar(100) COMMENT '装修公司'")
    private String decorationCompany ;

    @Column(name="structure_acreage",columnDefinition="decimal(10,2) COMMENT '建筑面积'")
    private Double structureAcreage;

    @Column(name="renovation_unit_price",columnDefinition="decimal(10,2) COMMENT '装修单价'")
    private Double renovationUnitPrice;

    @Column(name="decorate_cost",columnDefinition="decimal(10,2) COMMENT '装修施工'")
    private Double decorateCost;

    @Column(name="business_screen",columnDefinition="decimal(10,2) COMMENT '商业展屏'")
    private Double businessScreen;

    @Column(name="furniture",columnDefinition="decimal(10,2) COMMENT '家具'")
    private Double furniture ;

    @Column(name="light_box",columnDefinition="decimal(10,2) COMMENT '标牌和灯箱'")
    private Double lightBox ;

    @Column(name="process_manage",columnDefinition="decimal(10,2) COMMENT '过程管理'")
    private Double processManage ;

    @Column(name="process_manage_surcharge",columnDefinition="decimal(10,2) COMMENT '过程管理额外'")
    private Double processManageSurcharge ;

    @Column(name="whole_process_manage_surcharge",columnDefinition="decimal(10,2) COMMENT '全过程管理额外'")
    private Double wholeProcessManageSurcharge ;

    @Column(name="air_conditioner",columnDefinition="decimal(10,2) COMMENT '空调设备'")
    private Double airConditioner;

    @Column(name="air_conditioner_surcharge",columnDefinition="decimal(10,2) COMMENT '空调设备额外'")
    private Double airConditionerSurcharge;

    @Column(name="design",columnDefinition="decimal(10,2) COMMENT '设计'")
    private Double design;

    @Column(name="total",columnDefinition="decimal(10,2) COMMENT '单店总装修花销'")
    private Double total;

    @Column(name="amortize_month",columnDefinition="int(2) COMMENT '摊销月数'")
    private Integer amortizeMonth;

    @Column(name="amortize_money",columnDefinition="decimal(10,2) COMMENT '摊销金额'")
    private Double amortizeMoney;


    @Column(name="completed_date",columnDefinition="varchar(100) COMMENT '竣工日期'")
    private String completedDate;

    @Column(name="contract_date",columnDefinition="varchar(100) COMMENT '合同日期'")
    private String contractDate;

    @Column(name="expiration_contract",columnDefinition="int (2) COMMENT '装修合同是否有效 0 是 1 否'")
    private Integer expirationContract;




    public Double getWholeProcessManageSurcharge() {
        return wholeProcessManageSurcharge;
    }

    public void setWholeProcessManageSurcharge(Double wholeProcessManageSurcharge) {
        this.wholeProcessManageSurcharge = wholeProcessManageSurcharge;
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

    public String getDecorationCompany() {
        return decorationCompany;
    }

    public void setDecorationCompany(String decorationCompany) {
        this.decorationCompany = decorationCompany;
    }

    public Double getStructureAcreage() {
        return structureAcreage;
    }

    public void setStructureAcreage(Double structureAcreage) {
        this.structureAcreage = structureAcreage;
    }

    public Double getRenovationUnitPrice() {
        return renovationUnitPrice;
    }

    public void setRenovationUnitPrice(Double renovationUnitPrice) {
        this.renovationUnitPrice = renovationUnitPrice;
    }

    public Double getDecorateCost() {
        return decorateCost;
    }

    public void setDecorateCost(Double decorateCost) {
        this.decorateCost = decorateCost;
    }

    public Double getBusinessScreen() {
        return businessScreen;
    }

    public void setBusinessScreen(Double businessScreen) {
        this.businessScreen = businessScreen;
    }

    public Double getFurniture() {
        return furniture;
    }

    public void setFurniture(Double furniture) {
        this.furniture = furniture;
    }

    public Double getLightBox() {
        return lightBox;
    }

    public void setLightBox(Double lightBox) {
        this.lightBox = lightBox;
    }

    public Double getProcessManage() {
        return processManage;
    }

    public void setProcessManage(Double processManage) {
        this.processManage = processManage;
    }

    public Double getProcessManageSurcharge() {
        return processManageSurcharge;
    }

    public void setProcessManageSurcharge(Double processManageSurcharge) {
        this.processManageSurcharge = processManageSurcharge;
    }

    public Double getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(Double airConditioner) {
        this.airConditioner = airConditioner;
    }

    public Double getAirConditionerSurcharge() {
        return airConditionerSurcharge;
    }

    public void setAirConditionerSurcharge(Double airConditionerSurcharge) {
        this.airConditionerSurcharge = airConditionerSurcharge;
    }

    public Double getDesign() {
        return design;
    }

    public void setDesign(Double design) {
        this.design = design;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getAmortizeMonth() {
        return amortizeMonth;
    }

    public void setAmortizeMonth(Integer amortizeMonth) {
        this.amortizeMonth = amortizeMonth;
    }

    public Double getAmortizeMoney() {
        return amortizeMoney;
    }

    public void setAmortizeMoney(Double amortizeMoney) {
        this.amortizeMoney = amortizeMoney;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getControlCode() {
        return controlCode;
    }

    public void setControlCode(String controlCode) {
        this.controlCode = controlCode;
    }


    public Integer getExpirationContract() {
        return expirationContract;
    }

    public void setExpirationContract(Integer expirationContract) {
        this.expirationContract = expirationContract;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
