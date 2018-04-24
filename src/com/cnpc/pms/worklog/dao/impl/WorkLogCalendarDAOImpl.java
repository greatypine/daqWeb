package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.worklog.dao.WorkLogCalendarDAO;
import com.cnpc.pms.worklog.entity.WorkLogCalendar;

public class WorkLogCalendarDAOImpl extends BaseDAOHibernate implements
		WorkLogCalendarDAO {

	public Boolean getYearExits(String Date, Long Days) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr
				.append("Select * from TB_WORKLOG_USER_CALENDAR where to_char(calendarDate,'yyyy') = :calendarDate");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("calendarDate", Date);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list.size() > 0) {
			return true;// 表示已初始化
		} else {
			return false;// 表示未初始化
		}

	}

	public WorkLogCalendar getWorkLogCalendar(String date) {
		StringBuffer sqlStr = new StringBuffer();
		WorkLogCalendar workLogCalendar = new WorkLogCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr
				.append("Select * from TB_WORKLOG_CALENDAR where to_char(workday,'yyyy-mm-dd') = :workday");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("workday", date);
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (list.size() == 1) {
			for (Map map : list) {
				workLogCalendar.setDays(map.get("DAYS").toString());
				workLogCalendar.setId(Long.parseLong(map.get("ID").toString()));
			}
		}
		return workLogCalendar;
	}

	/**
	 * 初始化用户日历数据
	 * <li>id = userid * 10000 + calendar.id,保证id是不重复的
	 * <li>workdaystate 是否工作日
	 * <li>利用Tb_bizbase_user表和Tb_Worklog_Calendar两张表交叉来计算得到数据.
	 * @param d1 开始日期，格式yyyy-mm-dd
	 * @param d2 结束日期，格式yyyy-mm-dd
	 */
	public void formatCaleState(String d1, String d2) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr
				.append("insert into TB_WORKLOG_USER_CALENDAR (ID,PERSONID,WORKDAYSTATE,CALENDARDATE) ");
		sqlStr
				.append(" select (tb.id*10000+t.id) id,tb.id personId,t.isworkday workdaystate,t.workday calendardate from TB_Worklog_Calendar t ,TB_BIZBASE_USER tb ");
		sqlStr
				.append("where to_char(t.workday,'yyyy-mm-dd')>= :beginDate and to_char(t.workday,'yyyy-mm-dd')<= :endDate");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", d1);
		query.setString("endDate", d2);
		query.executeUpdate();
	}
	public Boolean checkExistCalendar(){
		String sqlStr;
		Session session = this.getSession();
		sqlStr="select count(*) from TB_WORKLOG_CALENDAR";
		Integer count=((BigDecimal) session.createSQLQuery(sqlStr)
				.setParameters(new Object[] {},new Type[] {}).uniqueResult()).intValue();
		System.out.println(count);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	public void formatPersonCaleState(Long userId,String beginDate,String endDate){
		System.out.println(userId+"--"+beginDate+"--"+endDate);
		StringBuffer sqlStr = new StringBuffer();
		sqlStr
				.append("insert into TB_WORKLOG_USER_CALENDAR (ID,PERSONID,WORKDAYSTATE,CALENDARDATE) ");
		sqlStr
				.append(" select (tb.id*10000+t.id) id,tb.id personId,t.isworkday workdaystate,t.workday calendardate from TB_Worklog_Calendar t ,TB_BIZBASE_USER tb ");
		sqlStr
				.append("where to_char(t.workday,'yyyy-mm-dd')>= :beginDate and to_char(t.workday,'yyyy-mm-dd')<= :endDate and tb.id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", beginDate);
		query.setString("endDate", endDate);
		query.setLong("userId", userId);
		query.executeUpdate();
	}
}
