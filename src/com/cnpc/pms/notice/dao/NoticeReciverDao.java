package com.cnpc.pms.notice.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;

/**
 * 
 * @author gbl
 * 消息接收者
 */
public interface NoticeReciverDao {
	
	/**
	 * 
	* @Title: selectNoticeReciver  
	* @Description: TODO 根据员工编号查询公告通知 
	* 2018年4月24日
	* @param @param employeeNo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String,Object>> selectNoticeReciver(String employeeNo,PageInfo pageInfo);
	
	/**
	 * 
	* @Title: updateNoticeReciverIsRead  
	* @Description: TODO 修改公告的阅读状态为已读 
	* 2018年4月24日
	* @param @param noticeNo
	* @param @param employeeNo
	* @param @return      
	* @return int 
	* @throws  
	* @author gbl
	 */
	public int updateNoticeReciverIsRead(String noticeNo,String employeeNo);
	
	
	
	
}
