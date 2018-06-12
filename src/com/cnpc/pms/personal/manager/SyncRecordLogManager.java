package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.SyncRecord;
import com.cnpc.pms.personal.entity.SyncRecordLog;

public interface SyncRecordLogManager extends IManager{

	public SyncRecordLog saveSyncRecordLog(SyncRecord syncRecord);
	
}
