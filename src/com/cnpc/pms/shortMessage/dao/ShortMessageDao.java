package com.cnpc.pms.shortMessage.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;

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

}
