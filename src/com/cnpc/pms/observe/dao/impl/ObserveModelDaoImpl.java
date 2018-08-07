package com.cnpc.pms.observe.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.observe.dao.ObserveModelDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/5.
 */
public class ObserveModelDaoImpl  extends BaseDAOHibernate implements ObserveModelDao{

    @Override
    public List<Map<String, Object>> getObserveList() {
        String sql="select model.model_name,details.observe_content,details.model_id,details.id from t_observe_model model INNER JOIN t_observe_check_details details ON (model.id = details.model_id)";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return lst_data;
    }

    @Override
    public List<Map<String, Object>> getObserveModelList() {
        String sql="select model.model_name,details.model_id,count(details.model_id) as count from t_observe_model model INNER JOIN t_observe_check_details details ON (model.id = details.model_id) GROUP BY details.model_id";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return lst_data;
    }

    @Override
    public List<String> getObserveContentList(){
        String find_sql = "select details.observe_content from t_observe_check_details details";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(find_sql);
        List<String> list = query.list();
        return list;
    }

}
