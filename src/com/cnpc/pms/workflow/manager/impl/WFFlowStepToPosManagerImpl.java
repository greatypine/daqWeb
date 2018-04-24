package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.util.SpringHelper;

import com.cnpc.pms.workflow.entity.WFFlowStepToPos;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.manager.WFFlowStepToPosManager;
import com.cnpc.pms.workflow.manager.WFFlowStepManager;
import com.cnpc.pms.workflow.util.WFManagerConst;

public class WFFlowStepToPosManagerImpl extends BaseManagerImpl implements
		WFFlowStepToPosManager {
	public WFFlowStepToPos addStepPos(WFFlowStepToPos wFStepPos) {
		super.saveObject(wFStepPos);
		return wFStepPos;
	}

	public boolean deleteStepPos(WFFlowStepToPos wFStepPos) {
		super.removeObject(wFStepPos);
		return true;
	}

	public WFFlowStepToPos queryStepPos(Long id) {
		WFFlowStepToPos wfStepPos = (WFFlowStepToPos) super.getObject(id);
		return wfStepPos;
	}

	public WFFlowStepToPos saveStepPos(WFFlowStepToPos wFStepPos) {
		WFFlowStepToPos dbObj = null;
		System.out.println(wFStepPos.getId());
		dbObj = (WFFlowStepToPos) super.getObject(wFStepPos.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = wFStepPos;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(wFStepPos, dbObj, new String[] { "id",
					"version" });

		}

		super.saveObject(dbObj);
		return wFStepPos;
	}

	public boolean removeStepPos(Long id) {
		// TODO Auto-generated method stub
		super.removeObjectById(id);
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<WFFlowStepToPos> getWFStepPosList(Long id) {
		WFFlowStepManager wfstepmanager = (WFFlowStepManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOWSTEP);
		WFFlowStep wfStep = wfstepmanager.queryStep(id);
		Set<WFFlowStepToPos> varStepToPos = wfStep.getVarStepToPos();
		List<WFFlowStepToPos> list = new ArrayList();
		for (WFFlowStepToPos wfStepPos : varStepToPos) {
			list.add(wfStepPos);
		}
		return list;
	}
}
