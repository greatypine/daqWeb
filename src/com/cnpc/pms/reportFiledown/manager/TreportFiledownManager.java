package com.cnpc.pms.reportFiledown.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import java.util.List;
import java.util.Map;


public interface TreportFiledownManager extends IManager{

    public List<TReportFiledown> selectReportFileDowns(String tableLogic);

    public void updateReportFileDowns(TReportFiledown tReportFiledown);

	

}