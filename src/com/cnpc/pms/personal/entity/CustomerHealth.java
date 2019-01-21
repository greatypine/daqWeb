package com.cnpc.pms.personal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;
@Entity
@Table(name = "t_customer_health")
public class CustomerHealth extends DataEntity{
	//附近门店编号
	@Column(length = 255, name = "store_no")
	private String store_no;
	
	//附近门店id
	@Column(length = 255, name = "store_id")
	private Long store_id;
	
	//附近门店名称
	@Column(length = 255, name = "store_name")
	private String store_name;
	
	//关联用户id
	@Column(length = 255, name = "customer_id")
	private Long customer_id;
	
	//是否是社员
	@Column(length = 255, name = "is_member")
	private String is_member;
	
	//职业状态
	@Column(length = 255, name = "work_status")
	private String work_status;
	
	//本人有血糖类病症
	@Column(length = 255, name = "me_is_diabetes")
	private String me_is_diabetes;
	
	//家人有血糖类病症
	@Column(length = 255, name = "family_is_diabetes")
	private String family_is_diabetes;
	
	//心血管疾病（多选）
	@Column(length = 255, name = "is_cardiovascular")
	private String is_cardiovascular;
	
	//医保报销额度
	@Column(length = 255, name = "medical_insurance")
	private String medical_insurance;
	
	//每周有适当运动
	@Column(length = 255, name = "is_weekly_activity")
	private String is_weekly_activity;
	
	//每周有测血糖的习惯
	@Column(length = 255, name = "weekly_measure_diabetes")
	private String weekly_measure_diabetes;
	
	//症状（可多选）
	@Column(length = 255, name = "diabetes_symptom")
	private String diabetes_symptom;
	
	//糖尿病并发症
	@Column(length = 255, name = "diabetes_complications")
	private String diabetes_complications;
	
	//血糖类医生的专业指导
	@Column(length = 255, name = "diabetes_doctor_advice")
	private String diabetes_doctor_advice;
	
	//糖尿病治疗开销额度
	@Column(length = 255, name = "diabetes_treatment_quota")
	private String diabetes_treatment_quota;
	
	//感兴趣的身体健康管理方法（可多选）
	@Column(length = 255, name = "interested_health_means")
	private String interested_health_means;
	
	//糖尿病病程
	@Column(length = 255, name = "diabetes_history")
	private String diabetes_history;
	
	//糖尿病的治疗方式
	@Column(length = 255, name = "diabetes_treatment_means")
	private String diabetes_treatment_means;
	
	//目前有无视力受损
	@Column(length = 255, name = "is_vision_damage")
	private String is_vision_damage;
	
	//想获得糖尿病相关健康教育
	@Column(length = 255, name = "diabetes_health_study")
	private String diabetes_health_study;
	
	//如何控制血糖（多选)
	@Column(length = 255, name = "diabetes_control")
	private String diabetes_control;
	
	//身高(CM)
	@Column(length = 255, name = "height")
	private Float height;
	
	//体重(KG)
	@Column(length = 255, name = "weight")
	private Float weight;
	
	//身体状况评估
	@Column(length = 255, name = "health_degree")
	private String health_degree;
	
	//糖尿病类型
	@Column(length = 255, name = "diabetes_type")
	private String diabetes_type;
	
	//餐前血糖值是多少
	@Column(length = 255, name = "blood_glucose_before_meal")
	private Float blood_glucose_before_meal;
	
	//餐后血糖值是多少
	@Column(length = 255, name = "blood_glucose_after_meal")
	private Float blood_glucose_after_meal;
	
	//最近测量血糖距离现在时长
	@Column(length = 255, name = "last_time_measuring_blood_sugar")
	private String last_time_measuring_blood_sugar;
	
	//就医采取的付费方式
	@Column(length = 255, name = "treatment_pay_type")
	private String treatment_pay_type;
	
	//使用的降糖药品
	@Column(length = 255, name = "antidiabetic")
	private String antidiabetic;

	
	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getBlood_glucose_before_meal() {
		return blood_glucose_before_meal;
	}

	public void setBlood_glucose_before_meal(Float blood_glucose_before_meal) {
		this.blood_glucose_before_meal = blood_glucose_before_meal;
	}

	public Float getBlood_glucose_after_meal() {
		return blood_glucose_after_meal;
	}

	public void setBlood_glucose_after_meal(Float blood_glucose_after_meal) {
		this.blood_glucose_after_meal = blood_glucose_after_meal;
	}

	public String getHealth_degree() {
		return health_degree;
	}

