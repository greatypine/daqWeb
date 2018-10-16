package com.cnpc.pms.observe.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by gaoll on 2018/8/2.
 */
@Entity
@Table(name = "t_observe_model")
public class ObserveModel extends DataEntity{

    @Column(name = "model_name",columnDefinition="varchar(100) COMMENT '检查模块名'")
    private String model_name;

    @Column(name = "model_color",columnDefinition="varchar(100) COMMENT '检查模块区别色'")
    private String model_color;

    @Column(name = "order_no",columnDefinition="int(11) COMMENT '模块排序'")
    private Integer order_no;

    @Column(name = "remark",columnDefinition="varchar(1000) COMMENT '备注'")
    private String remark;



    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public Integer getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        this.order_no = order_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModel_color() {
        return model_color;
    }

    public void setModel_color(String model_color) {
        this.model_color = model_color;
    }
}
