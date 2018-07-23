package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;

/**
 * @author gaoll
 */
public interface SyncRecordDao extends IDAO {
	
	/**
	 * 查询近六周线上人员入职情况（总部、城市公司）
	 * 2018年7月13
	 * @author gaoll
	 * @param org
	 * return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getjoinHumanresources(String org);
	
	
	/**
	 * 查询近六周线上人员离职情况（总部、城市公司）
	 * 2018年7月13
	 * @author gaoll
	 * @param org
	 * return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getleftHumanresources(String org);
	
	/**
	 * 查询近六周线上人员总人数（总部、城市公司）
	 * 2018年7月13
	 * @author gaoll
	 * @param org
	 * return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getTotalHumanresources(String org);
	

}
