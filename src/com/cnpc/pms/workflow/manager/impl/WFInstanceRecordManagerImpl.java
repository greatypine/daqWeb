package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;
import com.cnpc.pms.workflow.manager.WFStepInstanceManager;
import com.cnpc.pms.workflow.util.WFStepTypeConst;

public class WFInstanceRecordManagerImpl extends BaseManagerImpl implements
		WFInstanceRecordManager {
	/**
	 * 保存一条流程执行记录<br>
	 * 同时要更新对应的流程实例的状态信息<br>
	 */
	public WFInstanceRecord saveInstanceRecord(WFInstanceRecord instRecord) {
		// Step1:保存流程执行记录
		this.saveObject(instRecord);

		// Step2:根据流程执行记录信息,来同步流程实例的当前状态数据
		// Step2.1 检索当前执行节点,检索下一步节点
		WFFlowInstanceManager flowInstanceManager = (WFFlowInstanceManager) SpringHelper
				.getBean("WFFlowInstanceManager");
		WFFlowInstance flowInstance = flowInstanceManager
				.queryFlowInstance(instRecord.getFlowInstanceId());

		_checkNullValue(flowInstance, "指定的flowInstanceId不存在:"
				+ instRecord.getFlowInstanceId());

		// 当前记录关联步骤的类型
		WFStepInstanceManager stepInstanceManager = (WFStepInstanceManager) SpringHelper
				.getBean("WFStepInstanceManager");
		WFStepInstance nextStep = (WFStepInstance) stepInstanceManager
				.getObject(instRecord.getNextStepId());
		WFStepInstance curStep = (WFStepInstance) stepInstanceManager
				.getObject(instRecord.getStepId());

		_checkNullValue(curStep, "指定的当前步骤不存在:" + instRecord.getStepId());
		_checkNullValue(nextStep, "指定的下一步步骤不存在:" + instRecord.getNextStepId());

		// Step2.2 公用更新方法
		// 设置设置上次执行时间
		flowInstance.setLastStepDate(instRecord.getOperTime());
		// 设置下一步流程步骤
		flowInstance.setCurrentStepId(nextStep.getId());

		// Step2.3
		// 根据nextStep来更新流程实例的状态字段
		// state 0 正常 1终止
		// 步骤状态: 0 开始节点 1 执行节点 2 结束节点
		if (nextStep.getStepType().equals(WFStepTypeConst.END)) {
			// 流程终止
			flowInstance.setState(new Long(1));
		} else {
			// 流程执行中
			flowInstance.setState(new Long(0));
		}

		// Step2.4
		// 根据nextStep来更新流程实例的toDoOper字段
		// 如果nextStep是开始节点类型,代表流程是退回,那么就设置
		// toDoOper为流程发起人
		// 否则设置toDoOper字段为null
		// add by liujunsong
		// 2013-10-11
		// 这段代码逻辑从原来的WFInstanceManagerImpl迁移过来
		if (nextStep.getStepType().equals(WFStepTypeConst.BEGIN)) {
			// 设置待操作人
			flowInstance.setToDoOper(flowInstance.getPersonId());
			// 重新设置流程启动时间
			flowInstance.setStartDate(instRecord.getOperTime());
		} else {
			flowInstance.setToDoOper(null);
		}

		// Step2.5同步更新flowInstance的数据
		flowInstanceManager.saveFlowInstance(flowInstance);
		return instRecord;
	}

	@SuppressWarnings("unchecked")
	public List<WFInstanceRecord> getDoList(Long personId, Long moduleId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("operId=" + personId
				+ " and moduleId=" + moduleId));
		fsp.setSort(new Sort("operTime", Sort.DESC));
		List<WFInstanceRecord> doList = (List<WFInstanceRecord>) this
				.getObjects(fsp);
		return doList;
	}

	// @SuppressWarnings("unchecked")
	// public List<WFInstanceRecord> getInstanceRecord(Long flowInstanceId) {
	// FSP fsp = new FSP();
	// fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId="
	// + flowInstanceId));
	// List<WFInstanceRecord> recordList = (List<WFInstanceRecord>) this
	// .getObjects(fsp);
	// return recordList;
	// }

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
	 * 检查某个步骤实例是否为空
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
	 * 根据流程实例Id,来设置最后执行记录的抄送人信息
	 * 
	 * @param flowInstanceId
	 *            流程实例Id
	 * @param personName
	 *            抄送人名称
	 * @return 无
	 */
	public void setCopyPerson(Long flowInstanceId, String personNames) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId="
				+ flowInstanceId));
		fsp.setSort(new Sort("id", Sort.DESC));
		//获取最新一条instanceRecord记录,更新其抄送者信息
		List<WFInstanceRecord> instList = (List<WFInstanceRecord>) this
				.getObjects(fsp);
		if (instList.size() > 0) {
			WFInstanceRecord rec = instList.get(0);
			rec.setCopyPeronNames(personNames);
			this.saveObject(rec);
		}
	}
	
	/**
	 * 插入一条新的流程执行记录,不更新对应的流程记录表
	 * 
	 * @param instRecord
	 * @return
	 */
	public WFInstanceRecord saveInstanceRecordOnly(WFInstanceRecord instRecord){
		super.save(instRecord);
		return instRecord;
	}
}
