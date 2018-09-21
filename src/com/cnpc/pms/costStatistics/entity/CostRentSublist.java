package com.cnpc.pms.costStatistics.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.entity
 * @Description:租金成本子表
 * @Author: gbl
 * @CreateDate: 2018/8/22 9:34
 */
@Entity
@Table(name="t_cost_rent_sublist")
public class CostRentSublist extends DataEntity {

    @Column(name="storeNo",columnDefinition="varchar(100) COMMENT '门店编号'")
    private String storeNo;

    @Column(name="year",columnDefinition="int(4) COMMENT '年份'")
    private int year;

    @Column(name="rent",columnDefinition="decimal(10,2) COMMENT '租金'")
    private Double rent;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }
}
