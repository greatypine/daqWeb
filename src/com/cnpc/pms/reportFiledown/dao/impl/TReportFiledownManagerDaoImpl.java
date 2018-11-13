package com.cnpc.pms.reportFiledown.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.reportFiledown.dao.TReportFiledownManagerDao;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.util.List;

/**
 * @author shijunhui
 *
 */
public class TReportFiledownManagerDaoImpl extends BaseDAOHibernate implements TReportFiledownManagerDao {

    @Override
    public List<TReportFiledown> getReportDown(String username,String tableLogic) {
        // TODO Auto-generated method stub
        String hql = " select * from t_report_filedown where username = '"+username+"' ";
               if(!tableLogic.equals("null")){
                   hql = hql + " and table_logic = '"+tableLogic+"' ";
               }
                hql = hql + " ORDER BY create_time DESC LIMIT 5";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
        List<TReportFiledown> list = query.addEntity(TReportFiledown.class).list();
        return list;
    }
    @Override
    public List<TReportFiledown> getReportDownList(String username) {
        // TODO Auto-generated method stub
        String hql = " select * from t_report_filedown where username = '"+username+"' ORDER BY create_time DESC";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql);
        List<TReportFiledown> list = query.addEntity(TReportFiledown.class).list();
        return list;
    }
    @Override
    public void getUpdateReportFileDowns(TReportFiledown tReportFiledown){
        String  sql="update t_report_filedown set down_times="+tReportFiledown.getDownTimes()+",down_time = now()  where id="+tReportFiledown.getId();
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        query.executeUpdate();
    }

    @Override
    public void getDeleteFileDowns(int id){
        String  sql=" DELETE FROM t_report_filedown where id = '"+ id +"' ";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
        query.executeUpdate();
    }

}
