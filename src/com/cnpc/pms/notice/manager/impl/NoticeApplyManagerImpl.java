package com.cnpc.pms.notice.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.notice.dao.NoticeApplyDao;
import com.cnpc.pms.notice.dao.NoticeDao;
import com.cnpc.pms.notice.dao.NoticeReciverApplyDao;
import com.cnpc.pms.notice.dao.NoticeReciverDao;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.entity.NoticeApply;
import com.cnpc.pms.notice.manager.NoticeApplyManager;
import com.cnpc.pms.notice.manager.NoticeManager;
import com.cnpc.pms.notice.util.SendNotice;

public class NoticeApplyManagerImpl extends BizBaseCommonManager implements NoticeApplyManager{

	@Override
	public Map<String, Object> saveNoticeApply(NoticeApply noticeApply) {
		UserManager uManager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = uManager.getCurrentUserDTO();	
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		User receiveUser = noticeApply.getReceiveUser();
		String city = receiveUser.getCitynames();//传值使用
		String store = receiveUser.getName();//传值使用
		String zw  = receiveUser.getZw();
		Integer type = noticeApply.getType();//类型
		Integer grade = noticeApply.getGrade();//等级
		String timestamp = String.valueOf(System.currentTimeMillis());
		noticeApply.setNoticeNo(timestamp);
		noticeApply.setCheckStatus(0);
		preObject(noticeApply);
		saveObject(noticeApply);
		
		
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		
		try{
			if(city!=null&&!"".equals(city)){
				StringBuilder citySb = new StringBuilder();
				String[] cityArr = city.split(",");
	            for(String cityName:cityArr){
	            	citySb.append(",'"+cityName+"'");
	            }
	            citySb = citySb.deleteCharAt(0);
	            param.put("city", "("+citySb.toString()+")");
	            
			}
			
			if(store!=null&&!"".equals(store)){
				StringBuilder storeSb = new StringBuilder();
				String[] storeArr = store.split(",");
	            for(String storeName:storeArr){
	            	storeSb.append(","+storeName+"");
	            }
	            storeSb = storeSb.deleteCharAt(0);
	            param.put("store", "("+storeSb.toString()+")");
			}
			
			if(zw!=null&&!"".equals(zw)){
				StringBuilder zwSb = new StringBuilder();
				String[] zwArr = zw.split(",");
	            for(String zwName:zwArr){
	            	zwSb.append(",'"+zwName+"'");
	            }
	            zwSb = zwSb.deleteCharAt(0);
	            param.put("zw", "("+zwSb.toString()+")");
			}
			
			List<Map<String, Object>> receiveUserList = noticeDao.getReceiveEmployee(param);
			
			//保存公告接收人并发送app通知或者短信
//			SendNotice sn = new SendNotice(noticeApply, receiveUserList);
//			Thread thread = new Thread(sn);
//			thread.start();
		
			result.put("code",CodeEnum.success.getValue());
			result.put("message","申请成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code",CodeEnum.error.getValue());
			result.put("message","申请失败");
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> checkNoticeApply(NoticeApply noticeApply) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		UserManager uManager = (UserManager)SpringHelper.getBean("userManager");
		NoticeApplyManager noticeApplymanager= (NoticeApplyManager)SpringHelper.getBean("noticeApplyManager");
		UserDTO userDTO = uManager.getCurrentUserDTO();	
		NoticeDao noticeDao = (NoticeDao)SpringHelper.getBean(NoticeDao.class.getName());
		User receiveUser = noticeApply.getReceiveUser();
		List<NoticeApply> nelist=null;
		try {
			
			
			nelist = (List<NoticeApply>)this.getObjects(FilterFactory.getSimpleFilter("noticeNo", noticeApply.getNoticeNo()));
			if(nelist!=null&&nelist.size()>0){
		    	NoticeApply na = nelist.get(0);
	            int checkStatus = noticeApply.getCheckStatus();
				na.setCheckStatus(checkStatus);
				na.setCheckDate(new Date());
				na.setCheckUserId(userDTO.getId());
				na.setCheckUserName(userDTO.getName());
				if(-1==checkStatus||2==checkStatus){//驳回||审核不通过
					na.setRemark(noticeApply.getRemark());
				}
				
				preObject(na);
				noticeApplymanager.saveObject(na);
				result.put("code", CodeEnum.success.getValue());
				result.put("message","修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", CodeEnum.success.getValue());
			result.put("message","修改成功");
		}
		return result;
	}

	@Override
	public NoticeApply getNoticeApplyByNoticeNo(String noticeNo) {
		NoticeManager noticeManager = (NoticeManager) SpringHelper.getBean("noticeManager");
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("noticeNo",noticeNo));
				
		if (lst_data != null && lst_data.size() > 0) {
			return (NoticeApply) lst_data.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> selectNoticeByNoticeNo(String noticeNo) {
		NoticeApplyDao noticeDao = (NoticeApplyDao)SpringHelper.getBean(NoticeApplyDao.class.getName());
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			list = noticeDao.selectNoticeByNoticeNo(noticeNo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", list);
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> deleteNotice(String noticeNo) {
		    NoticeApplyDao noticeDao = (NoticeApplyDao)SpringHelper.getBean(NoticeApplyDao.class.getName());
			NoticeReciverApplyDao noReciverDao = (NoticeReciverApplyDao)SpringHelper.getBean(NoticeReciverApplyDao.class.getName());
			Map<String,Object> result = new HashMap<String,Object>();
			
			try {
				
				noticeDao.deleteNoticeApply(noticeNo);
				noReciverDao.deleteNoticeReciverApply(noticeNo);
				result.put("code",CodeEnum.success.getValue());
				result.put("message","撤销成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.put("code",CodeEnum.error.getValue());
				result.put("message","撤销失败");
				return result;
			}
			
			return result;
	}

}
