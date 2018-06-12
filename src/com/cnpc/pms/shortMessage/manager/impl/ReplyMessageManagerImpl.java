package com.cnpc.pms.shortMessage.manager.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.MessageAction;
import com.cnpc.pms.shortMessage.entity.MessageType;
import com.cnpc.pms.shortMessage.entity.ReplyMessage;
import com.cnpc.pms.shortMessage.manager.MessageActionManager;
import com.cnpc.pms.shortMessage.manager.MessageTypeManager;
import com.cnpc.pms.shortMessage.manager.ReplyMessageManager;
import com.cnpc.pms.shortMessage.utility.ReplyMessageExecuteAction;

public class ReplyMessageManagerImpl extends BizBaseCommonManager implements ReplyMessageManager{

	@Override
	public Map<String, Object> reciveMessageReply(ReplyMessageDto reDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		ReplyMessageManager reManager = (ReplyMessageManager)SpringHelper.getBean("replyMessageManager");
		MessageActionManager messageActionManager = (MessageActionManager) SpringHelper.getBean("messageActionManager");
		try {
			MessageAction ma = messageActionManager.selectMessageAction(reDto.getContent());//根据回复内容查询短信类型
			if(ma==null){
				//此处发短信告知正确的回复内容
				return result; 
			}
			reDto.setMessageType(ma.getMessgeType());
			reDto.setActionCode(reDto.getActionCode());
			this.saveReplyMessage(reDto);
			ReplyMessageExecuteAction.executeAction(reDto);
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> saveReplyMessage(ReplyMessageDto reDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		ReplyMessageManager reManager = (ReplyMessageManager)SpringHelper.getBean("replyMessageManager");
		
		ReplyMessage re = new ReplyMessage();
		re.setPhone(reDto.getPhone());
		re.setContent(reDto.getContent());
		re.setSpNumber(reDto.getSpNumber());
		preObject(re);
		reManager.saveObject(re);
		return result;
	}

	
	
}
