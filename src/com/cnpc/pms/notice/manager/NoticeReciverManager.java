package com.cnpc.pms.notice.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.inter.common.Result;
import com.cnpc.pms.notice.entity.NoticeReciver;

/**
 * 
 * @author gbl
 * 公告接收者
 */
public interface NoticeReciverManager extends IManager {
	
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
	public Result selectNoticeReciver(NoticeReciver noticeReciver,PageInfo pageInfo);
	
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