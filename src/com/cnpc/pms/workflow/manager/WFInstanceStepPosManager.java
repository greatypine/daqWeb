package com.cnpc.pms.workflow.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFInstanceStepPos;
import com.cnpc.pms.workflow.entity.WFFlowStepToPos;

/**
 * 步骤实例岗位对应维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFInstanceStepPosManager extends IManager {
	/**
	 * 插入一条新的流程执行记录
	 * 
	 * @param stepPos
	 * @param convertMap
	 *            以模板中步骤Id为key, 以实例中步骤id为Value
	 * @return
	 */
	public WFInstanceStepPos addInstanceStepPos(WFFlowStepToPos stepPos,
			Map<Long, Long> convertMap);

}
