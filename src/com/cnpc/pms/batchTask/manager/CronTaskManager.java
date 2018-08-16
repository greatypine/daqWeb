package com.cnpc.pms.batchTask.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.entity.CronTask;

public interface CronTaskManager extends IManager{
	
	public Map<String, Object> queryCronTaskResult(QueryConditions conditions);

	public CronTask saveOrUpdateCronTask(CronTask dsTaskList);

	public CronTask getDsTaskListById(Long id);

	public void insertDsTaskList(CronTask saveDsTaskList);

}
