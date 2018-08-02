package com.cnpc.pms.communeMember.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.communeMember.dao.ChartMemberDao;
import com.cnpc.pms.platform.entity.ChartMemberDto;
import com.cnpc.pms.utils.DateUtils;

public class ChartMemberDaoImpl extends BaseDAOHibernate implements ChartMemberDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryContainsStoreDistCityList(){
		String sql = "select distinct dum.regist_cityno cityno,tdc.cityname cityname from df_user_member dum,t_dist_citycode tdc where LPAD(dum.regist_cityno, 4, '0') = tdc.cityno ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryDayTurnover(ChartMemberDto csd) {
		
		String sql = "SELECT IFNULL(FLOOR(SUM(dod.trading_price)),0) AS day_amount FROM df_mass_order_daily dod WHERE DATE(dod.sign_time) = DATE(curdate()) "
				+ "AND dod.store_name NOT LIKE '%测试%' ";
		if(StringUtils.isNotEmpty(csd.getStoreno())){
			sql = sql + " AND dod.store_code = '"+csd.getStoreno()+"' ";
		}
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND dod.store_city_name like '"+csd.getCityname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getDeptname())){
			sql = sql + " AND dod.department_name like '%"+csd.getDeptname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getChannelname())){
			sql = sql + " AND dod.channel_name like '%"+csd.getChannelname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getcLabel()) && StringUtils.isNotEmpty(csd.getcLabel())){
			if("1".equals(csd.getcLabel()) && "1".equals(csd.getSmallBLabel()) && "0".equals(csd.getMaxBLabel())){//C+小B
				sql = sql + " AND dod.store_name not like '%企业购%' ";
			}else if("0".equals(csd.getcLabel()) && "0".equals(csd.getSmallBLabel()) && "1".equals(csd.getMaxBLabel())){//大B
				sql = sql + " AND dod.store_name like '%企业购%' ";
			}else if("1".equals(csd.getcLabel()) && "1".equals(csd.getSmallBLabel()) && "1".equals(csd.getMaxBLabel())){//All
			}else{
				return new HashMap<String, Object>();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTurnoverByHour(ChartMemberDto csd){
		String sql = "SELECT IFNULL(FLOOR(sum(dom.trading_price)), 0) as turnover,DATE_FORMAT(dom.sign_time,'%H') as time FROM	df_mass_order_daily dom "
				+ "WHERE DATEDIFF(dom.sign_time,NOW())=0 AND dom.store_name NOT LIKE '%测试%' ";
		if(StringUtils.isNotEmpty(csd.getStoreno())){
			sql = sql + " AND dom.store_code = '"+csd.getStoreno()+"' ";
		}
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND dom.store_city_name like '"+csd.getCityname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getDeptname())){
			sql = sql + " AND dom.department_name like '%"+csd.getDeptname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getChannelname())){
			sql = sql + " AND dom.channel_name like '%"+csd.getChannelname()+"%' ";
		}
		if(StringUtils.isNotEmpty(csd.getcLabel()) && StringUtils.isNotEmpty(csd.getcLabel())){
			if("1".equals(csd.getcLabel()) && "1".equals(csd.getSmallBLabel()) && "0".equals(csd.getMaxBLabel())){//C+小B
				sql = sql + " AND dom.store_name not like '%企业购%' ";
			}else if("0".equals(csd.getcLabel()) && "0".equals(csd.getSmallBLabel()) && "1".equals(csd.getMaxBLabel())){//大B
				sql = sql + " AND dom.store_name like '%企业购%' ";
			}else if("1".equals(csd.getcLabel()) && "1".equals(csd.getSmallBLabel()) && "1".equals(csd.getMaxBLabel())){//All
			}else{
				return new ArrayList<Map<String, Object>>();
			}
		}
		sql = sql + " GROUP BY DATE_FORMAT(dom.sign_time,'%H') ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTurnoverByDay(ChartMemberDto csd){
		String sql="SELECT IFNULL(FLOOR(sum(mem_gmv)), 0) AS turnover,DATE(date) as day_time FROM ds_ope_member_city_day dom WHERE 1=1";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " GROUP BY DATE(date) ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTurnoverByWeek(ChartMemberDto csd){
		String sql = "SELECT YEARWEEK(date) as week_date ,subdate(date,date_format(date,'%w')) as week_time, IFNULL(FLOOR(SUM(mem_gmv)),0) AS week_amount FROM ds_ope_member_city_day WHERE 1=1 ";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " GROUP BY week_date";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTurnoverByMonth(ChartMemberDto csd){
		String sql = "select CONCAT(YEAR(date),'-',MONTH(date))  months,IFNULL(FLOOR(SUM(mem_gmv)),0) AS mon_amount from ds_ope_member_city_day where 1=1";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " GROUP BY months ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryDataOfScatterplot(ChartMemberDto csd){
		String sql = "select IFNULL(FLOOR(sum(dom.mem_gmv)), 0) order_amount, IFNULL(FLOOR(sum(dom.mem_count)), 0) order_count, dom.city_code cityno, dom.city_name city_name, CONCAT( YEAR (dom.date), '-', MONTH (dom.date)) months from ds_ope_member_city_day dom where 1 = 1 ";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND dom.city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " group by dom.city_code, months";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
}
