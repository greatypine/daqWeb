package com.cnpc.pms.shortMessage.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.shortMessage.entity.SMSUserGroup;

/**
 * 
 * @author gbl
 *
 */
public interface SMSUserGroupManager extends IManager{
	
	/**
	 * 
	* @Title: saveUserGroup  
	* @Description: TODO 保存短信用户组
	* 2018年5月24日
	* @param @param smsUserGroup
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String,Object> saveUserGroup(SMSUserGroup smsUserGroup);
	
	/**
	 * 
	* @Title: checkUserGroupRepeat  
	* @Description: TODO 用户组查重
	* 2018年5月24日
	* @param @param smsUserGroup
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public List<Map<String,Object>> selectUserGroup(SMSUserGroup smsUserGroup);
	
	/**
	 * 
	* @Title: selectUserGroup  
	* @Description: TODO 根据编码查询用户组
	* 2018年6月20日
	* @param @param code
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectUserGroup(String code);
}
