package com.cnpc.pms.notice.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;

/**
 * 
 * @author gbl
 * 公告接收者
 */
public interface NoticeReciverMananger extends IManager {
	
	/**
	 * 
	* @Title: selectNoticeReciver  
	* @Description: TODO  根据员工编号查询公告通知
	* 2018年4月24日
	* @param @param employeeNo
	* @param @return      
	* @return Map<String,Object> 
	* @throws  
	* @author gbl
	 */
	public Map<String,Object> selectNoticeReciver(String employeeNo);
	
	/**
	 * 
	* @Title: updateNoticeReciverIsRead  
	* @Description: TODO 更新公告通知的阅读状态 
	* 2018年4月24日
	* @param @param noticeNo
	* @param @param employeeNo      
	* @return int 
	* @throws  
	* @author gbl
	 */
	public int updateNoticeReciverIsRead(String noticeNo,String employeeNo);
	
	
}
