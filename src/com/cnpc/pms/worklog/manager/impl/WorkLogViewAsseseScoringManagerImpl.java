package com.cnpc.pms.worklog.manager.impl;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.worklog.entity.WorkLogViewAsseseScoring;
import com.cnpc.pms.worklog.manager.WorkLogViewAsseseScoringManager;

public class WorkLogViewAsseseScoringManagerImpl extends BaseManagerImpl implements WorkLogViewAsseseScoringManager{

	public WorkLogViewAsseseScoring getWorkLogViewAsseseScoring(Long id) {
		WorkLogViewAsseseScoring w =(WorkLogViewAsseseScoring)super.getObject(id);
		return w;
	}
	
}
