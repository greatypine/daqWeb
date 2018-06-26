package com.cnpc.pms.shortMessage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.shortMessage.dao.MessageActionDao;

public class MessageActionDaoImpl extends BaseDAOHibernate implements MessageActionDao{

	@Override
	public List<Map<String, Object>> selectMessageAction(String messageType,String actionCode) {
		String sql= "select * from t_message_action where messageTypeCode!='"+messageType+"' and  actionCode in ("+actionCode+")";
		//SQL查询对象
        SQLQuery query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);
        
        
       
        //获得查询数据
        List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();


        //如果没有数据返回
        if(lst_data == null || lst_data.size() == 0){
            return new ArrayList<Map<String, Object>>();
        }

        return (List<Map<String,Object>>)lst_data;
	}
	
}
