package com.cnpc.pms.notice.dao.impl;

import org.hibernate.SQLQuery;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.notice.dao.NoticeReciverApplyDao;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.manager.NoticeReciverManager;

public class NoticeReciverApplyDaoImpl extends BaseDAOHibernate implements NoticeReciverApplyDao{

	@Override
	public int deleteNoticeReciverApply(String noticeNo) {
		
		String sql="update t_notice_reciver_apply set status=1 where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

}
