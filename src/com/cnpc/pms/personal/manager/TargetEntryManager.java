package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.dto.StorexpandDTO;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.entity.TargetEntry;

import java.util.Map;

public interface TargetEntryManager extends IManager {
	/**
	 * 获取目标值分页信息
	 * @param conditions
	 * @return
	 */
	Map<String, Object> showTargetData(QueryConditions conditions);

	TargetEntry saveOrUpdateTargetEntry(TargetEntry targetEntry);


	void insertTargetEntry(TargetEntry saveTargetEntry);

	Map<String, Object> getUserInfo();

	/**
	 * 查询是否录入过合作店和自营店目标值
	 * @param cityname
	 * @return
	 */
	Map<String, Object> getTaskQuantityExist(String cityname);
	/**
	 * 查询是否录入过
	 * @param statistics
	 * @param
	 * @return
	 */
	Map<String, Object> getByTarget(String statistics, String deptName,String channelName,TargetEntry targetEntry);
	/**
	 * 通过id查询目标值信息
	 * @param id
	 * @return
	 */
	TargetEntry getTargetEntryById(Long id);
	TargetEntry getTargetEntryByOriginId(Long id);
	
	/**
	 * 网络建设进展
	 * 2018年4月11
	 * @author gll
	 * @return
	 */
	public Map<String, Object> progressOfNetworkConstruction();
}
