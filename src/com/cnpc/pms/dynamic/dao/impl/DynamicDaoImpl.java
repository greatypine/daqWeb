/**
 * gaobaolei
 */
package com.cnpc.pms.dynamic.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.dao.DynamicDao;
import com.cnpc.pms.dynamic.entity.AbnormalOrderDto;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.ImpalaUtil;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gaobaolei
 *
 */
public class DynamicDaoImpl extends BaseDAOHibernate implements DynamicDao{

	
	@Override
	public int getNewaddcus(DynamicDto dd) {
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "   tor.store_code in (select t.storeno from t_store t	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like  '%闭店%')";
			}else{
				sub_str= "   tor.store_code in ( select t.storeno	from t_store t inner join 	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}
			
		}else if(dd.getTarget()==2){//店长
			sub_str = "  tor.store_code='"+dd.getStoreNo()+"'";
		}
		String sql="select  ifnull(sum(case when customer_isnew_flag >='10' then 1 else 0 end),0) as new_10_count "+
				   " from df_mass_order_monthly tor where customer_isnew_flag !='-1' "+
				   " and tor.sign_time >='"+dd.getBeginDate()+"' and tor.sign_time <'"+dd.getEndDate()+"' "+
				   " and "+sub_str+
				   " and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA' "+
				   " and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0  ";
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}
 
	@Override
	public int  getNewaddcusOfGAX(DynamicDto dd){
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "   tor.store_code in (select t.storeno from t_store t	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like  '%闭店%')";
			}else{
				sub_str= "   tor.store_code in ( select t.storeno	from t_store t inner join 	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}
			
		}else if(dd.getTarget()==2){//店长
			sub_str = "  tor.store_code='"+dd.getStoreNo()+"'";
		}
		String sql="select "+
					" ifnull(sum(case when customer_isnew_flag >='10' then 1 else 0 end),0) as new_10_count "+
					" from df_mass_order_monthly tor "+
					" where customer_isnew_flag !='-1' "+
					" and tor.sign_time >='"+dd.getBeginDate()+"' and tor.sign_time <'"+dd.getEndDate()+"' "+
					" and "+sub_str+
					" and info_employee_a_no is not null and info_employee_a_no !='' "+
					" and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA'  "+
					" and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0 ";
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}
	
	@Override
	public int getRebuycus(DynamicDto dd) {
		
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}else{
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}
		}else if(dd.getTarget()==2){//店长
			sub_str = " where t.store_id="+dd.getStoreId();
		}
		String sql="SELECT IFNULL(SUM(IFNULL(aa.rebuy_20_count,0)),0) AS amount FROM ds_rebuycus aa  INNER JOIN "
				+"  (SELECT t.store_id,t.platformid FROM t_store t "
				+ sub_str
				+"  ) bb on aa.platformid = bb.platformid and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}

	
	@Override
	public double getStoretrade(DynamicDto dd) {
		
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "   and storeno in (select t.storeno from t_store t	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}else{
				sub_str= "   and storeno in ( select t.storeno	from t_store t inner join 	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where  t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}
		}else if(dd.getTarget()==2){//店长
			sub_str = " and storeno='"+dd.getStoreNo()+"'";
		}
		 String sql="select ifnull(sum(ifnull(pesgmv,0)),0) as amount  from ds_pes_gmv_store_month where year="+dd.getYear()+" and month="+dd.getMonth()+sub_str;
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    
		 return Double.valueOf(query.uniqueResult().toString());
	}

	
	public double  getStoretradeOfGAX(DynamicDto dd){ 
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "   and storeno in (select t.storeno from t_store t	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}else{
				sub_str= "   and storeno in ( select t.storeno	from t_store t inner join 	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   where t.flag=0 "
						+"   AND t.estate not like '%闭店%')";
			}
		}else if(dd.getTarget()==2){//店长
			sub_str = " and storeno='"+dd.getStoreNo()+"'";
		}
		 String sql="select ifnull(sum(ifnull(pesgmv,0)),0) as amount  from ds_pes_gmv_emp_month where year="+dd.getYear()+" and month="+dd.getMonth()+sub_str;
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    
		 return Double.valueOf(query.uniqueResult().toString());
	}
	
	@Override
	public int getSendorders(DynamicDto dd) {
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}else{
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}
			
		}else if(dd.getTarget()==2){//店长
			sub_str = " where t.store_id="+dd.getStoreId();
		}else if(dd.getTarget()==3){//国安侠
			dd.setEmployeeNo(dd.getEmployeeName()+"+"+dd.getEmployeeNo());
			String sql="SELECT IFNULL(SUM(IFNULL(aa.datanum,0)),0) AS amount FROM ds_pes_order_empchannel_month aa  where username='"+dd.getEmployeeNo()+"' and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
			
			 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		     return Integer.valueOf(query.uniqueResult().toString());
		}
		String sql="SELECT IFNULL(SUM(IFNULL(aa.datanum,0)),0) AS amount FROM ds_pes_order_empchannel_month aa  INNER JOIN "
				+"  (SELECT t.store_id,t.platformid FROM t_store t "
				+ sub_str
				+"  ) bb on aa.platformid = bb.platformid and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
		
	}

	
	@Override
	public int getRewardtimes(DynamicDto dd) {
		
		String sub_str="";
		if(dd.getTarget()==1){//城市总监
			
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}else{
				sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
			}
		}else if(dd.getTarget()==2){//店长
			sub_str = " where t.store_id="+dd.getStoreId();
		}
		String sql="SELECT IFNULL(SUM(IFNULL(aa.dashang,0)),0) AS amount FROM ds_rewardtimes aa  INNER JOIN "
				+"  (SELECT t.store_id,t.platformid FROM t_store t "
				+ sub_str
				+"  ) bb on aa.platformid = bb.platformid and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getRelation(DynamicDto dd) {
	     String sub_str="";
	     if(dd.getTarget()==1){//城市总监
	    	     if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
	    	    	 sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
	 						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
	 						+"   AND t.platformid IS NOT NULL "
	 						+"   AND t.platformid != ''";
	    	     }else{
	    	    	 sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM "
	 						+"	 t_dist_citycode tdc where  tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
	 						+"   AND t.platformid IS NOT NULL "
	 						+"   AND t.platformid != ''";
	    	     }
				 
			}else if(dd.getTarget()==2){//店长
				 sub_str = " where t.store_id="+dd.getStoreId();
			}else if(dd.getTarget()==3){//国安侠
				 String sql="SELECT ifnull(SUM(IFNULL(aa.relationnum,0)),0) AS amount FROM ds_topdata aa  where aa.persontype = 2 and employeeno='"+dd.getEmployeeNo()+"' and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
				 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			     return Integer.valueOf(query.uniqueResult().toString());
			}
			String sql="SELECT ifnull(SUM(IFNULL(aa.storerelationnum,0)),0) AS amount FROM ds_topdata aa  INNER JOIN "
					+"  (SELECT t.store_id,t.platformid FROM t_store t "
					+ sub_str
					+"  ) bb on aa.storeid = bb.store_id and aa.persontype = 1 and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
			
			 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getCustomer(DynamicDto dd) {
	    String sub_str="";
	    if(dd.getTarget()==1){//城市总监
	    	if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
   	    	 sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
						+"	 t_dist_citycode tdc ON a.citycode  = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
   	     }else{
   	    	 sub_str= "	INNER JOIN	(SELECT tdc.id,tdc.cityname FROM "
						+"	 t_dist_citycode tdc where  tdc.id = "+dd.getCityId()+" ) t1 ON t.city_name = t1.cityname "
						+"   AND t.platformid IS NOT NULL "
						+"   AND t.platformid != ''";
   	     }
		}else if(dd.getTarget()==2){//店长
			sub_str = " where t.store_id="+dd.getStoreId();
		}else if(dd.getTarget()==3){//国安侠
			String sql="SELECT ifnull(SUM(IFNULL(aa.cusgrade2,0)),0) AS amount FROM ds_topdata aa where  employeeno='"+dd.getEmployeeNo()+"' and aa.persontype = 2 and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
			
			 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		     return Integer.valueOf(query.uniqueResult().toString());
		}
		String sql="SELECT ifnull(SUM(IFNULL(aa.storecusgrade2,0)),0) AS amount FROM ds_topdata aa  INNER JOIN "
				+"  (SELECT t.store_id,t.platformid FROM t_store t "
				+ sub_str
				+"  ) bb on aa.storeid = bb.store_id and aa.persontype = 1 and aa.year="+dd.getYear()+" and aa.month= "+dd.getMonth();
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getBusinessInfo(DynamicDto dd) {
		
		String sql="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				 sql = "select ifnull(count(ttbi.id),0) as amount from "
							+" (select aa.id from t_city aa ,"
							+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
							
							+" INNER JOIN t_dist_citycode tdc ON a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
							+" INNER JOIN t_county tc "
							+" on cc.id = tc.city_id "
							+" INNER JOIN t_town tt "
							+" on tc.id= tt.county_id "
							+" INNER JOIN "
							+" t_town_business_info ttbi "
							+" on tt.id = ttbi.town_id and  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
			}else{
				 sql = "select ifnull(count(ttbi.id),0) as amount from "
							+" (select aa.id from t_city aa ,"
							+" (SELECT tdc.id,tdc.cityname FROM"
							+" t_dist_citycode tdc where tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
							+" INNER JOIN t_county tc "
							+" on cc.id = tc.city_id "
							+" INNER JOIN t_town tt "
							+" on tc.id= tt.county_id "
							+" INNER JOIN "
							+" t_town_business_info ttbi "
							+" on tt.id = ttbi.town_id and  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
			}
			
		}else if(dd.getTarget()==2){//店长
			 sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_town_business_info ttbi "
						+" on t.town_id = ttbi.town_id and t.store_id="+dd.getStoreId()+"  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
		}
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getOfficeInfo(DynamicDto dd) {
		String sql="";
		if(dd.getTarget()==1){//城市总监
			if(dd.getEmployeeId()!=null&&!"".equals(dd.getEmployeeId())){
				 sql = "select ifnull(count(ttbi.id),0) as amount from "
							+" (select aa.id from t_city aa ,"
							+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
							
							+" INNER JOIN t_dist_citycode tdc ON a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
							+" INNER JOIN t_county tc "
							+" on cc.id = tc.city_id "
							+" INNER JOIN t_town tt "
							+" on tc.id= tt.county_id "
							+" INNER JOIN "
							+" t_town_business_info ttbi "
							+" on tt.id = ttbi.town_id and  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
			}else{
				 sql = "select ifnull(count(ttbi.id),0) as amount from "
							+" (select aa.id from t_city aa ,"
							+" (SELECT tdc.id,tdc.cityname FROM"
							+" t_dist_citycode tdc where tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
							+" INNER JOIN t_county tc "
							+" on cc.id = tc.city_id "
							+" INNER JOIN t_town tt "
							+" on tc.id= tt.county_id "
							+" INNER JOIN "
							+" t_town_business_info ttbi "
							+" on tt.id = ttbi.town_id and  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
			}
		}else if(dd.getTarget()==2){//店长
			 sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_office_info toi "
						+" on t.town_id = toi.town_id and t.store_id="+dd.getStoreId()+" and  DATE_FORMAT(toi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";
		}
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public List<Map<String, Object>> getStoretradeList(Long cityId, Long employeeId, Integer year, Integer month,
			String flag) {
		String sql="SELECT aa.* FROM ds_pes_gmv_store_month aa  INNER JOIN "
				+"  (SELECT t.store_id,t.platformid FROM t_store t INNER JOIN "
				+"	(SELECT tdc.id,tdc.cityname FROM	t_dist_city a INNER JOIN "
				
				+"	 t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid="+employeeId+" and tdc.id="+cityId+") t1 ON t.city_name = t1.cityname "
				+"   AND t.platformid IS NOT NULL "
				+"   AND t.platformid != '') bb on aa.platformid = bb.platformid and aa.year="+year+" and aa.month= "+month;
		
		 //SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);

        
        //获得查询数据
        List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        List<Map<String,Object>> lst_result = new ArrayList<Map<String, Object>>();

        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return lst_result;
        }
       
        return lst_result;
	}


	
	@Override
	public List<Map<String, Object>> getStoreOrderList(Long cityId, String employeeId, Integer year, Integer month,
			String flag) {
		
		return null;
	}


	
	@Override
	public List<Map<String, Object>> getRelationList(Long cityId, String employeeId, Integer year, Integer month,
			String flag) {
		
		return null;
	}


	
	@Override
	public int getAllHouseAmount(Long cityId, Long employeeId,Integer house_type) {
		String sql="";
		if(house_type==0){
			 sql = "select ifnull(count(th.id),0) as amount from "
					+" (select aa.id from t_city aa ,"
					+" (SELECT a.id,a.cityname from  t_dist_citycode a inner join t_dist_city b on a.citycode = b.citycode and b.pk_userid="+employeeId+" and a.id="+cityId+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc on cc.id = tc.city_id"
					+" INNER JOIN t_town tt on tc.id= tt.county_id"
					+" INNER JOIN t_village  tv on tt.id = tv.town_id "
					+" INNER JOIN t_tiny_village ttv on ttv.village_id = tv.id"
					+" INNER JOIN t_house th on th.tinyvillage_id = ttv.id ";
		}else if( house_type==1){
			sql = "select ifnull(count(th.id),0) as amount from "
					+" (select aa.id from t_city aa ,"
					+" (SELECT a.id,a.cityname from  t_dist_citycode a inner join t_dist_city b on a.citycode = b.citycode and b.pk_userid="+employeeId+" and a.id="+cityId+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc on cc.id = tc.city_id"
					+" INNER JOIN t_town tt on tc.id= tt.county_id"
					+" INNER JOIN t_village  tv on tt.id = tv.town_id "
					+" INNER JOIN t_tiny_village ttv on ttv.village_id = tv.id "
					+" INNER JOIN t_building tb on ttv.id = tb.tinyvillage_id "
					+" INNER JOIN t_house th on th.building_id = tb.id ";
		}
		
				
	
	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
     return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getAllTinyVillageAmount(Long cityId, Long employeeId) {
		
		String	sql = "select ifnull(count(ttv.id),0) as amount from "
					+" (select aa.id from t_city aa ,"
					+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
					+" INNER JOIN t_dist_citycode tdc on a.citycode = tdc.citycode and a.pk_userid = "+employeeId+" and tdc.id = "+cityId+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc on cc.id = tc.city_id"
					+" INNER JOIN t_town tt on tc.id= tt.county_id"
					+" INNER JOIN t_village  tv on tt.id = tv.town_id "
					+" INNER JOIN t_tiny_village ttv on ttv.village_id = tv.id ";
					
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	 	return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getAllVillageAmount(Long cityId, Long employeeId) {
		String	sql = "select ifnull(count(tv.id),0) as amount from "
				+" (select aa.id from t_city aa ,"
				+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
				+" INNER JOIN t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid = "+employeeId+" and tdc.id= "+cityId+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
				+" INNER JOIN t_county tc on cc.id = tc.city_id"
				+" INNER JOIN t_town tt on tc.id= tt.county_id"
				+" INNER JOIN t_village  tv on tt.id = tv.town_id ";
	
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	 	return Integer.valueOf(query.uniqueResult().toString());
	}


	
	@Override
	public int getAllTownAmount(Long cityId, Long employeeId) {
		String	sql = "select ifnull(count(tt.id),0) as amount from "
				+" (select aa.id from t_city aa ,"
				+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
				
				+" INNER JOIN t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid="+employeeId+" and tdc.id="+cityId+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
				+" INNER JOIN t_county tc on cc.id = tc.city_id"
				+" INNER JOIN t_town tt on tc.id= tt.county_id";
				
	
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	 	return Integer.valueOf(query.uniqueResult().toString());
	}


	/* (non-Javadoc)
	 * @see com.cnpc.pms.dynamic.dao.DynamicDao#queryBusiness(com.cnpc.pms.dynamic.entity.DynamicDto, com.cnpc.pms.base.paging.impl.PageInfo)
	 */
	@Override
	public Map<String, Object> queryBusiness(DynamicDto dd, PageInfo pageInfo) {
		String sql="";
		if(dd.getTarget()==1){//城市总监
			 sql = "select cc.cityname,tt.name as townname,ttbi.business_name from "
					+" (select aa.id,bb.cityname from t_city aa ,"
					+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
					
					+" INNER JOIN t_dist_citycode tdc ON a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc "
					+" on cc.id = tc.city_id "
					+" INNER JOIN t_town tt "
					+" on tc.id= tt.county_id "
					+" INNER JOIN "
					+" t_town_business_info ttbi "
					+" on tt.id = ttbi.town_id and  ttbi.business_name like '%"+dd.getSearchstr()+"%'";
		}else if(dd.getTarget()==2){//店长
			 /*sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_town_business_info ttbi "
						+" on t.town_id = ttbi.town_id and t.store_id="+dd.getStoreId()+"  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";*/
		}
		
		String sql_count = "SELECT COUNT(1) FROM ("+sql+") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

		Map<String,Object> map_result = new HashMap<String,Object>();
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("totalpage", total_pages);
		map_result.put("data", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> queryOffice(DynamicDto dd, PageInfo pageInfo) {
		String sql="";
		if(dd.getTarget()==1){//城市总监
			 sql = "select cc.cityname,tt.name as townname,toi.office_name from "
					+" (select aa.id,bb.cityname from t_city aa ,"
					+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
					
					+" INNER JOIN t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc "
					+" on cc.id = tc.city_id "
					+" INNER JOIN t_town tt "
					+" on tc.id= tt.county_id "
					+" INNER JOIN "
					+" t_office_info toi "
					+" on tt.id = toi.town_id and  toi.office_name like '%"+dd.getSearchstr()+"%'";
		}else if(dd.getTarget()==2){//店长
			/* sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_office_info toi "
						+" on t.town_id = toi.town_id and t.store_id="+dd.getStoreId()+" and  DATE_FORMAT(toi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";*/
		}
		
		
		String sql_count = "SELECT COUNT(1) FROM ("+sql+") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

		Map<String,Object> map_result = new HashMap<String,Object>();
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("totalpage", total_pages);
		map_result.put("pageinfo",pageInfo);
		map_result.put("data", list);
		return map_result;
	}


	
	@Override
	public List<Map<String, Object>> queryBusiness(DynamicDto dd) {
		String sql="";
		if(dd.getTarget()==1){//城市总监
			 sql = "select cc.cityname,tt.name as townname,ttbi.business_name from "
					+" (select aa.id,bb.cityname from t_city aa ,"
					+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
					+" INNER JOIN t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc "
					+" on cc.id = tc.city_id "
					+" INNER JOIN t_town tt "
					+" on tc.id= tt.county_id "
					+" INNER JOIN "
					+" t_town_business_info ttbi "
					+" on tt.id = ttbi.town_id and  ttbi.business_name like '%"+dd.getSearchstr()+"%'";
		}else if(dd.getTarget()==2){//店长
			 /*sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_town_business_info ttbi "
						+" on t.town_id = ttbi.town_id and t.store_id="+dd.getStoreId()+"  DATE_FORMAT(ttbi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";*/
		}
		
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
		
	}


	
	@Override
	public List<Map<String, Object>> queryOffice(DynamicDto dd) {
		String sql="";
		if(dd.getTarget()==1){//城市总监
			 sql = "select cc.cityname,tt.name as townname,toi.office_name from "
					+" (select aa.id,bb.cityname from t_city aa ,"
					+" (SELECT tdc.id,tdc.cityname FROM t_dist_city a "
					
					+" INNER JOIN t_dist_citycode tdc ON a.citycode = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+" and tdc.id="+dd.getCityId()+") as bb where aa.`name` like CONCAT('%',bb.cityname,'%')) as cc "
					+" INNER JOIN t_county tc "
					+" on cc.id = tc.city_id "
					+" INNER JOIN t_town tt "
					+" on tc.id= tt.county_id "
					+" INNER JOIN "
					+" t_office_info toi "
					+" on tt.id = toi.town_id and  toi.office_name like '%"+dd.getSearchstr()+"%'";
		}else if(dd.getTarget()==2){//店长
			/* sql = "select ifnull(count(ttbi.id),0) as amount from "
						+" t_store t inner join "
						+" t_office_info toi "
						+" on t.town_id = toi.town_id and t.store_id="+dd.getStoreId()+" and  DATE_FORMAT(toi.create_time,'%Y-%c') = CONCAT('"+dd.getYear()+"','-','"+dd.getMonth()+"') ";*/
		}
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	
	
	@Override
	public Map<String, Object> queryAbnormalOrder(AbnormalOrderDto abnormalOrderDto,PageInfo pageInfo) {
		// TODO Auto-generated method stub
		String sql = "select a.*,a.abnortype as description,ifnull(b.gmv_price,0) as gmv_price from ds_abnormal_order a left join  df_mass_order_monthly b on a.ordersn = b.order_sn    where  a.status in ('0','3') ";
		if("can_appeal".equals(abnormalOrderDto.getDatatype())){
			sql="select a.*,a.abnortype as description,ifnull(b.gmv_price,0) as gmv_price,  case a.status when 0 then '未申诉'  when 1  then '申诉中' when 2 then '已驳回' when '3' then '已通过' end as state from ds_abnormal_order a  left join  df_mass_order_monthly b on a.ordersn = b.order_sn  where 1=1 ";
		}
		
		if(abnormalOrderDto.getBeginDate()!=null&&!"".equals(abnormalOrderDto.getBeginDate())){
			sql =sql+ " and DATE_FORMAT(a.signedtime,'%Y/%m/%d') >='"+abnormalOrderDto.getBeginDate()+"'";
		}
		
		if(abnormalOrderDto.getEndDate()!=null&&!"".equals(abnormalOrderDto.getEndDate())){
			sql = sql+ " and DATE_FORMAT(a.signedtime,'%Y/%m/%d') <= '"+abnormalOrderDto.getEndDate()+"'";
		}
		
		if(abnormalOrderDto.getAbnortype()!=null&&!"".equals(abnormalOrderDto.getAbnortype())){
			sql=sql+" and a.abnortype ='"+abnormalOrderDto.getAbnortype()+"'";
		}
		
		if(abnormalOrderDto.getStatus()!=null&&!"".equals(abnormalOrderDto.getStatus())){
			sql=sql+" and a.status ='"+abnormalOrderDto.getStatus()+"'";
		}
		
		if(abnormalOrderDto.getOrdersn()!=null&&!"".equals(abnormalOrderDto.getOrdersn())){
			sql=sql+" and a.ordersn ='"+abnormalOrderDto.getOrdersn()+"'";
		}
		
		if(abnormalOrderDto.getDatatype()!=null&&!"".equals(abnormalOrderDto.getDatatype())){
			if("cannot_appeal".equals(abnormalOrderDto.getDatatype())){
				sql=sql+" and a.datatype !='manual_can_appeal'";
			}else {
				sql=sql+" and a.datatype ='manual_can_appeal'";
			}
			
		}
		
		if(abnormalOrderDto.getStoreno()!=null&&!"".equals(abnormalOrderDto.getStoreno())){
			sql=sql+" and (b.store_id ='"+abnormalOrderDto.getStoreno()+"' or b.normal_store_id='"+abnormalOrderDto.getStoreno()+"')";
		}
		
		if(abnormalOrderDto.getCityname()!=null&&!"".equals(abnormalOrderDto.getCityname())){
			sql=sql+" and a.cityname like '%"+abnormalOrderDto.getCityname()+"%'";
		}
		
//		if(abnormalOrderDto.getProduct()!=null&&!"".equals(abnormalOrderDto.getProduct())){
//			sql=sql+" and a.cityname like '%"+abnormalOrderDto.getCityname()+"%'";
//		}
		
		if(abnormalOrderDto.getMinPrice()!=null&&!"".equals(abnormalOrderDto.getMinPrice().toString())){
			sql=sql+" and a.tradingprice >= "+abnormalOrderDto.getMinPrice();
		}
		
		if(abnormalOrderDto.getMaxPrice()!=null&&!"".equals(abnormalOrderDto.getMaxPrice().toString())){
			sql=sql+" and a.tradingprice <= "+abnormalOrderDto.getMaxPrice();
		}
		
		if(abnormalOrderDto.getDept()!=null&&!"".equals(abnormalOrderDto.getDept())){
			sql=sql+" and a.deptname like '%"+abnormalOrderDto.getDept()+"%'";
		}
		
		if(abnormalOrderDto.getChannel()!=null&&!"".equals(abnormalOrderDto.getChannel())){
			sql=sql+" and a.channelname like '%"+abnormalOrderDto.getChannel()+"%'";
		}
		if("can_appeal".equals(abnormalOrderDto.getDatatype())){
			sql=sql+" order by a.status,a.updatetime asc";
		}else{
			sql=sql+" order by a.signedtime desc";
		}
		
		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<?> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage()
						* (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

		Map<String,Object> map_result = new HashMap<String,Object>();
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("data", list);
		map_result.put("total_pages", total_pages);
		return map_result;
		
	}



	@Override
	public List<Map<String, Object>> queryAbnormalType() {
		String sql="select * from ds_abnormal_type where status=1";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	
	@Override
	public List<Map<String, Object>> queryAbnormalOrder(AbnormalOrderDto abnormalOrderDto) {
		String sql = "select a.*,a.abnortype as description,ifnull(b.gmv_price,0) as gmv_price,  case a.status when 0 then '未通过'   when '3' then '已通过' else a.status end as state from ds_abnormal_order a left join df_mass_order_monthly b on a.ordersn = b.order_sn    where  a.status in ('0','3') ";
		if("can_appeal".equals(abnormalOrderDto.getDatatype())){
			sql="select a.*,a.abnortype as description,ifnull(b.gmv_price,0) as gmv_price,  case a.status when 0 then '未申诉'  when 1  then '申诉中' when 2 then '已驳回' when '3' then '已通过' end as state from ds_abnormal_order a left join df_mass_order_monthly b on a.ordersn = b.order_sn where 1=1 ";
		}
		
		if(abnormalOrderDto.getBeginDate()!=null&&!"".equals(abnormalOrderDto.getBeginDate())){
			sql =sql+ " and DATE_FORMAT(a.signedtime,'%Y/%m/%d') >='"+abnormalOrderDto.getBeginDate()+"'";
		}
		
		if(abnormalOrderDto.getEndDate()!=null&&!"".equals(abnormalOrderDto.getEndDate())){
			sql = sql+ " and DATE_FORMAT(a.signedtime,'%Y/%m/%d') <= '"+abnormalOrderDto.getEndDate()+"'";
		}
		
		if(abnormalOrderDto.getAbnortype()!=null&&!"".equals(abnormalOrderDto.getAbnortype())){
			sql=sql+" and a.abnortype ='"+abnormalOrderDto.getAbnortype()+"'";
		}
		
		if(abnormalOrderDto.getStatus()!=null&&!"".equals(abnormalOrderDto.getStatus())){
			sql=sql+" and a.status ='"+abnormalOrderDto.getStatus()+"'";
		}
		
		if(abnormalOrderDto.getOrdersn()!=null&&!"".equals(abnormalOrderDto.getOrdersn())){
			sql=sql+" and a.ordersn ='"+abnormalOrderDto.getOrdersn()+"'";
		}
		
		if(abnormalOrderDto.getDatatype()!=null&&!"".equals(abnormalOrderDto.getDatatype())){
			if("cannot_appeal".equals(abnormalOrderDto.getDatatype())){
				sql=sql+" and a.datatype !='manual_can_appeal'";
			}else {
				sql=sql+" and a.datatype ='manual_can_appeal'";
			}
		}
		
		if(abnormalOrderDto.getStoreno()!=null&&!"".equals(abnormalOrderDto.getStoreno())){
			sql=sql+" and (b.store_id ='"+abnormalOrderDto.getStoreno()+"' or b.normal_store_id='"+abnormalOrderDto.getStoreno()+"')";

		}
		
		if(abnormalOrderDto.getCityname()!=null&&!"".equals(abnormalOrderDto.getCityname())){
			sql=sql+" and a.cityname like  '%"+abnormalOrderDto.getCityname()+"%'";
		}
		
//		if(abnormalOrderDto.getProduct()!=null&&!"".equals(abnormalOrderDto.getProduct())){
//			sql=sql+" and a.cityname like '%"+abnormalOrderDto.getCityname()+"%'";
//		}
		
		if(abnormalOrderDto.getMinPrice()!=null&&!"".equals(abnormalOrderDto.getMinPrice().toString())){
			sql=sql+" and a.tradingprice >= "+abnormalOrderDto.getMinPrice();
		}
		
		if(abnormalOrderDto.getMaxPrice()!=null&&!"".equals(abnormalOrderDto.getMaxPrice().toString())){
			sql=sql+" and a.tradingprice <= "+abnormalOrderDto.getMaxPrice();
		}
		
		if(abnormalOrderDto.getDept()!=null&&!"".equals(abnormalOrderDto.getDept())){
			sql=sql+" and a.deptname like '%"+abnormalOrderDto.getDept()+"%'";
		}
		
		if(abnormalOrderDto.getChannel()!=null&&!"".equals(abnormalOrderDto.getChannel())){
			sql=sql+" and a.channelname like '%"+abnormalOrderDto.getChannel()+"%'";
		}
		
		if("can_appeal".equals(abnormalOrderDto.getDatatype())){
			sql=sql+" order by a.status,a.updatetime asc";
		}else{
			sql=sql+" order by a.signedtime desc";
		}
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}


	
	@Override
	public List<Map<String, Object>> queryAbnormalByOrderSn(String ordersn) {
		// TODO Auto-generated method stub
		String  sql="select id ,storeno from ds_abnormal_order where ordersn='"+ordersn+"'";
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}


	
	@Override
	public List<Map<String, Object>> queryCityByUser(Integer target,Long userid, String name) {
		String sql="select a.id as ctid,a.cityname as name from  t_dist_citycode a inner join t_dist_city b on a.citycode= b.citycode and b.pk_userid="+userid;
		if(target==0||target==3){//总部权限或者事业群
			sql = "select a.id as ctid,a.cityname as name from t_dist_citycode a where 1=1 ";
		}
		if(name!=null){
			sql = sql+" and a.cityname like '%"+name+"%'";
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
		
	}


	
	@Override
	public List<Map<String, Object>> queryAbnormalType(String descrip) {
		String sql="select * from ds_abnormal_type where description='"+descrip+"' and datatype='manual'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	
	@Override
	public List<Map<String, Object>> queryStoreTradeOfDept(DynamicDto dynamicDto) {
		String sql = "select SUM(d.gmv) as gmv,name,storeno,career_group,storename,employee_no from (select a.*,b.store_name,b.dep_name,IFNULL(b.gmv,0) as gmv,ifnull(c.name,'') as name,ifnull(c.employee_no,'') as employee_no from "+
					" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td where storeno in ("+dynamicDto.getStoreNo()+")) a LEFT JOIN "+
					" (select store_name,storeno,dep_name,SUM(IFNULL(order_amount,0)) as gmv from ds_ope_gmv_storechannel_month where year="+dynamicDto.getYear()+"  and month="+dynamicDto.getMonth()+" GROUP BY storeno,dep_name) b "+
					" on a.storeno = b.storeno and a.career_group like CONCAT('%',b.dep_name,'%') LEFT JOIN (select th.name,th.employee_no,th.career_group,ts.storeno  from t_humanresources th INNER JOIN t_store ts on th.store_id = ts.store_id  and   th.zw='服务专员' and th.humanstatus = 1) c on  a.career_group  = c.career_group and c.storeno = a.storeno) d  GROUP BY d.storeno,d.career_group,d.employee_no";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	
	@Override
	public Map<String, Object> getLastMonthGMVCityRankingTop10(DynamicDto dd,PageInfo pageInfo) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql = "SELECT d.id as city_id,ds.city_name,SUM(ds.order_amount) as gmv_sum,t.province_id," +
				"t.`name` FROM ds_ope_gmv_store_month ds LEFT JOIN t_store t ON ds.storeno=t.storeno left join  t_dist_citycode d on d.cityname=t.city_name  " +
				"WHERE ds.`month`='"+dd.getMonth()+"' and ds.`year`='"+dd.getYear()+"' "+provinceStr+cityStr+" AND d.id IS NOT NULL GROUP BY ds.city_name  ORDER BY gmv_sum DESC ";
		String sql_count = "SELECT count(tdd.city_name) as city_count from ("+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("city_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}

	//cityId
	@Override
	public Map<String, Object> getLastMonthGMVStoreRankingTop10(DynamicDto dd,PageInfo pageInfo) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql = "SELECT d.id as city_id,t.city_name as city_name,t.store_id as store_id,ds.store_name as store_name,ds.order_amount as store_pesgmv,t.province_id " +
				"FROM ds_ope_gmv_store_month ds LEFT JOIN t_store t ON ds.storeno=t.storeno left join t_dist_citycode d on d.cityname=t.city_name  WHERE " +"t.storeno is not null "+
				" and ds.`month`='"+dd.getMonth()+"' " +
				"and ds.`year`='"+dd.getYear()+"' "+provinceStr+cityStr+" ORDER BY store_pesgmv DESC ";
		String sql_count = "SELECT count(tdd.store_name) as store_count from ("+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("store_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}


	@Override
	public List<Map<String, Object>> selectAllCity() {
		String sql="select * from  t_dist_citycode ";
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}



	/**
	 * 总部查看：按事业部交易额进行排名
	 * @param dynamicDto
	 * @return
	 */
	//cityId
	@Override
	public Map<String, Object> queryTradeByDepName(DynamicDto dynamicDto,PageInfo pageInfo) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select dsch.city_name as city_name,d.id as city_id,tdht.career_group as dep_name,ifnull(sum(order_amount),0) as order_amount from ds_ope_gmv_storechannel_month dsch "+
					"left join t_store ts on (dsch.storeno = ts.storeno) "+
					"left join t_data_human_type tdht on (tdht.career_group like CONCAT('%',dsch.dep_name,'%')) "+
					"left join t_dist_citycode d on d.cityname=ts.city_name  "+
					"where ts.storeno is not null and year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" AND tdht.career_group IS NOT NULL "+ 
					 provinceStr + cityStr +
					"group by tdht.career_group "+ 
					"order by sum(order_amount) desc ";
		String sql_count = "SELECT count(tdd.dep_name) as dep_count from ("+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("dep_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}

	/**
	 * 总部查看：按频道交易额进行排名
	 * @param dynamicDto
	 * @return
	 */
	@Override
	public Map<String, Object> queryTradeByChannelName(DynamicDto dynamicDto,PageInfo pageInfo) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select d.id as city_id,d.cityname as city_name,channel_name,ifnull(sum(order_amount),0) as order_amount from ds_ope_gmv_storechannel_month dsch "+
				"left join t_store ts on (dsch.storeno = ts.storeno) "+
				" left join t_dist_citycode d on d.cityname=ts.city_name "+
				"where ts.storeno is not null and year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" and channel_name is not null "+
				 provinceStr + cityStr +
				"group by channel_name "+ 
				"order by sum(order_amount) desc ";
		String sql_count = "SELECT count(tdd.channel_name) as channel_count from ( "+sql+" ) tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("channel_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}
    
	@Override
	public List<Map<String, Object>> findCityCount(DynamicDto dd) {
		String sql="";
		if(dd.getTarget()==1){//省
			sql="select t1.city_name,t2.id,t2.citycode,t2.cityno from (select id from t_city where province_id = "+dd.getProvinceId()+") t INNER JOIN t_store t1 on t.id = t1.city_id INNER JOIN t_dist_citycode t2 on t1.city_name = t2.cityname group by t1.city_name";
		}else if(dd.getTarget()==0||dd.getTarget()==3){//全国
			sql="select cityname as city_name,id,citycode,cityno from  t_dist_citycode ";
			if(dd.getCityId()!=null&&!"".equals(dd.getCityId())){
				sql=sql+" where id="+dd.getCityId();
			}
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}


	@Override
	public List<Map<String, Object>> findStoreCount(DynamicDto dd) {
		String sql="";
		if(dd.getTarget()==1){//省
			sql="select t.store_id,t.name,t.storeno,t.city_name,t1.id as cityId from t_store t INNER JOIN (select id from t_city where province_id="+dd.getProvinceId()+") t2 on t.city_id = t2.id " +
					"left join t_dist_citycode t1  on t.city_name  = t1.cityname where  t.name not like '%测试%' and " +
					"t.name not like '%储备%' and t.name not like '%办公室%' and t.flag='0' and ifnull(t.estate,'') ='运营中' and t.storetype!='V' and t.storetype!='W' ";
		}else if(dd.getTarget()==0||dd.getTarget()==3){//全国
			
			String whereStr = "";
			if(dd.getCityId()!=null&&!"".equals(dd.getCityId())){
				whereStr = " and t1.id="+dd.getCityId();
			}
			
			sql ="select t.store_id,t.name,t.storeno,t.city_name,t1.id as cityId from t_store t left join t_dist_citycode t1  on t.city_name  = t1.cityname where  t.name not like '%测试%' " +
					"and t.name not like '%储备%' and t.name not like '%办公室%' and t.flag='0' and ifnull(t.estate,'') ='运营中' and t.storetype!='V' and t.storetype!='W' "+ whereStr+"";
		}
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}


	@Override
	public List<Map<String, Object>> findStoreKeeperCount(DynamicDto dd) {
		
		String sql="";
		if(dd.getTarget()==0||dd.getTarget()==3){//全国||城市
			String whereStr="";
			if(dd.getCityId()!=null&&!"".equals(dd.getCityId())){
				whereStr = " where tdc.id="+dd.getCityId();
			}
			
			sql = "select tbu.name as keeperName,tbu.employeeId,tbu.mobilephone,tbu.id,t2.name as storeName,t2.city_name from (select t.skid,t.name,t.city_name " +
					" from t_store t  inner join  (select tdc.id,tdc.cityname from" +
					" t_dist_citycode tdc  "+whereStr+") t1	on t.city_name  = t1.cityname 		WHERE t. NAME NOT LIKE '%测试%' " +
							"AND t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') not like '%闭店%' " +
							"AND t.storetype != 'V' AND t.storetype != 'W' ) t2  " +
					" INNER JOIN tb_bizbase_user as tbu on t2.skid = tbu.id";
		}else if(dd.getTarget()==1){//省
			sql="select tbu.name as keeperName,tbu.employeeId,tbu.mobilephone,tbu.id,t.name as storeName,t.city_name from t_store t INNER JOIN (select id from t_city where province_id="+dd.getProvinceId()+") t2 on t.city_id = t2.id left join t_dist_citycode t1  on t.city_name  = t1.cityname left JOIN tb_bizbase_user as tbu on t.skid = tbu.id  where  " +
					"	t. NAME NOT LIKE '%测试%' AND t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') not like '%闭店%' " +
					"AND t.storetype != 'V' AND t.storetype != 'W' and tbu.employeeId is not null";
		}
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}
	@Override
	public Map<String, Object> findGaxCount(DynamicDto dd,PageInfo pageInfo) {
		String sql="";
		if(dd.getTarget()==0||dd.getTarget()==3){//城市||全国
			String whereStr=" ";
			if(dd.getCityId()!=null&&!"".equals(dd.getCityId())){
				whereStr=" where tdc.id="+dd.getCityId();
			}
			sql = "SELECT a.name,a.employee_no,b.storeName,a.sex,a.authorizedtype,a.topostdate,b.city_name FROM " +
					"`t_humanresources` a INNER JOIN (select t.store_id,name as storeName,t.city_name from t_store t  " +
					" inner join  (select tdc.id,tdc.cityname from t_dist_citycode tdc "+whereStr+")"+
					" t1 on t.city_name  = t1.cityname AND t. NAME NOT LIKE '%测试%' AND t. NAME NOT LIKE '%储备%' " +
					"AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') not like '%闭店%' ) b " +
					" on a.store_id = b.store_id and  a.humanstatus =1 where a.name not like '%测试%' ";
			
		}else if(dd.getTarget()==1){//省
			sql = "SELECT a.name,a.employee_no,b.storeName,a.sex,a.authorizedtype,a.topostdate,b.city_name FROM " +
					"`t_humanresources` a INNER JOIN (select t.store_id,name as storeName,t.city_name from t_store t  " +
					" inner join  (select id from t_city where province_id="+dd.getProvinceId()+")"+
					" t1 on t.city_id = t1.id AND t. NAME NOT LIKE '%测试%' " +
					"AND t. NAME NOT LIKE '%储备%' AND t. NAME NOT LIKE '%办公室%' AND t.flag = '0' AND ifnull(t.estate, '') not like '%闭店%' ) b " +
					" on a.store_id = b.store_id and  a.humanstatus =1 where a.name not like '%测试%' ";
		}
		
		if(dd.getEmployeeName()!=null&&!"".equals(dd.getEmployeeName())){
			sql = sql+" and a.name like '%"+dd.getEmployeeName()+"%'";
		}
		
		if(dd.getStoreName()!=null&&!"".equals(dd.getStoreName())){
			sql = sql+" and b.storeName like '%"+dd.getStoreName()+"%'";
		}
		
		if(dd.getCityName()!=null&&!"".equals(dd.getCityName())){
			sql = sql+" and b.city_name like '%"+dd.getCityName()+"%'";
		}
		if(dd.getSearchstr()!=null&&!"".equals(dd.getSearchstr())){
			sql = sql+" and a.zw = '"+dd.getSearchstr()+"'";
		}
		Map<String,Object> map_result = new HashMap<String,Object>();
		
		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";

		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		Object total = query_count.uniqueResult();
		pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<?>  list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

		
		Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
		map_result.put("pageinfo",pageInfo);
		map_result.put("data", list);
		map_result.put("status","success");
		map_result.put("totalPage", total_pages);
		return map_result;
	}

	@Override
	public List<Map<String, Object>> getLastMonthGMVStoreChinaRanking(DynamicDto dd) {
		String  sql = "SELECT a.name,a.employee_no,b.storeName,a.sex,a.authorizedtype,a.topostdate FROM " +
				"`t_humanresources` a INNER JOIN (select t.store_id,name as storeName from t_store t  " +
				"inner join  (select tdc.id,tdc.cityname from t_dist_city a  INNER JOIN t_dist_citycode tdc " +
				"on  a.citycode = tdc.citycode and a.pk_userid="+dd.getEmployeeId()+") t1 on t.city_name  = t1.cityname) b " +
				"on a.store_id = b.store_id and  a.humanstatus =1 where t.storeno is not null ";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	
	@Override
	public Map<String, Object> selectAreaRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo) {
	    //String sql = "select SUM(IFNULL(pesgmv,0)) as amount,area_no,area_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" group by area_no ORDER BY amount desc";
		//String sql = "select SUM(IFNULL(pesgmv,0)) as amount,area_no,area_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year=2017 and month=11 group by area_no ORDER BY amount desc";
		
		String  sql="SELECT SUM(IFNULL(a.gmv_price,0)) as amount,a.area_code as area_no,ifnull(b.name,a.area_code) as name "+
						" FROM (select * from df_mass_order_monthly  " +
						" where sign_time >='"+dynamicDto.getBeginDate()+" 00:00:00' and sign_time<'"+dynamicDto.getEndDate()+"'" +
						" and area_code is not null and store_code='"+dynamicDto.getStoreNo()+"'"+
						" ) a LEFT JOIN (select * from t_area where store_id="+dynamicDto.getStoreId()+" and status=0)  b on a.area_code = b.area_no"+
						" GROUP BY a.area_code ORDER BY amount desc";
		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
		
		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		if(pageInfo!=null){
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			Object total = query_count.uniqueResult();
			pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

			
			Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
			map_result.put("pageinfo",pageInfo);
			map_result.put("totalPage", total_pages);
			map_result.put("totalRecords", total);
		}else{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public List<Map<String, Object>> selectDeptRankingOfStore(DynamicDto dynamicDto) {
//		String sql="select convert(SUM(d.gmv),decimal(20,2))  as gmv,storeno,career_group,storename from "+
//					" (select a.*,b.store_name,b.department_name,IFNULL(b.gmv,0) as gmv from "+
//					" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td where storeno='"+dynamicDto.getStoreNo()+"') a LEFT JOIN "+ 
//					" (select store_name,store_code,department_name,SUM(IFNULL(gmv_price,0)) as gmv from df_mass_order_monthly where DATE_FORMAT(sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m') and store_code='"+dynamicDto.getStoreNo()+"' GROUP BY store_code,department_name) b  on a.storeno = b.store_code and a.career_group like CONCAT('%',b.department_name,'%') "+
//					" ) d  GROUP BY d.storeno,d.career_group";
		String sql="select SUM(d.gmv) as gmv,storeno,career_group,storename from "+
				" (select a.*,b.store_name,b.dep_name,IFNULL(b.gmv,0) as gmv from "+
				" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td where storeno='"+dynamicDto.getStoreNo()+"') a LEFT JOIN "+ 
				" (select store_name,storeno,dep_name,SUM(IFNULL(order_amount,0)) as gmv from ds_ope_gmv_storechannel_month where year= "+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno='"+dynamicDto.getStoreNo()+"' GROUP BY storeno,dep_name) b  on a.storeno = b.storeno and  a.career_group like CONCAT('%',b.dep_name,'%') "+
				" ) d  GROUP BY d.storeno,d.career_group";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	
	@Override
	public Map<String, Object> selectChannelRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo) {
		//String sql="select convert(SUM(IFNULL(gmv_price,0)),decimal(20,2)) as amount,channel_name as name from df_mass_order_monthly where DATE_FORMAT(sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m') and store_code='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc";
	    //String sql="select SUM(IFNULL(order_amount,0)) as amount,channel_name as name from ds_storetrade_channel where year = 2017 and month=11 and storeno='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc";
		String sql="select SUM(IFNULL(order_amount,0)) as amount,channel_name as name from ds_ope_gmv_storechannel_month where year = "+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc";

		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		if(pageInfo!=null){
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			Object total = query_count.uniqueResult();
			pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();
			Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
			map_result.put("pageinfo",pageInfo);
			map_result.put("totalPage", total_pages);
			map_result.put("totalRecords", total);			
		}else{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
		}
		map_result.put("gmv", list);
		return map_result;
	}

	//cityId
	@Override
	public Map<String, Object> CityOrderRankingTop10(DynamicDto dd,PageInfo pageInfo) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String  sql = "SELECT d.id as city_id,t.city_name as city_name,t.store_id as store_id,ds.store_name as store_name,sum(ds.order_count) AS order_count,t.province_id " +
				"FROM ds_pes_gmv_store_month ds LEFT JOIN t_store t ON ds.storeno=t.storeno left join t_dist_citycode d on d.cityname=t.city_name  WHERE t.storeno is not null and " +
				"ds.`month`='"+dd.getMonth()+"' and ds.`year`='"+dd.getYear()+"'"+provinceStr+cityStr+" GROUP BY ds.store_name ORDER BY " +
				"order_count DESC ";
		String sql_count = "SELECT count(tdd.store_name) as store_order_count from ("+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("store_order_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}



	@Override
	public Map<String, Object> selectEmployeeRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo) {
		//String sql = "select SUM(IFNULL(pesgmv,0)) as amount,employee_a_no,employee_a_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" group by employee_a_no ORDER BY amount desc";
		//String sql = "select SUM(IFNULL(pesgmv,0)) as amount,employee_a_no,employee_a_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year=2017 and month=11 group by employee_a_no ORDER BY amount desc";

		String sql=" select employee_no as employee_a_no,employee_name as name,IFNULL(pesgmv,0) as amount "+
				" from ds_pes_gmv_emp_month where year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" and storeno= '"+dynamicDto.getStoreNo()+"'  ORDER BY amount desc";
		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		if(pageInfo!=null){
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			Object total = query_count.uniqueResult();
			pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
			
			map_result.put("pageinfo",pageInfo);
			map_result.put("totalPage", total_pages);
			map_result.put("totalRecords", total);
		}else{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
		}
		map_result.put("gmv", list);
		return map_result;
	}



	@Override
	public List<Map<String, Object>> selectDeptServerRanking(DynamicDto dynamicDto) {
//		String sql = "select convert(SUM(d.gmv),decimal(20,2)) as gmv,name,storeno,career_group,storename,employee_no from (select a.*,b.store_name,b.department_name,IFNULL(b.gmv,0) as gmv,ifnull(c.name,'') as name,ifnull(c.employee_no,'') as employee_no from "+
//				" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td where storeno = '"+dynamicDto.getStoreNo()+"') a LEFT JOIN "+
//				" (select store_name,store_code,department_name,SUM(IFNULL(gmv_price,0)) as gmv from df_mass_order_monthly where DATE_FORMAT(sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m') GROUP BY store_code,department_name) b "+
//				" on a.storeno = b.store_code and a.career_group like CONCAT('%',b.department_name,'%') LEFT JOIN (select th.name,th.employee_no,th.career_group,ts.storeno  from t_humanresources th INNER JOIN t_store ts on th.store_id = ts.store_id  and   th.zw='服务专员' and th.humanstatus = 1 and ts.storeno='"+dynamicDto.getStoreNo()+"') c on  a.career_group  = c.career_group and c.storeno = a.storeno) d  GROUP BY d.storeno,d.career_group,d.employee_no having d.employee_no is not null and d.employee_no!=''  order by gmv desc ";
		String sql = "select SUM(d.gmv) as gmv,name,storeno,career_group,storename,employee_no from (select a.*,b.store_name,b.dep_name,IFNULL(b.gmv,0) as gmv,ifnull(c.name,'') as name,ifnull(c.employee_no,'') as employee_no from "+
				" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td where storeno = '"+dynamicDto.getStoreNo()+"') a LEFT JOIN "+
				" (select store_name,storeno,dep_name,SUM(IFNULL(order_amount,0)) as gmv from ds_ope_gmv_storechannel_month where year="+dynamicDto.getYear()+"  and month="+dynamicDto.getMonth()+" GROUP BY storeno,dep_name) b "+
				" on a.storeno = b.storeno and  a.career_group like CONCAT('%',b.dep_name,'%') LEFT JOIN (select th.name,th.employee_no,th.career_group,ts.storeno  from t_humanresources th INNER JOIN t_store ts on th.store_id = ts.store_id  and   th.zw='服务专员' and th.humanstatus = 1) c on  a.career_group  = c.career_group and c.storeno = a.storeno) d  GROUP BY d.storeno,d.career_group,d.employee_no having d.employee_no is not null and d.employee_no!=''  order by gmv desc ";
	SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	return lst_data;
	}


	
	@Override
	public Map<String, Object> selectAreaOrderRanking(DynamicDto dynamicDto,PageInfo pageInfo) {
		String sql = "select sum(ifnull(other_order_count,0)) as amount,employee_a_no,employee_a_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" group by employee_a_no ORDER BY amount desc ";
		//String sql = "select sum(ifnull(other_order_count,0)) as amount,employee_a_no,employee_a_name as name  from ds_areatrade where storeno='"+dynamicDto.getStoreNo()+"' and year=2017 and month=11 group by employee_a_no ORDER BY amount desc ";

		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		if(pageInfo!=null){
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			Object total = query_count.uniqueResult();
			pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

			
			Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
			
			map_result.put("pageinfo",pageInfo);
			map_result.put("totalRecords", total);
			map_result.put("totalPage", total_pages);
		}else{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> selectChannelOrderRanking(DynamicDto dynamicDto,PageInfo pageInfo) {
		//String sql="select count(1)  as amount,channel_name as name from df_mass_order_monthly where DATE_FORMAT(sign_time,'%Y-%m') = DATE_FORMAT(CURDATE(),'%Y-%m') and store_code='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc ";
		//String sql="select sum(ifnull(order_count,0))  as amount,channel_name as name from ds_storetrade_channel where year = 2017 and month=11 and storeno='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc ";
		String sql="select sum(ifnull(order_count,0))  as amount,channel_name as name from ds_ope_gmv_storechannel_month where year = "+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno='"+dynamicDto.getStoreNo()+"'  GROUP BY channel_name order by amount desc ";

		String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
		
		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		if(pageInfo!=null){
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			Object total = query_count.uniqueResult();
			pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

			Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
			map_result.put("pageinfo",pageInfo);
			map_result.put("totalPage", total_pages);
			map_result.put("totalRecords", total);
		}else{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public List<Map<String, Object>> selectGMVOfStore(DynamicDto dynamicDto) {
		String sql="select * from ds_pes_gmv_store_month where year = "+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno='"+dynamicDto.getStoreNo()+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}



	@Override
	public Map<String, Object> queryOrderCountByChannelName(DynamicDto dynamicDto,PageInfo pageInfo) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select dsch.channel_id as channel_id,channel_name,ifnull(sum(order_count),0) as order_count from ds_ope_gmv_storechannel_month dsch "+
				"left join t_store ts on (dsch.storeno = ts.storeno) left join t_dist_citycode d on d.cityname=ts.city_name  "+
				"where year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" and channel_name is not null "+
				 provinceStr + cityStr +
				"group by channel_name "+ 
				"order by sum(order_count) desc ";
		String sql_count = "SELECT count(tdd.channel_name) as chanel_count from ("+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("chanel_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}

	//总部当月营业额
	@Override
	public Map<String, Object> queryTradeSumByMonth(DynamicDto dynamicDto) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select ifnull(sum(order_amount),0) as order_amount,ifnull(sum(order_count),0) as order_count from ds_ope_gmv_store_month dst "+
				"left join t_store ts on (dst.storeno = ts.storeno) left join t_dist_citycode d on d.cityname=ts.city_name "+
				" where dst.store_name not like '%测试%' and year ="+dynamicDto.getYear()+" and month ="+dynamicDto.getMonth()+" "+
				  provinceStr + cityStr ;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		resuMap.put("cur_order_amount",Double.valueOf(String.valueOf(lst_data.get(0).get("order_amount"))));
		resuMap.put("month_order_count",Double.valueOf(String.valueOf(lst_data.get(0).get("order_count"))));
		return resuMap;
	}


	@Override
	public Map<String, Object> queryAreaTradeByEmp(DynamicDto dynamicDto,PageInfo pageInfo) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
		Map<String, Object> maps = new HashMap<String, Object>();
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select dat.city_name as city_name,d.id as city_id,d.cityno as cityno,dat.store_name as store_name,dat.employee_name AS employee_a_name,dat.employee_no AS employee_no,pesgmv as pesgmv from ds_pes_gmv_emp_month dat "+
				"left join t_store ts on (dat.storeno = ts.storeno) left join t_dist_citycode d on d.cityname=ts.city_name "+
				"where year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" "+
				 provinceStr + cityStr +"order by pesgmv desc ";
		String sql_count = "SELECT count(tdd.employee_no) as employee_a_count  from ( "+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("employee_a_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}


	@Override
	public List<Map<String, Object>> queryServerTradeByEmp(DynamicDto dynamicDto) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" where ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" where ts.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" where ts.city_id='"+province_id+"' ";
		}
		String sql = "select name as employee_name,sum(gmv) gmv from (select SUM(d.gmv) as gmv,name,storeno,career_group,storename,employee_no from (select a.*,b.store_name,b.dep_name,IFNULL(b.gmv,0) as gmv,ifnull(c.name,'') as name,ifnull(c.employee_no,'') as employee_no from "+
				" (select ts.storeno,ts.name as storename,ts.store_id,td.career_group from t_store ts,t_data_human_type td "+provinceStr+cityStr+") a LEFT JOIN "+
				" (select store_name,storeno,dep_name,SUM(IFNULL(order_amount,0)) as gmv from ds_ope_gmv_storechannel_month where year="+dynamicDto.getYear()+"  and month="+dynamicDto.getMonth()+" GROUP BY storeno,dep_name) b "+
				" on a.storeno = b.storeno and b.dep_name like CONCAT('%',a.career_group,'%') "+ 
				" LEFT JOIN (select th.name,th.employee_no,th.career_group,ts.storeno  from t_humanresources th INNER JOIN t_store ts on th.store_id = ts.store_id  and   th.zw='服务专员' and th.humanstatus = 1) c on  a.career_group  = c.career_group and c.storeno = a.storeno) d  "+ 
				" GROUP BY d.storeno,d.career_group,d.employee_no) tmpline " +
				" where tmpline.employee_no!='' group by employee_no order by sum(gmv) desc limit 10";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

//cityId
	@Override
	public Map<String, Object> queryTradeSumOfHistory(DynamicDto dynamicDto) {
		String province_id = dynamicDto.getProvinceId()==null?"":String.valueOf(dynamicDto.getProvinceId());
		String city_id = dynamicDto.getCityId()==null?"":String.valueOf(dynamicDto.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql ="select ifnull(sum(order_amount),0) as order_amount,ifnull(sum(order_count),0) as history_order_count from ds_ope_gmv_store_history dst "+
				" left join t_store ts on (dst.storeno = ts.storeno) left join t_dist_citycode d on d.cityname=ts.city_name "+
				" where dst.store_name not like '%测试%' "+ provinceStr + cityStr ;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map<String, Object> resuMap = new HashMap<String,Object>();
		if(lst_data!=null&&lst_data.size()>0){
			Map<String, Object> map_lst = (Map<String, Object>)lst_data.get(0);
			resuMap.put("history_order_amount",map_lst.get("order_amount"));
			resuMap.put("history_order_count",map_lst.get("history_order_count"));
		}
		return resuMap;
	}


	
	@Override
	public List<Map<String, Object>> selectHistoryGMVOfStore(DynamicDto dynamicDto) {
		String sql="select * from ds_storetrade_history where  storeno='"+dynamicDto.getStoreNo()+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}


	@Override
	public Map<String, Object> queryProductCityOrder(DynamicDto dynamicDto,PageInfo pageInfo) {
		String province_name = dynamicDto.getProvinceName()==null?"":String.valueOf(dynamicDto.getProvinceName());
		String city_name = dynamicDto.getCityName()==null?"":String.valueOf(dynamicDto.getCityName());
		String provinceStr = "";
		String cityStr = "";
		Map<String, Object> maps = new HashMap<String, Object>();
		if(city_name!=null&&city_name!=""){
			if(city_name.contains("黔东南")){
				city_name = city_name.substring(0,3);
			}
			cityStr+=" and tpc.city_name like '%"+city_name+"%' ";
		}else if(province_name!=null&&province_name!=""){
			provinceStr+=" and tpc.province_name like '%"+province_name+"%' ";
		}
		String sql = " select product_name,SUM(product_count) as product_count,city_name,product_id,cityno from ds_product_city tpc where 1=1 "+
					   cityStr+provinceStr +
					 " GROUP BY product_id,cityno order by product_count desc "  ;
		String sql_count = "SELECT count(tdd.product_name) as product_count from ( "+sql+") tdd ";
		Query query_count = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql_count);
		List<?> total = query_count
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(pageInfo!=null){
			if(total!=null&&total.size()>0){
				maps = (Map<String, Object>) total.get(0);
				pageInfo.setTotalRecords(Integer.valueOf(maps.get("product_count").toString()));
			}else{
				pageInfo.setTotalRecords(Integer.valueOf(0));
			}
		}
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		List<?> list = null;
		if(pageInfo==null){
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}else{
			list=query
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(
					pageInfo.getRecordsPerPage()
							* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> employeeOfAreaGmv(DynamicDto dynamicDto, PageInfo pageInfo) {
		String sql=" select city_name,storeno,store_name,employee_no,employee_name,pesgmv,pes_sendgmv,pes_areagmv,pes_assigngmv,pes_pergmv"+
					" from ds_pes_gmv_emp_month where year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" and storeno in ("+dynamicDto.getStoreNo()+")";
		if(dynamicDto.getEmployeeNo()!=null&&!"".equals(dynamicDto.getEmployeeNo())){
			sql=sql+" and employee_no like '%"+dynamicDto.getEmployeeNo()+"%'";
		}
		
		if(dynamicDto.getEmployeeName()!=null&&!"".equals(dynamicDto.getEmployeeName())){
			sql=sql+" and employee_name like '%"+dynamicDto.getEmployeeName()+"%'";
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<?> list=null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) as total from ("+sql+") c";
			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage()*(pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> storeGmv(DynamicDto dynamicDto, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		
		String sql=" select city_name,storeno,store_name, pesgmv,order_amount, other_order_amount,returned_amount, other_returned_amount, order_count,other_order_count, returned_count, other_returned_count "+
				   " from ds_pes_gmv_store_month where year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno in ("+dynamicDto.getStoreNo()+")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> storeNewaddcus(DynamicDto dynamicDto, PageInfo pageInfo) {
//		String sql="select  tor.store_code,tor.store_name,tor.store_city_name,sum(case when customer_isnew_flag >='10' and customer_isnew_flag !='-1' then 1 else 0 end) as new_10_count ,count(DISTINCT(tor.customer_id)) as total"+
//				   " from df_mass_order_monthly tor where  tor.customer_id not like 'fakecustomer%' "+
//				   " and DATE_FORMAT(tor.sign_time,'%Y-%m-%d') >='"+dynamicDto.getBeginDate()+"' and DATE_FORMAT(tor.sign_time,'%Y-%m-%d') <='"+dynamicDto.getEndDate()+"' "+
//				   " and tor.store_code in ("+dynamicDto.getStoreNo()+")"+
//				   " and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA' "+
//				   " and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0 group by store_id ";
		
		
		String sql="select  tor.storeno,tor.storename,tor.cityname ,new_cusnum_ten,cusnum,cusnum_ten"+
				   " from ds_pes_customer_store_month tor  "+
				   " where tor.year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+
				   " and tor.storeno in ("+dynamicDto.getStoreNo()+")";
				   
		List<?> list=null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Map<String, Object> map_result = new HashMap<String, Object>();
		if(pageInfo!=null){
			
		
			String sql_count = "SELECT count(1) as total from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		
		map_result.put("gmv", list);
		return map_result;
	}


	
	@Override
	public Map<String, Object> employeeOfAreaNewaddcus(DynamicDto dynamicDto, PageInfo pageInfo) {
		String sql="";
		if("pre".equals(dynamicDto.getSearchstr())){//上个月
			sql="select ifnull(a.new_cusnum_ten,0) as new_cusnum_ten,ifnull(a.cusnum,0) as cusnum,ifnull(a.cusnum_ten,0) as cusnum_ten,ts.city_name as cityname,ts.name as storename,ts.storeno, th.name,th.employee_no as employeeno  from (select employeeno,new_cusnum_ten ,cusnum ,cusnum_ten "+
					" from ds_pes_customer_employee_month tor "+
					" where year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+
					//" and tor.storeno in ("+dynamicDto.getStoreNo()+")"+
					" ) a right join (select username as name,employeeno as employee_no,storeid as store_id from ds_topdata where   year ="+dynamicDto.getYear()+" and month ="+dynamicDto.getMonth()+" and zw ='国安侠' and (humanstatus =1 or (humanstatus=2 and YEAR(DATE(leavedate)) =YEAR(DATE_ADD(NOW(),INTERVAL -1 MONTH)) and MONTH(DATE(leavedate))=MONTH(DATE_ADD(NOW(),INTERVAL -1 MONTH)))) and storeid IN ("+dynamicDto.getStoreIds()+")) th   on a.employeeno = th.employee_no   left join t_store ts on th.store_id = ts.store_id ";
		}else if("cur".equals(dynamicDto.getSearchstr())){//当前月
			 
			sql="select ifnull(a.new_cusnum_ten,0) as new_cusnum_ten,ifnull(a.cusnum,0) as cusnum,ifnull(a.cusnum_ten,0)  as cusnum_ten,ts.city_name as cityname,ts.name as storename,ts.storeno, th.name,th.employee_no as employeeno  from (select employeeno,new_cusnum_ten ,cusnum,cusnum_ten "+
					" from ds_pes_customer_employee_month tor "+
					" where year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+
					//" and tor.storeno in ("+dynamicDto.getStoreNo()+")"+
					" ) a  right join (select name,employee_no,store_id from t_humanresources where (humanstatus = 1 or (humanstatus=2 and YEAR(DATE(leavedate)) =YEAR(NOW()) and MONTH(DATE(leavedate))=MONTH(NOW()))) and  zw = '国安侠' and store_id IN ("+dynamicDto.getStoreIds()+")) th   on a.employeeno = th.employee_no   left join t_store ts on th.store_id = ts.store_id ";
		}
		
		sql=sql+" where 1=1 ";
		if(dynamicDto.getEmployeeNo()!=null&&!"".equals(dynamicDto.getEmployeeNo())){
			sql=sql+" and th.employee_no like '%"+dynamicDto.getEmployeeNo()+"%'";
		}
		
		if(dynamicDto.getEmployeeName()!=null&&!"".equals(dynamicDto.getEmployeeName())){
			sql=sql+" and th.name like '%"+dynamicDto.getEmployeeName()+"%'";
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<?> list= null;
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) as total from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(pageInfo.getRecordsPerPage()* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		
		map_result.put("gmv", list);
		return map_result;
	}

	
	@Override
	public Map<String, Object> getGaxOfAreaNewaddcus(DynamicDto dynamicDto, PageInfo pageInfo) {
		String sql="select ifnull(a.new_10_count,0) as amount, th.name,th.employee_no as info_employee_a_no  from (select info_employee_a_no as employee_a_no,ifnull(sum(case when customer_isnew_flag >='10' then 1 else 0 end),0) as new_10_count"+
				" from df_mass_order_monthly tor "+
				" where customer_isnew_flag !='-1'"+
				" and DATE_FORMAT(tor.sign_time,'%Y-%m-%d') >='"+dynamicDto.getBeginDate()+"' and DATE_FORMAT(tor.sign_time,'%Y-%m-%d') <= '"+dynamicDto.getEndDate()+"'"+
				" and tor.store_code = '"+dynamicDto.getStoreNo()+"'"+
				" and info_employee_a_no is not null and info_employee_a_no !=''"+
				" and tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA' "+
				" and tor.store_name NOT LIKE '%测试%' and tor.store_white!='QA' AND tor.store_status =0"+
				" group by info_employee_a_no) a right join (select name,employee_no,store_id from t_humanresources where humanstatus = 1 and  zw = '国安侠' and store_id IN ("+dynamicDto.getStoreId()+")) th   on a.employee_a_no = th.employee_no order by amount desc";
	
	Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	Map<String, Object> map_result = new HashMap<String, Object>();
	List<?> list= null;
	if(pageInfo!=null){
		String sql_count = "SELECT count(1) as total from ("+sql+") c ";
		Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
		pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
		list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(pageInfo.getRecordsPerPage()* (pageInfo.getCurrentPage() - 1))
			.setMaxResults(pageInfo.getRecordsPerPage()).list();
		Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
		map_result.put("pageinfo", pageInfo);
		map_result.put("totalPage", total_pages);
		map_result.put("totalRecords", pageInfo.getTotalRecords());	
	}else{
		list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
		map_result.put("gmv", list);
		return map_result;
	}

	@Override
	public List<Map<String, Object>> queryPlatformidByStoreId(String storeId) {
		String sql = "select store_id from t_store where platformid= '"+storeId+"' " ;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> queryAllCityCode() {
		String sql="select cityno from t_dist_citycode";
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryCityAllDept() {
		String sql="select career_group as dep_name,0 AS order_amount FROM t_data_human_type ";
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryMonthCustomerCount(DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String lastMonthSqlStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ds_cus.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and ds_cus.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and ds_cus.city_id='"+province_id+"' ";
		}
		String monthStr = "";
		String monthArr[] = dd.getBeginDate().split("-");
		int month = Integer.parseInt(monthArr[1]);
		monthStr = monthArr[0]+(month<10?("0"+month):month);
		String sql = "SELECT SUM(ds_cus.pay_count) AS  customer_count FROM  ds_cusum_month_city ds_cus " +
				"LEFT JOIN t_dist_citycode d ON d.id = ds_cus.city_id WHERE 1 = 1 AND " +
				"ds_cus.order_ym = '"+monthStr+"'"+ cityStr+provinceStr;
		
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if(lst_data != null){
				for(Object obj : lst_data){
					Map<String,Object> map_data = (Map<String,Object>)obj;
					Map<String,Object> map_content = (Map<String,Object>)obj;
					if(map_data.get("customer_count")==null){
						map_content.put("customer_count",0);
					}else{
						map_content.put("customer_count",map_data.get("customer_count"));
					}
					lst_result.add(map_content);
				}
			}
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> queryMonthCustomerCountGroup(DynamicDto dd, List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO, String key) {
		String sqlStr = "";
		String whereStr = "";
		String cityNo = "";
		String monthStr = "";
		if(cityNO!=null&&cityNO.size()>0){
			cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo!=null&&cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			whereStr+=" AND dcomt.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			whereStr+=" and dcomt.store_province_code='"+provinceNO.get(0).get("gb_code")+"' ";
		}
		if(key!=null&&!"".equals(key)){
			monthStr = " AND dcomt.sign_time LIKE '%"+dd.getYear()+"-"+(dd.getMonth()<10?("0"+dd.getMonth()):dd.getMonth())+"%'";
		}
		sqlStr = "SELECT count(DISTINCT(dcomt.customer_id)) AS month_customer_count,dcomt.store_province_code,dcomt.store_city_code,dcomt.store_city_name " +
					"FROM df_mass_order_monthly dcomt WHERE 1=1  " +
					monthStr+whereStr+" GROUP BY dcomt.store_city_code ORDER BY month_customer_count DESC ";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlStr);
			List<?> lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(lst_data != null){
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("customer_count",map_data.get("customer_count"));
                    if(String.valueOf(map_data.get("store_city_name")).lastIndexOf("市")>0){
                    	map_content.put("store_city_name",String.valueOf(map_data.get("store_city_name")).substring(0,String.valueOf(map_data.get("store_city_name")).length()-1));
                    }else{
                    	map_content.put("store_city_name",map_data.get("store_city_name"));
                    }
                    lst_result.add(map_content);
                }
            }
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> queryYearSumGMV(DynamicDto dd,String cityId,
			String provinceId, String key) {
		String sqlStr = "";
		String monthStr = "";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ts.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		if(key!=null&&!"".equals(key)){
			monthStr = " AND dcomt.year="+dd.getYear()+"";
		}
		sqlStr = "SELECT sum(dcomt.order_amount) as year_sum_gmv,SUM(dcomt.order_count) AS year_sum_order " +
					" FROM ds_ope_gmv_store_month dcomt left join t_store ts on dcomt.storeno=ts.storeno left join t_dist_citycode d on d.cityname=ts.city_name " +
					"WHERE 1=1  " +
					monthStr+provinceStr + cityStr;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlStr);
			List<?> lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(lst_data != null){
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("year_sum_gmv",map_data.get("year_sum_gmv"));
                    lst_result.add(map_content);
                }
            }
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	public Map<String, Object> queryCustomerCountByTime(DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ds_cus.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and ds_cus.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and ds_cus.city_id='"+province_id+"' ";
		}
		String sql = "SELECT SUM(ds_cus.pay_count) AS month_customer_count,SUM(ds_cus.new_count) AS new_customer_count," +
				"date_format(ds_cus.sign_date, '%m-%d') AS week_date,d.id FROM ds_cusum_day_city ds_cus left join  t_dist_citycode d on d.id=ds_cus.city_id WHERE 1=1 "+cityStr+provinceStr+
				" and ds_cus.sign_date >='"+dd.getBeginDate()+"' AND ds_cus.sign_date <='"+dd.getEndDate()+"' GROUP BY ds_cus.sign_date ";
		
		List<Map<String, Object>> lst_data = null;
	 	Map<String, Object> map_all = new HashMap<String, Object>();
	     try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
	     Integer total_pages = 0;
	     map_all.put("lst_data", lst_data);
	     map_all.put("totalPage", total_pages);
		return map_all;
	}
	@Override
	public Map<String, Object> queryTurnoverByTime(String cityNo,String beginDate,
			String endDate) {
		String sqlStr = "";
		String cityStr1 = "";
		List<Float> arrSort = new ArrayList<Float>();
		Map<String, Object> maxMap = new HashMap<String, Object>();
		if(cityNo.startsWith("00")){
			cityNo = cityNo.substring(1,cityNo.length());
		}
		cityStr1+=" and store_city_code='"+cityNo+"' ";
		sqlStr = "SELECT IFNULL(COUNT(trading_price), 0) AS checked_order_count,IFNULL(SUM(total_quantity), 0) " +
				"AS total_order_count,IFNULL(SUM(trading_price), 0) AS storecur_all_price,IFNULL(SUM(total_quantity), 0) " +
				"AS total_quantity,DAY (sign_time) AS week_date,DATE(sign_time) FROM df_mass_order_monthly WHERE sign_time >= '"+
				beginDate+"'AND sign_time <= '"+endDate+"'"+cityStr1+" GROUP BY DATE(sign_time)";
		List<Map<String, Object>> lst_data = null;
	 	List<Map<String, Object>> max_data = new ArrayList<Map<String,Object>>();
	 	Map<String, Object> map_all = new HashMap<String, Object>();
	     try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sqlStr);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
	     Integer total_pages = 0;
	     if(lst_data!=null&&lst_data.size()>0){
	    	 for (Map<String, Object> lst_data_map : lst_data) {
	    		 Float checkedCount = Float.valueOf(String.valueOf(lst_data_map.get("storecur_all_price")));
	    		 arrSort.add(checkedCount);
	    	 }
	    	 maxMap.put("max_all_price", Collections.max(arrSort));
	     }else{
	    	 maxMap.put("max_all_price", 0);
	     }
	     max_data.add(maxMap);
	     map_all.put("lst_data", lst_data);
	     map_all.put("max_price", max_data);
	     map_all.put("totalPage", total_pages);
		return map_all;
	}
	@Override
	public Map<String, Object> queryNewMonthUserCount(DynamicDto dd,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		Map<String, Object> map_all = new HashMap<String, Object>();
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ds_cus.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and ds_cus.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and ds_cus.city_id='"+province_id+"' ";
		}
		String sql = "SELECT SUM(ds_cus.new_count) AS new_cus_count,SUM(ds_cus.pay_count) AS pay_cus_count," +
				"date_format(ds_cus.sign_date, '%m-%d') AS week_date,d.id FROM ds_cusum_day_city ds_cus left join  t_dist_citycode " +
				"d on d.id=ds_cus.city_id WHERE 1=1 "+cityStr+provinceStr+" and ds_cus.sign_date>='"
				+dd.getBeginDate()+"' and ds_cus.sign_date <='"+dd.getEndDate()+"' GROUP BY ds_cus.sign_date ORDER BY week_date ";
		
		List<Map<String, Object>> lst_data = null;
		try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		map_all.put("lst_data", lst_data);
		return map_all;
	}
	@Override
	public Map<String, Object> getWeekCustomerOrderRate(DynamicDto dd) {
		Map<String, Object> map_all = new HashMap<String, Object>();
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ds_cus.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and ds_cus.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and ds_cus.city_id='"+province_id+"' ";
		}
		String sql = "SELECT SUM(ds_cus.new_count) AS new_cus_count,SUM(ds_cus.pay_count) AS pay_cus_count," +
				"date_format(ds_cus.sign_date, '%m-%d') AS week_date,d.id FROM ds_cusum_day_city ds_cus left join  t_dist_citycode d on d.id=ds_cus.city_id WHERE 1=1 "+cityStr+provinceStr+
				" and ds_cus.sign_date >='"+dd.getBeginDate()+"' AND ds_cus.sign_date <='"+dd.getEndDate()+"' GROUP BY ds_cus.sign_date ";
		List<Map<String, Object>> lst_data = null;
		try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		map_all.put("lst_data", lst_data);
		return map_all;
	}
	@Override
	public List<Map<String, Object>> queryHistoryCustomerCount(DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND ds_cus.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and ds_cus.city_id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and ds_cus.city_id='"+province_id+"' ";
		}
		String monthStr = dd.getBeginDate().substring(0,dd.getBeginDate().lastIndexOf("-")).replace("-", "");
		String sql = "SELECT SUM(ds_cus.pay_count) AS  history_customer_count FROM ds_cusum_month_city ds_cus LEFT JOIN " +
				"t_dist_citycode d ON d.id = ds_cus.city_id WHERE 1 = 1 AND " +
				"ds_cus.order_ym <= '"+monthStr+"'"+ cityStr+provinceStr;
		
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if(lst_data != null){
				for(Object obj : lst_data){
					Map<String,Object> map_data = (Map<String,Object>)obj;
					Map<String,Object> map_content = (Map<String,Object>)obj;
					if(map_data.get("history_customer_count")==null){
						map_content.put("history_customer_count",0);
					}else{
						map_content.put("history_customer_count",map_data.get("history_customer_count"));
					}
					lst_result.add(map_content);
				}
			}
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> queryMonthZbCustomerCount(DynamicDto dd) {
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		String monthStr = "";
		String monthArr[] = dd.getBeginDate().split("-");
		int month = Integer.parseInt(monthArr[1]);
		monthStr = monthArr[0]+(month<10?("0"+month):month);
		//String sql = "select count(distinct customer_id) as customer_count from df_customer_order_month_trade_new where order_ym ='"+monthStr+"'";
		String sql = "select cusnum_month as customer_count from ds_ope_gmvorcus_all_total ";
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    	 if(lst_data != null){
					for(Object obj : lst_data){
						Map<String,Object> map_data = (Map<String,Object>)obj;
						Map<String,Object> map_content = (Map<String,Object>)obj;
						if(map_data.get("customer_count")==null){
							map_content.put("customer_count",0);
						}else{
							map_content.put("customer_count",map_data.get("customer_count"));
						}
						lst_result.add(map_content);
					}
				}
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> queryHistoryZbCustomerCount(DynamicDto dd) {
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		String sql = "select cusnum_history as customer_count from ds_ope_gmvorcus_all_total ";
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    	 if(lst_data != null){
					for(Object obj : lst_data){
						Map<String,Object> map_data = (Map<String,Object>)obj;
						Map<String,Object> map_content = (Map<String,Object>)obj;
						if(map_data.get("customer_count")==null){
							map_content.put("history_customer_count",0);
						}else{
							map_content.put("history_customer_count",map_data.get("customer_count"));
						}
						lst_result.add(map_content);
					}
				}
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		return lst_result;
	}

	
	@Override
	public Map<String, Object> queryEmployeeSendorders(DynamicDto dd,PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String whereStr = "";
		if(dd.getDept()!=null&&!"".equals(dd.getDept())){
			whereStr = whereStr+" and dss.deptname like '%"+dd.getDept()+"%'";
		}
		
		if(dd.getChannel()!=null&&!"".equals(dd.getChannel())){
			whereStr = whereStr+" and dss.channelname like '%"+dd.getChannel()+"%'";
		}
		
		if(dd.getEmployeeName()!=null&&!"".equals(dd.getEmployeeName())){
			whereStr = whereStr+" and dss.username like '%"+dd.getEmployeeName()+"%'";
		}
		
		if(dd.getEmployeeNo()!=null&&!"".equals(dd.getEmployeeNo())){
			whereStr = whereStr+" and dss.employee_no like '%"+dd.getEmployeeNo()+"%'";
		}
		String sql="select datanum as total,dss.deptname,dss.channelname,dss.cityname,dss.storename,dss.storeno,dss.employee_no,ifnull(dss.username,'') as username"+
				" from ds_pes_order_empchannel_month dss "+ 
				" where year="+dd.getYear()+" and month="+dd.getMonth()+whereStr+
				" and dss.storeno in ("+dd.getStoreNo()+")"+
				" and dss.employee_no is not null and dss.employee_no !=''";
				
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("sendorders", list);
		return map_result;
	}

	
	@Override
	public Map<String, Object> queryDeptGmv(DynamicDto dd, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String whereStr = "";
		if(dd.getDept()!=null&&!"".equals(dd.getDept())){
			whereStr =whereStr+ " and concat(deptname,'事业部') like '%"+dd.getDept()+"%'";
		}
		
		if(dd.getCityName()!=null&&!"".equals(dd.getCityName())){
			whereStr = whereStr+" and cityname like '"+dd.getCityName()+"%'";
		}
		
		if(dd.getStoreNo()!=null&&!"".equals(dd.getStoreNo())){
			whereStr = whereStr+" and storeno = '"+dd.getStoreNo()+"'";
		}
		String sql="select cityname,storeno,storename,deptname,pesgmv"+
				" from ds_pes_gmv_storedept_month  "+
				" where year="+dd.getYear()+" and month="+dd.getMonth()+whereStr+
				" order by storeno";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}

	@Override
	public Map<String, Object> queryDeptConsumer(DynamicDto dd, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String whereStr = "";
		if(dd.getDept()!=null&&!"".equals(dd.getDept())){
			whereStr =whereStr+ " and concat(deptname,'事业部') like '%"+dd.getDept()+"%'";
		}
		
		if(dd.getCityName()!=null&&!"".equals(dd.getCityName())){
			whereStr = whereStr+" and cityname like '"+dd.getCityName()+"%'";
		}
		
		if(dd.getStoreNo()!=null&&!"".equals(dd.getStoreNo())){
			whereStr = whereStr+" and storeno = '"+dd.getStoreNo()+"'";
		}
		String sql="select cityname,storeno,storename,deptname,cusnum,ifnull(cusnum_ten,0) as cusnum_ten"+
				" from ds_pes_customer_storedept_month  "+
				" where year="+dd.getYear()+" and month="+dd.getMonth()+whereStr+
				" order by storeno";
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("consumer", list);
		return map_result;
	}
	@Override
	public List<Map<String, Object>> queryAllProvinces() {
		String sql = "SELECT tp.id as province_id,tp.name as name ,tp.type as type from t_province tp ";
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryAllOpenProvinces() {
		String sql = "SELECT ts.province_id as province_id,tp.name,tp.type from t_store ts " +
				"LEFT JOIN t_province tp ON tp.id=ts.province_id WHERE ts.province_id IS NOT " +
				"NULL GROUP BY ts.province_id ";
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryCityByName(String cityname) {
		String sql = "SELECT tc.* from t_dist_citycode tc ";
		if(cityname!=null&&!"".equals(cityname)){
			sql+= " where tc.cityname ='"+cityname+"' ";
		}
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_data;
	}

	@Override
	public Map<String, Object> queryEmployeeAvgCustomer(Integer number) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int i = number; i >0; i--){
			
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> queryMonthAndLastMonthTodayCustomerOrderCount(
			DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		Date myDate = new Date();
		int date = myDate.getDate();
		int lastMaxDate = DateUtils.getLastMonthMaxDay();
		String lastTodayDay = "";
		if(date>=lastMaxDate){//如果当前日大于上个月最大的日,取上个月最大日期
			lastTodayDay = DateUtils.getLastMaxDate();
		}else{
			lastTodayDay = DateUtils.getLastMonthTodayDay();
		}
		String sql = "SELECT count(distinct ds.customer_id) as customer_count,IFNULL(COUNT(ds.customer_id),0) AS checked_order_count FROM df_mass_order_monthly ds LEFT JOIN t_store t " +
				"ON ds.store_code=t.storeno left join t_dist_citycode d on d.cityname=t.city_name WHERE ds.sign_time >='" +
				lastTodayDay+" 00:00:00' and ds.sign_time<='"+lastTodayDay+" 23:59:59' "+provinceStr+cityStr;
		try{
			SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    	 if(lst_data != null){
					for(Object obj : lst_data){
						Map<String,Object> map_data = (Map<String,Object>)obj;
						Map<String,Object> map_content = (Map<String,Object>)obj;
						if(map_data.get("customer_count")==null){
							map_content.put("last_customer_count",0);
						}else{
							map_content.put("last_customer_count",map_data.get("customer_count"));
						}
						if(map_data.get("checked_order_count")==null){
							map_content.put("last_order_count",0);
						}else{
							map_content.put("last_order_count",map_data.get("checked_order_count"));
						}
						lst_result.add(map_content);
					}
				}
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getDailyNowStoreOrderOfCurDay(DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql = "SELECT IFNULL(COUNT(tor.trading_price),0) AS checked_order_count,IFNULL(SUM(tor.total_quantity), 0) " +
				"AS total_order_count,IFNULL(SUM(tor.trading_price),0) AS storecur_all_price,IFNULL" +
				"(SUM(tor.total_quantity),0) AS total_quantity FROM df_mass_order_daily tor LEFT JOIN t_store t " +
				"ON tor.store_code=t.storeno left join  t_dist_citycode d on d.cityname=t.city_name  " +
				"WHERE tor.store_name NOT LIKE '%测试%' and tor.sign_time  BETWEEN '"+dd.getBeginDate()+
				" 00:00:00' and '"+dd.getEndDate()+" 23:59:59'"+provinceStr+cityStr;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	        if(lst_data != null){
	            for(Object obj : lst_data){
	            	 Map<String,Object> map_data = (Map<String,Object>)obj;
	                 Map<String,Object> map_content = (Map<String,Object>)obj;
	                 map_content.put("storecur_all_price",map_data.get("storecur_all_price"));
	                 map_content.put("checked_order_count",map_data.get("checked_order_count"));
	                 lst_result.add(map_content);
	            }
	        }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getDailyNowUserOfCurDay(DynamicDto dd) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sql = "SELECT COUNT(DISTINCT customer_id) customer_count  FROM df_mass_order_daily " +
				"tor LEFT JOIN t_store t ON tor.store_code=t.storeno left join t_dist_citycode d on d.cityname=t.city_name  " +
				"WHERE tor.store_name NOT LIKE '%测试%' and tor.sign_time  BETWEEN '"+dd.getBeginDate()+
				" 00:00:00' and '"+dd.getEndDate()+" 23:59:59'"+provinceStr+cityStr;
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<?> lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(lst_data != null){
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("customer_count",map_data.get("customer_count"));
                    lst_result.add(map_content);
                }
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> getDailyFirstOrderCity() {
		String sqlStr = "";
		sqlStr="SELECT t.store_province_code as province_code,t.store_city_code as city_code,tor.signe_time FROM df_mass_order_daily tor LEFT JOIN " +
				"t_store t ON t.storeno = tor.store_code  WHERE t.store_city_code IS NOT null ORDER BY tor.sign_time DESC LIMIT 6 ";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sqlStr);
			List<Map<String,Object>> lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	@Override
	public Map<String, Object> getCityGMVRangeForWeek(DynamicDto dd,List<Map<String, Object>> cityNO, 
			List<Map<String, Object>> provinceNO) {
		Map<String, Object> map_all = new HashMap<String, Object>();
		String cityStr1 = "";
		String provinceStr1 = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			cityStr1+=" and dom.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			provinceStr1+=" and dom.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		String sql = "SELECT IFNULL(FLOOR(sum(dom.trading_price)), 0) AS week_gmv,date_format(dom.sign_time, '%m-%d') AS week_date FROM df_mass_order_monthly dom  " +
				"WHERE dom.store_name NOT LIKE '%测试%' AND dom.sign_time >='"+dd.getBeginDate()+" 00:00:00' AND dom.sign_time<='"+dd.getEndDate()+" 23:59:59' "+provinceStr1+cityStr1+"   GROUP BY DATE(dom.sign_time) ";
		List<Map<String, Object>> lst_data = null;
		try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		map_all.put("lst_data", lst_data);
		return map_all;
	}
	@Override
	public List<Map<String, Object>> getStoreKindCountByCityAndProvince(
			DynamicDto dd) {
		String cityStr = "";
		String provinceStr = "";
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String zx = "no";
//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//			zx = "yes";
//		}
		if(province_id!=null&&province_id!=""&&"no".equals(zx)){
			provinceStr+=" AND t.province_id='"+province_id+"' ";
		}
		if(city_id!=null&&city_id!=""){
			cityStr+=" and d.id='"+city_id+"' ";
		}else if("yes".equals(zx)){
			cityStr+=" and d.id='"+province_id+"' ";
		}
		String sqlStr = "";
		sqlStr="SELECT CASE WHEN t.storetype = 'X' THEN '经营星店' WHEN t.storetype = 'E' THEN '校园店' " +
				"ELSE t.storetypename END AS storetypename,count(storetypename) AS store_kind_count " +
				"FROM t_store t LEFT JOIN t_dist_citycode d ON t.cityno=d.cityno  WHERE t.storetype !='V' AND " +
				"t.storetype !='W' AND t.storetypename IS NOT NULL and t.flag=0 AND t.`name` NOT  LIKE '%测试%' and t.`name` NOT  LIKE '%储备%' and t.`name` NOT  LIKE '%办公室%' AND ifnull(t.estate,'')='运营中' "+provinceStr+cityStr+"  GROUP BY t.storetype ORDER BY store_kind_count DESC";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		
		try{ 
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sqlStr);
			List<Map<String,Object>> lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}
	@Override
	public Map<String, Object> getCityGMVRangeForMonth(DynamicDto dd,
			List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO) {
		Map<String, Object> map_all = new HashMap<String, Object>();
		String cityStr1 = "";
		String provinceStr1 = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			cityStr1+=" and dom.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			provinceStr1+=" and dom.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
		String sql = "SELECT IFNULL(FLOOR(sum(dom.trading_price)), 0) AS month_gmv,date_format(dom.sign_time, '%m-%d') AS week_date FROM df_mass_order_monthly dom  " +
				"WHERE dom.store_name NOT LIKE '%测试%' AND dom.sign_time >='"+dd.getBeginDate()+" 00:00:00' AND dom.sign_time<='"+dd.getEndDate()+" 23:59:59' "+provinceStr1+cityStr1+"   GROUP BY DATE(dom.sign_time) ";
		List<Map<String, Object>> lst_data = null;
		try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		map_all.put("lst_data", lst_data);
		return map_all;
	}

	@Override
	public List<Map<String, Object>> selectAllCitySort(DynamicDto dd) {
		String sql= "";
		if(dd==null){
			sql="SELECT d.cityname as city_name,d.id as id,d.citycode as citycode,d.cityno as cityno FROM t_store t LEFT JOIN t_dist_citycode d ON t.city_name=d.cityname WHERE t.flag=0 AND t.`name` NOT  LIKE '%测试%' and t.`name` NOT  LIKE '%储备%' and t.`name` NOT  LIKE '%办公室%' and t.storetype!='V' and t.storetype !='W' AND ifnull(t.estate,'') not like '%闭店%' GROUP BY t.cityno ";
		}else{
			String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
			String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
			String provinceStr = "";
			String cityStr = "";
			String zx = "no";
//			if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
//				zx = "yes";
//			}
			if(province_id!=null&&province_id!=""&&"no".equals(zx)){
				provinceStr+=" AND province_id='"+province_id+"' ";
			}
			if(city_id!=null&&city_id!=""){
				cityStr+=" and dd.id='"+city_id+"' ";
			}else if("yes".equals(zx)){
				cityStr+=" and dd.id='"+province_id+"' ";
			}
			sql = "SELECT dd.* FROM (SELECT d.cityname as city_name,d.id as id,d.citycode as citycode,d.cityno as cityno,t.province_id as province_id  FROM t_store t LEFT JOIN t_dist_citycode d ON t.city_name=d.cityname WHERE t.flag=0 AND t.`name` NOT  LIKE '%测试%' and t.`name` NOT  LIKE '%储备%' and t.`name` NOT  LIKE '%办公室%' and t.storetype!='V' and t.storetype !='W' AND ifnull(t.estate,'') not like '%闭店%' GROUP BY t.cityno) dd LEFT JOIN t_province tp ON tp.id = dd.province_id where 1=1 " +
					provinceStr+cityStr;
		}
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;
	}

	@Override
	public Map<String, Object> getStoreMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String join_sql = "";
		String condition_sql = "";
		String sql = "";
		if(dynamicDto.getStoreId()==null||"".equals(dynamicDto.getStoreId())){
			if(!cityNo.equals("")){
				join_sql = " INNER JOIN t_dist_citycode city ON (lpad(dutm.regist_cityno,4,'0') = city.cityno) ";
				condition_sql = " where lpad(member.regist_cityno,4,'0') = '"+cityNo+"' ";
			}
			sql = "select ifnull(ts.storeno,'无') as storeno,ifnull(ts.name,'无') as name,ifnull(ts.city_name,'无') as city_name,sum(dutm.nowcount) as nowcount,sum(dutm.opencount) as opencount, " +
					"sum(dutm.count199) as count199 from (select regist_storeid,regist_cityno, " +
					"SUM(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.status = 1 and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59'  then 1 else 0 end) as opencount, " +
					"SUM(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount, " +
					"SUM(case when (member_type = 'associator_start_2' or member_type = 'yearCard' or member_type = 'yearCard-19') and member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as count199 " +
					"from df_user_member member "+condition_sql+" GROUP BY member.regist_storeid) dutm " +
					"left join t_store ts ON (dutm.regist_storeid = ts.platformid) "+join_sql+" GROUP BY ts.storeno " +
					"ORDER BY (case when ts.storeno is null then '9010C0130' else ts.storeno end),ts.storeno asc";
		}else{
			sql = "select ifnull(ts.storeno,'无') as storeno,ifnull(ts.name,'无') as name,ifnull(ts.city_name,'无') as city_name,dutm.nowcount as nowcount,dutm.opencount as opencount," +
					"dutm.count199 as count199 from (select regist_storeid,regist_cityno," +
					"SUM(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.status = 1 and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as opencount," +
					"SUM(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount," +
					"SUM(case when (member_type = 'associator_start_2' or member_type = 'yearCard' or member_type = 'yearCard-19') and member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as count199 " +
					"from df_user_member member GROUP BY member.regist_storeid) dutm " +
					"left join t_store ts ON (dutm.regist_storeid = ts.platformid) where ts.storeno = '"+dynamicDto.getStoreNo()+"' " +
					"ORDER BY (case when ts.storeno is null then '9010C0130' else ts.storeno end),ts.storeno asc";
		}
		
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			
			
			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}
	
	
	@Override
	public Map<String, Object> getCityMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();

		String sql= "";
		if(cityNo == null || "".equals(cityNo) ){
			sql="select ifnull(city.cityname,'无') as city_name,sum(dutm.opencount) as opencount,sum(dutm.nowcount) as nowcount,sum(dutm.count199) as count199 from (select (case when regist_cityno = '' then null else regist_cityno end) as cityno," +
					"SUM(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.status = 1 and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as opencount," +
					"SUM(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount," +
					"SUM(case when (member_type = 'associator_start_2' or member_type = 'yearCard' or member_type = 'yearCard-19') and member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as count199 " +
					"from df_user_member member GROUP BY cityno) dutm LEFT JOIN t_dist_citycode city ON (lpad(dutm.cityno,4,'0') = city.cityno) " +
					"GROUP BY city_name ORDER BY (case when city.cityno is null then '9999' else city.cityno end),city.cityno";
		}else{
			sql="select ifnull(city.cityname,'无')  as city_name,dutm.opencount,dutm.nowcount,dutm.count199 from (select (case when regist_cityno = '' then null else regist_cityno end) as cityno," +
					"SUM(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.status = 1 and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as opencount," +
					"SUM(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount," +
					"SUM(case when (member_type = 'associator_start_2' or member_type = 'yearCard' or member_type = 'yearCard-19') and member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as count199 " +
					"from df_user_member member where member.status = 1 and lpad(member.regist_cityno,4,'0') = '"+cityNo+"') dutm LEFT JOIN t_dist_citycode city ON (lpad(dutm.cityno,4,'0') = city.cityno)";
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();


			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}

	@Override
	public Map<String, Object> getStoreTryMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		String join_sql = "";
		String condition_sql = "";
		String sql = "";
		if(dynamicDto.getStoreId()==null||"".equals(dynamicDto.getStoreId())){
			if(!cityNo.equals("")){
				join_sql = " INNER JOIN t_dist_citycode city ON (lpad(dutm.regist_cityno,4,'0') = city.cityno) ";
				condition_sql = " where lpad(member.regist_cityno,4,'0') = '"+cityNo+"' ";
			}
			sql = "select ifnull(ts.storeno,'无') as storeno,ifnull(ts.name, '无') AS name,ifnull(ts.city_name, '无') AS city_name,sum(dutm.opencount) as opencount,sum(dutm.nowcount) as nowcount " +
					"from (select regist_storeid,regist_cityno, " +
					"sum(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end ) as opencount, " +
					"sum(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount " +
					"from df_user_try_member member "+condition_sql+" group by member.regist_storeid ) dutm   " +
					"left join t_store ts  on (dutm.regist_storeid = ts.platformid) "+join_sql+" GROUP BY ts.storeno " +
					"ORDER BY (case when ts.storeno is null then '9010C0130' else ts.storeno end),ts.storeno asc";
		}else{
			sql = "select ifnull(ts.storeno,'无') as storeno,ifnull(ts.name, '无') AS name,ifnull(ts.city_name, '无') AS city_name,dutm.opencount as opencount,dutm.nowcount as nowcount " +
					"from (select regist_storeid," +
					"sum(case when member.associator_expiry_date>now() and  member.opencard_time is not null and member.opencard_time <= '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end ) as opencount," +
					"sum(case when member.opencard_time BETWEEN '"+dynamicDto.getBeginDate()+" 00:00:00' and '"+dynamicDto.getEndDate()+" 23:59:59' then 1 else 0 end) as nowcount " +
					"from df_user_try_member member group by member.regist_storeid ) dutm  " +
					"left join t_store ts  on (dutm.regist_storeid = ts.platformid) where ts.storeno = '"+dynamicDto.getStoreNo()+"' " +
					"ORDER BY (case when ts.storeno is null then '9010C0130' else ts.storeno end),ts.storeno asc";
		}

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();


			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}


	@Override
	public List<Map<String, Object>> queryDistCityListByUserId(long userId) {
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		String sql = "SELECT t.*,d.cityno FROM t_dist_city t LEFT JOIN t_dist_citycode d ON t.citycode=d.citycode WHERE pk_userid='"+userId+"'";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_data = query
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}

	@Override
	public Map<String, Object> queryMemberInvitation(DynamicDto dynamicDto, PageInfo pageInfo) {
		String whereStoreId="";
		String  whereOnline="";
		String whereJoin="";
		String whereCity="";
		String whereStore="";
		if((dynamicDto.getTarget()==0||dynamicDto.getTarget()==1)&&"N".equals(dynamicDto.getStoreNumer())&&!"-10000".equals(dynamicDto.getStoreNo())){
			whereOnline = " UNION select name,phone as mobilephone,employee_no,inviteCode,'' as storename,'' as city_name from t_online_humanresources t where  t.inviteCode is not null and t.inviteCode!=''";
		}
		if(dynamicDto.getStoreNo()!=null){
			whereStoreId = "and t.store_id in ("+dynamicDto.getStoreNo()+")";
		}

		if(dynamicDto.getTarget()==0&&"N".equals(dynamicDto.getStoreNumer())&&dynamicDto.getCityId()!=null){
			whereCity = " inner join   (select cityname from t_dist_citycode where id="+dynamicDto.getCityId()+") tdc on t.citySelect=tdc.cityname";
			whereStore=" inner join  (select cityname from t_dist_citycode where id="+dynamicDto.getCityId()+") tdc on tdc.cityname=t.city_name";
		}
		if(dynamicDto.getTarget()==1&&"N".equals(dynamicDto.getStoreNumer())){
			whereCity = " inner join   (select cityname from t_dist_citycode where id="+dynamicDto.getCityId()+") tdc on t.citySelect=tdc.cityname";
		}

		if(dynamicDto.getTarget()==1||dynamicDto.getTarget()==2){
			whereJoin = " inner join ";
		}else{
			if(dynamicDto.getStoreNumer().equals("Y")){
				whereJoin = " inner join ";
			}else if(dynamicDto.getStoreNumer().equals("N")){
				if(dynamicDto.getCityId()!=null){
					whereJoin = " inner join ";
				}else if(dynamicDto.getCityId()==null){
					whereJoin = " left join ";
				}
			}

		}
		String sql=" select IFNULL(b.inviteCode,'暂无') as inviteCode,ifnull(a.total,0) as total,b.employee_no,CONCAT('*******',SUBSTR(b.mobilephone,8,11)) as mobilephone,b.name,concat(GROUP_CONCAT(b.storename),'') as storename,b.city_name from "
				+" (select inviteCode,COUNT(1) as total from df_user_member where invitecode REGEXP  '^[0-9]{6}$' and  customer_id not in (select customer_id from df_member_whitelist) and DATE_FORMAT(opencard_time,'%Y-%m')='"+dynamicDto.getBeginDate()+"' GROUP BY inviteCode) a"
				+" RIGHT JOIN"
				+" (select t.name,t.phone as mobilephone,t.employee_no,t.inviteCode,ifnull(ts.name,'') as storename,ifnull(t.citySelect,'') as city_name from t_humanresources t LEFT JOIN t_store ts ON t.store_id = ts.store_id "+whereCity+" where  t.inviteCode is not null and t.inviteCode!='' "+whereStoreId

				+" UNION"
				+" select tst.name,tst.phone as mobilephone,tst.employee_no,tst.inviteCode,ifnull(c.storename,'') as storename,ifnull(c.city_name,'') as city_name from t_storekeeper tst  "+whereJoin+" (select tbu.employeeId,t.name as storename,t.city_name from t_store  t INNER JOIN tb_bizbase_user tbu  on t.skid = tbu.id "+whereStore+" where t.skid is not null  "+whereStoreId+") c on tst.employee_no = c.employeeId where  tst.inviteCode is not null and tst.inviteCode!=''"
				+ whereOnline+" ) b"
				+" on a.inviteCode = b.inviteCode   GROUP BY b.inviteCode,b.employee_no having 1=1 ";
		if(dynamicDto.getEmployeeName()!=null&&!"".equals(dynamicDto.getEmployeeName())){
			sql =sql+" and b.name like '%"+dynamicDto.getEmployeeName()+"%'";
		}

		if(dynamicDto.getEmployeeNo()!=null&&!"".equals(dynamicDto.getEmployeeNo())){
			sql =sql+" and b.employee_no like '%"+dynamicDto.getEmployeeNo()+"%'";
		}
        sql =sql+" order by total desc";
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);


		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();


			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("member", list);
		return map_result;
	}

	@Override
	public List<Map<String, Object>> getsixWeekDate() {
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int settime = 0;
		if(i<5){
			settime = 3;
		}else{
			settime = 10;
		}
		String sql = "select CONCAT(DATE_SUB(t.week_time, INTERVAL 35 DAY),'') as week1,CONCAT(DATE_SUB(t.week_time, INTERVAL 28 DAY),'') as week2,CONCAT(DATE_SUB(t.week_time, INTERVAL 21 DAY),'') as week3,"
				+"CONCAT(DATE_SUB(t.week_time, INTERVAL 14 DAY),'') as week4,CONCAT(DATE_SUB(t.week_time, INTERVAL 7 DAY),'') as week5,t.week_time as week6 from (select subdate( "
				+"DATE_FORMAT(NOW(),'%Y-%m-%d'),date_format(NOW(), '%w') -"+settime+") AS week_time ) t";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getsixMonthCustomer() {
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		int i = Calendar.getInstance().get(Calendar.DATE);
		String begindate = "01";
		String enddate = "01";
		if(i>= 1 && i<=7){
			begindate = "01";
			enddate = "07";
		}else if (i>= 8 && i<=14){
			begindate = "08";
			enddate = "14";
		}else if (i>= 15 && i<=21){
			begindate = "15";
			enddate = "21";
		}else if (i>= 22 && i<=28){
			begindate = "22";
			enddate = "28";
		}else if (i>= 29 && i<=31){
			begindate = "29";
			enddate = "31";
		}
		String sql = "select t1.cityname,CONCAT(left(t1.`month`,4),'-',right(t1.`month`,2)) as `month`,round(if(t2.humancounts is null,t1.customer_count,t1.customer_count/t2.humancounts),0) as customer_count,"
				+"round(if(t2.humancounts is null,t1.customer_new_count,t1.customer_new_count/t2.humancounts),0) as customer_new_count,"
				+"round(if(t2.humancounts is null,t3.week_cus_count,t3.week_cus_count/t2.humancounts),0) as week_cus_count from ( "
				+"SELECT cityname,SUM(ds_cus.pay_count) AS  customer_count,SUM(ds_cus.new_count) AS  customer_new_count,ds_cus.order_ym as month FROM  ds_cusum_month_city ds_cus LEFT JOIN t_dist_citycode d ON d.id = ds_cus.city_id "
				+"WHERE ds_cus.order_ym >=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m') GROUP BY ds_cus.order_ym,cityname ) t1 LEFT JOIN ( "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +4 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +4 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(NOW(),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(NOW(),'%Y%m%d') GROUP BY citySelect "
				+") t2 ON (t1.cityname = t2.citySelect and t1.month = t2.month) LEFT JOIN ( "
				+"SELECT SUM(ds_cus.pay_count) AS week_cus_count, d.cityname as cityname, DATE_FORMAT(ds_cus.sign_date,'%Y%m') as `month` FROM ds_cusum_day_city ds_cus left join  t_dist_citycode d on (d.id=ds_cus.city_id) WHERE "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(NOW(),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(NOW(),'%Y-%m-"+enddate+"')) GROUP BY city_id ,month "
				+") t3 ON (t1.cityname = t3.cityname and t1.month = t3.month) ORDER BY CONVERT(t1.cityname USING gbk),t1.`month`";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> getsixMonthAllCustomer() {
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
		int i = Calendar.getInstance().get(Calendar.DATE);
		String begindate = "01";
		String enddate = "01";
		if(i>= 1 && i<=7){
			begindate = "01";
			enddate = "07";
		}else if (i>= 8 && i<=14){
			begindate = "08";
			enddate = "14";
		}else if (i>= 15 && i<=21){
			begindate = "15";
			enddate = "21";
		}else if (i>= 22 && i<=28){
			begindate = "22";
			enddate = "28";
		}else if (i>= 29 && i<=31){
			begindate = "29";
			enddate = "31";
		}
		String sql = "select '全国' as cityname,CONCAT(left(t1.`month`,4),'-',right(t1.`month`,2)) as `month`,round(if(t2.humancounts is null,t1.customer_count,t1.customer_count/t2.humancounts),0) as customer_count,"
				+"round(if(t2.humancounts is null,t1.customer_new_count,t1.customer_new_count/t2.humancounts),0) as customer_new_count,"
				+"round(if(t2.humancounts is null,t3.week_cus_count,t3.week_cus_count/t2.humancounts),0) as week_cus_count from ( "
				+"SELECT cityname,SUM(ds_cus.pay_count) AS  customer_count,SUM(ds_cus.new_count) AS  customer_new_count,ds_cus.order_ym as month FROM  ds_cusum_month_city ds_cus LEFT JOIN t_dist_citycode d ON d.id = ds_cus.city_id "
				+"WHERE ds_cus.order_ym >=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m') GROUP BY ds_cus.order_ym) t1 LEFT JOIN ( "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +5 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +4 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +4 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y%m%d') GROUP BY citySelect UNION "
				+"select citySelect,COUNT(id) AS humancounts,DATE_FORMAT(NOW(),'%Y%m') as `month` from t_humanresources where "
					+"zw = '国安侠' and humanstatus = 1 and DATE_FORMAT(topostdate,'%Y%m%d') < DATE_FORMAT(NOW(),'%Y%m%d')"
				+") t2 ON (t1.cityname = t2.citySelect and t1.month = t2.month) LEFT JOIN ( "
				+"SELECT SUM(ds_cus.pay_count) AS week_cus_count, d.cityname as cityname, DATE_FORMAT(ds_cus.sign_date,'%Y%m') as `month` FROM ds_cusum_day_city ds_cus left join  t_dist_citycode d on (d.id=ds_cus.city_id) WHERE "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +3 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +2 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(DATE_SUB(NOW(), INTERVAL +1 MONTH),'%Y-%m-"+enddate+"')) or "
				+"(ds_cus.sign_date>=DATE_FORMAT(NOW(),'%Y-%m-"+begindate+"') and ds_cus.sign_date <=DATE_FORMAT(NOW(),'%Y-%m-"+enddate+"')) GROUP BY month "
				+") t3 ON (t1.cityname = t3.cityname and t1.month = t3.month)";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_data;
		}catch (Exception e){
            e.printStackTrace();
        }
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> selectAreaDealOfEmployeeByChannel(String beginDate, String endDate, String areaCode) {
		String sql = "SELECT count(1) as total,channel_name,channel_id FROM daqWeb.df_mass_order_total where area_code in ("+areaCode+") group by channel_id,channel_name ";
		List<Map<String,Object>> list = ImpalaUtil.execute(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectAreaDealOfEmployeeByConsum(String beginDate, String endDate, String areaCode) {
		String sql = "select count(1) as total,rang from (select  CASE when total >=20 then 20 when (total<20 and total>=10) then 10  when (total<10 and total>=5) then 5  when (total<5 and total>1) then 4 when total=1 then 1 else 0 end as  rang from  (SELECT count(1) as total,customer_id  FROM daqWeb.df_mass_order_total where area_code in ("+areaCode+") group by customer_id) a ) b group by rang";
        //String  sql="select count(1) as total,3 as rang from daqWeb.df_mass_order_total";
		List<Map<String,Object>> list = ImpalaUtil.execute(sql);
		return list;
	}

	@Override
	public List<Map<String, Object>> selectGMVOfEmployee(Integer year, Integer month, String employeeNo) {
		String sql=" select city_name,storeno,store_name,employee_no,employee_name,pesgmv,pes_sendgmv,pes_areagmv,pes_assigngmv,pes_pergmv"+
				" from ds_pes_gmv_emp_month where year ="+year+" and month = "+month+" and employee_no ='"+employeeNo+"'";



		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> selectCustomerOfEmployee(Integer year, Integer month, String employeeNo) {
		String sql="select ifnull(a.new_cusnum_ten,0) as new_cusnum_ten,ifnull(a.cusnum,0) as cusnum,ifnull(a.cusnum_ten,0)  as cusnum_ten "+
				" from ds_pes_customer_employee_month a "+
				" where a.year="+year+" and a.month="+month+" and a.employeeno='"+employeeNo+"'";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> selectOrderOfEmployee(Integer year, Integer month, String employeeNo) {
		// TODO Auto-generated method stub
		String sql = "select SUM(IFNULL(datanum,0)) AS total  from ds_pes_order_empchannel_month dss WHERE YEAR = "+year+" AND MONTH = "+ month+
					 " AND and dss.employee_no like '%"+employeeNo+"%'"+
					 " AND dss.employee_no IS NOT NULL"+
					 " AND dss.employee_no != ''"+
					 " group by employee_no ";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return lst_result;
	}

	@Override
	public List<Map<String, Object>> queryLastSevenDayCommunityMembersOfStore(String storeId) {
		String sql="select SUM(a.newcount) as newcount ,a.opentime from (select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 7 DAY),'%Y-%m-%d') AS opentime  \n" +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 6 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 5 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 4 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 3 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 2 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" select 0 as newcount,DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 DAY),'%Y-%m-%d') AS opentime " +
				" UNION" +
				" SELECT count(1) AS newcount,DATE_FORMAT(dum.opencard_time,'%Y-%m-%d') AS opentime FROM df_user_member dum where regist_storeid='"+storeId+"' and date(dum.opencard_time)>=DATE_SUB(CURDATE(), INTERVAL 7 DAY) and date(dum.opencard_time)<CURDATE()  GROUP BY DATE_FORMAT(dum.opencard_time,'%Y-%m-%d')) a group by a.opentime";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{

			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			lst_result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			lst_result = lst_result;
		}catch (Exception e){
			e.printStackTrace();
		}
		return lst_result;
	}
	
	@Override
	public Map<String, Object> getTwoTwoOneGMVRangeForWeek(DynamicDto dd,List<Map<String, Object>> cityNO, 
			List<Map<String, Object>> provinceNO) {
		Map<String, Object> map_all = new HashMap<String, Object>();
		String cityStr1 = "";
		String provinceStr1 = "";
		if(cityNO!=null&&cityNO.size()>0){
			String cityNo = String.valueOf(cityNO.get(0).get("cityno"));
			if(cityNo.startsWith("00")){
				cityNo = cityNo.substring(1,cityNo.length());
			}
			cityStr1+=" and tor.store_city_code='"+cityNo+"' ";
		}
		if(provinceNO!=null&&provinceNO.size()>0){
			provinceStr1+=" and tor.store_province_code='"+provinceNO.get(0).get("gb_code")+"'";
		}
/*		String sql = "SELECT IFNULL(FLOOR(sum(dom.trading_price)), 0) AS week_gmv,date_format(dom.sign_time, '%m-%d') AS week_date FROM df_mass_order_monthly dom  " +
				"WHERE dom.store_name NOT LIKE '%测试%' AND dom.sign_time >='"+dd.getBeginDate()+" 00:00:00' AND dom.sign_time<='"+dd.getEndDate()+" 23:59:59' "+provinceStr1+cityStr1+"   GROUP BY DATE(dom.sign_time) ";*/
		String sql = "SELECT IFNULL(FLOOR(SUM(trading_price)), 0) AS week_gmv , date_format(tor.sign_time, '%m-%d') AS week_date FROM df_mass_order_monthly tor " +
				"JOIN df_activity_scope das ON tor.store_id = das.platformid JOIN df_activity_bussiness_scope dab ON tor.channel_id = dab.id AND dab.LEVEL = 2 " +
				"WHERE (tor.store_name NOT LIKE '%测试%' AND tor.sign_time >= '"+dd.getBeginDate()+" 00:00:00' AND tor.sign_time <= '"+dd.getEndDate()+" 23:59:59' "+provinceStr1+cityStr1+") GROUP BY DATE(tor.sign_time)";
		List<Map<String, Object>> lst_data = null;
		try{
	    	 SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	    	 lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	     }catch (Exception e){
	         e.printStackTrace();
	     }
		map_all.put("lst_data", lst_data);
		return map_all;
	}

	@Override
	public Map<String, Object> getStoreGmv_221(DynamicDto dynamicDto, PageInfo pageInfo) {
		List<?> list=null;
		Map<String, Object> map_result = new HashMap<String, Object>();

		String sql=" select city_name,storeno,store_name, pesgmv "+
				" from ds_pes_gmv_activity_store_month where year="+dynamicDto.getYear()+" and month="+dynamicDto.getMonth()+" and storeno in ("+dynamicDto.getStoreNo()+")";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		if(pageInfo!=null){
			String sql_count = "SELECT count(1) from ("+sql+") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();


			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}

	@Override
	public Map<String, Object> getEmployeeGmv_221(DynamicDto dynamicDto, PageInfo pageInfo) {
		String sql=" select city_name,storeno,store_name,employee_no,employee_name,pesgmv"+
				" from ds_pes_gmv_activity_emp_month where year ="+dynamicDto.getYear()+" and month = "+dynamicDto.getMonth()+" and storeno in ("+dynamicDto.getStoreNo()+")";
		if(dynamicDto.getEmployeeNo()!=null&&!"".equals(dynamicDto.getEmployeeNo())){
			sql=sql+" and employee_no like '%"+dynamicDto.getEmployeeNo()+"%'";
		}

		if(dynamicDto.getEmployeeName()!=null&&!"".equals(dynamicDto.getEmployeeName())){
			sql=sql+" and employee_name like '%"+dynamicDto.getEmployeeName()+"%'";
		}
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<?> list=null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if(pageInfo!=null){
			String sql_count = "SELECT count(1) as total from ("+sql+") c";
			Query query_count = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql_count);
			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(pageInfo.getRecordsPerPage()*(pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;

			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		}else{
			list =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}
		map_result.put("gmv", list);
		return map_result;
	}

	@Override
	public Map<String, Object> getCustomerMember(DynamicDto dynamicDto, PageInfo pageInfo) {
		List<?> list = null;
		Map<String, Object> map_result = new HashMap<String, Object>();

		String sql = "select t1.opencount,ifnull(t2.nowcount,0) as nowcount from (select count(member.customer_id) as opencount from df_user_member member where  member.associator_expiry_date>now() and member.status = 1 and  member.opencard_time is null" +
				" and member.create_time <= '"+dynamicDto.getEndDate()+" 23:59:59') t1,(" +
				"select count(member.customer_id) as nowcount from df_user_member member where member.opencard_time is null and member.create_time BETWEEN '" + dynamicDto.getBeginDate() + " 00:00:00' and '" + dynamicDto.getEndDate() + " 23:59:59') t2";

		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		if (pageInfo != null) {
			String sql_count = "SELECT count(1) from (" + sql + ") c ";
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

			pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();


			Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
			map_result.put("pageinfo", pageInfo);
			map_result.put("total_pages", total_pages);
		} else {
			list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		}

		map_result.put("member", list);
		return map_result;
	}

	@Override
	public Map<String, Object> selectEStoreRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo) {
		String  sql ="select SUM(IFNULL(gmv_price,0)) as amount,eshop_name as name,eshop_id from df_mass_order_monthly a where store_id='"+dynamicDto.getStoreIds()+"'  and sign_time>='"+dynamicDto.getBeginDate()+" 00:00:00' and  sign_time<'"+dynamicDto.getEndDate()+"' GROUP BY eshop_id order by amount desc ";


		Map<String,Object> map_result = new HashMap<String,Object>();
		List<?> list=null;
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);

		try {
			String sql_count = "SELECT COUNT(1) as total FROM ("+sql+") T";
			if(pageInfo!=null){
				Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

				pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));
				list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
						pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1)).setMaxResults(pageInfo.getRecordsPerPage()).list();

				Integer total_pages = (pageInfo.getTotalRecords()-1)/pageInfo.getRecordsPerPage()+1;
				map_result.put("pageinfo",pageInfo);
				map_result.put("totalPage", total_pages);
				map_result.put("totalRecords", pageInfo.getTotalRecords());
			}else{

				list= query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}

			map_result.put("gmv", list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map_result;
	}

	@Override
	public Map<String, Object> getUserBehaviorByLog(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo) {
		String appendSql = "";
		String  sql = "";
		String log_str = "";
		String order_str = "";
		String behavior_name_str = " and lf.behavior_name in( '专场/首页','微信首页') ";
		if(dynamicDto.getOrder_way().equals("wx")){
			log_str = " and lf.log_type='wx' ";
			order_str = " and tor.order_source = 'wechat' ";
			behavior_name_str = " and lf.behavior_name ='微信首页' ";
		}else if(dynamicDto.getOrder_way().equals("app")){
			log_str = " and lf.log_type !='wx' ";
			order_str = " and tor.order_source != 'wechat' ";
			behavior_name_str = " and lf.behavior_name = '专场/首页' ";
		}
		if(dynamicDto.getSearchstr().equals("user_active_store")){
			if(dynamicDto.getStoreId()!=null && !"".equals(dynamicDto.getStoreId())){
				appendSql = " where T_2.store_id = '"+dynamicDto.getStoreNo()+"' ";
			}else if(cityNo!=null && !"".equals(cityNo)){
				appendSql = " where lpad(T_2.city_code,4,'0') = '"+cityNo+"' ";
			}
			sql ="select tsa.name as city_name,ts.name as store_name,isnull( T_2.visit_num , 0) as visit_num,isnull( T_3.add_num , 0)  as add_num,isnull( T_4.order_num, 0) as order_num," +
					"isnull( T_5.sign_num, 0) as sign_num,isnull( T_6.pvNum, 0) as pvNum  " +
					"from (select fnv_hash(concat(T_1.city_code ,T_1.store_id)) as id,T_1.city_code , T_1.store_id , count(1) as visit_num  from ( " +
					"select max(lf.city_code) as city_code,lf.store_id from datacube_kudu.log_final lf where 1=1 and lf.simple_date >= '"+dynamicDto.getBeginDate()+"' and lf.simple_date < '"+dynamicDto.getEndDate()+"' " +log_str+
					"and lf.customer_id is not null and lf.city_code is not null and lf.store_id is not null  and lf.customer_id not in (" +
					"'fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") group by lf.customer_id,lf.store_id ) T_1 group by T_1.city_code,T_1.store_id " +
					") T_2 left join ( " +
					"select fnv_hash(concat(T_1.city_code ,T_1.store_id)) as id,T_1.city_code,T_1.store_id,count(1) as add_num from (select max(lf.city_code) as city_code,lf.store_id " +
					"from datacube_kudu.log_final lf  where 1=1 and lf.simple_date >= '"+dynamicDto.getBeginDate()+"' and lf.simple_date < '"+dynamicDto.getEndDate()+"'"+log_str+" and lf.customer_id is not null and lf.city_code is not null " +
					"and lf.store_id is not null and lf.customer_id not in ( " +
					"'fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001') " +
					"and lf.behavior_name in (select  behavior_name from datacube_kudu.d_action_level where level = 9 " +
					") group by lf.customer_id,lf.store_id) T_1 group by T_1.city_code ,T_1.store_id) " +
					"T_3 on  T_2.id= T_3.id left join " +
					"(select fnv_hash(concat(T_1.city_code ,T_1.store_id)) as id,T_1.city_code,T_1.store_id,count(1) as order_num from (select max(ts.city_code) as city_code,tor.store_id from " +
					"gemini.t_order tor left join gemini.t_store ts on tor.store_id = ts.id where 1=1 and tor.create_time > '"+dynamicDto.getBeginDate()+"' and tor.create_time < '"+dynamicDto.getEndDate()+"'"+order_str+" and tor.store_id is not null " +
					" and tor.customer_id not in ( 'fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					")group by tor.customer_id , tor.store_id) T_1 group by T_1.city_code , T_1.store_id) " +
					"T_4 on T_3.id = T_4.id left join " +
					"(select fnv_hash(concat(T_1.city_code ,T_1.store_id)) as id,T_1.city_code,T_1.store_id,count(1) as sign_num from (select max(ts.city_code) as city_code,tor.store_id " +
					"from  gemini.t_order tor left join gemini.t_store ts on tor.store_id = ts.id   where 1=1  and tor.create_time > '"+dynamicDto.getBeginDate()+"' and tor.create_time < '"+dynamicDto.getEndDate()+"' " +order_str+
					"and tor.store_id is not null  and tor.sign_time is not null and tor.customer_id not in ( " +
					"'fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") group by tor.customer_id , tor.store_id ) T_1 group by T_1.city_code , T_1.store_id " +
					") T_5 on T_4.id = T_5.id left join " +
					"(select lf.store_id,count(distinct lf.action_id) as pvNum from datacube_kudu.log_final lf " +
					"inner join gemini.t_store ts on lf.store_id = ts.id and ts.name not like '%测试%' where 1=1 " +
					"and lf.simple_date >= '"+dynamicDto.getBeginDate()+"' and lf.simple_date < '"+dynamicDto.getEndDate()+"'"+log_str+" and lf.customer_id is not null and lf.city_code is not null " +
					"and lf.store_id is not null and lf.customer_id not in ('fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") "+behavior_name_str+" group by lf.store_id " +
					")T_6 on  T_2.store_id = T_6.store_id " +
					"left join  gemini.t_sys_area tsa on T_2.city_code =  tsa.code " +
					"inner join  gemini.t_store ts on T_2.store_id = ts.id  and ts.name not like '%测试%' " +appendSql+
					" order by  T_2.city_code,T_2.store_id,T_2.visit_num";
		}else if(dynamicDto.getSearchstr().equals("user_active_city")){
			if(cityNo!=null && !"".equals(cityNo)){
				appendSql = " where lpad(T_2.city_code,4,'0') = '"+cityNo+"' ";
			}
			sql = "select tsa.name as city_name,isnull( T_2.visit_num , 0) as visit_num,isnull( T_3.add_num , 0)  as add_num,isnull( T_4.order_num, 0) as order_num,isnull( T_5.sign_num, 0) as sign_num,isnull( T_6.pvNum, 0) as pvnum " +
					"from (select T_1.city_code,count(1) as visit_num from (select max(lf.city_code) as city_code from datacube_kudu.log_final lf inner join gemini.t_store ts on lf.store_id = ts.id and " +
					"ts.name not like '%测试%' where 1=1 and lf.simple_date >= '"+dynamicDto.getBeginDate()+"' and lf.simple_date < '"+dynamicDto.getEndDate()+"'"+log_str+" and lf.customer_id is not null and lf.city_code is not null " +
					"and lf.store_id is not null and lf.customer_id not in (" +
					"'fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") group by lf.customer_id ) T_1 group by T_1.city_code ) T_2 left join " +
					"(select T_1.city_code,count(1) as add_num from (select max(lf.city_code) as city_code from datacube_kudu.log_final lf inner join gemini.t_store ts on lf.store_id = ts.id and " +
					"ts.name not like '%测试%' where 1=1 and lf.simple_date >= '"+dynamicDto.getBeginDate()+"'  and lf.simple_date < '"+dynamicDto.getEndDate()+"'"+log_str+" and lf.customer_id is not null and lf.city_code is not null " +
					"and lf.store_id is not null and lf.customer_id not in ('fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") and lf.behavior_name in ( select  behavior_name from datacube_kudu.d_action_level where level = 9 " +
					") group by lf.customer_id ) T_1 group by T_1.city_code ) " +
					"T_3 on  T_2.city_code= T_3.city_code left join " +
					"(select T_1.city_code , count(1) as order_num  from  (select max(ts.city_code) as city_code from " +
					"gemini.t_order tor inner join gemini.t_store ts on tor.store_id = ts.id  and ts.name not like '%测试%'where 1=1 and tor.create_time > '"+dynamicDto.getBeginDate()+"' and tor.create_time < '"+dynamicDto.getEndDate()+"' " +order_str+
					"and tor.store_id is not null and tor.customer_id not in ('fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") group by tor.customer_id ) T_1 group by T_1.city_code " +
					")T_4 on T_3.city_code = T_4.city_code left join " +
					"(select T_1.city_code ,count(1) as sign_num from (select max(ts.city_code) as city_code from gemini.t_order tor inner join gemini.t_store ts on tor.store_id = ts.id  and " +
					"ts.name not like '%测试%' where 1=1 and tor.create_time > '"+dynamicDto.getBeginDate()+"' and tor.create_time < '"+dynamicDto.getEndDate()+"'"+order_str+" and tor.store_id is not null and tor.sign_time is not null " +
					"and tor.customer_id not in ('fakecustomerformicromarket000002','fakecustomerformicromarket000001','fakecustomerforexpress0000000001' " +
					") group by tor.customer_id ) T_1 group by T_1.city_code " +
					") T_5 on T_4.city_code = T_5.city_code left join ( " +
					"select lf.city_code,count(distinct lf.action_id) as pvNum from datacube_kudu.log_final lf " +
					"inner join gemini.t_store ts on lf.store_id = ts.id and ts.name not like '%测试%' where 1=1 and lf.simple_date >= '"+dynamicDto.getBeginDate()+"' " +
					"and lf.simple_date < '"+dynamicDto.getEndDate()+"'"+log_str+" and lf.customer_id is not null and lf.city_code is not null and lf.store_id is not null " +
					"and lf.customer_id not in ( 'fakecustomerformicromarket000002', 'fakecustomerformicromarket000001', 'fakecustomerforexpress0000000001' " +
					") "+behavior_name_str+" group by lf.city_code " +
					") T_6 on T_2.city_code = T_6.city_code " +
					"left join  gemini.t_sys_area tsa on T_2.city_code =  tsa.code "  +appendSql+
					"order by   T_2.city_code,T_2.visit_num";
		}




		Map<String,Object> map_result = ImpalaUtil.executeByPage(sql,pageInfo);
		return map_result;
	}
    @Override
    public Map<String, Object> employeeOfMaoli(DynamicDto dynamicDto, PageInfo pageInfo) {

        String sql="";
        if("2018-10".equals(dynamicDto.getBeginDate())){
            sql="select c2.city_name as city_name,c2.storename as store_name,c2.storeno as storeno,c2.name as employee_name,c2.employeeno as employee_no,c2.income as income,round(c2.sumprice,2) as sumprice,ifnull(dbaosun.count_money_avg,0) as  baosun,ifnull(dpankui.count_money_avg,0) as pankui," +
                    "round(c2.income-ifnull(c2.sumprice,0) -ifnull(dbaosun.count_money_avg,0),2) as maoli,c2.zw as zw,c2.store_id as store_id  " +
                    "from (select " +
                    "c1.income, c1.rebate, c1.sumprice, c1. name, c1.employeeno, c1.zw, c1.storename, c1.store_id, tss.storeno, tss.city_name " +
                    "from " +
                    " (select " +
                    "b1.income, b1.rebate, b1.sumprice, b1.employeeno, th. name, th.zw, th.storename, th.store_id " +
                    " from " +
                    "( select round( a1.mon_profit - ifnull(a2.mon_profit,0), 2 ) income, round(a1.rebate - ifnull(a2.rebate,0), 2) rebate, round(a1.sumprice - ifnull(a2.sumprice,0), 2) sumprice, a1.info_employee_a_no employeeno from ( select ifnull(sum(dmot.order_profit), 0) mon_profit, ifnull( sum(dmot.apportion_rebate), 0 ) rebate, ifnull(sum(CASE when  dmot.order_tag4 is null then dmot.platform_price else 0 end), 0) sumprice, dmot.info_employee_a_no from daqweb.df_mass_order_total dmot where dmot.info_employee_a_no is not null and dmot.info_employee_a_no <> '' and strleft (dmot.sign_time, 7) = '"+dynamicDto.getBeginDate()+"' group by dmot.info_employee_a_no ) a1 left join ( select ifnull(sum(dmot2.order_profit), 0) mon_profit, ifnull( sum(dmot2.apportion_rebate), 0 ) rebate, ifnull(sum(CASE when  dmot2.order_tag4 is null then dmot2.platform_price else 0 end), 0) sumprice, dmot2.info_employee_a_no from daqweb.df_mass_order_total dmot2 where dmot2.info_employee_a_no is not null and dmot2.info_employee_a_no <> '' and strleft (dmot2.return_time, 7) = '"+dynamicDto.getBeginDate()+"' group by dmot2.info_employee_a_no ) a2 on a1.info_employee_a_no = a2.info_employee_a_no ) b1 " +
                    " left join t_humanresources th on b1.employeeno = th.employee_no) c1 " +
                    "left join daqweb.t_store tss on c1.store_id = tss.store_id) c2  " +
                    "left join (select * from daqweb.df_pankui_baosun_info  where count_type = '0' and count_month = '"+ dynamicDto.getBeginDate()+"' and create_date=(select max(mm.create_date) from daqweb.df_pankui_baosun_info mm where mm.count_month = '"+ dynamicDto.getBeginDate()+"' and  mm.count_type = '0')) dbaosun on (c2.storeno=dbaosun.store_code ) " +
                    "left join (select * from daqweb.df_pankui_baosun_info  where count_type = '1' and count_month = '"+ dynamicDto.getBeginDate()+"' and create_date=(select max(mm.create_date) from daqweb.df_pankui_baosun_info mm where mm.count_month = '"+ dynamicDto.getBeginDate()+"' and  mm.count_type = '1')) dpankui on (c2.storeno=dpankui.store_code ) where 1=1   ";
        }else{
            sql = "select c2.city_name as city_name, c2.storename as store_name, c2.storeno as storeno, c2. name as employee_name, c2.employeeno as employee_no, round((c2.income+ifnull(c5.income, 0)+ifnull(c6.wcd_profit, 0)),2) as allcome,c2.income as income, round(ifnull(c5.income, 0), 2) as outcome," +
                    " round(ifnull(c6.wcd_profit, 0), 2) as wcd_profit, round(ifnull(c2.sumprice,0)+ifnull(c5.sumprice, 0), 2) as sumprice,round( ifnull(dbaosun.count_money_avg, 0), 2 ) as baosun," +
                    " round( ifnull(dpankui.count_money_avg, 0), 2 ) as pankui, round( c2.income + ifnull(c5.income, 0) + ifnull(c6.wcd_profit, 0) - ifnull(c5.sumprice, 0) - ifnull(c2.sumprice,0) - ifnull(dbaosun.count_money_avg, 0), 2 ) as maoli," +
                    " round( (c2.income + ifnull(c5.income, 0) + ifnull(c6.wcd_profit, 0) + c2.gayysubsidy - ifnull(c5.sumprice, 0) - ifnull(c2.sumprice,0) - ifnull(dbaosun.count_money_avg, 0))*0.8, 2 ) as endmaoli," +
                    " round(ifnull(c2.saleprofit,0)*0.8,2) as saleprofit,round(ifnull(c2.gayysubsidy,0),2) as gayysubsidy,c2.zw as zw, c2.store_id as store_id from ( select c1.income, c1.rebate, c1.sumprice, c1. name, c1.employeeno, c1.zw,c1.saleprofit,c1.gayysubsidy, c1.storename, c1.store_id, tss.storeno, tss.city_name" +
                    " from ( select b1.income, b1.rebate,b1.saleprofit,b1.gayysubsidy, b1.sumprice, b1.employeeno, th. name, th.zw, th.storename, th.store_id from ( select round( a1.mon_profit - ifnull(a2.mon_profit, 0), 2 ) income," +
                    " round( a1.rebate - ifnull(a2.rebate, 0), 2 ) rebate, round( a1.sumprice - ifnull(a2.sumprice, 0), 2 ) sumprice,a1.saleprofit,a1.gayysubsidy, a1.info_employee_a_no employeeno" +
                    " from ( select ifnull(sum(dmot.order_profit), 0) mon_profit, ifnull( sum(dmot.apportion_rebate), 0 ) rebate,ifnull(sum(case when dmot.sale_profit > 0 then dmot.sale_profit else 0 end),0) saleprofit,sum(ifnull(dmot.gayy_subsidy,0)) as gayysubsidy, ifnull( sum( CASE when dmot.order_tag4 is null then dmot.platform_price else 0 end ), 0 ) sumprice," +
                    " dmot.info_employee_a_no from daqweb.df_mass_order_total dmot where dmot.info_employee_a_no is not null and dmot.info_employee_a_no <> '' and strleft (dmot.sign_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by dmot.info_employee_a_no ) a1" +
                    " left join ( select ifnull(sum(dmot2.order_profit), 0) mon_profit, ifnull( sum(dmot2.apportion_rebate), 0 ) rebate, ifnull( sum( CASE when dmot2.order_tag4 is null then dmot2.platform_price else 0 end ), 0 ) sumprice," +
                    " dmot2.info_employee_a_no from daqweb.df_mass_order_total dmot2 where dmot2.info_employee_a_no is not null and dmot2.info_employee_a_no <> '' and strleft (dmot2.return_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by dmot2.info_employee_a_no ) a2" +
                    " on a1.info_employee_a_no = a2.info_employee_a_no ) b1" +
                    " left join t_humanresources th on b1.employeeno = th.employee_no ) c1" +
                    " left join daqweb.t_store tss on c1.store_id = tss.store_id ) c2 " +
                    "left join (select * from daqweb.df_pankui_baosun_info  where count_type = '0' and count_month = '"+ dynamicDto.getBeginDate()+"' and create_date=(select max(mm.create_date) from daqweb.df_pankui_baosun_info mm where mm.count_month = '"+ dynamicDto.getBeginDate()+"' and  mm.count_type = '0')) dbaosun on (c2.storeno=dbaosun.store_code ) " +
                    "left join (select * from daqweb.df_pankui_baosun_info  where count_type = '1' and count_month = '"+ dynamicDto.getBeginDate()+"' and create_date=(select max(mm.create_date) from daqweb.df_pankui_baosun_info mm where mm.count_month = '"+ dynamicDto.getBeginDate()+"' and  mm.count_type = '1')) dpankui on (c2.storeno=dpankui.store_code ) " +
                    " left join ( select round( d1.mon_profit - ifnull(d2.mon_profit, 0), 2 ) income, round( d1.rebate - ifnull(d2.rebate, 0), 2 ) rebate, round( d1.sumprice - ifnull(d2.sumprice, 0), 2 ) sumprice," +
                    " d1.employee_no employeeno from ( select ifnull(sum(dmot3.order_profit), 0) mon_profit, ifnull( sum(dmot3.apportion_rebate), 0 ) rebate," +
                    " ifnull( sum( CASE when dmot3.order_tag4 is null then dmot3.platform_price else 0 end ), 0 ) sumprice, dopm.employee_no as employee_no" +
                    " from df_order_pubseas_monthly dopm join df_mass_order_total dmot3 on (dopm.df_order_id = dmot3.id) where dopm.employee_no is not null" +
                    " AND dmot3.eshop_name NOT LIKE '%测试%' AND dmot3.eshop_white != 'QA' AND dmot3.store_white != 'QA' and dmot3.pubseas_label = '1'" +
                    " AND strleft (dmot3.sign_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by dopm.employee_no ) d1 left join ( select ifnull(sum(dmot4.order_profit), 0) mon_profit," +
                    " ifnull( sum(dmot4.apportion_rebate), 0 ) rebate, ifnull( sum( CASE when dmot4.order_tag4 is null then dmot4.platform_price else 0 end ), 0 ) sumprice, " +
                    " dopm1.employee_no as employee_no from df_order_pubseas_monthly dopm1 join df_mass_order_total dmot4 on (dopm1.df_order_id = dmot4.id) where dopm1.employee_no is not null" +
                    " AND dmot4.eshop_name NOT LIKE '%测试%' AND dmot4.eshop_white != 'QA' AND dmot4.store_white != 'QA' and dmot4.pubseas_label = '1'" +
                    " AND strleft (dmot4.return_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by dopm1.employee_no ) d2 on d1.employee_no = d2.employee_no ) c5 on ( c2.employeeno = c5.employeeno )" +
                    " left join ( select ifnull((e5.pergmv - ifnull(e6.pergmv,0)), 0) wcd_profit, e5.storeno from ( select e1.storeno, e2.persum, e1.pernum, ifnull((e2.persum / e1.pernum), 0) as pergmv from ( ";

            if(dynamicDto.getBeginDate().equals(DateUtils.getCurrMonthDate())){
                sql += "select tts.storeno, dtop.pernum from ( select store_id storeno, count(1) as pernum from daqweb.t_humanresources where zw = '国安侠' and humanstatus = 1 group by store_id ) dtop left join daqweb.t_store tts on dtop.storeno = tts.store_id" +
                        ") e1, ( select tor1.store_code store_code, sum(tor1.order_profit) persum from df_mass_order_total tor1 where tor1.eshop_name NOT LIKE '%测试%'" +
                        " AND tor1.eshop_white != 'QA' AND tor1.store_white != 'QA' and tor1.store_status = 0 AND tor1.customer_id like 'fakecustomer%' AND strleft (tor1.sign_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by tor1.store_code ) e2" +
                        " where e1.storeno = e2.store_code ) e5 left join ( select e3.storeno, ifnull((e4.persum / e3.pernum), 0) as pergmv from ( " +
                        "select tts1.storeno, dtop1.pernum from ( select store_id storeno, count(1) as pernum from daqweb.t_humanresources where zw = '国安侠' and humanstatus = 1 group by store_id ) dtop1 left join daqweb.t_store tts1 on dtop1.storeno = tts1.store_id" +
                        ") e3, ( select tor2.store_code, sum(tor2.order_profit) persum from df_mass_order_total tor2 where tor2.eshop_name NOT LIKE '%测试%'" +
                        " AND tor2.eshop_white != 'QA' AND tor2.store_white != 'QA' and tor2.store_status = 0 AND tor2.customer_id like 'fakecustomer%' AND strleft (tor2.return_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by tor2.store_code ) e4 where e3.storeno = e4.store_code ) e6" +
                        " on e5.storeno = e6.storeno ) c6 on (c2.storeno = c6.storeno) where 1 = 1";
            }else{
                sql += "select dtop.storeno storeno, count(1) as pernum from ds_topdata dtop where dtop.zw = '国安侠' and dtop.humanstatus = 1 and dtop.yearmonth = '"+ dynamicDto.getBeginDate()+"' group by dtop.storeno " +
                        ") e1, ( select tor1.store_code store_code, sum(tor1.order_profit) persum from df_mass_order_total tor1 where tor1.eshop_name NOT LIKE '%测试%'" +
                        " AND tor1.eshop_white != 'QA' AND tor1.store_white != 'QA' and tor1.store_status = 0 AND tor1.customer_id like 'fakecustomer%' AND strleft (tor1.sign_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by tor1.store_code ) e2" +
                        " where e1.storeno = e2.store_code ) e5 left join ( select e3.storeno, ifnull((e4.persum / e3.pernum), 0) as pergmv from ( " +
                        "select dtop1.storeno, count(1) as pernum from ds_topdata dtop1 where dtop1.zw = '国安侠' and dtop1.humanstatus = 1 and dtop1.yearmonth = '"+ dynamicDto.getBeginDate()+"' group by dtop1.storeno " +
                        ") e3, ( select tor2.store_code, sum(tor2.order_profit) persum from df_mass_order_total tor2 where tor2.eshop_name NOT LIKE '%测试%'" +
                        " AND tor2.eshop_white != 'QA' AND tor2.store_white != 'QA' and tor2.store_status = 0 AND tor2.customer_id like 'fakecustomer%' AND strleft (tor2.return_time, 7) = '"+ dynamicDto.getBeginDate()+"' group by tor2.store_code ) e4 where e3.storeno = e4.store_code ) e6" +
                        " on e5.storeno = e6.storeno ) c6 on (c2.storeno = c6.storeno) where 1 = 1";;
            }

        }

        if(dynamicDto.getEmployeeNo()!=null&&!"".equals(dynamicDto.getEmployeeNo())){
            sql=sql+" and c2.employeeno like '%"+dynamicDto.getEmployeeNo()+"%'";
        }

        if(dynamicDto.getEmployeeName()!=null&&!"".equals(dynamicDto.getEmployeeName())){
            sql=sql+" and c2.name like '%"+dynamicDto.getEmployeeName()+"%'";
        }
        if(dynamicDto.getStoreName()!=null&&!"".equals(dynamicDto.getStoreName())){
            sql=sql+" and c2.storename like '%"+dynamicDto.getStoreName()+"%'";
        }
        if(dynamicDto.getStoreNo()!=null&&!"".equals(dynamicDto.getStoreNo())){
            sql=sql+" and c2.storeno ="+dynamicDto.getStoreNo()+"";
        }
        if(dynamicDto.getCityName()!=null&&!"".equals(dynamicDto.getCityName())){
            sql=sql+" and c2.city_name like '%"+dynamicDto.getCityName()+"%'";
        }
        sql = sql+" order by c2.storeno";
        String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";
        Map<String, Object> map_result = new HashMap<String, Object>();
        if(pageInfo!=null){
            int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
            int recordsPerPage = pageInfo.getRecordsPerPage();
            sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;
            List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

            String total = "0";
            List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
            if(resultCount !=null && resultCount.size()>0 ){
                total = String.valueOf(resultCount.get(0).get("total"));
            }

            pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

            Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
            map_result.put("pageinfo", pageInfo);
            map_result.put("maoli", list);
            map_result.put("total_pages", total_pages);

        }else{
            List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
            map_result.put("maoli", list);
        }
        return map_result;
    }

	@Override

	public Map<String, Object> queryStoreCustmerCount(DynamicDto dd,List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO, PageInfo pageInfo) {
		String province_id = dd.getProvinceId()==null?"":String.valueOf(dd.getProvinceId());
		String city_id = dd.getCityId()==null?"":String.valueOf(dd.getCityId());
		String provinceStr = "";
		String cityStr = "";
		String zx = "no";
	//		if("1".equals(province_id)||"2".equals(province_id)||"3".equals(province_id)){
	//		zx = "yes";
	//	}
		if(provinceNO!=null&&provinceNO.size()>0){
			if(province_id!=null&&province_id!=""&&"no".equals(zx)){
				provinceStr+=" AND ts.province_id='"+province_id+"' ";
			}
		}
		if(cityNO!=null&&cityNO.size()>0){
			if(city_id!=null&&city_id!=""){
				cityStr+=" and d.id='"+city_id+"' ";
			}else if("yes".equals(zx)){
				cityStr+=" and d.id='"+province_id+"' ";
			}
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql = "SELECT count(tr.customer_id) AS customer_count,ts.`name` AS store_name,case WHEN LEFT(d.cityno,2)='00' THEN right(d.cityno,3) ELSE d.cityno END AS city_code,concat(tr.store_id,'') as store_id FROM " +
				"df_customer_order_month_trade_new tr LEFT JOIN t_store ts ON tr.store_id = ts.platformid left join t_dist_citycode d on d.cityname=ts.city_name  WHERE tr.order_ym = '"+
				dd.getYear()+(dd.getMonth()<10?("0"+dd.getMonth()):dd.getMonth())+"' "+provinceStr+cityStr+" and ts.`name` is not null GROUP BY ts.`name` ORDER BY customer_count DESC ";
		String sql_count = "SELECT count(tdd.customer_count) as customer_cnt from ( "+sql+") tdd ";
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<?> list = null;
		try {
			Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);
			List<?> total = query_count
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if(pageInfo!=null){
				if(total!=null&&total.size()>0){
					maps = (Map<String, Object>) total.get(0);
					pageInfo.setTotalRecords(Integer.valueOf(maps.get("customer_cnt").toString()));
				}else{
					pageInfo.setTotalRecords(Integer.valueOf(0));
				}
			}
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			
			if(pageInfo==null){
				list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}else{
				list=query
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.setFirstResult(
						pageInfo.getRecordsPerPage()
								* (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
			}
			if(pageInfo!=null){
				Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
				map_result.put("pageinfo", pageInfo);
				map_result.put("total_pages", total_pages);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("gmv", list);
        return map_result;
    }

	public Map<String, Object> queryDeptGMVByImpala(DynamicDto dynamicDto, PageInfo pageInfo) {


		String groupStr = " group by bussiness_group_id";
		String whereStr= " where deptgmv.deptid is not null and deptgmv.deptname not like '%运营管理中心%' and deptgmv.deptname not like '%测试%' and deptgmv.deptname is not null";
		String onStr = " on deptgmv.deptid = deptthgmv.deptid";
		String orderStr=" order by deptgmv.deptid";
		String selectStr1 = ",min(department_name) as deptname";
		String selectStr2 = "";
		String storeWhere = "";
		if(dynamicDto.getDept()!=null&&!"".equals(dynamicDto.getDept())){
			whereStr = whereStr+" and  deptgmv.deptid='"+dynamicDto.getDept()+"'";
		}

		if(dynamicDto.getSearchstr().contains("dept_city_active")) {
			groupStr = groupStr + " ,store_city_code";
			if(dynamicDto.getCityName()!=null&&!"".equals(dynamicDto.getCityName())){
				whereStr = whereStr+" and deptgmv.store_city_name like '%"+dynamicDto.getCityName()+"%'";
			}

			onStr = onStr+" and deptgmv.store_city_code = deptthgmv.store_city_code ";
			selectStr1=selectStr1+" ,store_city_code,min(store_city_name) as store_city_name";
			selectStr2 = selectStr2+",deptgmv.store_city_name";
            orderStr = orderStr+",deptgmv.store_city_name";
		}

		if(dynamicDto.getSearchstr().contains("dept_store_active")){
			groupStr = groupStr + ",real_store_id ";

//			if(dynamicDto.getStoreNo()!=null&&!"".equals(dynamicDto.getStoreNo())){
//				whereStr = whereStr+" and deptgmv.store_id='"+dynamicDto.getStoreNo()+"'";
//			}

			if(dynamicDto.getStoreNo()!=null&&!"".equals(dynamicDto.getStoreNo())){
				storeWhere = "tor.real_store_id='"+dynamicDto.getStoreNo()+"' and ";
			}

			onStr = onStr+" and deptgmv.store_id = deptthgmv.store_id LEFT JOIN t_store ts on deptgmv.store_id = ts.id ";
			selectStr1=selectStr1+" ,real_store_id AS store_id";
			selectStr2 = selectStr2+",deptgmv.store_id,ts.name as store_name,ts.storeno as store_code";

		}

		if(dynamicDto.getSearchstr().contains("dept_channel_active")){
			groupStr = groupStr + ",channel_id";

			if(dynamicDto.getChannel()!=null&&!"".equals(dynamicDto.getChannel())){
				whereStr = whereStr+" and deptgmv.channel_name like '%"+dynamicDto.getChannel()+"%'";
			}

			onStr = onStr+"  and deptgmv.channel_id = deptthgmv.channel_id ";
			selectStr1=selectStr1+" ,channel_id,min(channel_name) as channel_name";
			selectStr2 = selectStr2+",deptgmv.channel_name";
            orderStr = orderStr+",deptgmv.channel_name";
		}

		whereStr = whereStr+orderStr;

		String sql = "select " +
				" deptgmv.deptid," +
				" deptgmv.deptname," +
				" (IFNULL(deptgmv.gmv_price,0) - IFNULL(deptthgmv.thgmv_price,0)) as pesgmv," +
				" IFNULL(deptgmv.gmv_price,0) as deptgmv," +
				" IFNULL(deptthgmv.thgmv_price,0) as thgmv" +
				selectStr2+
				" from (" +
				" select " +
				" tor.bussiness_group_id as deptid," +
				" sum(tor.gmv_price) as gmv_price " +
					selectStr1+
				" from df_mass_order_monthly tor " +
				" where "+storeWhere+" tor.sign_time >='"+dynamicDto.getBeginDate()+"' "+
				" and tor.sign_time < from_unixtime(unix_timestamp(days_add(from_unixtime(unix_timestamp('"+dynamicDto.getEndDate()+"'), 'yyyy-MM-dd') , 1)), 'yyyy-MM-dd')" +
				" and (tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA')" +
				" and (tor.store_white !='QA' and tor.store_status = 0 )" +
				" and (tor.loan_label ='0' or tor.loan_label ='3' or tor.loan_label = '5')" +
				" and (tor.abnormal_label ='0')" +
				    groupStr+
				" ) deptgmv left join ( " +
				" select" +
				" tor.bussiness_group_id as deptid," +
				" sum(tor.returned_amount) as thgmv_price" +
					selectStr1+
				" from df_mass_order_total tor" +
				" where "+storeWhere+" tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA'" +
				" AND tor.store_white !='QA' and tor.store_status = 0" +
				" AND (tor.loan_label ='0' or tor.loan_label ='3' or tor.loan_label = '5')" +
				" AND (tor.abnormal_label ='0')" +
				" AND tor.return_time >= '"+dynamicDto.getBeginDate()+"' "+
				" AND tor.return_time < from_unixtime(unix_timestamp(days_add(from_unixtime(unix_timestamp('"+dynamicDto.getEndDate()+"'), 'yyyy-MM-dd') , 1)), 'yyyy-MM-dd')" +
					groupStr+
				") deptthgmv"+onStr+ whereStr;


        Map<String, Object> map_result = new HashMap<String, Object>();
        if(pageInfo!=null) {

            String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

            int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
            int recordsPerPage = pageInfo.getRecordsPerPage();
            sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;


            String total = "0";
            List<Map<String, Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
            if (resultCount != null && resultCount.size() > 0) {
                total = String.valueOf(resultCount.get(0).get("total"));
            }

            pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

            Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
            map_result.put("pageinfo", pageInfo);

            map_result.put("total_pages", total_pages);

        }

        List<Map<String, Object>> list = ImpalaUtil.executeGuoan(sql);
        map_result.put("data", list);
		return map_result;

	}

	@Override
	public Map<String, Object> queryDeptConsumerByImpala(DynamicDto dynamicDto, PageInfo pageInfo) {
		String groupStr1 = " group by bussiness_group_id";
		String whereStr= " where c.bussiness_group_id is not null and c.department_name not like '%运营管理中心%' and c.department_name not like '%测试%' and c.department_name is not null";
		String onStr = " on c.bussiness_group_id = b.bussiness_group_id";
        String orderStr=" order by c.bussiness_group_id";
		String selectStr1 = ",min(department_name) as department_name";
		String selectStr2 = "";
		String storeWhere = "";
		if(dynamicDto.getDept()!=null&&!"".equals(dynamicDto.getDept())){
			whereStr = whereStr+" and c.bussiness_group_id='"+dynamicDto.getDept()+"'";
		}

		if(dynamicDto.getSearchstr().contains("dept_city_active")) {
			groupStr1 = groupStr1 + " ,store_city_code";
			if(dynamicDto.getCityName()!=null&&!"".equals(dynamicDto.getCityName())){
				whereStr = whereStr+" and c.store_city_name like '%"+dynamicDto.getCityName()+"%'";
			}

			onStr = onStr+" and c.store_city_code = b.store_city_code ";
			selectStr1=selectStr1+" ,store_city_code,min(store_city_name) as store_city_name";
			selectStr2 = selectStr2+",c.store_city_name";
            orderStr = orderStr+",c.store_city_name";
		}

		if(dynamicDto.getSearchstr().contains("dept_store_active")){
			groupStr1 = groupStr1 + ",real_store_id ";
//			if(dynamicDto.getStoreNo()!=null&&!"".equals(dynamicDto.getStoreNo())){
//				whereStr = whereStr+" and c.store_id='"+dynamicDto.getStoreNo()+"'";
//			}

			if(dynamicDto.getStoreNo()!=null&&!"".equals(dynamicDto.getStoreNo())){
				storeWhere = "tor.real_store_id='"+dynamicDto.getStoreNo()+"'  and ";
			}
			onStr = onStr+" and c.real_store_id = b.real_store_id LEFT JOIN t_store ts ON c.real_store_id = ts.id ";
			selectStr1=selectStr1+" ,real_store_id";
			selectStr2 = selectStr2+",c.real_store_id as store_id,ts.name as store_name,ts.storeno as store_code ";

		}

		if(dynamicDto.getSearchstr().contains("dept_channel_active")){
			groupStr1 = groupStr1 + ",channel_id ";
			if(dynamicDto.getChannel()!=null&&!"".equals(dynamicDto.getChannel())){
				whereStr = whereStr+" and c.channel_name like '%"+dynamicDto.getChannel()+"%'";
			}

			onStr = onStr+"  and c.channel_id = b.channel_id ";
			selectStr1=selectStr1+" ,channel_id,min(channel_name) as channel_name";
			selectStr2 = selectStr2+",c.channel_name";
            orderStr = orderStr+",c.channel_name";
		}

		whereStr = whereStr+orderStr;

		String sql = "SELECT " +
				" c.bussiness_group_id as deptid," +
				" c.department_name as deptname," +
				" c.cusnum," +
				" b.cusnum_ten " +
				 selectStr2+
				" FROM " +
				" (select " +
				" bussiness_group_id," +
				" count(distinct customer_id) as cusnum " +
				  selectStr1+
				" from df_mass_order_monthly tor" +
				" where "+storeWhere+" tor.sign_time >='"+dynamicDto.getBeginDate()+"' "+
				" and tor.sign_time < from_unixtime(unix_timestamp(days_add(from_unixtime(unix_timestamp('"+dynamicDto.getEndDate()+"'), 'yyyy-MM-dd') , 1)), 'yyyy-MM-dd')" +
				" and (tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA')" +
				" and (tor.store_white !='QA' and tor.store_status = 0)" +
				  groupStr1+") c" +
				" LEFT JOIN " +
				" (select " +
				" bussiness_group_id," +
				"sum(case when a.monetary > 10 then 1 else 0 end) as cusnum_ten" +
				  selectStr1+
				"  from (select " +
				" bussiness_group_id," +
				" sum(IFNULL(tor.gmv_price,0)) as monetary" +
				 selectStr1+
				" from df_mass_order_monthly tor" +
				" where "+storeWhere+" tor.sign_time >='"+dynamicDto.getBeginDate()+"' "+
				" and tor.sign_time < from_unixtime(unix_timestamp(days_add(from_unixtime(unix_timestamp('"+dynamicDto.getEndDate()+"'), 'yyyy-MM-dd') , 1)), 'yyyy-MM-dd')" +
				" and (tor.eshop_name NOT LIKE '%测试%' AND tor.eshop_white!='QA')" +
				" and (tor.store_white !='QA' and tor.store_status = 0)" +
				  groupStr1+",customer_id) a "+groupStr1+") b "+onStr+ whereStr;


        Map<String, Object> map_result = new HashMap<String, Object>();
        if(pageInfo!=null){
            String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

            int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
            int recordsPerPage = pageInfo.getRecordsPerPage();
            sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;


            String total = "0";
            List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
            if(resultCount !=null && resultCount.size()>0 ){
                total = String.valueOf(resultCount.get(0).get("total"));
            }

            pageInfo.setTotalRecords(Integer.valueOf(total.toString()));

            Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
            map_result.put("pageinfo", pageInfo);

            map_result.put("total_pages", total_pages);
        }
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        map_result.put("data", list);

		return map_result;
	}
}
