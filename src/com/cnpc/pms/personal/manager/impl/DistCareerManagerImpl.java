package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.cnpc.pms.base.entity.DataEntity;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.DistCareerDao;
import com.cnpc.pms.personal.entity.DistCareer;
import com.cnpc.pms.personal.entity.DistCareerCode;
import com.cnpc.pms.personal.manager.DistCareerCodeManager;
import com.cnpc.pms.personal.manager.DistCareerManager;

@SuppressWarnings("all")
public class DistCareerManagerImpl extends BizBaseCommonManager implements DistCareerManager {

	@Override
	public List<DistCareer> queryDistCareerByUserIdCareer(Long userid, String careername) {
		IFilter iFilter = FilterFactory.getSimpleFilter("pk_userid", userid)
				.appendAnd(FilterFactory.getSimpleFilter("careername", careername));
		List<DistCareer> distCareerList = (List<DistCareer>) this.getList(iFilter);
		return distCareerList;
	}

	@Override
	public List<DistCareer> queryDistCareerListByUserId(Long userid) {
		IFilter iFilter = FilterFactory.getSimpleFilter("pk_userid", userid);
		List<DistCareer> distCareerList = (List<DistCareer>) this.getList(iFilter);
		return distCareerList;
	}

	protected void preSaveObject(Object o) {
		if (o instanceof DataEntity) {
			User sessionUser = null;
			if (null != SessionManager.getUserSession() && null != SessionManager.getUserSession().getSessionData()) {
				sessionUser = (User) SessionManager.getUserSession().getSessionData().get("user");
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

	@Override
	public DistCareer queryDistCareersByUserId(Long userid) {
		// 先查询user
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		User user = (User) userManager.getObject(userid);
		DistCareer distCareer = new DistCareer();
		IFilter iFilter = FilterFactory.getSimpleFilter("pk_userid", userid);
		List<DistCareer> lst_data = (List<DistCareer>) this.getList(iFilter);
		distCareer.setUsername(user.getName());
		distCareer.setPk_userid(user.getId());
		if (lst_data != null) {
			if (lst_data.size() == 1) {
				distCareer.setCareer1(lst_data.get(0).getCareername());
			}
			if (lst_data.size() == 2) {
				distCareer.setCareer1(lst_data.get(0).getCareername());
				distCareer.setCareer2(lst_data.get(1).getCareername());
			}
			if (lst_data.size() == 3) {
				distCareer.setCareer1(lst_data.get(0).getCareername());
				distCareer.setCareer2(lst_data.get(1).getCareername());
				distCareer.setCareer3(lst_data.get(2).getCareername());
			}
			if (lst_data.size() == 4) {
				distCareer.setCareer1(lst_data.get(0).getCareername());
				distCareer.setCareer2(lst_data.get(1).getCareername());
				distCareer.setCareer3(lst_data.get(2).getCareername());
				distCareer.setCareer4(lst_data.get(3).getCareername());
			}
			if (lst_data.size() == 5) {
				distCareer.setCareer1(lst_data.get(0).getCareername());
				distCareer.setCareer2(lst_data.get(1).getCareername());
				distCareer.setCareer3(lst_data.get(2).getCareername());
				distCareer.setCareer4(lst_data.get(3).getCareername());
				distCareer.setCareer5(lst_data.get(4).getCareername());
			}
			if (lst_data.size() > 6) {
				distCareer.setCareer5("全部");
			}
		}
		return distCareer;
	}

	@Override
	public DistCareer updateDistCareer(DistCareer distCareer) {

		UserManager manager = (UserManager) SpringHelper.getBean("userManager");
		UserDTO userDTO = manager.getCurrentUserDTO();

		Long pk_userid = distCareer.getPk_userid();
		String username = distCareer.getUsername();

		// 从配置文件改为数据库取
		DistCareerCodeManager distCareerCodeManager = (DistCareerCodeManager) SpringHelper.getBean("distCareerCodeManager");

		if (distCareer.getIsSelectAll() != null && distCareer.getIsSelectAll()) {
			// 选择了全部
			List<DistCareerCode> dis = distCareerCodeManager.queryAllDistCareerList();
			if (dis != null && dis.size() > 0) {
				// 保存之前 删除之前的
				DistCareerDao distCareerDao = (DistCareerDao) SpringHelper.getBean(DistCareerDao.class.getName());
				distCareerDao.removeDistCareerByUserid(pk_userid);

				for (DistCareerCode dc : dis) {
					DistCareer d = new DistCareer();
					d.setCareername(dc.getCareer_group());
					d.setCareercode(dc.getGroupcode());
					d.setPk_userid(pk_userid);
					preSaveObject(d);
					saveObject(d);
				}
			}

		} else {
			String career1 = distCareer.getCareer1();
			String career2 = distCareer.getCareer2();
			String career3 = distCareer.getCareer3();
			String career4 = distCareer.getCareer4();
			String career5 = distCareer.getCareer5();
			List<DistCareer> distCareers = new ArrayList<DistCareer>();
			// PropertiesValueUtil propertiesValueUtil = new
			// PropertiesValueUtil("classpath:conf/citycode.properties");
			if (career1 != null && career1.trim().length() > 0&&!career1.equals("请选择")) {
				DistCareer distCareer1 = new DistCareer();
				distCareer1.setPk_userid(pk_userid);
				DistCareerCode dis = distCareerCodeManager.queryDistCareerCodeByName(career1);
				if (dis == null) {
					return null;
				}
				distCareer1.setCareercode(dis.getGroupcode());
				distCareer1.setCareername(career1);
				distCareers.add(distCareer1);
			}
			if (career2 != null && career2.trim().length() > 0&&!career2.equals("请选择")) {
				DistCareer distCareer2 = new DistCareer();
				distCareer2.setPk_userid(pk_userid);
				DistCareerCode dis = distCareerCodeManager.queryDistCareerCodeByName(career2);
				if (dis == null) {
					return null;
				}
				distCareer2.setCareercode(dis.getGroupcode());
				distCareer2.setCareername(career2);
				distCareers.add(distCareer2);
			}
			if (career3 != null && career3.trim().length() > 0&&!career3.equals("请选择")) {
				DistCareer distCareer3 = new DistCareer();
				distCareer3.setPk_userid(pk_userid);
				DistCareerCode dis = distCareerCodeManager.queryDistCareerCodeByName(career3);
				if (dis == null) {
					return null;
				}
				distCareer3.setCareercode(dis.getGroupcode());
				distCareer3.setCareername(career3);
				distCareers.add(distCareer3);
			}
			if (career4 != null && career4.trim().length() > 0&&!career4.equals("请选择")) {
				DistCareer distCareer4 = new DistCareer();
				distCareer4.setPk_userid(pk_userid);
				DistCareerCode dis = distCareerCodeManager.queryDistCareerCodeByName(career4);
				if (dis == null) {
					return null;
				}
				distCareer4.setCareercode(dis.getGroupcode());
				distCareer4.setCareername(career4);
				distCareers.add(distCareer4);
			}
			if (career5 != null && career5.trim().length() > 0&&!career5.equals("请选择")) {
				DistCareer distCareer5 = new DistCareer();
				distCareer5.setPk_userid(pk_userid);
				DistCareerCode dis = distCareerCodeManager.queryDistCareerCodeByName(career5);
				if (dis == null) {
					return null;
				}
				distCareer5.setCareercode(dis.getGroupcode());
				distCareer5.setCareername(career5);
				distCareers.add(distCareer5);
			}
			// 删除之前的
			DistCareerDao distCareerDao = (DistCareerDao) SpringHelper.getBean(DistCareerDao.class.getName());
			distCareerDao.removeDistCareerByUserid(pk_userid);

			for (DistCareer d : distCareers) {
				preSaveObject(d);
				saveObject(d);
			}
		}
		return distCareer;
	}

	@Override
	public List<Long> queryDistinctUserId() {
		List<DistCareer> distCareerList = (List<DistCareer>) this.getList();
		List<Long> userIds = new ArrayList<Long>();
		if (distCareerList != null) {
			for (DistCareer d : distCareerList) {
				userIds.add(d.getPk_userid());
			}
		}
		HashSet hashSet = new HashSet(userIds);
		userIds.clear();
		userIds.addAll(hashSet);
		return userIds;
	}

	/**
	 * 根据城市 查找所有的 usesrId
	 * 
	 * @param city
	 * @return
	 */
	@Override
	public List<Long> queryDistinctByCareer(String career_group) {
		IFilter iFilter = FilterFactory.getSimpleFilter("careername like '" + career_group + "'");
		List<DistCareer> distCareerList = (List<DistCareer>) this.getList(iFilter);
		List<Long> userIds = new ArrayList<Long>();
		if (distCareerList != null) {
			for (DistCareer d : distCareerList) {
				userIds.add(d.getPk_userid());
			}
		}
		HashSet hashSet = new HashSet(userIds);
		userIds.clear();
		userIds.addAll(hashSet);
		return userIds;
	}

	/**
	 * 统计 每个人 都管理多少个城市 数量
	 * 
	 * @return
	 */
	@Override
	public List<?> queryDistCareerCount() {
		DistCareerDao distCareerDao = (DistCareerDao) SpringHelper.getBean(DistCareerDao.class.getName());
		List<?> list = distCareerDao.queryDistCareerCount();
		return list;
	}

	@Override
	public void saveDistCareer(DistCareer d) {
		preSaveObject(d);
		this.saveObject(d);
	}

	@Override
	public DistCareer findDistCareerBycareername(String career_group) {
		// 获取当前登录人的用户id
		User sessionUser = null;
		if (null != SessionManager.getUserSession() && null != SessionManager.getUserSession().getSessionData()) {
			sessionUser = (User) SessionManager.getUserSession().getSessionData().get("user");
		}
		List<?> lst_data = this.getList(
				FilterFactory.getSimpleFilter("career_group='" + career_group + "' and pk_userid=" + sessionUser.getId()));
		if (lst_data != null && lst_data.size() > 0) {
			return (DistCareer) lst_data.get(0);
		}
		return null;
	}

}
