package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

public interface StoreResourcesManager extends IManager{
	
	public Map<String,Object> saveStoreResource(List<Map<String, Object>> mapParam1,List<Map<String,Object>> mapParam2);
	
	
	public Map<String,Object> findStoreResourcesByType();

}
