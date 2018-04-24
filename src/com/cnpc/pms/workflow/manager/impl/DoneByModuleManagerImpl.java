package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.entity.DoneByModule;
import com.cnpc.pms.workflow.manager.DoneByModuleManager;

public class DoneByModuleManagerImpl extends BaseManagerImpl implements DoneByModuleManager{

	public List<DoneByModule> getDoneByModuleByUserId() {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		Long userid = (Long) sessionData.get("userId");
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper.getBean(WFInstanceDAO.class.getName());
		List<DoneByModule> list=instanceDAO.getDoneByModuleByUserId(userid);
		return list;
	}

}
