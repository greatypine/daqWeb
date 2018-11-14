package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.OnLineHumanresources;
import com.cnpc.pms.personal.entity.SyncRecord;

public interface OnLineHumanresourcesManager extends IManager{
	
	public OnLineHumanresources saveOnlineHuman(SyncRecord syncRecord);
}
