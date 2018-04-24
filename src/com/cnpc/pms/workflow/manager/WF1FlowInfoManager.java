package com.cnpc.pms.workflow.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.dto.WF1FlowInfoParam;
import com.cnpc.pms.workflow.entity.WFFlowInstance;

public interface WF1FlowInfoManager extends IManager {
	/**
	 * 检索对应的流程实例Id
	 * @param rTableName
	 * @param rId
	 * @return
	 */
	public WFFlowInstance queryFlowInstance(String rTableName,Long rId);
	

	
	/**
	 * 生成流程实例数据
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public Long createInstance(WF1FlowInfoParam param);
	
	/**
	 * 删除现有流程实例
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public Long removeInstance(WF1FlowInfoParam param);
	
}
