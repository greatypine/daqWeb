package com.cnpc.pms.workflow.manager.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.workflow.entity.WFInstanceStepPos;
import com.cnpc.pms.workflow.entity.WFFlowStepToPos;
import com.cnpc.pms.workflow.manager.WFInstanceStepPosManager;

public class WFInstanceStepPosManagerImpl extends BaseManagerImpl implements
		WFInstanceStepPosManager {

	public WFInstanceStepPos addInstanceStepPos(WFFlowStepToPos stepPos,
			Map<Long, Long> convertMap) {
		// TODO Auto-generated method stub
		WFInstanceStepPos instanceStepPos = new WFInstanceStepPos();
		BeanUtils.copyProperties(stepPos, instanceStepPos,
				new String[] { "id" });
		instanceStepPos.setStepId(convertMap.get(stepPos.getStepId()));
		this.saveObject(instanceStepPos);
		return instanceStepPos;
	}
}
