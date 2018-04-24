package com.cnpc.pms.workflow.wfflowdefine.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlow;

/**
 * 这个接口是为外部组件查询工作流定义的接口,根据ID来返回一个复合的工作流定义对象<br>
 * 实际上应该将所有和前台界面交互的接口统一抽取出来进行调用.
 * @author liujunsong
 *
 */
public interface WFFlowDefineManager extends IManager {
	/**
	 * 根据ID来检索工作流定义信息.<br>
	 * 这个接口目前暂未使用.<br>
	 * @param id
	 * @return
	 */
	public WFFlow queryFlow(Long id);
}
