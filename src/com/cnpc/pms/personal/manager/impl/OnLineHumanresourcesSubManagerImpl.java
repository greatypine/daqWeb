package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.entity.DistCity;
import com.cnpc.pms.personal.entity.OnLineHumanresourcesSub;
import com.cnpc.pms.personal.manager.DistCityManager;
import com.cnpc.pms.personal.manager.OnLineHumanresourcesSubManager;
import com.cnpc.pms.personal.manager.RegOnLineHumanresourcesManager;

public class OnLineHumanresourcesSubManagerImpl extends BizBaseCommonManager implements OnLineHumanresourcesSubManager {
	
	
	
	
	@Override
	public OnLineHumanresourcesSub removeOnlineSub(OnLineHumanresourcesSub onLineHumanresourcesSub) {
		
		return null;
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
