package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFDelegateModule;
import com.cnpc.pms.workflow.entity.WFModule;
import com.cnpc.pms.workflow.manager.WFDelegateModuleManager;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.util.WFManagerConst;

public class WFDelegateModuleManagerImpl extends BaseManagerImpl implements
		WFDelegateModuleManager {
	/**
	 * 增加一个委托代理和模块之间的关联
	 * 
	 * @param obj
	 * @return
	 */
	public WFDelegateModule addWFDelegateModule(WFDelegateModule obj) {
		// 首先根据里面的module.id来重新检索module,然后再设置
		WFModuleManager moduleManager = (WFModuleManager) SpringHelper
				.getBean(WFManagerConst.WF_MODULEMANAGER);
		// 根据传入的Id重新检索对应的WFModule
		WFModule module = moduleManager.queryTBModule(obj.getModule().getId());
		// 重新设置module的值
		obj.setModule(module);
		// 存入数据库之中
		super.saveObject(obj);
		return obj;
	}

	/**
	 * 删除一个指定的关联管理
	 * 
	 * @param id,id号是删除条件
	 * @return
	 */
	public Boolean deleteWFDelegateModule(Long id) {
		super.removeObjectById(id);
		return true;
	}

	/**
	 * 按照Id来查询关联关系对象
	 * 
	 * @param id
	 * @return
	 */
	public WFDelegateModule queryWFDelegateModule(Long id) {
		return (WFDelegateModule) super.getObject(id);
	}

	/**
	 * 保存一个关联对象(未使用)
	 * 
	 * @param obj
	 * @return
	 */
	public WFDelegateModule savaWFDelegateModule(WFDelegateModule obj) {
		WFDelegateModule dbObj = null;
		dbObj = (WFDelegateModule) super.getObject(obj.getId());
		if (dbObj == null) {
			dbObj = obj;
		} else {
			BeanUtils.copyProperties(obj, dbObj,
					new String[] { "id", "version" });
		}
		super.saveObject(obj);
		return obj;
	}

	/**
	 * 根据代理Id来获取所有的委托对象
	 * 
	 * @param id
	 * @return
	 */
	public List<WFDelegateModule> getListBydelegateId(Long id) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("delegateId", id));
		return (List<WFDelegateModule>) this.getObjects(fsp);

	}

}
