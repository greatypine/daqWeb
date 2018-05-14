package com.cnpc.pms.festival.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.festival.dao.FestivalStatDao;
import com.cnpc.pms.festival.manager.FestivalStatManager;
import com.cnpc.pms.platform.dao.OrderDao;

/**
 * @Function:节日 518 数据大屏 Manager实现类
 * @author:chenchuang
 * @date:2018年5月12日下午1:19:02  
 *
 * @version V1.0
 */
public class FestivalStatManagerImpl extends BizBaseCommonManager implements FestivalStatManager{

	@Override
	public List<Map<String, Object>> eshopRanking(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.eshopRanking(dateTime);
    	return lst_data;
	}

	@Override
	public Map<String, Object> queryTurnoverAndUser(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		Map<String,Object> order_obj = festivalStatDao.queryTurnoverAndUser(dateTime);
		return order_obj;
	}

	@Override
	public Map<String, Object> queryNewCusUser(String dateTime) {
		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		Map<String,Object> order_obj = orderDao.queryNewCusUser(dateTime);
		return order_obj;
	}

	@Override
	public List<Map<String, Object>> cityRanking(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.cityRanking(dateTime);
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryTurnoverByHour(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.queryTurnoverByHour(dateTime);
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryUserByHour(String dateTime) {
		FestivalStatDao festivalStatDao = (FestivalStatDao)SpringHelper.getBean(FestivalStatDao.class.getName());
		List<Map<String, Object>> lst_data = festivalStatDao.queryUserByHour(dateTime);
    	return lst_data;
	}

}
