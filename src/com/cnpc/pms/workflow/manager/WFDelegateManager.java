package com.cnpc.pms.workflow.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.workflow.entity.WFDelegate;

public interface WFDelegateManager extends IManager {
	public WFDelegate addWFDelegate(WFDelegate obj);

	public Boolean deleteWFDelegate(Long id);

	public WFDelegate queryWFDelegate(Long id);

	public WFDelegate saveWFDelegate(WFDelegate obj);

	public User getUser();
}
