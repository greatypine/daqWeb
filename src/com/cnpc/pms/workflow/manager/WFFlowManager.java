package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Set;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowOrg;

public interface WFFlowManager extends IManager {
	/**
	 * 通过id查询工作流模板
	 * 
	 * @param id
	 * @return
	 */
	public WFFlow queryFlow(Long id);

	/**
	 * 通过业务模块ID获取工作流模板对象
	 * 
	 * @param moduleId
	 * @return
	 */
	public List<WFFlow> getFlowListByModuleId(Long moduleId);

	/**
	 * 增加工作流模板
	 * 
	 * @param obj
	 * @return
	 */
	public WFFlow addFlow(WFFlow obj);

	/**
	 * 删除工作流模板
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean deleteFlow(WFFlow obj);

	/**
	 * 保存工作流模板
	 * 
	 * @param obj
	 * @return
	 */
	public WFFlow saveFlow(WFFlow obj);

	/**
	 * 校验
	 */
	public String validation(Long id);

	/**
	 * 通过id删除工作流模板
	 * 
	 * @param id
	 * @return
	 */
	public Boolean deleteFlowById(Long id);

	/**
	 * 通过id查询流程部门列表
	 * 
	 * @param id
	 * @return
	 */
	public Set<WFFlowOrg> getWFFlowOrgListById(Long id);
	
	/**
	 * 通过流程图定义工作流
	 * @param workFlowDTO
	 * @return
	 */
//	public String saveWorkFlow(WorkFlowDTO workFlowDTO);
}
