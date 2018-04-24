package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;

public class WFFlowInstanceManagerImpl extends BaseManagerImpl implements
		WFFlowInstanceManager {

	public boolean deleteFlowInstance(WFFlowInstance flowInstance) {
		// TODO Auto-generated method stub
		this.removeObject(flowInstance);
		return queryFlowInstance(flowInstance.getId()) == null;
	}

	public WFFlowInstance addFlowInstance(WFFlowInstance flowInstance) {
		// TODO Auto-generated method stub
		this.saveObject(flowInstance);
		return flowInstance;
	}

	public WFFlowInstance queryFlowInstance(Long id) {
		// TODO Auto-generated method stub
		WFFlowInstance flowInstance = (WFFlowInstance) this.getObject(id);
		return flowInstance;
	}

	public WFFlowInstance saveFlowInstance(WFFlowInstance flowInstance) {
		// TODO Auto-generated method stub
		this.saveObject(flowInstance);
		return flowInstance;
	}

	@SuppressWarnings("unchecked")
	public WFFlowInstance getFlowInstance(Long moduleId, Long sheetId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("moduleId", moduleId)
				.appendAnd(FilterFactory.getSimpleFilter("sheetId", sheetId)));
		// 按照最新实例排序
		fsp.setSort(new Sort("lastStepDate", Sort.DESC));
		List<WFFlowInstance> list = (List<WFFlowInstance>) this.getObjects(fsp);
		if (list == null || list.size() == 0)
			return null;
		else
			return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<WFFlowInstance> getFlowInstanceList() {
		// TODO Auto-generated method stub
		List<WFFlowInstance> list = (List<WFFlowInstance>) super.getObjects();
		return list;
	}

	public WFFlowInstance getFlowInstancebySheetId(Long sheetId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("sheetId", sheetId));
		// 按照最新实例排序
		fsp.setSort(new Sort("lastStepDate", Sort.DESC));
		List<WFFlowInstance> list = (List<WFFlowInstance>) this.getObjects(fsp);
		if (list == null || list.size() == 0)
			return null;
		else
			return list.get(0);
	}

	/**
	 * 删除一条流程实例,按照Id进行删除，<br>
	 * 用于:1.系统运维维护<br>
	 * 2.表单被审批退回以后,变成草稿状态以后的删除,同时将对应的流程信息进行联动删除<br>
	 * 
	 * @param wFFlowInstance
	 * @return 0成功 1失败 2不存在
	 */
	public Long deleteFlowInstanceById(Long id) {
		WFFlowInstance inst = (WFFlowInstance) this.getObject(id);
		if (inst == null) {
			return 2L;
		}
		try {
			this.removeObject(inst);
			return 0L;
		} catch (Exception e) {
			e.printStackTrace();
			return 1L;
		}
	}

	/**
	 * 根据模块Id来检索所有的流程实例列表,在删除模块时进行校验<br>
	 * 如果某个ModuleId下面挂有流程实例,则不容许删除模块定义<br>
	 */
	public List<WFFlowInstance> getFLowInstanceListByModuleId(Long moduleId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("moduleId", moduleId));
		// 按照最新实例排序
		fsp.setSort(new Sort("lastStepDate", Sort.DESC));
		List<WFFlowInstance> list = (List<WFFlowInstance>) this.getObjects(fsp);
		if (list == null) {
			list = new ArrayList<WFFlowInstance>();
		}
		return list;
	}
}
