package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:租金成本
 * @Author: gbl
 * @CreateDate: 2018/8/10 16:41
 */
@Entity
@Table(name="t_cost_rent")
public class CostRent extends DataEntity {

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="storeName",columnDefinition="varchar(100) COMMENT '门店名称'")
    private String storeName;

    @Column(name="addr",columnDefinition="varchar(200) COMMENT '门店地址'")
    private String addr;

    @Column(name="contract_grand_total",columnDefinition="decimal(20,2) COMMENT '合同总金额'")
    private Double contractGrandTotal;

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


    @Column(name="structure_acreage",columnDefinition="decimal(10,2) COMMENT '建筑面积'")
    private Double structureAcreage;

    @Column(name="lease_unit_price",columnDefinition="decimal(10,2) COMMENT '租赁单价'")
    private Double leaseUnitPrice;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private Integer year;

    @Column(name="deposit",columnDefinition="decimal(10,2) COMMENT '押金'")
    private Double deposit;

    @Column(name="agency_fee",columnDefinition="decimal(10,2) COMMENT '中介费'")
    private Double agencyFee;

    @Column(name="property_fee",columnDefinition="decimal(10,2) COMMENT '物业费/月'")
    private Double propertyFee;

    @Column(name="property_deadline",columnDefinition="int(2) COMMENT '物业期限/月 '")
    private Integer propertyDeadline;


    @Column(name="free_lease_start_date",columnDefinition="varchar(12) COMMENT '起租日 含免租期'")
    private String  freeLeaseStartDate;


    @Column(name="lease_start_date",columnDefinition="varchar(12) COMMENT '起租日 免租期截止）'")
    private String  leaseStartDate;

    @Column(name="lease_stop_date",columnDefinition="varchar(12) COMMENT '到期日'")
    private String  leaseStopDate;

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getPropertyDeadline() {
        return propertyDeadline;
    }

    public void setPropertyDeadline(Integer propertyDeadline) {
        this.propertyDeadline = propertyDeadline;
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

    public Double getPropertyFee() {
        return propertyFee;
    }

    public void setPropertyFee(Double propertyFee) {
        this.propertyFee = propertyFee;
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

    public Double getStructureAcreage() {
        return structureAcreage;
    }

    public void setStructureAcreage(Double structureAcreage) {
        this.structureAcreage = structureAcreage;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
