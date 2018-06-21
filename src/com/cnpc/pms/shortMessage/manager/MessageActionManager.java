package com.cnpc.pms.shortMessage.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.shortMessage.dto.MessageActionDto;
import com.cnpc.pms.shortMessage.entity.MessageAction;

/**
 * 回复短信动作
 * @author gbl
 *
 */
public interface MessageActionManager extends IManager{
	
	/**
	 * 
	* @Title: selectMessageAction  
	* @Description: TODO  根据短信类型查询短信动作
	* 2018年6月12日
	* @param @param messageTypeCode
	* @param @return      
	* @return MessageAction 
	* @throws
	* @author gbl
	 */
	public List<MessageAction> selectMessageAction(String messageTypeCode);
	
	/**
	 * 
	* @Title: selectMessageActionByActionCode  
	* @Description: TODO 根据短信回复内容查询短信动作
	* 2018年6月20日
	* @param @param actionCode
	* @param @return      
	* @return List<MessageAction> 
	* @throws
	* @author gbl
	 */
	public List<MessageAction> selectMessageActionByActionCode(String actionCode);
	
	/**
	 * 
	* @Title: checkActionCodeIsRepeat  
	* @Description: TODO 检测短信回复编码是否有重复 
	* 2018年6月13日
	* @param @param actionCodes
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> checkActionCodeIsRepeat(String messageType,String actionCodes);
	
	/**
	 * 
	* @Title: saveMessageAction  
	* @Description: TODO 保存短信回复内容 
	* 2018年6月13日
	* @param @param messageActionDto
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> saveMessageAction(MessageAction messageAction);
}
