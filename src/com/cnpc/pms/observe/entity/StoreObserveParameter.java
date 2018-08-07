package com.cnpc.pms.observe.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by h on 2018/8/5.
 */
@Entity
@Table(name = "t_observe_parameter")
public class StoreObserveParameter  extends DataEntity {

    @Column(name="store_id",columnDefinition="bigint(11) COMMENT '门店id'")
    private Long store_id;

    @Column(name="storeno",columnDefinition="varchar(45) COMMENT '门店编号'")
    private String storeno;

    @Column(name="observe_month",columnDefinition="varchar(45) COMMENT '明查月份'")
    private String observe_month;

    @Column(name="check_details_id",columnDefinition="bigint(11) COMMENT '对应门店明查明细表id'")
    private Long check_details_id;

    @Column(name="content_score",columnDefinition="varchar(45) COMMENT '对应项内容扣分'")
    private String content_score;

    @Column(name="points_deduction_description",columnDefinition="varchar(1000) COMMENT '扣分情况说明'")
    private String points_deduction_description;

    @Column(name="score_empno_empname",columnDefinition="varchar(1000) COMMENT '被扣分人员姓名、工号、扣分数'")
    private String score_empno_empname;

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getStoreno() {
        return storeno;
    }

    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }

    public String getObserve_month() {
        return observe_month;
    }

    public void setObserve_month(String observe_month) {
        this.observe_month = observe_month;
    }

    public Long getCheck_details_id() {
        return check_details_id;
    }

    public void setCheck_details_id(Long check_details_id) {
        this.check_details_id = check_details_id;
    }

    public String getContent_score() {
        return content_score;
    }

    public void setContent_score(String content_score) {
        this.content_score = content_score;
    }

    public String getPoints_deduction_description() {
        return points_deduction_description;
    }

    public void setPoints_deduction_description(String points_deduction_description) {
        this.points_deduction_description = points_deduction_description;
    }

    public String getScore_empno_empname() {
        return score_empno_empname;
    }

    public void setScore_empno_empname(String score_empno_empname) {
        this.score_empno_empname = score_empno_empname;
    }
}
