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
	public Map<String, Object> queryStrategyGMV(String start_time,String end_time) {
		String sql = "SELECT IFNULL(MAX(pro_total_gmv),0) as pro_total_gmv,	IFNULL(MAX(ser_total_gmv),0) as ser_total_gmv,"
				+ "IFNULL(MAX(gro_total_gmv),0) as gro_total_gmv FROM (SELECT CASE WHEN tor.order_tag2='1' THEN SUM(gmv_price) "
				+ "END as pro_total_gmv,CASE WHEN tor.order_tag2='2' THEN SUM(gmv_price) END as ser_total_gmv,CASE WHEN tor.order_tag2='3' "
				+ "THEN SUM(gmv_price) END as gro_total_gmv FROM df_mass_order_monthly tor JOIN df_activity_scope das ON (tor.store_id = das.platformid) "
				+ "WHERE tor.order_tag2 IS NOT NULL  ";
		if(StringUtils.isNotEmpty(start_time)){
			sql = sql + " AND date(tor.sign_time) >= '"+start_time+"' ";
		}
		if(StringUtils.isNotEmpty(end_time)){
			sql = sql + " AND date(tor.sign_time) <= '"+end_time+"' ";
		}
		sql = sql + " GROUP BY order_tag2) aa ";
		
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
	public List<Map<String, Object>> queryUnStrategyGMV(String start_time,String end_time){
		String sql = "SELECT IFNULL(SUM(gmv_price), 0) total_gmv,department_name FROM df_mass_order_monthly tor,df_activity_scope ds "
				+ "WHERE ds.platformid=tor.store_id AND tor.order_tag2 IS NULL ";
		if(StringUtils.isNotEmpty(start_time)){
			sql = sql + " AND date(tor.sign_time) >= '"+start_time+"' ";
		}
		if(StringUtils.isNotEmpty(end_time)){
			sql = sql + " AND date(tor.sign_time) <= '"+end_time+"' ";
		}
		sql = sql + " AND tor.department_name IS NOT NULL AND tor.department_name NOT LIKE '测试%' "
				+ "GROUP BY bussiness_group_id";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public Map<String, Object> queryNewMember(String store_no,String start_time,String end_time) {
		String sql = "SELECT count(member.customer_id) AS membernum FROM df_user_member member JOIN df_activity_scope das ON (member.regist_storeid = das.platformid ";
		if(StringUtils.isNotEmpty(store_no)){
			sql = sql + "AND das.store_no='"+store_no+"'";
		}
		sql = sql + ")WHERE 1=1 ";
		
		if(StringUtils.isNotEmpty(start_time)){
			sql = sql + " AND date(member.opencard_time) >= '"+start_time+"' ";
		}
		if(StringUtils.isNotEmpty(end_time)){
			sql = sql + " AND date(member.opencard_time) <= '"+end_time+"' ";
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
	public Map<String, Object> queryStoreUser(String store_no){
		String sql = "SELECT tu.`name` AS store_user FROM	t_store ts,tb_bizbase_user tu WHERE	ts.skid = tu.id AND ts.storeno = '"+store_no+"'";
		
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
	public List<Map<String, Object>> queryStoreCompleteInfo(String store_no){
		String sql = "SELECT IFNULL(MAX(pro_total_gmv), 0) AS pro_total_gmv,IFNULL(MAX(ser_total_gmv), 0) AS ser_total_gmv,"
				+ "IFNULL(MAX(gro_total_gmv), 0) AS gro_total_gmv,store_name,store_no FROM (SELECT CASE WHEN tor.order_tag2 = '1' "
				+ "THEN	SUM(gmv_price)	END AS pro_total_gmv,CASE WHEN tor.order_tag2 = '2' THEN SUM(gmv_price) END AS ser_total_gmv,"
				+ "CASE WHEN tor.order_tag2 = '3' THEN	SUM(gmv_price) END AS gro_total_gmv,das.store_name,das.store_id,das.store_no,"
				+ "tor.order_tag2 FROM	df_mass_order_monthly tor JOIN df_activity_scope das ON (tor.store_id = das.platformid) "
				+ "WHERE TO_DAYS(NOW()) - TO_DAYS(tor.sign_time) <= 1 AND tor.order_tag2 IS NOT NULL ";
		if(StringUtils.isNotEmpty(store_no)){
			sql = sql + "AND tor.store_code='"+store_no+"'";
		}
		sql = sql +  "GROUP BY das.store_id,tor.order_tag2) aa GROUP BY store_id ORDER BY store_id";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryDataOfScatterplot() {
		String sql = "SELECT das.store_name,tor.ordergmv,tor.ordernum,das.store_no FROM(SELECT tor.store_id,sum(gmv_price) AS ordergmv,count(1) AS ordernum "
				+ "FROM df_mass_order_monthly tor FORCE INDEX(sign_time) WHERE TO_DAYS(NOW()) - TO_DAYS(tor.sign_time) <= 1 AND tor.order_tag2 IS NOT NULL "
				+ "GROUP BY tor.store_id) tor JOIN df_activity_scope das ON (tor.store_id = das.platformid)";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryYestodayGmvRanking(String dept_id,String order_by){
		String sql = "SELECT das.store_name,IFNULL(tor.ordergmv,0) AS ordergmv FROM (SELECT SUM(ordergmv) AS ordergmv,store_id FROM (SELECT tor.store_id,"
				+ "sum(gmv_price) AS ordergmv FROM df_mass_order_monthly tor WHERE TO_DAYS(NOW()) - TO_DAYS(tor.sign_time) <= 1 ";
		if(StringUtils.isNotEmpty(dept_id)){
			sql = sql + "AND tor.order_tag2 = '" + dept_id+"'";
		}
		sql = sql + "GROUP BY tor.store_id) aa GROUP BY	store_id) tor JOIN df_activity_scope das ON (tor.store_id = das.platformid)";			
		if(StringUtils.isNotEmpty(order_by)){
			sql = sql + "ORDER BY tor.ordergmv " + order_by;
		}
		sql = sql+" LIMIT 10";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryYestodayMemberRanking(String order_by){
		String sql = "SELECT count(member.customer_id) AS membernum,das.store_name FROM	df_user_member member "
				+ "JOIN df_activity_scope das ON (member.regist_storeid = das.platformid) WHERE	TO_DAYS(NOW()) - TO_DAYS(member.opencard_time) <= 1 "
				+ "GROUP BY das.store_id ";
		if(StringUtils.isNotEmpty(order_by)){
			sql = sql + "ORDER BY membernum " + order_by;
		}
		sql = sql+" LIMIT 10";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryStoreRanking(String dept_id){
		String sql = "SELECT das.store_name,tor.ordergmv FROM (SELECT tor.store_id,sum(gmv_price) AS ordergmv FROM df_mass_order_monthly tor "
				+ "FORCE INDEX(sign_time) WHERE tor.sign_time >= '2018-07-01' AND tor.order_tag2 IS NOT NULL ";
		if(StringUtils.isNotEmpty(dept_id)){
			sql  = sql + " AND tor.order_tag2='"+dept_id+"' ";
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
	public List<Map<String, Object>> queryProductRanking(String dept_id,String store_no){
		String sql = "SELECT tor.product_name, tor.product_quantity FROM (SELECT tor.product_name,  tor.store_id,sum(product_quantity) AS product_quantity "
				+ "FROM ds_ope_product_store_day tor JOIN df_activity_bussiness_scope dab ON (tor.channel_id = dab.id AND dab. LEVEL = 2";
		if(StringUtils.isNotEmpty(dept_id)){
			sql  = sql + " AND dab.type='"+dept_id+"' ";
		}
		sql = sql + ") WHERE tor.recdate >= '2018-07-01' ";
		
		if(StringUtils.isNotEmpty(store_no) && !"all".equals(store_no)){
			sql = sql + " AND tor.store_no = '"+store_no+"' ";
		}
		sql = sql + "GROUP BY tor.product_id) tor JOIN df_activity_scope das ON (tor.store_id = das.platformid) ORDER BY product_quantity desc limit 10 ";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTypeGMV() {
		/**
		 * @author wuxinxin
		 * 2018年7月16日
		 */
		String sql = "select " + 
				"	dateSeven.dates selDate, " + 
				"	ifnull(datas.allcount, 0) cou, " + 
				"	dateSeven.ordt as ordtypes " + 
				"from " + 
				"	( " + 
				"		select distinct " + 
				"			date(dmom0.sign_time) dates, " + 
				"			alltype.tag2 ordt " + 
				"		from " + 
				"			df_mass_order_monthly dmom0, " + 
				"			( " + 
				"				select distinct " + 
				"					dmom1.order_tag2 tag2 " + 
				"				from " + 
				"					df_mass_order_monthly dmom1 " + 
				"				where " + 
				"					dmom1.order_tag2 is not null " + 
				"			) alltype " + 
				"		where " + 
				"			DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(dmom0.sign_time) " + 
				"		and CURDATE() > date(dmom0.sign_time) " + 
				"	) dateSeven " + 
				"left join ( " + 
				"	select " + 
				"		TRUNCATE ((SUM(dmom.gmv_price)) / 10000,2) AS allcount, " + 
				"		DATE_FORMAT(dmom.sign_time, \"%Y-%m-%d\") as crtime, " + 
				"		dmom.order_tag2 as ordtype " + 
				"	from " + 
				"		df_mass_order_monthly dmom, " + 
				"		df_activity_scope das " + 
				"	where " + 
				"		dmom.store_id = das.platformid " + 
				"	and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(dmom.sign_time) " + 
				"	and CURDATE() > date(dmom.sign_time) " + 
				"	and dmom.order_tag2 is not null " + 
				"	group by " + 
				"		DATE_FORMAT(dmom.sign_time, \"%Y-%m-%d\"), " + 
				"		dmom.order_tag2 " + 
				") datas on (dateSeven.dates = datas.crtime and dateSeven.ordt=datas.ordtype)" + 
				"order by " + 
				"	dateSeven.dates, " + 
				"	dateSeven.ordt";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
		
	}
}
