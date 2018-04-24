package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

/**
 * 待办信息提示
 * 
 * @author jrn
 * 
 */
@Entity
@Table(name = "WF_ToDoMemo")

public class WFToDoMemo extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 528061844262036349L;

	/**
	 * 流程实例Id
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;

	/**
	 * 发送日期
	 */
	@Column(name = "sendDate")
	private Date sendDate;

	/**
	 * 消息内容
	 */
	@Column(name = "contents")
	private String contents;

	/**
	 * 接收人Id
	 */
	@Column(name = "receiverId")
	private Long receiverId;

	/**
	 * 消息状态1已读 0未读
	 */
	@Column(name = "state")
	private Long state;

	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

}
