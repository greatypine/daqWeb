package com.cnpc.pms.workflow.util;

/**
 * 工作流待办类型的数据字典定义DICT_WF_TODO_TYPE
 * 
 * @author liujunsong
 * 
 */
public class WFToDoTypeConst {
	/**
	 * 审批待办
	 */
	public static long FLOW_TODO = 0L;
	/**
	 * 抄送待办
	 */
	public static long COPY_TODO = 1L;
	/**
	 * 委托待办
	 */
	public static long DELEGATE_TODO = 2L;
	/**
	 * 下达待办
	 */
	public static long SEND_TODO = 3L;
	
	/**
	 * 退回待办
	 */
	public static long BACK_TODO =4L;
}
