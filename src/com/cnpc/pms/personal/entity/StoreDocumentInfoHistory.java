package com.cnpc.pms.personal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

@Table
@Entity(name = "t_store_document_info_history")
public class StoreDocumentInfoHistory extends DataEntity {
	// 门店id
	@Column(name = "store_id")
	private Long store_id;
	// 功能方案提交日期
	@Column(name = "submit_date")
	private String submit_date;
	// 功能方案通过日期
	@Column(name = "audit_date")
	private String audit_date;
	// 装修进场日期
	@Column(name = "enter_date")
	private String enter_date;
	// 装修竣工日期
	@Column(name = "enter_end_date")
	private String enter_end_date;
	// 证照内容
	@Column(name = "card_content", length = 200)
	private String card_content;
	// 审核状态
	@Column(name = "audit_status")
	private Integer audit_status;
	// 流程id
	@Column(name = "work_id", length = 255)
	private String work_id;

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	public String getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(String audit_date) {
		this.audit_date = audit_date;
	}

	public String getEnter_date() {
		return enter_date;
	}

	public void setEnter_date(String enter_date) {
		this.enter_date = enter_date;
	}

	public String getEnter_end_date() {
		return enter_end_date;
	}

	public void setEnter_end_date(String enter_end_date) {
		this.enter_end_date = enter_end_date;
	}

	public String getCard_content() {
		return card_content;
	}

	public void setCard_content(String card_content) {
		this.card_content = card_content;
	}

	public Integer getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
	}

	public String getWork_id() {
		return work_id;
	}

	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}

}
