package com.cnpc.pms.worklog.dto;

import java.util.Date;

/**
 * 工作日志打分时抽取用的DTO对象
 * 
 * @author liujunsong
 * 
 */
public class WorkLogAssessSelectDto {
	/**
	 * 抽取开始日期
	 */
	private Date beginDate;
	/**
	 * 抽取结束日期
	 */
	private Date endDate;

	/**
	 * 抽取比例
	 */
	private double selectRate;
	/**
	 * 要抽取的部门的列表的Id的组合,多个部门之间用逗号进行分割
	 * <p>
	 * 例如：(001,002)
	 */
	private String depts;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getSelectRate() {
		return selectRate;
	}

	public void setSelectRate(double selectRate) {
		this.selectRate = selectRate;
	}

	public String getDepts() {
		return depts;
	}

	public void setDepts(String depts) {
		this.depts = depts;
	}

	
}
