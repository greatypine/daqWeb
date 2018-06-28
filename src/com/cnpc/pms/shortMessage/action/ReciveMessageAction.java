package com.cnpc.pms.shortMessage.action;

import java.io.File;
import java.io.FileWriter;
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
import org.apache.tools.ant.taskdefs.Sleep;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.ReplyMessage;
import com.cnpc.pms.shortMessage.manager.ReplyMessageBackupsManager;
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
	    	 ReplyMessageBackupsManager reBackupsManager = (ReplyMessageBackupsManager)SpringHelper.getBean("replyMessageBackupsManager");

	    	 String remoteAddress = req.getHeader("x-forwarded-for");  
	         if(remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress)) {  
	        	 remoteAddress = req.getHeader("Proxy-Client-IP");  
	         }  
	         if(remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress)) {  
	        	 remoteAddress = req.getHeader("WL-Proxy-Client-IP");  
	         }  
	         if(remoteAddress == null || remoteAddress.length() == 0 || "unknown".equalsIgnoreCase(remoteAddress)) {  
	        	 remoteAddress = req.getRemoteAddr();  
	        }  
	    	
	    	System.out.println("短信远程IP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+remoteAddress);
	    	
	    	String permitAddress = PropertiesUtil.getValue("permitAddress");
	    	String[] permitAddArray  = permitAddress.split(",");
	    	req.setCharacterEncoding("UTF-8");
	    	resp.setContentType("text/plain; charset=utf-8");
			PrintWriter out = resp.getWriter();
			
			String phone = req.getParameter("phone"); 
			String msgContent = req.getParameter("msgContent");
			String spNumber =req.getParameter("spNumber"); 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			StringBuilder replysb = new StringBuilder();
			replysb.append("\r\n").append(sdf.format(new Date())).append("&&&").append(remoteAddress).append("&&&").append(phone).append("&&&").append(msgContent).append("&&&").append(spNumber);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					saveReplyMessageAsFile(replysb.toString());//返回记录保存文件中
				}
			}).start();
			
//			if(Arrays.asList(permitAddArray).contains(remoteAddress)){
	    		try {
	    			 
	    			
//	    			 if(phone==null||"".equals(phone)){
//	    				 out.println("Error:Phone is requested but  not found");
//	    				 return;
//	    			 }
//	    			 
//	    			 if(msgContent==null||"".equals(msgContent)){
//	    				 out.println("Error:msgContent is requested but  not found");
//	    				 return;
//	    			 }
//	    			 
//	    			 if(spNumber==null||"".equals(spNumber)){
//	    				 out.println("Error:spNumber is requested but  not found");
//	    				 return;
//	    			 }
//	    			 
//	    			 Pattern p = Pattern.compile("^1[0-9]{10}$");  
//	    			  
//	    			 Matcher m = p.matcher(phone);  
//	    			 if(!m.matches()){
//	    				 out.println("Error:Phone format is wrong");
//	    				 return;
//	    			 } 
	    			
	    			 ReplyMessageDto re = new ReplyMessageDto();
	    			 re.setPhone(phone);
	    			 re.setContent(msgContent);
	    			 re.setSpNumber(spNumber);
	    			 re.setRemoteIP(remoteAddress);
	    			 re.setError("");
	    			 Map<String,Object> result= reManager.reciveMessageReply(re);//接收短信以后的逻辑
	    			 Object status = result.get("status");
	    			 
	    			 if(status==CodeEnum.nullData.getValue()){//没有对应的短信类型
	    				 out.println("Error:the value '"+msgContent+"' of the parameter 'msgContent' is invalid");
	    			 }else if(status==CodeEnum.success.getValue()){//回复成功
	    				 out.println("success");
	    			 }else {//回复失败
	    				 out.println("Error:"+status);
	    			 }
	    			 
	    			  
				} catch (Exception e) {
					e.printStackTrace();
					out.print("Error:" + e.getMessage()); 
				}
//			} else {
//    			
//    			 ReplyMessageDto re = new ReplyMessageDto();
//    			 re.setPhone(phone);
//    			 re.setContent(msgContent);
//    			 re.setSpNumber(spNumber);
//    			 re.setError("access forbidden");
//    			 reBackupsManager.saveReplyMessageBackups(re);
//    			 out.println("access forbidden");
//			}

	    }
	    
	    
	    
	    public synchronized void  saveReplyMessageAsFile(String content){
	    	
	    	String path = PropertiesUtil.getValue("replyMessageFilePath");
	    	
	    	  
	    	          
            //要保存文件的绝对路径
            String buildPath = path;
            //目标目录不存在的话就自动创建
            File f = new File(buildPath);
            if (!f.exists()) {
              f.mkdirs();//建立目录
            }
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	String fileName = df.format(new Date())+".txt";
            
	    	
	    	
	    	FileWriter writer=null;
	    	try {
	    		 File f1 = new File(buildPath+fileName);
	    		 if(!f1.exists()){
	 	    		f1.createNewFile();
	 	    	 }
	    		 writer = new FileWriter(buildPath+fileName, true);
		         writer.write(content);
		         writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(writer!=null){
					try {
						writer.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
	    	
	    }

}
