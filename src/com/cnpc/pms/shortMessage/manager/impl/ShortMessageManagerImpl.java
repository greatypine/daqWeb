package com.cnpc.pms.shortMessage.manager.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.inter.manager.InterManager;
import com.cnpc.pms.notice.dao.NoticeDao;
import com.cnpc.pms.personal.entity.SendMessage;
import com.cnpc.pms.personal.manager.SendMessageManager;
import com.cnpc.pms.shortMessage.dao.ShortMessageDao;
import com.cnpc.pms.shortMessage.entity.ShortMessage;
import com.cnpc.pms.shortMessage.manager.SMSUserGroupManager;
import com.cnpc.pms.shortMessage.manager.ShortMessageManager;
import com.cnpc.pms.shortMessage.utility.SendShortMessageTask;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ShortMessageManagerImpl extends BizBaseCommonManager implements ShortMessageManager{

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
		Map<String,Object> result = new HashMap<String,Object>();
		
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
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			String[] userGroupArray = shortMessage.getUserGroupCode().split(",");
			String timestamp = String.valueOf(System.currentTimeMillis());
			shortMessage.setCode(timestamp);
			
			for(int i=0;i<userGroupArray.length;i++){
				ShortMessage sm = new ShortMessage();
				sm.setCheckStatus(0);
				sm.setCode(timestamp);
				sm.setTitle(shortMessage.getTitle());
				sm.setType(shortMessage.getType());
				sm.setContent(shortMessage.getContent());
				sm.setSignature(shortMessage.getSignature());
				sm.setUserGroupCode(userGroupArray[i].replaceAll("'", ""));
				preObject(sm);
				saveObject(sm);//保存短信
			}
			
			
			list = shortMessageDao.selectSMSUserGroupUser(shortMessage.getUserGroupCode());
			Thread thread = new Thread(new SendShortMessageTask(list, shortMessage));
			thread.start();
//			StringBuilder[] mobilePhoneArr = new StringBuilder[list.size()/50+1];
//			for(int i=0;i<mobilePhoneArr.length;i++){//50个号码一组
//				int begin_index = i*50;
//				int end_index = (i+1)*50;
//				 StringBuilder sb = new StringBuilder();
//				for(int j=begin_index;j<end_index;j++){
//					if(j==list.size()){
//						break;
//					}
//				    String mobilePhone = String.valueOf(list.get(j).get("mobilePhone"));
//				    sb.append(","+mobilePhone);
//				}
//				sb = sb.deleteCharAt(0);
//				mobilePhoneArr[i] = sb;
//			}
//			
//			
//			for(int i=0;i<mobilePhoneArr.length;i++){
//				String resultString = interManager.commonSendMessage(mobilePhoneArr[i].toString(),shortMessage.getContent(), null);
//				SendMessage sendMessage = new SendMessage();
//				sendMessage.setFunctionname("自定义短信");
//				sendMessage.setMobilephone(mobilePhoneArr[i].toString());
//				sendMessage.setCode(timestamp);
//				sendMessage.setRcvmessage(resultString);
//				sendMessage.setMsgstatus(1L);//未使用 
//				sendMessageManager.saveSendMessage(sendMessage);
//				Thread.sleep(2000);
//				System.out.println("sending short message >>>>>>");
//			}
			
			
			
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

	@Override
	public Map<String, Object> getOfflineEmployee(QueryConditions queryConditions) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
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
            	sb_where.append(" and name like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",shortMessageDao.getOfflineEmployee(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> getOnlineEmployee(QueryConditions queryConditions) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
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
            	sb_where.append(" and name like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",shortMessageDao.getOnlineEmployee(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> getCustomer(QueryConditions queryConditions) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
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
            	sb_where.append(" and name like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",shortMessageDao.getCustomer(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> getAllOfflineEmployee() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			list = shortMessageDao.getAllOfflineEmployee();
		   
		    result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data",list); 
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllOnlineEmployee() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			list = shortMessageDao.getAllOnlineEmployee();
		   
		    result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data",list); 
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getAllCustomer() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			list = shortMessageDao.getAllCustomer();
		   
		    result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data",list);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getStoreKeeperEmployee(QueryConditions queryConditions) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
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
            	sb_where.append(" and name like '"+map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",shortMessageDao.getStoreKeeperEmployee(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> getAllStoreKeeperEmployee() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			list = shortMessageDao.getStoreKeeperEmployee();
		   
		    result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data",list); 
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> sendShortMessage(ShortMessage shortMessage) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		InterManager interManager = (InterManager)SpringHelper.getBean("interManager");
		SendMessageManager sendMessageManager = (SendMessageManager)SpringHelper.getBean("sendMessageManager");
		SMSUserGroupManager smsUserGroupManager = (SMSUserGroupManager)SpringHelper.getBean("sMSUserGroupManager");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> smsuserGroup = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			smsuserGroup = smsUserGroupManager.selectUserGroup(shortMessage.getType());
			String timestamp = String.valueOf(System.currentTimeMillis());
			shortMessage.setCode(timestamp);
			StringBuilder userGroupSb = new StringBuilder();
			for(int i=0;i<smsuserGroup.size();i++){
				ShortMessage sm = new ShortMessage();
				sm.setCheckStatus(0);
				sm.setCode(timestamp);
				sm.setTitle(shortMessage.getTitle());
				sm.setType(shortMessage.getType());
				sm.setContent(shortMessage.getContent());
				sm.setSignature(shortMessage.getSignature());
				sm.setUserGroupCode(String.valueOf(smsuserGroup.get(i)));
				preObject(sm);
				saveObject(sm);//保存短信
				
				userGroupSb.append(",'").append(smsuserGroup.get(i)).append("'");
			}
			
			userGroupSb = userGroupSb.deleteCharAt(0);
			list = shortMessageDao.selectSMSUserGroupUser(userGroupSb.toString());
			//Thread thread = new Thread(new SendShortMessageTask(list, shortMessage));
			//thread.start();
//			StringBuilder[] mobilePhoneArr = new StringBuilder[list.size()/50+1];
//			for(int i=0;i<mobilePhoneArr.length;i++){//50个号码一组
//				int begin_index = i*50;
//				int end_index = (i+1)*50;
//				 StringBuilder sb = new StringBuilder();
//				for(int j=begin_index;j<end_index;j++){
//					if(j==list.size()){
//						break;
//					}
//				    String mobilePhone = String.valueOf(list.get(j).get("mobilePhone"));
//				    sb.append(","+mobilePhone);
//				}
//				sb = sb.deleteCharAt(0);
//				mobilePhoneArr[i] = sb;
//			}
//			
//			
//			for(int i=0;i<mobilePhoneArr.length;i++){
//				String resultString = interManager.commonSendMessage(mobilePhoneArr[i].toString(),shortMessage.getContent(), null);
//				SendMessage sendMessage = new SendMessage();
//				sendMessage.setFunctionname("自定义短信");
//				sendMessage.setMobilephone(mobilePhoneArr[i].toString());
//				sendMessage.setCode(timestamp);
//				sendMessage.setRcvmessage(resultString);
//				sendMessage.setMsgstatus(1L);//未使用 
//				sendMessageManager.saveSendMessage(sendMessage);
//				Thread.sleep(2000);
//				System.out.println("sending short message >>>>>>");
//			}
			
			
			
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

	@Override
	public Map<String, Object> commonSendShortMessage(Map<String,Object> param) {

		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		InterManager interManager = (InterManager)SpringHelper.getBean("interManager");
		SendMessageManager sendMessageManager = (SendMessageManager)SpringHelper.getBean("sendMessageManager");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();


		try {
			String type= param.get("type")==null?"":String.valueOf(param.get("type"));//短信类型
			//String signature = param.get("signature")==null?"":String.valueOf(param.get("signature"));//短信签名
			String timestamp = String.valueOf(System.currentTimeMillis());
            String inviteCode = param.get("inviteCode")==null?"":String.valueOf(param.get("inviteCode"));
			list.add(param);



			ShortMessage sm = new ShortMessage();
			if("SYYQM".equals(type)){//社员邀请码


                // 外部人员邀请码以8或者9开头
                String regEx = "(8|9)[0-9]{5}";
                // 编译正则表达式
                Pattern pattern = Pattern.compile(regEx);
                // 忽略大小写的写法
                Matcher matcher = pattern.matcher(inviteCode);
                // 字符串是否与正则表达式相匹配
                boolean rs = matcher.matches();
				String content_in = "亲爱的同事XXX：国安社区全员社员卡开卡推荐激励开启啦！请牢记您的推荐码XXXXXX，务必要求被推荐人在线填写，以此认定推荐人！";
				String content_out = "XXX，您好。您的国安社区安心合作社社员卡开卡邀请码生成完毕，请牢记您的推荐码：XXXXXX。邀请被推荐人开通社员卡时，务必要求被邀请人在线准确填写您的邀请码，以此认定邀请人！感谢您的支持。";
				if(rs){
                    sm.setContent(content_out);
                    sm.setSignature("123742");//国安管家
                }else{
                    sm.setContent(content_in);
                    sm.setSignature("123743");//国安数据
                }
				sm.setCheckStatus(0);
				sm.setCode(timestamp);
				sm.setType(type);
				sm.setTitle("社员邀请码");
				sm.setUserGroupCode("only one");
				preObject(sm);
				saveObject(sm);//保存短信
			}

			Thread thread = new Thread(new SendShortMessageTask(list, sm));
			thread.start();

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

	@Override
	public Map<String, Object> getAllOutSider() {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();

		try {
			list = shortMessageDao.getAllOutSider();

			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data",list);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getOutSider(QueryConditions queryConditions) {
		ShortMessageDao shortMessageDao = (ShortMessageDao)SpringHelper.getBean(ShortMessageDao.class.getName());
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
				sb_where.append(" and name like '"+map_where.get("value")+"'");
			}
		}
		try {
			map_result.put("data",shortMessageDao.getOutSider(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);

		return map_result;
	}

	@Override
	public String sendShortMessage(String mobilephone, String content, String functionname, String epid) {
		String resultString = "";
		String proxydomain = PropertiesUtil.getValue("proxy.domain");
		int proxyport = Integer.parseInt(PropertiesUtil.getValue("proxy.port"));
		String proxySwitch = PropertiesUtil.getValue("proxy.switch");
		try {

			CloseableHttpClient httpclient = null;


			if("off".equals(proxySwitch)){
				//实例化CloseableHttpClient对象
				httpclient = HttpClients.custom().build();
			}else if("on".equals(proxySwitch)){
				//设置代理IP、端口、协议（请分别替换）
				HttpHost proxy = new HttpHost(proxydomain, proxyport, "http");

				//把代理设置到请求配置
				RequestConfig defaultRequestConfig = RequestConfig.custom()
						.setProxy(proxy)
						.build();

				//实例化CloseableHttpClient对象
				httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
			}

				String sendcode_gb2312 = URLEncoder.encode(content,"utf8");
				String url = "http://datatest.guoanshequ.top/eprj/smsSend.action?phone=%s&sendcode=%s&epid=%s";
//				CloseableHttpClient httpclient = HttpClients.createDefault();
				HttpGet httpGet = new HttpGet(String.format(url, new Object[]{mobilephone,sendcode_gb2312,epid}));
				CloseableHttpResponse response = httpclient.execute(httpGet);
				resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			//}


		} catch (Exception e) {
			e.printStackTrace();
			resultString="Error:"+e.getMessage();
		}

		return resultString;
	}



}
