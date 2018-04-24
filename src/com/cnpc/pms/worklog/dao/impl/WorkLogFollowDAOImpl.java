package com.cnpc.pms.worklog.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.worklog.dao.WorkLogFollowDAO;
import com.cnpc.pms.worklog.entity.WorkLogFollow;

public class WorkLogFollowDAOImpl extends BaseDAOHibernate implements WorkLogFollowDAO{
	public WorkLogFollow getPosAndOrg(Long userId){

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select NAME4,POSNAME,NAME3 from VIEW_WF_BIZBASE_USER where id=:userId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		WorkLogFollow w=new WorkLogFollow();
		if(list1.size()==1){
			for(Map map:list1){
				if(map.get("NAME4")!=null){
					w.setOrgName(map.get("NAME4").toString());

				}
				if(map.get("NAME3")!=null){
					w.setSuoName(map.get("NAME3").toString());

				}
				if(map.get("POSNAME")!=null){
					w.setPosName(map.get("POSNAME").toString());

				}
			}
			return w;
		}else{
			return null;
		}
	}
	public Long checkIsFollowed(Long userId,Long followId){
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select ID from TB_WORKLOG_FOLLOW where userid=:userId and followId=:followId");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
		.getCurrentSession().createSQLQuery(sqlStr.toString());
		query.setLong("userId", userId);
		query.setLong("followId", followId);
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		WorkLogFollow w=new WorkLogFollow();
		System.out.println(list1.size());
		Long ids=null;
		if(list1.size()==1){
			for(Map map1:list1){
				if(map1.get("ID")!=null){
					ids=Long.parseLong(map1.get("ID").toString());
				}
			}
			return ids;
		}else{
			return 0L;
		}
		
	}
}
