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
        String hql = " select * from t_report_filedown where username = '"+username+"' and table_logic = '"+tableLogic+"' ORDER BY create_time DESC LIMIT 20 ";
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

}
