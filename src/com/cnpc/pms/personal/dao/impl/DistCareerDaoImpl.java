package com.cnpc.pms.personal.dao.impl;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.DistCareerDao;

public class DistCareerDaoImpl extends BaseDAOHibernate implements DistCareerDao {
	
	public int removeDistCareerByUserid(Long userid){
		String removeSql = "delete from t_dist_career where pk_userid="+userid;
		SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(removeSql);
		int update = query.executeUpdate();
		return update;
	}
	
	@Override
	public List queryDistCareerCount(){
		String sql = "SELECT pk_userid,count(1) as careercount FROM t_dist_career GROUP BY pk_userid";
		SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	

}
