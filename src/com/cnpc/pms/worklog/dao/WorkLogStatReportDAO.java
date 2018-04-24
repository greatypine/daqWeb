package com.cnpc.pms.worklog.dao;

import java.util.Date;
import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.dto.WorkLogStatByBranchOrgDTO;


/**
 * 日志统计使用的DAO对象,负责同步刷新工作日志的统计信息
 * 
 * @author liujunsong
 * 
 */
public interface WorkLogStatReportDAO extends IDAO {
	/**
	 * 以年为维度，将这一年工作日志统计相关数据插入TB_WorkLog_StatReport
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
	 * 以月为维度，将这一年工作日志统计相关数据插入TB_WorkLog_StatReport
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void insertStatReportByMonth(String logDate);
	/**
	 * 以周为维度，将这一年工作日志统计相关数据插入TB_WorkLog_StatReport
	 * 
	 * @param logDate的格式为"yyyy"
	 * 
	 */
	public void insertStatReportByWeek(String logDate);
	
	/**
	 * 给出某个机关的id，统计该机关下所有的院用此方法
	 * 
	 * @param beginDate的格式为"yyyy-mm-dd"
	 * 
	 */
	public List<WorkLogStatByBranchOrgDTO> getList(String statType,String statDateType,Long jobType,Long statId,String beginDate);
	/**
	 * 给出某个院的id，统计该院下所有的所用此方法
	 * 
	 * @param beginDate的格式为"yyyy-mm-dd"
	 * 
	 */
	public List<WorkLogStatByBranchOrgDTO> getDeptList(String statType,String statDateType,Long jobType,Long statId,String beginDate);
	/**
	 * 给出某个所或室的id，统计该机构下所有的人此方法
	 * 
	 * @param beginDate的格式为"yyyy-mm-dd"
	 * 
	 */
	public List<WorkLogStatByBranchOrgDTO> getUserList(String statType,String statDateType,Long jobType,Long statId,String beginDate);
	
}
