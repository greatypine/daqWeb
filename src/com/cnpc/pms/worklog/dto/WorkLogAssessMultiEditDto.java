package com.cnpc.pms.worklog.dto;

/**
 * 一次修改多条工作日志打分记录的DTO
 * @author liujunsong
 *
 */
public class WorkLogAssessMultiEditDto {
	/**
	 * ids代表要打分的多个工作日志ID,中间用逗号分隔
	 */
	private String ids;
	/**
	 * values代表具体的打分记录,中间用逗号进行分割
	 */
	private String values;
	
	//GetSet方法定义
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
}
