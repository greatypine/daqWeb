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

    @Column(name = "model_name",length=1000)
    private String model_name;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }
}
