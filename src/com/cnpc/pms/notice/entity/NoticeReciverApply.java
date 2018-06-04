package com.cnpc.pms.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 公告接收人申请表
 * @author gbl
 *
 */
@Entity
@Table(name = "t_notice_reciver_apply")
public class NoticeReciverApply extends DataEntity{
    private static final long serialVersionUID = 1L;
	
	@Column(name="noticeNo",length=20)
	private String noticeNo;//编号
	
	@Column(name="storeNo",length=45)
	private String storeNo;//门店编码
	
	@Column(name="storeId",length=20)
	private Long storeId;//门店ID
	
	@Column(name="employeeNo",length=100)
	private String employeeNo;//门店员工编号

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	
	
	

}
