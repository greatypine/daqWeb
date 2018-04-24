package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFDelegateModule;

public interface WFDelegateModuleManager extends IManager {
	/**
	 * 增加一个委托代理和模块之间的关联
	 * @param obj
	 * @return
	 */
	public WFDelegateModule addWFDelegateModule(WFDelegateModule obj);

	/**
	 * 删除一个指定的关联管理
	 * @param id
	 * @return
	 */
	public Boolean deleteWFDelegateModule(Long id);
	
	/**
	 * 按照Id来查询关联关系对象
	 * @param id
	 * @return
	 */
	public WFDelegateModule queryWFDelegateModule(Long id);
	
	/**
	 * 保存一个关联对象(未使用)
	 * @param obj
	 * @return
	 */
	public WFDelegateModule savaWFDelegateModule(WFDelegateModule obj);
	
	/**
	 * 根据代理Id来获取所有的委托对象
	 * @param id
	 * @return
	 */
	public List<WFDelegateModule> getListBydelegateId(Long id);
}
