package com.cnpc.pms.personal.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cnpc.pms.base.message.SMSStatusCode;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.dynamic.common.SSLClient;
import com.cnpc.pms.personal.dto.CommentDto;
import com.cnpc.pms.personal.entity.DistCity;
import com.cnpc.pms.personal.entity.HumanReContent;
import com.cnpc.pms.personal.entity.HumanVacation;
import com.cnpc.pms.personal.manager.HumanReContentManager;
import com.cnpc.pms.personal.manager.HumanVacationManager;
import com.gexin.fastjson.JSONArray;
import com.gexin.fastjson.JSONObject;

@SuppressWarnings("all")
public class HumanVacationManagerImpl extends BizBaseCommonManager implements HumanVacationManager {
    
	
	static final String URL = "http://localhost:8889/GASM/dispatcher.action";
    /**
     * 查询列表 
     */
    @Override
    public Map<String, Object> queryMyProcessList(QueryConditions condition) {
    	Map<String,Object> returnMap = new java.util.HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String employee_name = null;
		String process_status = "";
		for(Map<String, Object> map : condition.getConditions()){
			if("employee_name".equals(map.get("key"))&&map.get("value")!=null){//查询条件
				employee_name = map.get("value").toString();
			}
			
			if("process_status".equals(map.get("key"))&&map.get("value")!=null){//查询条件
				process_status = map.get("value").toString();
			}
			
		}
		List<?> lst_data = null;
		FSP fsp = new FSP();
		fsp.setSort(SortFactory.createSort("id", ISort.DESC));
		StringBuffer sbfCondition = new StringBuffer(); 
		
		//这里根据当前登录人 过滤 显示所能看到的数据 
		sbfCondition.append(" 1=1 ");
		
		//根据登录角色过滤显示
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		String userGroupCode = userManager.getCurrentUserDTO().getUsergroup().getCode();
		
		if(employee_name!=null&&employee_name.length()>0) {
			sbfCondition.append(" and employee_name like '%"+employee_name+"%'");	

		}
		if(process_status!=null&&process_status.length()>0) {
			sbfCondition.append(" and process_status="+Integer.parseInt(process_status));
		}
		
		
		List<DistCity> distCityList = userManager.getCurrentUserCity();
		if(distCityList!=null&&distCityList.size()>0) {
			String cityssql = "";
			//根据城市查询所有门店 id 然后过滤所有门店id
			if (distCityList != null && distCityList.size() > 0) {
				for (DistCity d : distCityList) {
					cityssql += "'" + d.getCityname() + "',";
				}
				cityssql = cityssql.substring(0, cityssql.length() - 1);
			}
			if(cityssql!=null&&cityssql.length()>0) {
				sbfCondition.append(" and city_name in("+cityssql+") ");
			}else {
				sbfCondition.append(" and 1=0 ");
			}
			
		}else {
			sbfCondition.append(" and 1=0 ");
		}
		
		
		/*if(userGroupCode!=null&&userGroupCode.length()>0) {
			if(userGroupCode.equals("GAX")) {
				sbfCondition.append(" and employee_no='"+userManager.getCurrentUserDTO().getEmployeeId()+"'");	
			}
			if(userGroupCode.equals("DZ")) {
				sbfCondition.append(" and store_id ="+userManager.getCurrentUserDTO().getStore_id());
			}
		}else {
			sbfCondition.append(" and 1=1 ");
		}*/
		
		
		/*if(vacation_type!=null&&vacation_type.length()>0) {
			sbfCondition.append(" and vacation_type = '"+vacation_type+"' ");
		}*/
		
		IFilter iFilter =FilterFactory.getSimpleFilter(sbfCondition.toString());
		fsp.setPage(pageInfo);
		fsp.setUserFilter(iFilter);
		lst_data = this.getList(fsp);
		returnMap.put("pageinfo", pageInfo);
		returnMap.put("header", "");
		returnMap.put("data", lst_data);
		return returnMap;
	}
	    
	    
    
    
    
    @Override
	public HumanVacation queryHumanVacationInfo(Long id) {
		HumanVacation humanVacation = (HumanVacation) this.getObject(id);
		String processInstanceId=humanVacation.getProcessInstanceId();
		List<CommentDto> comments = findCommentByProcessId(processInstanceId);
		humanVacation.setProcesslog(comments);
		return humanVacation;
	}
    
	  
    
