package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.entity.StoreDocumentInfo;
import com.cnpc.pms.personal.entity.StoreDocumentInfoHistory;
import com.cnpc.pms.personal.manager.StoreDocumentInfoHistoryManager;

public class StoreDocumentInfoHistoryManagerImpl extends BizBaseCommonManager
		implements StoreDocumentInfoHistoryManager {

	@Override
	public void InsertStoreDocumentInfoHistory(StoreDocumentInfo storeDocumentInfo) {
		StoreDocumentInfoHistory storeDocumentInfoHistory = new StoreDocumentInfoHistory();
		storeDocumentInfoHistory.setSubmit_date(storeDocumentInfo.getSubmit_date());
		storeDocumentInfoHistory.setEnter_date(storeDocumentInfo.getEnter_date());
		storeDocumentInfoHistory.setEnter_end_date(storeDocumentInfo.getEnter_end_date());
		storeDocumentInfoHistory.setCard_content(storeDocumentInfo.getCard_content());
		storeDocumentInfoHistory.setStore_id(storeDocumentInfo.getStore_id());
		storeDocumentInfoHistory.setAudit_date(storeDocumentInfo.getAudit_date());
		storeDocumentInfoHistory.setAudit_status(storeDocumentInfo.getAudit_status());
		storeDocumentInfoHistory.setCreate_user(storeDocumentInfo.getCreate_user());
		storeDocumentInfoHistory.setCreate_time(storeDocumentInfo.getCreate_time());
		storeDocumentInfoHistory.setCreate_user_id(storeDocumentInfo.getCreate_user_id());
		storeDocumentInfoHistory.setUpdate_time(storeDocumentInfo.getUpdate_time());
		storeDocumentInfoHistory.setUpdate_user(storeDocumentInfo.getUpdate_user());
		storeDocumentInfoHistory.setUpdate_user_id(storeDocumentInfo.getUpdate_user_id());
		this.saveObject(storeDocumentInfoHistory);
	}

}
