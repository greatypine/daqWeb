package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.WFSendInfo;

public interface WFSendInfoManager extends IManager {
	/**
	 * 
	 * 生成一条下达记录状态为待处理，供查询和生成待办用
	 */
	public WFSendInfo addWFSendInfo(WFSendInfo wf);
	/**
	 * 更新下达信息，此处两个步骤，将当前下达步骤设为已处理，并生成一条新的下达信息
	 * 此处传入的WFSendInfo是拼凑而成的ID为需要更新记录的旧ID其他的数据为需要新建记录的信息
	 * 
	 */
	public Boolean updateWFSendInfo(WFSendInfo wf);
	/**
	 * 结束下达流程，原理为将当前下达信息设置为已结束
	 * 
	 */
	public Boolean submitWFSendInfo(Long id);
	/**
	 * 根据ID获取下达信息
	 * 
	 */
	public WFSendInfo queryWFSendInfo(Long id);
	/**
	 * 根据moduleCode和sheetId结束所有下达流程
	 * 
	 */
	public Boolean submitAllSendInfo(String moduleCode,Long sheetId);
	
	/**
	 * 按照模块名称，表单号，接收人Id来检索未处理的消息<br>
	 * state=0<br>
	 * 如果已经处理，则不再处理。
	 * @param modulecode 模块编号
	 * @param sheetId 表单号
	 * @param receiverId 接收人Id
	 * @return 找不到则返回null
	 */
	public WFSendInfo queryToDoWFSendInfo(String modulecode,Long sheetId,Long receiverId);
	
	/**
	 * 根据指定的id，删除一条业务待办消息
	 * @param id
	 * @return
	 */
	public Long deleteSendInfoById(Long id);
}
