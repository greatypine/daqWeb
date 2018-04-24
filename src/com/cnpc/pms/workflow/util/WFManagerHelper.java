package com.cnpc.pms.workflow.util;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.manager.WFCopyInfoManager;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFFlowManager;
import com.cnpc.pms.workflow.manager.WFFlowStepManager;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;
import com.cnpc.pms.workflow.manager.WFInstanceTransitionManager;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;

public class WFManagerHelper {

	/**
	 * 获取WFModuleManager
	 * 
	 * @return
	 */
	public static WFModuleManager getWFModuleManager() {
		WFModuleManager manager = (WFModuleManager) SpringHelper
				.getBean(WFManagerConst.WF_MODULEMANAGER);
		return manager;
	}

	/**
	 * 获取WFFlowManager
	 * 
	 * @return
	 */
	public static WFFlowManager getWFFlowManager() {
		WFFlowManager flowManager = (WFFlowManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOW);
		return flowManager;
	}

	/**
	 * 获取WFFlowInstanceManager
	 * 
	 * @return
	 */
	public static WFFlowInstanceManager getWFFlowInstanceManager() {
		WFFlowInstanceManager flowInstanceManager = (WFFlowInstanceManager) SpringHelper
				.getBean(WFManagerConst.WF_INSTANCE);
		return flowInstanceManager;
	}
	/**
	 * 获取WFStepInstanceManager
	 * @return
	 */
	public static WFStepInstanceManager getWFStepInstanceManager(){
		return (WFStepInstanceManager) SpringHelper
		.getBean(WFManagerConst.WF_INSTANCESTEP);
	}
	
	/**
	 * 获取WFInstanceTransitionManager
	 * @return
	 */
	public static WFInstanceTransitionManager getWFInstanceTransitionManager(){
		return (WFInstanceTransitionManager) SpringHelper
		.getBean(WFManagerConst.WF_INSTANCETRANSITION);
	}
	
	/**
	 * 获取WFFlowStepManager
	 * @return
	 */
	public static WFFlowStepManager getWFFlowStepManager(){
		return (WFFlowStepManager) SpringHelper
		.getBean(WFManagerConst.WF_FLOWSTEP);
	}
	
	/**
	 * 获取WFInstanceRecordManager
	 * @return
	 */
	public static WFInstanceRecordManager getWFInstanceRecordManager(){
		return (WFInstanceRecordManager) SpringHelper
		.getBean("WFInstanceRecordManager");
	}
	
	/**
	 * 获取WFCopyInfoManager
	 * @return
	 */
	public static WFCopyInfoManager getWFCopyInfoManager(){
		return (WFCopyInfoManager)SpringHelper.getBean(WFManagerConst.WF_COPYINFOMANAGER);
	}
}
