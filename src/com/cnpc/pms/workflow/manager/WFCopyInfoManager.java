package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFCopyInfo;

public interface WFCopyInfoManager extends IManager {
	public WFCopyInfo addWFCopyInfo(WFCopyInfo wfCopyInfo);

	public Boolean deleteWFCopyInfo(Long id);

	public WFCopyInfo queryWfCopyInfo(Long id);

	public WFCopyInfo updateWfCotyInfo(WFCopyInfo wf);
	
	public List<WFCopyInfo> getUserCopyInfo(Long receiveId,Long flowInstanceid);
}
