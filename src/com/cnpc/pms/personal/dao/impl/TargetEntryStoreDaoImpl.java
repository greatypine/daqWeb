package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.TargetEntryDao;
import com.cnpc.pms.personal.dao.TargetEntryStoreDao;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.entity.TargetEntryStore;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.*;

public class TargetEntryStoreDaoImpl extends BaseDAOHibernate  implements TargetEntryStoreDao {
	@Override
	public List<Map<String, Object>> getTargetEntryStoreList(String where,
			PageInfo pageInfo) {
				StringBuffer sb_where = new StringBuffer();
				UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
//
		// sql查询列，用于分页计算数据总数
				String str_count_sql = "select COUNT(DISTINCT t.store_id) from t_store t where  t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '')='运营中' AND t.storetype != 'V' AND t.storetype != 'W' ";
				System.out.println(str_count_sql);
				// sql查询列，用于页面展示所有的数据
				String find_sql = "select t.city_name,t.`name` as store_name,t.storeno as store_code,'0' as maori_target,'0' as profit_target,'0' as user_target from t_store t where  t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '')='运营中' AND t.storetype != 'V' AND t.storetype != 'W'  ";
				StringBuilder sb_sql = new StringBuilder();
				sb_sql.append(find_sql);
				sb_sql.append(where +sb_where.toString()+ " order by t.id desc");
				// SQL查询对象
				SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
						.createSQLQuery(sb_sql.toString());

				// 查询数据量对象
				SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
						.createSQLQuery(str_count_sql);
				pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
				// 获得查询数据
				List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
						.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
						.setMaxResults(pageInfo.getRecordsPerPage()).list();

				List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

				// 如果没有数据返回
				if (lst_data == null || lst_data.size() == 0) {
					return lst_result;
				}
				// 转换成需要的数据形式
				for (Object obj_data : lst_data) {
					lst_result.add((Map<String, Object>) obj_data);
				}
				return lst_result;
	}

	@Override
	public List<Map<String, Object>> getTargetEntryStoreList1(String where,
															 PageInfo pageInfo) {
		// 取得当前登录人 所管理城市
		String cityssql = "";
		StringBuffer sb_where = new StringBuffer();
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		// sql查询列，用于分页计算数据总数
		String find_sql = "select t.city_name,t.`name` as store_name,t.storeno as store_code,'0' as maori_target,'0' as profit_target,'0' as user_target from t_store t where  t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '')='运营中' AND t.storetype != 'V' AND t.storetype != 'W'  ";
		StringBuilder sb_sql = new StringBuilder();
		sb_sql.append(find_sql);
		sb_sql.append(where +sb_where.toString()+ " order by t.id desc");
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());

