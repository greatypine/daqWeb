package com.cnpc.pms.shortMessage.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.shortMessage.dto.MessageTypeDto;
import com.cnpc.pms.shortMessage.entity.MessageType;
import com.cnpc.pms.slice.entity.Area;

/**
 * 短信类型
 * @author gbl
 *
 */
public interface MessageTypeManager extends IManager{
	
	
	public MessageType selectMessageType(String type);
	
	/**
	 * 
	* @Title: queryMessageType  
	* @Description: TODO 查询短信类型 
	* 2018年6月13日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> queryMessageType(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: saveMessageType  
	* @Description: TODO 保存短信类型
	* 2018年6月13日
	* @param @param messageTypeDto
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> saveMessageType(MessageType messageType);
	
	/**
	 * 
	* @Title: selectMessageTypeById  
	* @Description: TODO 根据短信类型ID查询 
	* 2018年6月13日
	* @param @param id
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectMessageTypeById(Long id);
	
	/**
	 * 
	* @Title: selectMessageType  
	* @Description: TODO  查询短信类型
	* 2018年6月14日
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectAllMessageType();
	
}
