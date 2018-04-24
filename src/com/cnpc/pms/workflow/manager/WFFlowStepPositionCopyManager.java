package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowStepPositionCopy;

public interface WFFlowStepPositionCopyManager extends IManager {
	public WFFlowStepPositionCopy addWFPosCopyInfo(WFFlowStepPositionCopy obj);

	public WFFlowStepPositionCopy saveWFPosCopyInfo(WFFlowStepPositionCopy obj);

	public WFFlowStepPositionCopy queryWFPosCopyInfo(Long id);

	public Boolean deleteWFPosCopyInfo(Long id);

	public List<WFFlowStepPositionCopy> getCopyInfos();

	/**
	 * 根据步骤id获取抄送岗位列表
	 * 
	 * @param stepId
	 * @return
	 */
	public List<WFFlowStepPositionCopy> getListByStepId(Long stepId);
}
