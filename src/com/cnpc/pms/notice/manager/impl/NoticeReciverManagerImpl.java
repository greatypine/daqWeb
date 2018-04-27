package com.cnpc.pms.notice.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.dao.core.IDAORoot;
import com.cnpc.pms.base.exception.PMSManagerException;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.IJoin;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.manager.NoticeReciverMananger;

public class NoticeReciverManagerImpl extends BizBaseCommonManager implements NoticeReciverMananger {

	@Override
	public Map<String, Object> selectNoticeReciver(String employeeNo) {
		NoticeReciverDao noticeReciverDao = (NoticeReciverDao)SpringHelper.getBean(NoticeReciverDao.class.getName());
		Map<String,Object> result = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = noticeReciverDao.selectNoticeReciver(employeeNo);
			result.put("data", list);
		} catch (Exception e) {
			result.put("data", list);
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public int updateNoticeReciverIsRead(String noticeNo, String employeeNo) {
		
		NoticeReciverDao noticeReciverDao = (NoticeReciverDao)SpringHelper.getBean(NoticeReciverDao.class.getName());
		
		return noticeReciverDao.updateNoticeReciverIsRead(noticeNo, employeeNo);
		
	}

}
