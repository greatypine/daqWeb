package com.cnpc.pms.shortMessage.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.shortMessage.dao.MessageActionDao;
import com.cnpc.pms.shortMessage.dto.MessageActionDto;
import com.cnpc.pms.shortMessage.entity.MessageAction;
import com.cnpc.pms.shortMessage.entity.MessageType;
import com.cnpc.pms.shortMessage.manager.MessageActionManager;
import com.cnpc.pms.shortMessage.manager.MessageTypeManager;

public class MessageActionManagerImpl extends BizBaseCommonManager implements MessageActionManager{

	@Override
	public List<MessageAction> selectMessageAction(String messageTypeCode) {
		
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("messageTypeCode='" + messageTypeCode+"'"));
				
		if (lst_data != null && lst_data.size() > 0) {
			return (List<MessageAction>) lst_data;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> checkActionCodeIsRepeat(String messageType,String actionCodes) {
		MessageActionDao messageActionDao = (MessageActionDao)SpringHelper.getBean(MessageActionDao.class.getName());
		List<Map<String,Object>> list = null;
		try {
			list = messageActionDao.selectMessageAction(messageType,actionCodes);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public Map<String, Object> saveMessageAction(MessageAction messageA) {
		MessageActionManager meActionManager = (MessageActionManager)SpringHelper.getBean("messageActionManager");
		Map<String,Object> result = new HashMap<String,Object>();
		MessageAction messageAction = new MessageAction();
		messageAction.setActionCode(messageA.getActionCode());
		messageAction.setRemark(messageA.getRemark());
		messageAction.setMessageTypeCode(messageA.getMessageTypeCode());
		preObject(messageAction);
		meActionManager.saveObject(messageAction);
		return result;
	}

	@Override
	public List<MessageAction> selectMessageActionByActionCode(String actionCode) {
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("actionCode='" + actionCode+"'"));
		
		if (lst_data != null && lst_data.size() > 0) {
			return (List<MessageAction>) lst_data;
		}
		return null;
	}

}
