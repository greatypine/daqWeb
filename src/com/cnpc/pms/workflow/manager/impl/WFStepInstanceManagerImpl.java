package com.cnpc.pms.workflow.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;
import com.cnpc.pms.workflow.util.WFStepTypeConst;

public class WFStepInstanceManagerImpl extends BaseManagerImpl implements
		WFStepInstanceManager {

	public Map<Long, Long> copyStep(List<WFFlowStep> stepList,
			Long flowinstanceid) {
		// TODO Auto-generated method stub
		Map<Long, Long> map = new HashMap<Long, Long>();
		for (WFFlowStep step : stepList) {
			WFStepInstance stepInstance = addStepInstance(step, flowinstanceid);
			map.put(step.getId(), stepInstance.getId());
		}
		return map;
	}

	public WFStepInstance addStepInstance(WFFlowStep step, Long flowinstanceid) {
		WFStepInstance stepInstance = new WFStepInstance();
		BeanUtils.copyProperties(step, stepInstance, new String[] { "id",
				"version" });
		stepInstance.setFlowInstanceId(flowinstanceid);
		stepInstance.setIsDel(0L);
		this.saveObject(stepInstance);
		return stepInstance;
	}

	public WFStepInstance readStepInstance(Long id) {
		// TODO Auto-generated method stub
		WFStepInstance stepInstance = (WFStepInstance) this.getObject(id);
		return stepInstance;
	}

	@SuppressWarnings("unchecked")
	public List<WFStepInstance> getStepInstanceList(Long flowInstanceId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId).appendAnd(
				FilterFactory.getSimpleFilter("isDel", new Long(0))));
		fsp.setSort(new Sort("stepNo", 1));
		List<WFStepInstance> stepInstList = (List<WFStepInstance>) this
				.getObjects(fsp);
		return stepInstList;
	}

	/**
	 * 根据流程实例Id,当前步骤Id,来检索后续的所有节点列表<br>
	 * 去掉其中的结束节点
	 * 
	 * @param flowInstanceId
	 * @param currStepInstanceId
	 * @return
	 */
	public List<WFStepInstance> getNextStepList(Long flowInstanceId,
			Long currStepInstanceId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId)
				.appendAnd(
						FilterFactory.getSimpleFilter("isDel", new Long(0))
								.appendAnd(
										FilterFactory.getStringFilter("id>"
												+ currStepInstanceId))
								.appendAnd(
										FilterFactory.getSimpleFilter(
												"stepType", new Long(1)))));
		//修改排序方式，用id排序，而不用stepNo排序
		//fsp.setSort(new Sort("stepNo", Sort.ASC));
		fsp.setSort(new Sort("id", Sort.ASC));
		List<WFStepInstance> stepInstList = (List<WFStepInstance>) this
				.getObjects(fsp);
		return stepInstList;
	}

	/**
	 * 根据流程实例Id,开始步骤Id,结束步骤Id,删除中间的所有步骤信息<br>
	 * 
	 * @param flowInstanceId
	 *            流程实例Id
	 * @param beginStepId
	 *            开始步骤Id
	 * @param endStepId
	 *            结束步骤Id
	 */
	public void removeMiddleStep(Long flowInstanceId, Long beginStepId,
			Long endStepId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId).appendAnd(
				FilterFactory.getSimpleFilter("isDel", new Long(0)).appendAnd(
						FilterFactory.getStringFilter("id>" + beginStepId))
						.appendAnd(
								FilterFactory
										.getStringFilter("id<" + endStepId))
						.appendAnd(
								FilterFactory.getSimpleFilter("stepType",
										new Long(1)))));
		fsp.setSort(new Sort("stepNo", Sort.ASC));
		List<WFStepInstance> stepInstList = (List<WFStepInstance>) this
				.getObjects(fsp);

		// 删除中间的无效流程实例,此处采用物理删除方式进行删除
		for (WFStepInstance step : stepInstList) {
			this.removeObjectById(step.getId());
		}
	}

	/**
	 * 根据流程实例Id,获取开始步骤节点,用于在退回操作时进行跳转<br>
	 * 仅考虑未逻辑删除的步骤
	 * 
	 * @param flowInstanceId
	 *            流程实例Id
	 * @return 开始步骤节点，可能为空
	 */
	public WFStepInstance getBeginStep(Long flowInstanceId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId).appendAnd(
				FilterFactory.getSimpleFilter("isDel", new Long(0)).appendAnd(
						FilterFactory.getSimpleFilter("stepType",
								WFStepTypeConst.BEGIN))));
		List<WFStepInstance> stepInstList = (List<WFStepInstance>) this
				.getObjects(fsp);
		// 正常情况下仅有一个开始节点
		if (stepInstList.size() == 1) {
			return stepInstList.get(0);
		} else {
			// 异常情况下一律返回null
			return null;
		}
	}

	/**
	 * 根据流程实例Id,获取结束步骤节点,用于在强制终止操作时进行跳转<br>
	 * 仅考虑未逻辑删除的步骤
	 * 
	 * @param flowInstanceId
	 *            流程实例Id
	 * @return 结束步骤节点，可能为空
	 */
	public WFStepInstance getEndStep(Long flowInstanceId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId).appendAnd(
				FilterFactory.getSimpleFilter("isDel", new Long(0)).appendAnd(
						FilterFactory.getSimpleFilter("stepType",
								WFStepTypeConst.END))));
		List<WFStepInstance> stepInstList = (List<WFStepInstance>) this
				.getObjects(fsp);
		// 正常情况下仅有一个开始节点
		if (stepInstList.size() == 1) {
			return stepInstList.get(0);
		} else {
			// 异常情况下一律返回null
			return null;
		}
	}
	
	/**
	 * 插入一条步骤实例数据
	 * @param stepInstance
	 * @return
	 */
	public WFStepInstance addStepInstance(WFStepInstance stepInstance){
		super.save(stepInstance);
		return stepInstance;
	}
}
