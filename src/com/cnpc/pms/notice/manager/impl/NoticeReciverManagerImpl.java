package com.cnpc.pms.notice.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.dao.core.IDAORoot;
import com.cnpc.pms.base.exception.PMSManagerException;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.IJoin;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.inter.common.Result;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.entity.NoticeReciver;
import com.cnpc.pms.notice.manager.NoticeReciverManager;
import com.cnpc.pms.notice.util.NoticeTouchTask;
import com.cnpc.pms.notice.util.SingleThreadPool;
import com.cnpc.pms.slice.entity.AreaInfo;

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
		int result= 0;
		try {
			
			result = noticeReciverDao.updateNoticeReciverIsRead(noticeNo, employeeNo);
			
				
			ExecutorService exe = SingleThreadPool.getInstance().getExe();
			exe.execute(new NoticeTouchTask(noticeNo));//更新公告触达率
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<NoticeReciver> selectNoticeReciverOfUnRead(String employeeNo) {
		NoticeReciverManager nrm = (NoticeReciverManager)SpringHelper.getBean("noticeReciverManager");
		List<NoticeReciver> lst_areaInfos = new ArrayList<NoticeReciver>();
		try {
			IFilter filter = FilterFactory.getSimpleFilter("status", 0).appendAnd(FilterFactory.getSimpleFilter("isRead", 0));
			if(employeeNo!=null&&!"".equals(employeeNo)){
				filter = FilterFactory.getSimpleFilter("status", 0).appendAnd(FilterFactory.getSimpleFilter("isRead", 0)).appendAnd(FilterFactory.getSimpleFilter("employeeNo", employeeNo));
			}
			lst_areaInfos = (List<NoticeReciver>) nrm.getList(filter);
		} catch (Exception e) {
			e.printStackTrace();
			return lst_areaInfos;
		}
		return lst_areaInfos;
	}

	@Override
	public int getUnReadNotice(String employeeNo) {
		List<NoticeReciver> lst_areaInfos = this.selectNoticeReciverOfUnRead(employeeNo);
		return  lst_areaInfos.size();
	}

	@Override
	public List<NoticeReciver> getAllNotice(NoticeReciver noticeReciver) {
		NoticeReciverManager nrm = (NoticeReciverManager)SpringHelper.getBean("noticeReciverManager");
		List<NoticeReciver> lst_areaInfos = new ArrayList<NoticeReciver>();
		try {
			
			IFilter filter = FilterFactory.getSimpleFilter("status", 0).appendAnd(FilterFactory.getSimpleFilter("noticeNo",noticeReciver.getNoticeNo()));
			if(noticeReciver.getIsRead()!=null){
				filter = FilterFactory.getSimpleFilter("status", 0).appendAnd(FilterFactory.getSimpleFilter("noticeNo",noticeReciver.getNoticeNo())).appendAnd(FilterFactory.getSimpleFilter("isRead",noticeReciver.getIsRead()));
			}
			lst_areaInfos = (List<NoticeReciver>) nrm.getList(filter);
		} catch (Exception e) {
			e.printStackTrace();
			return lst_areaInfos;
		}
		return lst_areaInfos;
	}

	
    

}
