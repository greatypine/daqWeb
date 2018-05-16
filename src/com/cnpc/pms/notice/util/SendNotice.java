package com.cnpc.pms.notice.util;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.inter.manager.InterManager;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.entity.NoticeReciver;
import com.cnpc.pms.notice.manager.NoticeReciverManager;
import com.cnpc.pms.personal.manager.SendMessageManager; 

/**
 * 
 * @author gbl
 * 保存公告消息接受者并发送通知
 */
public class SendNotice implements Runnable {
	private Notice notice;
	private List<Map<String, Object>> reciver;
    public SendNotice(Notice notice,List<Map<String, Object>> reciver){
    	this.notice = notice;
    	this.reciver = reciver;
    }
	@Override
	public void run() {
		
		NoticeReciverManager nrm = (NoticeReciverManager)SpringHelper.getBean("noticeReciverManager");
		InterManager interManager = (InterManager)SpringHelper.getBean("interManager");
		SendMessageManager sendMessageManager = (SendMessageManager)SpringHelper.getBean("sendMessageManager");
		try {
			User user  = null;
			Object os = "";
			Object client_id = "";
			Object token="";
			Object employeeId ="";
			String storeNo="";
			Long storeId = 0L;
			Object mobilePhone = "";
			StringBuilder phoneSb = new StringBuilder();
			for(int i=0;i<reciver.size();i++){
				
				employeeId = reciver.get(i).get("employeeId");
				token = reciver.get(i).get("token");
				client_id = reciver.get(i).get("client_id");
				os = reciver.get(i).get("os");
				storeNo=reciver.get(i).get("storeNo")==null?"":String.valueOf(reciver.get(i).get("storeNo"));
				storeId = reciver.get(i).get("storeId")==null?0L:Long.parseLong(String.valueOf(reciver.get(i).get("storeId")));
				mobilePhone = reciver.get(i).get("mobilephone");
				if(mobilePhone!=null&&!"".equals(mobilePhone)){
					phoneSb.append(",").append(mobilePhone);
				}
				
				
				
				
				NoticeReciver nr = new NoticeReciver();
				
				
				
				nr.setNoticeNo(notice.getNoticeNo());
				nr.setIsRead(0);
				nr.setStoreNo(storeNo);
				nr.setStoreId(storeId);
				nr.setEmployeeNo(String.valueOf(employeeId));
				
				nr.setCreate_time(notice.getCreate_time());
				nr.setCreate_user(notice.getCreate_user());
				nr.setCreate_user_id(notice.getCreate_user_id());
				
				nr.setUpdate_time(notice.getUpdate_time());
				nr.setUpdate_user(notice.getUpdate_user());
				nr.setUpdate_user_id(notice.getUpdate_user_id());
				nrm.saveObject(nr);
				
				if(employeeId==null||token==null||client_id==null||os==null){
					continue;
				}
				if(notice.getGrade()==1){//高级
					user = new User();
					user.setOs(os.toString());
					user.setClient_id(client_id.toString());
					user.setToken(token.toString());
					//发送app通知
					NoticeSebdUtil.getInstance().pushNotice(user, notice);
					
					//发送短信
//					if(mobilePhone!=null&&!"".equals(mobilePhone)){
//						String resultString = interManager.commonSendMessage(mobilePhone.toString(), notice.getTitle(), null);
//						SendMessage sendMessage = new SendMessage();
//						sendMessage.setFunctionname("公告通知");
//						sendMessage.setMobilephone(mobilePhone.toString());
//						sendMessage.setCode(notice.getTitle());
//						sendMessage.setRcvmessage(resultString);
//						sendMessage.setMsgstatus(1L);//未使用 
//						sendMessageManager.saveSendMessage(sendMessage);
//					}
				}else if(notice.getGrade()==2){//中级
					
					user = new User();
					user.setOs(os.toString());
					user.setClient_id(client_id.toString());
					user.setToken(token.toString());
					//发送app通知
					NoticeSebdUtil.getInstance().pushNotice(user, notice);
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}

}
