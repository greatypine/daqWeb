package com.cnpc.pms.workflow.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.entity.WFInstanceVariable;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.entity.WFFlowTransition;
import com.cnpc.pms.workflow.entity.WFInstanceTransition;
import com.cnpc.pms.workflow.manager.WFInstanceVariableManager;
import com.cnpc.pms.workflow.manager.WFInstanceTransitionManager;
import com.cnpc.pms.workflow.util.WFManagerConst;

public class WFInstanceTransitionManagerImpl extends BaseManagerImpl implements
		WFInstanceTransitionManager {

	public WFInstanceTransition addTransitionInstance(WFFlowTransition trans,
			Map<Long, Long> convertMap, Long flowInstanceId) {
		// TODO Auto-generated method stub
		WFInstanceTransition transitionInstance = new WFInstanceTransition();
		BeanUtils.copyProperties(trans, transitionInstance, new String[] {
				"id", "version" });
		transitionInstance.setBeginId(convertMap.get(trans.getBeginId()));
		transitionInstance.setEndId(convertMap.get(trans.getEndId()));
		transitionInstance.setFlowInstanceId(flowInstanceId);
		this.saveObject(transitionInstance);
		return transitionInstance;
	}

	public WFInstanceTransition readTransitionInstance(Long id) {
		// TODO Auto-generated method stub
		WFInstanceTransition transitionInstance = (WFInstanceTransition) this
				.getObject(id);
		return transitionInstance;
	}

	@SuppressWarnings("unchecked")
	public WFInstanceTransition getTranInstanceByConditionMap(
			Long flowInstanceId, Long startStepId,
			Map<String, String> condtionMap) {
		FSP fsp = new FSP();
		fsp
				.setUserFilter(FilterFactory.getSimpleFilter("beginId",
						startStepId));
		fsp.setSort(new Sort("transitionNo", 1));
		List<WFInstanceTransition> tranInstList = (List<WFInstanceTransition>) this
				.getObjects(fsp);
		if (tranInstList == null || tranInstList.size() == 0)
			return null;
		// 默认转换条件转化的优先级 ：如果有条件，取满足条件的转换，否则取默认转换，以上转换均无法取到，则返回null
		WFInstanceTransition returnTranInst = null;
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper
				.getBean(WFInstanceDAO.class.getName());
		for (WFInstanceTransition tranInst : tranInstList) {
			if (tranInst.getIsDefault().equals(new Long(1))) {
				returnTranInst = tranInst;
				// 需要确认，影响路径选择的优先级
				// break;
			} else {
				// 获取实例变量管理器
				WFInstanceVariableManager instanceVarManager = (WFInstanceVariableManager) SpringHelper
						.getBean(WFManagerConst.WF_INSTANCEVARIABLE);
				WFInstanceVariable instanceVar = null;
				String condition = tranInst.getCondition();
				if (null != condition && "" != condition) {
					if (null != condtionMap && condtionMap.values().size() > 0) {
						for (Map.Entry<String, String> entry : condtionMap
								.entrySet()) {
							instanceVar = instanceVarManager
									.getInstanceVarByCode(entry.getKey(),
											flowInstanceId);
							if (instanceVar != null
									&& instanceVar.getVariableType() != null
									&& instanceVar.getVariableType().equals(
											"字符型"))
								condition = condition.replaceAll(
										entry.getKey(), "'" + entry.getValue()
												+ "'");
							else
								condition = condition.replaceAll(
										entry.getKey(), entry.getValue());
						}
						System.out.println("###########赋值后的流程转换条件：condition"
								+ condition);
						if (instanceDAO.isMeetCondition(condition)) {
							returnTranInst = tranInst;
							break;
						}
					}
				}
			}
		}
		return returnTranInst;
	}

	@SuppressWarnings("unchecked")
	public WFInstanceTransition getTranInstance(Long startStepId) {
		FSP fsp = new FSP();
		fsp
				.setUserFilter(FilterFactory.getSimpleFilter("beginId",
						startStepId));
		List<WFInstanceTransition> tranInstList = (List<WFInstanceTransition>) this
				.getObjects(fsp);
		if (tranInstList == null || tranInstList.size() == 0) {
			return null;
		}
		return tranInstList.get(0);
	}

	@SuppressWarnings("unchecked")
	public WFInstanceTransition getFirstAction(List<WFStepInstance> stepSet) {
		Long startStepId = null;
		for (WFStepInstance stepInstance : stepSet) {
			// 寻找开始节点ID
			if (stepInstance.getStepType().equals(new Long(0))) {
				startStepId = stepInstance.getId();
				break;
			}
		}
		if (startStepId == null)
			return null;
		FSP fsp = new FSP();
		fsp
				.setUserFilter(FilterFactory.getSimpleFilter("beginId",
						startStepId));
		fsp.setSort(new Sort("transitionNo", 1));
		List<WFInstanceTransition> tranInstList = (List<WFInstanceTransition>) this
				.getObjects(fsp);
		if (tranInstList == null || tranInstList.size() == 0)
			return null;
		/*		2013-10-15删除是否默认转换判断，以为流程实例转换路径已算好，无默认非默认之分
		WFInstanceTransition returnTranInst = null;
		for (WFInstanceTransition tranInst : tranInstList) {
			if (tranInst.getIsDefault().equals(new Long(1))) {
				returnTranInst = tranInst;
				break;
			}
		}
		*/
		return tranInstList.get(0);
	}

}
