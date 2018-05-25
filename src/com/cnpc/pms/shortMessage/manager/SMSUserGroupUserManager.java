package com.cnpc.pms.shortMessage.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.UserGroup;
import com.cnpc.pms.shortMessage.dto.UserGroupUserDto;
import com.cnpc.pms.shortMessage.entity.SMSUserGroup;

/**
 * 
 * @author gbl
 *
 */
public interface SMSUserGroupUserManager extends IManager{
	
	/**
	 * 
	* @Title: saveUserGroupUser  
	* @Description: TODO 保存
	* 2018年5月24日
	* @param @param userGroup
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String,Object> saveUserGroupUser(SMSUserGroup userGroup);
}
