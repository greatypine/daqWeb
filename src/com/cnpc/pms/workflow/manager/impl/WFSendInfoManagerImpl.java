package com.cnpc.pms.workflow.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bid.manager.AttachmentManager;
import com.cnpc.pms.workflow.entity.WFSendInfo;
import com.cnpc.pms.workflow.exception.WFException;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.manager.WFSendInfoManager;

public class WFSendInfoManagerImpl extends BaseManagerImpl implements
		WFSendInfoManager {

	public WFSendInfo addWFSendInfo(WFSendInfo wf) {
		// TODO Auto-generated method stub
		if(wf.getModuleId()==null){
			throw new WFException("传入参数出错：ModuleId为空！");
		}
		if(wf.getSheetId()==null){
			throw new WFException("传入参数出错：SheetId为空！");
		}
		if(wf.getSendId()==null){
			throw new WFException("传入参数出错：SendId为空！");
		}
		if(wf.getReceiveId()==null){
			throw new WFException("传入参数出错：ReceiveId为空！");
		}
		wf.setState(0L);
		wf.setSendTime(new Date());
		super.saveObject(wf);
		return wf;
	}

	/**
	 * 此处传入的WFSendInfo是拼凑而成的ID为需要更新记录的ID其他的数据为需要新建记录的信息
	 * 
	 */
	public Boolean updateWFSendInfo(WFSendInfo wf) {
		// TODO Auto-generated method stub
		WFSendInfo oldSI = (WFSendInfo) super.getObject(wf.getId());
		if (oldSI == null) {
			System.out.println("搜索不到要更新的记录");
			return false;
		} else {
			WFSendInfo oldTemp = oldSI;
			oldTemp.setState(1L);
			BeanUtils.copyProperties(oldTemp, oldSI, new String[] { "id",
					"version" });
			oldSI.setResolvedTime(new Date());
			super.saveObject(oldSI);
		}
		WFSendInfo newSI = wf;
		newSI.setId(null);
		newSI.setVersion(null);
		newSI.setSendTime(new Date());
		super.saveObject(newSI);
		return true;

	}

	public Boolean submitWFSendInfo(Long id) {
		// TODO Auto-generated method stub
		WFSendInfo resolvedSI = (WFSendInfo) super.getObject(id);
		if (resolvedSI == null) {
			System.out.println("搜索不到要处理的记录");
			return false;
		} else {
			WFSendInfo resolvedTemp = resolvedSI;
			resolvedTemp.setState(2L);
			BeanUtils.copyProperties(resolvedTemp, resolvedSI, new String[] {
					"id", "version" });
			resolvedSI.setResolvedTime(new Date());
			super.saveObject(resolvedSI);
		}
		return true;
	}

	public WFSendInfo queryWFSendInfo(Long id) {
		// TODO Auto-generated method stub
		WFSendInfo wf = (WFSendInfo) super.getObject(id);
		return wf;
	}

	@SuppressWarnings("unchecked")
	public Boolean submitAllSendInfo(String moduleCode, Long sheetId) {
		// TODO Auto-generated method stub
		WFModuleManager moduleManager = (WFModuleManager) SpringHelper
				.getBean("WFModuleManager");
		Long moduleId = moduleManager.getModuleIdByCode(moduleCode);
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter(" moduleId = '"
				+ moduleId + "' and sheetId = '" + sheetId + "'"));
		WFSendInfoManager sendInfoManager = (WFSendInfoManager) SpringHelper
				.getBean("WFSendInfoManager");
		List<WFSendInfo> sendInfoList = (List<WFSendInfo>) sendInfoManager
				.getObjects(fsp);
		for (WFSendInfo sendInfo : sendInfoList) {
			submitWFSendInfo(sendInfo.getId());
		}
		return true;
	}

	/**
	 * 按照模块名称，表单号，接收人Id来检索未处理的消息<br>
	 * state=0<br>
	 * 如果已经处理，则不再处理。
	 * 
	 * @param modulecode
	 *            模块编号
	 * @param sheetId
	 *            表单号
	 * @param receiverId
	 *            接收人Id
	 * @return 找不到则返回null
	 */
	public WFSendInfo queryToDoWFSendInfo(String moduleCode, Long sheetId,
			Long receiverId) {
		WFModuleManager moduleManager = (WFModuleManager) SpringHelper
				.getBean("WFModuleManager");
		Long moduleId = moduleManager.getModuleIdByCode(moduleCode);
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter(" moduleId = '"
				+ moduleId + "' and sheetId = '" + sheetId + "'"
				+ " and receiveid='" + receiverId + "'" + " and state = 0"));
		List<WFSendInfo> list1 = (List<WFSendInfo>) this.getObjects(fsp);
		if (list1.size() > 0) {
			return list1.get(0);
		} else {
			return null;
		}
		
	}
	
	/**
	 * 删除一条业务待办消息
	 */
	public Long deleteSendInfoById(Long id) {
		try{
			super.removeById(id);
			return 1L;
		}catch(Exception e){
			e.printStackTrace();
			return 0L;
		}
	}
}
