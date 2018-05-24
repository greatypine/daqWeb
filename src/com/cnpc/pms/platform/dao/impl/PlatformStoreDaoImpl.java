package com.cnpc.pms.platform.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.core.impl.DAORootHibernate;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.IJoin;
import com.cnpc.pms.base.paging.IPage;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.model.PMSQuery;
import com.cnpc.pms.personal.dto.StoreDTO;
import com.cnpc.pms.personal.entity.YyStore;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.entity.PlatformStore;

public class PlatformStoreDaoImpl extends DAORootHibernate implements PlatformStoreDao{

	@Override
	public List<PlatformStore> getPlatformStoreList(String where, PageInfo pageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * from t_store where 1=1 ");
		sql.append(where);
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        Map<String,Object> map_result = new HashMap<String,Object>();
        List<PlatformStore> lst_data = null;
        try{
            SQLQuery query = session.createSQLQuery(sql.toString());
            query.addEntity(PlatformStore.class);
            lst_data = query
                    .setFirstResult(
                            pageInfo.getRecordsPerPage()
                                    * (pageInfo.getCurrentPage() - 1))
                    .setMaxResults(pageInfo.getRecordsPerPage()).list();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        /*if(lst_data == null){
            lst_data = new ArrayList<Map<String,Object>>();
        }
        map_result.put("pageinfo", pageInfo);
        map_result.put("header", "");
        map_result.put("data", lst_data);*/
        return lst_data;
		
		
		
		
		
		/*SQLQuery query = getHibernateTemplate().getSessionFactory()
				.openSession().createSQLQuery(sql.toString());
		query.addEntity(StoreDTO.class);
		List<StoreDTO> list = query.list();
		pageInfo.setTotalRecords(list.size());
		List<StoreDTO> returnList = query.setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
		return returnList;*/
	}
	
	
	
	
	
	@Override
	public Map<String, Object> getPlatformStoreInfoList(String where, PageInfo pageInfo) {
		 String sql_count="select count(1) from t_store where 1=1 "+where;
		 String find_sql="SELECT id,name from t_store where 1=1 "+where;
	        Session session = getHibernateTemplate().getSessionFactory().openSession();
	        Map<String,Object> map_result = new HashMap<String,Object>();
	        List<?> lst_data = null;
	        try{
	            SQLQuery countQuery = session.createSQLQuery(sql_count);
	            pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));

	            SQLQuery query = session.createSQLQuery(find_sql);
	            lst_data = query
	                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
	                    .setFirstResult(
	                            pageInfo.getRecordsPerPage()
	                                    * (pageInfo.getCurrentPage() - 1))
	                    .setMaxResults(pageInfo.getRecordsPerPage()).list();
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        if(lst_data == null){
	            lst_data = new ArrayList<Map<String,Object>>();
	        }
	        map_result.put("pageinfo", pageInfo);
	        map_result.put("header", "平台门店列表");
	        map_result.put("data", lst_data);
	        return map_result;
	}





