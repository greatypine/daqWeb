package com.cnpc.pms.platform.manager.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.community.dao.CommunityMembersDao;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.platform.manager.CommunityMembersManager;
import com.cnpc.pms.utils.DateUtils;

public class CommunityMembersManagerImpl extends BizBaseCommonManager implements CommunityMembersManager {

	@Override
	public Map<String, Object> getNewMembersCount(DynamicDto dynamicDto) {
		Calendar calendar=Calendar.getInstance();
		dynamicDto.setYear(calendar.get(Calendar.YEAR));
		dynamicDto.setMonth(calendar.get(Calendar.MONTH)+1);
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> newCmCountList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> historyCountList = new ArrayList<Map<String,Object>>();
		newCmCountList =communityMembersDao.getNewMembersCount(dynamicDto,"0");//查询新开社员总量
		historyCountList =communityMembersDao.getNewMembersCount(dynamicDto,"1");//查询历史新开社员总量
		if(newCmCountList!=null&&newCmCountList.size()>0){
			result.put("newMemberCount", newCmCountList.get(0).get("newCount"));
		}else {
			result.put("newMemberCount", "0");
		}
		if(historyCountList!=null&&historyCountList.size()>0){
			result.put("historyCount", historyCountList.get(0).get("newCount"));
		}else {
			result.put("historyCount", "0");
		}
		return result;
	}

	@Override
	public Map<String, Object> getMembersWeekCount(DynamicDto dynamicDto) {
		Map<String, Object>  result = new HashMap<String, Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> newWeekCountList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> totalWeekCountList = new ArrayList<Map<String,Object>>();
		List dateXCounts=new ArrayList();
		try{
			newWeekCountList = communityMembersDao.getWeekMembersCount(dynamicDto);//查询近7日每日新开社员数
			totalWeekCountList = communityMembersDao.getWeekTotalMembersCount(dynamicDto);//查询近7日每日累计社员总数
			dateXCounts = reDate(7);
			String curDate = DateUtils.lastDate();
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			String maxDateStr = curDate;    
		    String minDateStr = "";    
		    Calendar calc =Calendar.getInstance();      
	         for(int i=0;i<7;i++){  
	        	 calc.setTime(sdf.parse(maxDateStr));    
	             calc.add(calc.DATE, -i);    
	             Date minDate = calc.getTime();    
	             minDateStr = sdf.format(minDate);   
	             Map<String,Object> lst_new_map = new HashMap<String, Object>();
	           	Map<String,Object> lst_total_map = new HashMap<String, Object>();
	           	lst_new_map.put("crtime", minDateStr.substring((minDateStr.indexOf("-")+1),minDateStr.length()));
	           	lst_total_map.put("crtime", minDateStr.substring((minDateStr.indexOf("-")+1),minDateStr.length()));
	           	lst_new_map.put("year_date", minDateStr.substring(0,(minDateStr.indexOf("-"))));
	           	lst_new_map.put("newcount", 0);
	           	lst_new_map.put("totalcount", 0);
	           	for(int j=0;j<newWeekCountList.size();j++){
	           		Map<String,Object> lst_map_week = newWeekCountList.get(j);
	           		String dateStr = String.valueOf(lst_map_week.get("crtime"));
	           		if(minDateStr.contains(dateStr)){
	           			newWeekCountList.remove(j);
	           			String week_date = String.valueOf(lst_map_week.get("crtime"));
	           			lst_new_map.put("crtime", week_date);
	           			lst_new_map.put("newcount", lst_map_week.get("newcount"));
	           		}
	           	}
	           	newWeekCountList.add(lst_new_map);
	         }
	         result.put("week_new_data", newWeekCountList);
	         JSONArray jsonDateX=(JSONArray)JSONArray.fromObject(dateXCounts);
	         result.put("jsonDateX",jsonDateX);
	         result.put("growAllCounts",totalWeekCountList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//生成X轴坐标
	 public List reDate(int dd) throws ParseException {
		 DateFormat f = new SimpleDateFormat("MM-dd");  
			
	     Date today = new Date();  

	     Calendar c = Calendar.getInstance();  
	     c.setTime(today);  
	     c.add(Calendar.DAY_OF_MONTH, -dd);
	     List dateX=new ArrayList();
	     dateX.add(f.format(c.getTime()));
	     for(int i=dd;i>1;i--) {
	    	 c.add(Calendar.DAY_OF_MONTH, 1);
	    	 dateX.add(f.format(c.getTime()));
	     }
			return dateX;
	 }

	@Override
	public Map<String, Object> getDayGMVOfMonthForCityStore(
			DynamicDto dynamicDto) {
		
		return null;
	}

	@Override
	public Map<String, Object> getDepDayGMVOfMonthForStore(String storeId) {
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> depGMVList = new ArrayList<Map<String,Object>>();
		depGMVList =communityMembersDao.getDeptMonthDayGMV(storeId);
		return null;
	}
}
