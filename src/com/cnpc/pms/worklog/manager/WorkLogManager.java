package com.cnpc.pms.worklog.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.dto.WorkLogDTO;
import com.cnpc.pms.worklog.dto.WorkLogStaAndQueryDTO;
import com.cnpc.pms.worklog.entity.WorkLog;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;

/**
 * 工作日志Manager
 * 
 * @author ws
 * 
 */
public interface WorkLogManager extends IManager {

	/**
	 * 保存工作日志,保存工作日志的时候需要同步更新用户日历表中的状态.
	 * <p>
	 * 更新逻辑如下:
	 * <li>1.针对是否上报字段,Select count(*) from TB_worklog where userid=? and
	 * to_char(logdate,'yyyymmdd'=? ,如果大于0,则为1,否则为0
	 * <li>2.针对是否及时上报字段,Select max(to_char(logdate,'yyyy-mm-dd')
	 * ,max(to_char(inputdate,'yyyy-mm-dd')) from tb_worklog, 如果两者均不为空,而且两者
	 * 相同,则为及时上报,设置为1,否则设置为0
	 * <li>3.针对工作小时数, Select sum(hours) from tb_worklog where userid=? 
	 * and to_char(logdate,'yyyy-mm-dd')=?,得到当日的汇总小时数
	 * 
	 * @param workLog
	 * @return
	 */
	/**
	 * 保存日志，并且去用户日历表修改相应的状态
	 * 
	 * @param WorkLogDTO
	 */
	public WorkLog saveWorkLog(WorkLogDTO workLogDTO) ;
	/**
	 * 只保存日志，不去用户日历表修改相应的状态
	 * 
	 * @param WorkLogDTO
	 */
	public WorkLog saveWorkLogOnly(WorkLogDTO workLogDTO) ;
	/**
	 * 日志提交时，批量更新抄送，并且去用户日历表里面更新相应的值
	 * 
	 * @param WorkLogDTO
	 */
	public void saveWorkLogCopy(WorkLogDTO workLogDTO) ;
	public WorkLog getWorkLog(String logDate);

	/**
	 * 逻辑删除工作日志,这个方法要废弃掉，不再使用
	 * 
	 * @param id
	 */
	public void deleteWorkLog(Long id);

	/**
	 * 物理删除工作日志，删除工作日志时要进行用户日志统计信息的更新操作，
	 * 业务逻辑同保存工作日志时的逻辑。
	 * 
	 * @param id
	 */
	public void deleteWorkLogReal(Long id);

	/**
	 * 根据主键获取工作日志
	 * 
	 * @param id
	 * @return
	 */
	public WorkLogDTO getWorkLogById(Long id);

	/**
	 * 验证用户输入的Email地址是否存在
	 * 
	 * @param emails
	 * @return
	 */
	public String validateUserEmailExists(String emails);

	/**
	 * 获取当前用户最后一次创建日志输入的抄送用户
	 * 
	 * @return
	 */
	public List<Map> getLastCreateWorkLogUsers();

	/**
	 * 通过外键leaveId获取到List<WorkLog>
	 * 
	 * @return
	 */
	public List<WorkLog> getWorkLogsList(Long id);

	/**
	 * 检测该日期是否存在
	 * 
	 * @return
	 */
	public boolean checkWorLogExists(String logDate);
	/**
	 * 检测该3天前该日期是否存在记录
	 * 
	 * @return
	 */
	public Boolean checkWorLogExistsThreeDays(String logDate);

	/**
	 * 通过单位属性获得院
	 * 
	 * @return
	 */
	public List<PurStruOrg> getPurStruOrgParent(String entityOrgFlag);

	/**
	 * 通过父节点ID获取到List<PurStruOrg>
	 * 
	 * @return
	 */
	public List<PurStruOrg> getPurStruOrgChildren(Long parent_id);

