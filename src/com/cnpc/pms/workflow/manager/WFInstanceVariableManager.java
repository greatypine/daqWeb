package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFFlowVariable;
import com.cnpc.pms.workflow.entity.WFInstanceVariable;

/**
 * 工作流实例变量维护Manager
 * 
 * @author jrn
 * 
 */
public interface WFInstanceVariableManager extends IManager {
	/**
	 * 新增一条变量数据
	 * 
	 * @param var
	 * @return
	 */
	public WFInstanceVariable addInstanceVar(WFInstanceVariable var);

	/**
	 * 更新变量值
	 * 
	 * @param var
	 * @return
	 */
	public void saveInstanceVarList(Long flowInstanceId,
			Map<String, String> paramMap);

	/**
	 * 检索变量值
	 * 
	 * @param varname
	 * @param flowInstanceId
	 * @return
	 */
	public WFInstanceVariable getInstanceVarByCode(String code, Long flowInstanceId);

	/**
	 * 拷贝流程模版参数到流程实例参数
	 * 
	 * @param argList
	 * @param flowinstanceid
	 * @param args
	 * @return
	 */
	public List<WFInstanceVariable> copyInstanceVar(List<WFFlowVariable> argList,
			Map<String, String> args, Long flowinstanceid);

	/**
	 * 根据流程实例ID获取参数名称与值的对应列表
	 * 
	 * @param flowInstanceId
	 * @return
	 */
	public List<WFInstanceVariable> getListByInstanceId(Long flowInstanceId);
}
