package com.cnpc.pms.worklog.manager.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.datapermission.manager.CopyRecordManager;
import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.orgview.manager.PurStruOrgManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.task.entity.TaskInfo;
import com.cnpc.pms.worklog.dao.WorkLogDAO;
import com.cnpc.pms.worklog.dao.WorkLogQueryDAO;
import com.cnpc.pms.worklog.dao.WorkLogStatDAO;
import com.cnpc.pms.worklog.dao.WorkLogStatReportDAO;
import com.cnpc.pms.worklog.dao.WorkLogUserCalendarDAO;
import com.cnpc.pms.worklog.dto.WorkLogDTO;
import com.cnpc.pms.worklog.dto.WorkLogQueryUserDTO;
import com.cnpc.pms.worklog.dto.WorkLogStaAndQueryDTO;
import com.cnpc.pms.worklog.dto.WorkLogStatByBranchOrgDTO;
import com.cnpc.pms.worklog.entity.WorkLog;
//import com.cnpc.pms.worklog.entity.WorkLogCopy;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;
//import com.cnpc.pms.worklog.manager.WorkLogCopyManager;
import com.cnpc.pms.worklog.manager.WorkLogManager;
import com.cnpc.pms.worklog.manager.WorkLogUserCalendarManager;
import com.cnpc.pms.worklog.util.StatisticsComarators;

/**
 * 工作日志的具体实现类
 * @author liujunsong
 *
 */
