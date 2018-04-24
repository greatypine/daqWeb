package com.cnpc.pms.workflow.wfinstance.manager.impl;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.wfinstance.manager.WFSystemManager;

public class WFSystemManagerImpl extends BaseManagerImpl implements
		WFSystemManager {

	/**
	 * 获取当前登录用户号
	 * 
	 * @return
	 */

	public String getCurrentLogin() {
		User sessionUser = (User) SessionManager.getUserSession()
				.getSessionData().get("user");
		if (sessionUser != null) {
			return sessionUser.getCode();
		} else {
			return "";
		}
	}

	/**
	 * 删除流程实例数据
	 */
	public boolean deleteFlowInstance(WFFlowInstance flowInstance) {
		// TODO Auto-generated method stub
		WFFlowInstanceManager flowInstanceManager = (WFFlowInstanceManager) SpringHelper
				.getBean("WFFlowInstanceManager");
		return flowInstanceManager.deleteFlowInstance(flowInstance);
	}

	/**
	 * 删除一条流程实例,按照Id进行删除，系统维护使用
	 * 
	 * @param wFFlowInstance
	 * @return
	 */
	public Long deleteFlowInstanceById(Long id) {
		WFFlowInstanceManager inst = (WFFlowInstanceManager)SpringHelper.getBean
			(WFManagerConst.WF_INSTANCE);
		return inst.deleteFlowInstanceById(id);
	}
}
