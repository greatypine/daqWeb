package com.cnpc.pms.shortMessage.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.ReplyMessage;
import com.cnpc.pms.shortMessage.manager.ReplyMessageManager;
import com.cnpc.pms.utils.DownloadUtil;

public class ReciveMessageAction extends HttpServlet{
	
	 @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doPost(req,resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	ReplyMessageManager reManager = (ReplyMessageManager)SpringHelper.getBean("replyMessageManager");
	    	String remoteAddress = req.getRemoteAddr();
	    	String permitAddress = PropertiesUtil.getValue("permitAddress");
	    	String[] permitAddArray  = permitAddress.split(",");
	    	
	    	resp.setContentType("text/plain; charset=utf-8");
			PrintWriter out = resp.getWriter();
	    	
			if(Arrays.asList(permitAddArray).contains(remoteAddress)){
	    		try {
	    			 
	    			 String phone = req.getParameter("Phone");
	    			 String msgContent = req.getParameter("msgContent");
	    			 String spNumber = req.getParameter("spNumber");
	    			 if(phone==null||"".equals(phone)){
	    				 out.println("Phone is requested but  not found");
	    				 return;
	    			 }
	    			 
	    			 if(msgContent==null||"".equals(msgContent)){
	    				 out.println("msgContent is requested but  not found");
	    				 return;
	    			 }
	    			 
	    			 if(spNumber==null||"".equals(spNumber)){
	    				 out.println("spNumber is requested but  not found");
	    				 return;
	    			 }
	    			 
	    			 Pattern p = Pattern.compile("^1[0-9]{10}$");  
	    			  
	    			 Matcher m = p.matcher(phone);  
	    			 if(!m.matches()){
	    				 out.println("Phone format is wrong");
	    				 return;
	    			 } 
	    			
	    			 ReplyMessageDto re = new ReplyMessageDto();
	    			 re.setPhone(phone);
	    			 re.setContent(msgContent);
	    			 re.setSpNumber(spNumber);
	    			 Map<String,Object> result= reManager.reciveMessageReply(re);//接收短信以后的逻辑
	    			 Object status = result.get("status");
	    			 
	    			 if(status==CodeEnum.nullData.getValue()){//没有对应的短信类型
	    				 out.println("The value '"+msgContent+"' of the parameter 'msgContent' is invalid");
	    			 }else if(status==CodeEnum.success.getValue()){//回复成功
	    				 out.println("success");
	    			 }else {//回复失败
	    				 out.println("Error:"+status);
	    			 }
	    			 
	    			  
				} catch (Exception e) {
					e.printStackTrace();
					out.print("Error:" + e.getMessage()); 
				}
			} else {
				out.println("access forbidden");
			}

	    }

}
