package com.cnpc.pms.batchTask.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.batchTask.dao.CronTaskDao;
import com.cnpc.pms.batchTask.manager.CronTaskManager;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.entity.CronTask;


public class CronTaskManagerImpl extends BizBaseCommonManager implements CronTaskManager{
	
	@Override
	public Map<String, Object> queryCronTaskResult(QueryConditions conditions) {
		CronTaskDao cronTaskDao = (CronTaskDao)SpringHelper.getBean(CronTaskDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = conditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
		List<Map<String, Object>> mapWhereList = conditions.getConditions();
		Map<String, Object> classify_where = mapWhereList.get(0);
		Map<String, Object> datatable_where = mapWhereList.get(1);
		Map<String, Object> status_where = mapWhereList.get(2);
		if ("classify".equals(classify_where.get("key")) && null != classify_where.get("value")
				&& !"".equals(classify_where.get("value"))) {
			sb_where.append(" AND classify like '").append(classify_where.get("value")).append("'");
		}
		if ("datatable".equals(datatable_where.get("key")) && null != datatable_where.get("value")
				&& !"".equals(datatable_where.get("value"))) {
			sb_where.append(" AND datatable like '").append(datatable_where.get("value")).append("'");
		}
		if ("status".equals(status_where.get("key")) && null != status_where.get("value")
				&& !"".equals(status_where.get("value"))) {
			sb_where.append(" AND status like '").append(status_where.get("value")).append("'");
		}
		
		try {
			 map_result.put("data",cronTaskDao.queryCronTaskResult(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}
	
	@Override
	public CronTask saveOrUpdateCronTask(CronTask dsTaskList) {
		CronTask crontask = null;
		if (dsTaskList.getId() != null) {
			crontask = getDsTaskListById(dsTaskList.getId());
		} else {
			crontask = new CronTask();
		}
		crontask.setTasksn(dsTaskList.getTasksn());
		crontask.setClassify(dsTaskList.getClassify());
		crontask.setDescription(dsTaskList.getDescription());
		crontask.setRuntime(dsTaskList.getRuntime());
		crontask.setFrequency(dsTaskList.getFrequency());
		crontask.setDatatable(dsTaskList.getDatatable());
		crontask.setTaskmethod(dsTaskList.getTaskmethod());
		preObject(crontask);
		CronTaskManager cronTaskManager = (CronTaskManager) SpringHelper.getBean("cronTaskManager");
		if (dsTaskList.getId() == null) {
			this.insertDsTaskList(crontask);
		}else{
			cronTaskManager.saveObject(crontask);
		}
		return crontask;
	}	
	
	@Override
	public void insertDsTaskList(CronTask saveDsTaskList) {
		CronTaskManager cronTaskManager=(CronTaskManager)SpringHelper.getBean("cronTaskManager");
		CronTask dsTaskList = new CronTask();
		dsTaskList.setTasksn(saveDsTaskList.getTasksn());
		dsTaskList.setClassify(saveDsTaskList.getClassify());
		dsTaskList.setDescription(saveDsTaskList.getDescription());
		dsTaskList.setRuntime(saveDsTaskList.getRuntime());
		dsTaskList.setFrequency(saveDsTaskList.getFrequency());
		dsTaskList.setDatatable(saveDsTaskList.getDatatable());
		dsTaskList.setTaskmethod(saveDsTaskList.getTaskmethod());
		preObject(saveDsTaskList);
		cronTaskManager.saveObject(dsTaskList);
	}
	
	@Override
	public CronTask getDsTaskListById(Long id) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("id", id));
		if (list != null && list.size() > 0) {
				CronTask dsTaskList =  (CronTask)list.get(0);
			 return dsTaskList;
		}
		return null;
	}
	
	
}
