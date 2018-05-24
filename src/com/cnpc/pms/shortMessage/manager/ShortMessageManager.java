package com.cnpc.pms.shortMessage.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.shortMessage.entity.ShortMessage;

/**
 * 短信
 * @author gbl
 *
 */
public interface ShortMessageManager extends IManager{
	
	/**
	 * 
	* @Title: getSMSUserGroup  
	* @Description: TODO 查询短信接收用户组
	* 2018年5月23日
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> getSMSUserGroup();
	
	/**
	 * 
	* @Title: StatisticalSMSCost  
	* @Description: TODO  统计短信成本
	* 2018年5月23日
	* @param @param userGroupCode
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> StatisticalSMSCost(String userGroupCode);
	
	/**
	 * 
	* @Title: saveShortMessage  
	* @Description: TODO 发送短信 
	* 2018年5月23日
	* @param @param shortMessage
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> saveShortMessage(ShortMessage shortMessage);

}
