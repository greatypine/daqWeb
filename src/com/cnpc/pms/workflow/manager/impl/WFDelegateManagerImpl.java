package com.cnpc.pms.workflow.manager.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.workflow.entity.WFDelegate;
import com.cnpc.pms.workflow.manager.WFDelegateManager;

public class WFDelegateManagerImpl extends BaseManagerImpl implements
		WFDelegateManager {

	public WFDelegate addWFDelegate(WFDelegate obj) {
		super.saveObject(obj);
		return null;
	}

	public Boolean deleteWFDelegate(Long id) {
		super.removeObjectById(id);
		return true;
	}

	public WFDelegate queryWFDelegate(Long id) {
		return (WFDelegate) super.getObject(id);
	}

	public WFDelegate saveWFDelegate(WFDelegate obj) {
		WFDelegate dbObj = null;
		dbObj = (WFDelegate) super.getObject(obj.getId());
		if (dbObj == null) {
			dbObj = obj;
		} else {
			BeanUtils.copyProperties(obj, dbObj,
					new String[] { "id", "version" });
		}
		super.saveObject(dbObj);
		return obj;
	}

	public User getUser() {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		User user = (User) sessionData.get("user");
		System.out.println(user.getName());
		System.out.println(user.getId());
		return user;
	}

}
