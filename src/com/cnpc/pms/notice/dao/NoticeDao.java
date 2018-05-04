package com.cnpc.pms.notice.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.messageModel.entity.Message;

/**
 * 
 * @author gbl
 * 公告
 */
public interface NoticeDao {
	
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
	public List<Map<String,Object>> queryAllCity(String whereStr,PageInfo pageInfo);
	
	
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
	public List<Map<String, Object>> queryPartCity(String whereStr,PageInfo pageInfo);
    
	
	/**
	 * 
	* @Title: queryCityByUserId  
	* @Description: TODO 查询用户管理的城市 
	* 2018年4月19日
	* @param @param userId
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String,Object>> queryCityByUserId(Long userId);
	
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
	public List<Map<String, Object>> queryStoreByCity(String cityInfo);
	
	/**
	 * 
	* @Title: getStoreOfCity  
	* @Description: TODO  查询门店
	* 2018年4月19日
	* @param @param cityStr
	* @param @param otherWhere
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String, Object>> getStoreOfCity(String cityStr,String otherWhere,PageInfo pageInfo);
	
	/**
	 * 
	* @Title: getReceiveZW  
	* @Description: TODO  查询职务
	* 2018年4月20日
	* @param @param otherWhere
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String, Object>> getReceiveZW(String otherWhere,PageInfo pageInfo);
	
	/**
	 * 
	* @Title: getReceiveEmployee  
	* @Description: TODO  获取公告接受人
	* 2018年4月20日
	* @param @param param
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String, Object>> getReceiveEmployee(Map<String, Object> param);
	
	/**
	 * 
	* @Title: selectNoticeByNoticeNo  
	* @Description: TODO 根据公告编号查询公告
	* 2018年4月24日
	* @param @param noticeNo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws  
	* @author gbl
	 */
	public List<Map<String,Object>> selectNoticeByNoticeNo(String noticeNo);
	
	public List<Map<String,Object>> getCityOfZb();
	
	public List<Map<String,Object>> getCityOfCs(Long userId);
	
	public List<Map<String,Object>> getStoreByCity(String cityCode);
	
	public List<Map<String,Object>> getAllZw();
	
	/**
	 * 
	* @Title: deleteNotice  
	* @Description: TODO 撤销公告 
	* 2018年5月4日
	* @param @param noticeNo
	* @param @return      
	* @return int 
	* @throws
	 */
	public int deleteNotice(String noticeNo);
}
