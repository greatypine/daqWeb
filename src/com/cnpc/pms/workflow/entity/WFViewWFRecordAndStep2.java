/**
 * 
 */
package com.cnpc.pms.workflow.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

/**
 * 用于显示流程执行过程的视图,将流程已执行部分和未执行部分混合起来展示<br>
 * recordstep2代表这个视图是第2版本
 * @author liujunsong
 *
 */
@Entity
@Table(name = "view_wf_recordstep2")

public class WFViewWFRecordAndStep2 implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798729038088550175L;
	@Id
	@Column(name = "id")
	private Long id;



	/**
	 * 步骤名称
	 */
	@Column(name = "stepName")
	private String stepName;// 步骤ID

	/**
	 * 
	 */
	@Column(name = "posId")
	private String posId;
	/**
	 * 处理人
	 */
	@Column(name = "posName2")
	private String posName2;
	/**
	 * 处理结果
	 */
	@Column(name = "result")
	private String result;
	/**
	 * 处理意见
	 */
	@Column(name = "advice")
	private String advice;
	/**
	 * 处理时间
	 */
	@Column(name = "time")
	private Date time;
	/**
	 * 流程实例Id
	 */
	@Column(name = "flowInstanceId")
	private Long flowInstanceId;

	// public Long getNo() {
	// return no;
	// }

	// public void setNo(Long no) {
	// this.no = no;
	// }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFlowInstanceId() {
		return flowInstanceId;
	}

	public void setFlowInstanceId(Long flowInstanceId) {
		this.flowInstanceId = flowInstanceId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date date) {
		this.time = date;
	}

	public String getPosName2() {
		return posName2;
	}

	public void setPosName2(String posName) {
		this.posName2 = posName;
	}



}
