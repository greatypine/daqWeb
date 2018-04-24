package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.entity.FinishedByModule;
import com.cnpc.pms.workflow.manager.FinishedByModuleManager;

public class FinishedByModuleManagerImpl extends BaseManagerImpl implements FinishedByModuleManager{

	public List<FinishedByModule> getFinishedByModuleByUserId() {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		Long userid = (Long) sessionData.get("userId");
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper.getBean(WFInstanceDAO.class.getName());
		List<FinishedByModule> list=instanceDAO.getFinishByModuleByUserId(userid);
		return list;
	}

}
