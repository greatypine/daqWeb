package com.cnpc.pms.personal.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.OnLineHumanresourcesDao;

public class OnLineHumanresourcesDaoImpl extends BaseDAOHibernate implements OnLineHumanresourcesDao{

	@Override
	public String queryMaxInviteCode() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(inviteCode)+1 as maxInviteCode FROM t_online_humanresources");
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql.toString());
		//获得查询数据
        List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        String maxInviteCode = null;
        for(Object o:lst_data){
        	Map<String, Object> map = (Map<String, Object>)o;
        	maxInviteCode=map.get("maxInviteCode")==null?"":map.get("maxInviteCode").toString().substring(0,6);
        }
		return maxInviteCode;
	}
	
}
