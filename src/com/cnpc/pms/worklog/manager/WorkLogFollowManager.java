package com.cnpc.pms.worklog.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.worklog.entity.WorkLogFollow;

public interface WorkLogFollowManager extends IManager{
	public WorkLogFollow saveWorkLogFollow(WorkLogFollow obj);
	public void deleteWorkLogFollow(Long id);
	//在session里先取得登录人ID，再通过人员Id获取其所关注
	public List<WorkLogFollow> getWorkLogFollowList();
	public  void deleteWorkLogFollowByFollowId(Long followId);
}
