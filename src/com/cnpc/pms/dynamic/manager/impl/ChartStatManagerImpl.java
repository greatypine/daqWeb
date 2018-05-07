package com.cnpc.pms.dynamic.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.dao.ChartStatDao;
import com.cnpc.pms.dynamic.entity.ChartStatDto;
import com.cnpc.pms.dynamic.manager.ChartStatManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.utils.DateUtils;

public class ChartStatManagerImpl extends BizBaseCommonManager implements ChartStatManager {

	@Override
	public List<Map<String, Object>> queryAllDept(){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String, Object>> lst_data = orderDao.queryAllDept();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryAllChannel(){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String, Object>> lst_data = orderDao.queryAllChannel();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> findChannelByDept(String deptId){
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		List<Map<String, Object>> lst_data = orderDao.findChannelByDept(deptId);
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryDayTurnover(ChartStatDto csd) {
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryDayTurnover(csd);
		return order_obj;
	}
	
	@Override
	public Map<String, Object> queryMonthTurnover(ChartStatDto csd) {
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryMonthTurnover(csd);
    	return order_obj;
	}
	
	@Override
	public Map<String, Object> queryOnlineOfflineTurnover(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryOnlineOfflineTurnover(csd);
    	return order_obj;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByHour(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryTurnoverByHour(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByDay(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryTurnoverByDay(csd);
    	return lst_data;
	}
	
	@Override
	public List<String> getDateByWeek(){
		return DateUtils.getDateByWeek();
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByWeek(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryTurnoverByWeek(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTurnoverByMonth(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryTurnoverByMonth(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryTargetByMonth(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryTargetByMonth(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryDataOfScatterplot(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryDataOfScatterplot(csd);
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryDayUser(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryDayUser(csd);
		return order_obj;
	}
	
	@Override
	public List<Map<String, Object>> queryUserByHour(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryUserByHour(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryUserByDay(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryUserByDay(csd);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryUserByWeek(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryUserByWeek(csd);
    	return lst_data;
	}
	@Override
	public List<Map<String, Object>> queryUserByMonth(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryUserByMonth(csd);
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryOnlineOfflineUser(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryOnlineOfflineUser(csd);
    	return order_obj;
	}

	@Override
	public List<Map<String, Object>> querUserOfScatterplot(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.querUserOfScatterplot(csd);
		return lst_data;
	}

	@Override
	public Map<String, Object> queryCurMonthUser(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		Map<String,Object> order_obj = chartStatDao.queryCurMonthUser(csd);
		return order_obj;
	}
	
}
