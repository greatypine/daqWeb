package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

public interface StoreResourcesDao {

	List<Map<String,Object>> findStoreResourcesByType(int i);

}
