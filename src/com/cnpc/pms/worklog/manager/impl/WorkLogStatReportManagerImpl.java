package com.cnpc.pms.worklog.manager.impl;

import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.worklog.dao.WorkLogStatReportDAO;
import com.cnpc.pms.worklog.manager.WorkLogStatReportManager;

public class WorkLogStatReportManagerImpl extends BizBaseCommonManager implements WorkLogStatReportManager{
	public WorkLogStatReportDAO getDao(){
		return (WorkLogStatReportDAO) super.getDao();
	}
	public void insertStatReportByYear(String logDate){
		this.getDao().insertStatReportByYear(logDate);
	}
	public void deleteStatReportByYear(String logDate){
		this.getDao().deleteStatReportByYear(logDate);
	}
	public void insertStatReportByMonth(String logDate){
		this.getDao().insertStatReportByMonth(logDate);
	}
	public void insertStatReportByWeek(String logDate){
		this.getDao().insertStatReportByWeek(logDate);
	}

}
