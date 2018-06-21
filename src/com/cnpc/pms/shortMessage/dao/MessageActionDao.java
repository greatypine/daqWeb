package com.cnpc.pms.shortMessage.dao;

import java.util.List;
import java.util.Map;

/**
 * 回复短信动作
 * @author gbl
 *
 */
public interface MessageActionDao {
	
	/**
	 * 
	* @Title: selectMessageAction  
	* @Description: TODO 查询短信回复编码 
	* 2018年6月13日
	* @param @param actionCode
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectMessageAction(String messageType,String actionCode);
}
