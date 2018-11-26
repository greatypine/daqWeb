package com.cnpc.pms.personal.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;

public interface RegOnLineHumanresourcesManager extends IManager{
	
	public Map<String, Object> queryRegOnLineHumanList(QueryConditions conditions);
}
