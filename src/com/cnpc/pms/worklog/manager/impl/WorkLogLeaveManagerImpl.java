package com.cnpc.pms.worklog.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.manager.PurStruOrgManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.worklog.dao.WorkLogLeaveDAO;
import com.cnpc.pms.worklog.dao.WorkLogUserCalendarDAO;
import com.cnpc.pms.worklog.dto.WorkLogLeaveDTO;
import com.cnpc.pms.worklog.dto.WorkLogDTO;
import com.cnpc.pms.worklog.entity.WorkLogLeave;
import com.cnpc.pms.worklog.entity.WorkLog;
import com.cnpc.pms.worklog.manager.WorkLogLeaveManager;
import com.cnpc.pms.worklog.manager.WorkLogManager;

public class WorkLogLeaveManagerImpl extends BizBaseCommonManager implements
		WorkLogLeaveManager {

	public UserManager getUserManager() {
		return (UserManager) SpringHelper.getBean("userManager");
	}

	/**
	 * 新建一条出差休假记录
	 * <li>插入出差休假表,一条记录
	 * <li>插入日志表,多条记录,记录其类型为出差休假
	 * <li>更新用户日历表,多条记录,设置WorkDayState=0
	 */
	public String saveLeave(WorkLogLeaveDTO workLogLeaveDTO) {
		if (workLogLeaveDTO.getId() != null) {
			this.removeObjectById(workLogLeaveDTO.getId());
		}
		WorkLogLeave workLogLeave = new WorkLogLeave();
		BeanUtils.copyProperties(workLogLeaveDTO, workLogLeave);
		User leaveUser = this.getUserManager().getUserEntity(
				workLogLeaveDTO.getLeaveUserId());
		User recordUser = (User) SessionManager.getUserSession()
				.getSessionData().get("user");
		workLogLeave.setLeaveUserId(workLogLeaveDTO.getLeaveUserId());
		workLogLeave.setLeaveUserName(leaveUser.getName());
		workLogLeave.setRecordUserId(recordUser.getId());
		workLogLeave.setRecordUserName(recordUser.getName());
		PurStruOrgManager pmanager=(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		PurStruOrgDTO pdto=pmanager.getPurOrgDTOById(leaveUser.getPk_org());
		workLogLeave.setOrgCode(pdto.getCode());
		workLogLeave.setCreateUserId(leaveUser.getCreateUserId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String msg;
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());

		//if(workLogLeaveDTO.getLeaveUserId().equals(recordUser.getId())){//如果请假人和休假人是同一人,则保存
			this.saveObject(workLogLeave);
			WorkLogDTO workLogDTO = new WorkLogDTO();
			int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
					.getBeginDate().getTime())
					/ (24 * 60 * 60 * 1000);
			/*System.out.println(workLogLeaveDTO.getLeaveUserId());
			workLogDTO.setUserId(workLogLeaveDTO.getLeaveUserId());
			workLogDTO.setInputDate(workLogLeaveDTO.getInputDate());
			workLogDTO.setWorkLogType("1");
			workLogDTO.setMeeting(workLogLeave.getMeeting());
			workLogDTO.setHolidayType(workLogLeave.getLeaveType());
			workLogDTO.setLeaveId(workLogLeave.getId());
			workLogDTO.setLeaveState(workLogLeave.getLeaveState());
			workLogDTO.setEnableStatus(workLogLeave.getEnableStatus());
			int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
					.getBeginDate().getTime())
					/ (24 * 60 * 60 * 1000);
			WorkLogManager manager = (WorkLogManager) SpringHelper
					.getBean("workLogManager");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i <= leaveDays; i++) {
				Long time = workLogLeave.getBeginDate().getTime() + i
						* (24 * 60 * 60 * 1000);
				workLogDTO.setLogDate(new Date(time));
				String dates = sdf.format(new Date(time));
				this.getDao().updateWorkLogUserCalendar(dates,
						workLogLeaveDTO.getLeaveUserId());
				manager.saveWorkLog(workLogDTO);
			}*/
			for (int i = 0; i <= leaveDays; i++) {
				Long time = workLogLeave.getBeginDate().getTime() + i
						* (24 * 60 * 60 * 1000);
				workLogDTO.setLogDate(new Date(time));
				String dates = sdf.format(new Date(time));
				System.out.println(dates);
				workLogUserCalendarDAO.refreshOneWorkDayState(leaveUser.getId(), dates);
				//this.getDao().updateWorkLogUserCalendar(dates,
						//workLogLeaveDTO.getLeaveUserId());
			}

			msg="保存成功";
		/*}else{//如果不是。请检查请假人的职位
			if(recordUser.getPk_position().equals(new Long(240))){//如果请假人是室主任，在查看休假人是否在该室
				if(recordUser.getPk_org().equals(leaveUser.getPk_org())){//如果休假人和请假人在同一个室下则保存
					this.saveObject(workLogLeave);
					WorkLogDTO workLogDTO = new WorkLogDTO();
					int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
							.getBeginDate().getTime());
					System.out.println(workLogLeaveDTO.getLeaveUserId());
					workLogDTO.setUserId(workLogLeaveDTO.getLeaveUserId());
					workLogDTO.setInputDate(workLogLeaveDTO.getInputDate());
					workLogDTO.setWorkLogType("1");
					workLogDTO.setMeeting(workLogLeave.getMeeting());
					workLogDTO.setHolidayType(workLogLeave.getLeaveType());
					workLogDTO.setLeaveId(workLogLeave.getId());
					workLogDTO.setLeaveState(workLogLeave.getLeaveState());
					workLogDTO.setEnableStatus(workLogLeave.getEnableStatus());
					int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
							.getBeginDate().getTime())
							/ (24 * 60 * 60 * 1000);
					WorkLogManager manager = (WorkLogManager) SpringHelper
							.getBean("workLogManager");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					for (int i = 0; i <= leaveDays; i++) {
						Long time = workLogLeave.getBeginDate().getTime() + i
								* (24 * 60 * 60 * 1000);
						workLogDTO.setLogDate(new Date(time));
						String dates = sdf.format(new Date(time));
						this.getDao().updateWorkLogUserCalendar(dates,
								workLogLeaveDTO.getLeaveUserId());
						manager.saveWorkLog(workLogDTO);
					}
					for (int i = 0; i <= leaveDays; i++) {
						Long time = workLogLeave.getBeginDate().getTime() + i
								* (24 * 60 * 60 * 1000);
						workLogDTO.setLogDate(new Date(time));
						String dates = sdf.format(new Date(time));
						this.getDao().updateWorkLogUserCalendar(dates,
								workLogLeaveDTO.getLeaveUserId());
					}
					msg="保存成功";
				}else{

					msg ="不在同一个室，不允许代请假";
				}
			}else{
				msg = "不是室主任，不允许代别人请假";
			}
		}*/
		
		
		return msg;
	}

	/**
	 *  根据主键获取对象 
	 */
	public WorkLogLeaveDTO getLeaveById(Long id) {
		WorkLogLeaveDTO workLogLeaveDTO = new WorkLogLeaveDTO();
		WorkLogLeave workLogLeave = (WorkLogLeave) this.getObject(id);
		BeanUtils.copyProperties(workLogLeave, workLogLeaveDTO);
		workLogLeaveDTO.setLeaveUserId(workLogLeave.getLeaveUserId());
		workLogLeaveDTO.setLeaveUserName(workLogLeave.getLeaveUserName());
		workLogLeaveDTO.setRecordUserId(workLogLeave.getRecordUserId());
		workLogLeaveDTO.setRecordUserName(workLogLeave.getRecordUserName());
				
		return workLogLeaveDTO;
	}
	/**
	 * 逻辑删除，休假的删除不采用逻辑删除，采用物理删除
	 * 删除以后要更新用户统计表
	 * (此功能无效,出差休假不可删除)
	 */
	public void deleteLeaveById(Long id) {
		// 逻辑删除休假记录
		WorkLogLeave workLogLeave = (WorkLogLeave) this.getObject(id);
		workLogLeave.setEnableStatus(0);
		this.saveObject(workLogLeave);
		WorkLogManager manager = (WorkLogManager) SpringHelper
				.getBean("workLogManager");
		List<WorkLog> workLogList = manager.getWorkLogsList(id);
		for (WorkLog workLog : workLogList) {
			System.out.println(workLog.getId());
			// 逻辑删除该休假对应的工作日志里面的记录
			manager.deleteWorkLog(workLog.getId());
		}
	}
	/**
	 * 采用物理删除休假
	 */
	public void deleteLeaveByIdReal(Long id){
		WorkLogLeave workLogLeave=(WorkLogLeave) super.getObject(id);
		int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
				.getBeginDate().getTime())
				/ (24 * 60 * 60 * 1000);
		System.out.println(leaveDays);
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i <= leaveDays; i++) {
			Long time = workLogLeave.getBeginDate().getTime() + i
					* (24 * 60 * 60 * 1000);
			String dates = sdf.format(new Date(time));
			workLogUserCalendarDAO.refreshOneWorkDayState(workLogLeave.getLeaveUserId(), dates);
			//this.getDao().updateWorkLogUserCalendar(dates,
					//workLogLeaveDTO.getLeaveUserId());
		}
		super.removeObjectById(id);
	}
	/**
	 *  修改状态为已销假
	 *  <li>仅操作出差休假表
	 *  
	 */	
	public void changeSickLeave(Long id) {
		// TODO Auto-generated method stub
		WorkLogLeave lv = (WorkLogLeave) this.getObject(id);
		lv.setLeaveState(1);
		this.saveObject(lv);
	}
	/**
	 * 出差休假的销假功能,出差休假记录仅可以修改一次
	 * <li>更新出差休假表
	 * <li>将工作日志中原来的出差休假记录删除,插入新的出差休假记录
	 * <li>对应更新用户日历表中的工作日状态设置
	 * <li>TODO: 更新用户日历表时,需要考虑要更新的日期是否是节假日,
	 * <li>负责容易把节假日也修改成为工作日,这部分代码需要考虑进行修改
	 * @param workLogLeaveDTO
	 */
	public void changeSickLeaveByObj(WorkLogLeaveDTO workLogLeaveDTO) {
		WorkLogLeave workLogLeave = (WorkLogLeave) super
				.getObject(workLogLeaveDTO.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (workLogLeave != null) {
			Date date1 = workLogLeave.getEndDate();// 未销假之前的请假时填写的结束日期
			Date date2 = workLogLeaveDTO.getEndDate();// 销假时的结束日期
			// 保存销假后的休假信息
			BeanUtils.copyProperties(workLogLeaveDTO, workLogLeave,
					new String[] { "id", "version" });
			User leaveUser = this.getUserManager().getUserEntity(
					workLogLeaveDTO.getLeaveUserId());
			User recordUser = (User) SessionManager.getUserSession()
					.getSessionData().get("user");
			workLogLeave.setLeaveUserId(leaveUser.getId());
			workLogLeave.setLeaveUserName(leaveUser.getName());
			workLogLeave.setRecordUserId(recordUser.getId());
			workLogLeave.setRecordUserName(recordUser.getName());
			workLogLeave.setHistoryDate(date1);//历史日期,销假时将原来设定的休假结束日期记录下来
			this.saveObject(workLogLeave);
			int leaveDays = (int) (date1.getTime() - workLogLeave
					.getBeginDate().getTime())
					/ (24 * 60 * 60 * 1000);
			WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
			for (int i = 0; i <= leaveDays; i++) {
				Long time = workLogLeave.getBeginDate().getTime() + i
						* (24 * 60 * 60 * 1000);
				String dates = sdf.format(new Date(time));
				workLogUserCalendarDAO.refreshOneWorkDayState(leaveUser.getId(), dates);
				//this.getDao().updateWorkLogUserCalendar(dates,
						//workLogLeaveDTO.getLeaveUserId());
			}
		/*	// 获取到对应工作日志里面的记录，实行物理删除
			WorkLogManager manager = (WorkLogManager) SpringHelper
					.getBean("workLogManager");
			List<WorkLog> workLogList = manager.getWorkLogsList(workLogLeaveDTO
					.getId());
			for (WorkLog workLog : workLogList) {
				System.out.println(workLog.getId());
				manager.deleteWorkLogReal(workLog.getId());
			}
			// 物理删除后，再将销假后的休假记录插入到工作日志
			WorkLogDTO workLogDTO = new WorkLogDTO();
			workLogDTO.setUserId(workLogLeave.getLeaveUser().getId());
			workLogDTO.setInputDate(workLogLeaveDTO.getInputDate());
			workLogDTO.setWorkLogType("1");
			workLogDTO.setMeeting(workLogLeave.getMeeting());
			workLogDTO.setHolidayType(workLogLeave.getLeaveType());
			workLogDTO.setLeaveId(workLogLeave.getId());
			workLogDTO.setLeaveState(workLogLeave.getLeaveState());
			workLogDTO.setEnableStatus(workLogLeave.getEnableStatus());
			int leaveDays = (int) (workLogLeave.getEndDate().getTime() - workLogLeave
					.getBeginDate().getTime())
					/ (24 * 60 * 60 * 1000);
			for (int i = 0; i <= leaveDays; i++) {
				Long time = workLogLeave.getBeginDate().getTime() + i
						* (24 * 60 * 60 * 1000);
				workLogDTO.setLogDate(new Date(time));
				manager.saveWorkLog(workLogDTO);
			}*/
			// 比较date2和date1，算出中间相差的那几天，去人员日历表里面把这个人的这几天的记录修改一下。
			/*int xgdays = (int) (date1.getTime() - date2.getTime())
					/ (24 * 60 * 60 * 1000);
			for (int i = 1; i <= xgdays; i++) {
				Long time = date2.getTime() + i * (24 * 60 * 60 * 1000);
				String dates = sdf.format(new Date(time));
				this.getDao().updateWorkLogUserCalendarBack(dates,
						workLogLeaveDTO.getLeaveUserId());
			}*/
		}
	}

	@Override
	public WorkLogLeaveDAO getDao() {
		return (WorkLogLeaveDAO) super.getDao();
	}
	public String checkLeave(WorkLogLeaveDTO workLogLeaveDTO){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginDate = sdf.format(workLogLeaveDTO.getBeginDate());
		String endDate = sdf.format(workLogLeaveDTO.getEndDate());
		System.out.println(workLogLeaveDTO.getLeaveUserId());
		Long userId =workLogLeaveDTO.getLeaveUserId();
		return this.getDao().checkLeave(userId,beginDate, endDate);
		
	}
	public String getUserOrgCode(){
		PurStruOrgManager pmanager=(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		User  cuser= (User) SessionManager.getUserSession()
		.getSessionData().get("user");
		Long userId = cuser.getId();
		Long orgId=this.getDao().getUserOrgId(userId);
		System.out.println(orgId);
		if(orgId!=null&&!orgId.equals(new Long(0))){
			PurStruOrgDTO pdto=pmanager.getPurOrgDTOById(orgId);
			return pdto.getCode();
		}else{
			return null;
		}
	}
	public void updateRefreshOneWorkDayState(String logDate){
		User  cuser= (User) SessionManager.getUserSession()
		.getSessionData().get("user");
		WorkLogUserCalendarDAO workLogUserCalendarDAO =(WorkLogUserCalendarDAO)SpringHelper.getBean(WorkLogUserCalendarDAO.class.getName());
		workLogUserCalendarDAO.refreshOneWorkDayState(cuser.getId(), logDate);
	}
}
