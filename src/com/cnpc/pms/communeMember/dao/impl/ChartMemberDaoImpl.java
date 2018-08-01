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
		String sql="SELECT IFNULL(FLOOR(sum(dom.trading_price)), 0) AS turnover,DATE(dom.sign_time) as day_time FROM df_mass_order_total dom WHERE dom.sign_time>'2018-05-17'  and dom.sign_time<CURDATE() and dom.order_tag1 like '%E%'  and dom.order_tag1 like '%M%' ";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND dom.store_city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " GROUP BY DATE(dom.sign_time) ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryTurnoverByWeek(ChartMemberDto csd){
		String sql = "SELECT YEARWEEK(sign_time) as week_date ,	subdate(sign_time,date_format(sign_time,'%w')) as week_time, IFNULL(FLOOR(SUM(trading_price)),0) AS week_amount FROM df_mass_order_total WHERE sign_time>'2018-05-17'  and order_tag1 like '%E%'  and order_tag1 like '%M%' ";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND store_city_name like '"+csd.getCityname()+"%' ";
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
		String sql = "select CONCAT(YEAR(sign_time),'-',MONTH(sign_time))  months,IFNULL(FLOOR(SUM(trading_price)),0) AS mon_amount from df_mass_order_total where sign_time>'2018-05-17'  and order_tag1 like '%E%'  and order_tag1 like '%M%'";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND store_city_name like '"+csd.getCityname()+"%' ";
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
		String sql = "select IFNULL(FLOOR(sum(dom.trading_price)), 0) order_amount,count(dom.sign_time) order_count,dom.store_city_code cityno,dom.store_city_name city_name from  df_mass_order_total dom where dom.sign_time>'2018-05-17' and dom.order_tag1 like '%E%'  and dom.order_tag1 like '%M%' ";
		if(StringUtils.isNotEmpty(csd.getCityname())){
			sql = sql + " AND dom.store_city_name like '"+csd.getCityname()+"%' ";
		}
		sql = sql + " group by dom.store_city_code";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
}
