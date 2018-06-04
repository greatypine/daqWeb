package com.cnpc.pms.notice.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.notice.dao.NoticeApplyDao;
import com.cnpc.pms.notice.entity.NoticeApplyCity;
import com.cnpc.pms.notice.entity.NoticeApplyJob;
import com.cnpc.pms.notice.entity.NoticeApplyStore;

public class NoticeApplyDaoImpl extends BaseDAOHibernate implements NoticeApplyDao{

	@Override
	public List<Map<String, Object>> selectNoticeByNoticeNo(String noticeNo) {
		String sql = "select create_time,create_user,title,content,type,grade,releaseUnit,checkUserName,checkStatus,cityes,stores,zw,noticeNo,id,filePath,fileName from t_notice_apply where status=0 and noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		//获得查询数据
		List<Map<String, Object>> lst_data = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return lst_data;
	}

	@Override
	public int deleteNoticeApply(String noticeNo) {
		String sql="update t_notice_apply set status=1 where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public int saveNoticeApplyCity(NoticeApplyCity noticeApplyCity) {
		String sql=" insert into t_notice_apply_city(noticeNo,cityCode,cityName) values('"+noticeApplyCity.getNoticeNo()+"','"+noticeApplyCity.getCityCode()+"','"+noticeApplyCity.getCityName()+"')";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate(); 
	}

	@Override
	public int saveNoticeApplyStore(NoticeApplyStore noticeApplyStore) {
		String sql=" insert into t_notice_apply_store(noticeNo,storeId,storeNo,StoreName) values('"+noticeApplyStore.getNoticeNo()+"','"+noticeApplyStore.getStoreId()+"','"+noticeApplyStore.getStoreNo()+"','"+noticeApplyStore.getStoreName()+"')";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate(); 
	}

	@Override
	public int saveNoticeApplyJob(NoticeApplyJob noticeApplyJob) {
		String sql=" insert into t_notice_apply_job(noticeNo,job) values('"+noticeApplyJob.getNoticeNo()+"','"+noticeApplyJob.getJob()+"')";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

	@Override
	public int deleteNoticeApplyCity(String noticeNo) {
		String sql=" delete from  t_notice_apply_city where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate(); 
	}

	@Override
	public int deleteNoticeApplyStore(String noticeNo) {
		String sql=" delete from  t_notice_apply_store where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate(); 
	}

	@Override
	public int deleteNoticeApplyJob(String noticeNo) {
		String sql=" delete from  t_notice_apply_job where noticeNo='"+noticeNo+"'";
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}

}
