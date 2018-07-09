package com.cnpc.pms.personal.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.dao.ObserveParameterDao;

public class ObserveParameterDaoImpl extends BaseDAOHibernate  implements ObserveParameterDao{
	
	@Override
	public List<Map<String, Object>> queryObserveParameterList(String where, PageInfo pageInfo) {
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();	
		// sql查询列，用于页面展示所有的数据
		String find_sql = "select store_id,store_name,storeno,observe_month from t_store_observe_parameter where status = 0 and type = 0"+where;
		// SQL查询对象
				SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
						.createSQLQuery(find_sql);
		
		if(pageInfo != null){
			// sql查询列，用于分页计算数据总数
			String str_count_sql = "select COUNT(DISTINCT st.id) "
					+ "from t_store_observe_parameter st WHERE status = 0 and type = 0 " + where;
			System.out.println(str_count_sql);
			
			// 查询数据量对象
			SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
					.createSQLQuery(str_count_sql);
			pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
			// 获得查询数据
			List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();

			// 如果没有数据返回
			if (lst_data == null || lst_data.size() == 0) {
				return lst_result;
			}
			// 转换成需要的数据形式
			for (Object obj_data : lst_data) {
				lst_result.add((Map<String, Object>) obj_data);
			}
		}else{
			lst_result =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> queryExitObserveParameter(String store_id, String observe_month,String save_type,String cityname) {
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();	
		String where = "";
		if(!"".equals(store_id) && store_id != null){
			where = " AND store_id = '"+store_id+"'";
		}
		if(!"".equals(observe_month) && observe_month != null){
			where += " and observe_month = '"+observe_month+"'";
		}
		if(!"".equals(save_type) && save_type != null){
			where += " and type = '"+save_type+"'";
		}
		if(!"".equals(cityname) && cityname != null){
			where = " and city_name = '"+cityname+"'";
		}
		String find_sql = "select * from t_store_observe_parameter where status = 0 "+where;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(find_sql);
		lst_result =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_result;
	}

	@Override
	public List<String> queryObserveMonthByCity(String cityname) {
		String where = "";
		if(!"".equals(cityname) && cityname != null){
			where = " and city_name = '"+cityname+"'";
		}
		String find_sql = "select observe_month from t_store_observe_parameter where 1=1 "+where+" GROUP BY observe_month";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(find_sql);
		List<String> list = query.list();
		return list;
	}

	@Override
	public List<Map<String, Object>> queryObserveParameterSummaryByCity(String cityname) {
		String where = "";
		if(!"".equals(cityname) && cityname != null){
			where = " and city_name = '"+cityname+"'";
		}
		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();	
		String find_sql = "select top.storeno,top.store_name,ifnull(ts.rmname,'') as rmname,ifnull(ts.skname,'') as skname,top.observe_date,top.observe_person,top.points_combined,top.observe_question_number,top.observe_month from t_store_observe_parameter top INNER JOIN "
				+"(select(select name from tb_bizbase_user t1 where t1.id = t.rmid) as rmname,(select name from tb_bizbase_user t2 where t2.id = t.skid) as skname,t.store_id from t_store t) ts "
				+"ON (ts.store_id = top.store_id) WHERE top.type = '0' "+where+"  GROUP BY top.store_id,top.observe_month";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
				.createSQLQuery(find_sql);
		lst_result =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_result;
	}

}
