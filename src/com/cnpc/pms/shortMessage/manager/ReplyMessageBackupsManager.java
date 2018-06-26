package com.cnpc.pms.shortMessage.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.shortMessage.dto.ReplyMessageDto;
import com.cnpc.pms.shortMessage.entity.ReplyMessageBackups;
/**
 * 
 * @author gbl
 *
 */
public interface ReplyMessageBackupsManager extends IManager{
	/**
	 * 
	* @Title: saveReplyMessageBackups  
	* @Description: TODO 保存短信回复记录
	* 2018年6月26日
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	* @author gbl
	 */
	public void saveReplyMessageBackups(ReplyMessageDto rdto);
}