	@Override
	public int getPlatformStoreCount(String where) {
		int platformcount=0;
		String sql_count="select count(1) from t_store where 1=1 "+where;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		 try{
	            SQLQuery countQuery = session.createSQLQuery(sql_count);
	            platformcount= Integer.valueOf(countQuery.list().get(0).toString());
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
		return platformcount;
	}





	@Override
	public List<Map<String, Object>> getEmployeeByStore(String storeNo,Boolean isRealtime) {
		String sql = "";
		if(isRealtime){
			sql = " and te.online = 'online' and ts.`code` = '"+storeNo+"'";
		}else{
			sql = "and te.online is not null and ts.`code` = '"+storeNo+"'";
		}
		String str_sql = "select concat(te.id,'') as employeeId,CONCAT(te.store_id,'') as platformid,te.name as employeeName,ts.name as storeName,te.code as employeeNo,te.online as online from t_employee te INNER JOIN t_store ts ON ts.id = te.store_id where te.status = 0 "+sql;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        try{
            SQLQuery query = session.createSQLQuery(str_sql);
            List<Map<String,Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
		return lst_result;
	}





	@Override
	public List<Map<String, Object>> getEmployeeByEmployeeNo(String employeeNo,Boolean isRealtime) {
		String sql = "";
		if(isRealtime){
			sql = " and te.online = 'online' and te.CODE = '"+employeeNo+"'";
		}else{
			sql = " and te.CODE = '"+employeeNo+"'";
		}
		String str_sql = "select concat(te.id,'') as employeeId,te.name as employeeName,ts.name as storeName,te.code as employeeNo,te.`online` as online from t_employee te INNER JOIN t_store ts ON ts.id = te.store_id where te.status = 0"+sql;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        try{
            SQLQuery query = session.createSQLQuery(str_sql);
            List<Map<String,Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
		return lst_result;
	}





	@Override
	public Map<String,Object> getEmployeeStatus(String employeeNo) {
		String str_sql = "select `online` from t_employee where status = 0 and code = '"+employeeNo+"'";
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		List<Map<String,Object>> lst_data = new ArrayList<Map<String,Object>>();
        try{
            SQLQuery query = session.createSQLQuery(str_sql);
            lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return lst_data.size()>0?lst_data.get(0):null;
	}





	@Override
	public List<Map<String, Object>> getEmployeeByCity(String city_code, Boolean isRealtime) {
		String str_sql = "";
		if(isRealtime){
			str_sql = " and ts.city_code ='"+city_code+"' and te.online = 'online'";
		}else{
			str_sql = " and ts.city_code ='"+city_code+"' and te.online is not null";
		}
		String sql = "select concat(te.id,'') as employeeId,CONCAT(te.store_id,'') as platformid,te.name as employeeName,ts.name as storeName,te.code as employeeNo,te.online as online,te.mobilephone as mobilephone from t_employee te "
				+"INNER JOIN t_store ts ON ts.id = te.store_id where  te.status = 0 and ts.name NOT LIKE '%测试%' and ts.white!='QA'"+str_sql;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> lst_result = new ArrayList<Map<String,Object>>();
        try{
            SQLQuery query = session.createSQLQuery(sql);
            List<Map<String,Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            lst_result = lst_data;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
		return lst_result;
	}
	
	//------------------------------------2018-05-18        wuxinxin------------------------------//
	//------------------------------------------------start---------------------------------------//
	@Override
	public List<Map<String, Object>> selectAllCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		
		String cmSql="select count(*) as cou from t_eshop e where e.super_member = 'yes'";
		
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(cmSql);
	            //获得查询数据
	            List<Map<String, Object>> cm_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return cm_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;
		
	}

	@Override
	public List<Map<String, Object>> selectNewCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		
		String newCmSql="select count(*) as cou from t_eshop e where e.super_member = 'yes'";
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(newCmSql);
	            //获得查询数据
	            List<Map<String, Object>> newCm_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return newCm_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;

	}

	@Override
	public List<Map<String, Object>> selectOldCm(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		String oldCmSql="select count(*) as cou from t_eshop e where e.super_member = 'yes'";
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(oldCmSql);
	            //获得查询数据
	            List<Map<String, Object>> oldCm_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return oldCm_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;
		
	}

	@Override
	public List<Map<String, Object>> getEshopCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		String sql="select count(*) as cou from t_eshop e where e.super_member = 'yes'";
		
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(sql);
	            //获得查询数据
	            List<Map<String, Object>> eshop_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return eshop_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;
	}

	@Override
	public List<Map<String, Object>> getGoodsTypeCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 查询新老用户增长量  按天查10天
		 * 2018年5月17日
		 */
		String skuSql="select count(*) cou from t_product p,t_eshop te where  te.super_member = 'yes' and p.eshop_id=te.id";
		
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(skuSql);
	            //获得查询数据
	            List<Map<String, Object>> sku_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return sku_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;
	}

	@Override
	public List<Map<String, Object>> getCmGoodsDealCount(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		String dealCouSql="select count(1) as cou from t_order tor join t_eshop te on (tor.eshop_id = te.id and te.super_member = 'yes')";
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(dealCouSql);
	            //获得查询数据
	            List<Map<String, Object>> dealCou_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return dealCou_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
	        }
	        return null;
		
	}

	@Override
	public List<Map<String, Object>> getCmGoodsTurnover(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月17日
		 */
		String turnoverSql="select ifnull(sum(tor.trading_price),0) as cou from t_order tor join t_eshop te on (tor.eshop_id = te.id and te.super_member = 'yes')";
		 Session session = getHibernateTemplate().getSessionFactory().openSession();
	        try{
	            SQLQuery sqlQuery = session.createSQLQuery(turnoverSql);
	            //获得查询数据
	            List<Map<String, Object>> turnover_data = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	            return turnover_data;
	        }catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            session.close();
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
		 return null;
		
	}


	@Override
	public List<Map<String, Object>> getCmAgeRatios(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		 return null;
		
	}


	@Override
	public Map<String, Object> getCmBirthday(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		 return null;
		
	}


	@Override
	public Map<String, Object> getCmGrow(String dd) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年5月18日
		 */
		 return null;
		
	}

	//----------------------------------wuxinxin   end------------------------------------//
	
	

}
