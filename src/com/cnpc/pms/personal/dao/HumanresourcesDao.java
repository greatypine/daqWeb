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
	public List<Map<String,Object>> queryToPostHuman();
	
	/**
	 * 
	 * TODO  查询近六周离职人员
	 * 2018年4月8日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryLeaveHuman();
	/**
	 * 
	 * TODO  查询近六周总人数
	 * 2018年4月9日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> queryHumanTotal();
	public List<Map<String,Object>> queryWeekPoint();
}