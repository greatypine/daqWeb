package com.cnpc.pms.costStatistics.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.dao.impl
 * @Description: 成本控制
 * @Author: gbl
 * @CreateDate: 2018/8/13 11:20
 */
public class CostStatisticsDaoImpl extends BaseDAOHibernate implements CostStatisticsDao {
    @Override
    public List<Map<String, Object>> queryCostLabor(String storeNo, String storeName, Integer year, Integer month) {
        String sql="select *  from t_cost_labor where status=0";
        if(storeNo!=null&&!"".equals(storeNo)){
            sql+=" and storeNo like '%"+storeNo+"%'";
        }

        if(storeName!=null&&!"".equals(storeName)){
            sql+=" and storeName like '%"+storeName+"%'";
        }

        if(year!=null){
            sql+=" and year="+year;
        }

        if(month!=null){
            sql+=" and month="+month;
        }

        sql+=" order by month";
         List<Map<String,Object>> list = null;
        try{
            SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }
}
