package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:租房合同信息
 * @Author: gbl
 * @CreateDate: 2018/9/17 16:03
 */
@Entity
@Table(name="t_cost_rent_contract")
public class CostRentContract extends DataEntity {


    @Column(name="cityName",columnDefinition="varchar(100) COMMENT '城市名称'")
    private String cityName;

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="structure_acreage",columnDefinition="decimal(10,2) COMMENT '建筑面积'")
    private Double structureAcreage;

    @Column(name="lease_unit_price",columnDefinition="decimal(10,2) COMMENT '租赁单价'")
    private Double leaseUnitPrice;

    @Column(name="deposit",columnDefinition="decimal(10,2) COMMENT '押金'")
    private Double deposit;

    @Column(name="agency_fee",columnDefinition="decimal(10,2) COMMENT '中介费'")
    private Double agencyFee;

    @Column(name="free_lease_start_date",columnDefinition="varchar(12) COMMENT '起租日 含免租期'")
    private String  freeLeaseStartDate;

    @Column(name="lease_start_date",columnDefinition="varchar(12) COMMENT '起租日 免租期截止）'")
    private String  leaseStartDate;

    @Column(name="lease_stop_date",columnDefinition="varchar(12) COMMENT '到期日'")
    private String  leaseStopDate;

    @Column(name="contract_grand_total",columnDefinition="decimal(20,2) COMMENT '合同总金额'")
    private Double contractGrandTotal;

    @Column(name="rental_month",columnDefinition="decimal(10,2) COMMENT '每月租金 '")
    private Double rentalMonth;

    @Column(name="first_year_rent",columnDefinition="decimal(20,2) COMMENT '第一年租金'")
    private Double firstYearRent;

    @Column(name="second_year_rent",columnDefinition="decimal(20,2) COMMENT '第二年租金'")
    private Double secondYearRent;

    @Column(name="third_year_rent",columnDefinition="decimal(20,2) COMMENT '第三年租金'")
    private Double thirtYearRent;

    @Column(name="fourth_year_rent",columnDefinition="decimal(20,2) COMMENT '第四年租金'")
    private Double fourthYearRent;

    @Column(name="fifth_year_rent",columnDefinition="decimal(20,2) COMMENT '第五年租金'")
    private Double fifthYearRent;

    @Column(name="expiration_contract",columnDefinition="int(2) COMMENT '合同期满 0否 1：是'")
    private Integer expirationContract;


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

    public Double getStructureAcreage() {
        return structureAcreage;
    }

    public void setStructureAcreage(Double structureAcreage) {
        this.structureAcreage = structureAcreage;
    }

    public Double getLeaseUnitPrice() {
        return leaseUnitPrice;
    }

    public void setLeaseUnitPrice(Double leaseUnitPrice) {
        this.leaseUnitPrice = leaseUnitPrice;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(Double agencyFee) {
        this.agencyFee = agencyFee;
    }

    public String getFreeLeaseStartDate() {
        return freeLeaseStartDate;
    }

    public void setFreeLeaseStartDate(String freeLeaseStartDate) {
        this.freeLeaseStartDate = freeLeaseStartDate;
    }

    public String getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public String getLeaseStopDate() {
        return leaseStopDate;
    }

    public void setLeaseStopDate(String leaseStopDate) {
        this.leaseStopDate = leaseStopDate;
    }

    public Double getContractGrandTotal() {
        return contractGrandTotal;
    }

    public void setContractGrandTotal(Double contractGrandTotal) {
        this.contractGrandTotal = contractGrandTotal;
    }

    public Double getFirstYearRent() {
        return firstYearRent;
    }

    public void setFirstYearRent(Double firstYearRent) {
        this.firstYearRent = firstYearRent;
    }

    public Double getSecondYearRent() {
        return secondYearRent;
    }

    public void setSecondYearRent(Double secondYearRent) {
        this.secondYearRent = secondYearRent;
    }

    public Double getThirtYearRent() {
        return thirtYearRent;
    }

    public void setThirtYearRent(Double thirtYearRent) {
        this.thirtYearRent = thirtYearRent;
    }

    public Double getFourthYearRent() {
        return fourthYearRent;
    }

    public void setFourthYearRent(Double fourthYearRent) {
        this.fourthYearRent = fourthYearRent;
    }

    public Double getFifthYearRent() {
        return fifthYearRent;
    }

    public void setFifthYearRent(Double fifthYearRent) {
        this.fifthYearRent = fifthYearRent;
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

    public Double getRentalMonth() {
        return rentalMonth;
    }

    public void setRentalMonth(Double rentalMonth) {
        this.rentalMonth = rentalMonth;
    }
}
