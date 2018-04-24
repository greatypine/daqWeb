package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
//import com.cnpc.pms.bizbase.rbac.position.entity.CnpcPosition;
//import com.cnpc.pms.bizbase.rbac.position.manager.CnpcPositionManager;
import com.cnpc.pms.workflow.entity.WFFlowStepPositionCopy;
import com.cnpc.pms.workflow.manager.WFFlowStepPositionCopyManager;

public class WFFlowStepPositionCopyManagerImpl extends BaseManagerImpl implements
		WFFlowStepPositionCopyManager {

	public WFFlowStepPositionCopy addWFPosCopyInfo(WFFlowStepPositionCopy obj) {
		//添加岗位名称
//		CnpcPositionManager cnpc = (CnpcPositionManager)SpringHelper.getBean("cnpcPositionManager");
//		CnpcPosition pos = (CnpcPosition)cnpc.getObject(obj.getPositionId());
//		if(pos!=null){
//			obj.setPositionName(pos.getName());
//		}else{
//			obj.setPositionName("");
//		}
		super.saveObject(obj);
		return obj;
	}

	public Boolean deleteWFPosCopyInfo(Long id) {
		super.removeObjectById(id);
		return true;
	}

	public WFFlowStepPositionCopy queryWFPosCopyInfo(Long id) {
		WFFlowStepPositionCopy wfPositionCopyInfo = (WFFlowStepPositionCopy) super
				.getObject(id);
		return wfPositionCopyInfo;
	}

	public List<WFFlowStepPositionCopy> getCopyInfos() {
		List<WFFlowStepPositionCopy> list = (List<WFFlowStepPositionCopy>) super
				.getObjects();
		return list;
	}

	public WFFlowStepPositionCopy saveWFPosCopyInfo(WFFlowStepPositionCopy obj) {
		//添加岗位名称
//		CnpcPositionManager cnpc = (CnpcPositionManager)SpringHelper.getBean("cnpcPositionManager");
//		CnpcPosition pos = (CnpcPosition)cnpc.getObject(obj.getPositionId());
//		if(pos!=null){
//			obj.setPositionName(pos.getName());
//		}else{
//			obj.setPositionName("");
//		}
		
		WFFlowStepPositionCopy dbObj = null;
		System.out.println(obj.getId());
		dbObj = (WFFlowStepPositionCopy) super.getObject(obj.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = obj;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(obj, dbObj,
					new String[] { "id", "version" });

		}

		super.saveObject(dbObj);
		return obj;

	}

	@SuppressWarnings("unchecked")
	public List<WFFlowStepPositionCopy> getListByStepId(Long stepId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowStepsId", stepId));
		return (List<WFFlowStepPositionCopy>) this.getObjects(fsp);
	}

}
