package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.entity.WFStepInstance;

/**
 * 工作流步骤维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFStepInstanceManager extends IManager {
	/**
	 * 按照序号来检索步骤实例
	 * 
	 * @param id
	 * @return
	 */
	public WFStepInstance readStepInstance(Long id);

	/**
	 * 利用流程步骤的数据来插入步骤实例表
	 * 
	 * @param step
	 * @param flowinstanceid
	 * @return
	 */
	public WFStepInstance addStepInstance(WFFlowStep step, Long flowinstanceid);

	/**
	 * 利用流程步骤的列表来插入步骤实例表，得到一个新旧ID之间的对应关系
	 * 
	 * @param stepList
	 * @param flowinstanceid
	 * @return
	 */
	public Map<Long, Long> copyStep(List<WFFlowStep> stepList, Long flowinstanceid);

	/**
	 * 根据流程实例ID，得到实例步骤列表
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	public List<WFStepInstance> getStepInstanceList(Long flowInstanceId);
	
	/**
	 * 根据流程实例Id,当前步骤Id,来检索后续的所有节点列表<br>
	 * 去掉其中的结束节点
	 * @param flowInstanceId
	 * @param currStepInstanceId
	 * @return
	 */
	public List<WFStepInstance> getNextStepList(Long flowInstanceId,Long currStepInstanceId);
	
	/**
	 * 根据流程实例Id,开始步骤Id,结束步骤Id,删除中间的所有步骤信息<br>
	 * @param flowInstanceId 流程实例Id
	 * @param beginStepId 开始步骤Id
	 * @param endStepId 结束步骤Id
	 */
	public void removeMiddleStep(Long flowInstanceId,Long beginStepId,Long endStepId);
	
	/**
	 * 根据流程实例Id,获取开始步骤节点,用于在退回操作时进行跳转<br>
	 * 仅考虑未逻辑删除的步骤
	 * @param flowInstanceId 流程实例Id
	 * @return 开始步骤节点，可能为空
	 */
	public WFStepInstance getBeginStep(Long flowInstanceId);
	/**
	 * 根据流程实例Id,获取结束步骤节点,用于在强制终止操作时进行跳转<br>
	 * 仅考虑未逻辑删除的步骤
	 * @param flowInstanceId 流程实例Id
	 * @return 结束步骤节点，可能为空
	 */	
	public WFStepInstance getEndStep(Long flowInstanceId);
	
	/**
	 * 插入一条步骤实例数据
	 * @param stepInstance
	 * @return
	 */
	public WFStepInstance addStepInstance(WFStepInstance stepInstance);
	
}
