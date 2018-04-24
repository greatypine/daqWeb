package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowOrg;

public interface WFFlowOrgManager extends IManager {
	/**
	 * 增加流程组织机构
	 * 
	 * @param WFFlowOrg
	 * @return
	 */
	public WFFlowOrg addFlowOrg(WFFlowOrg wFFlowOrg);

	/**
	 * 删除流程组织机构
	 * 
	 * @param wFFlowOrg
	 * @return
	 */
	public boolean deleteFlowOrg(WFFlowOrg wFFlowOrg);

	/**
	 * 通过id查询组织机构
	 * 
	 * @param id
	 * @return
	 */
	public WFFlowOrg queryFlowOrg(Long id);

	/**
	 * 保存流程组织机构
	 * 
	 * @param wFFlowOrg
	 * @return
	 */
	public WFFlowOrg saveFlowOrg(WFFlowOrg wfFlowOrg);

	/**
	 * 通过id删除组织机构
	 * 
	 * @param WFFlowOrg
	 * @return
	 */
	public boolean deleteFlowOrgs(Long id);

	/**
	 * 获取组织机构，返回一个List<WFFlowOrg>
	 * 
	 * @param
	 * @return
	 */
	public List<WFFlowOrg> getFlowOrgsList();
}
