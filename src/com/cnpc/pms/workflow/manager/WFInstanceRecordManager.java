package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;

/**
 * 流程执行记录对应维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFInstanceRecordManager extends IManager {
	/**
	 * 插入一条新的流程执行记录
	 * 
	 * @param instRecord
	 * @return
	 */
	public WFInstanceRecord saveInstanceRecord(WFInstanceRecord instRecord);
	
	/**
	 * 插入一条新的流程执行记录,不更新对应的流程记录表
	 * 
	 * @param instRecord
	 * @return
	 */
	public WFInstanceRecord saveInstanceRecordOnly(WFInstanceRecord instRecord);

	/**
	 * 根据人员ID和模块ID获得已办列表
	 * 
	 * @param personId
	 * @param moduleId
	 * @return
	 */
	public List<WFInstanceRecord> getDoList(Long personId, Long moduleId);

	/**
	 * 根据流程实例ID获得已办列表
	 * 
	 * @param personId
	 * @param moduleId
	 * @return
	 */
//	public List<WFInstanceRecord> getInstanceRecord(Long flowInstanceId);
	
	/**
	 * 根据流程实例Id,来设置最后执行记录的抄送人信息
	 * @param flowInstanceId 流程实例Id
	 * @param personName 抄送人名称
	 * @return 无
	 */
	public void setCopyPerson(Long flowInstanceId,String personNames);

}
