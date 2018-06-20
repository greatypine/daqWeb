package com.cnpc.pms.personal.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.StoreActivitiesDao;
import com.cnpc.pms.personal.dao.VillageDao;
import com.cnpc.pms.personal.entity.StoreActivities;
import com.cnpc.pms.personal.manager.StoreActivitiesManager;

public class StoreActivitiesManagerImpl extends BizBaseCommonManager implements StoreActivitiesManager{

	@Override
	public Map<String, Object> saveStoreActivities(List<Map<String, Object>> mapParam) {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			if(mapParam.size() > 0){
				for(int i = 0; i < mapParam.size(); i++){
					StoreActivities storeActivities = new StoreActivities();
					Map<String, Object> map = mapParam.get(i);
					
					storeActivities.setAvtivities_count(Integer.parseInt(map.get("avtivities_count").toString()));
					storeActivities.setCityname(map.get("cityname")+"");
					storeActivities.setNumbers_of_activities(Integer.parseInt(map.get("numbers_of_activities").toString()));
					storeActivities.setNumbers_of_single_activitie(Integer.parseInt(map.get("numbers_of_single_activitie").toString()));
					storeActivities.setPublic_welfare_activities(Integer.parseInt(map.get("public_welfare_activities").toString()));
					storeActivities.setRecreational_activities(Integer.parseInt(map.get("recreational_activities").toString()));
					storeActivities.setVolunteer_activity(Integer.parseInt(map.get("volunteer_activity").toString()));
					//
					storeActivities.setStore_independent_activitie(Integer.parseInt(map.get("store_independent_activitie").toString()));
					storeActivities.setStore_numbers_of_activities(Integer.parseInt(map.get("store_numbers_of_activities").toString()));
					storeActivities.setStore_numbers_of_single_activitie(Integer.parseInt(map.get("store_numbers_of_single_activitie").toString()));
					storeActivities.setStore_independent_activitie_price(Integer.parseInt(map.get("store_independent_activitie_price").toString()));
					storeActivities.setTotal_activities_count(Integer.parseInt(map.get("total_activities_count").toString()));
					preObject(storeActivities);
					this.saveObject(storeActivities);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "-1");
			result.put("message", "update fail");
			return result;
		}
		
		result.put("code", "1");
		result.put("message", "update success");
		return result;
	}

	@Override
	public Map<String, Object> getNewStoreActivtiesInfo() {
		Map<String,Object> result = new HashMap<String,Object>();
		StoreActivitiesDao storeActivitiesDao = (StoreActivitiesDao)SpringHelper.getBean(StoreActivitiesDao.class.getName());
		VillageDao villageDao = (VillageDao)SpringHelper.getBean(VillageDao.class.getName());
		List<Map<String,Object>> newActivitiesInfo = storeActivitiesDao.getNewActivitiesInfo();
		//List<Map<String,Object>> findConVillageCountOfCity = villageDao.findConVillageCountOfCity();
		result.put("newActivitiesInfo", newActivitiesInfo);
		//result.put("findConVillageCountOfCity", findConVillageCountOfCity);
		return result;
	}

}
