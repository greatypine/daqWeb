package com.cnpc.pms.personal.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.StoreActivitiesDao;

public class StoreActivitiesDaoImpl extends BaseDAOHibernate implements StoreActivitiesDao{

	@Override
	public List<Map<String, Object>> getNewActivitiesInfo() {
		String sql = "select COUNT(DISTINCT cityname) as count from t_store_acivities";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Integer count = 0;
		if(list.size()>0){
			 count = Integer.parseInt(list.get(0).get("count").toString());
		}
		String str_sql = "select * from t_store_acivities where WEEKOFYEAR(create_time) = WEEKOFYEAR(NOW()) ORDER BY create_time DESC limit "+count;
		SQLQuery query_1 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str_sql);
		// 获得查询数据
		List<Map<String, Object>> list_data = query_1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list_data;
	}

}
