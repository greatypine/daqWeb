package com.cnpc.pms.shortMessage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.shortMessage.dao.ReplyMessageDao;

public class ReplyMessageDaoImpl extends BaseDAOHibernate implements ReplyMessageDao{

	@Override
	public List<Map<String, Object>> selectReplyMessage(String whereStr, PageInfo pageInfo) {
		String sql="SELECT a.id,concat(a.create_time,'') as create_time,a.content,a.phone,IFNULL(b.name,'') as messageType FROM t_reply_message  a LEFT JOIN t_message_type b on a.messageType = b.code where a.status=0 "+whereStr;
		sql= sql+" order by a.id desc";
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

}
