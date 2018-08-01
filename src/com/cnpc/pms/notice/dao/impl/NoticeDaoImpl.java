package com.cnpc.pms.notice.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.notice.dao.NoticeDao;

/**
 * 
 * @author gbl
 * 公告
 */
public class NoticeDaoImpl extends BaseDAOHibernate implements NoticeDao{

	@Override
	public List<Map<String, Object>> queryAllCity(String whereStr, PageInfo pageInfo) {
		String sql="select * from   t_dist_citycode a where a.status=0 "+whereStr;
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        pageInfo.setTotalRecords(query.list().size());
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setFirstResult(
                        pageInfo.getRecordsPerPage()
                                * (pageInfo.getCurrentPage() - 1))
                .setMaxResults(pageInfo.getRecordsPerPage()).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }


        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> queryPartCity(String whereStr, PageInfo pageInfo) {
		String sql="select * from   t_dist_city a where a.status=0 "+whereStr;
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
        pageInfo.setTotalRecords(query.list().size());
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setFirstResult(
                        pageInfo.getRecordsPerPage()
                                * (pageInfo.getCurrentPage() - 1))
                .setMaxResults(pageInfo.getRecordsPerPage()).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }


        return (List<Map<String,Object>>)lst_data;
	}
	
	
	public List<Map<String,Object>> queryCityByUserId(Long userId){
		String sql="select * from t_dist_city where status=0 and  pk_userid="+userId;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());

        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data;   
	}

	@Override
	public List<Map<String, Object>> queryStoreByCity(String cityInfo) {
		
		
		String sql = "select a.store_id from t_store a  inner join (select cityname from t_dist_citycode where citycode in("+cityInfo+")) b on a.city_name =b.cityname and a.status=0";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());

		//获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    return lst_data; 
	}

	@Override
	public List<Map<String, Object>> getStoreOfCity(String cityStr, String otherWhere, PageInfo pageInfo) {
		if(cityStr!=null&&!"".equals(cityStr)){
			cityStr = " where citycode in ("+cityStr+")";
		}
		String sql="select b.store_id,b.name,b.city_name from (select * from t_dist_citycode "+cityStr+") as a inner join t_store as b on a.cityname=b.city_name "+otherWhere;
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
        pageInfo.setTotalRecords(query.list().size());
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setFirstResult(
                        pageInfo.getRecordsPerPage()
                                * (pageInfo.getCurrentPage() - 1))
                .setMaxResults(pageInfo.getRecordsPerPage()).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getReceiveZW(String otherWhere, PageInfo pageInfo) {
		String sql=" select * from (select DISTINCT zw from t_humanresources union ALL select DISTINCT zw from t_storekeeper) as a where 1=1 "+otherWhere;
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
        pageInfo.setTotalRecords(query.list().size());
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .setFirstResult(
                        pageInfo.getRecordsPerPage()
                                * (pageInfo.getCurrentPage() - 1))
                .setMaxResults(pageInfo.getRecordsPerPage()).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }


        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getReceiveEmployee(Map<String, Object> param) {
		    String sql = "select tt.employeeId,tt.token,tt.os,tt.client_id,tt.phone as mobilephone,tt.storeno as storeNo,tt.store_id as storeId from "+
		    
		    " (SELECT sk.employee_no as employeeId,tu.token,tu.os,tu.client_id,sk.phone,sk.zw,sk.humanstatus,ts.storeno,ts.store_id,ts.name,ts.city_name FROM t_store ts "+
		    " INNER JOIN tb_bizbase_user tu ON tu.id=ts.skid "+
		    " INNER JOIN t_storekeeper sk ON sk.employee_no=tu.employeeId "+
		    " UNION ALL"+
		    " SELECT a.employeeId,a.token,a.os,a.client_id,b.phone, b.zw,b.humanstatus,t.storeno,t.store_id,t.name,t.city_name FROM tb_bizbase_user AS a"+
		    " INNER JOIN  t_humanresources b on a.employeeId = b.employee_no "+		
		    " INNER JOIN t_store t on t.store_id = b.store_id ) tt  where tt.humanstatus=1";

		    if(param.get("city")!=null){ 
				
				sql =sql+ " and   tt.city_name in (select cityname from t_dist_citycode where citycode in "+param.get("city")+")";
			}
			
			if(param.get("store")!=null){
				sql =sql+ " and tt.store_id in "+param.get("store");
			}
			
			if(param.get("zw")!=null){
				sql =sql+ "  and tt.zw in "+param.get("zw");
			}
			
		
		
		 SQLQuery query = getHibernateTemplate().getSessionFactory()
	                .getCurrentSession().createSQLQuery(sql);
	        
	        
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
         

        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }


        return (List<Map<String, Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> selectNoticeByNoticeNo(String noticeNo) {
		String sql = "select create_time,create_user,title,content,type,grade,releaseUnit,cityes,stores,zw,noticeNo,id,filePath,fileName,filePath2,fileName2,filePath3,fileName3 from t_notice where status=0 and noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		//获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public List<Map<String, Object>> getCityOfZb() {
		String sql="select * from    t_dist_citycode a where a.status=0 ";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }
        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getCityOfCs(Long userId) {
		String sql="select * from   t_dist_city a where a.status=0  and pk_userid="+userId;
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getStoreByCity(String cityCode) {
		String whereStr="";
		if(cityCode!=null&&!"".equals(cityCode)){
			whereStr = " where citycode in ("+cityCode+")";
		}
		String sql="select b.store_id,b.name,b.city_name from (select * from t_dist_citycode "+whereStr+") as a inner join t_store as b on a.cityname=b.city_name ";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllZw() {
		String sql=" select a.zw,1 as id from (select DISTINCT zw from t_humanresources union ALL select DISTINCT zw from t_storekeeper) as a ";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }


        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public int deleteNotice(String noticeNo) {
		String sql="update t_notice set status=1 where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	 
}
