package com.cnpc.pms.workflowdemo.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.dto.WFParam;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflowdemo.dto.WFStepDto;
import com.cnpc.pms.workflowdemo.entity.WFPlanDemo;

public interface WFPlanDemoManager extends IManager {
	public WFPlanDemo addWFplanDemo(WFPlanDemo obj);

	public WFPlanDemo saveSheet(WFPlanDemo demo);

	public Boolean deleteDemo(Long id);

	public WFPlanDemo queryPlanDemo(Long id);

	public WFPlanDemo saveWFPlanDemo(WFPlanDemo demo);

	public WFPlanDemo saveWFPlanDemoAgain(WFPlanDemo demo);

//	public List<WFStepDto> getStepNoRecordList(Long id);

	public WFFlowInstance getFlowInstance(WFParam para);

	public Boolean deleteSheetAndIns(WFParam para);

//	public WFStepInstance getStepType(Long id);

	public Boolean submitWfDemo(WFParam wf);
}
