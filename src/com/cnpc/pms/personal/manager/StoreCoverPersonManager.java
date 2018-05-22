package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

public interface StoreCoverPersonManager extends IManager {
	
	public Map<String, Object> saveStoreCoverPerson(List<Map<String,Object>> mapParam1,List<Map<String,Object>> mapParam2);
	
	public Map<String,Object> getNewStoreCoverPerson();
}
