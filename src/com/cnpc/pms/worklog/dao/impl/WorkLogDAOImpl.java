package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.util.PropertiesUtil;

import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.worklog.dao.WorkLogDAO;

public class WorkLogDAOImpl extends BaseDAOHibernate implements WorkLogDAO {
	public List<Map> getLastCreateWorkLogUsers(User cuser) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT U.* ");
		sqlStr.append("  FROM TB_WORKLOG WL");
		sqlStr.append("  LEFT JOIN TB_WORKLOG_COPY WLCP");
		sqlStr.append("    ON WL.ID = WLCP.WORKLOGID");
		sqlStr.append("  LEFT JOIN TB_BIZBASE_USER U");
		sqlStr.append("    ON WLCP.TOUSERID = U.ID");
		sqlStr.append(" WHERE WL.USERID = :userid");
		sqlStr.append("   AND NOT EXISTS (SELECT 1 FROM TB_WORKLOG T WHERE T.ID > WL.ID)");

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		Long userId = cuser.getId();
		query.setLong("userid", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	public Boolean checkWorLogExists(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		int flag = 0;
		sqlStr.append("Select WORKLOGTYPE from TB_WORKLOG where to_char(logDate,'yyyy-mm-dd')= :logDate and userId=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("logDate", logDate);
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();

		for (Map temp : list) {
			if (temp.get("WORKLOGTYPE").toString().trim().equals("1")) {
				flag = 1;
			}
		}
		// WORKLOGTYPE="1"，表示休假，ENABLESTATUS==“0”表示逻辑上已删除
		/*
		 * for(int i=0;i<list.size();i++){
		 * if(list.get(i).get("WORKLOGTYPE")=="1"
		 * ||(list.get(i).get("WORKLOGTYPE"
		 * )=="0"&&list.get(i).get("ENABLESTATUS")=="0")){ flag=1; }; }
		 */
		if (flag == 1) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean checkWorLogExistsThreeDays(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		int flag = 0;
		sqlStr.append("Select WORKLOGTYPE from TB_WORKLOG where to_char(logDate,'yyyy-mm-dd')= :logDate and userId=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("logDate", logDate);
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void formatCaleState(String d1, String d2) {
		Session tx = this.getSession();
		tx.beginTransaction();
		Connection con = tx.connection();
		String procedure = "{call sp_query_worklog(?,?)}";
		try {
			CallableStatement cstmt = con.prepareCall(procedure);
			cstmt.setString(1, d1);
			cstmt.setString(2, d2);
			cstmt.executeUpdate();
			((Connection) tx).commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateUserCalendar(String date1, Long userId) {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET recordState = 1 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
		session.createSQLQuery(sqlStr)
				.setParameters(new Object[] { date1, userId },
						new Type[] { Hibernate.STRING, Hibernate.LONG })
				.executeUpdate();
	}

	public void updateUserCalendarBack(String date1, Long userId) {
		String sqlStr;
		String sqlStr1;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(date1 + "--" + userId);
		sqlStr1 = "select COUNT(*) from TB_WORKLOG where   to_char(logdate,'yyyy-mm-dd') = ? and userid = ?";
		Integer counts = ((BigDecimal) session
				.createSQLQuery(sqlStr1)
				.setParameters(new Object[] { date1, userId },
						new Type[] { Hibernate.STRING, Hibernate.LONG })
				.uniqueResult()).intValue();
		System.out.println(counts);
		if (counts <= 1) {// 删除后若该人在当日没有填写其他的日志了，则recordState =''
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET recordState ='',toUserIds='',toUserNames='',toUserCodes='',commitdate='' where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
			session.createSQLQuery(sqlStr)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.executeUpdate();
		} else {// 删除后若该人在当日还有有填写其他的日志，则recordState =1
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET recordState =1  where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
			session.createSQLQuery(sqlStr)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.executeUpdate();
		}
	}

	public void updateUserCalendarOntimeState(String date1, String date2,
			Long userId) {
		String sqlStr;
		String sqlStr1;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (date1.equals(date2)) {// 日志日期与填写日期相等，及时上报
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET ontimestate = 1 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
			session.createSQLQuery(sqlStr)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.executeUpdate();
		} else {// 日志日期与填写日期不相等，去查看该人在当日是否填写了其他日志，是否存在日志日期与填写日期相等的日志，若没有则ontimestate
				// = 0，有则ontimestate = 1
			sqlStr1 = "SELECT COUNT(*) FROM TB_WORKLOG WHERE to_char(logdate,'yyyy-mm-dd') = ? AND USERID= ?  AND to_char(inputdate,'yyyy-mm-dd')=to_char(logdate,'yyyy-mm-dd')";
			Integer counts = ((BigDecimal) session
					.createSQLQuery(sqlStr1)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.uniqueResult()).intValue();
			System.out.println(counts);
			if (counts <= 0) {
				sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET ontimestate = 0 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
				session.createSQLQuery(sqlStr)
						.setParameters(new Object[] { date1, userId },
								new Type[] { Hibernate.STRING, Hibernate.LONG })
						.executeUpdate();
			} else {// 该日填写了多条日志，取出其他的日志，比较其填写日期和日志日期
				sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET ontimestate = 1 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
				session.createSQLQuery(sqlStr)
						.setParameters(new Object[] { date1, userId },
								new Type[] { Hibernate.STRING, Hibernate.LONG })
						.executeUpdate();
			}
		}
	}

	public void updateUserCalendarOntimeStateBack(String date1, Long userId) {
		String sqlStr;
		String sqlStr1;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr1 = "SELECT COUNT(*) FROM TB_WORKLOG WHERE to_char(logdate,'yyyy-mm-dd') = ? AND USERID= ?  AND to_char(inputdate,'yyyy-mm-dd')=to_char(logdate,'yyyy-mm-dd')";
		Integer counts = ((BigDecimal) session
				.createSQLQuery(sqlStr1)
				.setParameters(new Object[] { date1, userId },
						new Type[] { Hibernate.STRING, Hibernate.LONG })
				.uniqueResult()).intValue();
		if (counts <= 1) {
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET ontimestate = '' where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
			session.createSQLQuery(sqlStr)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.executeUpdate();
		} else {
			sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET ontimestate = 1 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
			session.createSQLQuery(sqlStr)
					.setParameters(new Object[] { date1, userId },
							new Type[] { Hibernate.STRING, Hibernate.LONG })
					.executeUpdate();
		}

	}

	public BigDecimal getTotalHours(Long userId, String date) {
		String sqlStr;
		Session session = this.getSession();
		sqlStr = "select SUM(HOURS) from TB_WORKLOG where userId=? and to_char(logdate,'yyyy-mm-dd')=?";
		// if db.type=MYSQL then change the sql code.
		String dbtype = PropertiesUtil.getValue("db.type");
		// get dbtype from application.xml
		if (dbtype != null && dbtype.equals("MYSQL")) {
			//IF this is MYSQL,then change SQL code.
			sqlStr = "select SUM(HOURS) from TB_WORKLOG where userId=? and date_format(logdate,'%Y-%m-%d')=?";
		}
		BigDecimal counts = ((BigDecimal) session
				.createSQLQuery(sqlStr)
				.setParameters(new Object[] { userId, date },
						new Type[] { Hibernate.LONG, Hibernate.STRING })
				.uniqueResult());
		return counts;
	}

	public List<Long> getWorkLogId(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("Select ID from TB_WORKLOG where to_char(logDate,'yyyy-mm-dd')= :logDate and userId=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("logDate", logDate);
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		List<Long> list1 = new ArrayList();
		for (Map map : list) {
			list1.add(Long.parseLong(map.get("ID").toString()));
		}
		return list1;
	}

	public List<String> getLogdatesList(Long userId, String beginDate,
			String endDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select DISTINCT(CALENDARDATE) from TB_WorkLog_User_Calendar where personId=:userId and  recordState=1");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		// query.setString("beginDate", beginDate);
		// query.setString("endDate", endDate);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		List<String> list1 = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(list.size());
		for (Map map : list) {

			System.out.println(map.get("CALENDARDATE").toString());
			list1.add(map.get("CALENDARDATE").toString().substring(0, 10));
		}
		return list1;
	}

	public String getInputDate(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select MIN(INPUTDATE) from TB_WORKLOG where userId=:userId and to_char(logdate,'yyyy-mm-dd')=:logDate and worklogtype=0 ");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setString("logDate", logDate);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		String inpudate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(list.size());
		for (Map map : list) {

			if (map.get("MIN(INPUTDATE)") != null
					&& map.get("MIN(INPUTDATE)") != "") {
				inpudate = map.get("MIN(INPUTDATE)").toString()
						.substring(0, 10);
				System.out.println(inpudate);
			}

		}
		return inpudate;
	}

	public void updateUserCalendarAndComitDate(String date1, Long userId,
			String commitDate) throws ParseException {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dates = new Date();
		System.out.println(dates);
		sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET recordState = 1,commitdate=? where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";
		try {
			session.createSQLQuery(sqlStr)
					.setParameters(
							new Object[] { dates, date1, userId },
							new Type[] { Hibernate.TIMESTAMP, Hibernate.STRING,
									Hibernate.LONG }).executeUpdate();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCommitDate(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select COMMITDATE from TB_WORKLOG_USER_CALENDAR where to_char(calendarDate,'yyyy-mm-dd') = :logDate and personId = :userId and recordState = 1");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setString("logDate", logDate);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		String commitdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(list.size());
		for (Map map : list) {

			if (map.get("COMMITDATE") != null && map.get("COMMITDATE") != "") {
				commitdate = map.get("COMMITDATE").toString().substring(0, 10);
				System.out.println(commitdate);
			}

		}
		return commitdate;
	}

	public void updateUserCalendarAndCopy(String logDate, Long userId,
			String toUserCodes, String toUserNames, String toUserIds) {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET recordState = 1,toUserIds=?,toUserNames=?,toUserCodes=? where to_char(calendarDate,'yyyy-mm-dd') = ? and personId = ?";

		session.createSQLQuery(sqlStr)
				.setParameters(
						new Object[] { ',' + toUserIds + ',', toUserNames,
								toUserCodes, logDate, userId },
						new Type[] { Hibernate.STRING, Hibernate.STRING,
								Hibernate.STRING, Hibernate.STRING,
								Hibernate.LONG }).executeUpdate();

	}

	public Long getUserCanlendarId(Long userId, String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID from TB_WORKLOG_USER_CALENDAR where to_char(calendarDate,'yyyy-mm-dd') = :logDate and personId = :userId and recordState = 1");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setString("logDate", logDate);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		Long userCanlendarId = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(list.size());
		for (Map map : list) {

			if (map.get("ID") != null && map.get("ID") != "") {
				userCanlendarId = Long.parseLong(map.get("ID").toString());
				System.out.println(userCanlendarId);
			}

		}
		return userCanlendarId;
	}

	public PurStruOrgDTO getYuanNameAndId(Long userId) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID2,NAME2 from VIEW_WORKLOG_USERCALENDAR_ORG where  id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		PurStruOrgDTO p = new PurStruOrgDTO();
		for (Map map : list) {

			if (map.get("ID2") != null && map.get("ID2") != "") {
				p.setId(Long.parseLong(map.get("ID2").toString()));
			}
			if (map.get("NAME2") != null && map.get("NAME2") != "") {
				p.setName(map.get("NAME2").toString());
			}

		}
		return p;
	}

	public PurStruOrgDTO getSuoNameAndId(Long userId) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID3,NAME3 from VIEW_WORKLOG_USERCALENDAR_ORG where  id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		PurStruOrgDTO p = new PurStruOrgDTO();
		for (Map map : list) {

			if (map.get("ID3") != null && map.get("ID3") != "") {
				p.setId(Long.parseLong(map.get("ID3").toString()));
			}
			if (map.get("NAME3") != null && map.get("NAME3") != "") {
				p.setName(map.get("NAME3").toString());
			}

		}
		return p;
	}

	public PurStruOrgDTO getShiNameAndId(Long userId) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID4,NAME4 from VIEW_WORKLOG_USERCALENDAR_ORG where  id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		PurStruOrgDTO p = new PurStruOrgDTO();
		for (Map map : list) {

			if (map.get("ID4") != null && map.get("ID4") != "") {
				p.setId(Long.parseLong(map.get("ID4").toString()));
			}
			if (map.get("NAME4") != null && map.get("NAME4") != "") {
				p.setName(map.get("NAME4").toString());
			}

		}
		return p;
	}

	public List<Long> getUseIds(String logDate) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID from TB_BIZBASE_USER where  id not in (select personid from TB_WORKLOG_USER_CALENDAR where to_char(calendardate,'yyyy-mm-dd')=:logDate)");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("logDate", logDate);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		List<Long> list1 = new ArrayList();
		for (Map map : list) {
			if (map.get("ID") != null) {
				list1.add(Long.parseLong(map.get("ID").toString()));
			}
		}
		return list1;
	}

	public List<PurStruOrg> getPurStruOrgDTO() {
		String strSql = "select * from tb_bizbase_psorg where entityorgflag = 1 "
				+ " order by orderno";
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql);
		query.addEntity(PurStruOrg.class);
		List<PurStruOrg> list1 = query.list();
		System.out.println(list1.size());
		return list1;

	}
}
