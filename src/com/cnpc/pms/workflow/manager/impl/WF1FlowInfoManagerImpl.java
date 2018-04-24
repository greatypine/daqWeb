package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.dto.WF1FlowInfoParam;
import com.cnpc.pms.workflow.entity.WF1FlowInfo;
import com.cnpc.pms.workflow.entity.WF1FlowMapping;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WF1FlowInfoManager;
import com.cnpc.pms.workflow.manager.WF1FlowMappingManager;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;
import com.cnpc.pms.workflow.util.WFManagerHelper;

public class WF1FlowInfoManagerImpl extends BaseManagerImpl implements
		WF1FlowInfoManager {
	/**
	 * 检索对应的流程实例Id
	 * 
	 * @param rTableName
	 * @param rId
	 * @return 对应的流程实例,但可能为空值
	 */
	public WFFlowInstance queryFlowInstance(String rTableName, Long rId) {
		// Step1:获取对应表数据
		WF1FlowMappingManager wf1flow = (WF1FlowMappingManager) SpringHelper
				.getBean("WF1FlowMappingManager");
		WF1FlowMapping flowmapping = wf1flow.getWF1FlowMapping(rTableName, rId);

		// 无对应数据,返回null
		if (flowmapping == null) {
			return null;

		} else {
			// 检索流程实例的真实对象,返回对象
			// 如果不存在,则会返回null
			WFFlowInstanceManager flowInstManager = WFManagerHelper
					.getWFFlowInstanceManager();
			WFFlowInstance flowInstance = flowInstManager.getFlowInstance(
					flowmapping.getModuleId(), flowmapping.getSheetId());
			return flowInstance;
		}
	}

	/**
	 * 删除现有流程实例
	 * 
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public Long removeInstance(String rTableName, Long rId) {
		// Step1:获取流程实例对象
		WFFlowInstance wFFlowInstance = this.queryFlowInstance(rTableName, rId);
		_checkNull(wFFlowInstance, "流程实例不存在,不可删除");

		// Step2:获取对应表数据
		WF1FlowMappingManager wf1flow = (WF1FlowMappingManager) SpringHelper
				.getBean("WF1FlowMappingManager");
		WF1FlowMapping flowmapping = wf1flow.getWF1FlowMapping(rTableName, rId);

		WFFlowInstanceManager flowInstManager = WFManagerHelper
				.getWFFlowInstanceManager();
		// Step3:级联删除流程审批记录
		flowInstManager.deleteFlowInstance(wFFlowInstance);

		// Step4:更新flowinstanceId字段为空
		flowmapping.setFlowInstanceId(null);
		wf1flow.saveWF1FlowMapping(flowmapping);
		return 0L;
	}

	/**
	 * 生成流程实例数据
	 * 
	 * @param rTableName
	 *            数据表名
	 * @param rId
	 *            记录Id
	 * @return
	 */
	private Long createInstance(String rTableName, Long rId) {
		// Step1:获取现有流程实例
		WFFlowInstance wFFlowInstance = this.queryFlowInstance(rTableName, rId);

		if (wFFlowInstance != null) {
			throw new WFException("流程实例已存在,不可重复生成流程实例.rTableName,rId="
					+ rTableName + "," + rId);
		}

		// Step2:获取对应表数据
		WF1FlowMappingManager wf1flow = (WF1FlowMappingManager) SpringHelper
				.getBean("WF1FlowMappingManager");
		WF1FlowMapping flowmapping = wf1flow.getWF1FlowMapping(rTableName, rId);

		_checkNull(flowmapping, "生成流程实例失败,对应关系不存在!请检查WF1_FlowMapping表。"
				+ "rTableName,rId=" + rTableName + "," + rId);

		// Step3: 生成流程实例记录,插入数据库之中
		WFFlowInstance flowInstance = _convertToFlowInstance(flowmapping,
				rTableName, rId);
		WFFlowInstanceManager flowinstManager = WFManagerHelper
				.getWFFlowInstanceManager();
		flowinstManager.addFlowInstance(flowInstance);

		// Step4: 检索一期的流程执行历史记录
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("rTableName",
				rTableName)
				.appendAnd(FilterFactory.getSimpleFilter("rId", rId)));

		fsp.setSort(new Sort("id", Sort.ASC)); // 按照创建日期来排序

		// Step5:检索数据,生成列表
		List<WF1FlowInfo> flowInfoList = (List<WF1FlowInfo>) super.getList(fsp);

		// 生成列表数据
		List<WFStepInstance> stepList = _convertToStepList(flowInfoList,
				rTableName, rId, flowInstance, flowmapping);

		// Step6:更新flowinstanceId字段
		flowmapping.setFlowInstanceId(flowInstance.getId());
		wf1flow.saveWF1FlowMapping(flowmapping);

		WFStepInstance stopStep = stepList.get(stepList.size() - 1); // 最后一个节点
		WF1FlowInfo startFlowInfo = flowInfoList.get(0); // 开始流程
		WF1FlowInfo stopFlowInfo = flowInfoList.get(flowInfoList.size() - 1); // 最后一条流程
		if (stopStep.getStepType() == null
				&& (!stopStep.getStepType().equals(2L))) {
			throw new WFException("程序错误，计算结束节点出错!" + "rTableName,rId="
					+ rTableName + "," + rId);
		}

		// Step7:重新检索一下流程实例,更新流程实例的其他信息项目
		WFFlowInstanceManager flowInstanceMananger = WFManagerHelper
				.getWFFlowInstanceManager();
		flowInstance = flowInstanceMananger.queryFlowInstance(flowInstance
				.getId());
		flowInstance.setState(1L); // 设置流程已经结束
		flowInstance.setCurrentStepId(stopStep.getId()); // 指向结束节点
		flowInstance.setStartDate(startFlowInfo.getCheckDate()); // 流程启动日期
		flowInstance.setLastStepDate(stopFlowInfo.getCheckDate()); // 流程结束日期
		flowInstanceMananger.saveFlowInstance(flowInstance); // 保存流程实例对象

		return 0L;
	}

	// --- 下面是私有方法,用来判断是否是Null值 ---//
	private void _checkNull(Long lvalue, String errorInfo) {
		if (lvalue == null) {
			throw new WFException(errorInfo);
		}
	}

	private void _checkNullOrZero(Long lvalue, String errorInfo) {
		if (lvalue == null || lvalue.equals(0L)) {
			throw new WFException(errorInfo);
		}
	}

	private void _checkNull(String svalue, String errorInfo) {
		if (svalue == null) {
			throw new WFException(errorInfo);
		}
	}

	private void _checkNull(WF1FlowMapping mapping, String errorInfo) {
		if (mapping == null) {
			throw new WFException(errorInfo);
		}
	}

	private void _checkNull(WFFlowInstance flowInstance, String errorInfo) {
		if (flowInstance == null) {
			throw new WFException(errorInfo);
		}
	}

	/**
	 * 将对应关系对象转换为流程实例对象
	 * 
	 * @param flowmapping
	 * @return
	 */
	private WFFlowInstance _convertToFlowInstance(WF1FlowMapping flowmapping,
			String rTableName, Long rId) {
		// Step1:检查数据的完整性
		_checkNullOrZero(flowmapping.getModuleId(),
				"生成流程实例失败,moduleId不可为空或零!rTableName,rId=" + rTableName + ","
						+ rId);
		_checkNullOrZero(flowmapping.getSheetId(),
				"生成流程实例失败,sheetId不可为空或零!rTableName,rId=" + rTableName + ","
						+ rId);
		_checkNullOrZero(flowmapping.getDeptId(),
				"生成流程实例失败,deptId不可为空或零!rTableName,rId=" + rTableName + ","
						+ rId);

		WFFlowInstance flowInstance = new WFFlowInstance();

		flowInstance.setFlowId(0L); // 无对应的流程Id,从一期迁移过来
		flowInstance.setOrgId(flowmapping.getDeptId()); // 启动部门Id
		flowInstance.setModuleId(flowmapping.getModuleId());// 对应模块Id
		flowInstance.setSheetId(flowmapping.getSheetId()); // 对应SheetId
		flowInstance.setSheetName(flowmapping.getSheetName());// 对应SheetName
		flowInstance.setState(1L); // 默认为终止状态
		flowInstance.setToDoOper(null); // 设置待操作人为空值
		// 其余字段需要根据流程的执行记录来进行设置

		return flowInstance;
	}

	/**
	 * 根据输入的执行历史,生成流程实例的步骤定义,写入数据库,并返回列表
	 * 
	 * @param list1
	 * @return
	 */
	private List<WFStepInstance> _convertToStepList(List<WF1FlowInfo> list1,
			String rTableName, Long rId, WFFlowInstance flowInstance,
			WF1FlowMapping flowmapping) {

		WFStepInstanceManager stepInstManager = WFManagerHelper
				.getWFStepInstanceManager();
		WFInstanceRecordManager recManager = WFManagerHelper
				.getWFInstanceRecordManager();
		WFFlowInstanceManager flowinstanceManager = WFManagerHelper
				.getWFFlowInstanceManager();
		List<WFStepInstance> retList = new ArrayList<WFStepInstance>();
		// 判断流程执行记录是否为空
		if (list1 == null || list1.size() == 0) {
			//删除多余的流程实例对象
			//这是由于没有正确进行数据回滚造成的错误.
			flowinstanceManager.deleteFlowInstance(flowInstance);
			throw new WFException("生成流程步骤失败,原始流程执行记录不存在!" + "rTableName,rId="
					+ rTableName + "," + rId);
		}
		// 判断流程是否已经执行结束
		WF1FlowInfo endInfo = list1.get(list1.size() - 1);
		if (endInfo.getIsDone() != null && endInfo.getIsDone().equals(0L)) {
			//删除多余的流程实例对象
			//这是由于没有正确进行数据回滚造成的错误.
			flowinstanceManager.deleteFlowInstance(flowInstance);
			throw new WFException("生成流程步骤失败,原始流程尚未执行完毕!" + "rTableName,rId="
					+ rTableName + "," + rId);
		}
		if (endInfo.getIsPass() != null && endInfo.getIsPass().equals(0L)) {
			//删除多余的流程实例对象
			//这是由于没有正确进行数据回滚造成的错误.
			flowinstanceManager.deleteFlowInstance(flowInstance);
			throw new WFException("生成流程步骤失败,原始流程尚未审批通过!" + "rTableName,rId="
					+ rTableName + "," + rId);
		}
		WF1FlowInfo beginInfo = list1.get(0);

		// 开始生成流程步骤
		WF1FlowInfo prevInfo = null;
		for (WF1FlowInfo flowinfo : list1) {
			WFStepInstance stepInstance = new WFStepInstance();
			if (flowinfo.equals(beginInfo)
					|| (flowinfo.getIsReturnStep() != null && flowinfo
							.getIsReturnStep().equals(1L))) {
				stepInstance.setStepType(0L); // 开始节点,第一个节点是开始节点,所有的返回节点也是开始节点
			} else {
				stepInstance.setStepType(1L); // 执行节点
			}
			String stepname = flowinfo.getStepDes() == null ? "" : flowinfo
					.getStepDes().trim();
			if (stepname.equals("") && flowinfo.equals(beginInfo)) {
				stepname = "表单填报";
			}
			stepInstance.setStepName(stepname); // 获取步骤名称,默认开始点为"表单填报"
			stepInstance.setFlowInstanceId(flowInstance.getId()); // 所属流程实例Id

			// 下面是自动生成的默认值
			stepInstance.setIsSameOrg(0L);// 默认非本部门
			stepInstance.setIsReturn(0L);// 是否可退回
			stepInstance.setIsFinish(0L);// 是否可终止
			stepInstance.setIsChoiseNext(0L);// 选择后续
			stepInstance.setIsChoisePrev(0L);// 选择前续
			stepInstance.setIsAllowEdit(0L);// 是否可编辑
			stepInstance.setIsPrompt(0L); // 是否催办
			stepInstance.setIsAutoPass(0L);// 是否自动提交
			stepInstance.setAutoPassDays(0L);// 自动提交天数
			stepInstance.setIsAutoSave(0L); // 自动保存
			stepInstance.setPromptDays(0L); // 自动提示天数
			stepInstance.setRemoteJava(""); // Java
			stepInstance.setRemoteArgs(""); // Args
			stepInstance.setIsDel(0L); // 未删除

			// 插入这条步骤实例数据
			stepInstManager.addStepInstance(stepInstance);

			if (flowinfo.equals(beginInfo)) {
				// Donothing
				// 开始点的数据转换是一个垃圾数据
			} else {
				// 如果不是开始点,则写入一条执行记录到执行表中去
				// 利用上一条记录来写入

				WFInstanceRecord instRec = new WFInstanceRecord();
				instRec.setFlowInstanceId(flowInstance.getId()); // 流程实例id
				instRec.setOperType(0L);
				instRec.setModuleId(flowmapping.getModuleId());
				instRec.setSheetId(flowmapping.getSheetId());

				// instRec.setToOperId(flowinfo.getFromEmpId());
				// instRec.setOperId(flowinfo.getFromEmpId());
				// instRec.setToOperName(flowinfo.getFromEmpName());
				// instRec.setOperName(flowinfo.getFromEmpName());
				instRec.setOperId(prevInfo.getToEmpId());
				instRec.setToOperId(prevInfo.getToEmpId());
				instRec.setToOperName(prevInfo.getToEmpName());
				instRec.setOperName(prevInfo.getToEmpName());

				// 操作描述
				instRec.setMemo(prevInfo.getOptionDes());

				instRec.setStepId((retList.get(retList.size() - 1)).getId());
				instRec.setNextStepId(stepInstance.getId());

				instRec.setOperTime(prevInfo.getCheckDate()); // 完成时间
				instRec.setIsPassed(prevInfo.getIsPass()); // 是否通过
				instRec.setPassType(0L);
				instRec.setReturnType(0L);

				recManager.saveInstanceRecordOnly(instRec);
			}
			retList.add(stepInstance);
			prevInfo = flowinfo;
		}// 循环结束

		// 插入一个结束节点
		WFStepInstance stepInstance = new WFStepInstance();
		stepInstance.setStepType(2L);
		stepInstance.setStepName("结束"); // 获取步骤名称
		stepInstance.setFlowInstanceId(flowInstance.getId()); // 所属流程实例Id
		stepInstance.setIsSameOrg(0L);// 默认非本部门
		stepInstance.setIsReturn(0L);// 是否可退回
		stepInstance.setIsFinish(0L);// 是否可终止
		stepInstance.setIsChoiseNext(0L);// 选择后续
		stepInstance.setIsChoisePrev(0L);// 选择前续
		stepInstance.setIsAllowEdit(0L);// 是否可编辑
		stepInstance.setIsPrompt(0L); // 是否催办
		stepInstance.setIsAutoPass(0L);// 是否自动提交
		stepInstance.setAutoPassDays(0L);// 自动提交天数
		stepInstance.setIsAutoSave(0L); // 自动保存
		stepInstance.setPromptDays(0L); // 自动提示天数
		stepInstance.setRemoteJava(""); // Java
		stepInstance.setRemoteArgs(""); // Args
		stepInstance.setIsDel(0L); // 未删除
		// 插入这条步骤实例数据
		stepInstManager.addStepInstance(stepInstance);

		// 插入一条结束转换数据

		WFInstanceRecord instRec = new WFInstanceRecord();
		instRec.setFlowInstanceId(flowInstance.getId()); // 流程实例id
		instRec.setOperType(0L);
		instRec.setModuleId(flowmapping.getModuleId()); // ModuleId
		instRec.setSheetId(flowmapping.getSheetId()); // SheetId
		instRec.setOperTime(prevInfo.getCheckDate()); // 完成时间

		instRec.setToOperId(prevInfo.getToEmpId()); // 操作人Id
		instRec.setToOperName(prevInfo.getToEmpName()); // 操作人姓名
		instRec.setOperId(prevInfo.getToEmpId()); // 待操作人Id
		instRec.setOperName(prevInfo.getToEmpName()); // 待操作人姓名

		instRec.setMemo(prevInfo.getOptionDes()); // 操作描述
		instRec.setStepId((retList.get(retList.size() - 1)).getId()); // 当前步骤
		instRec.setNextStepId(stepInstance.getId()); // 下一步骤
		instRec.setIsPassed(prevInfo.getIsPass()); // 是否通过
		instRec.setPassType(0L); // 通过类型,暂无含义
		instRec.setReturnType(0L); // 不通过类型,暂无含义

		recManager.saveInstanceRecordOnly(instRec);

		retList.add(stepInstance);
		return retList;
	}

	/**
	 * 生成流程实例数据
	 * 
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public Long createInstance(WF1FlowInfoParam param) {
		return createInstance(param.getrTableName(), param.getrId());
	}

	/**
	 * 删除现有流程实例
	 * 
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public Long removeInstance(WF1FlowInfoParam param) {
		return removeInstance(param.getrTableName(), param.getrId());
	}
}