		// 查询数据量对象
//		SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
//				.createSQLQuery(str_count_sql);
//		pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> getTargetEntryStoreData(String frame_time,String dept_name,
															 PageInfo pageInfo) {
		// 取得当前登录人 所管理城市
		String cityssql = "";
		StringBuffer sb_where = new StringBuffer();
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		// sql查询列，用于分页计算数据总数
				String str_count_sql = "select COUNT(DISTINCT ts.id) "
						+ "from df_target_entry_store ts WHERE 1=1  ";
				System.out.println(str_count_sql);
		// sql查询列，用于页面展示所有的数据
		String find_sql = "select ts.frame_time,ts.channel_name,ts.city_name,ts.store_name,ts.store_code,ts.maori_target,ts.profit_target,ts.user_target from df_target_entry_store ts where ts.frame_time='"+frame_time +"' and ts.businessGroup_name='"+ dept_name+"'  ";
		StringBuilder sb_sql = new StringBuilder();
		sb_sql.append(find_sql);
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());

		// 查询数据量对象
				SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
						.createSQLQuery(str_count_sql);
				pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	public List<Map<String, Object>> exportFile(TargetEntryStore targetEntryStore){
		String sql = " select ts.frame_time,ts.city_name,ts.store_name,ts.store_code,ts.maori_target,ts.profit_target,ts.user_target from df_target_entry_store ts where ts.frame_time='"+targetEntryStore.getFrame_time()+"' and ts.businessGroup_name='"+ targetEntryStore.getBusinessGroup_name()+"' ";


		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	public List<Map<String, Object>> exportFileAll(String startTime,String endTIme){
		String sql =  " select t1.city_name,t1.store_name,t1.store_code,";
		sql = sql + " 	IFNULL(t2.youyi_businessGroup_name,'国安优易') as youyi_businessGroup_name,";
		sql = sql + " 	IFNULL(t2.youyi_maori_target,0.00) as youyi_maori_target,";
		sql = sql + " 	IFNULL(t2.youyi_profit_target,0.00) as youyi_profit_target,";
		sql = sql + " 	IFNULL(t2.youyi_user_target,0.00) as youyi_user_target,";
		sql = sql + " 	IFNULL(t3.jws_businessGroup_name,'家务事') as jws_businessGroup_name,";
		sql = sql + " 	IFNULL(t3.jws_maori_target,0.00) as jws_maori_target,";
		sql = sql + " 	IFNULL(t3.jws_profit_target,0.00) as jws_profit_target,";
		sql = sql + " 	IFNULL(t3.jws_user_target,0.00) as jws_user_target, ";
		sql = sql + " 	IFNULL(t4.dw_businessGroup_name,'点位') as dw_businessGroup_name,";
		sql = sql + " 	IFNULL(t4.dw_maori_target,0.00) as dw_maori_target,";
		sql = sql + " 	IFNULL(t4.dw_profit_target,0.00) as dw_profit_target,";
		sql = sql + " 	IFNULL(t4.dw_user_target,0.00) as dw_user_target,";
		sql = sql + " 	IFNULL(t5.gajc_businessGroup_name,'国安金超') as gajc_businessGroup_name,";
		sql = sql + " 	IFNULL(t5.gajc_maori_target,0.00) as gajc_maori_target,";
		sql = sql + " 	IFNULL(t5.gajc_profit_target,0.00) as gajc_profit_target,";
		sql = sql + " 	IFNULL(t5.gajc_user_target,0.00) as gajc_user_target,";
		sql = sql + " 	IFNULL(t6.cpzx_businessGroup_name,'产品中心') as cpzx_businessGroup_name,";
		sql = sql + " 	IFNULL(t6.cpzx_maori_target,0.00) as cpzx_maori_target,";
		sql = sql + " 	IFNULL(t6.cpzx_profit_target,0.00) as cpzx_profit_target,";
		sql = sql + " 	IFNULL(t6.cpzx_user_target,0.00) as cpzx_user_target,";
		sql = sql + " 	IFNULL(t7.zfbt_businessGroup_name,'政府补贴') as zfbt_businessGroup_name,";
		sql = sql + " 	IFNULL(t7.zfbt_maori_target,0.00) as zfbt_maori_target,";
		sql = sql + " 	IFNULL(t7.zfbt_profit_target,0.00) as zfbt_profit_target,";
		sql = sql + " 	IFNULL(t7.zfbt_user_target,0.00) as zfbt_user_target";
		sql = sql + " from (SELECT";
		sql = sql + " 	tes.businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	tes.city_name,";
		sql = sql + " 	tes.store_name,";
		sql = sql + " 	sum(tes.maori_target),";
		sql = sql + " 	sum(tes.profit_target),";
		sql = sql + " 	sum(tes.user_target)";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '国安优易'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t1 ";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as youyi_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	tes.city_name,";
		sql = sql + " 	tes.store_name,";
		sql = sql + " 	sum(tes.maori_target) as youyi_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as youyi_profit_target,";
		sql = sql + " 	sum(tes.user_target) as youyi_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '国安优易'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t2 on (t1.store_code=t2.store_code)";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as jws_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	sum(tes.maori_target) as jws_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as jws_profit_target,";
		sql = sql + " 	sum(tes.user_target) as jws_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '家务事'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t3 on (t1.store_code=t3.store_code)";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as dw_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	sum(tes.maori_target) as dw_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as dw_profit_target,";
		sql = sql + " 	sum(tes.user_target) as dw_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '点位'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t4 on (t1.store_code=t4.store_code)";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as gajc_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	sum(tes.maori_target) as gajc_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as gajc_profit_target,";
		sql = sql + " 	sum(tes.user_target) as gajc_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '国安金超'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t5 on (t1.store_code=t5.store_code)";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as cpzx_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	sum(tes.maori_target) as cpzx_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as cpzx_profit_target,";
		sql = sql + " 	sum(tes.user_target) as cpzx_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '产品中心'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t6 on (t1.store_code=t6.store_code)";
		sql = sql + " LEFT JOIN (SELECT";
		sql = sql + " 	tes.businessGroup_name as zfbt_businessGroup_name,";
		sql = sql + " 	tes.store_code,";
		sql = sql + " 	sum(tes.maori_target) as zfbt_maori_target,";
		sql = sql + " 	sum(tes.profit_target) as zfbt_profit_target,";
		sql = sql + " 	sum(tes.user_target) as zfbt_user_target";
		sql = sql + " FROM";
		sql = sql + " 	df_target_entry_store tes";
		sql = sql + " WHERE";
		sql = sql + " 	tes.frame_time >= '"+startTime+"'";
		sql = sql + " AND tes.frame_time <= '"+endTIme+"'";
		sql = sql + " and tes.businessGroup_name = '政府补贴'";
		sql = sql + " GROUP BY tes.store_code";
		sql = sql + " ORDER BY tes.store_code) t7 on (t1.store_code=t7.store_code)";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}


	public List<Map<String, Object>> exportFileAllList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='国安优易' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}


	public List<Map<String, Object>> exportFileAlljwsList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='家务事' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	public List<Map<String, Object>> exportFileAlldwList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='点位' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	public List<Map<String, Object>> exportFileAllgajcList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='国安金超' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	public List<Map<String, Object>> exportFileAllcpzxList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='产品中心' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	public List<Map<String, Object>> exportFileAllzfbtList(String startTime,String endTIme){
		String sql = " SELECT te.businessGroup_name,sum(te.maori_target) as maori_target,sum(te.profit_target) as profit_target,sum(te.user_target) as user_target FROM df_target_entry te WHERE te.frame_time >= '"+startTime+"' AND te.frame_time <= '"+endTIme+"' and te.businessGroup_name='政府补贴' ";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	@Override
	public Map<String, Object> getTaskQuantityExist(String cityname) {
		String sql ="SELECT count(*) as task_quantity_count FROM df_bussiness_target WHERE 1=1 and type='store' and period_type='week' ";
		if(cityname!=null&&!"".equals(cityname)){
			sql += "AND cityname='"+cityname+"'";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		if(lst_data!=null&&lst_data.size()>0){
			Map<String, Object> map_lst = (Map<String, Object>)lst_data.get(0);
			resuMap.put("task_quantity_count",map_lst.get("task_quantity_count"));
		}
		return resuMap;
	}
	@Override
	public Map<String, Object> getStatisticsExist(String statistics,String cityname) {
		String sql ="SELECT count(*) as target_count FROM df_target_entry WHERE 1=1  ";
		if(cityname!=null&&!"".equals(cityname)){
			sql += " AND city_name='"+cityname+"' ";
		}
		if(statistics!=null&&!"".equals(statistics)){
			sql += " AND frame_time='"+statistics+"'";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		if(lst_data!=null&&lst_data.size()>0){
			Map<String, Object> map_lst = (Map<String, Object>)lst_data.get(0);
			resuMap.put("statistics",map_lst.get("target_count"));
		}else{
			resuMap.put("statistics",0);
		}
		return resuMap;
	}
	@Override
	public Storexpand getStorexpandById(Long id) {
		String findSql = "select * from df_bussiness_target where id=" + id;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(findSql);
		query.addEntity(Storexpand.class);
		List<Storexpand> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getContractAndthroughByYear(String year) {
		String sql = "select city_name,city_no,sum(param_second) as contract_quantity,sum(param_third) as through_quantity from df_bussiness_target where type = 'store' and period_type = 'week' "
				+"and DATE_FORMAT(start_time,'%Y') = '"+year+"' GROUP BY city_name;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getThroughByWeek() {
		int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int settime = 0;
		if(i<5){
			settime = 3;
		}else{
			settime = 10;
		}
		String sql = "select city_name,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*5 DAY),'%Y-%m-%d') then param_second else 0 end) as week1,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*4 DAY),'%Y-%m-%d') then param_second else 0 end) as week2,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*3 DAY),'%Y-%m-%d') then param_second else 0 end) as week3,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7*2 DAY),'%Y-%m-%d') then param_second else 0 end) as week4,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+"+7 DAY),'%Y-%m-%d') then param_second else 0 end) as week5,"
				+"sum(CASE when DATE_ADD(DATE_FORMAT(start_time,'%Y-%m-%d'), INTERVAL 6 DAY) = date_format(date_sub(NOW(),INTERVAL date_format(NOW(), '%w') -"+settime+" DAY),'%Y-%m-%d') then param_second else 0 end) as week6 "
				+"from df_bussiness_target where type = 'store' and period_type = 'week' GROUP BY city_name;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public void updateTargetEntryStore(TargetEntryStore targetEntryStore) {
		String sql = "UPDATE df_target_entry_store ts  SET ts.maori_target = '"+targetEntryStore.getMaori_target()+"' ,ts.profit_target='"+targetEntryStore.getProfit_target()+"' ,ts.user_target='"+targetEntryStore.getUser_target()+"' where ts.city_name = '"+targetEntryStore.getCity_name()+"' and ts.store_code='"+targetEntryStore.getStore_code()+"' and ts.frame_time ='"+targetEntryStore.getFrame_time()+"' and ts.businessGroup_name ='"+targetEntryStore.getBusinessGroup_name()+"' ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.executeUpdate();

	}

	@Override
	public List<Map<String, Object>> queryTargetEntryStoreDept(String date) {
		String find_sql = "SELECT ts.businessGroup_name,ts.frame_time,SUM(ts.maori_target)as maori_target from df_target_entry_store ts where 1=1 ";
		if(!"all".equals(date)){
			find_sql += " AND frame_time='"+date+"'";
		}
		find_sql = find_sql + " GROUP BY ts.businessGroup_name,ts.frame_time ";

		StringBuilder sb_sql = new StringBuilder();
		sb_sql.append(find_sql);
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());

		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> queryTargetEntryStoreCity(String date) {
		String find_sql = "SELECT ts.city_name,ts.frame_time,SUM(ts.maori_target)as maori_target from df_target_entry_store ts where 1=1 ";
		if(!"all".equals(date)){
			find_sql += " AND frame_time='"+date+"'";
		}
		find_sql = find_sql + " GROUP BY ts.city_name,ts.frame_time ";
		StringBuilder sb_sql = new StringBuilder();
		sb_sql.append(find_sql);
		// SQL查询对象
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(sb_sql.toString());

		// 获得查询数据
		List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (lst_data == null || lst_data.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : lst_data) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}


	@Override
	public List<Map<String, Object>> queryActualDeptMaori(String date) {
		String sql = " SELECT aa.department_name,aa.sign_time, dround (ifnull(aa.ims_profit, 0), 2) AS ims_profit, dround ( ifnull(aa.platform_profit, 0), 2 ) AS platform_profit, dround (ifnull(aa.order_fee, 0), 2) AS order_fee, ";
		sql = sql + " dround ( ifnull(dd.return_profit, 0), 2 ) AS return_profit, dround ( ifnull(aa.total_profit, 0), 2 ) AS total_profit, dround ( ifnull(aa.gayy_subsidy, 0), 2 ) AS gayy_subsidy, dround ( ifnull(dd.return_gayy_subsidy, 0), 2 ) AS return_gayy_subsidy,";
		sql = sql + "  dround ( ifnull(cc.sale_profit, 0) + ifnull(ee.first_sale_profit, 0), 2 ) AS sale_profit, dround ( ifnull(bb.return_sale_profit, 0) + ifnull( ff.return_first_sale_profit, 0 ), 2 ) AS return_sale_profit FROM ";
		sql = sql + " ( SELECT dot.bussiness_group_id, min(department_name) AS department_name,min(strleft(dot.sign_time,7)) AS sign_time, min(channel_name) AS channel_name, ifnull( dround ( sum( CASE WHEN dot.eshop_joint_ims = 'no' THEN dot.order_profit ELSE 0 END ), 2 ), 0 ) AS platform_profit, ";
		sql = sql + " ifnull( dround ( sum( CASE WHEN dot.eshop_joint_ims = 'yes' THEN dot.order_profit ELSE 0 END ), 2 ), 0 ) AS ims_profit, ifnull( dround ( sum( CASE WHEN dot.order_tag4 IS NULL THEN dot.platform_price ELSE 0 END ), 2 ), 0 ) AS order_fee,";
		sql = sql + "  ifnull( dround (sum(dot.order_profit), 2), 0 ) AS total_profit, ifnull( dround (sum(dot.gayy_subsidy), 2), 0 ) AS gayy_subsidy FROM df_mass_order_total dot, t_store ts, t_dist_citycode tdc ";
		sql = sql + " WHERE dot.real_store_id = ts.id AND ts.cityno = tdc.cityno ";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (sign_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (sign_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (sign_time, 7) <= '2019-12' ";
		}
		sql = sql + " AND dot.department_name NOT LIKE '%测试%' AND dot.department_name NOT LIKE '%运营管理中心%' GROUP BY dot.bussiness_group_id ) aa LEFT JOIN ( SELECT ifnull( dround (sum(order_profit), 2), 0 ) AS return_profit, ifnull( dround (sum(gayy_subsidy), 2), 0 ) AS return_gayy_subsidy,";
		sql = sql + "  bussiness_group_id FROM df_mass_order_total dot, t_store ts WHERE dot.real_store_id = ts.id ";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (return_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (return_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (return_time, 7) <= '2019-12' ";
		}
		sql = sql + " GROUP BY bussiness_group_id ) dd ON ( aa.bussiness_group_id = dd.bussiness_group_id ) LEFT JOIN ( SELECT dot.bussiness_group_id, min(department_name) AS department_name, ";
		sql = sql + " ifnull( dround ( sum( CASE WHEN ( dept.parent_id = '8ac28b935fed0bc8015fed4c76f60018' ) THEN dot.sale_profit ELSE dot.this_channel_profit END ), 2 ), 0 ) AS sale_profit FROM df_mass_order_total dot LEFT JOIN gemini.t_department_channel dept ON dot.first_order_channel = dept.id ";
		sql = sql + " LEFT JOIN t_store ts ON dot.real_store_id = ts.id WHERE 1=1 ";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (dot.sign_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (dot.sign_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (dot.sign_time, 7) <= '2019-12' ";
		}
		sql = sql + " AND dot.department_name NOT LIKE '%测试%' AND dot.department_name NOT LIKE '%运营管理中心%' GROUP BY dot.bussiness_group_id ) cc ON ( aa.bussiness_group_id = cc.bussiness_group_id ) LEFT JOIN ( SELECT dept.parent_id AS bussiness_group_id, max(dept2. NAME) AS department_name, ";
		sql = sql + " ifnull( dround ( sum( CASE WHEN ( dept.parent_id = '8ac28b935fed0bc8015fed4c76f60018' ) THEN 0 ELSE dot.first_channel_profit END ), 2 ), 0 ) AS first_sale_profit FROM df_mass_order_total dot LEFT JOIN gemini.t_department_channel dept ON dot.first_order_channel = dept.id ";
		sql = sql + " LEFT JOIN gemini.t_department_channel dept2 ON dept.parent_id = dept2.id LEFT JOIN t_store ts ON dot.real_store_id = ts.id WHERE 1=1";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (dot.sign_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (dot.sign_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (dot.sign_time, 7) <= '2019-12' ";
		}
		sql = sql + " AND dot.department_name NOT LIKE '%测试%' AND dot.department_name NOT LIKE '%运营管理中心%' GROUP BY dept.parent_id ) ee ON ( aa.bussiness_group_id = ee.bussiness_group_id ) LEFT JOIN ( SELECT dot.bussiness_group_id, min(department_name) AS department_name, ";
		sql = sql + " ifnull( dround ( sum( CASE WHEN ( dept.parent_id = '8ac28b935fed0bc8015fed4c76f60018' ) THEN dot.sale_profit ELSE dot.this_channel_profit END ), 2 ), 0 ) AS return_sale_profit FROM df_mass_order_total dot ";
		sql = sql + " LEFT JOIN gemini.t_department_channel dept ON dot.first_order_channel = dept.id LEFT JOIN t_store ts ON dot.real_store_id = ts.id WHERE 1=1";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (dot.return_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (dot.return_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (dot.return_time, 7) <= '2019-12' ";
		}
		sql = sql + " AND dot.department_name NOT LIKE '%测试%' AND dot.department_name NOT LIKE '%运营管理中心%' GROUP BY dot.bussiness_group_id ) bb ON ( aa.bussiness_group_id = bb.bussiness_group_id ) LEFT JOIN ( SELECT dept.parent_id AS";
		sql = sql + "  bussiness_group_id, max(dept2. NAME) AS department_name, ifnull( dround ( sum( CASE WHEN ( dept.parent_id = '8ac28b935fed0bc8015fed4c76f60018' ) THEN 0 ELSE dot.first_channel_profit END ), 2 ), 0 ) AS return_first_sale_profit FROM df_mass_order_total dot ";
		sql = sql + " LEFT JOIN gemini.t_department_channel dept ON dot.first_order_channel = dept.id LEFT JOIN gemini.t_department_channel dept2 ON dept.parent_id = dept2.id LEFT JOIN t_store ts ON dot.real_store_id = ts.id WHERE 1=1";
		if(!"all".equals(date)){
			sql = sql + " AND strleft (dot.return_time, 7) = '"+date+"' ";
		}else{
			sql = sql + " AND strleft (dot.return_time, 7) >= '2019-01' ";
			sql = sql + " AND strleft (dot.return_time, 7) <= '2019-12' ";
		}
		sql = sql + " AND dot.department_name NOT LIKE '%测试%' AND dot.department_name NOT LIKE '%运营管理中心%' GROUP BY dept.parent_id ) ff ON ( aa.bussiness_group_id = ff.bussiness_group_id ) WHERE 1 = 1 ORDER BY department_name, sale_profit DESC";

		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (list == null || list.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : list) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> queryActualCityMaori(String date) {
		String sql = " SELECT aa.*,";
		sql = sql + "        ifnull(dd.return_profit,0) AS return_profit,";
		sql = sql + "        ifnull(dbaosun.count_money,0) AS baosun FROM";
		sql = sql + "   (SELECT min(dot.store_city_name) AS city_name,";
		sql = sql + " 		  min(strleft(dot.sign_time,7)) AS sign_time,";
		sql = sql + "           min(dot.store_city_code) AS store_city_code,";
		sql = sql + "           min(dot.store_name) AS store_name,";
		sql = sql + "           ifnull(min(dot.store_code),'') AS store_code,";
		sql = sql + "           ifnull(min(dot.department_name),'无') AS department_name,";
		sql = sql + "           min(dot.channel_name) AS channel_name,";
		sql = sql + "           ifnull(dround(sum(CASE WHEN dot.eshop_joint_ims='no' THEN dot.order_profit ELSE 0 END),2),0) AS platform_profit,";
		sql = sql + "           ifnull(dround(sum(CASE WHEN dot.eshop_joint_ims='yes' THEN dot.order_profit ELSE 0 END),2),0) AS ims_profit,";
		sql = sql + "           ifnull(dround(sum(CASE WHEN dot.order_tag4 IS NULL THEN dot.platform_price ELSE 0 END),2),0) AS order_fee,";
		sql = sql + "           ifnull(dround(sum(dot.order_profit),2),0) AS total_profit";
		sql = sql + "    FROM df_mass_order_total dot,";
		sql = sql + "         t_dist_citycode tdc,";
		sql = sql + "         gemini.t_department_channel dc";
		sql = sql + "    WHERE LPAD(dot.store_city_code, 4, '0')=tdc.cityno";
		sql = sql + "      AND dc.id=dot.bussiness_group_id";
		sql = sql + "      AND dc.level=1";
		sql = sql + "      AND dc.name NOT LIKE '%测试%'";
		if(!"all".equals(date)){
			sql = sql + "      AND strleft(dot.sign_time,7)= '"+date+"'";
		}else{
			sql = sql + "      AND strleft(dot.sign_time,7)>= '2019-01'";
			sql = sql + " 	 AND strleft(dot.sign_time,7)<= '2019-12'";
		}
		sql = sql + "    GROUP BY dot.store_city_code";
		sql = sql + "    ORDER BY dot.store_city_code) aa";
		sql = sql + " LEFT JOIN";
		sql = sql + " (select sum(aa.count_money) as count_money,aa.city_code,aa.num from ";
		sql = sql + "   (SELECT count_money,";
		sql = sql + "           city_code,";
		sql = sql + "           create_date,";
		sql = sql + "           num";
		sql = sql + "    FROM";
		sql = sql + "      (SELECT ifnull(dround(sum(baosun.count_money),2),0) AS count_money,";
		sql = sql + "              ts.city_code,";
		sql = sql + "              baosun.count_month,";
		sql = sql + "              baosun.create_date,";
		sql = sql + "              ROW_NUMBER() OVER(PARTITION BY city_code,baosun.count_month";
		sql = sql + "                                ORDER BY create_date DESC) AS num";
		sql = sql + "       FROM df_pankui_baosun_info baosun";
		sql = sql + "       JOIN gemini.t_store ts ON baosun.store_code=ts.code";
		sql = sql + "       WHERE baosun.count_type='0'";
		if(!"all".equals(date)){
			sql = sql + "         AND baosun.count_month = '"+date+"'";
		}else{
			sql = sql + "         AND baosun.count_month >= '2019-01'";
			sql = sql + " 		AND baosun.count_month <= '2019-12'";
		}
		sql = sql + "       GROUP BY ts.city_code,count_month,";
		sql = sql + "                baosun.create_date) aa HAVING num=1) aa";
		sql = sql + "  group by city_code,num) dbaosun ON aa.store_city_code=dbaosun.city_code";
		sql = sql + " LEFT JOIN";
		sql = sql + "   (SELECT ifnull(dround(sum(order_profit),2),0) AS return_profit ,";
		sql = sql + "           store_city_code";
		sql = sql + "    FROM df_mass_order_total WHERE 1=1";
		if(!"all".equals(date)){
			sql = sql + "      AND strleft(return_time,7)= '"+date+"'";
		}else{
			sql = sql + "      AND strleft(return_time,7)>= '2019-01'";
			sql = sql + " 	 AND strleft(return_time,7)<= '2019-12'";
		}
		sql = sql + "    GROUP BY store_city_code) dd ON aa.store_city_code=dd.store_city_code";
		sql = sql + " ORDER BY aa.store_city_code";
		List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();

		// 如果没有数据返回
		if (list == null || list.size() == 0) {
			return lst_result;
		}
		// 转换成需要的数据形式
		for (Object obj_data : list) {
			lst_result.add((Map<String, Object>) obj_data);
		}
		return lst_result;
	}
}
