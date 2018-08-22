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
    private String contractGrandTotal;



    @Column(name="structure_acreage",columnDefinition="decimal(10,2) COMMENT '建筑面积'")
    private String structureAcreage;

    @Column(name="lease_unit_price",columnDefinition="decimal(10,2) COMMENT '租赁单价'")
    private Double leaseUnitPrice;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private int year;

    @Column(name="deposit",columnDefinition="decimal(10,2) COMMENT '押金'")
    private Double deposit;

    @Column(name="agency_fee",columnDefinition="decimal(10,2) COMMENT '中介费'")
    private Double agencyFee;

    @Column(name="property_fee",columnDefinition="decimal(10,2) COMMENT '物业费/月'")
    private Double propertyFee;

    @Column(name="property_deadline",columnDefinition="int(2) COMMENT '物业期限/月 '")
    private Integer propertyDeadline;

    //,columnDefinition="COMMENT '起租日 含免租期'"
    @Column(name="free_lease_start_date")
    private Date freeLeaseStartDate;

    //,columnDefinition="COMMENT '起租日 免租期截止）'"
    @Column(name="lease_start_date")
    private Date leaseStartDate;
    //,columnDefinition="COMMENT '到期日'"
    @Column(name="lease_stop_date")
    private Date leaseStopDate;

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

    public String getContractGrandTotal() {
        return contractGrandTotal;
    }

    public void setContractGrandTotal(String contractGrandTotal) {
        this.contractGrandTotal = contractGrandTotal;
    }

    public String getStructureAcreage() {
        return structureAcreage;
    }

    public void setStructureAcreage(String structureAcreage) {
        this.structureAcreage = structureAcreage;
    }

    public Integer getPropertyDeadline() {
        return propertyDeadline;
    }

    public void setPropertyDeadline(Integer propertyDeadline) {
        this.propertyDeadline = propertyDeadline;
    }

    public Date getFreeLeaseStartDate() {
        return freeLeaseStartDate;
    }

    public void setFreeLeaseStartDate(Date freeLeaseStartDate) {
        this.freeLeaseStartDate = freeLeaseStartDate;
    }

    public Date getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(Date leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public Date getLeaseStopDate() {
        return leaseStopDate;
    }

    public void setLeaseStopDate(Date leaseStopDate) {
        this.leaseStopDate = leaseStopDate;
    }

    public Double getLeaseUnitPrice() {
        return leaseUnitPrice;
    }

    public void setLeaseUnitPrice(Double leaseUnitPrice) {
        this.leaseUnitPrice = leaseUnitPrice;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
}
