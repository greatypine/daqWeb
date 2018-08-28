package com.cnpc.pms.observe.dto;

/**
 * Created by h on 2018/8/2.
 */
public class ObserveDTO {
    private String check_item_id;
    private Long model_id;
    private String observe_content;
    private String point_deduction_standard;
    private String check_details_id;
    private String model_name;
    private String add_or_edit;
    private String city_name;
    private String store_name;
    private String storeno;
    private Long store_id;
    private String observe_month;
    private Integer status;
    private Integer order_no;

    /** 功能节点主键. */
    private Long id;

    /** 功能节点名称,用于列表查询. */
    private String activityName;

    /** 功能节点名称,用于显示功能菜单树. */
    private String name;

    /** url显示位置. */
    private String target = "appFrame";

    /** 功能节点编码 与I2的activityID对应的字段. */
    private String activityCode;

    /** 功能节点的父节点编码. */
    private Long parentCode;

    /** 功能节点所属模块 module=1 招标模块 module=2 专家模块. */
    private String module;

    /**
     * 功能节点权限控制类型 功能树的目录节点 功能树的非目录节点 默认的功能节点 功能权限的菜单组 type=0 查看权限的功能节点 type=1
     * 修改权限的功能节点.
     */
    private int type;

    /** 功能节点权限控制扩展. */
    private String typeExt;

    /** 功能节点实际跳转路径. */
    private String url;

    /** 功能节点图标. */
    private String icon;

    /** The checked. */
    private boolean checked;
    /** The version. */
    private Long version;
    /** 备注 */
    private String remark;
    /** The is parent. */
    public boolean isParent;

    /** 排序字段. */
    private Integer orderNo;

    public String getCheck_item_id() {
        return check_item_id;
    }

    public void setCheck_item_id(String check_item_id) {
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

    public String getCheck_details_id() {
        return check_details_id;
    }

    public void setCheck_details_id(String check_details_id) {
        this.check_details_id = check_details_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeExt() {
        return typeExt;
    }

    public void setTypeExt(String typeExt) {
        this.typeExt = typeExt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getAdd_or_edit() {
        return add_or_edit;
    }

    public void setAdd_or_edit(String add_or_edit) {
        this.add_or_edit = add_or_edit;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStoreno() {
        return storeno;
    }

    public void setStoreno(String storeno) {
        this.storeno = storeno;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getObserve_month() {
        return observe_month;
    }

    public void setObserve_month(String observe_month) {
        this.observe_month = observe_month;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        this.order_no = order_no;
    }
}
