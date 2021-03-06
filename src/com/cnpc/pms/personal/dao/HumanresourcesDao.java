package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.entity.Humanresources;
import com.cnpc.pms.personal.entity.Store;

public interface HumanresourcesDao {

	public String queryMaxEmpNo(String type);
	
	public String queryMaxStoreKeeperNo();
	
	
	public Map<String, Object> queryHumanresourcesList(Humanresources humanresources, PageInfo pageInfo);
	
	
	public List<Map<String, Object>> exportHuman(Humanresources humanresources);
	
	
	public Map<String, Object> queryStoreOperationList(Store store, PageInfo pageInfo);
	
	public Map<String, Object> queryStoreOperationLeaveList();
	
	
	public List<Map<String, Object>> exportStoreOperationHuman(Store store);
	
	/**
	 * 
	 * TODO  查询近六周入职人员
	 * 2018年4月8日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryToPostHuman(String zw);
	
	
	
	/**
	 * 
	 * TODO  查询近六周离职人员
	 * 2018年4月8日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryLeaveHuman(String zw);
	/**
	 * 
	 * TODO  查询近六周总人数
	 * 2018年4月9日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryHumanTotal(String zw);
	/**
	 * 
	 * TODO  查询人员信息
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryWeekPoint();
	
	
	//取得最大邀请码
	public String queryMaxInviteCode();
	
	
	//员工档案查 询线上人员 列表 
	public Map<String, Object> queryOnLineHumanresourcesList(Humanresources humanresources, PageInfo pageInfo);
	//线上人员导出 
	public List<Map<String, Object>> exportOnLineHuman(Humanresources humanresources);


	/**
	 *
	 * TODO  查询近六周离职，或入职线下人员总人数（含店长）
	 * @author gaoll
	 * @return
	 */
	public List<Map<String, Object>> querySixWeekHuman();

	/**
	 *
	 * TODO  查询不同门店类型线下人员总人数（含店长）
	 * @author gaoll
	 * @return
	 */
	public List<Map<String, Object>> queryHumanByStoreType();

	/**
	 * TODO  统计不同职位在职总人数
	 * 2108/09/12
	 * @param zw
	 * @param city_id
	 * @param status 统计在职总人数还是统计月店国安侠总人数
	 * @author gaoll
	 * @return
	 */
	public Integer queryHumanresourceCountByZw(String zw,Long city_id,String status);


}