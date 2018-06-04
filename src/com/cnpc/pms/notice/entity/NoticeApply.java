package com.cnpc.pms.notice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;

/**
 * 公告申请表
 * @author gbl
 *
 */
@Entity
@Table(name = "t_notice_apply")
public class NoticeApply extends DataEntity{
	
	
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
	
	@Column(name="checkStatus",length=2)
	private int checkStatus;//审核状态 0：待审批 1：审批通过 2：驳回重新编辑 -1：审批不通过（不准发布）
	
	@Column(name="cityes")
	private String cityes;
	
	@Column(name="stores")
	private String stores;
	
	@Column(name="zw")
	private String zw;
	
	@Column(name="filePath",length=200)
	private String filePath;//附件路径
	
	@Column(name="fileName",length=50)
	private String fileName;
	
	@Column(name="remark",length=200)
	private String remark;//备注

	@Column(name="checkDate")
	private Date checkDate;
	
	@Column(name="checkUserId")
	private Long checkUserId;
	
	@Column(name="checkUserName")
	private String checkUserName;

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

	public User getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(User receiveUser) {
		this.receiveUser = receiveUser;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Long getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	
}
