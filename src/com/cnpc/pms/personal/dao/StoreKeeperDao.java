package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.entity.ImportHumanresources;
import com.cnpc.pms.personal.entity.YyMicrData;

public interface StoreKeeperDao {

	public String queryMaxNo(String type);
	
	//取得最大邀请码
	public String queryMaxInviteCode();
	
	
}