package com.cnpc.pms.shortMessage.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.MessageAction;
import com.cnpc.pms.shortMessage.entity.ReplyMessage;

/**
 * 短信回复
 * @author gbl
 *
 */
public interface ReplyMessageManager extends IManager{
	
	/**
	 * 
	* @Title: saveReplyMessage  
	* @Description: TODO 保存短息回复 
	* 2018年6月12日
	* @param @param reDto
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> saveReplyMessage(ReplyMessageDto reDto);
	
	/**
	 * 
	* @Title: reciveMessageReply  
	* @Description: TODO 接收短信回复 
	* 2018年6月12日
	* @param @param reDto
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> reciveMessageReply(ReplyMessageDto reDto);
	
	/**
	 * 
	* @Title: queryReplyMessage  
	* @Description: TODO 
	* 2018年6月25日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> queryReplyMessage(QueryConditions queryConditions);
}
