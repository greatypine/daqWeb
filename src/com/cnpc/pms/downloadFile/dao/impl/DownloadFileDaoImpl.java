package com.cnpc.pms.downloadFile.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.downloadFile.dao.DownloadFileDao;
import com.cnpc.pms.downloadFile.entity.DownLoadDto;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadFileDaoImpl  extends BaseDAOHibernate implements DownloadFileDao {


    @Override
    public Map<String, Object> getDownloadFile(DownLoadDto downloadDto, PageInfo pageInfo) {
        List<?> list = null;
        Map<String, Object> map_result = new HashMap<String, Object>();
        DownLoadDto df = downloadDto;
        String sql = "select file_name,file_insert_name from df_daily_monthly_file where file_tab='1' ";
        if(df!=null&&df.getCityCode()!=null){
            sql += " and city_tab='"+df.getCityCode()+"'";
        }
        if(df!=null&&df.getTarget()!=null){
            sql += " and file_type='"+df.getTarget()+"'";
        }else{
            sql +=" and file_type='月报'";
        }
        Query query = this.getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);

        if (pageInfo != null) {
            String sql_count = "SELECT count(1) from (" + sql + ") c ";
            Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

            pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                    .setFirstResult(
                            pageInfo.getRecordsPerPage()
                                    * (pageInfo.getCurrentPage() - 1))
                    .setMaxResults(pageInfo.getRecordsPerPage()).list();


            Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
            map_result.put("pageinfo", pageInfo);
            map_result.put("total_pages", total_pages);
        } else {
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }

        map_result.put("downfile", list);
        return map_result;
    }

    @Override
    public Map<String, Object> getOperDownloadFile(DownLoadDto downloadDto, PageInfo pageInfo) {
        List<?> list = null;
        Map<String, Object> map_result = new HashMap<String, Object>();
        DownLoadDto df = downloadDto;
        String sql = "select file_name,file_insert_name from df_daily_monthly_file where file_tab='2' ";
        if(df!=null&&df.getTarget()!=null){
            sql += " and file_type='"+df.getTarget()+"'";
        }else{
            sql +=" and file_type='运营月报'";
        }
        Query query = this.getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createSQLQuery(sql);

        if (pageInfo != null) {
            String sql_count = "SELECT count(1) from (" + sql + ") c ";
            Query query_count = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql_count);

            pageInfo.setTotalRecords(Integer.valueOf(query_count.uniqueResult().toString()));

            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                    .setFirstResult(
                            pageInfo.getRecordsPerPage()
                                    * (pageInfo.getCurrentPage() - 1))
                    .setMaxResults(pageInfo.getRecordsPerPage()).list();


            Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
            map_result.put("pageinfo", pageInfo);
            map_result.put("total_pages", total_pages);
        } else {
            list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        }

        map_result.put("downfile", list);
        return map_result;
    }
}
