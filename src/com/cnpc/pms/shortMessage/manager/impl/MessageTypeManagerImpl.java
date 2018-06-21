package com.cnpc.pms.shortMessage.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.manager.NoticeManager;
import com.cnpc.pms.shortMessage.dao.MessageTypeDao;
import com.cnpc.pms.shortMessage.dao.ShortMessageDao;
import com.cnpc.pms.shortMessage.dto.MessageActionDto;
import com.cnpc.pms.shortMessage.dto.MessageTypeDto;
import com.cnpc.pms.shortMessage.entity.MessageAction;
import com.cnpc.pms.shortMessage.entity.MessageType;
import com.cnpc.pms.shortMessage.manager.MessageActionManager;
import com.cnpc.pms.shortMessage.manager.MessageTypeManager;
import com.cnpc.pms.slice.entity.Area;

public class MessageTypeManagerImpl extends BizBaseCommonManager implements MessageTypeManager{

	@Override
	public MessageType selectMessageType(String type) {
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("code='" + type+"'"));
		
		if (lst_data != null && lst_data.size() > 0) {
			return (MessageType) lst_data.get(0);
		}
		return null;
	}
	
	public Map<String,Object> queryMessageType(QueryConditions queryConditions){
		MessageTypeDao messageTypeDao = (MessageTypeDao)SpringHelper.getBean(MessageTypeDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        StringBuilder citySb = new StringBuilder(); 
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if("name".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	sb_where.append(" and a.name like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",messageTypeDao.selectMessageType(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> saveMessageType(MessageType messageT) {
		MessageActionManager messageActionManager = (MessageActionManager)SpringHelper.getBean("messageActionManager");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			MessageType messageType  = null;
			if(messageT.getId()!=null){
				 messageType = (MessageType)this.getObject(messageT.getId());
				List<MessageAction> list  = messageActionManager.selectMessageAction(messageType.getCode());
				if(list!=null){
					for(int i=0;i<list.size();i++){
						MessageAction ma = list.get(i);
						messageActionManager.remove(ma);
					}
				}
				
			}else{
				messageType = new MessageType();
			}
				
				messageType.setCode(messageT.getCode());
				messageType.setName(messageT.getName());
				messageType.setRemark(messageT.getRemark());
				preObject(messageType);
				saveObject(messageType);
				List<MessageAction> list = messageT.getChildrens();
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						messageActionManager.saveMessageAction(list.get(i));
					}
				}
			
			
			result.put("code", CodeEnum.success.getValue());
			result.put("message", CodeEnum.success.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", CodeEnum.error.getValue());
			result.put("message", CodeEnum.error.getDescription());
			return result;
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectMessageTypeById(Long id) {
		
		MessageTypeDao messageTypeDao = (MessageTypeDao)SpringHelper.getBean(MessageTypeDao.class.getName());
		List<Map<String,Object>> list = null;
		try {
			list = messageTypeDao.selectMessageTypeById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> selectMessageType() {
		
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("status=0"));
		
		if (lst_data != null && lst_data.size() > 0) {
			return (List<Map<String, Object>>) lst_data;
		}
		return null;
	}



}
