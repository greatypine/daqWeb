package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

public interface StoreActivitiesManager extends IManager {
	
	public Map<String, Object> saveStoreActivities(List<Map<String,Object>> mapParam);
	
	public Map<String,Object> getNewStoreActivtiesInfo();
}
