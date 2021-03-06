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
	public List<Map<String, Object>> productRanking(String dateTime){
		String sql = "SELECT IFNULL(SUM(product_count), 0) AS product_count, product_name FROM ds_ope_product_city_day WHERE recdate = '"+dateTime+"' "
				+ "GROUP BY product_id ORDER BY product_count DESC LIMIT 5 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> eshopRanking(String dateTime) {
		String sql = "SELECT dod.eshop_name, SUM(dod.trading_price) AS trading_price FROM df_mass_order_daily dod WHERE	DATE_FORMAT(dod.sign_time,'%Y-%m-%d')='"+dateTime+"' "
				+ " GROUP BY dod.eshop_id ORDER BY trading_price DESC LIMIT 5 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public Map<String, Object> queryPayUser(String dateTime) {
		String sql = "select COUNT(DISTINCT(dod.customer_id)) as pay_customer  from df_mass_order_daily dod WHERE DATE_FORMAT(dod.sign_time,'%Y-%m-%d')='"+dateTime+"' ";
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
		String sql = "SELECT tt.cityname as city_name, IFNULL(SUM(dod.trading_price), 0) AS trading_price FROM (SELECT DISTINCT	(cityno) AS cityno,	city_name AS cityname "
				+ "FROM t_store WHERE NAME NOT LIKE '%储备%' AND NAME NOT LIKE '%办公%' AND NAME NOT LIKE '%测试%' AND flag = 0 ) tt LEFT JOIN df_mass_order_daily dod "
				+ "ON tt.cityno = CONVERT (dod.store_city_code, SIGNED) AND DATE_FORMAT(dod.sign_time, '%Y-%m-%d') = '"+dateTime+"' GROUP BY tt.cityno "
				+ "ORDER BY trading_price DESC LIMIT 12 ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryTurnoverByHour(String dateTime) {
		String sql = "SELECT IFNULL(SUM(dod.trading_price),0) AS trading_price, DATE_FORMAT(dod.sign_time, '%H') AS time FROM	df_mass_order_daily dod "
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
