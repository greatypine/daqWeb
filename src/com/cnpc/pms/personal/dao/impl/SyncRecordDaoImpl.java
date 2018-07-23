package com.cnpc.pms.personal.dao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.SyncRecordDao;

public class SyncRecordDaoImpl extends BaseDAOHibernate implements SyncRecordDao{

	@Override
	public List<Map<String, Object>> getjoinHumanresources(String org) {
		int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int settime = 0;
		if(i<5){
			settime = 3;
		}else{
			settime = 10;
		}
		String where = "";
		if(org != null){
			where = "and org like '%"+org+"%' ";
		}
		String sql = "select count(*) as count,subdate(DATE_FORMAT(jointime,'%Y-%m-%d'),date_format(jointime, '%w')-if(date_format(jointime, '%w')<4,3,10)) AS week_time from t_sync_record where "
				+"date_format(jointime,'%Y-%m-%d') > date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*6 DAY) and YEARWEEK(date_format(jointime,'%Y-%m-%d')) <= YEARWEEK(now()) "+where+" GROUP BY week_time";
	
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	@Override
	public List<Map<String, Object>> getleftHumanresources(String org) {
		int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int settime = 0;
		if(i<5){
			settime = 3;
		}else{
			settime = 10;
		}
		String where = "";
		if(org != null){
			where = "and org like '%"+org+"%' ";
		}
		String sql = "select count(*) as count,subdate(DATE_FORMAT(lefttime,'%Y-%m-%d'),date_format(lefttime, '%w')-if(date_format(lefttime, '%w')<4,3,10)) AS week_time from t_sync_record where "
				+"date_format(lefttime,'%Y-%m-%d') > date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*6 DAY) and YEARWEEK(date_format(lefttime,'%Y-%m-%d')) <= YEARWEEK(now()) "+where+" GROUP BY week_time";
	
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	@Override
	public List<Map<String, Object>> getTotalHumanresources(String org) {
		int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int settime = 0;
		if(i<5){
			settime = 3;
		}else{
			settime = 10;
		}
		String where = "";
		if(org != null){
			where = "and org like '%"+org+"%' ";
		}
		String sql = "select sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*5 DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as a,"
				+"sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*4 DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as b,"
				+"sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*3 DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as c,"
				+"sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*2 DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as d,"
				+"sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7 DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as e,"
				+"sum(CASE when lefttime = '' and (date_format(jointime,'%Y-%m-%d') <= date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+" DAY),'%Y-%m-%d') or jointime = '') then 1 else 0 end) as f from t_sync_record where 1=1"+where;
	
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

}
