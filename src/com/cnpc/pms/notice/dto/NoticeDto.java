package com.cnpc.pms.notice.dto;

import javax.persistence.Column;

public class NoticeDto {

	
	
	private Long id;
	
	private String title;//标题
	
	private String content;//内容
	
	private String noticeNo;//编号
	
	private String releaseUnit;//发布单位
	
	private Integer type;//公告类型 1：事务 2：业务
	
	private Integer grade;//公告等级  通知方式高级 1：国安数据APP 和短信通知 中级2：国安APP通知  低级3：不通知

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getReleaseUnit() {
		return releaseUnit;
	}

	public void setReleaseUnit(String releaseUnit) {
		this.releaseUnit = releaseUnit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
}
