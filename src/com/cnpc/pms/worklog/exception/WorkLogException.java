package com.cnpc.pms.worklog.exception;

import com.cnpc.pms.base.exception.PMSManagerException;

public class WorkLogException extends PMSManagerException  {
	/**
	 * 未指定类型的错误
	 */
	public static Integer WORKLOG_ERROR_CODE_NORMAL = 0;
	
	private Integer _ERROR_CODE = WORKLOG_ERROR_CODE_NORMAL;
	
	/**
	 * 构造函数，默认类型的错误
	 * @param msg 错误信息
	 */
	public WorkLogException(String msg){
		super("工作日志执行异常:" + msg);
	}
	
	/**
	 * 重载的异常构造函数
	 * 
	 * @param msg
	 */
	public WorkLogException(Integer errorCode, String msg) {
		super("工作日志执行异常:" + msg);
		this._ERROR_CODE = errorCode;
	}

	public Integer get_ERROR_CODE() {
		return _ERROR_CODE;
	}

	public void set_ERROR_CODE(Integer errorCODE) {
		_ERROR_CODE = errorCODE;
	}
}
