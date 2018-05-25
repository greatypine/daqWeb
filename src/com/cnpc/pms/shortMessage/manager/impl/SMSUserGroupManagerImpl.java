package com.cnpc.pms.shortMessage.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.inter.common.CodeEnum;

import com.cnpc.pms.shortMessage.dto.UserGroupUserDto;
import com.cnpc.pms.shortMessage.entity.SMSUserGroup;
import com.cnpc.pms.shortMessage.entity.SMSUserGroupUser;
import com.cnpc.pms.shortMessage.manager.SMSUserGroupManager;
import com.cnpc.pms.shortMessage.manager.SMSUserGroupUserManager;

public class SMSUserGroupManagerImpl extends BizBaseCommonManager implements SMSUserGroupManager{

	@Override
	public Map<String, Object> saveUserGroup(SMSUserGroup sMSUserGroup) {
		SMSUserGroupUserManager smsUserGroupUserManager = (SMSUserGroupUserManager)SpringHelper.getBean("SMSUserGroupUserManager");
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			SMSUserGroup sg = new SMSUserGroup();
			sg.setCode(sMSUserGroup.getCode());
			sg.setName(sMSUserGroup.getName());
			sg.setType(sMSUserGroup.getType());
			preObject(sg);
			saveObject(sg);
			
			smsUserGroupUserManager.saveUserGroupUser(sMSUserGroup);
			result.put("code",CodeEnum.success.getValue());
			result.put("message","保存成功");
			
		} catch (Exception e) {
			result.put("code",CodeEnum.error.getValue());
			result.put("message","保存失败");
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> selectUserGroup(SMSUserGroup smsUserGroup) {
		
		List<?> lst_data = this.getList(FilterFactory.getSimpleFilter("code",smsUserGroup.getCode()));
				
		if (lst_data != null && lst_data.size() > 0) {
			return (List<Map<String, Object>>)lst_data;
		}
		return null;
	}

}
