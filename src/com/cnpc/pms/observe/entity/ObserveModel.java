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

    @Column(name = "order_no",columnDefinition="int(11) COMMENT '模块排序'")
    private Integer order_no;

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
}
