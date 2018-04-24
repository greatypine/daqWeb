package com.cnpc.pms.worklog.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.worklog.entity.WorkLogFollow;

public interface WorkLogFollowDAO extends IDAO{
	public WorkLogFollow getPosAndOrg(Long userId);
	public Long checkIsFollowed(Long userId,Long followId);
}
