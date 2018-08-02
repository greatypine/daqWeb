package com.cnpc.pms.observe.dto;

/**
 * Created by h on 2018/8/2.
 */
public class ObserveDTO {
    private String check_item_id;
    private String model_id;
    private String observe_content;
    private String point_deduction_standard;
    private String check_details_id;
    private String id;

    public String getCheck_item_id() {
        return check_item_id;
    }

    public void setCheck_item_id(String check_item_id) {
        this.check_item_id = check_item_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
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

    public String getCheck_details_id() {
        return check_details_id;
    }

    public void setCheck_details_id(String check_details_id) {
        this.check_details_id = check_details_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
