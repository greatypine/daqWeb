package com.cnpc.pms.worklog.manager.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.dao.WorkLogUserCalendarDAO;
import com.cnpc.pms.worklog.dto.WorkLogUserCalendarDTO;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;
import com.cnpc.pms.worklog.manager.WorkLogUserCalendarManager;

public class WorkLogUserCalendarManagerImpl extends BaseManagerImpl implements
		WorkLogUserCalendarManager {

	/**
	 * 增加或保存一条用户日历
	 */
	public void addWorkLogPersonCaleState(WorkLogUserCalendar obj) {
		super.saveObject(obj);
	}

	/**
	 * 根据Id来检索用户日历
	 */
	public WorkLogUserCalendar getWorkLogPersonCaleState(Long id) {
		return (WorkLogUserCalendar) super.getObject(id);

	}

	@Override
	/**
	 * 获取对应的DAO对象
	 */
	public WorkLogUserCalendarDAO getDao() {
		return (WorkLogUserCalendarDAO) super.getDao();
	}

	/**
	 * 根据传入的DTO对象来进行保存.
	 */
	public WorkLogUserCalendar saveWorkLogPersonCaleState(
			WorkLogUserCalendar obj) {
		WorkLogUserCalendar dbObj = null;
		dbObj = (WorkLogUserCalendar) super.getObject(obj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = obj;
		} else {
			BeanUtils.copyProperties(obj, dbObj,
					new String[] { "id", "version" });
		}
		super.saveObject(dbObj);
		return obj;
	}

	public Boolean updateWorkLogUserCalendar(WorkLogUserCalendarDTO obj) {
		return this.getDao().updateWorkLogUserCalendar(obj);
	}

	/**
	 * 获取日志的提交状态<br/>
	 * 根据用户Id(从seesion里面取)和日志日期获取到用户日历表里面相应的recordstate状态
	 * 
	 * @param logDate
	 *            日志日期,格式yyyy-mm-dd
	 * @return 当前用户的指定日期的提交状态
	 */
	public String getCurrentUserRecordState(String logDate) {
		// Step1:获取当前用户的Id
		User cuser = (User) SessionManager.getUserSession().getSessionData()
				.get("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(cuser.getId());
		if(this.getDao().getRecordState(cuser.getId(), logDate)!=null){
			System.out.println(this.getDao().getRecordState(cuser.getId(), logDate).getCommitDate());
			if(this.getDao().getRecordState(cuser.getId(), logDate).getCommitDate()!=null&&this.getDao().getRecordState(cuser.getId(), logDate).getRecordState()!=null){
				return this.getDao().getRecordState(cuser.getId(), logDate).getCommitDate().toString().substring(0, 16);
			}else{
				return null;
			}
			
	
		}else{
			return null;
		}
	}

	/**
	 * 根据用户Id和日历日期获取用户日历表中的RecordState状态
	 * 
	 * @param userId
	 *            用户id
	 * @param logDate
	 *            日志日期，格式yyyy-mm-dd
	 */
	public String getUserRecordState(Long userId, String logDate) {
		if(this.getDao().getRecordState(userId, logDate)!=null){
			System.out.println(this.getDao().getRecordState(userId, logDate).getCommitDate());
			if(this.getDao().getRecordState(userId, logDate).getCommitDate()!=null&&this.getDao().getRecordState(userId, logDate).getRecordState()!=null){
				return this.getDao().getRecordState(userId, logDate).getCommitDate().toString().substring(0, 16);
			}else{
				return null;
			}
			
	
		}else{
			return null;
		}
	}
	
	/**
	 * 初始化用户Id指定年份的用户日历表，如果存在数据，则不重复初始化
	 */
	public String initUserCanlendar(Long userId, String logDate) {
		this.getDao().initUserCanlendar(userId, logDate);
		return "OK";
	}
	
	/**
	 * 初始化当前用户的指定日期对应年份的用户日历表
	 */
	public String initCurrentUserCanlendar(String logDate) {
		// Step1:获取当前用户的Id
		User cuser = (User) SessionManager.getUserSession().getSessionData()
				.get("user");
		this.getDao().initUserCanlendar(cuser.getId(), logDate);
		return "OK";

	}

}
