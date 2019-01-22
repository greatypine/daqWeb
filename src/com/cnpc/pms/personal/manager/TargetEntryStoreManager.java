package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.dto.UserProfileDto;
import com.cnpc.pms.personal.entity.TargetEntry;
import com.cnpc.pms.personal.entity.TargetEntryStore;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface TargetEntryStoreManager extends IManager {
	/**
	 * 获取目标值分页信息
	 * @param conditions
	 * @return
	 */
	Map<String, Object> showTarStoregetData(QueryConditions conditions);

	TargetEntry saveOrUpdateTargetEntry(TargetEntry targetEntry);

	public Map<String, Object> exportOrder(TargetEntryStore targetEntryStore);

	public Map<String, Object> exportOrderAll(String startTime,String endTime);

	public String saveTargetEntryStore(List<File> lst_import_excel) throws Exception;



	void insertTargetEntry(TargetEntry saveTargetEntry);

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
//	Map<String, Object> getByTarget(String statistics, String cityname);
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