    public List<CommentDto> findCommentByProcessId(String processId) {
    	String httpurl = URL+"?requestString={\"managerName\":\"InterManager\",\"methodName\":\"queryProcessCommentByProcessId\",\"parameters\":[\""+processId+"\"]}";
    	String ret = doGet(httpurl);
    	System.out.println("============================================");
    	System.out.println(ret);
    	System.out.println("============================================");
    	JSONObject jsonObject = JSONObject.parseObject(ret);
    	JSONArray jo = (JSONArray) JSONObject.parseObject(jsonObject.get("data").toString()).get("data");
    	List<CommentDto> commentDtos = new ArrayList<CommentDto>();
    	System.out.println("-------------------------------");
    	for(Object o:jo) {
    		CommentDto commentDto = new CommentDto();
    		Long time = (Long) ((JSONObject)o).get("createtime");
    		String message=(String) ((JSONObject)o).get("message");
    		commentDto.setMessage(message);
    		commentDto.setTime(time);
    		commentDtos.add(commentDto);
    		System.out.println(((JSONObject)o).get("createtime"));
    		System.out.println(((JSONObject)o).get("message"));
    	}
    	System.out.println("-------------------------------");
    	return commentDtos;

    }
    
    
    
    /**
     * HR通过
     * @param humanVacation
     * @return
     */
    @Override
    public HumanReContent update_hr_Audit(HumanVacation humanVacation) {
    	//processInstanceId,re_content,id,employee_name
    	HumanReContent humanReContent = new HumanReContent();
    	try {
    		String processInstanceId=humanVacation.getProcessInstanceId();
        	String re_content=humanVacation.getRe_content();
        	Long id = humanVacation.getId();
        	String employee_name=humanVacation.getEmployee_name();
        	HumanReContentManager humanReContentManager = (HumanReContentManager) SpringHelper.getBean("humanReContentManager");
        	humanReContent.setProcessInstanceId(processInstanceId);
        	humanReContent.setRe_content(re_content);
        	humanReContent.setVacationid(id);
        	humanReContent.setEmployee_name(employee_name);
        	humanReContentManager.saveObject(humanReContent);
        	//String re_content=URLEncoder.encode(humanVacation.getRe_content(),"UTF-8");
        	//String employee_name=URLEncoder.encode(humanVacation.getEmployee_name(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return humanReContent;
    }
    @Override
    public String update_process_status(Long vacationid) {
    	Long reContentId=vacationid;
    	String param = "requestString={\"managerName\":\"InterManager\",\"methodName\":\"update_hr_audit_cn\",\"parameters\":[\""+reContentId+"\"]}";
    	String ret = sendPost(URL,param);
    	//String ret = doGet(httpurl);
    	System.out.println("============================================");
    	System.out.println(ret);
    	System.out.println("============================================");
    	return ret;
    }
    /**
     * HR驳回 
     * @param humanVacation
     * @return
     */
    @Override
    public HumanReContent update_hr_Audit_Re(HumanVacation humanVacation) {
    	//processInstanceId,re_content,id,employee_name
    	HumanReContent humanReContent = new HumanReContent();
    	try {
    		String processInstanceId=humanVacation.getProcessInstanceId();
        	String re_content=humanVacation.getRe_content();
        	Long id = humanVacation.getId();
        	String employee_name=humanVacation.getEmployee_name();
        	HumanReContentManager humanReContentManager = (HumanReContentManager) SpringHelper.getBean("humanReContentManager");
        	humanReContent.setProcessInstanceId(processInstanceId);
        	humanReContent.setRe_content(re_content);
        	humanReContent.setVacationid(id);
        	humanReContent.setEmployee_name(employee_name);
        	humanReContentManager.saveObject(humanReContent);
        	//String re_content=URLEncoder.encode(humanVacation.getRe_content(),"UTF-8");
        	//String employee_name=URLEncoder.encode(humanVacation.getEmployee_name(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return humanReContent;
    }
    @Override
    public String update_process_status_re(Long vacationid) {
    	Long reContentId=vacationid;
    	String param = "requestString={\"managerName\":\"InterManager\",\"methodName\":\"update_hr_audit_re_cn\",\"parameters\":[\""+reContentId+"\"]}";
    	String ret = sendPost(URL,param);
    	//String ret = doGet(httpurl);
    	System.out.println("============================================");
    	System.out.println(ret);
    	System.out.println("============================================");
    	return ret;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * 运营经理通过
     * @param humanVacation
     * @return
     */
    @Override
    public HumanReContent update_rm_Audit(HumanVacation humanVacation) {
    	//processInstanceId,re_content,id,employee_name
    	HumanReContent humanReContent = new HumanReContent();
    	try {
    		String processInstanceId=humanVacation.getProcessInstanceId();
        	String re_content=humanVacation.getRe_content();
        	Long id = humanVacation.getId();
        	String employee_name=humanVacation.getEmployee_name();
        	HumanReContentManager humanReContentManager = (HumanReContentManager) SpringHelper.getBean("humanReContentManager");
        	humanReContent.setProcessInstanceId(processInstanceId);
        	humanReContent.setRe_content(re_content);
        	humanReContent.setVacationid(id);
        	humanReContent.setEmployee_name(employee_name);
        	humanReContent.setEmployee_no(humanVacation.getEmployee_no());
        	humanReContentManager.saveObject(humanReContent);
        	//String re_content=URLEncoder.encode(humanVacation.getRe_content(),"UTF-8");
        	//String employee_name=URLEncoder.encode(humanVacation.getEmployee_name(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return humanReContent;
    }
    @Override
    public String update_rm_process_status(Long vacationid) {
    	Long reContentId=vacationid;
    	String param = "requestString={\"managerName\":\"InterManager\",\"methodName\":\"update_rm_audit_cn\",\"parameters\":[\""+reContentId+"\"]}";
    	String ret = sendPost(URL,param);
    	//String ret = doGet(httpurl);
    	System.out.println("============================================");
    	System.out.println(ret);
    	System.out.println("============================================");
    	return ret;
    }
    /**
     * 运营经理驳回 
     * @param humanVacation
     * @return
     */
    @Override
    public HumanReContent update_rm_Audit_Re(HumanVacation humanVacation) {
    	//processInstanceId,re_content,id,employee_name
    	HumanReContent humanReContent = new HumanReContent();
    	try {
    		String processInstanceId=humanVacation.getProcessInstanceId();
        	String re_content=humanVacation.getRe_content();
        	Long id = humanVacation.getId();
        	String employee_name=humanVacation.getEmployee_name();
        	HumanReContentManager humanReContentManager = (HumanReContentManager) SpringHelper.getBean("humanReContentManager");
        	humanReContent.setProcessInstanceId(processInstanceId);
        	humanReContent.setRe_content(re_content);
        	humanReContent.setVacationid(id);
        	humanReContent.setEmployee_name(employee_name);
        	humanReContent.setEmployee_no(humanVacation.getEmployee_no());
        	humanReContentManager.saveObject(humanReContent);
        	//String re_content=URLEncoder.encode(humanVacation.getRe_content(),"UTF-8");
        	//String employee_name=URLEncoder.encode(humanVacation.getEmployee_name(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return humanReContent;
    }
    @Override
    public String update_rm_process_status_re(Long vacationid) {
    	Long reContentId=vacationid;
    	String param = "requestString={\"managerName\":\"InterManager\",\"methodName\":\"update_rm_audit_re_cn\",\"parameters\":[\""+reContentId+"\"]}";
    	String ret = sendPost(URL,param);
    	//String ret = doGet(httpurl);
    	System.out.println("============================================");
    	System.out.println(ret);
    	System.out.println("============================================");
    	return ret;
    }
    
    
    
    
    
    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
    
    
    
    
    public static String doPost(String url, String param) {
    	HttpClient client = null;
		HttpPost httpPost  = null;
		String result="";
		try {
			client = new SSLClient();
			httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type","application/json; charset=utf-8"); 
			httpPost.addHeader("requestString", param.toString());
			//httpPost.setEntity(new StringEntity(param.toString(), Charset.forName("UTF-8")));  
			 HttpResponse response = client.execute(httpPost);  
		        if(response != null){  
		            HttpEntity resEntity = response.getEntity();  
		            if(resEntity != null){  
		                result = EntityUtils.toString(resEntity,"UTF-8");  
		            }  
		        }  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
    }
    
    
    
    
    
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
           
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
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
