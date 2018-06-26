package com.cnpc.pms.communeMember.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;

/**
 * @author wuxinxin
 *
 */
public class CommuneMemberDaoImpl extends BaseDAOHibernate implements CommuneMemberDao{


	
	@Override
	public List<Map<String, Object>> getCmGoodsDealCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月22日
		 */
		String sql = "select member_count cou,member_type from ds_member_statistics where  member_type in ('1','2') order by member_type";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getCmGoodsTurnover(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		String sql = "select member_count cou from ds_member_statistics where  member_type='2' ";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<Map<String, Object>> getCmSexRatios(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		String mfSql = "select " + 
				"COUNT(case when duuf.sex = '男' then sex end  ) as mCount," + 
				"COUNT(case when duuf.sex = '女' then sex end  ) as fCount" + 
				" from df_user_member duuf";
			//查询新注册社员sql
			List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
			try{
				Query query = this.getHibernateTemplate().getSessionFactory()
						.getCurrentSession().createSQLQuery(mfSql);
				List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            if(!lst_data.isEmpty()){
	                for(Object obj : lst_data){
	                    Map<String,Object> map_data = (Map<String,Object>)obj;
	                    Map<String,Object> map_content = (Map<String,Object>)obj;
	                    map_content.put("mCount",map_data.get("mCount"));
	                    map_content.put("fCount",map_data.get("fCount"));
	                    lst_result.add(map_content);
	                }
	                
	            	if(lst_data.size()<10) {
	            		for(int i=0;i<10-lst_data.size();i++) {
	            			 Map<String,Object> map_content1 = new HashMap<String,Object>();
	            			map_content1.put("newcount",0);
	                        lst_result.add(map_content1);
	            		}
	            	}
	            }
			}catch (Exception e) {
				e.printStackTrace();
			}
			return lst_result;
		
	}

	@Override
	public List<Map<String, Object>> getCmAgeRatios(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		String ageSql = "select " + 
				
				"COUNT(case when birthday<'19600101' then 1 else null end) as age60, " + 
				"COUNT(case when birthday>'19591231' and birthday<'19700101' then 1 else null end) as age70, " + 
				"COUNT(case when birthday>'19691231' and birthday<'19800101' then 1 else null end) as age80, " + 
				"COUNT(case when birthday>'19791231' and birthday<'19900101' then 1 else null end) as age90, " + 
				"COUNT(case when birthday>'19891231' and birthday<'20000101' then 1 else null end) as age00, " + 
				"COUNT(case when birthday>'19991231'  then 1 else null end) as ageNow " + 
				" from df_user_member duuf";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(ageSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(!lst_data.isEmpty()){
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("age60",map_data.get("age60"));
                    map_content.put("age70",map_data.get("age70"));
                    map_content.put("age80",map_data.get("age80"));
                    map_content.put("age90",map_data.get("age90"));
                    map_content.put("age00",map_data.get("age00"));
                    map_content.put("ageNow",map_data.get("ageNow"));
                    lst_result.add(map_content);
                }
                
            	if(lst_data.size()<10) {
            		for(int i=0;i<10-lst_data.size();i++) {
            			 Map<String,Object> map_content1 = new HashMap<String,Object>();
            			map_content1.put("newcount",0);
                        lst_result.add(map_content1);
            		}
            	}
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}

	@Override
	public List<Map<String, Object>> getCmBirthday(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		//查询社员增长sql
				String birSql = "select t.monthNo as memMonth,count(1) as birCount from(select month(duuf.birthday) as monthNo,year(duuf.birthday) as myYear from df_user_member duuf where duuf.birthday is not null) as t  group by t.monthNo";
				List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
				
					Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(birSql);
					List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
   		            if(!lst_data.isEmpty()){
   		            	for(int j=0;j<lst_data.size();j++) {
   		            		Map<String,Object> map_content1 = new HashMap<String,Object>();
   		            		map_content1.put("birCount",lst_data.get(j).get("birCount"));
   		            		lst_result.add(map_content1);	
   		            	}
   		            }
   					return lst_result;
	}


	@Override
	public List<Map<String, Object>> getCmAllGrow(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		//查询社员增长sql
		String sql = "select count(*) as allcount,DATE_FORMAT(dum.opencard_time,\"%Y-%m-%d\") as crtime from df_user_member dum where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(dum.opencard_time) and CURDATE()> date(dum.opencard_time) group by DATE_FORMAT(dum.opencard_time,\"%Y-%m-%d\")  order by dum.opencard_time";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(!lst_data.isEmpty()){
            	//获取是否前多少天时候发有增长数据
        		Long growDate = breakDate(lst_data.get(0).get("crtime").toString());
        		if(growDate>1) {
        			for(int i=0;i<growDate;i++) {
        				Map<String,Object> nullMap = new HashMap<String, Object>();
        				nullMap.put("allcount","0");
        				lst_result.add(nullMap);
        			}
        			
        		}
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("allcount",map_data.get("allcount"));
                    lst_result.add(map_content);
                }
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}

	@Override
	public List<Map<String, Object>> getCmNewGrow(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		
		
		//查询新注册社员sql
		String sql = "select count(*) as newcount,DATE_FORMAT(dum2.opencard_time,\"%Y-%m-%d\") as crtime   from df_user_member dum2 where dum2.isnew_member=1 and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(dum2.opencard_time) group by DATE_FORMAT(dum2.opencard_time,\"%Y-%m-%d\") order by dum2.opencard_time";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            if(!lst_data.isEmpty()){
                for(Object obj : lst_data){
                    Map<String,Object> map_data = (Map<String,Object>)obj;
                    Map<String,Object> map_content = (Map<String,Object>)obj;
                    map_content.put("newcount",map_data.get("newcount"));
                    lst_result.add(map_content);
                }
                
            	if(lst_data.size()<10) {
            		for(int i=0;i<10-lst_data.size();i++) {
            			 Map<String,Object> map_content1 = new HashMap<String,Object>();
            			map_content1.put("newcount",0);
                        lst_result.add(map_content1);
            		}
            	}
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}

	@Override
	public List<Map<String, Object>> getCmOldGrow(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		
		//查询老用户转社员sql
		String sql = "select count(*) as oldcount,DATE_FORMAT(dum1.opencard_time,\"%Y-%m-%d\") as crtime  from  df_user_member dum1 where dum1.isnew_member=0 or dum.isnew_member is null and '2018-04-29' <= date(dum1.opencard_time) group by DATE_FORMAT(dum1.opencard_time,\"%Y-%m-%d\") order by dum1.opencard_time";
		List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
            if(!lst_data.isEmpty()){
            	  for(Object obj : lst_data){
                      Map<String,Object> map_data = (Map<String,Object>)obj;
                      Map<String,Object> map_content = (Map<String,Object>)obj;
                      map_content.put("oldcount",map_data.get("oldcount"));
                      lst_result.add(map_content);
                  }
            	if(lst_data.size()<10) {
            		for(int i=0;i<10-lst_data.size();i++) {
            			 Map<String,Object> map_content1 = new HashMap<String,Object>();
            			map_content1.put("oldcount",0);
                        lst_result.add(map_content1);
            		}
            	}
            }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}

 public Long breakDate(String date) throws ParseException {
	 DateFormat f = new SimpleDateFormat("yyyy-MM-dd");  
		
     Date today = new Date();  

     Calendar c = Calendar.getInstance();  
     c.setTime(today);  
     c.add(Calendar.DAY_OF_MONTH, -7);// 今天+1天  

     String growDate =  (f.format(c.getTime()));
     Long countDay = (f).parse(date).getTime()-f.parse(growDate).getTime();
		long d = countDay/1000/60/60/24;//天
		return d;
 }

@Override
public List<Map<String, Object>> getAllCount(String dd) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月18日
	 */
	
	//查询社员总量sql
	String sql = "select count(*) as allCount from  df_user_member dum";
	if(!"0000".equals(dd)) {
		sql = sql+ " where dum.regist_cityno='"+dd+"'";
	}
	List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
	try{
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
        if(!lst_data.isEmpty()){
        	  for(Object obj : lst_data){
                  Map<String,Object> map_data = (Map<String,Object>)obj;
                  Map<String,Object> map_content = (Map<String,Object>)obj;
                  map_content.put("allCount",map_data.get("allCount"));
                  lst_result.add(map_content);
              }
        }
	}catch (Exception e) {
		e.printStackTrace();
	}
	return lst_result;
	
}

@Override
public List<Map<String, Object>> getNewCount(String dd) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月18日
	 */
	
	//查询新用户注册社员sql
	String sql = "select count(*) as newCount from  df_user_member dum where dum.isnew_member=1";
	if(!"0000".equals(dd)) {
		sql = sql+ " and dum.regist_cityno='"+dd+"'";
	}
	List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
	try{
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
        if(!lst_data.isEmpty()){
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
public List<Map<String, Object>> getOldCount(String dd) {
	// TODO Auto-generated method stub
			/**
			 * @author wuxinxin
			 * 2018年5月18日
			 */
			
			//查询老用户转社员sql
			String sql = "select count(*) as oldcount from  df_user_member dum where dum.isnew_member=0 or dum.isnew_member is null";
			if(!"0000".equals(dd)) {
				sql = sql+ " and dum.regist_cityno='"+dd+"'";
			}
			List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
			try{
				Query query = this.getHibernateTemplate().getSessionFactory()
						.getCurrentSession().createSQLQuery(sql);
				List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				
	            if(!lst_data.isEmpty()){
	            	  for(Object obj : lst_data){
	                      Map<String,Object> map_data = (Map<String,Object>)obj;
	                      Map<String,Object> map_content = (Map<String,Object>)obj;
	                      map_content.put("oldCount",map_data.get("oldcount"));
	                      lst_result.add(map_content);
	                  }
	            }
			}catch (Exception e) {
				e.printStackTrace();
			}
			return lst_result;
	
}

@Override
public String getAllCmIds(String dd) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月18日
	 */
	String sql = "select replace(idds,\",\",\"','\") as ids  from (select group_concat(customer_id) as idds from  df_user_member dum) as idtable";
	
	
	 ;
	try{
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
        if(!lst_data.isEmpty()){
        	  for(Object obj : lst_data){
                  Map<String,Object> map_data = (Map<String,Object>)obj;
                  String idString = map_data.get("ids").toString();
                  return idString;
              }
        }
	}catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	
}

@Override
public List<Map<String, Object>> getAllMembers(String dd) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月21日
	 */
	int dayCount = 7;
	String sql = "select DATE_SUB(CURDATE(), INTERVAL 7 DAY)," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 7 DAY)  then 1 else 0 end) as day1," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 6 DAY)  then  1 else 0 end) as day2," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 5 DAY)  then  1 else 0 end) as day3," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 4 DAY)  then  1 else 0 end) as day4," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 3 DAY)  then  1 else 0 end) as day5," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 2 DAY)  then  1 else 0 end) as day6," + 
			"			sum(CASE when  date(dum.opencard_time)<= DATE_SUB(curdate(),INTERVAL 1 DAY)  then  1 else 0 end) as day7" + 
			"			 from df_user_member dum";
	if(!"0000".equals(dd)) {
		sql = sql+ " where dum.regist_cityno='"+dd+"'";
	}
	List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
	try{
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if(!lst_data.isEmpty()){
        	
        	Map<String,Object> map_data = (Map<String,Object>)lst_data.get(0);
        	for(int i=1;i<=dayCount;i++) {
        		Map<String,Object> map_content = new HashMap<String, Object>();
        		map_content.put("allcount", map_data.get("day"+i));
        		 lst_result.add(map_content);
        	}
           /* for(Object obj : lst_data){
                Map<String,Object> map_data = (Map<String,Object>)obj;
                Map<String,Object> map_content = (Map<String,Object>)obj;
                map_content.put("newcount",map_data.get("newcount"));
                lst_result.add(map_content);
            }*/
            
/*        	if(lst_data.size()<10) {
        		for(int i=0;i<10-lst_data.size();i++) {
        			 Map<String,Object> map_content1 = new HashMap<String,Object>();
        			map_content1.put("newcount",0);
                    lst_result.add(map_content1);
        		}
        	}*/
        }
	}catch (Exception e) {
		e.printStackTrace();
	}
	return lst_result;
	
}
private String doMonth(String mon) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月22日
	 */
	String selMon = "sel"+mon;
	
	 return selMon;
	
}

