package com.cnpc.pms.shortMessage.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;

public interface ShortMessageDao {
	
	/**
	 * 
	* @Title: selectSMSUserGroup  
	* @Description: TODO 查询短信用户组
	* 2018年5月23日
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectSMSUserGroup();
	
	/**
	 * 
	* @Title: selectSMSUserGroupUser  
	* @Description: TODO  查询用户组人员
	* 2018年5月23日
	* @param @param userGroup
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	 */
	public List<Map<String,Object>> selectSMSUserGroupUser(String userGroup);
	
	/**
	 * 
	* @Title: getOfflineEmployee  
	* @Description: TODO 查询线下员工
	* 2018年5月24日
	* @param @param String whereStr, PageInfo pageInfo
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public List<Map<String, Object>> getOfflineEmployee(String whereStr, PageInfo pageInfo);
	public List<Map<String, Object>> getAllOfflineEmployee();
	
	/**
	 * 
	* @Title: getStoreKeeperEmployee  
	* @Description: TODO  查询店长
	* 2018年5月25日
	* @param @param whereStr
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	 */
	public List<Map<String, Object>> getStoreKeeperEmployee(String whereStr, PageInfo pageInfo);
	public List<Map<String, Object>> getStoreKeeperEmployee();
	
	
	/**
	 * 
	* @Title: getOnlineEmployee  
	* @Description: TODO 查询线上员工
	* 2018年5月24日
	* @param @param String whereStr, PageInfo pageInfo
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public List<Map<String, Object>> getOnlineEmployee(String whereStr, PageInfo pageInfo);
	public List<Map<String, Object>> getAllOnlineEmployee();
	
	/**
	 * 
	* @Title: getCustomer  
	* @Description: TODO 查询用户
	* 2018年5月24日
	* @param @param String name, PageInfo pageInfo
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public List<Map<String, Object>> getCustomer(String name, PageInfo pageInfo);
	public List<Map<String, Object>> getAllCustomer();

	/**
	 * @Description  获取外部人员
	 * @author gbl
	 * @date 2018/8/7 9:55
	 **/

	public List<Map<String, Object>> getOutSider(String name, PageInfo pageInfo);
	public List<Map<String, Object>> getAllOutSider();
}
