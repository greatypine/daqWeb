package com.cnpc.pms.worklog.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.worklog.dao.WorkLogFollowDAO;
import com.cnpc.pms.worklog.entity.WorkLogFollow;
import com.cnpc.pms.worklog.manager.WorkLogFollowManager;

public class WorkLogFollowManagerImpl extends BaseManagerImpl implements WorkLogFollowManager{

	public void deleteWorkLogFollow(Long id) {
		// TODO Auto-generated method stub
		super.removeObjectById(id);
	}
	public  void deleteWorkLogFollowByFollowId(Long followId){
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		Long id=this.getDao().checkIsFollowed(cuser.getId(), followId);
		if(id.equals(new Long(0))){
			
		}else{
			super.removeObjectById(id);
		}
	}
	public WorkLogFollowDAO getDao(){
		return (WorkLogFollowDAO) super.getDao();
	}
	public WorkLogFollow saveWorkLogFollow(WorkLogFollow obj) {
		WorkLogFollow workLogFollow=new WorkLogFollow();
		//先检测登陆用户是否已经关注过该人
		
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		UserManager manager=(UserManager)SpringHelper.getBean("userManager");
		if(!this.getDao().checkIsFollowed(cuser.getId(), obj.getFollowId()).equals(new Long(0))){
			return null;
		}else{
			workLogFollow.setUserId(cuser.getId());
			//通过关注人Id(obj.getFollowId()),去查该用户所在室，所和职位
			if(this.getDao().getPosAndOrg(obj.getFollowId())!=null){
				WorkLogFollow w=this.getDao().getPosAndOrg(obj.getFollowId());
				workLogFollow.setFollowId(obj.getFollowId());
				//manager.getObject(obj.getFollowId());
				User user=(User)manager.getObject(obj.getFollowId());
				String followName=user.getName();
				workLogFollow.setFollowName(followName);
				workLogFollow.setOrgName(w.getOrgName());
				workLogFollow.setSuoName(w.getSuoName());
				workLogFollow.setPosName(w.getPosName());
				super.saveObject(workLogFollow);
				return workLogFollow;
			}else{
				return null;
			}
		}
		
		
		
	}
	public List<WorkLogFollow> getWorkLogFollowList(){
		//从session里面获取到本人信息;
		User cuser = (User) SessionManager.getUserSession().getSessionData()
		.get("user");
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("userId",cuser.getId()));
		List<WorkLogFollow> list=(List<WorkLogFollow>) this.getObjects(fsp);
		return list;
	}
}
