package com.cnpc.pms.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;

/**
 * 公告申请门店
 * @author gbl
 *
 */
@Entity
@Table(name="t_notice_apply_store")
public class NoticeApplyStore extends DataEntity{

	@Column(name="noticeNo")
	private String noticeNo;
	
	@Column(name="storeNo")
	private String storeNo;
    
	@Column(name="storeId")
	private long storeId;
	
	@Column(name="storeName")
	private String storeName;
	
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

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	
}
