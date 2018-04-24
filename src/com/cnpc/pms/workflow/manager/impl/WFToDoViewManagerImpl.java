package com.cnpc.pms.workflow.manager.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.dao.core.IDAORoot;
import com.cnpc.pms.base.exception.PMSManagerException;
import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.IJoin;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.workflow.dto.WFToDoParam;
import com.cnpc.pms.workflow.dto.WFToDoSheetIds;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFToDoView;
import com.cnpc.pms.workflow.manager.WFToDoViewManager;

/**
 * 访问代办信息视图的管理器
 * 
 * @author liujunsong
 * 
 */
public class WFToDoViewManagerImpl extends BaseManagerImpl implements
		WFToDoViewManager {

	private static Long ALL_TODO_TYPE = -1L; //-1代表本用户所有的代码
	/**
	 * 根据给定的查询参数,查询所有的可用单据及对应的访问URL.
	 * -1代表所有类型
	 * 其余代表对应的待办类型
	 * @param param
	 * @return
	 */
	public List getToDoSheets(WFToDoParam param) {
		FSP fsp = new FSP();
		if (param.getToDoType().equals(ALL_TODO_TYPE)) {
			fsp
					.setUserFilter(FilterFactory.getSimpleFilter("moduleCode",
							param.getModuleCode()).appendAnd(
							FilterFactory.getSimpleFilter("userId", param
									.getUserId())));
		} else {
			fsp.setUserFilter(FilterFactory.getSimpleFilter("moduleCode",
					param.getModuleCode()).appendAnd(
					FilterFactory.getSimpleFilter("userId", param.getUserId())
							.appendAnd(
									FilterFactory.getSimpleFilter("toDoType",
											param.getToDoType()))));
		}
		// 按照最新实例排序
		fsp.setSort(new Sort("sheetId", Sort.DESC));
		List<WFToDoView> list = (List<WFToDoView>) this.getObjects(fsp);
		if (list == null || list.size() == 0) {
			return null;
		}

		return list;
	}

}
