package com.cnpc.pms.personal.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.personal.entity.HumanReContent;
import com.cnpc.pms.personal.entity.HumanVacation;

public interface HumanVacationManager extends IManager{
	
	public Map<String, Object> queryMyProcessList(QueryConditions condition);
	
	public HumanVacation queryHumanVacationInfo(Long id);
	
	//HR驳回
	public HumanReContent update_hr_Audit_Re(HumanVacation humanVacation);
	public String update_process_status_re(Long vacationid);
	//HR通过
	public HumanReContent update_hr_Audit(HumanVacation humanVacation);
	public String update_process_status(Long vacationid);
	
	//运营经理 驳回
	public HumanReContent update_rm_Audit_Re(HumanVacation humanVacation);
	public String update_rm_process_status_re(Long vacationid);
	//运营经理通过 
	public HumanReContent update_rm_Audit(HumanVacation humanVacation);
	public String update_rm_process_status(Long vacationid);
	
	
	
	//门店总监通过
	public HumanReContent update_zj_Audit(HumanVacation humanVacation);
	public String update_zj_process_status(Long vacationid);
	//门店总监驳回
	public HumanReContent update_zj_Audit_Re(HumanVacation humanVacation);
	public String update_zj_process_status_re(Long vacationid);
	
}
