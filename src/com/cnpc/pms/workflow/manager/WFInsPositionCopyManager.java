package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFInsPositionCopy;

/**
 * 工作流实例抄送岗位维护Manager
 * 
 * @author lz
 * 
 */
public interface WFInsPositionCopyManager extends IManager {

	/**
	 * 根据流程步骤id和实力步骤id对应map拷贝抄送岗位信息
	 * 
	 * @param convertMap
	 */
	public void copyStepPosition(Map<Long, Long> convertMap);

	/**
	 * 根据实例步骤id获取抄送岗位列表
	 * 
	 * @param insStepId
	 * @return
	 */
	public List<WFInsPositionCopy> getListByInsStepId(Long insStepId);
}
