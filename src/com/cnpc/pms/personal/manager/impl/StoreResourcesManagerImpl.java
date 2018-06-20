package com.cnpc.pms.personal.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.dao.StoreResourcesDao;
import com.cnpc.pms.personal.entity.StoreResources;
import com.cnpc.pms.personal.manager.StoreResourcesManager;

public class StoreResourcesManagerImpl  extends BizBaseCommonManager implements StoreResourcesManager{

	@Override
	public Map<String, Object> saveStoreResource(List<Map<String, Object>> mapParam1,List<Map<String,Object>> mapParam2) {
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			if(mapParam1.size() > 0 && mapParam2.size() > 0){
				for(int i = 0; i < mapParam1.size(); i++){
					Map<String, Object> map = mapParam1.get(i);
					StoreResources storeResources = new StoreResources();
					storeResources.setSave_type(0);
					storeResources.setCityname(map.get("cityname").toString());
					storeResources.setHome_screen(Integer.parseInt(map.get("home_screen").toString()));
					storeResources.setHome_auxiliary_screen(Integer.parseInt(map.get("home_auxiliary_screen").toString()));
					storeResources.setOutdoor_electronic_screen(Integer.parseInt(map.get("outdoor_electronic_screen").toString()));
					storeResources.setRoll_up(Integer.parseInt(map.get("roll_up").toString()));
					storeResources.setPosters(Integer.parseInt(map.get("posters").toString()));
					storeResources.setCentral_pile_head(Integer.parseInt(map.get("central_pile_head").toString()));
					storeResources.setFloor_pile_head(Integer.parseInt(map.get("floor_pile_head").toString()));
					storeResources.setIndoor_interactive_area(Integer.parseInt(map.get("indoor_interactive_area").toString()));
					preObject(storeResources);
					this.saveObject(storeResources);
				}
				for(int z = 0; z < mapParam2.size(); z++){
					Map<String, Object> map = mapParam2.get(z);
					StoreResources storeResources = new StoreResources();
					storeResources.setSave_type(1);
					storeResources.setCityname(map.get("cityname").toString());
					storeResources.setAssociated_community_count(Integer.parseInt(map.get("associated_community_count").toString()));
					storeResources.setFrame_number(Integer.parseInt(map.get("frame_number").toString()));
					storeResources.setOpen_community_count(Integer.parseInt(map.get("open_community_count").toString()));
					storeResources.setClosed_community_count(Integer.parseInt(map.get("closed_community_count").toString()));			
					storeResources.setActivity_area_count(Integer.parseInt(map.get("activity_area_count").toString()));
					storeResources.setCharge_for_site_count(Integer.parseInt(map.get("charge_for_site_count").toString()));
					storeResources.setFree_for_site_count(Integer.parseInt(map.get("free_for_site_count").toString()));
					storeResources.setSite_area(Integer.parseInt(map.get("site_area").toString()));
					storeResources.setAssociated_community_town_count(Integer.parseInt(map.get("associated_community_town_count").toString()));
					preObject(storeResources);
					this.saveObject(storeResources);
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
	public Map<String, Object> findStoreResourcesByType() {
		Map<String,Object> result = new HashMap<String, Object>();
		StoreResourcesDao storeResourcesDao = (StoreResourcesDao)SpringHelper.getBean(StoreResourcesDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		//店内资源统计
		List<Map<String, Object>> storeResource = storeResourcesDao.findStoreResourcesByType(0);
		if(storeResource != null && storeResource.size() > 0 ){
			result.put("storeResource",storeResource);
		}else{
			List<Map<String,Object>> storeResourceCity = storeDao.getStoreCity();
			result.put("storeResourceCity",storeResourceCity);
		}
		//户外点位资源统计
		List<Map<String, Object>> outStoreResource = storeResourcesDao.findStoreResourcesByType(1);
		if(outStoreResource != null && outStoreResource.size() > 0 ){
			result.put("outStoreResource",outStoreResource);
		}else{
			List<Map<String,Object>> outStoreResourceCity = storeDao.getStoreCity();
			result.put("outStoreResourceCity",outStoreResourceCity);
		}
		return result;
	}

}
