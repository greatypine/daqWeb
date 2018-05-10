package com.cnpc.pms.personal.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.StorexpandDao;
import com.cnpc.pms.personal.entity.DistCity;
import com.cnpc.pms.personal.entity.Storexpand;

public class StorexpandDaoImpl extends BaseDAOHibernate  implements StorexpandDao {
	@Override
	public List<Map<String, Object>> getStorexpandList(String where,
			PageInfo pageInfo) {
		// 取得当前登录人 所管理城市
				String cityssql = "";
				StringBuffer sb_where = new StringBuffer();
				UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
				List<DistCity> distCityList = userManager.getCurrentUserCity();
				if (distCityList != null && distCityList.size() > 0) {
					for (DistCity d : distCityList) {
						cityssql += "'" + d.getCityname() + "',";
					}
					cityssql = cityssql.substring(0, cityssql.length() - 1);
				}

				if (cityssql != "" && cityssql.length() > 0) {
					sb_where.append(" and st.cityname in (" + cityssql + ") ");
				}
		// sql查询列，用于分页计算数据总数
				String str_count_sql = "select COUNT(DISTINCT st.id) "
						+ "from di_storexpand st WHERE 1=1  " + where;
				System.out.println(str_count_sql);
				// sql查询列，用于页面展示所有的数据
				String find_sql = "select st.id,st.survey_quantity,st.contract_quantity,st.through_quantity,st.cooperative_task" +
						",st.self_support_task,st.cityname,st.cityno,st.create_user,st.create_time," +
						"st.update_user,st.update_time,st.statistical_time_period from di_storexpand st  WHERE 1=1  ";
				StringBuilder sb_sql = new StringBuilder();
				sb_sql.append(find_sql);
				sb_sql.append(where +sb_where.toString()+ " order by st.id desc");
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
	public Map<String, Object> getTaskQuantityExist(String cityname) {
		String sql ="SELECT count(*) as task_quantity_count FROM di_storexpand WHERE 1=1 ";
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
		String sql ="SELECT count(*) as statistics_count FROM di_storexpand WHERE 1=1 ";
		if(cityname!=null&&!"".equals(cityname)){
			sql += " AND cityname='"+cityname+"' ";
		}
		if(statistics!=null&&!"".equals(statistics)){
			sql += " AND statistical_time_period='"+statistics+"'";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		if(lst_data!=null&&lst_data.size()>0){
			Map<String, Object> map_lst = (Map<String, Object>)lst_data.get(0);
			resuMap.put("statistics",map_lst.get("statistics_count"));
		}else{
			resuMap.put("statistics",0);
		}
		return resuMap;
	}
	@Override
	public Storexpand getStorexpandById(Long id) {
		String findSql = "select * from di_storexpand where id=" + id;
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
		String sql = "select cityname,cityno,sum(contract_quantity) as contract_quantity,sum(through_quantity) as through_quantity from di_storexpand "
				+"where DATE_FORMAT(start_time,'%Y') = '"+year+"' GROUP BY cityname;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getThroughByWeek() {
		String sql = "select cityname,sum(CASE when flag = 0 and YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now())-5 then contract_quantity else 0 end) as week1,"
				+"sum(CASE when YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now())-4 then contract_quantity else 0 end) as week2,"
				+"sum(CASE when YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now())-3 then contract_quantity else 0 end) as week3,"
				+"sum(CASE when YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now())-2 then contract_quantity else 0 end) as week4,"
				+"sum(CASE when YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now())-1 then contract_quantity else 0 end) as week5,"
				+"sum(CASE when YEARWEEK(date_format(start_time,'%Y-%m-%d')) = YEARWEEK(now()) then contract_quantity else 0 end) as week6 "
				+"from di_storexpand GROUP BY cityname;";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

}
