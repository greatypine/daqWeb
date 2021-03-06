package com.cnpc.pms.personal.manager.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.dao.StorexpandDao;
import com.cnpc.pms.personal.dto.StorexpandDTO;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.manager.StorexpandManager;
import com.cnpc.pms.utils.DateUtils;

public class StorexpandManagerImpl extends BizBaseCommonManager implements StorexpandManager {

	@Override
	public Map<String, Object> progressOfNetworkConstruction() {
		Map<String, Object> result = new HashMap<String, Object>();
		StorexpandDao storexpandDao = (StorexpandDao)SpringHelper.getBean(StorexpandDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		List<Map<String,Object>> contractAndthroughByYear = storexpandDao.getContractAndthroughByYear(Calendar.getInstance().get(Calendar.YEAR)+"");
		List<Map<String,Object>> throughByWeek = storexpandDao.getThroughByWeek();
		List<Map<String,Object>> storeStateCount = storeDao.getStoreStateCount();
		List<Map<String,Object>> storeCardBycity = storeDao.getStoreCardBycity();
		List<Map<String, Object>> dateAndWeek = DateUtils.getDateBeforesix();
		result.put("contractAndthroughByYear", contractAndthroughByYear);
		result.put("throughByWeek", throughByWeek);
		result.put("storeStateCount", storeStateCount);
		result.put("storeCardBycity", storeCardBycity);
		result.put("dateAndWeek", dateAndWeek);
		return result;
	}
	@Override
	public Map<String, Object> showOfficeData(QueryConditions conditions) {
		StorexpandDao storexpandDao = (StorexpandDao) SpringHelper.getBean(StorexpandDao.class.getName());
		// 查询的数据条件
		StringBuilder sb_where = new StringBuilder();
		// 分页对象
		PageInfo obj_page = conditions.getPageinfo();
		// 返回的对象，包含数据集合、分页对象等
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<Map<String, Object>> mapWhereList = conditions.getConditions();
		Map<String, Object> weidu_where = mapWhereList.get(2);
		Map<String, Object> statistical_time_period_where = mapWhereList.get(0);
		Map<String, Object> city_where = mapWhereList.get(1);
		if ("year".equals(weidu_where.get("key")) && null != weidu_where.get("value")
				&& !"".equals(weidu_where.get("value"))) {
			String str = (String) weidu_where.get("value");
			if ("period_type".equals(statistical_time_period_where.get("key")) && null != statistical_time_period_where.get("value")
					&& !"".equals(statistical_time_period_where.get("value"))) {
				if("0".equals(str)){
					sb_where.append(" AND st.time_period like '").append(statistical_time_period_where.get("value")).append("'");
				}else if("1".equals(str)){
					sb_where.append(" AND st.year = '").append(String.valueOf(statistical_time_period_where.get("value")).replace("%", "")).append("'");
				}
			}
		}
		if ("cityname".equals(city_where.get("key")) && null != city_where.get("value")
					&& !"".equals(city_where.get("value"))) {
				sb_where.append(" AND st.city_name like '").append(city_where.get("value")).append("'");
		}

		System.out.println(sb_where);
		map_result.put("pageinfo", obj_page);
		map_result.put("header", "目标值录入信息");
		map_result.put("data", storexpandDao.getStorexpandList(sb_where.toString(), obj_page));
		return map_result;
	}

	@Override
	public Storexpand saveOrUpdateOfficeNetwork(StorexpandDTO storexpand) {
		Storexpand saveStorexpand = null;
		if (storexpand.getId() != null) {
			saveStorexpand = getStorexpandByOriginId(storexpand.getId());
		} else {
			saveStorexpand = new Storexpand();
		}
		try{
			saveStorexpand.setCityname(storexpand.getCityname());
			saveStorexpand.setCityno(storexpand.getCityno());
			saveStorexpand.setSurvey_quantity(storexpand.getSurvey_quantity());
			saveStorexpand.setContract_quantity(storexpand.getContract_quantity());
			saveStorexpand.setThrough_quantity(storexpand.getThrough_quantity());
			saveStorexpand.setStart_time(new SimpleDateFormat("yyyy/MM/dd").parse(storexpand.getStart_time()));
			saveStorexpand.setEnd_time(new SimpleDateFormat("yyyy/MM/dd").parse(storexpand.getEnd_time()));
			saveStorexpand.setStatistical_time_period(storexpand.getStart_time().replace("/", "-")+"~"+storexpand.getEnd_time().replace("/", "-"));
			saveStorexpand.setType("store");
			saveStorexpand.setPeriod_type("week");
			preObject(saveStorexpand);
			StorexpandManager officeNetworkManager = (StorexpandManager) SpringHelper.getBean("storexpandManager");
			if (storexpand.getId() == null) {
				this.insertOfficeNetwork(saveStorexpand);
			}else{
				officeNetworkManager.saveObject(saveStorexpand);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saveStorexpand;
	}
	@Override
	public StorexpandDTO getStorexpandById(Long id) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("id", id));
		StorexpandDTO storexpandDTO = new StorexpandDTO();
		if (list != null && list.size() > 0) {
			Storexpand storexpand = (Storexpand) list.get(0);
			storexpandDTO.setId(storexpand.getId());
			storexpandDTO.setCityname(storexpand.getCityname());
			storexpandDTO.setCityno(storexpand.getCityno());
			storexpandDTO.setContract_quantity(storexpand.getContract_quantity());
			storexpandDTO.setEnd_time(DateUtils.dateFormat(storexpand.getEnd_time(), "YYYY/MM/dd"));
			storexpandDTO.setPeriod_type(storexpand.getPeriod_type());
			storexpandDTO.setStart_time(DateUtils.dateFormat(storexpand.getStart_time(), "YYYY/MM/dd"));
			storexpandDTO.setStatistical_time_period(storexpand.getStatistical_time_period().replace("-", "/"));
			storexpandDTO.setSurvey_quantity(storexpand.getSurvey_quantity());
			storexpandDTO.setThrough_quantity(storexpand.getThrough_quantity());
			return storexpandDTO;
		}
		return null;
	}
	@Override
	public Storexpand getStorexpandByOriginId(Long id) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("id", id));
		if (list != null && list.size() > 0) {
			 Storexpand storexpand = (Storexpand) list.get(0);
			 return storexpand;
		}
		return null;
	}

	@Override
	public void insertOfficeNetwork(Storexpand saveStorexpand) {
		StorexpandManager auditManager=(StorexpandManager)SpringHelper.getBean("storexpandManager");
		Storexpand storexpand = new Storexpand();
		try {
			storexpand.setCityname(saveStorexpand.getCityname());
			storexpand.setCityno(saveStorexpand.getCityno());
			storexpand.setContract_quantity(saveStorexpand.getContract_quantity());
			storexpand.setSurvey_quantity(saveStorexpand.getSurvey_quantity());
			storexpand.setThrough_quantity(saveStorexpand.getThrough_quantity());
			storexpand.setType(saveStorexpand.getType());
			storexpand.setPeriod_type(saveStorexpand.getPeriod_type());
			if(saveStorexpand.getStatistical_time_period()!=null&&!"".equals(saveStorexpand.getStatistical_time_period())){
				storexpand.setStart_time(saveStorexpand.getStart_time());
				storexpand.setEnd_time(saveStorexpand.getEnd_time());
				storexpand.setStatistical_time_period(saveStorexpand.getStart_time()+"~"+saveStorexpand.getEnd_time());
			}
			storexpand.setStatistical_time_period(saveStorexpand.getStatistical_time_period());
			preObject(storexpand);
			auditManager.saveObject(storexpand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> getTaskQuantityExist(String cityname) {
		Map<String,Object> result = new HashMap<String,Object>();
		StorexpandDao storexpandDao = (StorexpandDao)SpringHelper.getBean(StorexpandDao.class.getName());
		result = storexpandDao.getTaskQuantityExist(cityname);
		return result;
	}

	@Override
	public Map<String, Object> getStatistics(String statistics,String cityname) {
		Map<String,Object> result = new HashMap<String,Object>();
		StorexpandDao storexpandDao = (StorexpandDao)SpringHelper.getBean(StorexpandDao.class.getName());
		result = storexpandDao.getStatisticsExist(statistics.replace("/", "-"),cityname);
		return result;
	}
}
