package com.cnpc.pms.workflow.wfflowdefine.manager.impl;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFFlow;

import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.util.WFManagerHelper;
import com.cnpc.pms.workflow.wfflowdefine.manager.WFFlowDefineManager;
import com.cnpc.pms.workflow.manager.WFFlowManager;

/**
 * 这个接口是为外部组件查询工作流定义的接口,根据ID来返回一个复合的工作流定义对象<br>
 * 实际上应该将所有和前台界面交互的接口统一抽取出来进行调用.
 * 
 * @author liujunsong
 * 
 */
public class WFFlowDefineManagerImpl extends BaseManagerImpl implements
		WFFlowDefineManager {
	/**
	 * 根据ID来检索工作流定义信息.
	 * 
	 * @param id
	 * @return
	 */
	public WFFlow queryFlow(Long id) {

		// 获取WFFlowManager
		WFFlowManager manager = WFManagerHelper.getWFFlowManager();
		// 根据id进行检索 
		WFFlow wFFlow = manager.queryFlow(id);
		// 返回
		return wFFlow;
	}

}
