package com.cnpc.pms.workflow.util;

/**
 * 审批的操作类型常量
 * 
 * @author liujunsong
 * 
 */
public class WFSubmitOperTypeConst {
	public static long NORMAL_OPER = 0L; // 正常操作
	public static long CHOICENXET_OPER = 1L; // 强制跳转操作
	public static long FINISH_OPER = 2L; // 强制终止操作
	public static long SYSTEM_OPER = 4L; // 系统自动审批操作(自动通过使用)
}
