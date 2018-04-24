package com.cnpc.pms.worklog.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;

public interface WorkLogDAO extends IDAO {

	/**
	 * 获取当前用户最后一次创建日志时所填的抄送人
	 * 
	 * @param cuser
	 * @return
	 */
	public List<Map> getLastCreateWorkLogUsers(User cuser);

	public Boolean checkWorLogExists(Long userId,String logDate);
	//检测该3天前该日期是否存在记录
	public Boolean checkWorLogExistsThreeDays(Long userId,String logDate);

	public void formatCaleState(String d1, String d2);
	/**
	 * 
	 * 
	 * 新增工作日志时，修改用户日历表里面的recordState状态
	 * @return
	 */
	public void updateUserCalendar(String date1, Long userId);
	/**
	 * 
	 * 
	 * 新增工作日志时，修改用户日历表里面的recordState状态,并且将提交日期给记录下来
	 * 
	 * @return
	 */
	public void updateUserCalendarAndComitDate(String date1, Long userId,String commitDate) throws ParseException;
	/**
	 * 
	 * 
	 * 新增工作日志时，若有抄送，则通过此方法完成
	 * 
	 * @return
	 */
	public void updateUserCalendarAndCopy(String logDate,Long userId,String toUserCodes,String toUserNames,String toUserIds);
	/**
	 * 
	 * 
	 * 删除工作日志时，修改用户日历表里面的recordState状态
	 * @return
	 */
	public void updateUserCalendarBack(String date1, Long userId);
	/**
	 * 
	 * 
	 * 新增工作日志时，修改用户日历表里面的ontimestate状态,date1为日志日期，date2是填写日期
	 * @return
	 */
	public void updateUserCalendarOntimeState(String date1,String date2, Long userId);
	/**
	 * 
	 * 
	 * 删除工作日志时，修改用户日历表里面的ontimestate状态
	 * @return
	 */
	public void updateUserCalendarOntimeStateBack(String date1,Long userId);
	/**
	 * 
	 * 
	 * 新增工作日志时，先获取当天已填日志的总小时数
	 * @return
	 */
	public BigDecimal getTotalHours(Long userId,String date);
	public List<Long> getWorkLogId(Long userId,String logDate); 
	/**
	 * 
	 * 
	 * 获取工作日的的最早填报日期
	 * @return
	 */
	public String getInputDate(Long userId,String logDate); 
	/**
	 * 
	 * 
	 * 获取到这个时间段内的时间，转成String（"YYYY-MM-DD"）封装进LIST返回
	 * @return
	 */
	public List<String> getLogdatesList(Long userId,String beginDate,String endDate);
	/**
	 * 
	 * 
	 * 获取工作日志的提交时间。
	 * @return
	 */
	public String getCommitDate(Long userId,String logDate);
	public Long getUserCanlendarId(Long userId,String logDate);
	/**
	 * 
	 * 
	 * 获取当前人所在院。
	 * @return
	 */
	public PurStruOrgDTO getYuanNameAndId(Long userId);
	public PurStruOrgDTO getSuoNameAndId(Long userId);
	public PurStruOrgDTO getShiNameAndId(Long userId);
	/**
	 * 
	 * 
	 * 获取为初始化用户日历表的用户
	 * @author  
	 * @param logDate
	 * @return
	 */
	public List<Long> getUseIds(String logDate);
	/**
	 * 
	 * 
	 * 获取机关和院区
	 * @author  
	 * @param 
	 * @return
	 */
	public List<PurStruOrg> getPurStruOrgDTO();
}
