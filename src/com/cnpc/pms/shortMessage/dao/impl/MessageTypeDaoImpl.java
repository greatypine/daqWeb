package com.cnpc.pms.shortMessage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.shortMessage.dao.MessageTypeDao;

public class MessageTypeDaoImpl extends BaseDAOHibernate implements MessageTypeDao{

	@Override
	public List<Map<String, Object>> selectMessageType(String whereStr, PageInfo pageInfo) {
		
		String sql="select a.id,a.name,a.code,a.remark,b.actionCode,b.remark as description from t_message_type a LEFT JOIN t_message_action b on a.code = b.messageTypeCode  where a.status=0 "+whereStr;
		
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
	public List<Map<String, Object>> selectMessageType(Long id) {
		
		return null;
	}

	@Override
	public List<Map<String, Object>> selectMessageTypeById(Long id) {
		String sql = "select a.id,a.name,a.code,a.remark,b.actionCode,b.remark as description from t_message_type a LEFT JOIN t_message_action b on a.code = b.messageTypeCode  where a.status=0 and a.id= "+id;
		
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
                
        
		//获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}

}
