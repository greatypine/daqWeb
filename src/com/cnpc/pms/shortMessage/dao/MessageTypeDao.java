package com.cnpc.pms.shortMessage.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;

/**
 * 短信类型
 * @author gbl
 *
 */
public interface MessageTypeDao {
	
	/**
	 * 
	* @Title: selectMessageType  
	* @Description: TODO 查询短信类型 
	* 2018年6月13日
	* @param @param whereStr
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectMessageType(String whereStr,PageInfo pageInfo);
	
	public List<Map<String,Object>> selectMessageType(Long id);
	
	/**
	 * 
	* @Title: selectMessageTypeById  
	* @Description: TODO  
	* 2018年6月13日
	* @param @param id
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectMessageTypeById(Long id);
	
	
}
