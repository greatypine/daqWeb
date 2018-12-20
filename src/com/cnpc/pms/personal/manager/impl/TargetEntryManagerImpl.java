package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.dto.UserDTO;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.dao.StorexpandDao;
import com.cnpc.pms.personal.dao.TargetEntryDao;
import com.cnpc.pms.personal.dto.StorexpandDTO;
import com.cnpc.pms.personal.entity.Storexpand;
import com.cnpc.pms.personal.entity.TargetEntry;
import com.cnpc.pms.personal.manager.StorexpandManager;
import com.cnpc.pms.personal.manager.TargetEntryManager;
import com.cnpc.pms.utils.DateUtils;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TargetEntryManagerImpl extends BizBaseCommonManager implements TargetEntryManager {

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
	public Map<String, Object> showTargetData(QueryConditions conditions) {
		UserManager userManager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = userManager.getCurrentUserDTO();
		TargetEntryDao targetEntryDao = (TargetEntryDao) SpringHelper.getBean(TargetEntryDao.class.getName());
		// 查询的数据条件
		StringBuilder sb_where = new StringBuilder();
		// 分页对象
		PageInfo obj_page = conditions.getPageinfo();
		// 返回的对象，包含数据集合、分页对象等
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<Map<String, Object>> mapWhereList = conditions.getConditions();
//		Map<String, Object> channel_where = mapWhereList.get(2);
		Map<String, Object> time_where = mapWhereList.get(0);
		Map<String, Object> dept_where = mapWhereList.get(1);
//		if ("year".equals(weidu_where.get("key")) && null != weidu_where.get("value")
//				&& !"".equals(weidu_where.get("value"))) {
//			String str = (String) weidu_where.get("value");
//			if ("period_type".equals(statistical_time_period_where.get("key")) && null != statistical_time_period_where.get("value")
//					&& !"".equals(statistical_time_period_where.get("value"))) {
//				if("0".equals(str)){
//					sb_where.append(" AND st.time_period like '").append(statistical_time_period_where.get("value")).append("'");
//				}else if("1".equals(str)){
//					sb_where.append(" AND st.year = '").append(String.valueOf(statistical_time_period_where.get("value")).replace("%", "")).append("'");
//				}
//			}
//		}
		if ("fromTime".equals(time_where.get("key")) && null != time_where.get("value")
				&& !"".equals(time_where.get("value"))) {
			sb_where.append(" AND te.frame_time like '").append(time_where.get("value")).append("'");
		}
		if ("businessGroupName".equals(dept_where.get("key")) && null != dept_where.get("value")
					&& !"".equals(dept_where.get("value"))) {
				sb_where.append(" AND te.businessGroup_name like '").append(dept_where.get("value")).append("'");
		}
//		if ("channelName".equals(channel_where.get("key")) && null != channel_where.get("value")
//				&& !"".equals(channel_where.get("value"))) {
//			sb_where.append(" AND te.channel_name like '").append(channel_where.get("value")).append("'");
//		}

		System.out.println(sb_where);
		map_result.put("pageinfo", obj_page);
		map_result.put("header", "目标值录入信息");
		map_result.put("data", targetEntryDao.getTargetEntryList(sb_where.toString(), obj_page,userDTO));
		map_result.put("userList",userDTO);
		return map_result;
	}

	@Override
	public Map<String, Object> getUserInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		UserManager userManager = (UserManager)SpringHelper.getBean("userManager");
		UserDTO userDTO = userManager.getCurrentUserDTO();
		result.put("data", userDTO);
		return result;
	}

	@Override
	public TargetEntry saveOrUpdateTargetEntry(TargetEntry targetEntry) {
		TargetEntryDao targetEntryDao = (TargetEntryDao) SpringHelper.getBean(TargetEntryDao.class.getName());
		TargetEntry saveTargetEntry = null;
		Map<String,Object> result = new HashMap<String,Object>();
		if (targetEntry.getId() != null) {
			saveTargetEntry = getTargetEntryByOriginId(targetEntry.getId());
		} else {
			saveTargetEntry = new TargetEntry();
		}
		try{
			saveTargetEntry.setBusinessGroup_code(targetEntry.getBusinessGroup_code());
			saveTargetEntry.setBusinessGroup_name(targetEntry.getBusinessGroup_name());
//			saveTargetEntry.setChannel_code(targetEntry.getChannel_code());
//			saveTargetEntry.setChannel_name(targetEntry.getChannel_name());
			saveTargetEntry.setMaori_target(targetEntry.getMaori_target());
			saveTargetEntry.setProfit_target(targetEntry.getProfit_target());
			saveTargetEntry.setUser_target(targetEntry.getUser_target());
			saveTargetEntry.setFrame_time(targetEntry.getFrame_time());
			preObject(saveTargetEntry);
			TargetEntryManager targetEntryManager = (TargetEntryManager) SpringHelper.getBean("targetEntryManager");
			if (targetEntry.getId() == null) {
				this.insertTargetEntry(saveTargetEntry);
			}else{
				targetEntryManager.saveObject(saveTargetEntry);
			}
			result = targetEntryDao.getByIdList(saveTargetEntry);
			saveTargetEntry.setId(Long.valueOf(result.get("id").toString()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return saveTargetEntry;
	}
	@Override
	public TargetEntry getTargetEntryById(Long id) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("id", id));
		TargetEntry TargetEntryDTO = new TargetEntry();
		if (list != null && list.size() > 0) {
			TargetEntry targetEntry = (TargetEntry) list.get(0);
			TargetEntryDTO.setId(targetEntry.getId());
			TargetEntryDTO.setBusinessGroup_code(targetEntry.getBusinessGroup_code());
			TargetEntryDTO.setBusinessGroup_name(targetEntry.getBusinessGroup_name());
//			TargetEntryDTO.setChannel_code(targetEntry.getChannel_code());
//			TargetEntryDTO.setChannel_name(targetEntry.getChannel_name());
			TargetEntryDTO.setMaori_target(targetEntry.getMaori_target());
			TargetEntryDTO.setProfit_target(targetEntry.getProfit_target());
			TargetEntryDTO.setUser_target(targetEntry.getUser_target());
			TargetEntryDTO.setFrame_time(targetEntry.getFrame_time());
     			return TargetEntryDTO;
		}
		return null;
	}

	@Override
	public TargetEntry getTargetEntryByOriginId(Long id) {
		List<?> list = this.getList(FilterFactory.getSimpleFilter("id", id));
		if (list != null && list.size() > 0) {
			TargetEntry targetEntry = (TargetEntry) list.get(0);
			 return targetEntry;
		}
		return null;
	}

	@Override
	public void insertTargetEntry(TargetEntry saveTargetEntry) {
		TargetEntryManager auditManager=(TargetEntryManager)SpringHelper.getBean("targetEntryManager");
		TargetEntry targetEntry = new TargetEntry();
		try {
			targetEntry.setBusinessGroup_code(saveTargetEntry.getBusinessGroup_code());
			targetEntry.setBusinessGroup_name(saveTargetEntry.getBusinessGroup_name());
//			targetEntry.setChannel_code(saveTargetEntry.getChannel_code());
//			targetEntry.setChannel_name(saveTargetEntry.getChannel_name());
			targetEntry.setMaori_target(saveTargetEntry.getMaori_target());
			targetEntry.setProfit_target(saveTargetEntry.getProfit_target());
			targetEntry.setUser_target(saveTargetEntry.getUser_target());
			targetEntry.setFrame_time(saveTargetEntry.getFrame_time());
			preObject(targetEntry);
			auditManager.saveObject(targetEntry);
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
	public Map<String, Object> getByTarget(String statistics,String deptName,String channelName,TargetEntry targetEntry) {
		Map<String,Object> result = new HashMap<String,Object>();
		TargetEntryDao targetEntryDao = (TargetEntryDao)SpringHelper.getBean(TargetEntryDao.class.getName());
		result = targetEntryDao.getStatisticsExist(statistics.replace("/", "-"),deptName,channelName);
		String flag = result.get("statistics").toString();
		if(flag.equals("0")){
			targetEntry = saveOrUpdateTargetEntry(targetEntry);
		}
		result.put("targetEntry",targetEntry);
		return result;
	}
}
