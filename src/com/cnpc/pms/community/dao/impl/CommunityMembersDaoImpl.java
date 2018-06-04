package com.cnpc.pms.community.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.community.dao.CommunityMembersDao;
import com.cnpc.pms.dynamic.entity.DynamicDto;

public class CommunityMembersDaoImpl extends BaseDAOHibernate implements CommunityMembersDao {

	@Override
	public List<Map<String, Object>> getNewMembersCount(DynamicDto dd,String flag) {
		//查询新用户注册社员sql
		String sql = "select count(*) as newCount from  df_user_member dum where 1=1 ";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String dateSql = "";
		String isNewSql = "";
		if("0".equals(flag)){
			dateSql = " and date_format(dum.opencard_time,'%Y-%m')=date_format(now(),'%Y-%m') ";
			isNewSql = " and dum.isnew_member=1 ";
		}
		String zx = "no";
		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
			zx = "yes";
		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
				"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
					"where ts.province_id='"+province_id+"' "+dateSql;
		}
		if(city_id!=null&&city_id!=""){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
				"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
					"where d.id='"+city_id+"' "+dateSql;
		}else if("yes".equals(zx)){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
					"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
						"where d.id='"+province_id+"' "+dateSql;
		}
		sql+=isNewSql;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
	        if(lst_data != null){
	        	  for(Object obj : lst_data){
	                  Map<String,Object> map_data = (Map<String,Object>)obj;
	                  Map<String,Object> map_content = (Map<String,Object>)obj;
	                  map_content.put("newCount",map_data.get("newCount"));
	                  lst_result.add(map_content);
	              }
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getWeekMembersCount(DynamicDto dd) {
		String sql = "SELECT count(*) AS newcount,DATE_FORMAT(dum.opencard_time,'%m-%d') AS crtime FROM df_user_member dum " +
				"LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
				"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name where 1=1 AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
				"<= date(dum.opencard_time) ";
		String groupOrderBySql = " GROUP BY DATE_FORMAT(dum.opencard_time,'%Y-%m-%d') ORDER BY dum.opencard_time";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String likeSql = "";
		String zx = "no";
		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
			zx = "yes";
		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			likeSql=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			likeSql=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			likeSql=" and d.id='"+province_id+"' ";
		}
		sql=sql+likeSql+groupOrderBySql;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
	        if(lst_data != null){
	        	  for(Object obj : lst_data){
	                  Map<String,Object> map_content = (Map<String,Object>)obj;
	                  lst_result.add(map_content);
	              }
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> getWeekTotalMembersCount(DynamicDto dd) {
		String sql = "select sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 6 DAY)  then 1 else 0 end) as day1," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 5 DAY)  then  1 else 0 end) as day2," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 4 DAY)  then  1 else 0 end) as day3," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 3 DAY)  then  1 else 0 end) as day4," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 2 DAY)  then  1 else 0 end) as day5," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 1 DAY)  then  1 else 0 end) as day6," + 
				"sum(CASE when  dum.opencard_time<= DATE_SUB(curdate(),INTERVAL 0 DAY)  then  1 else 0 end) as day7" + 
				" from df_user_member dum LEFT JOIN (SELECT province_id,cityno,city_name FROM t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts " +
				"ON LPAD(dum.regist_cityno, 4, '0') = ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname = ts.city_name where 1=1 ";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String likeSql = "";
		String zx = "no";
		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
			zx = "yes";
		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			likeSql=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			likeSql=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			likeSql=" and d.id='"+province_id+"' ";
		}
		sql=sql+likeSql;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
	        if(lst_data != null){
	        	  for(Object obj : lst_data){
	                  Map<String,Object> map_content = (Map<String,Object>)obj;
	                  lst_result.add(map_content);
	              }
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}
}