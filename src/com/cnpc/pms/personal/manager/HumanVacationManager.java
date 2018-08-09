package com.cnpc.pms.personal.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.entity.HumanReContent;
import com.cnpc.pms.personal.entity.HumanVacation;

public interface HumanVacationManager extends IManager{
	
	public Map<String, Object> queryMyProcessList(QueryConditions condition);
	
	public HumanVacation queryHumanVacationInfo(Long id);
	
	public HumanReContent update_hr_Audit_Re(HumanVacation humanVacation);
	public String update_process_status_re(Long vacationid);
	
	public HumanReContent update_hr_Audit(HumanVacation humanVacation);
	public String update_process_status(Long vacationid);
	
	
}
