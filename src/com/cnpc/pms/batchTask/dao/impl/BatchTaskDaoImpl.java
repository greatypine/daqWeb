/**
 * gaobaolei
 */
package com.cnpc.pms.batchTask.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.batchTask.dao.BatchTaskDao;

/**
 * @author gaobaolei
 *
 */
public class BatchTaskDaoImpl extends BaseDAOHibernate implements BatchTaskDao{

	
	@Override
	public Map<String, Object> queryBatchResult(Integer year, Integer month) {
		String sql = "";
		Map<String, Object> map = new HashMap<String,Object>();
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return map;
	}

	
	@Override
	public List<Map<String, Object>> queryBatchResult(String sql) {
	
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

}
