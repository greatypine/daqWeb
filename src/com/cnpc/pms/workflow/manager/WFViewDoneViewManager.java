package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.dto.WFDoneParam;
import com.cnpc.pms.workflow.entity.WFViewDoneView;


public interface WFViewDoneViewManager extends IManager {
	
	/**
	 * 根据给定的查询参数,查询所有的可用单据及对应的访问URL.
	 * @param param
	 * @return
	 */
	public List<WFViewDoneView> getDoneSheets(WFDoneParam param) ;
	
	/**
	 * 根据给定的查询参数,查询所有的可用单据及对应的访问URL.
	 * @param param
	 * @return
	 */
	public List<WFViewDoneView> getFinishedSheets(WFDoneParam param) ;
}
