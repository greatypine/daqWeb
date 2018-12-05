package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.TargetEntryDao;
import com.cnpc.pms.personal.dao.TargetEntryStoreDao;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.entity.TargetEntryStore;
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
	public List<Map<String, Object>> getTargetEntryStoreData(String frame_time,String dept_name,String channel,
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
		String find_sql = "select ts.frame_time,ts.channel_name,ts.city_name,ts.store_name,ts.store_code,ts.maori_target,ts.profit_target,ts.user_target from df_target_entry_store ts where ts.frame_time='"+frame_time +"' and  ts.channel_name='"+ channel+"' and ts.businessGroup_name='"+ dept_name+"'  ";
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
		String sql = " select ts.frame_time,ts.city_name,ts.store_name,ts.store_code,ts.maori_target,ts.profit_target,ts.user_target from df_target_entry_store ts where ts.frame_time='"+targetEntryStore.getFrame_time()+"' and ts.channel_name='"+ targetEntryStore.getChannel_name()+"' and ts.businessGroup_name='"+ targetEntryStore.getBusinessGroup_name()+"' ";


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
		String sql = "UPDATE df_target_entry_store ts  SET ts.maori_target = '"+targetEntryStore.getMaori_target()+"' ,ts.profit_target='"+targetEntryStore.getProfit_target()+"' ,ts.user_target='"+targetEntryStore.getUser_target()+"' where ts.city_name = '"+targetEntryStore.getCity_name()+"' and ts.store_code='"+targetEntryStore.getStore_code()+"' and ts.frame_time ='"+targetEntryStore.getFrame_time()+"' and ts.channel_name ='"+targetEntryStore.getChannel_name()+"' and ts.businessGroup_name ='"+targetEntryStore.getBusinessGroup_name()+"' ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.executeUpdate();

	}

}