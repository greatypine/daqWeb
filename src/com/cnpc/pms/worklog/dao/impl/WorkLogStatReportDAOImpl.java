package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;

import com.cnpc.pms.worklog.dao.WorkLogStatDAO;
import com.cnpc.pms.worklog.dao.WorkLogStatReportDAO;
import com.cnpc.pms.worklog.dto.WorkLogStatByBranchOrgDTO;
import com.cnpc.pms.worklog.entity.WorkLogAssess;
import com.cnpc.pms.worklog.entity.WorkLogStatReport;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;

/**
 * 工作日志统计所使用的DAO方法的实现
 * 
 * @author liujunsong
 * 
 */
public class WorkLogStatReportDAOImpl extends BaseDAOHibernate implements
		WorkLogStatReportDAO {
	
	//以年为维度，将工作日志统计相关数据插入TB_WorkLog_StatReport
	public void insertStatReportByYear(String logDate){
		Session session=this.getSession();
		String sqlStr;
		sqlStr ="insert into tb_worklog_statreport "
			+" (id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType) "
			+" select  HIBERNATE_SEQUENCE.nextval id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType from "
			+" (select '13', 1 stattype,id2 statId,name2 statName,yearbegin beginDate,"
			  +   "  sum(t.recordstate) recordstate,"
			   +   " sum(workdaystate) workdaystate,"
			    +  " sum(ontimestate) ontimeState,"
			     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
			      + " sum(hours) hours,"
			       + " 3 as statDateType,"
			       + " jobtype"
			 + " from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by id2,name2,yearbegin,jobtype"
			 +" union all"
			 +" select '23', 2 stattype,id3 statId,name3 statName,yearbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 3 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by id3,name3,yearbegin,jobtype"
			 +" union all"
			  +" select '33', 3 stattype,personid statId,t.name statName,yearbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 3 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by personid,name,yearbegin,jobtype"
			 +" union all"
			 +" select '43', 1 stattype,id2 statId,name2 statName,yearbegin beginDate,"
			  +   "  sum(t.recordstate) recordstate,"
			   +   " sum(workdaystate) workdaystate,"
			    +  " sum(ontimestate) ontimeState,"
			     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
			      + " sum(hours) hours,"
			       + " 3 as statDateType,"
			       + " -1 as jobtype"
			 + " from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by id2,name2,yearbegin"
			 +" union all"
			 +" select '53', 2 stattype,id3 statId,name3 statName,yearbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 3 as statDateType,"
			       +"  -1 as jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by id3,name3,yearbegin"
			 +" union"
			  +" select '63', 3 stattype,personid statId,t.name statName,yearbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 3 as statDateType,"
			       +"  -1 as jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(yearbegin,'yyyy')=?"
			 +" group by personid,name,yearbegin)"
			 ;
		System.out.println(sqlStr);
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { logDate,logDate,logDate,logDate,logDate,logDate  },
				new Type[] { Hibernate.STRING, Hibernate.STRING, Hibernate.STRING,Hibernate.STRING, Hibernate.STRING, Hibernate.STRING  })
				.executeUpdate();
	}
	public void deleteStatReportByYear(String logDate){
		Session session=this.getSession();
		String sqlStr;
		sqlStr ="delete from tb_worklog_statreport where to_char(begindate,'yyyy')=?";
		System.out.println(sqlStr);
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { logDate },
				new Type[] { Hibernate.STRING  })
				.executeUpdate();
	}
	public void insertStatReportByMonth(String logDate){
		Session session=this.getSession();
		String sqlStr;
		sqlStr ="insert into tb_worklog_statreport "
			+" (id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType) "
			+" select  HIBERNATE_SEQUENCE.nextval id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType from "
			+" (select '13', 1 stattype,id2 statId,name2 statName,monthbegin beginDate,"
			  +   "  sum(t.recordstate) recordstate,"
			   +   " sum(workdaystate) workdaystate,"
			    +  " sum(ontimestate) ontimeState,"
			     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
			      + " sum(hours) hours,"
			       + " 2 as statDateType,"
			       + " jobtype"
			 + " from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
			 +" group by id2,name2,monthbegin,jobtype"
			 +" union all"
			 +" select '23', 2 stattype,id3 statId,name3 statName,monthbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 2 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
			 +" group by id3,name3,monthbegin,jobtype"
			 +" union all"
			  +" select '33', 3 stattype,personid statId,t.name statName,monthbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 2 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
			 +" group by personid,name,monthbegin,jobtype"
			 +" union all"
				+" select '43', 1 stattype,id2 statId,name2 statName,monthbegin beginDate,"
				  +   "  sum(t.recordstate) recordstate,"
				   +   " sum(workdaystate) workdaystate,"
				    +  " sum(ontimestate) ontimeState,"
				     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
				      + " sum(hours) hours,"
				       + " 2 as statDateType,"
				       + " -1 as jobtype"
				 + " from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
				 +" group by id2,name2,monthbegin"
				 +" union all"
				 +" select '53', 2 stattype,id3 statId,name3 statName,monthbegin beginDate,"
				       +" sum(t.recordstate) recordstate,"
				       +" sum(workdaystate) workdaystate,"
				       +" sum(ontimestate) ontimeState,"
				       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
				       +" sum(hours) hours,"
				       +" 2 as statDateType,"
				       +"  -1 as jobtype"
				 +" from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
				 +" group by id3,name3,monthbegin"
				 +" union all"
				  +" select '63', 3 stattype,personid statId,t.name statName,monthbegin beginDate,"
				       +" sum(t.recordstate) recordstate,"
				       +" sum(workdaystate) workdaystate,"
				       +" sum(ontimestate) ontimeState,"
				       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
				       +" sum(hours) hours,"
				       +" 2 as statDateType,"
				       +"  -1 as jobtype"
				 +" from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(monthbegin,'yyyy')=?"
				 +" group by personid,name,monthbegin)"
			 ;
		System.out.println(sqlStr);
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { logDate,logDate,logDate,logDate,logDate,logDate  },
				new Type[] { Hibernate.STRING, Hibernate.STRING, Hibernate.STRING,Hibernate.STRING, Hibernate.STRING, Hibernate.STRING   })
				.executeUpdate();
	}
	public void insertStatReportByWeek(String logDate){
		Session session=this.getSession();
		String sqlStr;
		sqlStr ="insert into tb_worklog_statreport "
			+" (id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType) "
			+" select  HIBERNATE_SEQUENCE.nextval id,stattype,statid,statname,begindate,recordstate,workdaystate,ontimeState,uprate,ontimeRate,hours,statDateType,jobType from "
			+" (select '13', 1 stattype,id2 statId,name2 statName,weekbegin beginDate,"
			  +   "  sum(t.recordstate) recordstate,"
			   +   " sum(workdaystate) workdaystate,"
			    +  " sum(ontimestate) ontimeState,"
			     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
			      + " sum(hours) hours,"
			       + " 1 as statDateType,"
			       + " jobtype"
			 + " from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
			 +" group by id2,name2,weekbegin,jobtype"
			 +" union all"
			 +" select '23', 2 stattype,id3 statId,name3 statName,weekbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 1 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
			 +" group by id3,name3,weekbegin,jobtype"
			 +" union all"
			  +" select '33', 3 stattype,personid statId,t.name statName,weekbegin beginDate,"
			       +" sum(t.recordstate) recordstate,"
			       +" sum(workdaystate) workdaystate,"
			       +" sum(ontimestate) ontimeState,"
			       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
			       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
			       +" sum(hours) hours,"
			       +" 1 as statDateType,"
			       +"  jobtype"
			 +" from view_worklog_logstat t"
			 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
			 +" group by personid,name,weekbegin,jobtype"
			 +" union all"
				+" select '43', 1 stattype,id2 statId,name2 statName,weekbegin beginDate,"
				  +   "  sum(t.recordstate) recordstate,"
				   +   " sum(workdaystate) workdaystate,"
				    +  " sum(ontimestate) ontimeState,"
				     + " (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				     + " (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate," 
				      + " sum(hours) hours,"
				       + " 1 as statDateType,"
				       + " -1 as jobtype"
				 + " from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
				 +" group by id2,name2,weekbegin"
				 +" union all"
				 +" select '53', 2 stattype,id3 statId,name3 statName,weekbegin beginDate,"
				       +" sum(t.recordstate) recordstate,"
				       +" sum(workdaystate) workdaystate,"
				       +" sum(ontimestate) ontimeState,"
				       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
				       +" sum(hours) hours,"
				       +" 1 as statDateType,"
				       +"  -1 as jobtype"
				 +" from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
				 +" group by id3,name3,weekbegin"
				 +" union"
				  +" select '63', 3 stattype,personid statId,t.name statName,weekbegin beginDate,"
				       +" sum(t.recordstate) recordstate,"
				       +" sum(workdaystate) workdaystate,"
				       +" sum(ontimestate) ontimeState,"
				       +" (case when sum(workdaystate)>0 then sum(t.recordstate)/sum(workdaystate) else 0 end ) uprate,"
				       +" (case when sum(recordstate)>0 then  sum(ontimestate)/ sum(recordstate) else 0 end) ontimeRate,"
				       +" sum(hours) hours,"
				       +" 1 as statDateType,"
				       +"  -1 as jobtype"
				 +" from view_worklog_logstat t"
				 +" where id3>0 and orderno3>0 and to_char(weekbegin,'yyyy')=?"
				 +" group by personid,name,weekbegin)"
				 ;
		System.out.println(sqlStr);
		session.createSQLQuery(sqlStr).setParameters(
				new Object[] { logDate,logDate,logDate,logDate,logDate,logDate  },
				new Type[] { Hibernate.STRING, Hibernate.STRING, Hibernate.STRING,Hibernate.STRING, Hibernate.STRING, Hibernate.STRING   })
				.executeUpdate();
	}
	public List<WorkLogStatByBranchOrgDTO> getList(String statType,String statDateType,Long jobType,Long statId,String beginDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		StringBuffer sqlStr=new StringBuffer();
		String jobTypeCondition=null;
		//if(jobType.equals(-1L)){//全体用功类型
			jobTypeCondition="(0,1,2,3)";
		//}else{
			jobTypeCondition="("+jobType+")";
		//}
		sqlStr.append("select * from TB_WorkLog_StatReport where statType=:statType and statDateType=:statDateType"
				+" and jobType in"+jobTypeCondition+" and to_char(beginDate,'yyyy-mm-dd')=:beginDate and statId in (select id from tb_bizbase_psorg where entityorgflag=1) ");
		System.out.println("statType:"+statType+"--statDateType:"+statDateType+"--beginDate:"+beginDate);
		System.out.println(sqlStr);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("statType", statType);
		query.setString("statDateType", statDateType);
		query.setString("beginDate", beginDate);
		//query.setLong("statId", statId);
		query.addEntity(WorkLogStatReport.class);
		List<WorkLogStatReport> list=query.list();
		List<WorkLogStatByBranchOrgDTO> returnList=new ArrayList<WorkLogStatByBranchOrgDTO>();
		for(WorkLogStatReport obj:list){
			WorkLogStatByBranchOrgDTO dto=this._convertMapToBranchDTO(obj);
			returnList.add(dto);
		}
		// 计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : returnList) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(totaldto.getHours() + dto.getHours());
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		returnList.add(totaldto);
		return returnList;
	}
	public List<WorkLogStatByBranchOrgDTO> getDeptList(String statType,String statDateType,Long jobType,Long statId,String beginDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		StringBuffer sqlStr=new StringBuffer();
		String jobTypeCondition=null;
		//if(jobType.equals(-1L)){//全体用功类型
			//jobTypeCondition="(0,1,2,3)";
		//}else{
			jobTypeCondition="("+jobType+")";
		//}
		sqlStr.append("select * from TB_WorkLog_StatReport where statType=:statType and statDateType=:statDateType"
				+" and jobType in "+jobTypeCondition+" and to_char(beginDate,'yyyy-mm-dd')=:beginDate"
				+" and statId in (select id from tb_bizbase_psorg where entityorgflag=2  and parent_id=:statId and logorderno>0) ");
		System.out.println(sqlStr);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("statType", statType);
		query.setString("statDateType", statDateType);
		query.setString("beginDate", beginDate);
		query.setLong("statId", statId);
		query.addEntity(WorkLogStatReport.class);
		List<WorkLogStatReport> list=query.list();
		List<WorkLogStatByBranchOrgDTO> returnList=new ArrayList<WorkLogStatByBranchOrgDTO>();
		for(WorkLogStatReport obj:list){
			WorkLogStatByBranchOrgDTO dto=this._convertMapToBranchDTO(obj);
			returnList.add(dto);
		}
		// 计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : returnList) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(Double.valueOf(totaldto.getHours().toString().substring(0, totaldto.getHours().toString().indexOf(".")+2))
					+ Double.valueOf(dto.getHours().toString().substring(0, dto.getHours().toString().indexOf(".")+2)));		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		returnList.add(totaldto);
		return returnList;
	}
	public List<WorkLogStatByBranchOrgDTO> getUserList(String statType,String statDateType,Long jobType,Long statId,String beginDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		StringBuffer sqlStr=new StringBuffer();
		String jobTypeCondition=null;
		//if(jobType.equals(-1L)){//全体用功类型
			//jobTypeCondition="(0,1,2,3)";
		//}else{
			jobTypeCondition="("+jobType+")";
		//}
		sqlStr.append("select * from TB_WorkLog_StatReport where statType=:statType and statDateType=:statDateType"
				+" and jobType in "+jobTypeCondition+" and to_char(beginDate,'yyyy-mm-dd')=:beginDate"
				+" and statId in (select id from view_worklog_bizbase_user  where  id3=:statId or id4=:statId  )");
		System.out.println(sqlStr);
		System.out.println(statId);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("statType", statType);
		query.setString("statDateType", statDateType);
		query.setString("beginDate", beginDate);
		query.setLong("statId", statId);
		query.addEntity(WorkLogStatReport.class);
		List<WorkLogStatReport> list=query.list();
		List<WorkLogStatByBranchOrgDTO> returnList=new ArrayList<WorkLogStatByBranchOrgDTO>();
		for(WorkLogStatReport obj:list){
			WorkLogStatByBranchOrgDTO dto=this._convertMapToBranchDTO(obj);
			returnList.add(dto);
		}
		// 计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : returnList) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(totaldto.getHours() + dto.getHours());
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		returnList.add(totaldto);
		return returnList;
	}
	
	private WorkLogStatByBranchOrgDTO _convertMapToBranchDTO(WorkLogStatReport obj){
		WorkLogStatByBranchOrgDTO dto=new WorkLogStatByBranchOrgDTO();
		dto.setHours(Double.valueOf(obj.getHours().toString()));
		if (obj.getStatId()!= null) {
			dto.setId(obj.getStatId());
		}
		if (obj.getStatName()!= null) {
			dto.setName(obj.getStatName());
		}
		if (obj.getRecordState()!= null) {
			dto.setRecordState(obj.getRecordState());

		}
		if (obj.getNoOntimeState()!= null) {
			dto.setOuttimeState(obj.getNoOntimeState());

		}
		if (obj.getWorkDayState()!= null) {
			dto.setWorkdayState(obj.getWorkDayState());

		}
		if (obj.getUpRate()!= null) {
			dto.setCommitRate(obj.getUpRate());

			dto.setCommitPercenter((int) (obj.getUpRate() * 10000 + 0.5)
					/ 100.0 + "%");
		} else {
			dto.setCommitPercenter("0.00%");

		}
		if (obj.getOnTimeRate() != null) {
			dto.setOnTimeRate(obj.getOnTimeRate());

			dto.setOnTimePercenter((int) (obj.getOnTimeRate()* 10000 + 0.5)
					/ 100.0 + "%");

		}
		if (obj.getHours() != null) {
			dto.setHours(Double.valueOf(obj.getHours().toString()));


		}
		
		return dto;
	} 
}
