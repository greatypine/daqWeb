package com.cnpc.pms.personal.manager.impl;


import java.util.List;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.dao.OnLineHumanresourcesDao;
import com.cnpc.pms.personal.entity.OnLineHumanresources;
import com.cnpc.pms.personal.entity.SyncRecord;
import com.cnpc.pms.personal.manager.OnLineHumanresourcesManager;

public class OnLineHumanresourcesManagerImpl extends BizBaseCommonManager implements OnLineHumanresourcesManager {
	
	@Override
	public OnLineHumanresources saveOnlineHuman(SyncRecord syncRecord) {
		OnLineHumanresourcesDao onLineHumanresourcesDao = (OnLineHumanresourcesDao) SpringHelper.getBean(OnLineHumanresourcesDao.class.getName());
		//同步线上人员表 并处理员工编号和邀请码
		IFilter repFilter =FilterFactory.getSimpleFilter("cardnumber",syncRecord.getCardid());
		List<OnLineHumanresources> onLineHumanresources = (List<OnLineHumanresources>) this.getList(repFilter);
		OnLineHumanresources saveOnLineHuman = null;
		if(onLineHumanresources!=null&&onLineHumanresources.size()>0){//如果存在 则更新 
			saveOnLineHuman = onLineHumanresources.get(0);
			saveOnLineHuman.setCardnumber(syncRecord.getCardid());
			saveOnLineHuman.setDeptname(syncRecord.getDept());
			saveOnLineHuman.setName(syncRecord.getName());
			saveOnLineHuman.setOrgname(syncRecord.getOrg());
			saveOnLineHuman.setPhone(syncRecord.getPhone());
			saveOnLineHuman.setWork_no(syncRecord.getCardid());
			saveOnLineHuman.setLefttime(syncRecord.getLefttime());
			preSaveObject(saveOnLineHuman);
			this.saveObject(saveOnLineHuman);
		} else {
			saveOnLineHuman = new OnLineHumanresources();
			saveOnLineHuman.setCardnumber(syncRecord.getCardid());
			saveOnLineHuman.setDeptname(syncRecord.getDept());
			
			String maxInviteCode = onLineHumanresourcesDao.queryMaxInviteCode();
			saveOnLineHuman.setInviteCode(maxInviteCode);
			saveOnLineHuman.setEmployee_no("GA"+maxInviteCode);
			
			saveOnLineHuman.setName(syncRecord.getName());
			saveOnLineHuman.setOrgname(syncRecord.getOrg());
			saveOnLineHuman.setPhone(syncRecord.getPhone());
			saveOnLineHuman.setWork_no(syncRecord.getCardid());
			saveOnLineHuman.setLefttime(syncRecord.getLefttime());
			
			preSaveObject(saveOnLineHuman);
			this.saveObject(saveOnLineHuman);
		}
		return saveOnLineHuman;
	}
	
	
	
	protected void preSaveObject(Object o) {
		if (o instanceof DataEntity) {
			User sessionUser = null;
			if (null != SessionManager.getUserSession()
					&& null != SessionManager.getUserSession().getSessionData()) {
				sessionUser = (User) SessionManager.getUserSession()
						.getSessionData().get("user");
			}
			DataEntity dataEntity = (DataEntity) o;
			java.util.Date date = new java.util.Date();
			java.sql.Date sdate = new java.sql.Date(date.getTime());
			// insert处理时添加创建人和创建时间
			if (null == dataEntity.getCreate_time()) {
				dataEntity.setCreate_time(sdate);
				if (null != sessionUser) {
					dataEntity.setCreate_user(sessionUser.getCode());
					dataEntity.setCreate_user_id(sessionUser.getId());
				}
			}
			dataEntity.setUpdate_time(sdate);
			if (null != sessionUser) {
				dataEntity.setUpdate_user(sessionUser.getCode());
				dataEntity.setUpdate_user_id(sessionUser.getId());
			}
		}
	}
	
}