	/**
	 * 获取用户所在所ID
	 * 
	 * @return
	 */
	public Long getUserOrg();
	/**
	 * 返回用户信息
	 * 
	 * @return
	 */
	public User getUser();
	/**
	 * 根据用户Id(session)里面取和日志日期获取到recordstate状态
	 * 
	 * @return
	 */
	public String getRecordState(String logDate);
	/**
	 * 根据用户Id和日志日期获取到recordstate状态,返回提交日期
	 * 
	 * @return String('yyyy-mm-dd hh:ss')
	 */
	public String getRecordStateById(WorkLogDTO dto);
	/**
	 * 获取到本月所有已填的工作日志的日期，返回给前端，用于控制日历控件的样式
	 * 
	 * @return
	 */
	public List<String> getLogdatesList();
	/**
	 * 通过用户ID获取到本月所有已填的工作日志的日期，返回给前端，用于控制日历控件的样式
	 * 
	 * @return
	 */
	public List<String> getLogdatesListByUID(Long userId);
	/**
	 * 获取本日已填日志的次数
	 * @param('yyyy-mm-dd')
	 * @return
	 */
	public int getCounts(String logDate);
	/**
	 * 获取日志最早的填报日期
	 * @param('yyyy-mm-dd')
	 * @return
	 */
	public String getInputDate(String logDate); 
	public WorkLogUserCalendar getWorkLogUserCalendar(String logDate); 
	/**
	 * 工作日志查询（分配给机关用）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogQueryByKYGLC(QueryConditions condition);
	/**
	 * 工作日志查询（分配给所领导用）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogQueryBySLD(QueryConditions condition);
	/**
	 * 工作日志查询（分配给室主任用）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogQueryBySZR(QueryConditions condition);
	/**
	 * 工作日志查询（分配给项目经理用）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogQueryByXMXG(QueryConditions condition);
	/**
	 * 统计研究院院下分院的工作日志（统计数据现算）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsByFenYuan(QueryConditions condition);
	/**
	 * 统计研究院院下分院的工作日志（从静态表中取数据）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsByFenYuanNew(QueryConditions condition);
	/**
	 * 统计某个院下所的工作日志（统计数据现算）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySuo1(QueryConditions condition);
	/**
	 * 统计某个院下所的工作日志（从静态表中取数据）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySuo1New(QueryConditions condition);
	/**
	 * 统计某个所下人员的工作日志（统计数据现算）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySuo2(QueryConditions condition);
	/**
	 * 统计某个所下人员的工作日志（从静态表中取数据）
	 *
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySuo2New(QueryConditions condition);
	/**
	 * 统计某个室下人员的工作日志（统计数据现算）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySZR(QueryConditions condition);
	/**
	 * 统计某个室下人员的工作日志（从静态表中取数据）
	 * 
	 * @return
	 */
	public Map<String, Object> getWorkLogStatisticsBySZRNew(QueryConditions condition);
	/**
	 * 获取当前登录人所在院的名字和ID
	 * 
	 * @return
	 */
	public PurStruOrgDTO getYuanNameAndId();
	/**
	 * 获取当前登录人所在所的名字和ID
	 * 
	 * @return
	 */
	public PurStruOrgDTO getSuoNameAndId();
	/**
	 * 获取当前登录人所在室的名字和ID
	 * 
	 * @return
	 */
	public PurStruOrgDTO getShiNameAndId();
	/**
	 * 单一用户的用户日历的初始化方法,一次初始化一年.<br/>
	 * 初始化时仅依据系统日历表的日期类型定义<br/>
	 * 
	 * @param logDate
	 *            日历日期,按年来执行初始化
	 */
	public void updateInitUserCanlendar(String logDate);
	/**
	 * 将数据库里已存在的人员且未初始化用户日历表的人员给初始化用户日历表,一次初始化一年.<br/>
	 * 初始化时仅依据系统日历表的日期类型定义<br/>
	 * 
	 * @param logDate
	 *            日历日期,按年来执行初始化
	 */
	public void updateInitAllUserCanlendar(String logDate);
	
	public WorkLogUserCalendar getWorkLogUserCalendarByDTO(WorkLogDTO obj);
	/**
	 * 
	 * 
	 * 获取机关和院区
	 * @author  
	 * @param 
	 * @return
	 */
	public List<PurStruOrg> getPurStruOrgDTO();
	public Long isLogged(WorkLogStaAndQueryDTO wdto);
	/**
	 * 
	 * 
	 * 更新统计物理表里面某一年的数据
	 *   
	 * @param logDate（日期格式为'yyyy'）
	 * @return
	 */
	public void updateStatReport();
}
