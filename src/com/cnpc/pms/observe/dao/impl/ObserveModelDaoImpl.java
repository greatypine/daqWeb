package com.cnpc.pms.observe.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.observe.dao.ObserveModelDao;
import com.cnpc.pms.observe.entity.CheckDetails;
import com.cnpc.pms.observe.entity.ObserveModel;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
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

    @Override
    public List<ObserveModel> getObserveModel() {
        String find_sql = "select * from t_observe_model";
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
        List<ObserveModel> list = query.addEntity(ObserveModel.class).list();
        return list;
    }

    @Override
    public List<CheckDetails> getObserveCheckdetailsByModelId(Long model_id) {
        String find_sql = "select (CASE when status = 1 then '是' else '否' end) as is_used,model_id,observe_content,(select model_name from t_observe_model where id = "+model_id+") as model_name from t_observe_check_details where model_id = "+model_id;
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(find_sql);
        List<CheckDetails> list = query.addEntity(CheckDetails.class).list();
        return list;
    }


    @Override
    public List<Map<String, Object>> queryObserveParameterList(Integer status,Long id, PageInfo pageInfo) {
        List<Map<String, Object>> lst_result = new ArrayList<Map<String, Object>>();
        // sql查询列，用于页面展示所有的数据
        String find_sql = "select (CASE when status = 1 then '是' else '否' end) as is_used,model_id,observe_content,id,(select model_name from t_observe_model where id = "+id+") as model_name from t_observe_check_details where  model_id = "+id+" and status = "+status;
        if(id == 0){
            find_sql = "select (CASE when status = 1 then '是' else '否' end) as is_used,id,model_name from t_observe_model where status =" + status;
        }
        // SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(find_sql);

        if(pageInfo != null){
            // sql查询列，用于分页计算数据总数
            String str_count_sql = "select count(id) from t_observe_check_details where  model_id = "+id+" and status = "+status;
            if(id == 0) {
                str_count_sql = "select count(id) from t_observe_model where status =" + status;
            }

                System.out.println(str_count_sql);

            // 查询数据量对象
            SQLQuery countQuery = getHibernateTemplate().getSessionFactory().getCurrentSession()
                    .createSQLQuery(str_count_sql);
            pageInfo.setTotalRecords(Integer.valueOf(countQuery.list().get(0).toString()));
            // 获得查询数据
            List<?> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                    .setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
                    .setMaxResults(pageInfo.getRecordsPerPage()).list();

            // 如果没有数据返回
            if (lst_data == null || lst_data.size() == 0) {
                return lst_result;
            }
            // 转换成需要的数据形式
            for (Object obj_data : lst_data) {
                lst_result.add((Map<String, Object>) obj_data);
            }
        }else{
            lst_result =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }

        return lst_result;
    }
}
