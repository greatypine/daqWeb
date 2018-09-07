package com.cnpc.pms.personal.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.dto.StorexpandDTO;
import com.cnpc.pms.personal.entity.Storexpand;

public interface StorexpandManager extends IManager {
	/**
	 * 获取目标值分页信息
	 * @param conditions
	 * @return
	 */
	Map<String, Object> showOfficeData(QueryConditions conditions);
	
	Storexpand saveOrUpdateOfficeNetwork(StorexpandDTO storexpand);

	
	void insertOfficeNetwork(Storexpand saveStorexpand);
	
	/**
	 * 查询是否录入过合作店和自营店目标值
	 * @param cityname
	 * @return
	 */
	Map<String, Object> getTaskQuantityExist(String cityname);
	/**
	 * 查询是否录入过
	 * @param statistics
	 * @param cityname
	 * @return
	 */
	Map<String, Object> getStatistics(String statistics,String cityname);
	/**
	 * 通过id查询目标值信息
	 * @param id
	 * @return
	 */
	StorexpandDTO getStorexpandById(Long id);
	Storexpand getStorexpandByOriginId(Long id);
	
	/**
	 * 网络建设进展
	 * 2018年4月11
	 * @author gll
	 * @return
	 */
	public Map<String, Object> progressOfNetworkConstruction();
}
