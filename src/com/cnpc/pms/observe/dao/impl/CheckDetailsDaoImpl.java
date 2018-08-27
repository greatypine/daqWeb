package com.cnpc.pms.observe.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.observe.dao.CheckDetailsDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/23.
 */
public class CheckDetailsDaoImpl  extends BaseDAOHibernate implements CheckDetailsDao{

    @Override
    public List<Map<String, Object>> getCheckDetailsById(Long id) {
        String sql="select model.model_name,details.observe_content,details.model_id,details.id from t_observe_model model INNER JOIN t_observe_check_details details ON (model.id = details.model_id) where details.id = "+id;
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        return lst_data;
    }
}
