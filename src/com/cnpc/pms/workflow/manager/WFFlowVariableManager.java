package com.cnpc.pms.workflow.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowVariable;

public interface WFFlowVariableManager extends IManager {
	/**
	 * 增加流程参数
	 * 
	 * @param arg
	 * @return
	 */
	public WFFlowVariable addArg(WFFlowVariable arg);

	/**
	 * 删除流程参数，传入整个对象，删除
	 * 
	 * @param arg
	 * @return
	 */
	public boolean deleteArg(WFFlowVariable arg);

	/**
	 * 保存流程参数，修改保存
	 * 
	 * @param arg
	 * @return
	 */
	public WFFlowVariable saveArg(WFFlowVariable arg);

	/**
	 * 查询流程参数，传入一个id，返回该参数对象
	 * 
	 * @param id
	 * @return
	 */
	public WFFlowVariable querArgs(Long id);

	/**
	 * 删除流程参数，传入一个id，删除该参数对象
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteArgs(Long id);
}
