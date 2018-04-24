package com.cnpc.pms.workflow.wfinstance.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowInstance;

/**
 * 工作流部分的系统管理功能<br>
 * 实现流程实例的界面直接删除功能<br>
 * 这一方法不是应用调用的方法<br>
 * 
 * @author liujunsong
 * 
 */
public interface WFSystemManager extends IManager{

	//--------------下面两个接口是为系统运维人员提供单独删除流程实例的方法----------//
	//--------------应用程序不可调用这两个方法 ------------------------------------//
	//--------------正式系统可能会删除这两个接口 ----------------------------------//
	/**
	 * 获取当前登录用户号
	 * @return
	 */
	public String getCurrentLogin();
	
	/**
	 * 级联删除流程实例记录
	 * 
	 * @param flowInstance
	 * @return
	 */
	public boolean deleteFlowInstance(WFFlowInstance flowInstance);
	
	/**
	 * 删除一条流程实例,按照Id进行删除，系统维护使用
	 * 
	 * @param wFFlowInstance
	 * @return
	 */
	public Long deleteFlowInstanceById(Long id);
	//--------------系统运维定义的接口结束 ----------------------------------------//
}
