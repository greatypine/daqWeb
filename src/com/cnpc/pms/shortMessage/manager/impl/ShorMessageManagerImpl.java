package com.cnpc.pms.shortMessage.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.inter.manager.InterManager;
import com.cnpc.pms.notice.dao.NoticeDao;
import com.cnpc.pms.personal.entity.SendMessage;
import com.cnpc.pms.personal.manager.SendMessageManager;
import com.cnpc.pms.shortMessage.dao.ShortMessageDao;
import com.cnpc.pms.shortMessage.entity.ShortMessage;
import com.cnpc.pms.shortMessage.manager.ShortMessageManager;

public class ShorMessageManagerImpl extends BizBaseCommonManager implements ShortMessageManager{

	@Override
	public List<Map<String, Object>> getSMSUserGroup() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = shortMessageDao.selectSMSUserGroup();
		} catch (Exception e) {
			e.getMessage();
			return list;
		}
		return list;
	}

	@Override
	public Map<String, Object> StatisticalSMSCost(String userGroupCode) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<>();
		
		try {
			list = shortMessageDao.selectSMSUserGroupUser(userGroupCode);
		    float cost = 0f;
		    cost = list.size()*0.04f;
		    result.put("cost", cost);
		} catch (Exception e) {
			e.getMessage();
			result.put("cost", 0f);
			return result;
		}
		return result;
		
	}

	@Override
	public Map<String, Object> saveShortMessage(ShortMessage shortMessage) {
		
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		InterManager interManager = (InterManager)SpringHelper.getBean("interManager");
		SendMessageManager sendMessageManager = (SendMessageManager)SpringHelper.getBean("sendMessageManager");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<>();
		
		try {
			list = shortMessageDao.selectSMSUserGroupUser(shortMessage.getUserGroupCode());
			ShortMessage sm = new ShortMessage();
			sm.setCheckStatus(0);
			String timestamp = String.valueOf(System.currentTimeMillis());
			sm.setCode(timestamp);
			sm.setContent(shortMessage.getContent());
			sm.setSignature(shortMessage.getSignature());
			preObject(sm);
			saveObject(sm);//保存短信
			StringBuilder[] mobilePhoneArr = new StringBuilder[list.size()/50+1];
			for(int i=0;i<mobilePhoneArr.length;i++){//50个号码一组
				int begin_index = i*50;
				int end_index = (i+1)*50;
				 StringBuilder sb = new StringBuilder();
				for(int j=begin_index;j<end_index;j++){
					if(j==list.size()){
						break;
					}
				    String mobilePhone = String.valueOf(list.get(j).get("mobilePhone"));
				    sb.append(","+mobilePhone);
				}
				sb = sb.deleteCharAt(0);
				mobilePhoneArr[i] = sb;
			}
			
			
			for(int i=0;i<mobilePhoneArr.length;i++){
				String resultString = interManager.commonSendMessage(mobilePhoneArr[i].toString(),shortMessage.getContent(), null);
				SendMessage sendMessage = new SendMessage();
				sendMessage.setFunctionname("自定义短信");
				sendMessage.setMobilephone(mobilePhoneArr[i].toString());
				sendMessage.setCode(timestamp);
				sendMessage.setRcvmessage(resultString);
				sendMessage.setMsgstatus(1L);//未使用 
				sendMessageManager.saveSendMessage(sendMessage);
				Thread.sleep(2000);
				System.out.println("sending short message >>>>>>");
			}
			
			
			
			result.put("code",CodeEnum.success.getValue());
			result.put("message","发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message","发送失败");
			return result;
		}
		return result;
	}


}
