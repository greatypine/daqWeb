package com.cnpc.pms.workflow.util;

/**
 * Manager名称对应的常量
 * 
 * @author liujunsong
 * 
 */
public class WFManagerConst {
	// ---------------第一部分----------------------------------//

	// 流程模版部分的Manager定义,8个Manager
	public static String WF_FLOW = "WFFlowManager";
	
	// Not Used Now.
	public static String WF_FLOWORG = "WFFlowOrgManager";
	// Called By WFInstanceManager
	public static String WF_FLOWTRANSITION = "WFFlowTransitionManager";
	// Not Used Now.
	public static String WF_FLOWVARIABLE = "WFFlowVariableManager";

	// 流程步骤部分(4个Manager)
	// Called by WFInstanceManager
	public static String WF_FLOWSTEP = "WFFlowStepManager";
	// Not Used Now.
	public static String WF_FLOWSTEPTOPOS = "WFFlowStepToPosManager";
	// Call by WFInsPersonCopyManagerImpl Only.
	public static String WF_FLOWSTEPPPERSONCOPY = "WFFlowStepPersonCopyManager";
	// Call by WFInsPositionCopyManagerImpl Only.
	public static String WF_FLOWSTEPPOSITIONCOPY = "WFFlowStepPositionCopyManager";

	// -------------- 第二部分 ------------------------------//
	// 流程执行部分的Manager定义(共8个)

	//实例
	public static String WF_INSTANCE="WFFlowInstanceManager";
	//实例变量
	public static String WF_INSTANCEVARIABLE = "WFInstanceVariableManager";
	//实例转换
	public static String WF_INSTANCETRANSITION="WFInstanceTransitionManager";
	//实例步骤
	public static String WF_INSTANCESTEP="WFStepInstanceManager";
	
	//流程执行步骤部分(4个)
	
	// -------------- 第三部分 ------------------------------//
	// 模块部分的Manager定义
	public static String WF_MODULEMANAGER = "WFModuleManager";
	
	// 流程抄送的Manager名称
	public static String WF_COPYINFOMANAGER="WFCopyInfoManager";
}
