package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFModule;

public interface WFModuleManager extends IManager {
	public WFModule addTBModule(WFModule obj);

	public WFModule queryTBModule(Long id);

	public int deleteTBModule(Long id);

	public WFModule saveTBModule(WFModule obj);

	public List<WFModule> getWFModuleList();

	/**
	 * 根据一级分类Id检索下属的模块定义列表
	 * @param id
	 * @return
	 */
	public List<WFModule> getWFModuleListByLevel1Id(Long id);

	public List<WFModule> getWFModuleListByLevel2Id(Long id);

	public Long getModuleIdByCode(String code);
}
