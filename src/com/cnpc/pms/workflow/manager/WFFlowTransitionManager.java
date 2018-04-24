package com.cnpc.pms.workflow.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowTransition;

public interface WFFlowTransitionManager extends IManager {
	/**
	 * 增加流程转换
	 * 
	 * @param wFTransition
	 * @return
	 */
	public WFFlowTransition addTransition(WFFlowTransition wFTransition);

	/**
	 * 删除流程转换
	 * 
	 * @param wFTransition
	 * @return
	 */
	public boolean deleteTransition(WFFlowTransition wFTransition);

	/**
	 * 保存流程转换
	 * 
	 * @param wFTransition
	 * @return
	 */
	public WFFlowTransition saveTransition(WFFlowTransition wFTransition);

	/**
	 * 通过id获取流程转换
	 * 
	 * @param id
	 * @return
	 */
	public WFFlowTransition queryTransition(Long id);

	/**
	 * 通过id删除流程转换
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteTransitions(Long id);

	/**
	 * 通过流程id、开始步骤id获取转换
	 * 
	 * @param flowId
	 * @param startStepId
	 * @return
	 */
	public WFFlowTransition getTransitionByCondition(Long flowInstanceId,
			Long startStepId);
}
