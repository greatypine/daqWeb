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
	
	/**
	 * 
	* @Title: getOfflineEmployee  
	* @Description: TODO 线下员工
	* 2018年5月24日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String, Object> getOfflineEmployee(QueryConditions queryConditions);
	/**
	 * 
	* @Title: getAllOfflineEmployee  
	* @Description: TODO 全部线下员工 
	* 2018年5月24日
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String, Object> getAllOfflineEmployee();
	
	/**
	 * 
	* @Title: getOnlineEmployee  
	* @Description: TODO 线上员工
	* 2018年5月24日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String, Object> getOnlineEmployee(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: getAllOnlineEmployee  
	* @Description: TODO 全部线上员工
	* 2018年5月24日
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String, Object> getAllOnlineEmployee();
	
	/**
	 * 
	* @Title: getCusotmer  
	* @Description: TODO 用户
	* 2018年5月24日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String, Object> getCustomer(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: getAllCusotmer  
	* @Description: TODO 全部用户
	* 2018年5月24日
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String, Object> getAllCustomer();
	
	/**
	 * 
	* @Title: getStoreKeeperEmployee  
	* @Description: TODO 查询店长
	* 2018年5月25日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String, Object> getStoreKeeperEmployee(QueryConditions queryConditions);
	
	public Map<String, Object> getAllStoreKeeperEmployee();
	
	/**
	 * 
	* @Title: sendShortMessage  
	* @Description: TODO 根据不同类型短信类型发送短信 
	* 2018年6月20日
	* @param @param shortMessage
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> sendShortMessage(ShortMessage shortMessage);

	/**
	 * @Description 公共的发送短信
	 * @author gbl
	 * @date 2018/7/17 15:37
	 **/

	public Map<String,Object> commonSendShortMessage(Map<String,Object> param);
	
}
