package com.cnpc.pms.observe.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by gaoll on 2018/8/2.
 */
@Entity
@Table(name = "t_observe_check_details")
public class CheckDetails extends DataEntity{

        @Column(name = "check_item_id",columnDefinition="bigint(11) COMMENT '检查项id'")
        private Long check_item_id;

        @Column(name = "model_id",length=11, columnDefinition="bigint(11) COMMENT '检查模块id'")
        private Long model_id;

        @Column(name = "observe_content",columnDefinition="varchar(2000) COMMENT '明查内容'")
        private String observe_content;

        @Column(name = "point_deduction_standard",columnDefinition="varchar(1000) COMMENT '扣分明细'")
        private String point_deduction_standard;

        public Long getCheck_item_id() {
                return check_item_id;
        }

        public void setCheck_item_id(Long check_item_id) {
                this.check_item_id = check_item_id;
        }

        public Long getModel_id() {
                return model_id;
        }

        public void setModel_id(Long model_id) {
                this.model_id = model_id;
        }

        public String getObserve_content() {
                return observe_content;
        }

        public void setObserve_content(String observe_content) {
                this.observe_content = observe_content;
        }

        public String getPoint_deduction_standard() {
                return point_deduction_standard;
        }

        public void setPoint_deduction_standard(String point_deduction_standard) {
                this.point_deduction_standard = point_deduction_standard;
        }
}
