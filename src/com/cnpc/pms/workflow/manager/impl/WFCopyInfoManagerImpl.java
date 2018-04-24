package com.cnpc.pms.workflow.manager.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WFCopyInfo;
import com.cnpc.pms.workflow.entity.WFInstanceRecord;
import com.cnpc.pms.workflow.manager.WFCopyInfoManager;
import com.cnpc.pms.workflow.manager.WFInstanceRecordManager;

public class WFCopyInfoManagerImpl extends BaseManagerImpl implements
		WFCopyInfoManager {

	public WFCopyInfo addWFCopyInfo(WFCopyInfo wfCopyInfo) {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		System.out.println("操作人是：" + sessionData.get("userId"));
		if (wfCopyInfo.getSendId() == null) {
			wfCopyInfo.setSendId((Long) sessionData.get("userId"));
		}
		super.saveObject(wfCopyInfo);

		return wfCopyInfo;
	}

	public Boolean deleteWFCopyInfo(Long id) {
		super.removeObjectById(id);
		return null;
	}

	public WFCopyInfo queryWfCopyInfo(Long id) {
		WFCopyInfo wfCopyInfo = (WFCopyInfo) super.getObject(id);
		return wfCopyInfo;
	}

	public WFCopyInfo updateWfCotyInfo(WFCopyInfo wf) {
		// TODO Auto-generated method stub
		WFCopyInfo dbObj = null;
		dbObj = (WFCopyInfo) super.getObject(wf.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = wf;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(wf, dbObj,
					new String[] { "id", "version" });

		}

		super.saveObject(dbObj);
		return wf;
	}

	/**
	 * 获取某一用户特定流程实例的待处理抄送信息
	 */
	public List<WFCopyInfo> getUserCopyInfo(Long receiverId, Long flowInstanceid) {
		String strFilter = "receiverId=" + receiverId + " and "
				+ "flowInstanceId=" + flowInstanceid;

		IFilter filter = FilterFactory.getStringFilter(strFilter);

		return (List<WFCopyInfo>) this.getList(filter);
	}

}
