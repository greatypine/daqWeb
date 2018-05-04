package com.cnpc.pms.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
/**
 * 
 * @author gbl
 * 公告
 */
@Entity
@Table(name = "t_notice")
public class Notice extends DataEntity{
	private static final long serialVersionUID = 1L;

	@Column(name = "title",length=255)
	private String title;//标题
	
	@Column(name = "content",length=2000)
	private String content;//内容
	
	@Column(name="noticeNo",length=20)
	private String noticeNo;//编号
	
	@Column(name="releaseUnit")
	private String releaseUnit;//发布单位
	
	@Column(name="type",length=1)
	private Integer type;//公告类型 1：事务 2：业务
	
	@Column(name="grade",length=1)
	private Integer grade;//公告等级  通知方式高级 1：国安数据APP 和短信通知 中级2：国安APP通知  低级3：不通知
	
	@Transient
	private User receiveUser;
	
	@Column(name="cityes")
	private String cityes;
	
	@Column(name="stores")
	private String stores;
	
	@Column(name="zw")
	private String zw;
	
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getReleaseUnit() {
		return releaseUnit;
	}

	public void setReleaseUnit(String releaseUnit) {
		this.releaseUnit = releaseUnit;
	}

	public String getCityes() {
		return cityes;
	}

	public void setCityes(String cityes) {
		this.cityes = cityes;
	}

	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	
	
	
}