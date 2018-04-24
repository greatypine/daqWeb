package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.workflow.entity.WFFlowStep;

import com.cnpc.pms.workflow.manager.WFFlowStepManager;

public class WFFlowStepManagerImpl extends BaseManagerImpl implements WFFlowStepManager {
	public WFFlowStep addStep(WFFlowStep wFStep) {
		WFFlowStep step = null;
		step = wFStep;
		wFStep.setId(null);
		super.saveObject(step);
		return wFStep;
	}

	public boolean deleteStep(WFFlowStep wFStep) {
		super.removeObject(wFStep);
		return true;
	}

	public WFFlowStep saveStep(WFFlowStep wFStep) {
		WFFlowStep dbObj = null;
		System.out.println(wFStep.getId());
		dbObj = (WFFlowStep) super.getObject(wFStep.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = wFStep;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(wFStep, dbObj, new String[] { "id",
					"version", "varStepToPos", "varFlowTransition" });

		}

		super.saveObject(dbObj);
		return wFStep;
	}

	public WFFlowStep queryStep(Long id) {
		WFFlowStep wfStep = (WFFlowStep) super.getObject(id);
		return wfStep;
	}

	public boolean deleteSteps(Long id) {
		super.removeObjectById(id);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<WFFlowStep> querySteps(Long id) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowId", id));
		fsp.setSort(new Sort("stepNo", 1));
		List<WFFlowStep> list = (List<WFFlowStep>) super.getObjects(fsp);
		// System.out.println("111");
		return list;
	}
}
