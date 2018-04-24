package com.cnpc.pms.workflow.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.workflow.entity.WF1FlowMapping;
import com.cnpc.pms.workflow.entity.WFDelegate;

public interface WF1FlowMappingManager extends IManager {
	/**
	 * 根据给定的表名和Id号来检索对应的数据
	 * @param rTableName 一期的给定表名
	 * @param rId 一期的数据表记录号
	 * @return
	 */
	public WF1FlowMapping getWF1FlowMapping(String rTableName,Long rId);
	
	/**
	 * 更新或者保存一个新的记录
	 * @param mapping
	 * @return
	 */
	public WF1FlowMapping saveWF1FlowMapping(WF1FlowMapping mapping);
	
	/**
	 * 检索数据
	 * @param id
	 * @return
	 */
	public WF1FlowMapping queryWF1FlowMapping(Long id);
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	public Long deleteWF1FlowMapping(Long id);
}
