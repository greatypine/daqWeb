package com.cnpc.pms.workflow.wfinstance.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.orgview.manager.PurStruOrgManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.dto.ToDoByModule;
import com.cnpc.pms.workflow.dto.WFCopyParam;
import com.cnpc.pms.workflow.dto.WFParam;
import com.cnpc.pms.workflow.entity.WFFlowVariable;
import com.cnpc.pms.workflow.entity.WFCopyInfo;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFFlowOrg;
import com.cnpc.pms.workflow.entity.WFInsPersonCopy;
import com.cnpc.pms.workflow.entity.WFInsPositionCopy;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.entity.WFInstanceStepPos;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.entity.WFFlowStepToPos;
import com.cnpc.pms.workflow.entity.WFToDoView;
import com.cnpc.pms.workflow.entity.WFFlowTransition;
import com.cnpc.pms.workflow.entity.WFInstanceTransition;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WFCopyInfoManager;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFFlowManager;
import com.cnpc.pms.workflow.manager.WFInsPersonCopyManager;
import com.cnpc.pms.workflow.manager.WFInsPositionCopyManager;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;
import com.cnpc.pms.workflow.manager.WFInstanceStepPosManager;
import com.cnpc.pms.workflow.manager.WFInstanceVariableManager;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;
import com.cnpc.pms.workflow.manager.WFFlowStepManager;
import com.cnpc.pms.workflow.manager.WFInstanceTransitionManager;
import com.cnpc.pms.workflow.manager.WFFlowTransitionManager;
import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.util.WFManagerHelper;
import com.cnpc.pms.workflow.util.WFStepTypeConst;
import com.cnpc.pms.workflow.util.WFSubmitConst;
import com.cnpc.pms.workflow.util.WFSubmitOperTypeConst;
import com.cnpc.pms.workflow.wfinstance.manager.WFInstanceManager;

