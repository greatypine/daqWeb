package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowStepPersonCopy;

public interface WFFlowStepPersonCopyManager extends IManager {
	public WFFlowStepPersonCopy addWFPsInfo(WFFlowStepPersonCopy obj);

	public WFFlowStepPersonCopy saveWFPsInfo(WFFlowStepPersonCopy obj);

	public WFFlowStepPersonCopy queryWFPsInfo(Long id);

	public Boolean deleteWFPsInfo(Long id);

	/**
	 * 根据步骤id获取抄送人员列表
	 * 
	 * @param stepId
	 * @return
	 */
	public List<WFFlowStepPersonCopy> getListByStepId(Long stepId);
}
