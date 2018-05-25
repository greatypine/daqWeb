package com.cnpc.pms.shortMessage.utility;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.inter.manager.InterManager;
import com.cnpc.pms.personal.entity.SendMessage;
import com.cnpc.pms.personal.manager.SendMessageManager;
import com.cnpc.pms.shortMessage.entity.ShortMessage;

public class SendShortMessageTask implements Runnable{
	
	List<Map<String,Object>> useres;
	ShortMessage shortMessage;
	
	

	
	public SendShortMessageTask(List<Map<String,Object>> useres,ShortMessage shortMessage){
		this.useres = useres;
		this.shortMessage = shortMessage;
	}
	@Override
	public void run() {
		InterManager interManager = (InterManager)SpringHelper.getBean("interManager");
		SendMessageManager sendMessageManager = (SendMessageManager)SpringHelper.getBean("sendMessageManager");
		for(int i=0;i<useres.size();i++){
			Object InvitationCode = useres.get(i).get("inviteCode");
			Object mobilephone = useres.get(i).get("mobilePhone");
			Object name  =  useres.get(i).get("userName");
			if(InvitationCode==null){
				continue;
			}
			
			
			String newContent = shortMessage.getContent().replaceAll("XXXXXX",String.valueOf(InvitationCode));
			newContent = newContent.replaceAll("XXX",String.valueOf(name));
			String resultString = interManager.commonSendMessage(String.valueOf(mobilephone),newContent, null);
			SendMessage sendMessage = new SendMessage();
			sendMessage.setFunctionname(shortMessage.getTitle());
			sendMessage.setMobilephone(String.valueOf(mobilephone));
			sendMessage.setCode(String.valueOf(InvitationCode));
			sendMessage.setRcvmessage(resultString);
			sendMessage.setMsgstatus(1L);//未使用 
			sendMessageManager.saveSendMessage(sendMessage);
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println("sending short message >>>>>>");
		}
		
	}

}
