package com.cnpc.pms.workflow.exception;

import com.cnpc.pms.base.exception.PMSManagerException;

/**
 * 工作流组件执行时抛出的异常,封装其业务含义
 * 
 * @author liujunsong
 * 
 */
public class WFException extends PMSManagerException {
	// 一般工作流异常,均定义为0,暂未详细进行区分.
	public static Integer WF_ERROR_CODE_NORMAL = 0;
	
	// -1 代表是针对流程当前步骤的类型错误抛出的异常 <br>
	// 例如在开始节点和结束节点来调用提交工作流的方法 <br>
	// 这个异常是为了避免在退回以后,错误调用submitWorkFlow的接口<br>
	public static Integer WF_ERROR_CODE_ILLEGAL = -1;

	private Integer _ERROR_CODE = WF_ERROR_CODE_NORMAL;

	/**
	 * 重载的异常构造函数
	 * 
	 * @param msg
	 */
	public WFException(String msg) {
		super("工作流调用异常:" + msg);
	}

	/**
	 * 重载的异常构造函数
	 * 
	 * @param msg
	 */
	public WFException(Integer errorCode, String msg) {
		super("工作流调用异常:" + msg);
		this._ERROR_CODE = errorCode;
	}

	public Integer getErrorCode() {
		return _ERROR_CODE;
	}
}
