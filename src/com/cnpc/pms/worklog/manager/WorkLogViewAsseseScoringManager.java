package com.cnpc.pms.worklog.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.worklog.entity.WorkLogViewAsseseScoring;

public interface WorkLogViewAsseseScoringManager extends IManager {
	public WorkLogViewAsseseScoring getWorkLogViewAsseseScoring(Long id);
}
