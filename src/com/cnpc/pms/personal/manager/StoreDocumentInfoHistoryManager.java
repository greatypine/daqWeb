package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.StoreDocumentInfo;

public interface StoreDocumentInfoHistoryManager extends IManager {
	void InsertStoreDocumentInfoHistory(StoreDocumentInfo storeDocumentInfo);
}
