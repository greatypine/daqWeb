package com.cnpc.pms.personal.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.DistCareerCode;

public interface DistCareerCodeManager extends IManager{
	
	
	public DistCareerCode queryDistCareerCodeByName(String name);
	
	public List<DistCareerCode> queryAllDistCareerList();
	
	public DistCareerCode queryDistCareerCodeByCode(Long id);
	
	public DistCareerCode saveDistCareerCode(DistCareerCode distCareerCode);
	
	
	
}