public class WFInstanceManagerImpl extends BaseManagerImpl implements
		WFInstanceManager {

	/**
	 * 根据给定部门和业务模块来检索可用流程列表<br>
	 * 输入:业务部门ID,<br>
	 * 输入:业务模块Code ,ModuleCode<br>
	 * 
	 * @param para
	 * @return 找不到的情况返回null,如果ModuleCode不对,抛出异常.
	 */
	public WFFlow findWorkFlow(WFParam para) {
		// Step1:先对输入参数的有效性进行校验,如果校验失败,抛出异常
		if (para.getModuleCode() == null) {
			throw new WFException("调用参数错误, 给定模块编号不可为空!");
		}
		if (para.getDeptId() == null) {
			throw new WFException("调用参数错误，给定部门编号不可为空!");
		}

		// Step2： 需要通过moduleCode先获取到moduleId封装进WFParam,再进行调用
		WFModuleManager manager = WFManagerHelper.getWFModuleManager();
		Long moduleId = (Long) manager.getModuleIdByCode(para.getModuleCode());

		if (moduleId == null) {
			throw new WFException("参数错误,给定的模块编号无效:" + para.getModuleCode());
		} else if (moduleId.equals(0L)) {
			throw new WFException("参数错误,给定的模块编号无效:" + para.getModuleCode());
		}

		para.setModuleId(moduleId);

		// step3:调用另一个方法来获取可用流程定义.
		WFFlow flow1 = _findWorkFlow(para.getDeptId(), para.getModuleId());

		// step4:如果获取的可用流程定义为null,则直接抛出异常信息
		if (flow1 == null) {
			throw new WFException(
					"查找可用流程定义失败,流程可能不存在或者未配置可用部门。deptid,moduleId="
							+ para.getDeptId() + "," + para.getModuleId());
		} else {
			return flow1;
		}
	}

	/**
	 * 根据指定所Id和模块Id来查找可用的工作流定义
	 * 
	 * @param deptId
	 *            所Id
	 * @param moduleId
	 *            模块Id
	 * @return
	 */
	private WFFlow _findWorkFlow(Long deptId, Long moduleId) {
		// Step1: 检索数据,并计算可用流程
		WFFlowManager flowManager = WFManagerHelper.getWFFlowManager();
		// 按照模块ID来获取所有的流程定义.仅获取状态为有效的流程定义
		List<WFFlow> flowList = flowManager.getFlowListByModuleId(moduleId);
		List<WFFlow> flows = new ArrayList<WFFlow>();
		for (WFFlow flow : flowList) {
			// 获取流程定义下属的可用部门的列表信息,检查制定的部门是否在其中
			Set<WFFlowOrg> orgSet = flow.getVarFlowToOrg();
			for (WFFlowOrg flowOrg : orgSet) {
				{
					if (flowOrg.getOrgId().equals(deptId))
						flows.add(flow);
				}
			}
		}
		// Step2.执行完毕,返回结果
		if (flows.size() > 0)
			return flows.get(0);
		else
			return null;
	}

	/**
	 * 按照Id获取流程实例的数据
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	public WFFlowInstance getFlowInstance(Long flowInstanceId) {
		WFFlowInstanceManager flowInstanceManager = (WFFlowInstanceManager) SpringHelper
				.getBean("WFFlowInstanceManager");
		return flowInstanceManager.queryFlowInstance(flowInstanceId);
	}

	/**
	 * 根据条件来检索工作流实例
	 */
	public WFFlowInstance getFlowInstanceByPara(WFParam para) {
		// Step1:检查输入参数的合法性校验
		if (para.getModuleCode() == null) {
			throw new WFException("参数错误,给定的模块编号不可为空!");
		}
		if (para.getSheetId() == null) {
			throw new WFException("参数错误,给定的表单Id不可为空!");
		}

		// Step2:检查模块编号的合法性,得到模块Id号
		// 需要通过moduleCode先获取到moduleId封装进WFParam,再进行调用

		WFFlowInstanceManager flowInstanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFModuleManager manager = WFManagerHelper.getWFModuleManager();

		Long moduleId = (Long) manager.getModuleIdByCode(para.getModuleCode());
		if (moduleId == null) {
			throw new WFException("参数错误,给定的模块编号无效:" + para.getModuleCode());
		}
		para.setModuleId(moduleId);

		// Step3:检索流程实例对象,并返回
		WFFlowInstance flowinstance = flowInstanceManager.getFlowInstance(para
				.getModuleId(), para.getSheetId());
		return flowinstance;
	}

	/**
	 * 启动工作流
	 */
	public WFFlowInstance startWorkFlow(WFParam para) {
		// Step1:进行必要的数据有效性检查
		if (para.getModuleCode() == null) {
			throw new WFException("调用参数错误, 给定模块编号不可为空!");
		}
		// 启动部门的检查判断
		if (para.getDeptId() == null) {
			throw new WFException("参数错误,给定的启动部门Id不可为空!");
		}
		if (para.getDeptId().equals(0L)) {
			throw new WFException("参数错误,给定的启动部门Id不可为零!");
		}
		// 启动人的检查判断
		if (para.getOperId() == null) {
			throw new WFException("参数错误,给定的启动人Id不可为空!");
		}
		if (para.getOperId().equals(0L)) {
			throw new WFException("参数错误,给定的启动人Id不可为零!");
		}
		// SheetId的检查判断
		if (para.getSheetId() == null) {
			throw new WFException("参数错误,给定的表单Id不可为空!");
		}
		if (para.getSheetId().equals(0L)) {
			throw new WFException("参数错误,给定的表单Id不可为零!");
		}
		// SheetName的检查判断
		if (para.getSheetName() == null) {
			throw new WFException("参数错误,给定的表单名称不可为空!");
		}
		para.setToOperId(para.getOperId());

		// Step2： 需要通过moduleCode先获取到moduleId封装进WFParam,再进行调用
		WFModuleManager manager = WFManagerHelper.getWFModuleManager();
		Long moduleId = (Long) manager.getModuleIdByCode(para.getModuleCode());
		if (moduleId == null) {
			throw new WFException("参数错误,给定的模块编号无效:" + para.getModuleCode());
		}
		// 设置ModuleId
		para.setModuleId(moduleId);

		// step3:调用另一个方法来获取可用流程定义.
		WFFlow flowdefine = _findWorkFlow(para.getDeptId(), para.getModuleId());
		if (flowdefine == null) {
			throw new WFException("启动流程失败。找不到对应的流程定义:模块编号,部门id="
					+ para.getModuleCode() + "," + para.getDeptId());
		}
		// 设置FlowId
		para.setFlowId(flowdefine.getId());

		// Step4:调用真正的实现类
		return _startWorkFlow(para.getFlowInstanceId(), para.getFlowId(), para
				.getDeptId(), para.getOperId(), para.getToOperId(), para
				.getModuleId(), para.getSheetId(), para.getSheetName(), para
				.getMemo(), para.getArgs());
	}

	/**
	 * 启动工作流,本地调用方法
	 * 
	 * @param instanceid
	 *            流程实例Id,再次启动时有效
	 * @param flowid
	 *            流程Id,不可为空
	 * @param deptid
	 *            启动部门,应该是一个所
	 * @param operId
	 *            操作人Id
	 * @param toOperId
	 *            代操作人Id,启动时应该与operId一致
	 * @param moduleId
	 *            模块Id,不可为空
	 * @param sheetId
	 *            表单Id,不可为空
	 * @param sheetName
	 *            表单名称,不可为空
	 * @param memo
	 *            备注
	 * @param args
	 *            流程启动时的参数Map
	 * @return
	 */
	private WFFlowInstance _startWorkFlow(Long instanceid, Long flowid,
			Long deptid, Long operId, Long toOperId, Long moduleId,
			Long sheetId, String sheetName, String memo,
			Map<String, String> args) {
		// Step1、根据模板号检索流程模板及相关信息
		// 按照OneToMany关系自动获取相关信息
		WFFlowManager flowManager = WFManagerHelper.getWFFlowManager();
		WFFlow flow = flowManager.queryFlow(flowid);
		// 2、将流程模板的相关信息拷贝到流程实例相关表中
		// 复制流程实例
		WFFlowInstanceManager flowInstanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFFlowInstance flowInstance = new WFFlowInstance();
		BeanUtils.copyProperties(flow, flowInstance, new String[] { "id",
				"version" });

		PurStruOrgManager orgmanager = (PurStruOrgManager) SpringHelper
				.getBean("purStruOrgManager");
		PurStruOrg org = orgmanager.getPurStruOrgEntityById(deptid);
		// 0代表是机关单位，例如科研管理处
		if ("0".equals(org.getEntityOrgFlag())) {
			// 设置启动机构的ID
			flowInstance.setOrgId(deptid);
		} else if ("2".equals(org.getEntityOrgFlag())) {
			// 2代表是所级机构
			flowInstance.setOrgId(deptid);
		} else {
			// 其他情况只考虑是所级机构下一级,也就是室一级的情况
			org = org.getParentEntity();
			if (org != null && "2".equals(org.getEntityOrgFlag())) {
				flowInstance.setOrgId(org.getId());
			} else {
				log.error("流程启动单位非所级及其下级单位，应抛出异常");
				throw new WFException("流程启动单位非所级及其下级单位.deptid=" + deptid);

			}
		}

		flowInstance.setPersonId(operId); // 启动人
		flowInstance.setFlowId(flowid); // 流程Id
		flowInstance.setSheetId(sheetId); // 表单Id
		flowInstance.setSheetName(sheetName);// 表单名称

		// instanceid!=null代表是退回以后重复进行启动
		if (instanceid != null) {
			WFFlowInstance instancetemp = flowInstanceManager
					.queryFlowInstance(instanceid);
			if (instancetemp == null) {
				log.error("流程重新启动时未找到对应实例，抛出异常");
				throw new WFException("流程重新启动时未找到对应实例，抛出异常.instanceid="
						+ instanceid);
				// return null;
			}
			if (!operId.equals(instancetemp.getToDoOper())) {
				log.error("流程重新启动人不是流程首次启动人，抛出异常");
				throw new WFException("流程重新启动人不是流程首次启动人.operId=" + operId);
				// return null;
			}
			// 如果当前的流程实例当前步骤不是开始节点,则抛出异常
			if (!instancetemp.getCurrentStepType().equals(0L)) {
				throw new WFException("流程重新启动时,当前节点不是开始开始节点!instanceid="
						+ instanceid);
			}
			// 将flowInstance的相关信息复制到instancetemp里面去
			// 包括:deptid,operId,flowid,sheetId,sheetName
			BeanUtils.copyProperties(flowInstance, instancetemp, new String[] {
					"id", "version", "varInstSet", "stepInstSet",
					"recordInstSet", "toDoInstMemoSet" });
			flowInstance = instancetemp;
			// 逻辑删除实例下相关组件
			WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper
					.getBean("com.cnpc.pms.workflow.dao.WFInstanceDAO");
			instanceDAO.deleteInstanceById(instanceid);
		}

		// 这里的flowinstance已经和数据库同步过了,直接保存即可.
		flowInstanceManager.saveFlowInstance(flowInstance); // 保存或修改实例
		System.out.println("################flowInstance id:"
				+ flowInstance.getId());

		// 复制流程实例参数
		// 获取实例变量管理器
		WFInstanceVariableManager instanceVarManager = (WFInstanceVariableManager) SpringHelper
				.getBean(WFManagerConst.WF_INSTANCEVARIABLE);
		List<WFFlowVariable> argsList = new ArrayList<WFFlowVariable>(flow
				.getVarSet());
		instanceVarManager
				.copyInstanceVar(argsList, args, flowInstance.getId());

		List<WFFlowStep> stepListTemp = new ArrayList<WFFlowStep>(flow
				.getVarStep());
		List<WFFlowStep> stepList = new ArrayList<WFFlowStep>();
		Map<Long, WFFlowTransition> tranmap = new HashMap<Long, WFFlowTransition>();
		WFFlowStep curStep = null;

		// 增加流程中开始节点和结束节点的逻辑判断
		// 流程定义中只能有一个开始节点,一个结束节点
		// Add by Liu junsong 2013-11-6
		Long beginCount = flow.getStartStepCount();
		Long endCount = flow.getEndStepCount();
		if (beginCount == 0L) {
			throw new WFException("流程启动失败,流程定义有误:指定流程找不到开始节点.flow:"
					+ flow.getFlowName());
		}
		if (beginCount > 1L) {
			throw new WFException("流程启动失败,流程定义有误:指定流程存在多个开始节点.flow:"
					+ flow.getFlowName());
		}
		if (endCount == 0L) {
			throw new WFException("流程启动失败,流程定义有误:指定流程找不到结束节点.flow:"
					+ flow.getFlowName());
		}
		// 增加开始节点和结束节点的逻辑判断结束

		// 查找开始节点
		for (WFFlowStep ws : stepListTemp) {
			if (ws.getStepType().equals(WFStepTypeConst.BEGIN)) {
				curStep = ws;
				stepList.add(curStep);
				break;
			}
		}
		// 如果找不到开始节点,抛出异常
		if (curStep == null) {
			throw new WFException("流程启动失败,找不到开始步骤.");
		}
		// 获取流程转换的定义
		WFFlowTransitionManager transitionManager = (WFFlowTransitionManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOWTRANSITION);
		// 流程步骤的定义
		WFFlowStepManager stepManager = (WFFlowStepManager) SpringHelper
				.getBean(WFManagerConst.WF_FLOWSTEP);

		// 根据参数得到一条审批路径
		while (!curStep.getStepType().equals(WFStepTypeConst.END)) {
			WFFlowTransition tran = transitionManager.getTransitionByCondition(
					flowInstance.getId(), curStep.getId());
			// 如果获取转换为空,则抛出异常
			// 获取某一步骤的转换出错,将造成流程启动失败.
			if (tran == null) {
				throw new WFException("流程启动出错,请检查流程定义是否正确:获取转换条件出错,curStep:"
						+ curStep.getStepName() + ";流程模块Id" + moduleId);
			}
			tranmap.put(curStep.getId(), tran);
			WFFlowStep nextStep = (WFFlowStep) stepManager.getObject(tran
					.getEndId());
			// 如果下一结点为空,则抛出异常
			if (nextStep == null) {
				throw new WFException("流程启动出错,请检查流程定义是否正确。"
						+ "流程中定义的转换指向了一个不存在的步骤节点。获取下一节点出错,curStep:"
						+ curStep.getStepName());
			}
			stepList.add(nextStep);
			curStep = nextStep;
		}

		// 复制步骤实例
		WFStepInstanceManager stepInstanceManager = (WFStepInstanceManager) SpringHelper
				.getBean("WFStepInstanceManager");
		// 模版步骤与实例步骤对应关系mapStep
		Map<Long, Long> convertMap = stepInstanceManager.copyStep(stepList,
				flowInstance.getId());
		// 复制转换实例
		WFInstanceTransitionManager transitionInstanceManager = (WFInstanceTransitionManager) SpringHelper
				.getBean(WFManagerConst.WF_INSTANCETRANSITION);
		for (WFFlowStep step : stepList) {
			WFFlowTransition trantemp = tranmap.get(step.getId());
			if (trantemp != null) {
				transitionInstanceManager.addTransitionInstance(trantemp,
						convertMap, flowInstance.getId());
			}
		}
		// 复制实例步骤岗位
		WFInstanceStepPosManager instanceStepPosManager = (WFInstanceStepPosManager) SpringHelper
				.getBean("WFInstanceStepPosManager");
		for (WFFlowStep step : stepList) {
			List<WFFlowStepToPos> stepPosList = new ArrayList<WFFlowStepToPos>(
					step.getVarStepToPos());
			for (WFFlowStepToPos stepPos : stepPosList) {
				instanceStepPosManager.addInstanceStepPos(stepPos, convertMap);
			}
		}
		// 复制实例步骤人员抄送
		WFInsPersonCopyManager insPersonCopyManager = (WFInsPersonCopyManager) SpringHelper
				.getBean("WFInsPersonCopyManager");
		insPersonCopyManager.copyStepPerson(convertMap);
		// 复制实例步骤岗位抄送
		WFInsPositionCopyManager insPositionCopyManager = (WFInsPositionCopyManager) SpringHelper
				.getBean("WFInsPositionCopyManager");
		insPositionCopyManager.copyStepPosition(convertMap);

		// 3、添加的一条流程记录：开始节点的记录
		WFInstanceRecord instanceRecord = new WFInstanceRecord();
		System.out.println("################flowInstance id: "
				+ flowInstance.getId());
		instanceRecord.setFlowInstanceId(flowInstance.getId());

		List<WFStepInstance> stepInstList = stepInstanceManager
				.getStepInstanceList(flowInstance.getId());

		WFInstanceTransition tranInst = transitionInstanceManager
				.getFirstAction(stepInstList);
		if (tranInst == null) {
			log.error("启动流程：流程ID为" + flowInstance.getId()
					+ " 的流程找不到第一个步骤节点（Action)，应抛出异常");
			System.out.println("启动流程：流程ID为" + flowInstance.getId()
					+ " 的流程找不到第一个步骤节点（Action），应抛出异常");
			throw new WFException("启动流程：流程ID为" + flowInstance.getId()
					+ " 的流程找不到第一个步骤节点（Action)，应抛出异常");
			// return getFlowInstance(flowInstance.getId());
		}
		WFStepInstance firstStep = stepInstanceManager
				.readStepInstance(tranInst.getBeginId());
		UserManager userManager = (UserManager) SpringHelper
				.getBean("userManager");
		instanceRecord.setStepId(tranInst.getBeginId());
		instanceRecord.setModuleId(moduleId);
		instanceRecord.setSheetId(sheetId);
		instanceRecord.setOperNo(firstStep.getStepNo());
		instanceRecord.setOperTime(new Date());
		instanceRecord.setToOperId(toOperId);
		if (toOperId != null) {
			instanceRecord.setToOperName(userManager.getUserEntity(toOperId)
					.getName());
		}
		// 审批意见
		instanceRecord.setMemo(memo);
		instanceRecord.setOperId(operId);
		if (operId != null) {
			instanceRecord.setOperName(userManager.getUserEntity(operId)
					.getName());
		}
		// 执行描述
		instanceRecord.setNextStepId(tranInst.getEndId());
		instanceRecord.setIsPassed(new Long(1));
		WFInstanceRecordManager instanceRecordManager = (WFInstanceRecordManager) SpringHelper
				.getBean("WFInstanceRecordManager");
		instanceRecordManager.saveInstanceRecord(instanceRecord);
		return getFlowInstance(flowInstance.getId());
	}

	/**
	 * 提交工作流
	 */
	public WFFlowInstance submitWorkFlow(WFParam param) {
		// 增加一部分数据逻辑的判断
		this._checkNullValue(param.getFlowInstanceId(),
				"调用参数错误.flowInstanceId不可为空!");
		this._checkNullValue(param.getOperId(), "调用参数错误.operId不可为空!");
		this._checkNullValue(param.getToOperId(), "调用参数错误.toOperId不可为空!");
		this._checkNullValue(param.getIsPassed(), "调用参数错误.isPassed不可为空!");

		// 确保isPassed只在0,1之间取值
		if (!(param.getIsPassed().equals(0L) || param.getIsPassed().equals(1L))) {
			throw new WFException("调用参数错误.isPassed只能为0,1!"
					+ param.getIsPassed());
		}

		// 0代表审核不通过
		if (param.getIsPassed().equals(new Long("0"))) {
			if (param.getMemo() == null) {
				throw new WFException("调用参数错误.审核不通过时,memo不可为空!");
			}
			// 审核不通过时,强制设置流程的其他两个参数的默认值
			// OperType强制设置为0,代表走正常审批流程
			// nextStepId强制设置为0L,代表无意义数据
			param.setOperType(WFSubmitOperTypeConst.NORMAL_OPER);
			param.setNextStepId(0L);
		}

		// 给OperType设置默认值,如果为空,则默认为0L.
		if (param.getOperType() == null) {
			param.setOperType(0L);
			param.setNextStepId(null);
		}
		// this._checkNullValue(para.getOperType(), "调用参数错误.operType不可为空!");

		// 确保operType的取值在0,1,2,4之间
		if (!(param.getOperType().equals(0L) || param.getOperType().equals(1L)
				|| param.getOperType().equals(2L) || param.getOperType()
				.equals(4L))) {
			throw new WFException("调用参数错误.operType只能为0,1,2,4!"
					+ param.getOperType());
		}

		// operType=1L,代表强制跳转,此时nextStepId不可为空
		if (param.getOperType().equals(WFSubmitOperTypeConst.CHOICENXET_OPER)) {
			this._checkNullValue(param.getNextStepId(),
					"调用参数错误.强制跳转时,nextStepId不可为空!");
			if (param.getNextStepId().equals(0L)) {
				throw new WFException("调用参数错误.强制跳转时,nextStepId不可为零!");
			}
		}

		// Step1: 获取流程实例和当前步骤实例,并判断其数据有效性
		WFStepInstanceManager stepInstanceManager = WFManagerHelper
				.getWFStepInstanceManager();

		// 流程实例
		WFFlowInstance flowInstance = this.getFlowInstance(param
				.getFlowInstanceId());
		this._checkNullValue(flowInstance, "调用参数错误.指定的flowInstanceId无效."
				+ param.getFlowInstanceId());

		// 获取当前步骤实例curStepId
		WFStepInstance curStep = stepInstanceManager
				.readStepInstance(flowInstance.getCurrentStepId());
		this._checkNullValue(curStep, "参数错误,给定的流程步骤无效."
				+ flowInstance.getCurrentStepId());

		// 只有Action类型的当前步骤才可以提交工作流
		if (!curStep.getStepType().equals(WFStepTypeConst.ACTION)) {
			throw new WFException(WFException.WF_ERROR_CODE_ILLEGAL,
					"参数错误,当前流程的步骤类型不可提交!" + curStep.getStepType());
		}

		// operType代表采用何种方式来进行审批,走不同的子方法
		// 这个字段是设计用来做强制跳转的.
		if (param.getOperType().equals(WFSubmitOperTypeConst.NORMAL_OPER)
				|| param.getOperType()
						.equals(WFSubmitOperTypeConst.SYSTEM_OPER)
				|| param.getOperType().equals(
						WFSubmitOperTypeConst.CHOICENXET_OPER)
				|| param.getOperType()
						.equals(WFSubmitOperTypeConst.FINISH_OPER)) {
			// 采用流程正常执行的步骤处理,这是原来的工作流程
			return submitWorkFlow_normal(param, flowInstance, curStep);
		}

		throw new WFException("调用参数错误,不支持的operType" + param.getOperType());

	}

	/**
	 * 提交工作流,按照审批通过和审批不通过来计算下一步骤的节点信息<br>
	 * 适用于: 实际审批流程
	 *
	 * @return
	 */
	private WFFlowInstance submitWorkFlow_normal(WFParam param,
			WFFlowInstance flowInstance, WFStepInstance curStep) {
		// Step1: 获取流程实例和当前步骤实例,并判断其数据有效性
		WFStepInstanceManager stepInstanceManager = WFManagerHelper
				.getWFStepInstanceManager();
		WFInstanceRecordManager instanceRecordManager = WFManagerHelper
				.getWFInstanceRecordManager();

		// Step2:检查判断给定的调用参数是否合法,是否可进行此项审批操作
		// TODO:检查用户的可操作性
		// this._checkUserValidataion(curStep, user, flowInstance, operType,
		// operid, toOperid)

		// Step3:计算下一步的流程步骤节点Id,不判断其有效性
		Long nextStepId = this._getNextStepId(flowInstance, param);

		// Step4:写入流程执行记录表中
		WFInstanceRecord instanceRecord = new WFInstanceRecord();

		instanceRecord.setFlowInstanceId(param.getFlowInstanceId());// 流程实例id
		instanceRecord.setStepId(curStep.getId()); // 当前步骤
		instanceRecord.setNextStepId(nextStepId);// 下一步骤
		instanceRecord.setOperType(param.getOperType());// 审批类型
		instanceRecord.setModuleId(flowInstance.getModuleId());// 模块Id
		instanceRecord.setSheetId(flowInstance.getSheetId());// 表单id
		instanceRecord.setOperNo(curStep.getStepNo());// 当前步骤序号
		instanceRecord.setOperTime(new Date());// 当前时间
		instanceRecord.setToOperId(param.getToOperId());// 代操作人id
		instanceRecord.setToOperName(this._getUserNameById(param.getOperId()));// 代操作人名称
		instanceRecord.setIsPassed(param.getIsPassed());// 是否通过
		instanceRecord.setMemo(param.getMemo());// 审批意见
		instanceRecord.setOperId(param.getOperId());// 操作人Id
		instanceRecord.setOperName(this._getUserNameById(param.getToOperId()));// 操作人名称

		// 写入流程执行记录表,同步更新流程实例表
		instanceRecordManager.saveInstanceRecord(instanceRecord);

		// Step5:执行自动抄送功能,发送抄送消息
		//this._autoCopyInfo(curStep, flowInstance);

		// Step6:如果是强制跳转和强制终止,需要删除中间的无效步骤节点数据
		// 删除本次转换的开始步骤与结束步骤之间的中间步骤信息.
		if (param.getOperType().equals(1L) || param.getOperType().equals(2L)) {
			stepInstanceManager.removeMiddleStep(param.getFlowInstanceId(),
					curStep.getId(), nextStepId);
		}

		// Step7.再次刷新数据,返回
		return getFlowInstance(param.getFlowInstanceId());
	}

	// ----------------------工作流自动执行的接口---------------------------//
	// ----------------------待详细测试 -----------------------------------//
	/**
	 * 工作流自动提交,批量执行
	 */
	public boolean submitWFAuto() {
		// TODO:待详细测试
		WFFlowInstanceManager flowInstanceManager = (WFFlowInstanceManager) SpringHelper
				.getBean("WFFlowInstanceManager");

		List<WFFlowInstance> list = flowInstanceManager.getFlowInstanceList();// 取出所有流程实例列表
		for (WFFlowInstance wf : list) {// 遍历流程实例列表
			Set<WFStepInstance> stepList = (Set<WFStepInstance>) wf
					.getStepInstSet();// 取出流程实例对应的步骤实例列表
			Iterator<WFStepInstance> it = stepList.iterator();
			Long stepId = wf.getCurrentStepId();// 获取流程实例当前步骤ID
			while (it.hasNext()) {
				WFStepInstance wfs = it.next();
				if (wfs.getId().equals(stepId)) {// 找到流程实例当前步骤ID对应的步骤实例
					if ((wfs.getIsAutoPass() != null)
							&& (wfs.getIsAutoPass().equals(1L))) {// 当前步骤实例信息适合可自动审批条件，执行自动审批功能
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(new Date());
						long timethis = calendar.getTimeInMillis();
						long theday = (timethis - wf.getLastStepDate()
								.getTime())
								/ (1000 * 60 * 60 * 24);
						if (theday >= wfs.getAutoPassDays()) {
							// if(theday>=0){//测试用此步骤，会将所有流程实例当前步骤为可自动提交的 全部自动通过
							System.out.println("已经找到需要自动提交的流程ID" + wf.getId()
									+ "自动提交的步骤ID" + stepId);

							// instanceManager.submitWF(wf.getId(), null,
							// 99999L,
							// 99999L, wf.getModuleId(), wf.getSheetId(),
							// 1L, "系统定时自动审批", 1L, null);
							// 自动审批改用WFParam来传递参数
							// 这一方法待测试

							WFParam param = new WFParam();
							param.setFlowInstanceId(wf.getId());
							param.setDeptId(null);
							param.setOperId(WFSubmitConst.SUBMIT_SYSTEMID);
							param.setToOperId(WFSubmitConst.SUBMIT_SYSTEMID);
							param.setModuleId(wf.getModuleId());
							param.setSheetId(wf.getSheetId());
							param.setIsPassed(1L);
							param.setMemo("系统定时自动审批");
							param
									.setOperType(WFSubmitOperTypeConst.SYSTEM_OPER);// 系统操作
							param.setSheetName(null);

							// 成功提交工作流.
							submitWorkFlow(param);
						}
					}
				}
			}
		}
		return true;
	}

	// --------------------- 这一部分是待办的功能汇总 -----------------------//

	/**
	 * 按照模块来汇总待办信息
	 */
	public Map<String, Object> getToDoModileList(QueryConditions condition) {
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper
				.getBean(WFInstanceDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
		PageInfo pageInfo = condition.getPageinfo();
		List<ToDoByModule> toDoList = instanceDAO.getToDoByModule(
				(Long) SessionManager.getUserSession().getSessionData().get(
						"userId"), pageInfo);
		// 设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", toDoList);
		return map;
	}

	// --------------------- 这一部分是已办的功能汇总 -----------------------//

	/**
	 * 按照模块来汇总已办信息
	 */
	public Map<String, Object> getDoneModileList(QueryConditions condition) {
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper
				.getBean(WFInstanceDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
		PageInfo pageInfo = condition.getPageinfo();
		List<ToDoByModule> toDoList = instanceDAO.getDoneByModule(
				(Long) SessionManager.getUserSession().getSessionData().get(
						"userId"), pageInfo);
		// 设置query信息
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", toDoList);
		return map;
	}

	// -------------------下面是待办的功能接口--------------------------//
	// -------------------现在已经失效 ---------------------------------//

	/**
	 * 获取流程的当前执行步骤节点,以获取其内部属性值<br>
	 * 这一功能为工作流审批组件提供功能支持
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	public WFStepInstance getCurrentStepInstance(Long flowInstanceId) {
		WFFlowInstanceManager instanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFFlowInstance flowInstance = instanceManager
				.queryFlowInstance(flowInstanceId);

		if (flowInstance == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId无效."
					+ flowInstanceId);
		}
		if (flowInstance.getCurrentStepId() == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId的当前步骤节点为空."
					+ flowInstanceId);
		}

		WFStepInstanceManager stepInstManager = WFManagerHelper
				.getWFStepInstanceManager();
		WFStepInstance stepInstance = stepInstManager
				.readStepInstance(flowInstance.getCurrentStepId());
		if (stepInstance == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId的当前步骤节点不存在,数据库错误."
					+ flowInstanceId);
		}
		return stepInstance;
	}

	/**
	 * 获取当前流程实例的所有下一步节点列表<br>
	 * 如果当前节点配置为可选择下一步,则检索所有下一步节点<br>
	 * 否则直接返回一个new ArrayList();<br>
	 * 这一功能为工作流审批组件来提供支持<br>
	 */
	public List<WFStepInstance> getNextStepList(Long flowInstanceId) {
		// Step1:检索流程实例信息
		WFFlowInstanceManager instanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFFlowInstance flowInstance = instanceManager
				.queryFlowInstance(flowInstanceId);

		if (flowInstance == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId无效."
					+ flowInstanceId);
		}
		if (flowInstance.getCurrentStepId() == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId的当前步骤节点为空."
					+ flowInstanceId);
		}

		// Step2: 检索流程实例当前步骤信息
		WFStepInstanceManager stepInstManager = WFManagerHelper
				.getWFStepInstanceManager();
		WFStepInstance stepInstance = stepInstManager
				.readStepInstance(flowInstance.getCurrentStepId());
		if (stepInstance == null) {
			throw new WFException("调用参数错误.指定的flowInstanceId的当前步骤节点不存在,数据库错误."
					+ flowInstanceId);
		}

		// Step3: 从流程实例里面来检索当前步骤的后续步骤信息
		List<WFStepInstance> retList = stepInstManager.getNextStepList(
				flowInstanceId, flowInstance.getCurrentStepId());
		// Step4:返回下一步骤列表
		return retList;
	}

	// --------------------私有功能方法定义--------------------------//
	/**
	 * 检查某个变量是否为空，如果是null，则抛出异常信息
	 * 
	 * @param inVal
	 * @param errorInfo
	 */
	private void _checkNullValue(Long inVal, String errorInfo) {
		if (inVal == null) {
			throw new WFException(errorInfo);
		}
	}

	private void _checkNullValue(String inVal, String errorInfo) {
		if (inVal == null) {
			throw new WFException(errorInfo);
		}
	}

	/**
	 * 检查某个流程实例是否为空
	 * 
	 * @param inVal
	 * @param errorInfo
	 */
	private void _checkNullValue(WFFlowInstance inVal, String errorInfo) {
		if (inVal == null) {
			throw new WFException(errorInfo);
		}
	}

	/**
	 * 检查某个流程实例是否为空
	 * 
	 * @param inVal
	 * @param errorInfo
	 */
	private void _checkNullValue(WFStepInstance inVal, String errorInfo) {
		if (inVal == null) {
			throw new WFException(errorInfo);
		}
	}

	/**
	 * 根据当前步骤的Id,检索正常情况下的下一步节点的Id<br>
	 * 用于在正常审批时,计算下一步节点使用
	 * 
	 * @param stepId
	 * @return
	 */
	private Long _getNextStepId_Normal(Long stepId) {
		WFInstanceTransitionManager transitionInstanceManager = WFManagerHelper
				.getWFInstanceTransitionManager();
		WFInstanceTransition tranInst = transitionInstanceManager
				.getTranInstance(stepId);
		if (null != tranInst) {
			// 写入执行记录表
			return tranInst.getEndId();
		} else {
			throw new WFException("获取正常流转情况下的下一步节点出错.没有可用转换定义.:stepId" + stepId);
		}
	}

	/**
	 * 根据传入的流程转换条件,计算下一步的节点的Id
	 * 
	 * @param flowInstance
	 *            流程实例对象,这个实例不可为null
	 * @param param
	 *            工作流调用参数对象,只读
	 * @return
	 */
	private Long _getNextStepId(WFFlowInstance flowInstance, WFParam param) {
		// Step0: 在调用之前,应当确保各项参数的有效性
		WFStepInstanceManager stepInstManager = WFManagerHelper
				.getWFStepInstanceManager();

		// 根据param来计算下一步步骤节点
		// Step1:审批不通过,退回的情况
		if (param.getIsPassed().equals(0L)) {
			WFStepInstance beginStep = stepInstManager.getBeginStep(param
					.getFlowInstanceId());
			// 检查返回值
			this._checkNullValue(beginStep, "审批退回的情况下，获取开始节点为空!flowInstanceId:"
					+ param.getFlowInstanceId());
			return beginStep.getId();
		} else if (param.getIsPassed().equals(1L)) {
			// 审批通过的情况
			if (param.getOperType().equals(0L)
					|| param.getOperType().equals(4L)) {
				// 正常审批通过的情况
				return this._getNextStepId_Normal(flowInstance
						.getCurrentStepId());
			} else if (param.getOperType().equals(1L)) {
				// 强制跳转的情况
				return param.getNextStepId();
			} else if (param.getOperType().equals(2L)) {
				// 强制终止的情况
				WFStepInstance endStep = stepInstManager.getEndStep(param
						.getFlowInstanceId());
				// 检查返回值
				this._checkNullValue(endStep,
						"强制终止的情况下，获取结束节点为空!flowInstanceId:"
								+ param.getFlowInstanceId());
				return endStep.getId();
			}
		}
		// 参数错误的情况下,直接抛出一个计算异常
		throw new WFException("获取下一步节点失败.flowInstanceId,isPassed=:"
				+ param.getFlowInstanceId() + "," + param.getIsPassed());
	}

	/**
	 * 检查当前用户是否具有实际的审批权限,避免越权调用,检查不通过会抛出异常
	 */
	private void _checkUserValidataion(WFStepInstance curStep, User user,
			WFFlowInstance flowInstance, Long opertype, Long operid,
			Long toOperid) {
		// 检验是否该当前审批用户审批，若不是则疑为修改url方式非法请求
		// TODO:此处待修改,需要从待办视图中判断
		boolean urlflag = false;
		List<WFInstanceStepPos> stepPosList = new ArrayList<WFInstanceStepPos>(
				curStep.getStepInstToPosSet());

		// 按照岗位来判断
//		for (WFInstanceStepPos stepPos : stepPosList) {
//			if (stepPos.getPosId().equals(user.getPk_position())) {
//				System.out.println(stepPos.getPosId().equals(
//						user.getPk_position()));
//				urlflag = true;
//			}
//		}
		// 按照operid来判断,如果是委托，则默认可以执行
		if (flowInstance.getToDoOper() != null
				&& flowInstance.getToDoOper().equals(operid)) {
			urlflag = true;
		}
		// 如果是代理操作,则不做控制限制
		if (operid != null && toOperid != null && !operid.equals(toOperid)) {
			urlflag = true;
		}

		// OperType=2代表强制终止流程.
		if (opertype != null && opertype == 2L) {
			urlflag = true;
		}
		if (!urlflag) {
			throw new WFException("用户ID为" + operid + "的用户无权审批当前步骤，抛出异常");
		}
	}

	/**
	 * 自动生成相关的抄送消息,此功能待测试
	
	private void _autoCopyInfo(WFStepInstance curStep,
			WFFlowInstance flowInstance) {
		// 自动抄送待办到人员或者某岗位的的所有人员
		WFCopyInfoManager copyInfoManager = (WFCopyInfoManager) SpringHelper
				.getBean("WFCopyInfoManager");
		WFInsPersonCopyManager personCopyManager = (WFInsPersonCopyManager) SpringHelper
				.getBean("WFInsPersonCopyManager");
		UserManager userManager = (UserManager) SpringHelper
				.getBean("userManager");
		List<WFInsPersonCopy> personCopyList = personCopyManager
				.getListByInsStepId(curStep.getId());
		// 抄送到人员
		if (personCopyList != null && personCopyList.size() > 0) {
			for (WFInsPersonCopy personCopy : personCopyList) {
				// 需要增加是否是同一所的判断
				if (personCopy.getIsSameOrg() != null
						|| personCopy.getIsSameOrg().equals(1L)) {
					// 获取用户所在所Id
					Long orgid = this._queryOrgIdByUserId(personCopy
							.getReceiverId());
					if (orgid == null) {
						// 非所内用户,不抄送
						continue;
					}
					if (!orgid.equals(flowInstance.getOrgId())) {
						// 抄送用户所在所和流程启动所Id不一致,不抄送
						continue;
					}
				}
				WFCopyInfo copyInfo = new WFCopyInfo();
				copyInfo.setFlowInstanceId(flowInstance.getId());
				copyInfo.setModuleId(flowInstance.getModuleId());
				copyInfo.setSheetId(flowInstance.getSheetId());
				copyInfo.setState(0L);
				copyInfo.setReceiverId(personCopy.getReceiverId());
				// 插入一条抄送记录
				copyInfoManager.addWFCopyInfo(copyInfo);
			}
		}
		WFInsPositionCopyManager positionCopyManager = (WFInsPositionCopyManager) SpringHelper
				.getBean("WFInsPositionCopyManager");
		List<WFInsPositionCopy> positionCopyList = positionCopyManager
				.getListByInsStepId(curStep.getId());
		// 抄送到岗位上所有人员
		if (positionCopyList != null && positionCopyList.size() > 0) {
			for (WFInsPositionCopy positionCopy : positionCopyList) {
				List<User> userList = null;
				if (positionCopy.getIsSameOrg().equals(new Long(1L))) {
					// 获取岗位的所有人员
					userList = userManager.getUsersByOrgAndPosition(
							flowInstance.getOrgId(), positionCopy
									.getPositionId());
				} else {
					// 获取不限制所的按岗位选择人员
					userList = userManager.getUserListByPosition(null,
							positionCopy.getPositionId());
				}
				if (userList != null && userList.size() > 0) {
					for (User us : userList) {
						WFCopyInfo copyInfo = new WFCopyInfo();
						copyInfo.setFlowInstanceId(flowInstance.getId());
						copyInfo.setModuleId(flowInstance.getModuleId());
						copyInfo.setSheetId(flowInstance.getSheetId());
						copyInfo.setState(0L);
						copyInfo.setReceiverId(us.getId());
						copyInfoManager.addWFCopyInfo(copyInfo);
					}
				}
			}
		}
	}
 */
	/**
	 * 根据Id来获取用户名称,如果获取不到,则抛出异常<br>
	 * 但系统管理员除外.
	 * 
	 * @param userid
	 *            用户Id号
	 */
	private String _getUserNameById(Long userid) {
		UserManager userManager = (UserManager) SpringHelper
				.getBean("userManager");

		if (userid.equals(99999L) || userid.equals(9999L)) {
			return "系统管理员";
		}
		User user = userManager.getUserEntity(userid);
		if (user == null) {
			throw new WFException("用户不存在!userid=" + userid);
		}
		return user.getName();
	}

	// --------------下面是增加的和流程抄送相关的功能接口 -------------------------//

	/**
	 * 当一个流程审批通过时,按照给定的抄送人,发送流程抄送信息
	 * 
	 * @param param
	 *            返回1成功 返回0失败
	 */
	public Long addCopyInfo(WFCopyParam param) {
		// Step1:判断数据的有效性
		_checkNullValue(param.getFlowInstanceId(), "抄送参数错误,flowInstanceId不可为空!");
		// _checkNullValue(param.getModuleId(), "抄送参数错误,moduleId不可为空!");
		_checkNullValue(param.getSendId(), "抄送参数错误,sendId不可为空!");
		_checkNullValue(param.getReceiverIds(), "抄送参数错误,receiverIds不可为空!");
		_checkNullValue(param.getReceiverNames(), "抄送参数错误,receiverNames不可为空!");
		// _checkNullValue(param.getSheetId(), "抄送参数错误,sheetId不可为空!");
		// _checkNullValue(param.getSheetName(), "抄送参数错误,sheetName不可为空!");

		WFFlowInstanceManager instManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFFlowInstance inst = instManager.queryFlowInstance(param
				.getFlowInstanceId());

		_checkNullValue(inst, "抄送参数错误,给定的流程实例不存在!" + param.getFlowInstanceId());
		param.setModuleId(inst.getModuleId());
		param.setSheetId(param.getSheetId());
		param.setSheetName(param.getSheetName());

		WFCopyInfoManager manager = WFManagerHelper.getWFCopyInfoManager();

		String ids[] = param.getReceiverIds().split(","); // 按照逗号进行分割
		if (ids.length == 0) {
			throw new WFException("抄送参数错误,receiverIds无数据!");
		}
		for (String id : ids) {
			WFCopyInfo copyInfo = new WFCopyInfo(); // 生成一条新记录
			if (id.length() > 0) {

				copyInfo.setFlowInstanceId(param.getFlowInstanceId());
				copyInfo.setModuleId(param.getModuleId());
				copyInfo.setSendId(param.getSendId());
				copyInfo.setReceiverId(Long.parseLong(id));
				copyInfo.setSheetId(param.getSheetId());
				copyInfo.setMemo(param.getSheetName());
				copyInfo.setState(0L);
				copyInfo.setReceiverId(Long.parseLong(id));
				manager.addWFCopyInfo(copyInfo);
			}
		}

		// 下一步: 将抄送人的信息写入到流程执行记录表中去
		if (param.getReceiverNames() != null
				&& param.getReceiverNames().length() > 0) {
			WFInstanceRecordManager instRec = WFManagerHelper
					.getWFInstanceRecordManager();
			instRec.setCopyPerson(param.getFlowInstanceId(), param
					.getReceiverNames());
		}
		return 1L;
	}

	/**
	 * 设置某一条流程抄送消息为已读,使用flowInstanceId和receiverIds两个字段 调用者：前台界面
	 * 
	 * @param param
	 * @return 1成功0失败
	 */
	public Long readCopyInfo(WFCopyParam param) {
		_checkNullValue(param.getFlowInstanceId(), "抄送参数错误,flowInstanceId不可为空!");
		// _checkNullValue(param.getModuleId(), "抄送参数错误,moduleId不可为空!");
		_checkNullValue(param.getReceiverIds(), "抄送参数错误,receiverIds不可为空!");

		WFCopyInfoManager manager = WFManagerHelper.getWFCopyInfoManager();
		Long receiverId = Long.parseLong(param.getReceiverIds());
		List<WFCopyInfo> unreadList = manager.getUserCopyInfo(receiverId, param
				.getFlowInstanceId());
		for (WFCopyInfo cinfo : unreadList) {
			cinfo.setState(1L);
			manager.saveObject(cinfo);
		}
		return 1L;
	}

	/**
	 * 根据用户Id来检索对应所的Id号
	 * 
	 * @param id
	 *            用户号
	 * @return 所Id,可能为null或者0L
	
	private Long _queryOrgIdByUserId(Long id) {
		RewardsUserAndOrgManager manager = (RewardsUserAndOrgManager) SpringHelper
				.getBean("rewardsUserAndOrgManager");
		// TODO Auto-generated method stub
		// RewardsUserAndOrg uao = (RewardsUserAndOrg) super.getObject(id);
		// return uao.getId3();
		System.out.println(id);
		return manager.queryOrgIdByUserId(id);
	} */

	/**
	 * 手工设置当前流程执行实例的待操作人字段
	 * 
	 * @param flowInstanceId
	 * @param tooperId
	 * @return
	 */
	public WFFlowInstance setToOperId(Long flowInstanceId, Long tooperId) {
		_checkNullValue(tooperId,"给定的待操作人Id不可为空!");
		//检索数据
		WFFlowInstanceManager instanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		WFFlowInstance flowInstance = instanceManager
				.queryFlowInstance(flowInstanceId);
		
		_checkNullValue(flowInstance,"给定的流程实例Id不存在!"+flowInstanceId);
		//手工设置待操作人
		flowInstance.setToDoOper(tooperId);
		return instanceManager.saveFlowInstance(flowInstance);
		
	}
}
