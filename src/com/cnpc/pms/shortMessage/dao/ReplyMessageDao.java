package com.cnpc.pms.shortMessage.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;

/**
 * 短信回复
 * @author gbl
 *
 */
public interface ReplyMessageDao {
		
	/**
	 * 
	* @Title: selectReplyMessage  
	* @Description: TODO 查询短信回复 
	* 2018年6月25日
	* @param @param whereStr
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String, Object>> selectReplyMessage(String whereStr, PageInfo pageInfo);
	
}
