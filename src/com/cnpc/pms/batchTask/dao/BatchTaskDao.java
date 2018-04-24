/**
 * gaobaolei
 */
package com.cnpc.pms.batchTask.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;

/**
 * @author gaobaolei
 * ds 批处理
 */
public interface BatchTaskDao extends IDAO{
	
	/**
	 * 
	 * TODO 查询结果 
	 * 2017年9月29日
	 * @author gaobaolei
	 * @param year
	 * @param month
	 */
	public Map<String,Object> queryBatchResult(Integer year,Integer month);
	
	public List<Map<String, Object>> queryBatchResult(String sql);
}
