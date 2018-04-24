package com.cnpc.pms.workflow.util;

/**
 * 工作流审批结果的常量定义
 * @author liujunsong
 *
 */
public class WFSubmitReustTypeConst {
	/**
	 * 未通过
	 */
	public static long SUBMIT_NOPASS = 0L;
	/**
	 * 通过
	 */
	public static long SUBMIT_PASS = 1L;
	/**
	 * 已填报(专用于开始节点) 
	 */
	public static long SUBMIT_WRITE = 2L;
}
