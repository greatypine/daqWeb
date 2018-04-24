package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.workflow.entity.WFFlowStepPersonCopy;
import com.cnpc.pms.workflow.manager.WFFlowStepPersonCopyManager;

public class WFFlowStepPersonCopyManagerImpl extends BaseManagerImpl implements
		WFFlowStepPersonCopyManager {

	public WFFlowStepPersonCopy addWFPsInfo(WFFlowStepPersonCopy obj) {
		//根据obj里面的personId,来检索personName
		UserManager userManager = (UserManager)SpringHelper.getBean("userManager");
		User user = userManager.getUserEntity(obj.getPersonId());
		if(user!=null){
			obj.setPersonName(user.getName());
		}else{
			obj.setPersonName("");
		}
		
		super.saveObject(obj);
		return obj;
	}

	public Boolean deleteWFPsInfo(Long id) {
		super.removeObjectById(id);
		return true;
	}

	public WFFlowStepPersonCopy queryWFPsInfo(Long id) {
		WFFlowStepPersonCopy wfPersonCopyInfo = (WFFlowStepPersonCopy) super
				.getObject(id);
		return wfPersonCopyInfo;
	}

	public WFFlowStepPersonCopy saveWFPsInfo(WFFlowStepPersonCopy obj) {
		//根据obj里面的personId,来检索personName
		UserManager userManager = (UserManager)SpringHelper.getBean("userManager");
		User user = userManager.getUserEntity(obj.getPersonId());
		if(user!=null){
			obj.setPersonName(user.getName());
		}else{
			obj.setPersonName("");
		}
		
		WFFlowStepPersonCopy dbObj = null;
		System.out.println(obj.getId());
		dbObj = (WFFlowStepPersonCopy) super.getObject(obj.getId());
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
	public List<WFFlowStepPersonCopy> getListByStepId(Long stepId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowStepsId", stepId));
		return (List<WFFlowStepPersonCopy>) this.getObjects(fsp);
	}

}
