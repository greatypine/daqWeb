package com.cnpc.pms.personal.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.entity.DataHumanType;
import com.cnpc.pms.personal.entity.DistCareer;
import com.cnpc.pms.personal.entity.DistCareerCode;
import com.cnpc.pms.personal.entity.DistCityCode;
import com.cnpc.pms.personal.entity.DistCareer;
import com.cnpc.pms.personal.entity.DistCareerCode;
import com.cnpc.pms.personal.entity.StoreDynamic;
import com.cnpc.pms.personal.manager.DataHumanTypeManager;
import com.cnpc.pms.personal.manager.DistCareerCodeManager;
import com.cnpc.pms.personal.manager.DistCareerCodeManager;
import com.cnpc.pms.personal.manager.DistCareerManager;
import com.cnpc.pms.personal.manager.StoreDynamicManager;
import com.cnpc.pms.personal.manager.StoreManager;

@SuppressWarnings("all")
public class DistCareerCodeManagerImpl extends BizBaseCommonManager implements DistCareerCodeManager {


	@Override
	public DistCareerCode queryDistCareerCodeByName(String name) {
		if(name!=null&&name.trim().equals("请选择")) {
			return null;
		}
		IFilter iFilter = FilterFactory.getSimpleFilter(" career_group = '" + name + "'");
		List<?> lst_groupList = this.getList(iFilter);
		if (lst_groupList != null && lst_groupList.size() > 0) {
			DistCareerCode DistCareerCode = (DistCareerCode) lst_groupList.get(0);
			return DistCareerCode;
		} else {
			return null;
		}
	}


	/**
	 * 取得全部事业群
	 * 
	 * @return
	 */
	@Override
	public List<DistCareerCode> queryAllDistCareerList() {
		List<DistCareerCode> lst_groupList = (List<DistCareerCode>) this.getList();
		return lst_groupList;
	}

	public DistCareerCode queryDistCareerCodeByCode(String code) {
		if (code != null && code.length() > 0) {
			code = code.trim().toUpperCase();
		}
		IFilter iFilter = FilterFactory.getSimpleFilter(" groupcode = '" + code + "'");
		List<?> lst_groupList = this.getList(iFilter);
		if (lst_groupList != null && lst_groupList.size() > 0) {
			DistCareerCode DistCareerCode = (DistCareerCode) lst_groupList.get(0);
			return DistCareerCode;
		} else {
			return null;
		}
	}

	/**
	 * 根据ID查找一条城市信息
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public DistCareerCode queryDistCareerCodeByCode(Long id) {
		DistCareerCodeManager distCareerCodeManager = (DistCareerCodeManager) SpringHelper.getBean("DistCareerCodeManager");
		IFilter filter = FilterFactory.getSimpleFilter("id", id);
		List<DistCareerCode> list = (List<DistCareerCode>) distCareerCodeManager.getList(filter);
		if (list.size() > 0 && list != null) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	//新增加事业群
	@Override
	public DistCareerCode saveDistCareerCode(DistCareerCode distCareerCode) {
		DistCareerCodeManager distCareerCodeManager = (DistCareerCodeManager) SpringHelper.getBean("distCareerCodeManager");
		//查询是否存在相同的事业群名  和 编码 
		String career_group = distCareerCode.getCareer_group();
		String groupcode = distCareerCode.getGroupcode();
		IFilter filter = FilterFactory.getSimpleFilter("career_group", career_group).appendOr(FilterFactory.getSimpleFilter("groupcode", groupcode));
		List<DistCareerCode> distCareerCodeList = (List<DistCareerCode>) distCareerCodeManager.getList(filter);
		if(distCareerCodeList!=null&&distCareerCodeList.size()>0) {
			return null;
		}
		
		//插入到全部的事业群分配中。
		DistCareerManager distCareerManager = (DistCareerManager) SpringHelper.getBean("distCareerManager");
		DataHumanTypeManager dataHumanTypeManager = (DataHumanTypeManager) SpringHelper.getBean("dataHumanTypeManager");
		List<DataHumanType> dataHumanTypes = dataHumanTypeManager.queryAllDataHumanTypes();//取得全部事业群 
		List<Long> pk_userList = new ArrayList<Long>();
		
		List<?> codeList = distCareerManager.queryDistCareerCount();
		if (codeList != null && codeList.size() > 0) {
			for (Object o : codeList) {
				Map<String, Object> obj = (Map<String, Object>) o;
				if (obj.get("careercount") != null
						&& Integer.parseInt(obj.get("careercount").toString()) == dataHumanTypes.size()) {
					pk_userList.add(Long.parseLong(obj.get("pk_userid").toString()));
				}
			}
		}
		
		// 如果是全部的用户 则在全部的基础上 添加 新增加的城市
		if (pk_userList != null && pk_userList.size() > 0) {
			for (Long userid : pk_userList) {
				DistCareer distCareer = new DistCareer();
				distCareer.setPk_userid(userid);
				distCareer.setCareercode(distCareerCode.getGroupcode());
				distCareer.setCareername(distCareerCode.getCareer_group());
				distCareerManager.saveDistCareer(distCareer);
			}
		}
		
		this.saveObject(distCareerCode);
		
		return distCareerCode;
	}


}
