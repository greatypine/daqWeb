package com.cnpc.pms.workflow.wfinstance.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.workflow.dto.WFCopyParam;
import com.cnpc.pms.workflow.dto.WFParam;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.entity.WFStepInstance;
import com.cnpc.pms.workflow.entity.WFToDoView;

/**
 * 工作流实例信息维护类Manager
 * 
 * @author jrn
 * 
 */
public interface WFInstanceManager extends IManager {

	/**
	 * 根据给定部门和业务模块来检索可用流程列表<br>
	 * 输入:业务部门ID,deptId<br>
	 * 输入:业务模块Code ModuleCode<br>
	 * 这两个字段封装在WFParam里面使用.<br>
	 * 这一接口在未来可能会被删除掉，暂时保留下来<br>
	 * 
	 * @param para
	 * @return 找不到的情况返回null
	 */
	public WFFlow findWorkFlow(WFParam para);

	/**
	 * 驱动工作流实例执行
	 * 
	 * @param flowinstanceid
	 *            流程实例ID
	 * @param deptid
	 *            部门ID
	 * @param operid
	 *            操作人ID
	 * @param toOperid
	 *            下一节点操作人ID
	 * @param moduleid
	 *            业务模块ID
	 * @param sheetid
	 *            表单ID
	 * @param isPassed
	 *            审批类型 1通过 0拒绝
	 * @param memo
	 *            审批意见
	 * @param opertype
	 *            委托待办类型
	 * @param args
	 *            审批过程中传递的参数
	 * @return
	 */

	// public WFFlowInstance submitWF(Long flowinstanceid, Long deptid,
	// Long operid, Long toOperid, Long moduleid, Long sheetid,
	// Long isPassed, String memo, Long opertype, Map<String, String> args);

	/**
	 * 检索流程实例的相关信息
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	// public WFFlowInstance getFlowInstance(Long flowInstanceId);

	/**
	 * 按照用户号和模块号来检索已办信息
	 * 
	 * @param personId
	 * @param moduleId
	 * @return
	 */
	// public List<WFInstanceRecord> getDoList(Long personId, Long moduleId);

	/**
	 * 根据用户ID获取用户待办列表
	 * 
	 * @param personId
	 *            用户ID
	 * @return
	 */
	// public List<WFToDoView> getToDoList(Long personId);

	/**
	 * 根据用户ID和业务模块ID获取用户待办列表</br> 用于生成待办页面</br>
	 * 
	 * @param personId
	 *            用户ID
	 * @param moduleId
	 *            业务模块ID
	 * @return
	 */
	// public List<WFToDoView> getToDoList(Long personId, Long moduleId);

	/**
	 * 启动工作流（与前台界面交互的方法）<br>
	 * 当流程被退回发起人时,可能会重新启动工作流<br>
	 * 
	 * 
	 * @param para
	 * @return
	 */
	public WFFlowInstance startWorkFlow(WFParam para);

	/**
	 * 提交工作流（与前台界面交互的方法）<br>
	 * 提交工作流有几种不同的情况:<br>
	 * 1.正常审批提交,用于界面操作提交,可能审批通过,也可能审批不通过<br>
	 * 2.自行选择后续节点,从后续节点中选择一个节点来审批通过<br>
	 * 3.强制终止,终止流程,直接跳转到结束节点<br>
	 * 4.系统自动审批通过,由系统自动审批通过,定时自动执行<br>
	 * @param para 一个用WFParam对象封装起来的审批参数对象
	 * @return
	 */
	public WFFlowInstance submitWorkFlow(WFParam para);

	/**
	 * 根据业务模块Code和表单ID获取流程实例，如无流程实例 返回null<br>
	 * 这一接口提供给用户程序进行调用,用来针对同一实例重新启动工作流<br>
	 * 参数:moduleCode 模块编号<br>
	 * 参数:sheetId 表单Id <br>
	 * 
	 * @param para
	 * @return
	 */
	public WFFlowInstance getFlowInstanceByPara(WFParam para);

	/**
	 * 根据query参数获取待办分类分页结果<br>
	 * 这一功能被工作流待办部分所使用.<br>
	 * 用来生成待办的汇总页面
	 * 
	 * @param condition
	 * @return
	 */
	public Map<String, Object> getToDoModileList(QueryConditions condition);

	/**
	 * 根据query参数获取已办分类分页结果<br>
	 * 这一功能被工作流待办部分所使用.<br>
	 * 用来生成已办的汇总页面
	 * 
	 * @param condition
	 * @return
	 */
	public Map<String, Object> getDoneModileList(QueryConditions condition);

	/**
	 * 自动提交所有配置为可自动提交的工作流实例</br> 用于处理工作流执行时超时自动通过的节点.
	 */
	public boolean submitWFAuto();

	// -------------下面是专门用来实现特殊流程处理的功能接口---------------------//
	/**
	 * 获取流程的当前执行步骤节点,以获取其内部属性值<br>
	 * 获取步骤实例以后,其相关属性如下:<br>
	 * isFinish: 是否可终止 1 是 0 否 <br>
	 * isChoiseNext: 是否可选择后续节点 1 是 0 否 <br>
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	public WFStepInstance getCurrentStepInstance(Long flowInstanceId);

	/**
	 * 获取当前流程实例的所有下一步节点列表<br>
	 * 如果当前节点配置为可选择下一步,则检索所有下一步节点<br>
	 * 否则直接返回一个new ArrayList();
	 */
	public List<WFStepInstance> getNextStepList(Long flowInstanceId);

	// --------------下面是增加的和流程抄送相关的功能接口 -------------------------//
	
	/**
	 * 当一个流程审批通过时,按照给定的抄送人,发送流程抄送信息
	 * @param param
	 * 返回1成功
	 * 返回0失败
	 */
	public Long addCopyInfo(WFCopyParam param);
	/**
	 * 设置某一条流程抄送消息为已读,仅用param中的Id字段即可
	 * @param param
	 * @return 1成功0失败
	 */
	public Long readCopyInfo(WFCopyParam param);
	
	/**
	 * 手工设置当前流程执行实例的待操作人字段
	 * @param flowInstanceId
	 * @param operId
	 * @return
	 */
	public WFFlowInstance setToOperId(Long flowInstanceId,Long tooperId);
}
