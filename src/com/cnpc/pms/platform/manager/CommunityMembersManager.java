package com.cnpc.pms.platform.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.dynamic.entity.DynamicDto;

public interface CommunityMembersManager extends IManager{
	/**
	 *查询全国/省/市的新增社员人数(当月),社员总数
	 * @param dynamicDto
	 * @return
	 */
	Map<String, Object> getNewMembersCount(DynamicDto dynamicDto);
	/**
	 * 查询全国/省/市的(新增社员人数/社员总数)(近7日)
	 * @param dynamicDto
	 * @return
	 */
	Map<String, Object> getMembersWeekCount(DynamicDto dynamicDto);
}
