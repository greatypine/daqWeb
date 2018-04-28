package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.StoreDynamic;

public interface StoreHistoryManager extends IManager {
	void insertStoreHistory(StoreDynamic storeDynamic);
}
