package com.cnpc.pms.festival.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.festival.dao.FestivalStatDao;

@SuppressWarnings("unchecked")
public class FestivalStatDaoImpl extends BaseDAOHibernate implements FestivalStatDao {

	@Override
	public List<Map<String, Object>> eshopRanking(String dateTime) {
		String sql = "SELECT dod.eshop_name, SUM(dod.trading_price) AS trading_price FROM df_mass_order_daily dod WHERE	DATE_FORMAT(dod.sign_time,'%Y-%m-%d')='"+dateTime+"' "
				+ " GROUP BY dod.eshop_id ORDER BY trading_price DESC LIMIT 10 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public Map<String, Object> queryTurnoverAndUser(String dateTime) {
		String sql = "select SUM(dod.trading_price) AS trading_price,COUNT(DISTINCT(dod.customer_id)) as pay_customer  from df_mass_order_daily dod "
				+ " WHERE DATE_FORMAT(dod.sign_time,'%Y-%m-%d')='"+dateTime+"' ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		Map<String, Object> order_obj = null;
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if (lst_data != null && lst_data.size() > 0) {
			order_obj = (Map<String, Object>) lst_data.get(0);
		}
		return order_obj;
	}

	@Override
	public List<Map<String, Object>> cityRanking(String dateTime) {
		String sql = "SELECT dod.store_city_name, SUM(dod.trading_price) AS trading_price FROM	df_mass_order_daily dod "
				+ " WHERE DATE_FORMAT(dod.sign_time, '%Y-%m-%d') = '"+dateTime+"' GROUP BY store_city_code ORDER BY trading_price DESC LIMIT 10 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryTurnoverByHour(String dateTime) {
		String sql = "SELECT SUM(dod.trading_price) AS trading_price, DATE_FORMAT(dod.sign_time, '%H') AS time FROM	df_mass_order_daily dod "
				+ " WHERE DATE_FORMAT(dod.sign_time, '%Y-%m-%d') = '"+dateTime+"' GROUP BY	DATE_FORMAT(dod.sign_time, '%H') ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryUserByHour(String dateTime) {
		String sql = "SELECT COUNT(DISTINCT customer_id) AS pay_customer, DATE_FORMAT(dod.sign_time, '%H') AS time FROM	df_mass_order_daily dod "
				+ " WHERE DATE_FORMAT(dod.sign_time, '%Y-%m-%d') = '"+dateTime+"' GROUP BY DATE_FORMAT(dod.sign_time, '%H') ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

}
