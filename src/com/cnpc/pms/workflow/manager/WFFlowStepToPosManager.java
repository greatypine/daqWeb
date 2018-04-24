package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;

import com.cnpc.pms.workflow.entity.WFFlowStepToPos;

public interface WFFlowStepToPosManager extends IManager {
	/**
	 * 增加步骤对应岗位
	 * 
	 * @param wFStepPos
	 * @return
	 */
	public WFFlowStepToPos addStepPos(WFFlowStepToPos wFStepPos);

	/**
	 * 删除步骤对应岗位
	 * 
	 * @param wFStepPos
	 * @return
	 */
	public boolean deleteStepPos(WFFlowStepToPos wFStepPos);

	/**
	 * 通过id删除步骤对应岗位
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeStepPos(Long id);

	/**
	 * 通过id获取步骤对应岗位
	 * 
	 * @param id
	 * @return
	 */
	public WFFlowStepToPos queryStepPos(Long id);

	/**
	 * 保存步骤对应岗位
	 * 
	 * @param wFStepPos
	 * @return
	 */
	public WFFlowStepToPos saveStepPos(WFFlowStepToPos wFStepPos);

	/**
	 * 返回List<WFStepPos>
	 * 
	 * @param
	 * @return
	 */
	public List<WFFlowStepToPos> getWFStepPosList(Long id);
}
