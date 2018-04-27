package com.cnpc.pms.notice.util;

import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.utils.PropertiesValueUtil;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 
 * @author gbl
 * 公告发送器
 */
public class NoticeSebdUtil {
	
	private NoticeSebdUtil(){}
	 
	 private static class ClassLazyLoader{
		 private static final NoticeSebdUtil singleton = new NoticeSebdUtil();
	 }
	 
	 public static final NoticeSebdUtil getInstance(){
		 return ClassLazyLoader.singleton;
	 }
	/**
	 * 
	* @Title: pushNotice  
	* @Description: TODO 公告发送 
	* 2018年4月20日
	* @param @param user
	* @param @param notice
	* @param @return      
	* @return Boolean 
	* @throws  
	* @author gbl
	 */
	 public static final Boolean pushNotice(User user, Notice notice) {
	        try{
	            PropertiesValueUtil valueUtil = new PropertiesValueUtil("conf/apppush.properties");
	            String url = valueUtil.getStringValue("url");
	            String appKey = valueUtil.getStringValue("appKey");
	            String masterSecret = valueUtil.getStringValue("masterSecret");
	            String appId = valueUtil.getStringValue("appId");
	            IGtPush push = new IGtPush(url, appKey, masterSecret);
	            if(user.getOs() != null){
	                if("android".equals(user.getOs().toLowerCase())){
	                    NotificationTemplate template = new NotificationTemplate();
	                    template.setAppId(appId);
	                    template.setAppkey(appKey);
	                    template.setTransmissionContent(notice.getContent());
	                    template.setTransmissionType(1);
	                    template.setTitle(notice.getTitle());
	                    template.setText(notice.getContent());
	                    template.setLogo("");
	                    template.setLogoUrl("");

	                    // 定义"message"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
	                    SingleMessage singleMessage = new SingleMessage ();
	                    singleMessage.setData(template);

	                    singleMessage.setOffline(true);
	                    singleMessage.setOfflineExpireTime(1000 * 600);

	                    Target target = new Target();
	                    target.setAppId(appId);
	                    target.setClientId(user.getClient_id());
	                    IPushResult ret = push.pushMessageToSingle(singleMessage, target);
	                    return ret != null;
	                }else{
	                    APNPayload.DictionaryAlertMsg dictionaryAlertMsg = new APNPayload.DictionaryAlertMsg();
	                    dictionaryAlertMsg.setTitle(notice.getTitle());
	                    dictionaryAlertMsg.setBody(notice.getContent());

	                    APNPayload apnPayload = new APNPayload();
	                    apnPayload.setAutoBadge("-1");
	                    apnPayload.setAlertMsg(dictionaryAlertMsg);
	                    TransmissionTemplate transmissionTemplate = new TransmissionTemplate();
	                    transmissionTemplate.setAppId(appId);
	                    transmissionTemplate.setAppkey(appKey);
	                    transmissionTemplate.setTransmissionContent(notice.getContent());
	                    transmissionTemplate.setTransmissionType(1);
	                    transmissionTemplate.setAPNInfo(apnPayload);

	                    SingleMessage singleMessage = new SingleMessage ();
	                    singleMessage.setData(transmissionTemplate);

	                    singleMessage.setOffline(true);
	                    singleMessage.setOfflineExpireTime(1000 * 600);

	                    Target target1 = new Target();
	                    target1.setAppId(appId);
	                    target1.setClientId(user.getClient_id());
	                    if(user.getToken() == null || "".equals(user.getToken())){
	                        return false;
	                    }

	                    IPushResult ret1 = push.pushAPNMessageToSingle(appId, user.getToken(), singleMessage);
	                    return ret1 != null;
	                }
	            }
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        return false;
	    }
}
