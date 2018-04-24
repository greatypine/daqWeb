package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFModule;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFFlowManager;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.util.WFManagerHelper;

public class WFModuleManagerImpl extends BaseManagerImpl implements
		WFModuleManager {

	@SuppressWarnings("unchecked")
	public WFModule addTBModule(WFModule obj) {
		List<WFModule> list = (List<WFModule>) super.getObjects();
		boolean flag = true;
		if (obj.getModuleCode() != null) {
			for (WFModule wfm : list) {
				String tempOne = wfm.getModuleCode();
				String tempTwo = obj.getModuleCode();
				if (tempTwo.equals(tempOne)) {
					flag = false;
				}
			}
		}
		if (flag == true) {
			super.saveObject(obj);
			return obj;
		} else {
			return null;
		}

	}

	public int deleteTBModule(Long id) {
		//step1:判断是否有关联流程定义
		WFFlowManager manager = WFManagerHelper.getWFFlowManager();
		List<WFFlow> ins = manager.getFlowListByModuleId(id);
		if (ins.size()>0){
			return 0;
		}
		
		//step2:判断是否有流程实例
		WFFlowInstanceManager manager2 = WFManagerHelper.getWFFlowInstanceManager();
		List<WFFlowInstance> list2 = manager2.getFLowInstanceListByModuleId(id);
		if(list2.size()>0){
			return 0;
		}
		
		//step3:删除模块定义,返回1代表删除成功
		super.removeObjectById(id);
		return 1;
		
	}

	public WFModule queryTBModule(Long id) {
		WFModule tbModule = (WFModule) super.getObject(id);
		return tbModule;
	}

	public WFModule saveTBModule(WFModule obj) {
		WFModule dbObj = null;
		System.out.println(obj.getId());
		dbObj = (WFModule) super.getObject(obj.getId());
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
	public List<WFModule> getWFModuleListByLevel1Id(Long id) {
		// TODO Auto-generated method stub
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("level1Id", id));
		// 按照最新实例排序
		fsp.setSort(new Sort("id", Sort.DESC));
		List<WFModule> list = (List<WFModule>) this.getObjects(fsp);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<WFModule> getWFModuleListByLevel2Id(Long id) {
		// TODO Auto-generated method stub
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("level2Id", id));
		// 按照最新实例排序
		fsp.setSort(new Sort("id", Sort.DESC));
		List<WFModule> list = (List<WFModule>) this.getObjects(fsp);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<WFModule> getWFModuleList() {
		// TODO Auto-generated method stub
		//List<WFModule> list = (List<WFModule>) super.getObjects();
		FSP fsp = new FSP();
		fsp.setSort(new Sort("id", Sort.DESC));
		List<WFModule> list = (List<WFModule>) this.getObjects(fsp);
		return list;
	}

	@SuppressWarnings("unchecked")
	public Long getModuleIdByCode(String code) {
		// 如果Code不合法,传回一个不存在的ModuleId:0
		// 如果传回null,基础模块中很多地方没有做空值校验
		if (code == null || "".equals(code)) {
			return 0L;
		}
		// TODO Auto-generated method stub
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("moduleCode", code));
		// 按照最新实例排序
		fsp.setSort(new Sort("id", Sort.DESC));
		List<WFModule> list = (List<WFModule>) this.getObjects(fsp);
		if ((list.size()) > 0) {
			WFModule wfm = list.get(0);
			return wfm.getId();
		} else {
			return 0L;
		}

	}

}
