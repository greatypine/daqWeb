package com.cnpc.pms.worklog.dto;

import java.util.Date;

/**
 * 工作日志打分时统计用的DTO对象
 * 
 * @author liujunsong
 * 
 */
public class WorkLogAssessStatDto {
	/**
	 * 统计开始日期
	 */
	private Date beginDate;
	/**
	 * 统计结束日期
	 */
	private Date endDate;

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

	public String getDepts() {
		return depts;
	}

	public void setDepts(String depts) {
		this.depts = depts;
	}

	/**
	 * 要统计的部门的列表的Id的组合,多个部门之间用逗号进行分割
	 * <p>
	 * 例如：(001,002)
	 */
	private String depts;

}
