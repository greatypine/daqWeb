package com.cnpc.pms.shortMessage.manager.impl;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.shortMessage.entity.MessageAction;
import com.cnpc.pms.shortMessage.entity.MessageType;
import com.cnpc.pms.shortMessage.manager.MessageActionManager;
import com.cnpc.pms.shortMessage.manager.MessageTypeManager;

public class MessageActionManagerImpl extends BizBaseCommonManager implements MessageActionManager{

	@Override
	public MessageAction selectMessageAction(String actionCode) {
		
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("actionCode='" + actionCode+"'"));
				
		if (lst_data != null && lst_data.size() > 0) {
			return (MessageAction) lst_data.get(0);
		}
		return null;
	}

}
