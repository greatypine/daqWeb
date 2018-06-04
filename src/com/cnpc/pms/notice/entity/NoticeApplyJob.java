package com.cnpc.pms.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.DataEntity;
/**
 * 公告申请职务
 * @author gbl
 *
 */
@Entity
@Table(name = "t_notice_apply_job")
public class NoticeApplyJob extends DataEntity{
	
	@Column(name="noticeNo")
	private String noticeNo;
	
	@Column(name="job")
	private String job;

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	
}
