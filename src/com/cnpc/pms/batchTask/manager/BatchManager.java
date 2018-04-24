/**
 * gaobaolei
 */
package com.cnpc.pms.batchTask.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

/**
 * @author gaobaolei
 * ds 批处理
 */
public interface BatchManager extends IManager{
	
	/**
	 * 
	 * TODO 开始批处理 
	 * 2017年9月29日
	 * @author gaobaolei
	 * @param code 
	 */
	public Map<String, String> startBatchTask(String code);
	
	/**
	 * 
	 * TODO 查询批处理结果 
	 * 2017年9月29日
	 * @author gaobaolei
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, Object> queryBatchResult(String year,String month);
}
