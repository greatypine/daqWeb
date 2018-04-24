package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.entity.WFFlowTransition;
import com.cnpc.pms.workflow.entity.WFInstanceTransition;

/**
 * 工作流转换维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFInstanceTransitionManager extends IManager {
	/**
	 * 按照序号来检索转换实例
	 * 
	 * @param id
	 * @return
	 */
	public WFInstanceTransition readTransitionInstance(Long id);

	/**
	 * 利用流程转换的数据来插入转换实例表
	 * 
	 * @param trans
	 * @param convertMap
	 * @param flowInstanceId
	 * @return
	 */
	public WFInstanceTransition addTransitionInstance(WFFlowTransition trans,
			Map<Long, Long> convertMap, Long flowInstanceId);

	/**
	 * 根据转换条件和当前步骤节点选择转换路径
	 * 
	 * @param startStepId
	 * @param condtion
	 * @return
	 */
	public WFInstanceTransition getTranInstanceByConditionMap(
			Long flowInstanceId, Long startStepId,
			Map<String, String> condtionMap);

	/**
	 * 获取流程的第一个Action
	 * 
	 * @param stepSet
	 * @return
	 */
	public WFInstanceTransition getFirstAction(List<WFStepInstance> stepSet);

	/**
	 * 根据开始步骤获取转换
	 * 
	 * @param startStepId
	 * @return
	 */
	public WFInstanceTransition getTranInstance(Long startStepId);
}
