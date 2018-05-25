package com.cnpc.pms.personal.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.StoreActivitiesDao;
import com.cnpc.pms.personal.dao.StoreCoverPersonDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.dao.VillageDao;
import com.cnpc.pms.personal.entity.StoreActivities;
import com.cnpc.pms.personal.entity.StoreCoverPerson;
import com.cnpc.pms.personal.manager.StoreActivitiesManager;
import com.cnpc.pms.personal.manager.StoreCoverPersonManager;

public class StoreCoverPersonManagerImpl extends BizBaseCommonManager implements StoreCoverPersonManager{

	@Override
	public Map<String, Object> saveStoreCoverPerson(List<Map<String, Object>> mapParam1,List<Map<String,Object>> mapParam2) {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			if(mapParam1.size() > 0 && mapParam2.size() > 0){
				for(int i = 0; i < mapParam1.size(); i++){
					StoreCoverPerson storeCoverPerson = new StoreCoverPerson();
					Map<String, Object> map1 = mapParam1.get(i);
					String cityname1 = map1.get("cityname")+"";
					storeCoverPerson.setCityname(cityname1);
					storeCoverPerson.setCommunity_person((Integer.parseInt(map1.get("community_person").toString())));
					storeCoverPerson.setAvg_community_person((Integer.parseInt(map1.get("avg_community_person").toString())));
					storeCoverPerson.setCivil_servants((Integer.parseInt(map1.get("civil_servants").toString())));
					storeCoverPerson.setGeneral_person((Integer.parseInt(map1.get("general_person").toString())));
					storeCoverPerson.setFolk_organization((Integer.parseInt(map1.get("folk_organization").toString())));
					storeCoverPerson.setCommunity_businesses((Integer.parseInt(map1.get("community_businesses").toString())));

					for(int z = 0; z < mapParam2.size(); z++){
						Map<String, Object> map2 = mapParam2.get(z);
						if(cityname1.equals(map2.get("cityname").toString())){
							storeCoverPerson.setAvg_wechant_crowd((Integer.parseInt(map2.get("avg_wechant_crowd").toString())));
							storeCoverPerson.setCrowd_person_count((Integer.parseInt(map2.get("crowd_person_count").toString())));
							storeCoverPerson.setCrowd_persons_count((Integer.parseInt(map2.get("crowd_persons_count").toString())));
							storeCoverPerson.setInteractive_person_count((Integer.parseInt(map2.get("interactive_person_count").toString())));
							storeCoverPerson.setInteractive_person_count_store((Integer.parseInt(map2.get("interactive_person_count_store").toString())));
							storeCoverPerson.setWechant_accounted_for_crowd((Integer.parseInt(map2.get("wechant_accounted_for_crowd").toString())));
							storeCoverPerson.setWechant_crowd((Integer.parseInt(map2.get("wechant_crowd").toString())));
							break;
						}
					}
					preObject(storeCoverPerson);
					this.saveObject(storeCoverPerson);
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
	public Map<String, Object> getNewStoreCoverPerson() {
		Map<String,Object> result = new HashMap<String,Object>();
		StoreCoverPersonDao storeCoverPersonDao = (StoreCoverPersonDao)SpringHelper.getBean(StoreCoverPersonDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		List<Map<String,Object>> storeCoverPersonInfo = storeCoverPersonDao.getStoreCoverPerson();
		List<Map<String,Object>> storeCountOfcity = storeDao.getStoreCountOfcity();
		List<Map<String,Object>> storeCountOfArea = storeDao.getStoreCountOfArea();
		result.put("storeCountOfcity", storeCountOfcity);
		result.put("storeCountOfArea", storeCountOfArea);
		//List<Map<String,Object>> findConVillageCountOfCity = villageDao.findConVillageCountOfCity();
		result.put("storeCoverPersonInfo", storeCoverPersonInfo);
		//result.put("findConVillageCountOfCity", findConVillageCountOfCity);
		return result;
	}

}
