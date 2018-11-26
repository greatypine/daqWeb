package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.dto.CareerChannelDto;
import com.cnpc.pms.personal.entity.OnLineHumanresources;
import com.cnpc.pms.personal.entity.SyncRecord;

public interface OnLineHumanresourcesManager extends IManager{
	
	public OnLineHumanresources saveOnlineHuman(SyncRecord syncRecord);
	
	public Map<String, Object> queryRegOnLineHumanList(QueryConditions condition);
	
	public  List<Map<String, Object>>  queryCareerChannel();
	
	public  OnLineHumanresources  saveRegOnLineHumanGroup(CareerChannelDto careerChannelDto);
	
	public CareerChannelDto queryCareerChannelById(Long id);
}
