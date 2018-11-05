package com.cnpc.pms.personal.manager;

import java.util.List;

import com.cnpc.pms.personal.entity.DistCareer;

public interface DistCareerManager {

	public DistCareer queryDistCareersByUserId(Long userid);

	public DistCareer updateDistCareer(DistCareer DistCareer);

	public List<DistCareer> queryDistCareerListByUserId(Long userid);

	public List<Long> queryDistinctUserId();

	public List<Long> queryDistinctByCareer(String career);

	public List<?> queryDistCareerCount();

	public void saveDistCareer(DistCareer d);

	public List<DistCareer> queryDistCareerByUserIdCareer(Long userid, String careername);

	// 根据当前登录人的id和城市选择城市
	public DistCareer findDistCareerBycareername(String careername);

}
