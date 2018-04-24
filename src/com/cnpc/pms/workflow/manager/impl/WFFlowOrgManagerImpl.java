package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.workflow.entity.WFFlowOrg;
import com.cnpc.pms.workflow.manager.WFFlowOrgManager;

public class WFFlowOrgManagerImpl extends BaseManagerImpl implements
		WFFlowOrgManager {
	public WFFlowOrg addFlowOrg(WFFlowOrg wFFlowOrg) {
		super.saveObject(wFFlowOrg);
		return wFFlowOrg;
	}

	public boolean deleteFlowOrg(WFFlowOrg wFFlowOrg) {
		super.removeObject(wFFlowOrg);
		return true;
	}

	public WFFlowOrg queryFlowOrg(Long id) {
		WFFlowOrg wfFlowOrg = (WFFlowOrg) super.getObject(id);
		return wfFlowOrg;
	}

	public WFFlowOrg saveFlowOrg(WFFlowOrg wfFlowOrg) {
		WFFlowOrg dbObj = null;
		System.out.println(wfFlowOrg.getId());
		dbObj = (WFFlowOrg) super.getObject(wfFlowOrg.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = wfFlowOrg;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(wfFlowOrg, dbObj, new String[] { "id",
					"version" });

		}

		super.saveObject(dbObj);
		return wfFlowOrg;
	}

	public boolean deleteFlowOrgs(Long id) {
		super.removeObjectById(id);
		return true;
	}

	public List<WFFlowOrg> getFlowOrgsList() {
		// TODO Auto-generated method stub
		List<WFFlowOrg> list = (List<WFFlowOrg>) super.getObjects();
		return list;
	}
}
