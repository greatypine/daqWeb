package com.cnpc.pms.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 
 * @author gbl
 * 公告接受人
 */
@Entity
@Table(name = "t_notice_reciver")
public class NoticeReciver extends DataEntity{
	private static final long serialVersionUID = 1L;
	
	@Column(name="noticeNo",length=20)
	private String noticeNo;//编号
	
	@Column(name="storeNo",length=45)
	private String storeNo;//门店编码
	
	@Column(name="storeId",length=20)
	private Long storeId;//门店ID
	
	@Column(name="employeeNo",length=100)
	private String employeeNo;//门店员工编号
	
	@Column(name="isRead",length=2)
	private Integer isRead;//0:未读 1:已读
	
	@Column(name="readDate")
	private Date readDate;//阅读时间

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

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	 
	
}
