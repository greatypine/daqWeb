package com.cnpc.pms.notice.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.inter.common.Result;
import com.cnpc.pms.messageModel.dao.MessageNewDao;
import com.cnpc.pms.messageModel.entity.Message;
import com.cnpc.pms.messageModel.entity.MessageSendUtil;
import com.cnpc.pms.notice.dao.NoticeDao;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.manager.NoticeManager;
import com.cnpc.pms.notice.util.SendNotice;

public class NoticeManagerImpl extends BizBaseCommonManager implements NoticeManager{

	@Override
	public Map<String,Object> queryAllCity(QueryConditions queryConditions) {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
       
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if ("cityname".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))) {
                sb_where.append(" and a.cityname like '").append(map_where.get("value")+"'");
            }
        }
		try {
			 map_result.put("data",noticeDao.queryAllCity(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> queryPartCity(QueryConditions queryConditions) {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
       
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if ("cityname".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))) {
                sb_where.append(" and a.cityname like '").append(map_where.get("value")+"'");
            }else if("employee_no".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	sb_where.append(" and a.pk_userid = ").append(map_where.get("value")+"");
            }
        }
		try {
			 map_result.put("data",noticeDao.queryPartCity(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> queryStoreByCity(QueryConditions queryConditions) {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        StringBuilder citySb = new StringBuilder(); 
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if ("city_id".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))) {
                String[] cityArr = map_where.get("value").toString().split(",");
                for(String city:cityArr){
                	citySb.append(",'"+city+"'");
                }
                citySb = citySb.deleteCharAt(0);
            }else if("name".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	sb_where.append(" and b.name like '"+map_where.get("value")+"'");
            }else if("employeeId".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))){
            	List<Map<String, Object>> citylist = noticeDao.queryCityByUserId(Long.parseLong(map_where.get("value").toString()));
            	if(citylist==null||citylist.size()==0){
            		return null;
            	}else{
            		for(int i=0;i<citylist.size();i++){
            			citySb.append(",'"+citylist.get(i).get("citycode")+"'");
            		}
            		 citySb = citySb.deleteCharAt(0);
            	}
            	
            }
        }
		try {
			 map_result.put("data",noticeDao.getStoreOfCity(citySb.toString(),sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> getNoticeCity(QueryConditions queryConditions) {
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = umanager.getCurrentUserDTO();
		String userCode = userDTO.getUsergroup().getCode();
	    Pattern p_zb = Pattern.compile("^(ZB|zb)\\w*$");
	    Pattern p_cs = Pattern.compile("^(CS|cs)\\w*$"); 
		
		Map<String,Object> result = new HashMap<String,Object>();  
		Matcher m_zb = p_zb.matcher(userCode);  
		Matcher m_cs = p_cs.matcher(userCode);
		
		if(m_zb.matches()){
			result = this.queryAllCity(queryConditions);
		}else if(m_cs.matches()){
			result = this.queryPartCity(queryConditions);
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getNoticeReceiveZW(QueryConditions queryConditions) {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		//查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        //分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        //返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
       
        for (Map<String, Object> map_where : queryConditions.getConditions()) {
        	if ("zw".equals(map_where.get("key"))
                    && null != map_where.get("value") && !"".equals(map_where.get("value"))) {
                sb_where.append(" and zw like '").append(map_where.get("value")).append("'");
            }
        }
		try {
			 map_result.put("data",noticeDao.getReceiveZW(sb_where.toString(), obj_page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		map_result.put("pageinfo", obj_page);
        
        return map_result;
	}

	@Override
	public Map<String, Object> saveNotice(Notice notice) {
		UserManager uManager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = uManager.getCurrentUserDTO();	
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		User receiveUser = notice.getReceiveUser();
		String city = receiveUser.getCitynames();//传值使用
		String store = receiveUser.getName();//传值使用
		String zw  = receiveUser.getZw();
		Integer type = notice.getType();//类型
		Integer grade = notice.getGrade();//等级
		String timestamp = String.valueOf(System.currentTimeMillis());
		notice.setNoticeNo(timestamp);
		preObject(notice);
		saveObject(notice);
		
		
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try{
			if(city!=null&&!"".equals(city)){
				StringBuilder citySb = new StringBuilder();
				String[] cityArr = city.split(",");
	            for(String cityName:cityArr){
	            	citySb.append(",'"+cityName+"'");
	            }
	            citySb = citySb.deleteCharAt(0);
	            param.put("city", "("+citySb.toString()+")");
	            
			}
			
			if(store!=null&&!"".equals(store)){
				StringBuilder storeSb = new StringBuilder();
				String[] storeArr = store.split(",");
	            for(String storeName:storeArr){
	            	storeSb.append(","+storeName+"");
	            }
	            storeSb = storeSb.deleteCharAt(0);
	            param.put("store", "("+storeSb.toString()+")");
			}
			
			if(zw!=null&&!"".equals(zw)){
				StringBuilder zwSb = new StringBuilder();
				String[] zwArr = zw.split(",");
	            for(String zwName:zwArr){
	            	zwSb.append(",'"+zwName+"'");
	            }
	            zwSb = zwSb.deleteCharAt(0);
	            param.put("zw", "("+zwSb.toString()+")");
			}
			
			List<Map<String, Object>> receiveUserList = noticeDao.getReceiveEmployee(param);
			
			//保存公告接收人并发送app通知或者短信
			SendNotice sn = new SendNotice(notice, receiveUserList);
			Thread thread = new Thread(sn);
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
	public Map<String, Object> selectNoticeByNoticeNo(String noticeNo) {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			list = noticeDao.selectNoticeByNoticeNo(noticeNo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", list);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> getCityOfRole() {
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		UserDTO userDTO = umanager.getCurrentUserDTO();
		String userCode = userDTO.getUsergroup().getCode();
	    Pattern p_zb = Pattern.compile("^(ZB|zb)\\w*$");
	    Pattern p_cs = Pattern.compile("^(CS|cs)\\w*$"); 
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Matcher m_zb = p_zb.matcher(userCode);  
		Matcher m_cs = p_cs.matcher(userCode);
		
		try {
			if(m_zb.matches()){
				list = noticeDao.getCityOfZb();
			}else if(m_cs.matches()){
				list = noticeDao.getCityOfCs(userDTO.getId());
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", list);
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getStoreOfRole(String cityCode) {
		UserManager umanager = (UserManager)SpringHelper.getBean("userManager");
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		UserDTO userDTO = umanager.getCurrentUserDTO();
		String userCode = userDTO.getUsergroup().getCode();
	    Pattern p_zb = Pattern.compile("^(ZB|zb)\\w*$");
	    Pattern p_cs = Pattern.compile("^(CS|cs)\\w*$"); 
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> cityList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Matcher m_zb = p_zb.matcher(userCode);  
		Matcher m_cs = p_cs.matcher(userCode);
		
		try {
			if(m_zb.matches()){
				if(cityCode!=null&&!"".equals(cityCode)){
					
					String[] citycodeArr = cityCode.split(",");
					StringBuilder sb = new StringBuilder();
					for(String code:citycodeArr){
						sb.append(",").append("'").append(code).append("'");
					}
					cityCode = sb.toString().substring(1);
				}
				list = noticeDao.getStoreByCity(cityCode);
			}else if(m_cs.matches()){
				if(cityCode==null||"".equals(cityCode)){
					cityList = noticeDao.getCityOfCs(userDTO.getId());
					StringBuilder sb = new StringBuilder();
					for(int i=0;i<cityList.size();i++){
						sb.append(",").append("'").append(cityList.get(i).get("citycode")).append("'");
					}
					if(cityList!=null&&cityList.size()>0){
						list =  noticeDao.getStoreByCity(sb.toString().substring(1));
					}
					
				}
				
			}
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", list);
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getAllZw() {
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			
			list = noticeDao.getAllZw();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", list);
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> deleteNotice(String noticeNo) {
        NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		NoticeReciverDao noReciverDao = (NoticeReciverDao)SpringHelper.getBean(NoticeReciverDao.class.getName());
		Map<String,Object> result = new HashMap<String,Object>();
		
		try {
			
			noticeDao.deleteNotice(noticeNo);
			noReciverDao.deleteNoticeReciver(noticeNo);
			result.put("code",CodeEnum.success.getValue());
			result.put("message","撤销成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message","撤销失败");
			return result;
		}
		
		return result;
	}

}
