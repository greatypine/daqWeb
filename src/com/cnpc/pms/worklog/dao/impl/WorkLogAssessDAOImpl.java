package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.worklog.dao.WorkLogAssessDAO;
import com.cnpc.pms.worklog.dto.WorkLogAssessStisticDTO;
import com.cnpc.pms.worklog.dto.WorkLogAssessSelectDto;
import com.cnpc.pms.worklog.entity.WorkLogAssess;

public class WorkLogAssessDAOImpl extends BaseDAOHibernate implements WorkLogAssessDAO{
	public Long completeRandomSelect(WorkLogAssessSelectDto dto){
		Session session = this.getSession();
		//传过来的depts的格式必须是"val1,val2,val3"
		String whereCondition="id3 in("+dto.getDepts()+")";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//根绝传过来的组织机构条件获得这些组织机构下的所有人的Id
		StringBuffer sqlStr = new StringBuffer();
		String sqlStr1;
		String sqlStr11;
		sqlStr.append("select ID from VIEW_WORKLOG_USERCALENDAR_ORG where "+whereCondition);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		System.out.println(list.size());
		List<Long> list1=new ArrayList();
		for(Map map:list){
			list1.add(Long.parseLong(map.get("ID").toString()));
		}
		System.out.println(list1.size());
		//从这些ID里面抽10%
		Integer  counts=(list1.size()*10+100)/100;
		//生成随机数，或取随机抽取到人的userId,拼成字符串组成查询条件
		String userIds=null;
		for(int i=0;i<counts;i++){
			int j=(int)(Math.random()*list1.size());
			Long userId=list1.get(j);
			if(userIds==null){
				userIds=userId+"";
			}else{
				userIds=userIds+","+userId;
			}
		}
		sqlStr1 ="update tb_worklog_user_calendar set isSelect =0 where to_char(calendardate,'yyyy-mm-dd')>=? and to_char(calendardate,'yyyy-mm-dd')<=? "
			+"and personid in ("+userIds+")";
		session.createSQLQuery(sqlStr1).setParameters(
			new Object[] { sdf.format(dto.getBeginDate()), sdf.format(dto.getEndDate()) },
			new Type[] { Hibernate.STRING, Hibernate.STRING })
			.executeUpdate();
		sqlStr11="update tb_worklog_user_calendar set isselect=1 where  to_char(calendardate,'yyyy-mm-dd')>=? and to_char(calendardate,'yyyy-mm-dd')<=? "
			+"and personid in ("+userIds+")";

		session.createSQLQuery(sqlStr11).setParameters(
			new Object[] { sdf.format(dto.getBeginDate()), sdf.format(dto.getEndDate()) },
			new Type[] { Hibernate.STRING, Hibernate.STRING  })
			.executeUpdate();
		String sqlStr2;
		sqlStr2="insert into tb_worklog_assess(id,personid,selectdate) select id,personid, calendardate from tb_worklog_user_calendar where isselect=1 and to_char(calendardate,'yyyy-mm-dd')>=? and to_char(calendardate,'yyyy-mm-dd')<=? "
				+"and personid in ("+userIds+") "
				+"and personid||to_char(calendardate) not in(select personid||to_char(selectdate) from tb_worklog_assess)";
		session.createSQLQuery(sqlStr2).setParameters(
				new Object[] { sdf.format(dto.getBeginDate()), sdf.format(dto.getEndDate()) },
				new Type[] { Hibernate.STRING, Hibernate.STRING  })
				.executeUpdate();
		String countSql="select count(*) from tb_worklog_assess where to_char(selectdate,'yyyy-mm-dd')>=? and to_char(selectdate,'yyyy-mm-dd')<=? "
				+"and personid in ("+userIds+") ";
		Integer count=((BigDecimal) session.createSQLQuery(countSql)
				.setParameters(new Object[] { sdf.format(dto.getBeginDate()), sdf.format(dto.getEndDate()) },new Type[] { Hibernate.STRING, Hibernate.STRING  }).uniqueResult()).intValue();
		//抽取完后,该人在抽取的那一天是否填写了日志，没有填写，则自动打分为F
		String sqlStr3="update tb_worklog_assess t  set Score='F' where t.personid||to_char(t.selectdate,'yyyy-mm-dd') not in (select userId||to_char(logdate,'yyyy-mm-dd') from TB_WORKLOG where worklogtype='0') and to_char(selectdate,'yyyy-mm-dd')>=? and to_char(selectdate,'yyyy-mm-dd')<=? "
					+"and personid in ("+userIds+") ";
		session.createSQLQuery(sqlStr3).setParameters(
				new Object[] { sdf.format(dto.getBeginDate()), sdf.format(dto.getEndDate()) },
				new Type[] { Hibernate.STRING, Hibernate.STRING  })
				.executeUpdate();
		return new Long(count);
	}
	public void saveWorkLogAssess(WorkLogAssess obj){
		String sqlStr;
		String sqlStr1;
		Session session = this.getSession();
		sqlStr="select count(*) from tb_worklog_assess  where id=?";
		Integer count=((BigDecimal) session.createSQLQuery(sqlStr)
				.setParameters(new Object[] { obj.getId() },new Type[] { Hibernate.LONG}).uniqueResult()).intValue();
		
		if(count==0){//新增
			sqlStr1="insert into tb_worklog_assess(id,operId,selectDate,personId,score,state) values(?,?,?,?,?,?)";
			session.createSQLQuery(sqlStr1).setParameters(
					new Object[] { obj.getId(), obj.getOperId(),obj.getSelectDate(),obj.getPersonId(),obj.getScore(),obj.getState() },
					new Type[] { Hibernate.LONG, Hibernate.LONG, Hibernate.DATE, Hibernate.LONG, Hibernate.STRING, Hibernate.STRING  })
					.executeUpdate();
		}else if(count==1){//修改
			sqlStr1="update tb_worklog_assess set operId=?,selectDate=?,score=? where id=?";
			session.createSQLQuery(sqlStr1).setParameters(
					new Object[] { obj.getOperId(),obj.getSelectDate(),obj.getScore(),obj.getId() },
					new Type[] { Hibernate.LONG,  Hibernate.DATE, Hibernate.STRING,Hibernate.LONG  })
					.executeUpdate();
		}else{//不应该出现这种情况
			
		}
	}
	public List<WorkLogAssessStisticDTO> getWorkLogAssessStistic(PageInfo pageInfo,String stype,String d1,String d2,Long orgId,String nm){
		StringBuffer sqlStr = new StringBuffer();
		List<WorkLogAssessStisticDTO> resultList = new ArrayList<WorkLogAssessStisticDTO>();
		sqlStr.append("select USERID,USERNAME,"+nm+",POSNAME,COUNT(*),SUM(RECORDSTATE),SUM(SCOREA),SUM(SCOREB),SUM(SCOREC),SUM(SCORED),SUM(SCOREF) from view_worklog_assessstatistic where to_char(logdate,'yyyy-mm-dd')>= :beginDate and to_char(logdate,'yyyy-mm-dd')<= :endDate and (id1=:orgId or id2=:orgId or id3=:orgId or id4=:orgId) group by USERID,USERNAME,"+nm+",POSNAME");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", d1);
		query.setString("endDate",d2);
		if(orgId!=null){
			query.setLong("orgId", orgId);
		}
		//list1是为了统计总条数
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list1.size());

		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
						pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1))
		.setMaxResults(pageInfo.getRecordsPerPage()).list();
		
		if(stype.equals("Inter")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get("USERID")!=null&&map.get("USERID")!=""){
					workLogAssessStisticDTO.setUserId(Long.parseLong(map.get("USERID").toString()));
				}
				if(map.get("USERNAME")!=null&&map.get("USERNAME")!=""){
					workLogAssessStisticDTO.setUserName(map.get("USERNAME").toString());
				}
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get("POSNAME")!=null&&map.get("POSNAME")!=""){
					workLogAssessStisticDTO.setPosName(map.get("POSNAME").toString());
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
				}
				if(map.get("SUM(RECORDSTATE)")!=null&&map.get("SUM(RECORDSTATE)")!=""){
					workLogAssessStisticDTO.setRealReport(Long.parseLong(map.get("SUM(RECORDSTATE)").toString()));
				}else{
					workLogAssessStisticDTO.setRealReport(0L);
				}
				if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
					workLogAssessStisticDTO.setScoreA(map.get("SUM(SCOREA)").toString());
				}
				if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
					workLogAssessStisticDTO.setScoreB(map.get("SUM(SCOREB)").toString());
				}
				if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
					workLogAssessStisticDTO.setScoreC(map.get("SUM(SCOREC)").toString());
				}
				if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
					workLogAssessStisticDTO.setScoreD(map.get("SUM(SCORED)").toString());
				}
				if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
					workLogAssessStisticDTO.setScoreF(map.get("SUM(SCOREF)").toString());
				}
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else if(stype.equals("percent")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get("USERID")!=null&&map.get("USERID")!=""){
					workLogAssessStisticDTO.setUserId(Long.parseLong(map.get("USERID").toString()));
				}
				if(map.get("USERNAME")!=null&&map.get("USERNAME")!=""){
					workLogAssessStisticDTO.setUserName(map.get("USERNAME").toString());
				}
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get("POSNAME")!=null&&map.get("POSNAME")!=""){
					workLogAssessStisticDTO.setPosName(map.get("POSNAME").toString());
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
					if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREA)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreA(str1+"%");
					}
					if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREB)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreB(str1+"%");
					}
					if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREC)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreC(str1+"%");
					}
					if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCORED)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreD(str1+"%");
					}
					if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREF)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreF(str1+"%");
					}
				}
				
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else{
			log.error("不支持这种查询");
		}
		return resultList;
	}
	//按维度进行统计
	public List<WorkLogAssessStisticDTO> getWorkLogAssessStisticByWd(PageInfo pageInfo,String stype,String d1,String d2,Long orgId,String nm,String wd){
		StringBuffer sqlStr = new StringBuffer();
		List<WorkLogAssessStisticDTO> resultList = new ArrayList<WorkLogAssessStisticDTO>();
		sqlStr.append("select "+wd+","+nm+",COUNT(*),SUM(SCOREA),SUM(SCOREB),SUM(SCOREC),SUM(SCORED),SUM(SCOREF) from view_worklog_assessstatistic where to_char(logdate,'yyyy-mm-dd')>= :beginDate and to_char(logdate,'yyyy-mm-dd')<= :endDate and (id1=:orgId or id2=:orgId or id3=:orgId or id4=:orgId) and "+wd+">0 group by "+wd+","+nm);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", d1);
		query.setString("endDate",d2);
		if(orgId!=null){
			query.setLong("orgId", orgId);
		}
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list1.size());
		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
						pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1))
		.setMaxResults(pageInfo.getRecordsPerPage()).list();
		
		if(stype.equals("Inter")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get(wd)!=null&&map.get(wd)!=""){
					workLogAssessStisticDTO.setOrgId(Long.parseLong(map.get(wd).toString()));
				}
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
				}
				if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
					workLogAssessStisticDTO.setScoreA(map.get("SUM(SCOREA)").toString());
				}
				if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
					workLogAssessStisticDTO.setScoreB(map.get("SUM(SCOREB)").toString());
				}
				if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
					workLogAssessStisticDTO.setScoreC(map.get("SUM(SCOREC)").toString());
				}
				if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
					workLogAssessStisticDTO.setScoreD(map.get("SUM(SCORED)").toString());
				}
				if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
					workLogAssessStisticDTO.setScoreF(map.get("SUM(SCOREF)").toString());
				}
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else if(stype.equals("percent")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get(wd)!=null&&map.get(wd)!=""){
					workLogAssessStisticDTO.setOrgId(Long.parseLong(map.get(wd).toString()));
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
					if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREA)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreA(str1+"%");
					}
					if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREB)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreB(str1+"%");
					}
					if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREC)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreC(str1+"%");
					}
					if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCORED)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreD(str1+"%");
					}
					if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREF)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						String str =percentage+"0000";
						int i = str.indexOf('.');
						String str1;
						if(str.length()>5){
							str1=str.substring(0, i+3);
						}else{
							str1=str.substring(0, i+2);
						}
						workLogAssessStisticDTO.setScoreF(str1+"%");
					}
				}
				
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else{
			log.error("不支持这种查询");
		}
		//下面是该单位的合计
		StringBuffer sqlStr1 = new StringBuffer();
		Session session = this.getSession();
		String yuanNameSql="select shortName from  TB_BIZBASE_PSORG t where t.id=?";
		String yuanName=(String)session.createSQLQuery(yuanNameSql)//获取该单位的名称
						.setParameters(new Object[] { orgId },
								new Type[] { Hibernate.LONG }).uniqueResult();
		sqlStr1.append("select COUNT(*),SUM(SCOREA),SUM(SCOREB),SUM(SCOREC),SUM(SCORED),SUM(SCOREF) from view_worklog_assessstatistic where to_char(logdate,'yyyy-mm-dd')>= :beginDate and to_char(logdate,'yyyy-mm-dd')<= :endDate and (id1=:orgId or id2=:orgId or id3=:orgId or id4=:orgId)");
		SQLQuery query1 = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr1.toString());
		query1.setString("beginDate", d1);
		query1.setString("endDate",d2);
		if(orgId!=null){
			query1.setLong("orgId", orgId);
		}
		List<Map> list2 = query1.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		for(Map map:list2){
			WorkLogAssessStisticDTO workLogAssessStisticDTO1 = new WorkLogAssessStisticDTO();
			 workLogAssessStisticDTO1.setNums("合计");
			 workLogAssessStisticDTO1.setOrgName(yuanName);
			 if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
				 workLogAssessStisticDTO1.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
			 }
			 if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
					workLogAssessStisticDTO1.setScoreA(map.get("SUM(SCOREA)").toString());
				}
				if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
					workLogAssessStisticDTO1.setScoreB(map.get("SUM(SCOREB)").toString());
				}
				if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
					workLogAssessStisticDTO1.setScoreC(map.get("SUM(SCOREC)").toString());
				}
				if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
					workLogAssessStisticDTO1.setScoreD(map.get("SUM(SCORED)").toString());
				}
				if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
					workLogAssessStisticDTO1.setScoreF(map.get("SUM(SCOREF)").toString());
				}
				resultList.add(workLogAssessStisticDTO1);
		}
		return resultList;
	}
	public List<WorkLogAssessStisticDTO> getWorkLogAssessSelectAndQuery(PageInfo pageInfo,String stype,String d1,String d2,String orgId,String nm){
		StringBuffer sqlStr = new StringBuffer();
		List<WorkLogAssessStisticDTO> resultList = new ArrayList<WorkLogAssessStisticDTO>();
		String ids="id3 in ("+orgId+")";
		sqlStr.append("select USERID,USERNAME,"+nm+",POSNAME,COUNT(*),SUM(RECORDSTATE),SUM(SCOREA),SUM(SCOREB),SUM(SCOREC),SUM(SCORED),SUM(SCOREF) from view_worklog_assessstatistic where to_char(logdate,'yyyy-mm-dd')>= :beginDate and to_char(logdate,'yyyy-mm-dd')<= :endDate and "+ids+" group by USERID,USERNAME,"+nm+",POSNAME");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setString("beginDate", d1);
		query.setString("endDate",d2);
		//list1是为了统计总条数
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list1.size());

		List<Map> list = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
						pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1))
		.setMaxResults(pageInfo.getRecordsPerPage()).list();
		
		if(stype.equals("Inter")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get("USERID")!=null&&map.get("USERID")!=""){
					workLogAssessStisticDTO.setUserId(Long.parseLong(map.get("USERID").toString()));
				}
				if(map.get("USERNAME")!=null&&map.get("USERNAME")!=""){
					workLogAssessStisticDTO.setUserName(map.get("USERNAME").toString());
				}
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get("POSNAME")!=null&&map.get("POSNAME")!=""){
					workLogAssessStisticDTO.setPosName(map.get("POSNAME").toString());
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
				}
				if(map.get("SUM(RECORDSTATE)")!=null&&map.get("SUM(RECORDSTATE)")!=""){
					workLogAssessStisticDTO.setRealReport(Long.parseLong(map.get("SUM(RECORDSTATE)").toString()));
				}else{
					workLogAssessStisticDTO.setRealReport(0L);
				}
				if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
					workLogAssessStisticDTO.setScoreA(map.get("SUM(SCOREA)").toString());
				}
				if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
					workLogAssessStisticDTO.setScoreB(map.get("SUM(SCOREB)").toString());
				}
				if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
					workLogAssessStisticDTO.setScoreC(map.get("SUM(SCOREC)").toString());
				}
				if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
					workLogAssessStisticDTO.setScoreD(map.get("SUM(SCORED)").toString());
				}
				if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
					workLogAssessStisticDTO.setScoreF(map.get("SUM(SCOREF)").toString());
				}
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else if(stype.equals("percent")){
			int index=1;
			for(Map map:list){
				WorkLogAssessStisticDTO workLogAssessStisticDTO = new WorkLogAssessStisticDTO();
				workLogAssessStisticDTO.setNums(index+"");
				if(map.get("USERID")!=null&&map.get("USERID")!=""){
					workLogAssessStisticDTO.setUserId(Long.parseLong(map.get("USERID").toString()));
				}
				if(map.get("USERNAME")!=null&&map.get("USERNAME")!=""){
					workLogAssessStisticDTO.setUserName(map.get("USERNAME").toString());
				}
				if(map.get(nm)!=null&&map.get(nm)!=""){
					workLogAssessStisticDTO.setOrgName(map.get(nm).toString());
				}
				if(map.get("POSNAME")!=null&&map.get("POSNAME")!=""){
					workLogAssessStisticDTO.setPosName(map.get("POSNAME").toString());
				}
				if(map.get("COUNT(*)")!=null&&map.get("COUNT(*)")!=""){
					workLogAssessStisticDTO.setShouldReport(Long.parseLong(map.get("COUNT(*)").toString()));
					if(map.get("SUM(SCOREA)")!=null&&map.get("SUM(SCOREA)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREA)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						workLogAssessStisticDTO.setScoreA(percentage+"%");
					}
					if(map.get("SUM(SCOREB)")!=null&&map.get("SUM(SCOREB)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREB)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						workLogAssessStisticDTO.setScoreB(percentage+"%");
					}
					if(map.get("SUM(SCOREC)")!=null&&map.get("SUM(SCOREC)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREC)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						workLogAssessStisticDTO.setScoreC(percentage+"%");
					}
					if(map.get("SUM(SCORED)")!=null&&map.get("SUM(SCORED)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCORED)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						workLogAssessStisticDTO.setScoreD(percentage+"%");
					}
					if(map.get("SUM(SCOREF)")!=null&&map.get("SUM(SCOREF)")!=""){
						float percentage=(float)Integer.parseInt(map.get("SUM(SCOREF)").toString())*100/Integer.parseInt(map.get("COUNT(*)").toString());
						workLogAssessStisticDTO.setScoreF(percentage+"%");
					}
				}
				
				resultList.add(workLogAssessStisticDTO);
				index++;
			}
		}else{
			log.error("不支持这种查询");
		}
		System.out.println(resultList.size());
		return resultList;
	}
}
