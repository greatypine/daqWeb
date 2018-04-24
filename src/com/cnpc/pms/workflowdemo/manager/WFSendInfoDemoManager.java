package com.cnpc.pms.workflowdemo.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFSendInfo;
import com.cnpc.pms.workflowdemo.entity.WFSendInfoDemo;

public interface WFSendInfoDemoManager extends IManager {
	
	/**
	 * 保存下达表单
	 */
	public WFSendInfoDemo addSendInfoDemo(WFSendInfoDemo demo);
	/**
	 * 启动下达实例
	 */
	public WFSendInfo startWFSendInfo(WFSendInfo wf);
	/**
	 * 更新下达信息
	 */
	public Boolean updateWFSendInfo(WFSendInfo wf);
	/**
	 * 结束下达实例
	 */
	public Boolean submitWFSendInfo(Long id);
	/**
	 * 根据表单ID获取表单信息
	 */
	public WFSendInfoDemo querySendInfoDemo(Long id);
}
