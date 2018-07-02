package com.cnpc.pms.shortMessage.manager.impl;

import java.util.Map;

import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.ReplyMessageBackups;
import com.cnpc.pms.shortMessage.manager.ReplyMessageBackupsManager;

public class ReplyMessageBackupsImpl extends BizBaseCommonManager implements ReplyMessageBackupsManager {

	@Override
	public void saveReplyMessageBackups(ReplyMessageDto rdto) {
		
		try {
			ReplyMessageBackups rmb = new ReplyMessageBackups();
			rmb.setMsgContent(rdto.getContent());
			rmb.setPhone(rdto.getPhone());
			rmb.setSpNumber(rdto.getSpNumber());
			rmb.setError(rdto.getError());
			rmb.setRemoteIP(rdto.getRemoteIP());
			preObject(rmb);
			saveObject(rmb);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
