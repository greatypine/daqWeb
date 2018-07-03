package com.cnpc.pms.personal.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.dao.AppDownloadLogDao;
import com.cnpc.pms.personal.dao.StoreResourcesDao;
import com.cnpc.pms.personal.dao.UserLoginLogDao;

public class StoreResourcesDaoImpl extends BaseDAOHibernate  implements StoreResourcesDao{

	@Override
	public List<Map<String, Object>> findStoreResourcesByType(int i) {
		
		String str_sql = "select t.cityname,t.storecount,sr.* from (select s.city_name as cityname,count(DISTINCT s.id) as storecount,MAX(r.id) as id from t_store s left JOIN t_store_resources r ON (s.city_name = r.cityname) "
				+"WHERE r.save_type = '"+i+"' and s.flag=0 AND s.`name` NOT  LIKE '%测试%' and s.`name` NOT  LIKE '%储备%' and s.`name` NOT  LIKE '%办公室%' "
				+"and s.storetype!='V' and s.storetype!='W' AND ifnull(s.estate,'')='运营中' GROUP BY s.city_name) t left JOIN t_store_resources sr ON t.id = sr.id";
		SQLQuery query_1 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(str_sql);
		// 获得查询数据
		List<Map<String, Object>> list_data = query_1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list_data;
	}

	
}
