package com.cnpc.pms.shortMessage.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.UserGroup;
import com.cnpc.pms.inter.common.CodeEnum;
import com.cnpc.pms.shortMessage.dto.UserGroupUserDto;
import com.cnpc.pms.shortMessage.entity.SMSUserGroup;
import com.cnpc.pms.shortMessage.entity.SMSUserGroupUser;
import com.cnpc.pms.shortMessage.manager.SMSUserGroupUserManager;

public class SMSUserGroupUserManagerImpl extends BizBaseCommonManager implements SMSUserGroupUserManager{

	@Override
	public Map<String, Object> saveUserGroupUser(SMSUserGroup userGroup) {
		    Map<String,Object> result = new HashMap<String,Object>();
		
			List<UserGroupUserDto> list= userGroup.getList();
			for(int i=0;i<list.size();i++){
				SMSUserGroupUser sgu = new SMSUserGroupUser();
				sgu.setUserGroupCode(userGroup.getCode());
				sgu.setMobilePhone(list.get(i).getMobilephone());
				sgu.setUserName(list.get(i).getName());
				sgu.setUserCode(list.get(i).getEmployee_no());
				sgu.setInviteCode(list.get(i).getInviteCode());
				preObject(sgu);
				saveObject(sgu);
			}
			
		
		return result;
	}

}
