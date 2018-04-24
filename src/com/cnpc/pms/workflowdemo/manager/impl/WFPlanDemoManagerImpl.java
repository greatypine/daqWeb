package com.cnpc.pms.workflowdemo.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.workflow.dto.WFParam;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.entity.WFInstanceStepPos;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;
import com.cnpc.pms.workflow.wfinstance.manager.WFInstanceManager;
import com.cnpc.pms.workflow.wfinstance.manager.WFSystemManager;
import com.cnpc.pms.workflowdemo.dto.WFStepDto;
import com.cnpc.pms.workflowdemo.entity.WFPlanDemo;
import com.cnpc.pms.workflowdemo.manager.WFPlanDemoManager;

public class WFPlanDemoManagerImpl extends BaseManagerImpl implements
		WFPlanDemoManager {

	public WFPlanDemo addWFplanDemo(WFPlanDemo obj) {
		super.saveObject(obj);
		return obj;
	}

	public Boolean deleteDemo(Long id) {
		super.removeObjectById(id);
		return true;
	}

	public WFPlanDemo queryPlanDemo(Long id) {
		WFPlanDemo wfPlanDemo = (WFPlanDemo) super.getObject(id);
		return wfPlanDemo;
	}

	public WFPlanDemo saveWFPlanDemo(WFPlanDemo demo) {
		WFPlanDemo dbObj = null;
		System.out.println(demo.getStartDate());
		if (demo.getSheetId() == null) {
			UserSession userSession = SessionManager.getUserSession();
			Map sessionData = userSession.getSessionData();
			User user = (User) sessionData.get("user");
			System.out.println(user.getName());
			demo.setInformant(user.getName());
			dbObj = demo;
		} else {
			UserSession userSession = SessionManager.getUserSession();
			Map sessionData = userSession.getSessionData();
			User user = (User) sessionData.get("user");
			System.out.println(user.getName());
			demo.setInformant(user.getName());
			demo.setId(demo.getSheetId());
			dbObj = (WFPlanDemo) super.getObject(demo.getId());
			BeanUtils.copyProperties(demo, dbObj, new String[] { "id",
					"version" });
		}
		Map<String, String> args = new HashMap<String, String>();
		if (demo.getTotalfunding() != null) {
			args.put("@TOTALFUND", (demo.getTotalfunding()).toString());
		}
		if (demo.getTbdw() != null) {
			args.put("@tbdw", (demo.getTbdw()).toString());
		}
		if (demo.getTjlx() != null) {
			args.put("@tjlx", (demo.getTjlx()).toString());
		}

		super.saveObject(dbObj);
		System.out.println("表单id" + dbObj.getId());
		WFParam wfParam = new WFParam();
		wfParam.setArgs(args);
		System.out.println("部门Id是" + dbObj.getDeptId());
		wfParam.setDeptId(dbObj.getDeptId());
		System.out.println("业务模块是：" + dbObj.getModuleCode());
		wfParam.setModuleCode(dbObj.getModuleCode());
		WFInstanceManager manager = (WFInstanceManager) SpringHelper
				.getBean("WFInstanceManager");
		//WFFlow wfFlow = manager.findWorkFlow(wfParam);
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		System.out.println("操作人是：" + sessionData.get("userId"));
		wfParam.setOperId((Long) sessionData.get("userId"));
		wfParam.setToOperId((Long) sessionData.get("userId"));
		//System.out.println("流程模板Id" + wfFlow.getId());
		//wfParam.setFlowId(wfFlow.getId());
		System.out.println("表单id" + dbObj.getId());
		wfParam.setSheetName(demo.getProjectName());
		wfParam.setSheetId(dbObj.getId());
		if (manager.getFlowInstanceByPara(wfParam) == null) {
			manager.startWorkFlow(wfParam);
		} else {

		}
		return demo;
	}

	/**
	 * 获取执行步骤列表
	 */
//	public List<WFStepDto> getStepNoRecordList(Long id) {
//		// TODO Auto-generated method stub
//		WFInstanceRecordManager recordManager = (WFInstanceRecordManager) SpringHelper
//				.getBean("WFInstanceRecordManager");
//		List<WFInstanceRecord> recordList = recordManager.getInstanceRecord(id);
//		WFStepInstanceManager stepManager = (WFStepInstanceManager) SpringHelper
//				.getBean("WFStepInstanceManager");
//		CnpcPositionManager positionManager = (CnpcPositionManager) SpringHelper
//				.getBean("cnpcPositionManager");
//		List<WFStepInstance> stepList = stepManager.getStepInstanceList(id);
//		List<WFStepInstance> stepListNew = new ArrayList<WFStepInstance>();
//		UserManager userManager = (UserManager) SpringHelper
//				.getBean("userManager");
//		List<WFStepDto> stepDto = new ArrayList<WFStepDto>();
//		for (int i = 0; i < stepList.size(); i++) {
//			Boolean flag = false;
//			for (int j = 0; j < recordList.size(); j++) {
//				if (stepList.get(i).getId().equals(
//						recordList.get(j).getStepId())) {
//					flag = true;
//					WFStepDto tempDto = new WFStepDto();
//					tempDto.setStepName(stepList.get(i).getStepName());
//					if (recordList.get(j).getIsPassed() == 1) {
//						tempDto.setResult("通过");
//					} else if (recordList.get(j).getIsPassed() == 0) {
//						tempDto.setResult("退回");
//					} else if (recordList.get(j).getIsPassed() == null) {
//						tempDto.setResult("空");
//					}
//					if (recordList.get(j).getMemo() == null) {
//						tempDto.setAdvice("无");
//					} else {
//						tempDto.setAdvice(recordList.get(j).getMemo());
//					}
//					tempDto.setNo(stepList.get(i).getStepNo());
//					tempDto.setTime(recordList.get(j).getOperTime());
//					tempDto.setPosId(recordList.get(j).getOperName());
//					stepDto.add(tempDto);
//				}
//			}
//			if (flag == false) {
//				String posString = "";
//				stepListNew.add(stepList.get(i));
//				WFStepDto tempDto = new WFStepDto();
//				tempDto.setStepName(stepList.get(i).getStepName());
//				tempDto.setNo(stepList.get(i).getStepNo());
//				tempDto.setResult("");
//				tempDto.setAdvice("");
//				Set<WFInstanceStepPos> posList = stepList.get(i)
//						.getStepInstToPosSet();
//				Iterator<WFInstanceStepPos> it = posList.iterator();
//				while (it.hasNext()) {
//					Long k = it.next().getPosId();
//					CnpcPositionDTO c = positionManager.getPositionById(k);
//					posString += c.getName();
//				}
//				tempDto.setPosId(posString);
//				stepDto.add(tempDto);
//			}
//		}
//		return stepDto;
//	}

	public WFFlowInstance getFlowInstance(WFParam para) {
		WFInstanceManager manager = (WFInstanceManager) SpringHelper
				.getBean("WFInstanceManager");
		WFFlowInstance wfFlowInstance = manager.getFlowInstanceByPara(para);
		return wfFlowInstance;

	}

	public Boolean deleteSheetAndIns(WFParam para) {
		WFInstanceManager manager = (WFInstanceManager) SpringHelper
				.getBean("WFInstanceManager");
		WFFlowInstance wfFlowInstance = manager.getFlowInstanceByPara(para);
		super.removeObjectById(para.getSheetId());
		
		//删除流程实例的方法,现在移动到另一个Manager里面去了.
		WFSystemManager manager2 = (WFSystemManager)SpringHelper.getBean("WFSystemManager");
		manager2.deleteFlowInstance(wfFlowInstance);
		return true;
	}

	public WFPlanDemo saveWFPlanDemoAgain(WFPlanDemo demo) {
		WFPlanDemo dbObj = null;
		System.out.println(demo.getStartDate());
		if (demo.getSheetId() == null) {
			UserSession userSession = SessionManager.getUserSession();
			Map sessionData = userSession.getSessionData();
			User user = (User) sessionData.get("user");
			System.out.println(user.getName());
			demo.setInformant(user.getName());
			dbObj = demo;
		} else {
			demo.setId(demo.getSheetId());
			dbObj = (WFPlanDemo) super.getObject(demo.getId());
			BeanUtils.copyProperties(demo, dbObj, new String[] { "id",
					"version" });
		}
		Map<String, String> args = new HashMap<String, String>();
		if (demo.getTotalfunding() != null) {
			args.put("@TOTALFUND", (demo.getTotalfunding()).toString());
		}
		if (demo.getTbdw() != null) {
			args.put("@tbdw", (demo.getTbdw()).toString());
		}
		if (demo.getTjlx() != null) {
			args.put("@tjlx", (demo.getTjlx()).toString());
		}

		super.saveObject(dbObj);
		System.out.println("表单id" + dbObj.getId());
		WFParam wfParam = new WFParam();

		System.out.println("部门Id是" + dbObj.getDeptId());
		wfParam.setDeptId(dbObj.getDeptId());
		System.out.println("业务模块是：" + dbObj.getModuleCode());
		wfParam.setModuleCode(dbObj.getModuleCode());
		WFInstanceManager manager = (WFInstanceManager) SpringHelper
				.getBean("WFInstanceManager");
		//WFFlow wfFlow = manager.findWorkFlow(wfParam);
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		wfParam.setArgs(args);
		wfParam.setOperId((Long) sessionData.get("userId"));
		wfParam.setToOperId((Long) sessionData.get("userId"));
		
		//wfParam.setFlowId(wfFlow.getId());
		wfParam.setSheetId(dbObj.getId());
		WFFlowInstance wfFlowInstance = manager.getFlowInstanceByPara(wfParam);
		System.out.println("Instance的Id是" + wfFlowInstance.getId());
		wfParam.setFlowInstanceId(wfFlowInstance.getId());
		manager.startWorkFlow(wfParam);
		return demo;

	}

//	public WFStepInstance getStepType(Long id) {
//		WFInstanceManager manager = (WFInstanceManager) SpringHelper
//				.getBean("WFInstanceManager");
//		WFFlowInstance wfFlowInstance = manager.getFlowInstance(id);
//		Long stepid = wfFlowInstance.getCurrentStepId();
//		WFStepInstanceManager manager1 = (WFStepInstanceManager) SpringHelper
//				.getBean("WFStepInstanceManager");
//		WFStepInstance wfStepInstance = manager1.readStepInstance(stepid);
//
//		return wfStepInstance;
//	}

	public WFPlanDemo saveSheet(WFPlanDemo demo) {
		WFPlanDemo dbObj = null;
		System.out.println(demo.getStartDate());
		if (demo.getSheetId() == null) {
			UserSession userSession = SessionManager.getUserSession();
			Map sessionData = userSession.getSessionData();
			User user = (User) sessionData.get("user");
			System.out.println(user.getName());
			demo.setInformant(user.getName());
			dbObj = demo;
		} else {
			demo.setId(demo.getSheetId());
			dbObj = (WFPlanDemo) super.getObject(demo.getId());
			BeanUtils.copyProperties(demo, dbObj, new String[] { "id",
					"version" });
		}

		super.saveObject(dbObj);

		return demo;
	}

	public Boolean submitWfDemo(WFParam wf) {
		// TODO Auto-generated method stub
		Map<String, String> args = new HashMap<String, String>();
		Long sheetId = wf.getSheetId();
		WFPlanDemoManager manager1 = (WFPlanDemoManager) SpringHelper
				.getBean("WFPlanDemoManager");
		WFPlanDemo wfPlanDemo = manager1.queryPlanDemo(sheetId);
		System.out.println(wfPlanDemo);
		if (wfPlanDemo.getTbdw() != null) {
			args.put("@tbdw", wfPlanDemo.getTbdw().toString());
		}
		if (wfPlanDemo.getTjlx() != null) {
			args.put("@tjlx", wfPlanDemo.getTjlx().toString());
		}
		wf.setArgs(args);
		WFInstanceManager manager = (WFInstanceManager) SpringHelper
				.getBean("WFInstanceManager");
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		System.out.println("操作人是：" + sessionData.get("userId"));
		wf.setOperId((Long) sessionData.get("userId"));
		wf.setToOperId((Long) sessionData.get("userId"));
		//增加一个判断
		//如果是在开始节点,则重新启动工作流
		//否则直接调用工作流
		WFFlowInstance inst = manager.getFlowInstanceByPara(wf);
		if(inst.getToDoOper()!=null){
			//设置启动工作流的一些参数
			wf.setFlowId(20620L);
			wf.setDeptId(3304L);
			manager.startWorkFlow(wf);
		}else{
			manager.submitWorkFlow(wf);
		}
		return true;
	}

}
