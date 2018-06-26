package com.cnpc.pms.shortMessage.manager.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.shortMessage.dao.ReplyMessageDao;
import com.cnpc.pms.shortMessage.dao.ShortMessageDao;
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
			List<MessageAction> ma = messageActionManager.selectMessageActionByActionCode(reDto.getContent());//根据回复内容查询短信类型
			if(ma==null){
				//此处发短信告知正确的回复内容
				result.put("status",CodeEnum.nullData.getValue());
				return result; 
			}
			reDto.setMessageType(ma.get(0).getMessageTypeCode());
			reDto.setActionCode(reDto.getActionCode());
			this.saveReplyMessage(reDto);
			ReplyMessageExecuteAction.executeAction(reDto);//根据返回值执行具体逻辑业务
			result.put("status",CodeEnum.success.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status",e.getMessage());
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
		re.setMessageType(reDto.getMessageType());
		preObject(re);
		reManager.saveObject(re);
		return result;
	}

	
	public Map<String,Object> queryReplyMessage(QueryConditions queryConditions){
		ReplyMessageDao reDao = (ReplyMessageDao)SpringHelper.getBean(ReplyMessageDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        StringBuilder citySb = new StringBuilder(); 
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if("messageType".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	sb_where.append(" and b.name like '"+map_where.get("value")+"'");
            }else if("phone".equals(map_where.get("key"))&& null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	sb_where.append(" and a.phone like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",reDao.selectReplyMessage(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}
}
