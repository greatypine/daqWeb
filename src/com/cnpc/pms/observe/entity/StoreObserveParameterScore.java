package com.cnpc.pms.observe.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by h on 2018/8/6.
 */
@Entity
@Table(name = "t_observe_parameter_score")
public class StoreObserveParameterScore extends DataEntity {

    @Column(name="store_id",columnDefinition="bigint(11) COMMENT '门店id'")
    private Long  store_id;

    @Column(name="store_name",columnDefinition="varchar(45) COMMENT '门店名称'")
    private String store_name;

    @Column(name="city_name",columnDefinition="varchar(45) COMMENT '城市名称'")
    private String city_name;

    @Column(name="observe_month",columnDefinition="varchar(45) COMMENT '明查月份'")
    private String observe_month;

    @Column(name="observe_person",columnDefinition="varchar(45) COMMENT '明查人'")
    private String observe_person;

    @Column(name="buckle_points_combined",columnDefinition="varchar(45) COMMENT '扣分合计'")
    private String buckle_points_combined;

    @Column(name="observe_question_number",columnDefinition="varchar(45) COMMENT '问题数量'")
    private  String observe_question_number;

    @Column(name="storeno",columnDefinition="varchar(45) COMMENT '门店编号'")
    private String storeno;

    @Column(name="points_combined",columnDefinition="varchar(45) COMMENT '明查得分'")
    private String points_combined;

    @Column(name="observe_date",columnDefinition="varchar(45) COMMENT '本次明查时间'")
    private String observe_date;

    @Column(name="observe_store_no",columnDefinition="varchar(45) COMMENT '明查专用门店编号'")
    private String observe_store_no;

    @Column(name="sk_name",columnDefinition="varchar(45) COMMENT '店长名'")
    private String sk_name;

    @Column(name="rm_name",columnDefinition="varchar(45) COMMENT '运营经理名'")
    private String rm_name;


    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getObserve_month() {
        return observe_month;
    }

    public void setObserve_month(String observe_month) {
        this.observe_month = observe_month;
    }

    public String getObserve_person() {
        return observe_person;
    }

    public void setObserve_person(String observe_person) {
        this.observe_person = observe_person;
    }

    public String getBuckle_points_combined() {
        return buckle_points_combined;
    }

    public void setBuckle_points_combined(String buckle_points_combined) {
        this.buckle_points_combined = buckle_points_combined;
    }

    public String getObserve_question_number() {
        return observe_question_number;
    }

    public void setObserve_question_number(String observe_question_number) {
        this.observe_question_number = observe_question_number;
    }

    public String getStoreno() {
        return storeno;
    }

    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }

    public String getPoints_combined() {
        return points_combined;
    }

    public void setPoints_combined(String points_combined) {
        this.points_combined = points_combined;
    }

    public String getObserve_date() {
        return observe_date;
    }

    public void setObserve_date(String observe_date) {
        this.observe_date = observe_date;
    }

    public String getObserve_store_no() {
        return observe_store_no;
    }

    public void setObserve_store_no(String observe_store_no) {
        this.observe_store_no = observe_store_no;
    }

    public String getSk_name() {
        return sk_name;
    }

    public void setSk_name(String sk_name) {
        this.sk_name = sk_name;
    }

    public String getRm_name() {
        return rm_name;
    }

    public void setRm_name(String rm_name) {
        this.rm_name = rm_name;
    }
}
