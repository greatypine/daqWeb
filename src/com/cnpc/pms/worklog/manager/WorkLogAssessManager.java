package com.cnpc.pms.worklog.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.worklog.dto.WorkLogAssessMultiEditDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessSelectDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessStatDto;
import com.cnpc.pms.worklog.entity.WorkLogAssess;

/**
 * 工作日志打分的管理接口定义
 * 
 * @author liujunsong
 * 
 */
public interface WorkLogAssessManager extends IManager {

	/**
	 * 根据给定的DTO对象,从工作日志用户日历表中,抽取指定比例的数据到评分记录表中去
	 * <li> 插入数据时要进行数据校验,同一用户,同一日期仅抽取一次,不重复抽取
	 * <li> 抽取以后要进行抽取数据的判断,如果判断是未填写日志,则自动打分为F
	 * <li>
	 * 
	 * @param dto
	 * @return 实际完成的抽取记录数 
	 * @throws Exception
	 */
	public Long addWorkLogAssess(WorkLogAssessSelectDto dto) throws Exception;

	/**
	 * 更新指定打分记录的打分情况，打分记录容许多次修改。<br>
	 * 如果是系统自动打分为F，则不可修改。<br>
	 * 支持批量打分，可以通过在前台多次循环调用后台此方法来实现。<br>
	 * 更新失败，抛出异常。
	 * 
	 * @param workAssess
	 * @return
	 * @throws Exception
	 */
	public WorkLogAssess saveWorkLogAssess(WorkLogAssess workAssess)
			throws Exception;
	
	/**
	 * 一次更新多条打分记录情况,由前台构建相应的对象传入进行处理.
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long saveMultiWorkLogAssess(WorkLogAssessMultiEditDto dto) throws Exception;

	/**
	 * 根据统计条件,对工作日志的打分情况进行统计计算展示。
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List statWorkLogAssess(WorkLogAssessStatDto dto) throws Exception;
	//统计查询
	public Map<String, Object> getWorkLogAssessStatistics(QueryConditions condition);
	public Map<String, Object> getWorkLogAssessSelectAndQuery(QueryConditions condition);
	public Map<String, Object> getWorkLogAssessStatisticsByWd(QueryConditions condition);
}
