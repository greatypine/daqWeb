package com.cnpc.pms.worklog.manager;

import com.cnpc.pms.base.manager.IManager;
/**
 * 工作日志统计物理存储Manager
 * 
 * @author czq
 * 
 */
public interface WorkLogStatReportManager extends IManager{
	/**
	 * 以年为维度，将工作日志统计相关数据插入TB_WorkLog_StatReport
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void insertStatReportByYear(String logDate);
	
	/**
	 * 将某年的工作日志统计相关数据删除
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void deleteStatReportByYear(String logDate);
	/**
	 * 以月为维度，将工作日志统计相关数据插入TB_WorkLog_StatReport
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void insertStatReportByMonth(String logDate);
	/**
	 * 以周为维度，将工作日志统计相关数据插入TB_WorkLog_StatReport
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void insertStatReportByWeek(String logDate);

}
