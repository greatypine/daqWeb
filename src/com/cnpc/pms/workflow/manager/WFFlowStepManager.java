package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowStep;

public interface WFFlowStepManager extends IManager {
	/**
	 * 增加流程步骤
	 * 
	 * @param wFStep
	 * @return
	 */
	public WFFlowStep addStep(WFFlowStep wFStep);

	/**
	 * 删除流程步骤
	 * 
	 * @param wFStep
	 * @return
	 * 
	 */
	public boolean deleteStep(WFFlowStep wFStep);

	/**
	 * 保存流程步骤
	 * 
	 * @param wFStep
	 * @return
	 * 
	 */
	public WFFlowStep saveStep(WFFlowStep wFStep);

	/**
	 * 通过流程步骤id查询流程步骤
	 * 
	 * @param id
	 * @return
	 * 
	 */
	public WFFlowStep queryStep(Long id);

	/**
	 * 通过流程步骤id删除流程步骤
	 * 
	 * @param id
	 * @return
	 * 
	 */
	public boolean deleteSteps(Long id);

	/**
	 * 通过工作流id查询对应的一系列WFStep
	 * 
	 * @param id
	 * @return
	 * 
	 */
	public List<WFFlowStep> querySteps(Long id);
}
