package com.cnpc.pms.workflow.manager.impl;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.workflow.entity.WFFlowVariable;

import com.cnpc.pms.workflow.manager.WFFlowVariableManager;

public class WFFlowVariableManagerImpl extends BaseManagerImpl implements WFFlowVariableManager {
	public WFFlowVariable addArg(WFFlowVariable arg) {
		super.saveObject(arg);
		return arg;
	}

	public boolean deleteArg(WFFlowVariable arg) {
		super.removeObject(arg);
		return true;
	}

	public WFFlowVariable saveArg(WFFlowVariable arg) {
		WFFlowVariable dbObj = null;
		System.out.println(arg.getId());
		dbObj = (WFFlowVariable) super.getObject(arg.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = arg;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(arg, dbObj,
					new String[] { "id", "version" });

		}

		super.saveObject(dbObj);
		return arg;
	}

	public WFFlowVariable querArgs(Long id) {
		WFFlowVariable wfArgs = (WFFlowVariable) super.getObject(id);
		return wfArgs;
	}

	public boolean deleteArgs(Long id) {
		super.removeObjectById(id);
		return true;
	}
}