@Override
public List<Map<String, Object>> getMembersArea(String dd) {
	// TODO Auto-generated method stub
	/**
	 * @author wuxinxin
	 * 2018年5月22日
	 */
	
	String birSql = "select dii.placename as mePro, count(*) as meCount from df_user_member dum,ds_idcard_item dii  where dum.born_province is not null and dum.born_province=dii.placecode group by dum.born_province";
	if(!"0000".equals(dd)) {
		birSql = birSql+ " and dum.regist_cityno='"+dd+"'";
	}
	List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
	
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(birSql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		StringBuffer sbPro = new StringBuffer();
		StringBuffer sbCou = new StringBuffer();
		 
           if(!lst_data.isEmpty()){
        	   
        	   for(Object obj : lst_data){
                   Map<String,Object> map_data = (Map<String,Object>)obj;
                   sbPro.append(map_data.get("mePro")+"-");//添加城市名
              	   sbCou.append(map_data.get("meCount")+"-");//添加城市社员数量
               }
        	   Map<String,Object> map_mePro = new HashMap<String,Object>();
        	   Map<String,Object> map_meCount = new HashMap<String,Object>();
        	   map_mePro.put("mePro",sbPro.toString());
        	   map_meCount.put("meCount",sbCou.toString());
               lst_result.add(map_mePro);
        	   lst_result.add(map_meCount);
           }
			return lst_result;
	
	}

	/**************************************************************
	 ******************** 第二版：社员注册信息统计*************************
	 **************************************************************/
	@Override
	public List<Map<String, Object>> getCmRegistCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin 2018年5月24日
		 */
		// 获取上月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 查询社员注册城市sql
		String citySql = "select tdc.cityname cityname, duuf.usedCount cou from (SELECT DISTINCT uw.regist_cityno uwci, IFNULL( tb.count, 0) usedCount from df_user_member uw  left join (select regist_cityno , count( *) count   from df_user_member  where date(opencard_time)<'"
				+ sdf.format(calendar.getTime())
				+ "' and regist_cityno is not null group by regist_cityno) AS tb  on uw.regist_cityno = tb.regist_cityno  where uw.regist_cityno is not null) as duuf,t_dist_citycode tdc where LPAD(duuf.uwci, 4, '0') = tdc.cityno";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(citySql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;

	}

	@Override
	public List<Map<String, Object>> getCmRegistMonthCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin 2018年5月24日
		 */
		// 获取上月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		// 按格式输出
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 查询当月新注册社员注册城市sql
		String citySql = "select tdc.cityname cityname, duuf.usedCount cou from (SELECT DISTINCT uw.regist_cityno uwci, IFNULL( tb.count, 0) usedCount from df_user_member uw  left join (select regist_cityno , count( *) count   from df_user_member  where date(opencard_time)>'"
				+ sdf.format(calendar.getTime())
				+ "' and regist_cityno is not null group by regist_cityno) AS tb  on uw.regist_cityno = tb.regist_cityno  where uw.regist_cityno is not null) as duuf,t_dist_citycode tdc where LPAD(duuf.uwci, 4, '0') = tdc.cityno";

		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(citySql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;

	}
	@Override
	public List<Map<String, Object>> getAllCmRegistCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月24日
		 */
		String citySql = "SELECT tdc.cityname cityname, count(*) cou FROM df_user_member dfum, t_dist_citycode tdc WHERE dfum.regist_cityno IS NOT NULL AND LPAD(dfum.regist_cityno, 4, '0') = tdc.cityno  GROUP BY dfum.regist_cityno order by cou  asc ";
		try{
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(citySql);
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getHotProduct(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月28日
		 */
		String sql = "select member_count cou,member_name pname,sell_duration selldur from ds_member_statistics where  member_type='3' order by CAST(member_count as SIGNED) desc limit 10";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getCoolProduct(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月28日
		 */
		String sql = "select member_count cou,member_name pname,sell_duration selldur from ds_member_statistics where  member_type='4' order by CAST(sell_duration as SIGNED) desc limit 10";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getMovingPinCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月1日
		 */
		String sql = "select member_count cou from ds_member_statistics where  member_type='5'";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getEshopSell(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月1日
		 */
		String sql = "select member_count sellsum,member_name ename,sell_duration sellcou from ds_member_statistics where  member_type='6' order by CAST(member_count as SIGNED) desc limit 10";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getAllEshopSum(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月2日
		 */
		String sql = "select member_count cou from ds_member_statistics where  member_type='7'";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
		
	}
	@Override
	public List<Map<String, Object>> getYesEshopSum(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月2日
		 */
		String sql = "select member_count cou from ds_member_statistics where  member_type='8'";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getNoEshopSum(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月2日
		 */
		String sql = "select member_count cou from ds_member_statistics where  member_type='9'";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDayDealCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月7日
		 */
		
		String daySumSql = "select ifnull(sum(dmod.trading_price),0) dealsum,count(1) cou from df_mass_order_daily dmod,df_user_member dum,ds_member_eshop dme where CURDATE()=date(dmod.sign_time) and dmod.customer_id=dum.customer_id and dmod.eshop_id=dme.eshop_id";
		
		if("0000".equals(string)) {
			daySumSql = daySumSql+" and dmod.city_code='"+string+"'";
		}
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDay7DealCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月7日
		 */
		String daySumSql = "SELECT count(dmod.customer_id) alldealcount, sum(dmod.trading_price) alldealsum, DATE_FORMAT(dmod.sign_time, '%Y-%m-%d') dealtime from df_mass_order_monthly dmod,df_user_member  dum where date(dmod.sign_time) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) and date(dmod.sign_time) < CURDATE() and dmod.customer_id=dum.customer_id group by DATE_FORMAT(dmod.sign_time, '%Y-%m-%d')";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDayaddMemCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin 2018年6月11日
		 */
		String daySumSql = "select count(1) as cou from df_user_member dum where CURDATE() = date(dum.opencard_time)";
		if("0000".equals(string)) {
			daySumSql = daySumSql+ " dum.regist_cityno='"+string+"'";
		}
		try {
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getDayOfEshopMemCount(String string) {
		/**
		 * @author wuxinxin 2018年6月12日
		 */
		int dayCount = 7;
		String sql = "select DATE_SUB(CURDATE(), INTERVAL 7 DAY)," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 7 DAY)  then 1 else 0 end),0) as day1," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 6 DAY)   then 1 else 0 end),0) as day2," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 5 DAY)  then 1 else 0 end),0) as day3," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 4 DAY)   then 1 else 0 end),0) as day4," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 3 DAY)   then 1 else 0 end),0) as day5," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 2 DAY)   then 1 else 0 end),0) as day6," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 1 DAY)  then 1 else 0 end),0) as day7" + 
				" from df_mass_order_monthly dmod  where dmod.order_tag1 like '%E%' and  dmod.order_tag1 like '%K%'";

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();
		try {
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if (!lst_data.isEmpty()) {

				Map<String, Object> map_data = (Map<String, Object>) lst_data.get(0);
				for (int i = 1; i <= dayCount; i++) {
					Map<String, Object> map_content = new HashMap<String, Object>();
					map_content.put("dayOfEshopMemCount", map_data.get("day" + i).toString());
					lst_result.add(map_content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopMemSum(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		int dayCount = 7;
		String sql = "select DATE_SUB(CURDATE(), INTERVAL 7 DAY)," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 7 DAY)  then dmod.trading_price else 0 end),0) as day1," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 6 DAY)   then dmod.trading_price else 0 end),0) as day2," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 5 DAY)  then dmod.trading_price else 0 end),0) as day3," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 4 DAY)   then dmod.trading_price else 0 end),0) as day4," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 3 DAY)   then dmod.trading_price else 0 end),0) as day5," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 2 DAY)   then dmod.trading_price else 0 end),0) as day6," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 1 DAY)  then dmod.trading_price else 0 end),0) as day7" + 
				" from df_mass_order_monthly dmod  where dmod.order_tag1 like '%E%' and  dmod.order_tag1 like '%K%'";

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();
		try {
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if (!lst_data.isEmpty()) {

				Map<String, Object> map_data = (Map<String, Object>) lst_data.get(0);
				for (int i = 1; i <= dayCount; i++) {
					Map<String, Object> map_content = new HashMap<String, Object>();
					map_content.put("dayOfEshopMemSum", map_data.get("day" + i));
					lst_result.add(map_content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopNmemCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		int dayCount = 7;
		String sql = "select DATE_SUB(CURDATE(), INTERVAL 7 DAY)," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 7 DAY)  then 1 else 0 end),0) as day1," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 6 DAY)   then 1 else 0 end),0) as day2," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 5 DAY)  then 1 else 0 end),0) as day3," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 4 DAY)   then 1 else 0 end),0) as day4," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 3 DAY)   then 1 else 0 end),0) as day5," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 2 DAY)   then 1 else 0 end),0) as day6," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 1 DAY)  then 1 else 0 end),0) as day7" + 
				" from df_mass_order_monthly dmod  where dmod.order_tag1 like '%E%' and  dmod.order_tag1 not like '%K%'";

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();
		try {
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if (!lst_data.isEmpty()) {

				Map<String, Object> map_data = (Map<String, Object>) lst_data.get(0);
				for (int i = 1; i <= dayCount; i++) {
					Map<String, Object> map_content = new HashMap<String, Object>();
					map_content.put("dayOfEshopNmemCount", map_data.get("day" + i));
					lst_result.add(map_content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopNmemSum(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		int dayCount = 7;
		String sql = "select DATE_SUB(CURDATE(), INTERVAL 7 DAY)," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 7 DAY)  then dmod.trading_price else 0 end),0) as day1," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 6 DAY)   then dmod.trading_price else 0 end),0) as day2," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 5 DAY)  then dmod.trading_price else 0 end),0) as day3," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 4 DAY)   then dmod.trading_price else 0 end),0) as day4," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 3 DAY)   then dmod.trading_price else 0 end),0) as day5," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 2 DAY)   then dmod.trading_price else 0 end),0) as day6," + 
				"ifnull(sum(CASE when  date(dmod.sign_time)= DATE_SUB(curdate(),INTERVAL 1 DAY)  then dmod.trading_price else 0 end),0) as day7" + 
				" from df_mass_order_monthly dmod  where dmod.order_tag1 like '%E%' and  dmod.order_tag1  not like '%K%'";

		List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();
		try {
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if (!lst_data.isEmpty()) {

				Map<String, Object> map_data = (Map<String, Object>) lst_data.get(0);
				for (int i = 1; i <= dayCount; i++) {
					Map<String, Object> map_content = new HashMap<String, Object>();
					map_content.put("dayOfEshopNmemSum", map_data.get("day" + i));
					lst_result.add(map_content);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lst_result;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopMemSumCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		String daySumSql = "select dfmod.store_city_name cname,ifnull(dfmo.daypri,0) citypri from df_mass_order_daily  dfmod left join (select dmod.store_city_name, sum( CASE when curdate() = date(dmod.sign_time) then dmod.trading_price else 0 end ) as daypri,dmod.store_city_name as cityname from df_mass_order_daily dmod where dmod.order_tag1 like '%E%' and dmod.order_tag1 like '%K%'  group by dmod.store_city_name) dfmo on dfmod.store_city_name= dfmo.cityname group by dfmod.store_city_name";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopMemCouCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		String daySumSql = "select dfmod.store_city_name cname,ifnull(dfmo.daypri,0) citypri from df_mass_order_daily  dfmod left join (select dmod.store_city_name, sum( CASE when curdate() = date(dmod.sign_time) then 1 else 0 end ) as daypri,dmod.store_city_name as cityname from df_mass_order_daily dmod where dmod.order_tag1 like '%E%' and dmod.order_tag1 like '%K%'  group by dmod.store_city_name) dfmo on dfmod.store_city_name= dfmo.cityname group by dfmod.store_city_name";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopNmemSumCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		String daySumSql = "select dfmod.store_city_name cname,ifnull(dfmo.daypri,0) citypri from df_mass_order_daily  dfmod left join (select dmod.store_city_name, sum( CASE when curdate() = date(dmod.sign_time) then dmod.trading_price else 0 end ) as daypri,dmod.store_city_name as cityname from df_mass_order_daily dmod where dmod.order_tag1 like '%E%' and dmod.order_tag1 not like '%K%'  group by dmod.store_city_name) dfmo on dfmod.store_city_name= dfmo.cityname group by dfmod.store_city_name";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getDayOfEshopNmemCouCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		String daySumSql = "select dfmod.store_city_name cname,ifnull(dfmo.daypri,0) citypri from df_mass_order_daily  dfmod left join (select dmod.store_city_name, sum( CASE when curdate() = date(dmod.sign_time) then 1 else 0 end ) as daypri,dmod.store_city_name as cityname from df_mass_order_daily dmod where dmod.order_tag1 like '%E%' and dmod.order_tag1 not like '%K%'  group by dmod.store_city_name) dfmo on dfmod.store_city_name= dfmo.cityname group by dfmod.store_city_name";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getEshopMemCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		String daySumSql = "select domcd.date,sum(domcd.mem_count) memcount,sum(domcd.mem_gmv) memgmv,sum(domcd.non_mem_count) nmemcount,sum(domcd.non_mem_gmv) nmemgmv,sum(domcd.eshop_count) eshopcou,sum(domcd.eshop_gmv) eshopgmv from ds_ope_member_city_day domcd where date(domcd.date)>=DATE_SUB(curdate(),INTERVAL 7 DAY) and date(domcd.date)<curdate() GROUP BY domcd.date";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getEshopNmemCouCity(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
			String daySumSql = "select domcd.city_name cname,sum(domcd.eshop_count) eshopcou,sum(domcd.eshop_gmv) eshopgmv from ds_ope_member_city_day domcd GROUP BY domcd.city_name order by domcd.city_code";
			try{
				Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
				List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				return lst_data;
			}catch (Exception e) {
				e.printStackTrace();
			}
			 return null;
		
	}
	@Override
	public List<Map<String, Object>> getEshopWeekCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月13日
		 */
		String daySumSql = "select ifnull(sum(domcd.eshop_count),0) eweekcou,ifnull(sum(domcd.eshop_gmv),0) eweekgmv from ds_ope_member_city_day domcd where domcd.date >=DATE_SUB(curdate(),INTERVAL 7 DAY) and domcd.date <curdate()";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getEshopQuitCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月13日
		 */
		String daySumSql = "select domcd.canceldate,sum(domcd.cancelcount) memcount from ds_ope_member_cancel_city_day domcd where date(domcd.canceldate)>=DATE_SUB(curdate(),INTERVAL 7 DAY) and date(domcd.canceldate)<curdate() GROUP BY domcd.canceldate";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
		
	}
	@Override
	public List<Map<String, Object>> getMemweekCount(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月13日
		 */
		 String daySumSql = "select count(distinct dmod.customer_id) memcou from df_mass_order_monthly dmod  where date(dmod.sign_time)>=DATE_SUB(curdate(),INTERVAL 7 DAY) and date(dmod.sign_time)<curdate() and dmod.order_tag1   not like '%E%' and  dmod.order_tag1  not like '%K%'";
			try{
				Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
				List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				return lst_data;
			}catch (Exception e) {
				e.printStackTrace();
			}
			 return null;
		
	}
	@Override
	public List<Map<String, Object>> getHourCount(String string) {
		/**
		 * @author wuxinxin
		 * 2018年6月20日
		 */
		String daySumSql = "select date_format(dmom.sign_time, '%H时') seltime, count(1) prisum from df_mass_order_total dmom where dmom.sign_time > '2018-05-17 23:59:59' and dmom.order_tag1 like '%E%' group by date_format(dmom.sign_time, '%H') order by date_format(dmom.sign_time, '%H') ";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public List<Map<String, Object>> getTimeDiff(String string) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月20日
		 */
		 String daySumSql = "select sum(dood.order_count) selcount,dood.order_times seltimes from ds_ope_order_distribution dood group by dood.order_times order by dood.order_times";
			try{
				Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
				List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				return lst_data;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
	}
	@Override
	public List<Map<String, Object>> getOrderType(String string) {
		/**
		 * @author wuxinxin
		 * 2018年6月21日
		 */
		String daySumSql = "select sum(dompad.ordercount) ordertypecou ,dompad.ordertype ordertypename from ds_ope_memberproduct_all_day dompad group by dompad.ordertype";
		try{
			Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(daySumSql);
			List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return lst_data;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