public class WorkLogManagerImpl extends BizBaseCommonManager implements
		WorkLogManager {

	public UserManager getUserManager() {
		return (UserManager) SpringHelper.getBean("userManager");
	}

	@Override
	public WorkLogDAO getDao() {
		// TODO Auto-generated method stub
		return (WorkLogDAO) super.getDao();
	}

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
	public WorkLog saveWorkLog(WorkLogDTO workLogDTO) {
		WorkLog workLog=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//WorkLogCopyManager pmanager =(WorkLogCopyManager)SpringHelper.getBean("workLogCopyManager");
		if (workLogDTO.getId() != null) {//修改
			workLog=(WorkLog) super.getObject(workLogDTO.getId());
			BeanUtils.copyProperties(workLogDTO, workLog,new String[] {"id"
			});
			//修改时需要去抄送表里把先前的关于这条日志的一系列抄送信息删除
			//pmanager.deleteWorkLogCopies(workLogDTO.getId());
		}else{//新增
			workLog = new WorkLog();
			BeanUtils.copyProperties(workLogDTO, workLog);
		}
		User cuser = (User) SessionManager.getUserSession().getSessionData()
				.get("user");
		
		BeanUtils.copyProperties(workLogDTO, workLog);
		if(workLogDTO.getUserId()!=null){
			workLog.setUserId(workLogDTO.getUserId());
		}else{
			workLog.setUserId(cuser.getId());
		}
		//计算当天的日志已填的小时数之和加上这次的小时数之和是否超过24小时，若干超则直接返回不允许保存，否则保存
		BigDecimal hour1=this.getDao().getTotalHours(workLog.getUserId(), sdf.format(workLog.getLogDate()));//已填日志里面hours之和
		Double hours2=null;
		if(workLog.getHours()!=null){
			hours2=Double.valueOf(workLog.getHours().toString());
		}
		
		Double hours3=null;
		if(hour1!=null){
			hours3=Double.valueOf(hour1.toString())+hours2;
		}
		
		if(hours3!=null&&hours3>Double.valueOf(new Long(24))){
			log.error("当天的日志已填的小时数之和加上这次的小时数之和超过24小时");
			return null;
		}else{
			this.saveObject(workLog);
			//新增的是工作日志则更新，否则不更新
			if(workLog.getWorkLogType().equals("0")){
				this.getDao().updateUserCalendar(sdf.format(workLogDTO.getLogDate()),
						workLog.getUserId());
				this.getDao().updateUserCalendarOntimeState(sdf.format(workLogDTO.getLogDate()),sdf.format(workLogDTO.getInputDate()),
						workLog.getUserId());
			}
			UserManager pmanager1 =(UserManager)SpringHelper.getBean("userManager");
			CopyRecordManager pmanager2=(CopyRecordManager)SpringHelper.getBean("copyRecordManager");
			String emails = workLogDTO.getToUserEmail();
			String usercodes=workLogDTO.getToUserCode();
			
			if (emails != null && !"".equals(emails)&&!"null,".equals(emails)) {
				String[] emailArr = emails.split(",");
				for (int i = 0; i < emailArr.length; i++) {
					String email = emailArr[i];
					//WorkLogCopy wlcp = new WorkLogCopy();
					//wlcp.setWorklogid(workLog.getId());
					//wlcp.setUserId(Long.parseLong(email));
					//pmanager.saveWorkLogCopy(wlcp);
					
				}
				pmanager2.deleteCopyRecord(workLog.getId());
				pmanager2.saveCopyRecord(usercodes, workLog.getId(), "tb_worklog");
			}
			return workLog;
		}
		
	}
	public WorkLog saveWorkLogOnly(WorkLogDTO workLogDTO){
		WorkLog workLog=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//WorkLogCopyManager pmanager =(WorkLogCopyManager)SpringHelper.getBean("workLogCopyManager");
		if (workLogDTO.getId() != null) {//修改
			workLog=(WorkLog) super.getObject(workLogDTO.getId());
			BeanUtils.copyProperties(workLogDTO, workLog,new String[] {"id"
			});
			//修改时需要去抄送表里把先前的关于这条日志的一系列抄送信息删除
			//pmanager.deleteWorkLogCopies(workLogDTO.getId());
		}else{//新增
			workLog = new WorkLog();
			BeanUtils.copyProperties(workLogDTO, workLog);
		}
		String contents=workLog.getWorkContent().replaceAll("\n", "<br>");
		String results=workLog.getWorkResult().replaceAll("\n", "<br>");
		User cuser = (User) SessionManager.getUserSession().getSessionData()
				.get("user");
		
		BeanUtils.copyProperties(workLogDTO, workLog);
		workLog.setWorkContent(contents);
		workLog.setWorkResult(results);
		if(workLogDTO.getUserId()!=null){
			workLog.setUserId(workLogDTO.getUserId());
		}else{
			workLog.setUserId(cuser.getId());
		}
		//计算当天的日志已填的小时数之和加上这次的小时数之和是否超过24小时，若干超则直接返回不允许保存，否则保存
		BigDecimal hour1=this.getDao().getTotalHours(workLog.getUserId(), sdf.format(workLog.getLogDate()));//已填日志里面hours之和
		Double hours2=null;
		if(workLog.getHours()!=null){
			hours2=Double.valueOf(workLog.getHours().toString());
		}
		
		Double hours3=null;
		if(hour1!=null){
			hours3=Double.valueOf(hour1.toString())+hours2;
		}
		
		if(hours3!=null&&hours3>Double.valueOf(new Long(24))){
			log.error("当天的日志已填的小时数之和加上这次的小时数之和超过24小时");
			return null;
		}else{//保存之前先判定是否已经提交。若提交了则，则将提交日期取出，给这次新增的日志的填报日期设置成提交日期，并且将抄送人也取出，封装进新增的workLog,若没有提交，则直接新增。
			
			/*if(this.getDao().getCommitDate(cuser.getId(), sdf.format(workLog.getLogDate()))!=null){
				String commitDate=this.getDao().getCommitDate(cuser.getId(), sdf.format(workLog.getLogDate()));
				List<Long> workogid=this.getDao().getWorkLogId(cuser.getId(), sdf.format(workLog.getLogDate()));//取出当天已经提交的日志，从中获取到抄送信息
				for(Long ids:workogid){
					WorkLog wg=(WorkLog) this.getObject(ids);
					if(wg.getToUserCode()!=null&&wg.getToUserCode()!=""){
						workLog.setToUserCode(wg.getToUserCode());
						workLog.setToUserEmail(wg.getToUserEmail());
						workLog.setToUserName(wg.getToUserName());
					}
				}
				try {
					workLog.setInputDate(sdf.parse(commitDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
			}*/
			this.saveObject(workLog);
			return workLog;
		}
	}
	//提交日志时完成下列操作
	public void saveWorkLogCopy(WorkLogDTO workLogDTO){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		//去用户日历表修改相应的信息
		try {
			this.getDao().updateUserCalendarAndComitDate(sdf.format(workLogDTO.getLogDate()),
						cuser.getId(),sdf.format(workLogDTO.getInputDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getDao().updateUserCalendarOntimeState(sdf.format(workLogDTO.getLogDate()),sdf.format(workLogDTO.getInputDate()),
					cuser.getId());
		//完成抄送,将toUserCodes,toUserNames,toUserIds存入TB_WorkLog_User_Calendar里面
		//WorkLogCopyManager pmanager =(WorkLogCopyManager)SpringHelper.getBean("workLogCopyManager");
		CopyRecordManager pmanager2=(CopyRecordManager)SpringHelper.getBean("copyRecordManager");
		String emails = workLogDTO.getToUserEmail();
		String usercodes=workLogDTO.getToUserCode();
		String userNames=workLogDTO.getToUserName();
		if(emails!=null&&emails!=""){
			this.getDao().updateUserCalendarAndCopy(sdf.format(workLogDTO.getLogDate()), cuser.getId(), usercodes, userNames, emails);
		}
		//通过人员和日期获取到List<WorkLog>
		List<Long> list=this.getDao().getWorkLogId(cuser.getId(), sdf.format(workLogDTO.getLogDate()));
		for(Long workLogId:list){
			WorkLog workLog=(WorkLog) this.getObject(workLogId);
			//workLog.setToUserCode(workLogDTO.getToUserCode());
			//workLog.setToUserEmail(workLogDTO.getToUserEmail());
			//workLog.setToUserName(workLogDTO.getToUserName());
			workLog.setInputDate(workLogDTO.getInputDate());//提交时，将当天所有的日志的提交时间更新
			this.save(workLog);
			//pmanager.deleteWorkLogCopies(workLog.getId());
			if (emails != null && !"".equals(emails)&&!"null,".equals(emails)) {
				/*String[] emailArr = emails.split(",");
				for (int i = 0; i < emailArr.length; i++) {
					String email = emailArr[i];
					WorkLogCopy wlcp = new WorkLogCopy();
					wlcp.setWorklogid(workLog.getId());
					wlcp.setUserId(Long.parseLong(email));
					pmanager.saveWorkLogCopy(wlcp);
					
				}*/
				pmanager2.deleteCopyRecord(workLog.getId());
				pmanager2.saveCopyRecord(usercodes, workLog.getId(), "tb_worklog");
			}
		}
		
	}
	/**
	 * 逻辑删除工作日志,这个方法要废弃掉，不再使用
	 * 
	 * @param id
	 */
	public void deleteWorkLog(Long id) {
		WorkLog workLog = (WorkLog) this.getObject(id);
		workLog.setEnableStatus(0);
		this.saveObject(workLog);
	}

	
	/**
	 * 根据主键获取工作日志
	 * 
	 * @param id
	 * @return
	 */
	public WorkLogDTO getWorkLogById(Long id) {
		WorkLog workLog = (WorkLog) this.getObject(id);
		String contents=workLog.getWorkContent().replaceAll("<br>", "\n");
		String results=workLog.getWorkResult().replaceAll("<br>", "\n");
		workLog.setWorkContent(contents);
		workLog.setWorkResult(results);
		WorkLogDTO workLogDTO = new WorkLogDTO();
		BeanUtils.copyProperties(workLog, workLogDTO);
		return workLogDTO;
	}

	public String validateUserEmailExists(String emails) {
		String retStr = "";
		String[] emailsArr = emails.split(";");
		List<String> emailsLs = Arrays.asList(emailsArr);
		List<User> users = getUserManager().getUsersByEmails(emailsLs);
		for (User user : users) {
			Long id = user.getId();
			String idStr = String.valueOf(id);
			retStr += idStr + ";";
		}
		return retStr;
	}

	public List<Map> getLastCreateWorkLogUsers() {
		User cuser = (User) SessionManager.getUserSession().getSessionData()
				.get("user");
		List<Map> ls = getDao().getLastCreateWorkLogUsers(cuser);
		return ls;
	}

	public List<WorkLog> getWorkLogsList(Long id) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("leaveId", id));
		List<WorkLog> workLogList = (List<WorkLog>) this.getObjects(fsp);
		return workLogList;
	}
	/**
	 * 物理删除工作日志，删除工作日志时要进行用户日志统计信息的更新操作，
	 * 业务逻辑同保存工作日志时的逻辑。
	 * 
	 * @param id
	 */
	public void deleteWorkLogReal(Long id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		WorkLog worklog = (WorkLog) super.getObject(id);
		String date1=sdf.format(worklog.getLogDate());
		Long userId=worklog.getUserId();
		//若有抄送需要先把抄送的信息给删除。
		
		CopyRecordManager pmanager2=(CopyRecordManager)SpringHelper.getBean("copyRecordManager");
		pmanager2.deleteCopyRecord(id);
		//WorkLogCopyManager pmanager =(WorkLogCopyManager)SpringHelper.getBean("workLogCopyManager");
		//通过workLog的Id获取到所有的与其关联的所有抄送信息，删除之
		//pmanager.deleteWorkLogCopies(id);
		this.removeObjectById(id);
		this.getDao().updateUserCalendarBack(date1,userId);
		this.getDao().updateUserCalendarOntimeStateBack(date1, userId);
		
	}

	public boolean checkWorLogExists(String logDate) {
		// TODO Auto-generated method stub
		User cuser = (User) SessionManager.getUserSession().getSessionData().get("user");
		return this.getDao().checkWorLogExists(cuser.getId(),logDate);
	}
	public Boolean checkWorLogExistsThreeDays(String logDate){
		User cuser = (User) SessionManager.getUserSession().getSessionData().get("user");
		return this.getDao().checkWorLogExistsThreeDays(cuser.getId(),logDate);
	}
	public List<PurStruOrg> getPurStruOrgChildren(Long parentId) {
		// TODO Auto-generated method stub
		PurStruOrgManager manager = (PurStruOrgManager) SpringHelper
				.getBean("purStruOrgManager");
		PurStruOrg purStruOrg = (PurStruOrg) manager.getObject(parentId);
		Set<PurStruOrg> childs = purStruOrg.getChilds();
		List<PurStruOrg> list = new ArrayList();
		for (PurStruOrg childOrg : childs) {
			list.add(childOrg);
		}
		return list;
	}

	public List<PurStruOrg> getPurStruOrgParent(String entityOrgFlag) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("entityOrgFlag",entityOrgFlag));
		PurStruOrgManager manager = (PurStruOrgManager) SpringHelper
				.getBean("purStruOrgManager");
		List<PurStruOrg> list = (List<PurStruOrg>) manager.getObjects(fsp);
		return list;
	}
	
	public PurStruOrgDTO getPurStruOrg(Long id){
		PurStruOrgManager manager=(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		return (PurStruOrgDTO)manager.getPurOrgDTOById(id);
	}
	public Long getUserOrg(){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		return cuser.getcOrgId();
	}
	public User getUser(){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		return cuser;
	}

	public WorkLog getWorkLog(String logDate) {
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		List<Long> list=this.getDao().getWorkLogId(cuser.getId(), logDate);
		WorkLog wg=new WorkLog();
		for(Long workLogId:list){
			WorkLog workLog=(WorkLog) this.getObject(workLogId);
			if(workLog.getToUserCode()!=null){
				wg=workLog;
				return workLog;
			}
		}
		return wg;
	}
	public String getRecordState(String logDate){
		WorkLogUserCalendarManager manager=(WorkLogUserCalendarManager)SpringHelper.getBean("workLogUserCalendarManager");
		return manager.getCurrentUserRecordState(logDate);
	}
	public String getRecordStateById(WorkLogDTO dto){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		WorkLogUserCalendarManager manager=(WorkLogUserCalendarManager)SpringHelper.getBean("workLogUserCalendarManager");
		return manager.getUserRecordState(dto.getUserId(), sdf.format(dto.getLogDate()));
	}
	public List<String> getLogdatesList(){
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取到本人信息
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		//获取本月第一天
		String beginDate=sdf.format(c.getTime());
		//获取本月最后一天
		String endDate=sdf.format(c1.getTime());
		return this.getDao().getLogdatesList(cuser.getId(), beginDate, endDate);
		
	}
	public List<String> getLogdatesListByUID(Long userId){
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//获取本月第一天
		String beginDate=sdf.format(c.getTime());
		//获取本月最后一天
		String endDate=sdf.format(c1.getTime());
		return this.getDao().getLogdatesList(userId, beginDate, endDate);
	}
	public int getCounts(String logDate){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		 int counts = this.getDao().getWorkLogId(cuser.getId(), logDate).size();
		 return counts;
	}
	public String getInputDate(String logDate){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		return this.getDao().getInputDate(cuser.getId(), logDate);
	}
	public WorkLogUserCalendar getWorkLogUserCalendar(String logDate){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		Long id=this.getDao().getUserCanlendarId(cuser.getId(), logDate);
		WorkLogUserCalendarManager manager=(WorkLogUserCalendarManager)SpringHelper.getBean("workLogUserCalendarManager");
		WorkLogUserCalendar obj=(WorkLogUserCalendar) manager.getObject(id);
		return obj;
	}
	public WorkLogUserCalendar getWorkLogUserCalendarByDTO(WorkLogDTO obj){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long id=this.getDao().getUserCanlendarId(obj.getUserId(), sdf.format(obj.getLogDate()));
		WorkLogUserCalendarManager manager=(WorkLogUserCalendarManager)SpringHelper.getBean("workLogUserCalendarManager");
		WorkLogUserCalendar obj1=(WorkLogUserCalendar) manager.getObject(id);
		return obj1;
	}

	public Map<String, Object> getWorkLogQueryByKYGLC(QueryConditions condition){
		WorkLogQueryDAO workLogQueryDAO =(WorkLogQueryDAO)SpringHelper.getBean(WorkLogQueryDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate=null;
		String orgId=null;
		String types=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types = (String)condition1.get("value");
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId=condition1.get("value").toString();
			}else if("logDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate =condition1.get("value").toString();
				
			}else if("userName".equals(condition1.get("key"))&&condition1.get("value")!=null){
				userName =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogQueryUserDTO> list =new ArrayList<WorkLogQueryUserDTO>();
		if(orgId!=null&&!orgId.equals("")){
			list=workLogQueryDAO.getQueryUserDTOListByOrgId(pageInfo, logDate, logDate, orgId, cuser.getId(), userName);
		}else{
			list=workLogQueryDAO.getQueryUserDTOListByFollow(pageInfo, cuser.getId(), logDate, logDate, userName);
		}
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogQueryBySLD(QueryConditions condition){
		WorkLogQueryDAO workLogQueryDAO =(WorkLogQueryDAO)SpringHelper.getBean(WorkLogQueryDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate=null;
		String orgId=null;
		String types=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types = condition1.get("value").toString().replaceAll(",", "");
			}else if("logDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate =condition1.get("value").toString();
				
			}else if("userName".equals(condition1.get("key"))&&condition1.get("value")!=null){
				userName =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogQueryUserDTO> list =new ArrayList<WorkLogQueryUserDTO>();
		orgId=cuser.getcOrgId().toString();
		if(types.equals("bs")){
			list=workLogQueryDAO.getQueryUserDTOListByOrgId(pageInfo, logDate, logDate, orgId, cuser.getId(), userName);
		}else if(types.equals("xmxg")){
			list=workLogQueryDAO.getQueryUserDTOListByProject(pageInfo, logDate, logDate, cuser.getId(), userName);
		}else if(types.equals("gz")){
			list=workLogQueryDAO.getQueryUserDTOListByFollow(pageInfo, cuser.getId(), logDate, logDate, userName);
		}
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogQueryBySZR(QueryConditions condition){
		WorkLogQueryDAO workLogQueryDAO =(WorkLogQueryDAO)SpringHelper.getBean(WorkLogQueryDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate=null;
		String orgId=null;
		String types=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types = condition1.get("value").toString().replaceAll(",", "");
			}else if("logDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate =condition1.get("value").toString();
				
			}else if("userName".equals(condition1.get("key"))&&condition1.get("value")!=null){
				userName =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogQueryUserDTO> list =new ArrayList<WorkLogQueryUserDTO>();
		orgId=cuser.getPk_org().toString();
		System.out.println(orgId);
		if(types.equals("bsAndsXmxg")){
			list=workLogQueryDAO.getQueryUserDTOListByOrgIdAndProject(pageInfo, logDate, logDate, Long.parseLong(orgId), cuser.getId(), userName);
		}else if(types.equals("gz")){
			list=workLogQueryDAO.getQueryUserDTOListByFollow(pageInfo, cuser.getId(), logDate, logDate, userName);
		}
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogQueryByXMXG(QueryConditions condition){
		WorkLogQueryDAO workLogQueryDAO =(WorkLogQueryDAO)SpringHelper.getBean(WorkLogQueryDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate=null;
		String orgId=null;
		String types=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			System.out.println(condition1.get("key")+"--"+condition1.get("value"));
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types = condition1.get("value").toString().replaceAll(",", "");
			}else if("logDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate =condition1.get("value").toString();
				
			}else if("userName".equals(condition1.get("key"))&&condition1.get("value")!=null){
				userName =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogQueryUserDTO> list =new ArrayList<WorkLogQueryUserDTO>();
		orgId=cuser.getcOrgId().toString();
		if(types.equals("xmxg")){
			list=workLogQueryDAO.getQueryUserDTOListByProject(pageInfo, logDate, logDate, cuser.getId(), userName);
		}else if(types.equals("gz")){
			list=workLogQueryDAO.getQueryUserDTOListByFollow(pageInfo, cuser.getId(), logDate, logDate, userName);
		}
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsByFenYuan(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		Long orgId=null;
		String types=null;
		String types1=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types = condition1.get("value").toString().replaceAll(",","");
			}else if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<PurStruOrg> list3=new ArrayList<PurStruOrg>();
		
		String orgflag=null;
		PurStruOrgManager purStruOrgManager =(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		orgId=purStruOrgManager.getIdByCode(types);
		PurStruOrgDTO purStruOrgDTO=purStruOrgManager.getPurOrgDTOById(orgId);
		
		orgflag=purStruOrgDTO.getEntityOrgFlag();
		if(orgflag.equals("0")){//研究院
			 list3=workLogStatDAO.getBranchList();
			list=workLogStatDAO.getUserStatByBranch(logDate1, logDate2, Long.parseLong(types1));
		}else{//分院
			//Long Id=Long.parseLong(types);
			 list3=workLogStatDAO.getDeptList(orgId);
			list=workLogStatDAO.getUserStatByOrg(logDate1, logDate2, Long.parseLong(types1), orgId);
		}
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把所有组织机构塞进List<WorkLogStatByBranchOrgDTO> list2
		for(PurStruOrg org:list3){
			Long tempOrgId=org.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(org.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsByFenYuanNew(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		WorkLogStatReportDAO workLogStatReportDAO =(WorkLogStatReportDAO)SpringHelper.getBean(WorkLogStatReportDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		Long orgId=null;
		String types=null;
		String types1=null;
		String types2=null;
		String statType=null;
		String statDateType=null;
		Long jobType=null;
		Long statId=null;
		String beginDate=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type".equals(condition1.get("key"))&&condition1.get("value")!=null){//组织机构
				types = condition1.get("value").toString().replaceAll(",","");
			}else if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){//用功类型
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("type2".equals(condition1.get("key"))&&condition1.get("value")!=null){//时间维度
				types2 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<PurStruOrg> list3=new ArrayList<PurStruOrg>();
		
		String orgflag=null;
		PurStruOrgManager purStruOrgManager =(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		orgId=purStruOrgManager.getIdByCode(types);
		
		
		PurStruOrgDTO purStruOrgDTO=purStruOrgManager.getPurOrgDTOById(orgId);
		orgflag=purStruOrgDTO.getEntityOrgFlag();
		statId=orgId;
		jobType=Long.parseLong(types1);
		beginDate=logDate1;
		if(types2.equals("day")){//以日为维度
			
			if(orgflag.equals("0")){//研究院
				 list3=workLogStatDAO.getBranchList();
				list=workLogStatDAO.getUserStatByBranch(logDate1, logDate2, Long.parseLong(types1));
			}else{//分院
				//Long Id=Long.parseLong(types);
				 list3=workLogStatDAO.getDeptList(orgId);
				list=workLogStatDAO.getUserStatByOrg(logDate1, logDate2, Long.parseLong(types1), orgId);
			}
		}else{
			if(types2.equals("week")){//以周为维度
				statDateType="1";
			}else if(types2.equals("month")){//以月为维度
				statDateType="2";
			}else if(types2.equals("year")){//以年为维度
				statDateType="3";
			}
			if(orgflag.equals("0")){//研究院
				 list3=workLogStatDAO.getBranchList();
				 statType="1";
				list=workLogStatReportDAO.getList(statType, statDateType, jobType, statId, beginDate);
			}else{//分院
				//Long Id=Long.parseLong(types);
				statType="2";
				 list3=workLogStatDAO.getDeptList(orgId);
				 list=workLogStatReportDAO.getDeptList(statType, statDateType, jobType, statId, beginDate);
			}
		}
		
		
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把所有组织机构塞进List<WorkLogStatByBranchOrgDTO> list2
		for(PurStruOrg org:list3){
			Long tempOrgId=org.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(org.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		//点击排序
		Sort st = condition.getSortinfo();
		if(st!=null){
			String sort = st.getString();
			StatisticsComarators statisticsComarators=new StatisticsComarators();
			statisticsComarators.getComarators(list2, sort);
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySuo1(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){//用功类型
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<PurStruOrg> list3=new ArrayList<PurStruOrg>();
		list=workLogStatDAO.getUserStatByOrg(logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));
		list3=workLogStatDAO.getDeptList(Long.parseLong(orgId));
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把所有组织机构塞进List<WorkLogStatByBranchOrgDTO> list2
		for(PurStruOrg org:list3){
			Long tempOrgId=org.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(org.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		list2.add(hejiDto);

		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySuo1New(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		WorkLogStatReportDAO workLogStatReportDAO =(WorkLogStatReportDAO)SpringHelper.getBean(WorkLogStatReportDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String types2=null;
		String statType=null;
		String statDateType=null;
		Long jobType=null;
		Long statId=null;
		String beginDate=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){//用功类型
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("type2".equals(condition1.get("key"))&&condition1.get("value")!=null){//时间维度
				types2 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<PurStruOrg> list3=new ArrayList<PurStruOrg>();
		statId=Long.parseLong(orgId);
		jobType=Long.parseLong(types1);
		beginDate=logDate1;
		if(types2.equals("day")){
			list=workLogStatDAO.getUserStatByOrg(logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));

		}else{
			if(types2.equals("week")){//以周为维度
				statDateType="1";
			}else if(types2.equals("month")){//以月为维度
				statDateType="2";
			}else if(types2.equals("year")){//以年为维度
				statDateType="3";
			}
			statType="2";
			list=workLogStatReportDAO.getDeptList(statType, statDateType, jobType, statId, beginDate);
		}
		list3=workLogStatDAO.getDeptList(Long.parseLong(orgId));
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把所有组织机构塞进List<WorkLogStatByBranchOrgDTO> list2
		for(PurStruOrg org:list3){
			Long tempOrgId=org.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(org.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		//点击排序
		Sort st = condition.getSortinfo();
		if(st!=null){
			String sort = st.getString();
			StatisticsComarators statisticsComarators=new StatisticsComarators();
			statisticsComarators.getComarators(list2, sort);
		}
		list2.add(hejiDto);

		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySuo2(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		PageInfo pageInfo1 = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<User> list3= new ArrayList<User>();
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		list=workLogStatDAO.getUserStatByPerson(pageInfo, logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));
		//list3=workLogStatDAO.getUserList(Long.parseLong(orgId), pageInfo);
		list3=workLogStatDAO.getUserListByJobType(Long.parseLong(orgId),Long.parseLong(types1),pageInfo);

		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把组织机构下所有人塞进List<WorkLogStatByBranchOrgDTO> list2
		for(User user:list3){
			Long tempOrgId=user.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(user.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySuo2New(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		WorkLogStatReportDAO workLogStatReportDAO =(WorkLogStatReportDAO)SpringHelper.getBean(WorkLogStatReportDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String types2=null;
		String statType=null;
		String statDateType=null;
		Long jobType=null;
		Long statId=null;
		String beginDate=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		PageInfo pageInfo1 = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){//用功类型
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("type2".equals(condition1.get("key"))&&condition1.get("value")!=null){//时间维度
				types2 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<User> list3= new ArrayList<User>();
		statId=Long.parseLong(orgId);
		jobType=Long.parseLong(types1);
		beginDate=logDate1;
		if(types2.equals("day")){
			list=workLogStatDAO.getUserStatByPerson(pageInfo, logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));

		}else{
			if(types2.equals("week")){//以周为维度
				statDateType="1";
			}else if(types2.equals("month")){//以月为维度
				statDateType="2";
			}else if(types2.equals("year")){//以年为维度
				statDateType="3";
			}
			statType="3";
			list=workLogStatReportDAO.getUserList(statType, statDateType, jobType, statId, beginDate);
		}
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		//list3=workLogStatDAO.getUserList(Long.parseLong(orgId), pageInfo);
		list3=workLogStatDAO.getUserListByJobType(Long.parseLong(orgId),jobType,pageInfo);

		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把组织机构下所有人塞进List<WorkLogStatByBranchOrgDTO> list2
		for(User user:list3){
			Long tempOrgId=user.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(user.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		//点击排序
		Sort st = condition.getSortinfo();
		if(st!=null){
			String sort = st.getString();
			StatisticsComarators statisticsComarators=new StatisticsComarators();
			statisticsComarators.getComarators(list2, sort);
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySZR(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		PageInfo pageInfo1 = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<User> list3= new ArrayList<User>();
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		list=workLogStatDAO.getUserStatByPerson2(pageInfo, logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));
		//list3=workLogStatDAO.getUserList(Long.parseLong(orgId), pageInfo);
		list3=workLogStatDAO.getUserListByJobType(Long.parseLong(orgId),Long.parseLong(types1),pageInfo);

		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把组织机构下所有人塞进List<WorkLogStatByBranchOrgDTO> list2
		for(User user:list3){
			Long tempOrgId=user.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(user.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public Map<String, Object> getWorkLogStatisticsBySZRNew(QueryConditions condition){
		WorkLogStatDAO workLogStatDAO =(WorkLogStatDAO)SpringHelper.getBean(WorkLogStatDAO.class.getName());
		WorkLogStatReportDAO workLogStatReportDAO =(WorkLogStatReportDAO)SpringHelper.getBean(WorkLogStatReportDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		// 获取到几个查询条件
		String logDate1=null;
		String logDate2=null;
		String orgId=null;
		String types=null;
		String types1=null;
		String types2=null;
		String statType=null;
		String statDateType=null;
		Long jobType=null;
		Long statId=null;
		String beginDate=null;
		String userName=null;
		PageInfo pageInfo = condition.getPageinfo();
		PageInfo pageInfo1 = condition.getPageinfo();
		for(Map<String, Object> condition1 : condition.getConditions()){
			if("type1".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types1 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("type2".equals(condition1.get("key"))&&condition1.get("value")!=null){
				types2 =condition1.get("value").toString().replaceAll(",","");
				
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
				orgId =condition1.get("value").toString();
				
			}else if("beginLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate1 =condition1.get("value").toString();
				
			}else if("endLogDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				logDate2 =condition1.get("value").toString();
				
			}else{
				
			}
		}
		List<WorkLogStatByBranchOrgDTO> list =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> list2 =new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<User> list3= new ArrayList<User>();
		WorkLogStatByBranchOrgDTO hejiDto=new WorkLogStatByBranchOrgDTO();
		statId=Long.parseLong(orgId);
		jobType=Long.parseLong(types1);
		beginDate=logDate1;
		if(types2.equals("day")){
			list=workLogStatDAO.getUserStatByPerson2(pageInfo, logDate1, logDate2, Long.parseLong(types1), Long.parseLong(orgId));

		}else{
			if(types2.equals("week")){//以周为维度
				statDateType="1";
			}else if(types2.equals("month")){//以月为维度
				statDateType="2";
			}else if(types2.equals("year")){//以年为维度
				statDateType="3";
			}
			statType="3";
			list=workLogStatReportDAO.getUserList(statType, statDateType, jobType, statId, beginDate);
		}
		list3=workLogStatDAO.getUserListByJobType(Long.parseLong(orgId),jobType,pageInfo);
		for(WorkLogStatByBranchOrgDTO tempDto:list){
			if(tempDto.getName().equals("合计")){
				hejiDto=tempDto;
			}
		}
		//把组织机构下所有人塞进List<WorkLogStatByBranchOrgDTO> list2
		for(User user:list3){
			Long tempOrgId=user.getId();
			WorkLogStatByBranchOrgDTO tempWorkLogStatByBranchOrgDTO=new WorkLogStatByBranchOrgDTO();
			tempWorkLogStatByBranchOrgDTO.setId(tempOrgId);
			tempWorkLogStatByBranchOrgDTO.setName(user.getName());
			tempWorkLogStatByBranchOrgDTO.setCommitPercenter("0.0%");
			tempWorkLogStatByBranchOrgDTO.setCommitRate(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setHours(Double.valueOf("0"));
			tempWorkLogStatByBranchOrgDTO.setRecordState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setOuttimeState(new Long(0));
			tempWorkLogStatByBranchOrgDTO.setWorkdayState(new Long(0));
			list2.add(tempWorkLogStatByBranchOrgDTO);
		}
		//遍历list
		for(WorkLogStatByBranchOrgDTO dto:list){
			Long tempOrgId=dto.getId();
			for(WorkLogStatByBranchOrgDTO dto1:list2){
				if(dto1.getId().equals(tempOrgId)){
					//dto1=dto;
					BeanUtils.copyProperties(dto, dto1,new String[] {"id"
					});
				}
			}
		}
		//点击排序
		Sort st = condition.getSortinfo();
		if(st!=null){
			String sort = st.getString();
			StatisticsComarators statisticsComarators=new StatisticsComarators();
			statisticsComarators.getComarators(list2, sort);
		}
		list2.add(hejiDto);
		//设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list2);
		return map;
	}
	public PurStruOrgDTO getYuanNameAndId(){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		Long userId=cuser.getId();
		return this.getDao().getYuanNameAndId(userId);
	}
	public PurStruOrgDTO getSuoNameAndId(){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		Long userId=cuser.getId();
		return this.getDao().getSuoNameAndId(userId);
	}
	public PurStruOrgDTO getShiNameAndId(){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		Long userId=cuser.getId();
		return this.getDao().getShiNameAndId(userId);
	}
	public void updateInitUserCanlendar(String logDate){
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		workLogUserCalendarDAO.initUserCanlendar(cuser.getId(), logDate);

	}
	public void updateInitAllUserCanlendar(String logDate){
		UserManager userManager=(UserManager)SpringHelper.getBean("userManager");
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		//List<User> list=(List<User>) userManager.getObjects();
		//System.out.println(list.size());
		List<Long> userIds=this.getDao().getUseIds(logDate);
		for(Long userId:userIds){
			workLogUserCalendarDAO.initUserCanlendar(userId, logDate);

		}
	}
	public List<PurStruOrg> getPurStruOrgDTO(){
		return this.getDao().getPurStruOrgDTO();
	}
	public Long isLogged(WorkLogStaAndQueryDTO wdto){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		WorkLogQueryDAO workLogQueryDAO =(WorkLogQueryDAO)SpringHelper.getBean(WorkLogQueryDAO.class.getName());

		Long currUserId=cuser.getId();
		Long userId=wdto.getUserId();
		Long operType=wdto.getOperType();
		Date logDate=wdto.getLogDate();
		return workLogQueryDAO.isLogged(currUserId, userId, operType, logDate);
	}
	public void updateStatReport(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String thisYear=c.get(Calendar.YEAR)+"";//获取今年的年份并且转化成字符串“yyyy”格式
		String LastYear=(c.get(Calendar.YEAR)-1)+"";//获取去年的年份并且转化成字符串“yyyy”格式
		System.out.println(thisYear+"--"+LastYear);
		WorkLogStatReportDAO workLogStatReportDAO =(WorkLogStatReportDAO)SpringHelper.getBean(WorkLogStatReportDAO.class.getName());
		workLogStatReportDAO.deleteStatReportByYear(LastYear);
		workLogStatReportDAO.insertStatReportByYear(LastYear);
		workLogStatReportDAO.insertStatReportByMonth(LastYear);
		workLogStatReportDAO.insertStatReportByWeek(LastYear);
		workLogStatReportDAO.deleteStatReportByYear(thisYear);
		workLogStatReportDAO.insertStatReportByYear(thisYear);
		workLogStatReportDAO.insertStatReportByMonth(thisYear);
		workLogStatReportDAO.insertStatReportByWeek(thisYear);

	}
	private void orderByDesc(List<WorkLogStatByBranchOrgDTO> list){
		List<WorkLogStatByBranchOrgDTO> listTemp =new ArrayList<WorkLogStatByBranchOrgDTO>();
		for(WorkLogStatByBranchOrgDTO w:list){//将升序排列的list2的数据暂时储存到listTemp中
			listTemp.add(w);
		}
		list.removeAll(list);//删除list里面所有的数据
		int i;
		for(i=listTemp.size()-1;i>=0;i--){//将listTemp倒叙插入list
			list.add(listTemp.get(i));
		}
	}
}
