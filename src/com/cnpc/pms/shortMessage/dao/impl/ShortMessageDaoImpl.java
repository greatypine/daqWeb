package com.cnpc.pms.shortMessage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.shortMessage.dao.ShortMessageDao;
import com.cnpc.pms.shortMessage.manager.ShortMessageManager;

public class ShortMessageDaoImpl extends BaseDAOHibernate implements ShortMessageDao{

	@Override
	public List<Map<String, Object>> selectSMSUserGroup() {
		String sql="select * from t_sms_usergroup where status=0";
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
	public List<Map<String, Object>> selectSMSUserGroupUser(String userGroup) {
		String sql="select mobilePhone,userCode,userGroupCode,userName,inviteCode from t_sms_usergroup_user where mobilePhone is not null and mobilePhone!='' and userGroupCode in ("+userGroup+")";
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
	public List<Map<String, Object>> getOfflineEmployee(String whereStr, PageInfo pageInfo) {
		
		String sql="select name,phone as mobilephone,employee_no,inviteCode from t_humanresources where humanstatus=1 and phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!='' "+whereStr;
				
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
	public List<Map<String, Object>> getOnlineEmployee(String whereStr, PageInfo pageInfo) {
		String sql="select name,phone as mobilephone,employee_no,inviteCode from t_online_humanresources where phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!='' "+whereStr;
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
	public List<Map<String, Object>> getCustomer(String whereStr, PageInfo pageInfo) {
		String sql="select id,name,mobilephone,id as employee_no from t_customer where mobilephone is not null "+whereStr;
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
	public List<Map<String, Object>> getAllOfflineEmployee() {
		String sql="select name,phone as mobilephone,employee_no,inviteCode from t_humanresources where humanstatus=1 and phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!='' ";
				
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllOnlineEmployee() {
		String sql="select name,phone as mobilephone,employee_no,inviteCode from t_online_humanresources where phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!=''";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	@Override
	public List<Map<String, Object>> getAllCustomer() {
		String sql="select id,name,mobilephone,id as employee_no from t_customer where mobilephone is not null ";
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
    public List<Map<String, Object>> getOutSider(String whereStr, PageInfo pageInfo) {
        String sql= "select * from t_external_humanresources where status=0 "+whereStr;
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
    public List<Map<String, Object>> getAllOutSider() {
        String sql="select * from t_external_humanresources where phone is not null and inviteCode is not null";
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
	public List<Map<String, Object>> getStoreKeeperEmployee(String whereStr, PageInfo pageInfo) {
		String sql= " select name,phone as mobilephone,employee_no,inviteCode from t_storekeeper where humanstatus=1 and phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!='' "+whereStr;
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
	public List<Map<String, Object>> getStoreKeeperEmployee() {
		String sql=" select name,phone as mobilephone,employee_no,inviteCode from t_storekeeper where humanstatus=1 and phone is not null and phone!='' and cardnumber is not null and cardnumber!='' and inviteCode is not null and inviteCode!='' ";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

	

}
