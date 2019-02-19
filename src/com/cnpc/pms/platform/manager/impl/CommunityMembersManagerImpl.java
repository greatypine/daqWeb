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
import com.cnpc.pms.dynamic.dao.ChartStatDao;
import com.cnpc.pms.dynamic.entity.ChartStatDto;
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
		DynamicDto dd2 = new DynamicDto();
		dd2.setCityId(dynamicDto.getCityId());
		dd2.setProvinceId(dynamicDto.getProvinceId());
		dd2.setYear(Integer.parseInt(com.cnpc.pms.base.file.comm.utils.DateUtil.findYearByIndex(-1)));
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> newCmCountList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> lastYearCountList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> historyCountList = new ArrayList<Map<String,Object>>();
		newCmCountList =communityMembersDao.getNewMembersCount(dynamicDto,"0");//查询新开社员总量
		historyCountList =communityMembersDao.getNewMembersCount(dynamicDto,"1");//查询历史新开社员总量
		lastYearCountList =communityMembersDao.getNewMembersLastYearCount(dynamicDto);//查询今年新开社员总量
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
		if(lastYearCountList!=null&&lastYearCountList.size()>0){
			result.put("yearCount", lastYearCountList.get(0).get("newCount"));
		}else {
			result.put("yearCount", "0");
		}
		return result;
	}

	@Override
	public Map<String, Object> getMembersWeekCount(DynamicDto dynamicDto) {
		Map<String, Object>  result = new HashMap<String, Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> newWeekCountList = new ArrayList<Map<String,Object>>();
		List dateXCounts=new ArrayList();
		Long city_id = dynamicDto.getCityId();
		String province_id = dynamicDto.getProvinceId();
		try{
			newWeekCountList = communityMembersDao.getWeekMembersCount(dynamicDto);//查询近7日每日新开社员数
			dateXCounts = reDate(7);
			String curDate = DateUtils.lastDate();
			newWeekCountList = getListByListMap(curDate,newWeekCountList);
			//查询当查询全国时，查询北京上海天津和其他
			if(city_id==null&&province_id==null){
				DynamicDto bjDto = new DynamicDto();
				bjDto.setEndDate(curDate);
				List<Map<String,Object>> lst_data_bj = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> lst_data_tj = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> lst_data_sh = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> communityMembersTCRateList = communityMembersDao.getWeekOtherMembersCount(bjDto);
				for (Map<String, Object> map : communityMembersTCRateList) {
					String cityno = String.valueOf(map.get("cityno"));
					if("0010".equals(cityno)){
						lst_data_bj.add(map);
					}else if("0022".equals(cityno)){
						lst_data_tj.add(map);
					}else if("0021".equals(cityno)){
						lst_data_sh.add(map);
					}
				}
				//北京近7日新增社员数
				lst_data_bj = getListByListMap(curDate,lst_data_bj);
				//天津近7日新增社员数
				lst_data_tj = getListByListMap(curDate,lst_data_tj);
				//上海近7日新增社员数
				lst_data_sh = getListByListMap(curDate,lst_data_sh);
				result.put("lst_data_bj", lst_data_bj);
				result.put("lst_data_tj", lst_data_tj);
				result.put("lst_data_sh", lst_data_sh);
			}
	         result.put("week_new_data", newWeekCountList);
	         JSONArray jsonDateX=(JSONArray)JSONArray.fromObject(dateXCounts);
	         result.put("jsonDateX",jsonDateX);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	List<Map<String,Object>> getListByListMap(String curDate,List<Map<String,Object>> newWeekCountList) throws ParseException{
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
           	lst_new_map.put("crtime", minDateStr.substring((minDateStr.indexOf("-")+1),minDateStr.length()));
           	lst_new_map.put("year_date", minDateStr.substring(0,(minDateStr.indexOf("-"))));
           	lst_new_map.put("newcount", 0);
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
         return newWeekCountList;
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

	@Override
	public Map<String, Object> queryTwoTwoOneGMVByWeek(DynamicDto dynamicDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> lst_data = communityMembersDao.queryTwoTwoOneGMVByWeek(dynamicDto);
		result.put("twoTwoOneData", lst_data);
		return result;
	}
	@Override
	public Map<String, Object> queryTwoTwoOneGMVByDay(DynamicDto dynamicDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> lst_data = communityMembersDao.queryTwoTwoOneGMVByDay(dynamicDto);
		result.put("twoTwoOneDayData", lst_data);
		return result;
	}

	@Override
	public Map<String, Object> queryTwoTwoOneGMVByHour(DynamicDto dynamicDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> lst_data = communityMembersDao.queryTwoTwoOneGMVByHour(dynamicDto);
		result.put("twoTwoOneHourData", lst_data);
		return result;
	}

	@Override
	public Map<String, Object> queryDataOfScatterplot(DynamicDto dynamicDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> lst_data = communityMembersDao.queryDataOfScatterplot(dynamicDto);
		result.put("twoTwoOneScatterplotData", lst_data);
		return result;
	}

	@Override
	public Map<String, Object> queryTwoTwoOneStoreCount(DynamicDto dynamicDto) {
		Map<String,Object> result = new HashMap<String,Object>();
		CommunityMembersDao communityMembersDao = (CommunityMembersDao)SpringHelper.getBean(CommunityMembersDao.class.getName());
		List<Map<String, Object>> lst_data = communityMembersDao.queryTwoTwoOneStoreCount(dynamicDto);
		result.put("twoTwoOneStoreCountData", String.valueOf(lst_data.get(0).get("store_count")));
		return result;
	}
}
