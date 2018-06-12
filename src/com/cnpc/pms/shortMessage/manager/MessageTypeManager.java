package com.cnpc.pms.shortMessage.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.shortMessage.entity.MessageType;

/**
 * 短信类型
 * @author gbl
 *
 */
public interface MessageTypeManager extends IManager{
	
	
	public MessageType selectMessageType(String type);
}
