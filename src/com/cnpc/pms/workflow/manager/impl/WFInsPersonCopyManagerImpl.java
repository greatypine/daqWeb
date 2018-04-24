package com.cnpc.pms.workflow.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFInsPersonCopy;
import com.cnpc.pms.workflow.entity.WFFlowStepPersonCopy;
import com.cnpc.pms.workflow.manager.WFInsPersonCopyManager;
import com.cnpc.pms.workflow.manager.WFFlowStepPersonCopyManager;
import com.cnpc.pms.workflow.util.WFManagerConst;

public class WFInsPersonCopyManagerImpl extends BaseManagerImpl implements
		WFInsPersonCopyManager {

	public void copyStepPerson(Map<Long, Long> convertMap) {
		//获取步骤人员抄送的Manager
		WFFlowStepPersonCopyManager copyManager = (WFFlowStepPersonCopyManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOWSTEPPPERSONCOPY);
		for (Map.Entry<Long, Long> con : convertMap.entrySet()) {
			List<WFFlowStepPersonCopy> personCopyList = copyManager
					.getListByStepId(con.getKey());
			if (personCopyList != null && personCopyList.size() > 0) {
				for (WFFlowStepPersonCopy info : personCopyList) {
					WFInsPersonCopy insPersonCopy = new WFInsPersonCopy();
					insPersonCopy.setFlowInstanceStepsId(con.getValue());
					insPersonCopy.setReceiverId(info.getPersonId());
					insPersonCopy.setIsSameOrg(info.getIsSameOrg());
					this.saveObject(insPersonCopy);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<WFInsPersonCopy> getListByInsStepId(Long insStepId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceStepsId",
				insStepId));
		return (List<WFInsPersonCopy>) this.getObjects(fsp);
	}
}
