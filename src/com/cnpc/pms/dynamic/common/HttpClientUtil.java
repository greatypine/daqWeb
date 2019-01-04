/**
 * gaobaolei
 */
package com.cnpc.pms.dynamic.common;

import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cnpc.pms.base.util.PropertiesUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


/**
 * @author gaobaolei
 *  接口请求工具
 */
public class HttpClientUtil {
	
	public String getRemoteData(String url,JSONObject param){
		HttpClient client = null;
		HttpPost httpPost  = null;
		String result="";
		String proxydomain = PropertiesUtil.getValue("proxy.domain");
		int proxyport = Integer.parseInt(PropertiesUtil.getValue("proxy.port"));
		String proxySwitch = PropertiesUtil.getValue("proxy.switch");
		try {
			client = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type","application/json; charset=utf-8");  
			httpPost.setHeader("Accept", "application/json");  
			
			//设置参数  
			
			httpPost.setEntity(new StringEntity(param.toString(), Charset.forName("UTF-8")));
			if("on".equals(proxySwitch)){

				HttpHost proxy = new HttpHost(proxydomain, proxyport, "http");
				//把代理设置到请求配置
				RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
				httpPost.setConfig(defaultRequestConfig);
			}
	       /* List<NameValuePair> list = new ArrayList<NameValuePair>();  
	        Iterator iterator = param.entrySet().iterator();  
	        while(iterator.hasNext()){  
	            Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	        }  
	        if(list.size() > 0){  
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");  
	            httpPost.setEntity(entity);  
	        }  */
	        
	        HttpResponse response = client.execute(httpPost);  
	        if(response != null){  
	            HttpEntity resEntity = response.getEntity();  
	            if(resEntity != null){  
	                result = EntityUtils.toString(resEntity,"UTF-8");  
	            }  
	        }  
		} catch (ConnectTimeoutException e) {
			result = "connectTimeout";
			return result;
		}catch (SocketTimeoutException e) {
			result = "socketTimeout";
			return result;
		}catch (Exception e) {
			result = "other";
			return result;
		}
		return result;
	}

	public String getRemoteDataProxy(String url,JSONObject param){

		HttpPost httpPost  = null;
		String result="";
		CloseableHttpClient httpclient=null;
		String proxydomain = PropertiesUtil.getValue("proxy.domain");
		int proxyport = Integer.parseInt(PropertiesUtil.getValue("proxy.port"));
		String proxySwitch = PropertiesUtil.getValue("proxy.switch");
		try {

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



			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type","application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");

			//设置参数
 			httpPost.setEntity(new StringEntity(param.toString(), Charset.forName("UTF-8")));

			HttpResponse response = httpclient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,"UTF-8");
				}
			}
		} catch (ConnectTimeoutException e) {
			result = "connectTimeout";
			return result;
		}catch (SocketTimeoutException e) {
			result = "socketTimeout";
			return result;
		}catch (Exception e) {
			result = "other";
			return result;
		}
		return result;
	}





	/**
	 * 调用接口方法 
	 * @param url  URL
	 * @param md5str  md5 sign
	 * @param param 参数body
	 * @return
	 */
	public String insRemoteData(String url,String md5str,String param){
		HttpClient client = null;
		HttpPost httpPost  = null;
		String result="";
		String proxydomain = PropertiesUtil.getValue("proxy.domain");
		int proxyport = Integer.parseInt(PropertiesUtil.getValue("proxy.port"));
		String proxySwitch = PropertiesUtil.getValue("proxy.switch");
		try {
			client = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type","application/json; charset=utf-8");  
			httpPost.setHeader("md5", md5str);
			httpPost.setHeader("appId","guoan_data");
			httpPost.setHeader("Accept", "application/json");  

			//设置参数  
			
			httpPost.setEntity(new StringEntity(param.toString(), Charset.forName("UTF-8")));

			if("on".equals(proxySwitch)){

				HttpHost proxy = new HttpHost(proxydomain, proxyport, "http");
				//把代理设置到请求配置
				RequestConfig defaultRequestConfig = RequestConfig.custom().setProxy(proxy).build();
				httpPost.setConfig(defaultRequestConfig);
			}
	       /* List<NameValuePair> list = new ArrayList<NameValuePair>();
	        Iterator iterator = param.entrySet().iterator();
	        while(iterator.hasNext()){
	            Entry<String,String> elem = (Entry<String, String>) iterator.next();
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
	            httpPost.setEntity(entity);
	        }  */
	        
	        HttpResponse response = client.execute(httpPost);  
	        if(response != null){  
	            HttpEntity resEntity = response.getEntity();  
	            if(resEntity != null){  
	                result = EntityUtils.toString(resEntity,"UTF-8");  
	            }  
	        }  
		} catch (ConnectTimeoutException e) {
			result = "connectTimeout";
			return result;
		}catch (SocketTimeoutException e) {
			result = "socketTimeout";
			return result;
		}catch (Exception e) {
			result = "other";
			return result;
		}
		return result;
	} 
	
	
	public String validateRemoteData(String url,String param){
		HttpClient client = null;
		HttpPost httpPost  = null;
		String result="";
		try {
			client = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type","application/x-www-form-urlencoded");  
			//设置参数  
			httpPost.setEntity(new StringEntity(param.toString(), Charset.forName("UTF-8")));  
	       /* List<NameValuePair> list = new ArrayList<NameValuePair>();  
	        Iterator iterator = param.entrySet().iterator();  
	        while(iterator.hasNext()){  
	            Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	        }  
	        if(list.size() > 0){  
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");  
	            httpPost.setEntity(entity);  
	        }  */
	        
	        HttpResponse response = client.execute(httpPost);  
	        if(response != null){  
	            HttpEntity resEntity = response.getEntity();  
	            if(resEntity != null){  
	                result = EntityUtils.toString(resEntity,"UTF-8");  
	            }  
	        }  
		} catch (ConnectTimeoutException e) {
			result = "connectTimeout";
			return result;
		}catch (SocketTimeoutException e) {
			result = "socketTimeout";
			return result;
		}catch (Exception e) {
			result = "other";
			return result;
		}
		return result;
	} 
	
	
	
}
