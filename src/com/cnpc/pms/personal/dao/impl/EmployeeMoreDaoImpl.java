package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.personal.dao.EmployeeMoreInfoDao;
import com.cnpc.pms.personal.entity.House;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.personal.dao.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/7/10 14:36
 */
public class EmployeeMoreDaoImpl extends BaseDAOHibernate implements EmployeeMoreInfoDao {


    @Override
    public List<Map<String, Object>> queryHumanresource() {
        String sql=" select employee_no,topostdate from t_humanresources where humanstatus=1";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryStoreKepeer() {
        String sql=" select employee_no,topostdate from t_storekeeper where humanstatus=1";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String,Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
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
