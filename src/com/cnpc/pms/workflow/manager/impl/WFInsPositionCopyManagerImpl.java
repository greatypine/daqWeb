package com.cnpc.pms.workflow.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFInsPositionCopy;
import com.cnpc.pms.workflow.entity.WFFlowStepPositionCopy;
import com.cnpc.pms.workflow.manager.WFInsPositionCopyManager;
import com.cnpc.pms.workflow.manager.WFFlowStepPositionCopyManager;
import com.cnpc.pms.workflow.util.WFManagerConst;

public class WFInsPositionCopyManagerImpl extends BaseManagerImpl implements
		WFInsPositionCopyManager {

	public void copyStepPosition(Map<Long, Long> convertMap) {
		//获取流程步骤的岗位抄送管理器
		WFFlowStepPositionCopyManager copyManager = (WFFlowStepPositionCopyManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOWSTEPPOSITIONCOPY);
		for (Map.Entry<Long, Long> con : convertMap.entrySet()) {
			List<WFFlowStepPositionCopy> positionCopyList = copyManager
					.getListByStepId(con.getKey());
			if (positionCopyList != null && positionCopyList.size() > 0) {
				for (WFFlowStepPositionCopy info : positionCopyList) {
					WFInsPositionCopy insPositionCopy = new WFInsPositionCopy();
					insPositionCopy.setFlowInstanceStepsId(con.getValue());
					insPositionCopy.setPositionId(info.getPositionId());
					insPositionCopy.setIsSameOrg(info.getIsSameOrg());
					this.saveObject(insPositionCopy);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<WFInsPositionCopy> getListByInsStepId(Long insStepId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceStepsId",
				insStepId));
		return (List<WFInsPositionCopy>) this.getObjects(fsp);
	}
}
