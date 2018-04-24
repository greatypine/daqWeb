package com.cnpc.pms.workflowdemo.manager.impl;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFSendInfo;
import com.cnpc.pms.workflow.manager.WFSendInfoManager;
import com.cnpc.pms.workflowdemo.entity.WFSendInfoDemo;
import com.cnpc.pms.workflowdemo.manager.WFSendInfoDemoManager;

public class WFSendInfoDemoManagerImpl extends BaseManagerImpl implements
		WFSendInfoDemoManager {

	public WFSendInfoDemo addSendInfoDemo(WFSendInfoDemo demo) {
		// TODO Auto-generated method stub
		super.saveObject(demo);
		return demo;
	}

	public WFSendInfo startWFSendInfo(WFSendInfo wf) {
		// TODO Auto-generated method stub
		WFSendInfoManager manager = (WFSendInfoManager) SpringHelper
				.getBean("WFSendInfoManager");
		manager.addWFSendInfo(wf);
		return wf;
	}

	public Boolean submitWFSendInfo(Long id) {
		// TODO Auto-generated method stub
		WFSendInfoManager manager = (WFSendInfoManager) SpringHelper
				.getBean("WFSendInfoManager");
		manager.submitWFSendInfo(id);
		return true;
	}

	public Boolean updateWFSendInfo(WFSendInfo wf) {
		// TODO Auto-generated method stub
		WFSendInfoManager manager = (WFSendInfoManager) SpringHelper
				.getBean("WFSendInfoManager");
		manager.updateWFSendInfo(wf);
		return true;
	}

	public WFSendInfoDemo querySendInfoDemo(Long id) {
		// TODO Auto-generated method stub
		WFSendInfoDemo wf = (WFSendInfoDemo) super.getObject(id);
		return wf;
	}

}
