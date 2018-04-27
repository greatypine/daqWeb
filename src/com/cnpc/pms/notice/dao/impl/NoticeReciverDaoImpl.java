package com.cnpc.pms.notice.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.notice.dao.NoticeReciverDao;

public class NoticeReciverDaoImpl extends BaseDAOHibernate implements NoticeReciverDao {

	@Override
	public List<Map<String, Object>> selectNoticeReciver(String employeeNo) {
		String sql="select * from t_notice_reciver where employeeNo='"+employeeNo+"'";
		
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        
	    //获得查询数据
	    List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
	    //如果没有数据返回
	    if(lst_data == null || lst_data.size() == 0){
	        return new ArrayList<Map<String, Object>>();
	    }
	    return (List<Map<String, Object>>)lst_data;
	}

	@Override
	public int updateNoticeReciverIsRead(String noticeNo, String employeeNo) {
		String sql = "update t_notice_reciver set isRead=1,readDate=NOW() where noticeNo='"+noticeNo+"' and employeeNo='"+employeeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		try {
			int updateResult = query.executeUpdate();
			return updateResult;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
