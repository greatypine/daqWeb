package com.cnpc.pms.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;

public class ShortUrlUtils {

	 static String actionUrl = "http://api.t.sina.com.cn/short_url/shorten.json";
	 //static String APPKEY = "2815391962,31641035,3271760578,3925598208";
	 static String APPKEY="3967377887";
	 
	 public static void main(String[] args) {
	        String longUrl = "http://10.16.21.89:8888/daqWeb/bizbase/message.html?id=12435";
	        System.out.println(ShortUrlUtils.buildShortUrl(longUrl));
	 }
	 
	 
	 public static String buildShortUrl(String longUrl){
	        longUrl = java.net.URLEncoder.encode(longUrl);
	        String appkey = APPKEY;
	        String[] sourceArray = appkey.split(",");
	        for(String key:sourceArray){
	            String shortUrl = ShortUrlUtils.sinaShortUrl(key,longUrl);
	            if(shortUrl != null){
	                return shortUrl;
	            }
	        }
	        return null;
	    }
	 
	 public static String sinaShortUrl(String source, String longUrl){
	        String result = sendPost(actionUrl, "url_long="+longUrl+"&source="+source);
	        if(result==null || "".equals(result)){
	            return "";
	        }
	        JSONArray jsonArray = JSONArray.parseArray(result);
	        JSONObject json = JSONObject.parseObject(jsonArray.get(0).toString());
	        String ret = json.get("url_short").toString();
	        return ret;
	    }  
	     
	 
	 
	    public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            URLConnection conn = realUrl.openConnection();
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            out.print(param);
	            out.flush();
	            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    } 
	    
	
}
