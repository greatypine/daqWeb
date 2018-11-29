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
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.UserGroup;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserGroupManager;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.OnLineHumanresourcesDao;
import com.cnpc.pms.personal.dto.CareerChannelDto;
import com.cnpc.pms.personal.entity.DistCity;
import com.cnpc.pms.personal.entity.OnLineHumanresources;
import com.cnpc.pms.personal.entity.OnLineHumanresourcesSub;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.personal.entity.StoreKeeper;
import com.cnpc.pms.personal.entity.SyncRecord;
import com.cnpc.pms.personal.manager.DistCityManager;
import com.cnpc.pms.personal.manager.OnLineHumanresourcesManager;
import com.cnpc.pms.personal.manager.OnLineHumanresourcesSubManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.utils.ChineseToEnglish;

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
	
	
	
	//查询开通线上人员列表 
	@Override
	public Map<String, Object> queryRegOnLineHumanList(QueryConditions condition) {
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		UserDTO userDTO = userManager.getCurrentUserDTO();
		Map<String, Object> returnMap = new java.util.HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String name = null;
		for (Map<String, Object> map : condition.getConditions()) {
			if ("name".equals(map.get("key")) && map.get("value") != null) {// 查询条件
				name = map.get("value").toString();
			}
		}
		FSP fsp = new FSP();
		StringBuffer sbfCondition = new StringBuffer();
		sbfCondition.append("1=1");
		String userCode = userDTO.getUsergroup().getCode();
		
		if(name!=null&&name.length()>0) {
			sbfCondition.append(" and name like '%"+name+"%'");
		}else {
			if(userCode.equals("GLY")) {
				sbfCondition.append(" and 1=1");
			}else if(userCode.equals("ZBCPGLBGLY")) {
				sbfCondition.append(" and groupcode='ZBCPGLB'");
			}else if(userCode.equals("ZBCSGLBGLY")){
				sbfCondition.append(" and groupcode='ZBCSGLB'");
			}else {
				sbfCondition.append(" and 1=0");
			}
		}
		
		IFilter iFilter = FilterFactory.getSimpleFilter(sbfCondition.toString());
		fsp.setPage(pageInfo);
		fsp.setSort(SortFactory.createSort("id",ISort.DESC));
		fsp.setUserFilter(iFilter);
		List<Map<String, Object>> lst_data =(List<Map<String, Object>>) this.getList(fsp);
		returnMap.put("pageinfo", pageInfo);
		returnMap.put("header", "");
		returnMap.put("data", lst_data);
		return returnMap;	
	}
	
	@Override
	public  List<Map<String, Object>>  queryCareerChannel(){
		OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String, Object>> rtCareerChannel = orderDao.getCareerChannelOfGemini();
		return rtCareerChannel;
	}
	
	@Override
	public  OnLineHumanresources  saveRegOnLineHumanGroup(CareerChannelDto careerChannelDto){
		OnLineHumanresourcesSubManager onLineHumanresourcesSubManager = (OnLineHumanresourcesSubManager) SpringHelper.getBean("onLineHumanresourcesSubManager");
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		UserGroupManager u = (UserGroupManager)SpringHelper.getBean("userGroupManager");

		//根据电话查询 系统是否存在 相同登录号。如果存在。则禁用账号 并重新开通新账号 
		OnLineHumanresources onLineHumanresources = (OnLineHumanresources) this.getObject(careerChannelDto.getId());
		
		String phone = onLineHumanresources.getPhone();
		IFilter userFilter = FilterFactory.getSimpleFilter("mobilephone='"+phone+"' and disabledFlag=1 ");
		List<User> users = (List<User>) userManager.getList(userFilter);
		
		if(users!=null&&users.size()>0&&phone!=null&&phone.length()>0) {
			//如果存在账号信息 则禁用 
			for(User rmUser : users) {
				rmUser.setDisabledFlag(0);
				preSaveObject(rmUser);
				userManager.saveObject(rmUser);
			}
		}
		//重新开通 
		
		User user = new User();
		user.setName(onLineHumanresources.getName());
		user.setCode(ChineseToEnglish.getPingYin(onLineHumanresources.getName()));
		user.setDisabledFlag(1);
		user.setDoctype(0);
		user.setEmail("123@123.com");
		user.setEmployeeId(onLineHumanresources.getEmployee_no());
		user.setEnablestate(1);
		user.setPassword("e10adc3949ba59abbe56e057f20f883e"); //123456
		user.setPk_org(Long.parseLong("40284"));
		user.setMobilephone(onLineHumanresources.getPhone());
		user.setCareergroup(careerChannelDto.getCareername());
		
		IFilter groupFilter =FilterFactory.getSimpleFilter("code",careerChannelDto.getGroupcode());
        List<?> lst_groupList = u.getList(groupFilter);
        UserGroup userGroup = (UserGroup) lst_groupList.get(0);
		
        user.setUsergroup(userGroup);
		user.setLogicDel(0);
		preSaveObject(user);
		userManager.saveObject(user);
		
		onLineHumanresources.setGroupcode(careerChannelDto.getGroupcode());
		onLineHumanresources.setGroupname(careerChannelDto.getGroupname());
		preSaveObject(onLineHumanresources);
		this.saveObject(onLineHumanresources);
		//保存子表

		IFilter iFilter = FilterFactory.getSimpleFilter("online_id="+careerChannelDto.getId());
		List<OnLineHumanresourcesSub> onLineHumanresourcesSubs = (List<OnLineHumanresourcesSub>) onLineHumanresourcesSubManager.getList(iFilter);
		if(onLineHumanresourcesSubs!=null&&onLineHumanresourcesSubs.size()>0) {
			for(OnLineHumanresourcesSub rv:onLineHumanresourcesSubs) {
				onLineHumanresourcesSubManager.removeObject(rv);
			}
		}
		OnLineHumanresourcesSub onLineHumanresourcesSub = new OnLineHumanresourcesSub();
		onLineHumanresourcesSub.setOnline_id(onLineHumanresources.getId());
		onLineHumanresourcesSub.setCareerid(careerChannelDto.getCareerid());
		onLineHumanresourcesSub.setCareername(careerChannelDto.getCareername());
		onLineHumanresourcesSub.setChannelid(careerChannelDto.getChannelid());
		onLineHumanresourcesSub.setChannelname(careerChannelDto.getChannelname());
		preSaveObject(onLineHumanresourcesSub);
		onLineHumanresourcesSubManager.saveObject(onLineHumanresourcesSub);
		return onLineHumanresources;
	}
	
	@Override
	public CareerChannelDto queryCareerChannelById(Long id) {
		CareerChannelDto careerChannelDto = new CareerChannelDto();
		OnLineHumanresources onLineHumanresources = (OnLineHumanresources) this.getObject(id);
		careerChannelDto.setGroupcode(onLineHumanresources.getGroupcode());
		careerChannelDto.setGroupname(onLineHumanresources.getGroupname());
		OnLineHumanresourcesSubManager onLineHumanresourcesSubManager = (OnLineHumanresourcesSubManager) SpringHelper.getBean("onLineHumanresourcesSubManager");
		IFilter iFilter = FilterFactory.getSimpleFilter("online_id="+id);
		List<OnLineHumanresourcesSub> humanresourcesSubs = (List<OnLineHumanresourcesSub>) onLineHumanresourcesSubManager.getList(iFilter);
		if(humanresourcesSubs!=null&&humanresourcesSubs.size()>0) {
			OnLineHumanresourcesSub onLineHumanresourcesSub = humanresourcesSubs.get(0);
			careerChannelDto.setCareerid(onLineHumanresourcesSub.getCareerid());
			careerChannelDto.setCareername(onLineHumanresourcesSub.getCareername());
			careerChannelDto.setChannelid(onLineHumanresourcesSub.getChannelid());
			careerChannelDto.setChannelname(onLineHumanresourcesSub.getChannelname());
		}
		return careerChannelDto;
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
