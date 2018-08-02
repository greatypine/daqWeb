package com.cnpc.pms.community.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
		String expirySql = "";
		if("0".equals(flag)){
			dateSql = " and date_format(dum.opencard_time,'%Y-%m')=date_format(now(),'%Y-%m') ";
			//isNewSql = " and dum.isnew_member=1 ";
		}
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
				"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
					"where ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
				"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
					"where d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			sql = "select count(*) as newCount from  df_user_member dum LEFT JOIN (select province_id,cityno,city_name from t_store t WHERE t.province_id IS NOT NULL GROUP BY cityno) ts ON LPAD(dum.regist_cityno,4,'0') = " +
					"ts.cityno LEFT JOIN t_dist_citycode d ON d.cityname=ts.city_name " +
						"where d.id='"+province_id+"' ";
		}
		if("1".equals(flag)){
			expirySql = " and date_format(dum.associator_expiry_date,'%Y-%m-%d') >= date_format(now(),'%Y-%m-%d') ";
		}
		sql+=expirySql+isNewSql+dateSql;
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
				"<= date(dum.opencard_time) AND date(dum.opencard_time)<CURDATE() ";
		String groupOrderBySql = " GROUP BY DATE_FORMAT(dum.opencard_time,'%Y-%m-%d') ORDER BY dum.opencard_time limit 0,7 ";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String likeSql = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
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
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
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
	@Override
	public List<Map<String, Object>> getDeptMonthDayGMV(String storeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Map<String, Object>> queryTwoTwoOneGMVByWeek(DynamicDto dto) {
		String sql = "SELECT YEARWEEK(dmo.sign_time) AS week_date,subdate(dmo.sign_time,date_format(dmo.sign_time, '%w')) AS week_time," +
				"IFNULL(FLOOR(SUM(dmo.trading_price)),0) AS week_amount FROM df_mass_order_monthly dmo JOIN df_activity_scope das ON dmo.store_code = das.store_no " +
				"WHERE dmo.store_name NOT LIKE '%测试%' AND dmo.order_tag2 IS NOT NULL AND dmo.sign_time > '2018-07-01 00:00:00' ";
		if(StringUtils.isNotEmpty(dto.getStoreNo())){
			sql = sql + " AND dmo.store_code = '"+dto.getStoreNo()+"' ";
		}
		if(StringUtils.isNotEmpty(dto.getCityName())){
			sql = sql + " AND dmo.store_city_name like '%"+dto.getCityName()+"%' ";
		}
		if(StringUtils.isNotEmpty(dto.getSearchstr())){//商品,团购,服务
			String str = "";
			String[] strArr = dto.getSearchstr().split(",");
			for(int i=0;i<strArr.length;i++){
				str+="'"+strArr[i]+"',";
			}
			str = str.substring(0,str.lastIndexOf(","));
			sql = sql + " AND dmo.order_tag2 in ("+str+") ";
		}else{
			sql = sql +" AND dmo.order_tag2 not in ('1','2','3')";
		}
		sql = sql + " GROUP BY week_date";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryTwoTwoOneGMVByDay(DynamicDto dynamicDto) {
		String sql="SELECT DATE(dmo.sign_time) AS day_time,IFNULL(FLOOR(sum(dmo.trading_price)),0) AS turnover FROM df_mass_order_monthly dmo " +
				"JOIN df_activity_scope das ON dmo.store_code = das.store_no WHERE dmo.store_name NOT LIKE '%测试%' AND dmo.order_tag2 IS NOT NULL AND dmo.sign_time > '2018-07-01 00:00:00' ";
		if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
			sql = sql + " AND dmo.store_code = '"+dynamicDto.getStoreNo()+"' ";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getCityName())){
			sql = sql + " AND dmo.store_city_name like '%"+dynamicDto.getCityName()+"%' ";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getSearchstr())){//商品,团购,服务
			String str = "";
			String[] strArr = dynamicDto.getSearchstr().split(",");
			for(int i=0;i<strArr.length;i++){
				str+="'"+strArr[i]+"',";
			}
			str = str.substring(0,str.lastIndexOf(","));
			sql = sql + " AND dmo.order_tag2 in ("+str+") ";
		}else{
			sql = sql +" AND dmo.order_tag2 not in ('1','2','3')";
		}
		sql = sql + " GROUP BY DATE(dmo.sign_time) ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryTwoTwoOneGMVByHour(DynamicDto dynamicDto) {
		String sql="SELECT IFNULL(FLOOR(sum(dmo.trading_price)),0) AS turnover,DATE_FORMAT(dmo.sign_time, '%H') AS time FROM df_mass_order_daily dmo" +
				"JOIN df_activity_scope das ON dmo.store_code = das.store_no WHERE dmo.store_name NOT LIKE '%测试%' AND dmo.order_tag2 IS NOT NULL ";
		if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
			sql = sql + " AND dmo.store_code = '"+dynamicDto.getStoreNo()+"' ";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getCityName())){
			sql = sql + " AND dmo.store_city_name like '%"+dynamicDto.getCityName()+"%' ";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getSearchstr())){//商品,团购,服务
			String str = "";
			String[] strArr = dynamicDto.getSearchstr().split(",");
			for(int i=0;i<strArr.length;i++){
				str+="'"+strArr[i]+"',";
			}
			str = str.substring(0,str.lastIndexOf(","));
			sql = sql + " AND dmo.order_tag2 in ("+str+") ";
		}else{
			sql = sql +" AND dmo.order_tag2 not in ('1','2','3')";
		}
		sql = sql + " GROUP BY DATE_FORMAT(dmo.sign_time,'%H') ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryDataOfScatterplot(DynamicDto dynamicDto) {
		String sql="SELECT IFNULL(SUM(dsc.order_count), 0) AS order_count,IFNULL(FLOOR(SUM(dsc.order_amount)),0) AS order_amount,dsc.city_name," +
				"dsc.cityno,dsc.store_name,CONCAT(dsc. YEAR, '-', dsc. MONTH) AS time,dsc.storeno FROM ds_ope_gmv_storechannel_month dsc JOIN df_activity_scope " +
				"das ON dsc.storeno=das.store_no WHERE 1 = 1 AND dsc.store_name NOT LIKE '%企业购%' ";
		if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
			sql = sql + " AND dsc.storeno = '"+dynamicDto.getStoreNo()+"' ";
		}
		if(StringUtils.isNotEmpty(dynamicDto.getCityName())){
			sql = sql + " AND dsc.city_name like '%"+dynamicDto.getCityName()+"%' ";
		}
		sql = sql + " GROUP BY city_name,store_name,time HAVING time >= DATE_FORMAT(DATE_SUB(CURRENT_DATE (),INTERVAL 3 MONTH),'%Y-%c') ";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryTwoTwoOneStoreCount(
			DynamicDto dynamicDto) {
		String sql="SELECT count(*) as store_count FROM df_activity_scope";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		// 获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
}
