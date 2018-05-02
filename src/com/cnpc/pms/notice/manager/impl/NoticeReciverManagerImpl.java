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
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.inter.common.Result;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.entity.NoticeReciver;
import com.cnpc.pms.notice.manager.NoticeReciverManager;

public class NoticeReciverManagerImpl extends BizBaseCommonManager implements NoticeReciverManager {

	
	public Result selectNoticeReciver(NoticeReciver noticeReciver,PageInfo pageInfo) {
		NoticeReciverDao noticeReciverDao = (NoticeReciverDao)SpringHelper.getBean(NoticeReciverDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Result result = new Result();
		try {
			list = noticeReciverDao.selectNoticeReciver(noticeReciver.getEmployeeNo(),pageInfo);
			result.setData(list);
			result.setCode(CodeEnum.success.getValue());
			result.setMessage(CodeEnum.success.getDescription());
		} catch (Exception e) {
			e.printStackTrace();
			result.setData(null);
			result.setCode(CodeEnum.error.getValue());
			result.setMessage(CodeEnum.error.getDescription());
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
