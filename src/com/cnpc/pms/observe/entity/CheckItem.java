package com.cnpc.pms.observe.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by gaoll on 2018/8/2.
 */
@Entity
@Table(name = "t_observe_check_item")
public class CheckItem  extends DataEntity {

    @Column(name = "model_id",length=1000)
    private String model_id;

    @Column(name = "item_name",length=1000)
    private String item_name;

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
