package com.cnpc.pms.shortMessage.manager;

import com.cnpc.pms.base.manager.IManager;
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
	* @Description: TODO  根据短信回复内容查询短信动作
	* 2018年6月12日
	* @param @param actionCode
	* @param @return      
	* @return MessageAction 
	* @throws
	* @author gbl
	 */
	public MessageAction selectMessageAction(String actionCode);
}
