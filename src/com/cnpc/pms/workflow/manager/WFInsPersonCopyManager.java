package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFInsPersonCopy;

/**
 * 工作流实例抄送人员维护Manager
 * 
 * @author lz
 * 
 */
public interface WFInsPersonCopyManager extends IManager {

	/**
	 * 根据流程步骤id和实力步骤id对应map拷贝抄送人员信息
	 * 
	 * @param convertMap
	 */
	public void copyStepPerson(Map<Long, Long> convertMap);

	/**
	 * 根据实例步骤id获取抄送人员列表
	 * 
	 * @param insStepId
	 * @return
	 */
	public List<WFInsPersonCopy> getListByInsStepId(Long insStepId);
}