	public void setHealth_degree(String health_degree) {
		this.health_degree = health_degree;
	}

	public String getDiabetes_type() {
		return diabetes_type;
	}

	public void setDiabetes_type(String diabetes_type) {
		this.diabetes_type = diabetes_type;
	}

	

	public String getLast_time_measuring_blood_sugar() {
		return last_time_measuring_blood_sugar;
	}

	public void setLast_time_measuring_blood_sugar(String last_time_measuring_blood_sugar) {
		this.last_time_measuring_blood_sugar = last_time_measuring_blood_sugar;
	}

	public String getTreatment_pay_type() {
		return treatment_pay_type;
	}

	public void setTreatment_pay_type(String treatment_pay_type) {
		this.treatment_pay_type = treatment_pay_type;
	}

	public String getAntidiabetic() {
		return antidiabetic;
	}

	public void setAntidiabetic(String antidiabetic) {
		this.antidiabetic = antidiabetic;
	}

	public String getStore_no() {
		return store_no;
	}

	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}

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

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getIs_member() {
		return is_member;
	}

	public void setIs_member(String is_member) {
		this.is_member = is_member;
	}

	public String getWork_status() {
		return work_status;
	}

	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}

	public String getMe_is_diabetes() {
		return me_is_diabetes;
	}

	public void setMe_is_diabetes(String me_is_diabetes) {
		this.me_is_diabetes = me_is_diabetes;
	}

	public String getFamily_is_diabetes() {
		return family_is_diabetes;
	}

	public void setFamily_is_diabetes(String family_is_diabetes) {
		this.family_is_diabetes = family_is_diabetes;
	}

	public String getIs_cardiovascular() {
		return is_cardiovascular;
	}

	public void setIs_cardiovascular(String is_cardiovascular) {
		this.is_cardiovascular = is_cardiovascular;
	}

	public String getMedical_insurance() {
		return medical_insurance;
	}

	public void setMedical_insurance(String medical_insurance) {
		this.medical_insurance = medical_insurance;
	}

	public String getIs_weekly_activity() {
		return is_weekly_activity;
	}

	public void setIs_weekly_activity(String is_weekly_activity) {
		this.is_weekly_activity = is_weekly_activity;
	}

	public String getWeekly_measure_diabetes() {
		return weekly_measure_diabetes;
	}

	public void setWeekly_measure_diabetes(String weekly_measure_diabetes) {
		this.weekly_measure_diabetes = weekly_measure_diabetes;
	}

	public String getDiabetes_symptom() {
		return diabetes_symptom;
	}

	public void setDiabetes_symptom(String diabetes_symptom) {
		this.diabetes_symptom = diabetes_symptom;
	}

	public String getDiabetes_complications() {
		return diabetes_complications;
	}

	public void setDiabetes_complications(String diabetes_complications) {
		this.diabetes_complications = diabetes_complications;
	}

	public String getDiabetes_doctor_advice() {
		return diabetes_doctor_advice;
	}

	public void setDiabetes_doctor_advice(String diabetes_doctor_advice) {
		this.diabetes_doctor_advice = diabetes_doctor_advice;
	}

	public String getDiabetes_treatment_quota() {
		return diabetes_treatment_quota;
	}

	public void setDiabetes_treatment_quota(String diabetes_treatment_quota) {
		this.diabetes_treatment_quota = diabetes_treatment_quota;
	}

	public String getInterested_health_means() {
		return interested_health_means;
	}

	public void setInterested_health_means(String interested_health_means) {
		this.interested_health_means = interested_health_means;
	}

	public String getDiabetes_history() {
		return diabetes_history;
	}

	public void setDiabetes_history(String diabetes_history) {
		this.diabetes_history = diabetes_history;
	}

	public String getDiabetes_treatment_means() {
		return diabetes_treatment_means;
	}

	public void setDiabetes_treatment_means(String diabetes_treatment_means) {
		this.diabetes_treatment_means = diabetes_treatment_means;
	}

	public String getIs_vision_damage() {
		return is_vision_damage;
	}

	public void setIs_vision_damage(String is_vision_damage) {
		this.is_vision_damage = is_vision_damage;
	}

	public String getDiabetes_health_study() {
		return diabetes_health_study;
	}

	public void setDiabetes_health_study(String diabetes_health_study) {
		this.diabetes_health_study = diabetes_health_study;
	}

	public String getDiabetes_control() {
		return diabetes_control;
	}

	public void setDiabetes_control(String diabetes_control) {
		this.diabetes_control = diabetes_control;
	}
	
	

	


}
