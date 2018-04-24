package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.worklog.dao.WorkLogLeaveDAO;

public class WorkLogLeaveDAOImpl extends BaseDAOHibernate implements
		WorkLogLeaveDAO {

	public void updateWorkLogUserCalendar(String dates, Long userId) {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET workDayState= 0 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId =?";
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { dates, userId },
				new Type[] { Hibernate.STRING, Hibernate.LONG })
				.executeUpdate();
	}

	public void updateWorkLogUserCalendarBack(String dates, Long userId) {
		String sqlStr;
		Session session = this.getSession();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sqlStr = "UPDATE TB_WORKLOG_USER_CALENDAR SET workDayState= 1 where to_char(calendarDate,'yyyy-mm-dd') = ? and personId =?";
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { dates, userId },
				new Type[] { Hibernate.STRING, Hibernate.LONG })
				.executeUpdate();
	}
	public String checkLeave(Long userId,String date1,String date2){
		String sqlStr;
		Session session = this.getSession();
		sqlStr ="select count(*) from TB_WORKLOG where userid=? and to_char(logdate,'yyyy-mm-dd')>=? and to_char(logdate,'yyyy-mm-dd')<=?";
		Integer count=((BigDecimal) session.createSQLQuery(sqlStr)
				.setParameters(new Object[] { userId,date1,date2 },new Type[] { Hibernate.LONG,Hibernate.STRING,Hibernate.STRING }).uniqueResult()).intValue();
		if(count>=1){
			return "休假日期有重复";
		}else{
			return "休假日期无重复";
		}
	}
	public Long getUserOrgId(Long userId){
		StringBuffer strSql = new StringBuffer();
		Long orgId=null;
		strSql.append("select POSCODE,ID3,ID4 from  VIEW_WORKLOG_BIZBASE_USER where id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(strSql.toString());
		query.setLong("userId", userId);
		List<Map> list = (List<Map>)query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(list.size());
		if(list.size()==1){
			for(Map map:list){
				if(map.get("POSCODE")!=null){
					if(map.get("POSCODE").toString().equals("PTRY")){
						orgId=Long.parseLong(map.get("ID4").toString());
					}else if(map.get("POSCODE").toString().equals("KYMS")||map.get("POSCODE").toString().equals("SBZR")){
						 orgId=Long.parseLong(map.get("ID3").toString());
					}else{
						orgId=null;
					}
				}
			}
		}
		
		return orgId;
	}
}
