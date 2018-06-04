package com.cnpc.pms.notice.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.notice.entity.Notice;
import com.cnpc.pms.notice.entity.NoticeApply;

public interface NoticeApplyManager extends IManager{
	
	/**
	 * 
	* @Title: saveNoticeApply  
	* @Description: TODO  保存公告申请
	* 2018年5月21日
	* @param @param notice
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public  Map<String,Object> saveNoticeApply(NoticeApply noticeApply);
	
	/**
	 * 
	* @Title: checkNoticeApply  
	* @Description: TODO 审核公告申请 
	* 2018年5月22日
	* @param @param noticeApply
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String,Object> checkNoticeApply(NoticeApply noticeApply);
	
	/**
	 * 
	* @Title: getNoticeApplyByNoticeNo  
	* @Description: TODO  根据公告编号查询公告申请
	* 2018年5月22日
	* @param @param noticeNo
	* @param @return      
	* @return NoticeApply 
	* @throws
	 */
	public NoticeApply getNoticeApplyByNoticeNo(String noticeNo);
	
	/**
	 * 
	* @Title: selectNoticeByNoticeNo  
	* @Description: TODO 根据公告查询公告申请详情 
	* 2018年5月22日
	* @param @param noticeNo
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String,Object> selectNoticeByNoticeNo(String noticeNo);
	
	/**
	 * 
	* @Title: deleteNotice  
	* @Description: TODO 撤销公告申请 
	* 2018年5月22日
	* @param @param noticeNo
	* @param @return      
	* @return Map<String,Object> 
	* @throws
	 */
	public Map<String,Object> deleteNotice(String noticeNo);
}
