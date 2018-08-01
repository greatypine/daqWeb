package com.cnpc.pms.employeeMoreInfo.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.employeeMoreInfo.dao.EmployeeMoreInfoDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.personal.dao.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/7/10 14:36
 */
public class EmployeeMoreInfoDaoImpl extends BaseDAOHibernate implements EmployeeMoreInfoDao {


    @Override
    public List<Map<String, Object>> queryValidHumanresource() {
        String sql=" select employee_no,topostdate from t_humanresources where humanstatus=1";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
            Query query = session.createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> selectValidStoreKepeer() {
        String sql=" select employee_no,topostdate from t_storekeeper where humanstatus=1";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        try{
            Query query = session.createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return list;
    }

    @Override
    public void updateEmployeeWorkingAge(String employeeNo, Integer year, Integer month, Integer day) {
        String sql ="update t_employee_more_info set workingAge_year="+year+",workingAge_month="+month+",workingAge_day="+day+" where employeeNo='"+employeeNo+"'";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    public List<Map<String, Object>> queryEmployeeMoveDistance(String employeeNo) {
        String sql="select FORMAT(moveDistance,2) as moveDistance  from t_employee_more_info where employeeNo='"+employeeNo+"' limit 1";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }


}
