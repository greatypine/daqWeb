package com.cnpc.pms.notice.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.notice.entity.NoticeApplyCity;
import com.cnpc.pms.notice.entity.NoticeApplyJob;
import com.cnpc.pms.notice.entity.NoticeApplyStore;

/***
 * 公告申请dao
 * @author gbl
 *
 */
public interface NoticeApplyDao {
	
	/**
	 * 
	* @Title: selectNoticeByNoticeNo  
	* @Description: TODO 查询公告申请详情
	* 2018年5月22日
	* @param @param noticeNo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	 */
	public List<Map<String,Object>> selectNoticeByNoticeNo(String noticeNo);
	
	/**
	 * 
	* @Title: deleteNotice  
	* @Description: TODO 撤销公告申请 
	* 2018年5月22日
	* @param @param noticeNo
	* @param @return      
	* @return int 
	* @throws
	 */
	public int deleteNoticeApply(String noticeNo);
	
	/**
	 * 
	* @Title: saveNoticeApplyCity  
	* @Description: TODO 保存公告接收城市 
	* 2018年5月22日
	* @param @param noticeApplyCity
	* @param @return      
	* @return int 
	* @throws
	 */
	public int saveNoticeApplyCity(NoticeApplyCity noticeApplyCity);
	
	public int deleteNoticeApplyCity(String noticeNo);
	
	/**
	 * 
	* @Title: saveNoticeApplyStore  
	* @Description: TODO  保存公告接收门店
	* 2018年5月22日
	* @param @param noticeApplyStore
	* @param @return      
	* @return int 
	* @throws
	 */
	public int saveNoticeApplyStore(NoticeApplyStore noticeApplyStore);
	
	public int deleteNoticeApplyStore(String noticeNo);
	
	/**
	 * 
	* @Title: saveNoticeApplyJob  
	* @Description: TODO 保存公告接收职务
	* 2018年5月22日
	* @param @param noticeApplyJob
	* @param @return      
	* @return int 
	* @throws
	 */
	public int saveNoticeApplyJob(NoticeApplyJob noticeApplyJob);
	
	public int deleteNoticeApplyJob(String noticeNo);
	
}
