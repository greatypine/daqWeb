package com.cnpc.pms.personal.manager.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.cnpc.pms.personal.dto.CommentDto;
import com.cnpc.pms.personal.entity.HumanVacation;
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
		for(Map<String, Object> map : condition.getConditions()){
			if("employee_name".equals(map.get("key"))&&map.get("value")!=null){//查询条件
				employee_name = map.get("value").toString();
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
    
}
