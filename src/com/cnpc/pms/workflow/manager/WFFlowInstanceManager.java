package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowInstance;

/**
 * 工作流实例信息维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFFlowInstanceManager extends IManager {
	/**
	 * 据工作流实例Id来查询工作流实例
	 * 
	 * @param id
	 * @return
	 */
	public WFFlowInstance queryFlowInstance(Long id);

	/**
	 * 新增一条流程实例基本信息数据
	 * 
	 * @param wFFlowInstance
	 * @return
	 */
	public WFFlowInstance addFlowInstance(WFFlowInstance wFFlowInstance);

	/**
	 * 删除一条流程实例
	 * 
	 * @param wFFlowInstance
	 * @return
	 */
	public boolean deleteFlowInstance(WFFlowInstance wFFlowInstance);

	/**
	 * 删除一条流程实例,按照Id进行删除，系统维护使用
	 * 
	 * @param wFFlowInstance
	 * @return
	 */
	public Long deleteFlowInstanceById(Long id);
	/**
	 * 保存模板实例信息数据
	 * 
	 * @param wFFlowInstance
	 *            保存前的模版实例
	 * @return 保存后的模版实例
	 */
	public WFFlowInstance saveFlowInstance(WFFlowInstance wFFlowInstance);

	/**
	 * 根据业务模块ID和表单ID获取流程实例对象
	 * 
	 * @param moduleId
	 *            业务模块ID
	 * @param sheetId
	 *            表单ID
	 * @return
	 */
	public WFFlowInstance getFlowInstance(Long moduleId, Long sheetId);
	public WFFlowInstance getFlowInstancebySheetId(Long sheetId);

	public List<WFFlowInstance> getFlowInstanceList();
	
	/**
	 * 根据模块Id来检索对应的流程执行实例,在删除模块时做为校验条件
	 * @param moduleId
	 * @return
	 */
	public List<WFFlowInstance> getFLowInstanceListByModuleId(Long moduleId);

}
