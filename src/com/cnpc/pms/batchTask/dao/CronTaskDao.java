package com.cnpc.pms.batchTask.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;

public interface CronTaskDao extends IDAO{
	
	public List<Map<String, Object>> queryCronTaskResult(String whereStr, PageInfo pageInfo);
	
}
