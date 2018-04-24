package com.cnpc.pms.worklog.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.IJoin;
import com.cnpc.pms.worklog.dao.WorkLogUserCalendarDAO;
import com.cnpc.pms.worklog.dto.WorkLogUserCalendarDTO;
import com.cnpc.pms.worklog.entity.WorkLogCalendar;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;

public class WorkLogUserCalendarDAOImpl extends BaseDAOHibernate implements
		WorkLogUserCalendarDAO {

	/**
	 * 批量更新用户日历的工作日状态
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean updateWorkLogUserCalendar(WorkLogUserCalendarDTO obj) {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (obj.getWorkDayState().equals(new Long(1))) {
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET workDayState= 1 where to_char(calendarDate,'yyyy-mm-dd') = ?";

		} else {
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET workDayState= 0 where to_char(calendarDate,'yyyy-mm-dd') = ?";

		}
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { sdf.format(obj.getCalendarDate()) },
				new Type[] { Hibernate.STRING }).executeUpdate();
		return true;
	}

	/**
	 * 新版本的获取状态的实现,直接获取整个对象,而不是获取其中一个字段
	 */
	public WorkLogUserCalendar getRecordState(Long userId, String logDate) {
		WorkLogUserCalendar uc = this._getWorkLogUserCalendar(userId, logDate);
		if (uc == null) {
			return null;
		} else {
			return uc;
		}

	}

	/**
	 * 根据用户Id,日志日期来检索用户日历表,看是否提交
	 */
	private String _getRecordState2(Long userId, String logdate) {
		String rState = null;
		// 构建SQL命令
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" Select RECORDSTATE from TB_WorkLog_User_Calendar ");
		sqlStr
				.append(" where personId=:userId and to_char(calendarDate,'yyyy-mm-dd')=:logdate");

		// 生成SQLQuery对象
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setString("logdate", logdate);

		// 执行查询,获取列表
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();

		// 按照数据逻辑的控制,每个用户每一日应当仅有一条记录,否则代表数据错误
		if (list.size() == 1) {
			Map map1 = (Map) list.get(0);
			rState = map1.get("RECORDSTATE").toString();
		}
		return rState;
	}

	// ------------- 本地私有方法 ----------------------------------//
	/**
	 * 输入一个SQL字符串,开发时使用,以后屏蔽掉
	 * 
	 * @param s
	 */
	private void _log(String s) {
		System.out.println("SQL:");
		System.out.println(s);
	}

	/**
	 * 单一用户的用户日历的初始化方法,一次初始化一年.<br/>
	 * 初始化时仅依据系统日历表的日期类型定义<br/>
	 * 
	 * @param userId
	 *            用户Id
	 * @param logDate
	 *            日历日期,按年来执行初始化
	 */
	public void initUserCanlendar(Long userId, String logDate) {
		// 先判断指定日期是否存在用户日历记录
		WorkLogUserCalendar uc = this._getWorkLogUserCalendar(userId, logDate);
		if (uc != null) {
			// 说明存在用户日志信息,则不需要进行重复的初始化
			return;
		}
		// 真正开始用户日志表的初始化
		// 一次初始化一年的数据记录
		String beginDate = logDate.substring(0, 4) + "-01-01"; // 年初
		String endDate = logDate.substring(0, 4) + "-12-31"; // 年末

		// 构建一条SQL命令类似

		String sqlStr = "insert into TB_WORKLOG_USER_CALENDAR (ID,PERSONID,CALENDARDATE,WORKDAYSTATE,RECORDSTATE)"
				+ " select (tb.id ||to_char(t.workday,'yyyymmdd') ) id,tb.id personId,t.workday,t.isworkday,0 "
				+ " from TB_Worklog_Calendar t ,TB_BIZBASE_USER tb "
				+ " where to_char(t.workday,'yyyy-mm-dd')>= :beginDate "
				+ " and to_char(t.workday,'yyyy-mm-dd')<=:endDate and tb.id=:userId";
		_log(sqlStr);

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", beginDate);
		query.setString("endDate", endDate);
		query.setLong("userId", userId);

		// 执行SQL命令，实现用户日志指定年份的初始化
		query.executeUpdate();

	}

	/**
	 * 根据UserId,logDate来检索唯一的一条用户日志数据
	 * 
	 * @param userId
	 *            用户Id
	 * @param logDate
	 *            日历日期，格式yyyy-mm-dd
	 * @return
	 */
	private WorkLogUserCalendar _getWorkLogUserCalendar(Long userId,
			String logDate) {
		// 构建SQL命令
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" Select * from TB_WorkLog_User_Calendar ");
		sqlStr
				.append(" where personId=:userId and to_char(calendarDate,'yyyy-mm-dd')=:logdate");

		_log(sqlStr.toString());
		// 生成SQLQuery对象
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setString("logdate", logDate);

		query.addEntity(WorkLogUserCalendar.class);

		// 执行查询,获取列表
		List<WorkLogUserCalendar> list1 = query.list();

		// 按照数据逻辑的控制,每个用户每一日应当仅有一条记录,否则代表数据错误

		if (list1.size() == 1) {
			WorkLogUserCalendar uc = list1.get(0);
			return uc;
		} else {
			return null;
		}
	}

	/**
	 * 根据输入的日期和是否是工作日,刷新用户日历表中的是否是工作日状态的字段<br>
	 * 输出: WorkDayState字段.
	 */
	public void refreshAllWorkDayState(String logDate) {
		boolean isWorkDay = false;
		Long workdayState = this._getIsWorkDayState(logDate);
		if (workdayState == null) {
			System.out.println("指定日期的工作日状态为空!" + logDate);
			return;
		}
		if ((!workdayState.equals(1L)) && (!workdayState.equals(0L))) {
			System.out.println("指定日期的工作日状态数据不合法!" + logDate + ":"
					+ workdayState.toString());
			return;
		}
		// 判定是否工作日
		isWorkDay = workdayState.equals(1L) ? true : false;

		// 假期时使用的SQL
		String strSql1 = "update tb_worklog_user_calendar  "
				+ " set workdaystate = 0 "
				+ " where to_char(tb_worklog_user_calendar.calendardate,'yyyy-mm-dd') = :logDate ";
		// 工作日时使用的SQL
		String strSql2 = " update tb_worklog_user_calendar  "
				+ " set workdaystate = "
				+ " (select (case when count(*) >0 then 0 else 1 end) from tb_worklog_leave b  "
				+ "    where b.leaveuserid = tb_worklog_user_calendar.personid  "
				+ "      and to_char(b.begindate,'yyyy-mm-dd') <= :logDate  "
				+ "      and to_char(b.enddate,  'yyyy-mm-dd') >= :logDate    ) "
				+ "  where to_char(tb_worklog_user_calendar.calendardate,'yyyy-mm-dd') = :logDate ";
		String strSql = isWorkDay ? strSql2 : strSql1;
		_log(strSql);

		// 生成SQLQuery对象
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql);
		query.setString("logDate", logDate);

		// 执行SQL命令，实现用户日志指定年份的初始化
		query.executeUpdate();

	}

	/**
	 * 根据输入的日期和是否是工作日,刷新用户日历表中的是否是工作日状态的字段<br>
	 * 仅更新指定用户在指定日期的状态，其余用户的数据不受影响。
	 * 输出: WorkDayState字段.
	 */
	public void refreshOneWorkDayState(Long userId, String logDate) {
		boolean isWorkDay = false;
		Long workdayState = this._getIsWorkDayState(logDate);
		if (workdayState == null) {
			System.out.println("指定日期的工作日状态为空!" + logDate);
			return;
		}
		if ((!workdayState.equals(1L)) && (!workdayState.equals(0L))) {
			System.out.println("指定日期的工作日状态数据不合法!" + logDate + ":"
					+ workdayState.toString());
			return;
		}
		// 判定是否工作日
		isWorkDay = workdayState.equals(1L) ? true : false;

		// 假期时使用的SQL
		String strSql1 = "update tb_worklog_user_calendar  "
				+ " set workdaystate = 0 "
				+ " where to_char(tb_worklog_user_calendar.calendardate,'yyyy-mm-dd') = :logDate"
				+ " and  personId= :userId ";
		// 工作日时使用的SQL
		String strSql2 = " update tb_worklog_user_calendar  "
				+ " set workdaystate = "
				+ " (select (case when count(*) >0 then 0 else 1 end) from tb_worklog_leave b  "
				+ "    where b.leaveuserid = tb_worklog_user_calendar.personid  "
				+ "      and to_char(b.begindate,'yyyy-mm-dd') <= :logDate  "
				+ "      and to_char(b.enddate,  'yyyy-mm-dd') >= :logDate    ) "
				+ "  where to_char(tb_worklog_user_calendar.calendardate,'yyyy-mm-dd') = :logDate "
				+ " and tb_worklog_user_calendar.personId= :userId ";

		String strSql = isWorkDay ? strSql2 : strSql1;
		_log(strSql);

		// 生成SQLQuery对象
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql);
		query.setString("logDate", logDate);
		query.setLong("userId", userId);

		// 执行SQL命令，实现用户日志指定年份的初始化
		query.executeUpdate();

	}

	/**
	 * 根据日期，从系统日历表中，检索日历的是否工作日字段出来。
	 * 
	 * @param logDate
	 * @return
	 */
	private Long _getIsWorkDayState(String logDate) {
		// 首先从数据库中检索日历表,判断日历指定日是否是工作日.
		String strSql3 = "select * from tb_worklog_calendar "
				+ " where to_char(workday,'yyyy-mm-dd')=:logDate";
		_log(strSql3);
		SQLQuery query1 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql3);
		query1.setString("logDate", logDate);
		query1.addEntity(WorkLogCalendar.class);
		List<WorkLogCalendar> list1 = query1.list();
		if (list1 == null || list1.size() == 0) {
			// 指定日期在日历表中不存在,不处理
			System.out.println("指定日期在日历表中不存在!" + logDate);
			return null;
		} else {
			return ((WorkLogCalendar) list1.get(0)).getIsworkday();
		}
	}

}
