package com.cnpc.pms.dynamic.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.dynamic.dao.StrategyActivityDao;

@SuppressWarnings("unchecked")
public class StrategyActivityDaoImpl extends BaseDAOHibernate implements StrategyActivityDao {

	@Override
	public Map<String, Object> queryStrategyGMV(String dept_id,String start_time,String end_time) {
		String sql = "SELECT IFNULL(SUM(trading_price),0) total_gmv FROM df_mass_order_monthly tor "
				+ "JOIN df_activity_scope das ON (tor.store_id = das.platformid) WHERE 1=1 ";
		
		if(StringUtils.isNotEmpty(start_time)){
			sql = sql + " AND tor.sign_time >= '"+start_time+"' ";
		}
		if(StringUtils.isNotEmpty(end_time)){
			sql = sql + " AND tor.sign_time <= '"+end_time+"' ";
		}
		
		if(StringUtils.isNotEmpty(dept_id)){
			if("groupon".equals(dept_id)){
				sql  = sql + " AND tor.groupon_instance_id IS NOT NULL ";
			}else if(!"all".equals(dept_id)){
				sql  = sql + " AND tor.bussiness_group_id = '"+dept_id+"'";
			}
		}
		
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
	public List<Map<String, Object>> queryGmvTrend(){
		String sql = "SELECT IFNULL(SUM(trading_price), 0) total_gmv,DATE(tor.sign_time) AS datetime FROM df_mass_order_monthly tor "
				+ "JOIN df_activity_scope das ON (tor.store_id = das.platformid) WHERE DATE_SUB(CURDATE(), INTERVAL 6 DAY) <= date(tor.sign_time) GROUP BY datetime";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public Map<String, Object> queryNewMember() {
		String sql = "SELECT count(member.customer_id) AS membernum FROM df_user_member member JOIN df_activity_scope das ON (member.regist_storeid = das.platformid) "
				+ "WHERE member.opencard_time >= '2018-07-01' ";
				
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
	public Map<String, Object> queryTotalMember() {
		String sql = "SELECT count(member.customer_id) AS totalnum FROM	df_user_member member JOIN df_activity_scope das ON (member.regist_storeid = das.platformid)";
		
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
	public List<Map<String, Object>> queryDataOfScatterplot() {
		String sql = "SELECT das.store_name,tor.ordergmv,tor.ordernum FROM(SELECT tor.store_id,sum(trading_price) AS ordergmv,count(1) AS ordernum "
				+ "FROM df_mass_order_monthly tor FORCE INDEX(sign_time) WHERE TO_DAYS(NOW()) - TO_DAYS(tor.sign_time) <= 1 GROUP BY tor.store_id) tor "
				+ "JOIN df_activity_scope das ON (tor.store_id = das.platformid)";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryDataOfPercent() {
		String sql = "SELECT concat(tor.order_type,'') as order_type,sum(trading_price) AS ordergmv FROM df_mass_order_monthly tor JOIN df_activity_scope das on (tor.store_id = das.platformid) "
				+ "WHERE tor.sign_time >= '2018-07-01' GROUP BY tor.order_type";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryStoreRanking(String dept_id){
		String sql = "SELECT das.store_name,tor.ordergmv FROM (SELECT tor.store_id,sum(trading_price) AS ordergmv FROM df_mass_order_monthly tor "
				+ "FORCE INDEX(sign_time) WHERE tor.sign_time >= '2018-07-01' ";
		if(StringUtils.isNotEmpty(dept_id)){
			if("groupon".equals(dept_id)){
				sql  = sql + " AND tor.groupon_instance_id IS NOT NULL ";
			} else {
				sql  = sql + " AND tor.bussiness_group_id = '"+dept_id+"'";
			}
		}
		sql = sql + "GROUP BY tor.store_id) tor JOIN df_activity_scope das "
				+ "ON (tor.store_id = das.platformid) ORDER BY tor.ordergmv desc limit 10";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryMemberTrend(){
		String sql = "SELECT count(member.customer_id) AS membernum, DATE(member.opencard_time) AS datetime FROM df_user_member member "
				+ "JOIN df_activity_scope das ON (member.regist_storeid = das.platformid) WHERE	DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(member.opencard_time) GROUP BY datetime ";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryActivityScope(){
		String sql = "SELECT store_name,store_no FROM df_activity_scope ";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryProductRanking(String product_type,String store_no){
		String sql = "SELECT tor.product_name, tor.product_quantity FROM (SELECT tor.product_name,  tor.store_id,sum(product_quantity) AS product_quantity "
				+ "FROM ds_ope_product_store_day tor WHERE tor.recdate >= '2018-07-01' ";
		
		if(StringUtils.isNotEmpty(product_type)){
			sql = sql + " AND tor.product_type = '"+product_type+"' ";
		}
		if(StringUtils.isNotEmpty(store_no) && !"all".equals(store_no)){
			sql = sql + " AND tor.store_no = '"+store_no+"' ";
		}
		sql = sql + "GROUP BY tor.product_id) tor JOIN df_activity_scope das ON (tor.store_id = das.platformid) ORDER BY product_quantity desc limit 10 ";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
}
