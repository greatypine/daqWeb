package com.cnpc.pms.notice.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.messageModel.entity.Message;
import com.cnpc.pms.notice.entity.Notice;

/**
 * 
 * @author gbl
 * 公告
 */
public interface NoticeManager extends IManager{
	
	/**
	 * 
	* @Title: queryAllCity  
	* @Description: TODO 获取全部城市 
	* 2018年4月19日
	* @param @param whereStr
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public Map<String,Object> queryAllCity(QueryConditions queryConditions);
	
	
	/**
	 * 
	* @Title: getPartCity 
	* @Description: TODO 获取权限管辖的城市 
	* 2018年4月19日
	* @param @param whereStr
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public Map<String, Object> queryPartCity(QueryConditions queryConditions);

	
	/**
	 * 
	* @Title: queryStoreByCity  
	* @Description: TODO 根据城市查询门店
	* 2018年4月19日
	* @param @param cityInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public Map<String, Object> queryStoreByCity(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: getNoticeCity  
	* @Description: TODO 获取城市 
	* 2018年4月19日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws  
	* @author gbl
	 */
	public Map<String,Object> getNoticeCity(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: getReceiveZW  
	* @Description: TODO 获取职务 
	* 2018年4月20日
	* @param @param queryConditions
	* @param @return      
	* @return Map<String,Object> 
	* @throws  
	* @author gbl
	 */
	public Map<String,Object> getNoticeReceiveZW(QueryConditions queryConditions);
	
	/**
	 * 
	* @Title: saveNotice  
	* @Description: TODO 保存消息
	* 2018年4月20日
	* @param @param notice
	* @param @return      
	* @return Map<String,Object> 
	* @throws  
	* @author gbl
	 */
	public  Map<String,Object> saveNotice(Notice notice);
	
	/**
	 * 
	* @Title: selectNoticeByNoticeNo  
	* @Description: TODO 根据公告编号查询公告 
	* 2018年4月24日
	* @param @param noticeNo
	* @param @return      
	* @return Map<String,Object> 
	* @throws  
	* @author gbl
	 */
	public Map<String,Object> selectNoticeByNoticeNo(String noticeNo);
}
