package com.cnpc.pms.batchTask.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.batchTask.dao.CronTaskDao;

public class CronTaskDaoImpl extends BaseDAOHibernate implements CronTaskDao{

	
	@Override
	public List<Map<String, Object>> queryCronTaskResult(String whereStr, PageInfo pageInfo) {
	
		String sql="select id,tasksn,classify,description,runtime,frequency,datatable,taskmethod,status from ds_tasklist where 1=1 "+whereStr;
		SQLQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
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
