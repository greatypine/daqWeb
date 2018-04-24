/**
 * 
 */
package com.cnpc.pms.workflowdemo.dto;

import java.util.Date;

import com.cnpc.pms.base.dto.PMSDTO;

/**
 * 启动流程或者提交流程时前后台交互参数
 * 
 * @author jrn
 * 
 */
public class WFStepDto extends PMSDTO {
	private Long no;// 序号
	private String stepName;// 步骤ID
	private String posId;
	private String posName;

	private String result;
	private String advice;
	private Date time;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
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

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

}
