package com.cnpc.pms.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;
/**
 * 公告申请接收城市
 * @author gbl
 *
 */
@Entity
@Table(name = "t_notice_apply_city")
public class NoticeApplyCity extends DataEntity{
	
	@Column(name="noticeNo")
	private String noticeNo;
	
	@Column(name="cityCode")
	private String cityCode;
	
	@Column(name="cityName")
	private String cityName;

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	
}
