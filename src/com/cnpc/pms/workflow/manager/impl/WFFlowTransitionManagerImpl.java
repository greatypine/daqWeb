package com.cnpc.pms.workflow.manager.impl;

import java.util.List;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.entity.WFInstanceVariable;
import com.cnpc.pms.workflow.entity.WFFlowTransition;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WFFlowStepManager;
import com.cnpc.pms.workflow.manager.WFInstanceVariableManager;
import com.cnpc.pms.workflow.manager.WFFlowTransitionManager;
import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.util.WFManagerHelper;

public class WFFlowTransitionManagerImpl extends BaseManagerImpl implements
		WFFlowTransitionManager {
	public WFFlowTransition addTransition(WFFlowTransition wFTransition) {
		addStepName(wFTransition);
		super.saveObject(wFTransition);
		// System.out.println("1111");
		return wFTransition;
	}

	public boolean deleteTransition(WFFlowTransition wFTransition) {
		super.removeObject(wFTransition);
		return true;
	}

	public WFFlowTransition saveTransition(WFFlowTransition wFTransition) {
		addStepName(wFTransition);
		WFFlowTransition dbObj = null;
		System.out.println(wFTransition.getId());
		dbObj = (WFFlowTransition) super.getObject(wFTransition.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = wFTransition;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(wFTransition, dbObj, new String[] { "id",
					"version" });

		}

		super.saveObject(dbObj);
		return wFTransition;
	}

	public WFFlowTransition queryTransition(Long id) {
		WFFlowTransition wfTransition = (WFFlowTransition) super.getObject(id);
		return wfTransition;
	}

	public boolean deleteTransitions(Long id) {
		super.removeObjectById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	public WFFlowTransition getTransitionByCondition(Long flowInstanceId,
			Long startStepId) {
		FSP fsp = new FSP();
		fsp
				.setUserFilter(FilterFactory.getSimpleFilter("beginId",
						startStepId));
		fsp.setSort(new Sort("transitionNo", 1));
		List<WFFlowTransition> tranList = (List<WFFlowTransition>) this.getObjects(fsp);
		if (tranList == null || tranList.size() == 0) {
			return null;
		}
		// 默认转换条件转化的优先级 ：如果有条件，取满足条件的转换，否则取默认转换，以上转换均无法取到，则返回null
		WFFlowTransition returnTran = null;
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper
				.getBean(WFInstanceDAO.class.getName());
		
		//获取实例变量管理器
		WFInstanceVariableManager instanceVarManager = (WFInstanceVariableManager) SpringHelper
				.getBean(WFManagerConst.WF_INSTANCEVARIABLE);
		List<WFInstanceVariable> varList = instanceVarManager
				.getListByInstanceId(flowInstanceId);
		for (WFFlowTransition tran : tranList) {
			if (tran.getIsDefault().equals(new Long(1))) {
				returnTran = tran;
			} else {
				String condition = tran.getCondition();
				if (null != condition && !"".equals(condition.trim())) {
					if (null != varList && varList.size() > 0) {
						for (WFInstanceVariable var : varList) {
							if (var.getVariableType().equals("字符型"))
								condition = condition.replaceAll(var
										.getVariableCode(), "'"
										+ var.getVariableName() + "'");
							else
								condition = condition.replaceAll(var
										.getVariableCode(), var
										.getVariableName());
						}
						System.out.println("###########赋值后的流程转换条件：condition"
								+ condition);
						if (instanceDAO.isMeetCondition(condition)) {
							returnTran = tran;
							break;
						}
					}
				}
			}
		}
		return returnTran;
	}
	
	/**
	 * 设置转换里面的开始节点名称和结束节点的名称
	 * @param wFTransition
	 */
	private void addStepName(WFFlowTransition wFTransition){
		//根据步骤的Id,检索步骤的名称并填充.
		WFFlowStepManager stepManager= WFManagerHelper.getWFFlowStepManager();
		WFFlowStep beginStep = stepManager.queryStep(wFTransition.getBeginId());
		WFFlowStep endStep=stepManager.queryStep(wFTransition.getEndId());
		if(beginStep == null){
			throw new WFException("转换定义开始节点不可为空!");
		}
		if(endStep == null){
			throw new WFException("转换定义结束节点不可为空!");
		}
		wFTransition.setBeginName(beginStep.getStepName());
		wFTransition.setEndName(endStep.getStepName());		
	}
}
