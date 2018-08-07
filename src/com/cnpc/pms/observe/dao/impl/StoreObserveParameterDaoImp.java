package com.cnpc.pms.observe.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.observe.dao.StoreObserveParameterDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public class StoreObserveParameterDaoImp extends BaseDAOHibernate implements StoreObserveParameterDao {

    @Override
    public List<Map<String, Object>> queryObserveParameterList(Long store_id,String observe_month){
        String sql="select details.model_id,model.model_name,details.observe_content,parameter.points_deduction_description,parameter.content_score,parameter.score_empno_empname,parameter.check_details_id as id from t_observe_parameter parameter " +
                "LEFT JOIN t_observe_check_details details ON (parameter.check_details_id = details.id) INNER JOIN t_observe_model model ON (model.id = details.model_id) where " +
                "parameter.store_id = "+store_id+" and parameter.observe_month = '"+observe_month+"'";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return lst_data;
    }

    @Override
    public List<Map<String, Object>> queryObserveParameterListByStoreNo(String storeno,String observe_month){
        List<Map<String, Object>> lst_data = new ArrayList<Map<String, Object>>();
        String sql="select details.model_id,model.model_name,details.observe_content,parameter.points_deduction_description,parameter.content_score,parameter.score_empno_empname from t_observe_parameter parameter " +
                "LEFT JOIN t_observe_check_details details ON (parameter.check_details_id = details.id) INNER JOIN t_observe_model model ON (model.id = details.model_id) where " +
                "parameter.storeno = '"+storeno+"' and parameter.observe_month = '"+observe_month+"'";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        if(lst_data == null || lst_data.size() <= 0  ){
            String sql1="select model.model_name,details.observe_content,details.model_id,details.id from t_observe_model model INNER JOIN t_observe_check_details details ON (model.id = details.model_id)";
            SQLQuery query1 = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql1);
            lst_data = query1.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }
        return lst_data;
    }

}
