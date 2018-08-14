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
    private double leaseUnitPrice;

    @Column(name="deposit",columnDefinition="decimal(10,2) COMMENT '押金'")
    private double deposit;

    @Column(name="agency_fee",columnDefinition="decimal(10,2) COMMENT '中介费'")
    private double agencyFee;

    @Column(name="property_fee",columnDefinition="decimal(10,2) COMMENT '物业费/年'")
    private double propertyFee;

    @Column(name="property_deadline",columnDefinition="int(2) COMMENT '物业期限/月 '")
    private Integer propertyDeadline;

    @Column(name="free_lease_start_date",columnDefinition="datetime(0,0) COMMENT '起租日（含免租期）'")
    private Date freeLeaseStartDate;

    @Column(name="lease_start_date",columnDefinition="datetime(0,0) COMMENT '起租日（免租期截止日）'")
    private Date leaseStartDate;

    @Column(name="lease_stop_date",columnDefinition="datetime(0,0) COMMENT '到期日'")
    private Date leaseStopDate;

}
