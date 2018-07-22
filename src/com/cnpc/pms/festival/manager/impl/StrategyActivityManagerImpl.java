package com.cnpc.pms.festival.manager.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.dao.StrategyActivityDao;
import com.cnpc.pms.festival.manager.StrategyActivityManager;
import com.cnpc.pms.utils.DateUtils;

import net.sf.json.JSONArray;

public class StrategyActivityManagerImpl extends BizBaseCommonManager implements StrategyActivityManager {

	@Override
	public Map<String, Object> queryStrategyGMV(String start_time,String end_time) {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryStrategyGMV(start_time,end_time);
		Map<String, Object> ylcgmvTemp = strategyActivityDao.queryYangLaoCanGmv(null, start_time, end_time);
		order_obj.put("ylc_total_gmv", ylcgmvTemp.get("ylc_total_gmv")==null?"0":ylcgmvTemp.get("ylc_total_gmv"));
    	return order_obj;
	}
	
	@Override
	public List<Map<String, Object>> queryUnStrategyGMV(String start_time,String end_time){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryUnStrategyGMV(start_time,end_time);
    	return lst_data;
	}

	@Override
	public Map<String, Object> queryNewMember(String store_no,String start_time,String end_time) {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryNewMember(store_no,start_time,end_time);
    	return order_obj;
	}
	
	@Override
	public Map<String, Object> queryTotalMember() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		Map<String, Object> order_obj = strategyActivityDao.queryTotalMember();
    	return order_obj;
	}

	@Override
	public List<Map<String, Object>> queryStoreCompleteInfo(String store_no){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryStoreCompleteInfo(store_no);
		String yestoday = DateUtils.lastDate();
		for(Map<String, Object> map : lst_data){
			store_no = String.valueOf(map.get("store_no"));
			Map<String, Object> temp = strategyActivityDao.queryStoreUser(store_no);
			if (temp != null) {
				map.put("store_user", temp.get("store_user"));
			}else{
				map.put("store_user", "");
			}
			Map<String, Object> memberTemp = strategyActivityDao.queryNewMember(store_no, yestoday, yestoday);
			map.put("member_num", memberTemp.get("membernum")==null?"0":memberTemp.get("membernum"));
			Map<String, Object> ylcgmvTemp = strategyActivityDao.queryYangLaoCanGmv(store_no, yestoday, yestoday);
			map.put("ylc_total_gmv", ylcgmvTemp.get("ylc_total_gmv")==null?"0":ylcgmvTemp.get("ylc_total_gmv"));
		}
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryDataOfScatterplot() {
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryDataOfScatterplot();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryYestodayGmvRanking(String dept_id,String order_by){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryYestodayGmvRanking(dept_id,order_by);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryYestodayMemberRanking(String order_by){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryYestodayMemberRanking(order_by);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryStoreRanking(String dept_id){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryStoreRanking(dept_id);
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryMemberTrend(){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryMemberTrend();
    	return lst_data;
	}
	
	@Override
	public List<Map<String, Object>> queryActivityScope(){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryActivityScope();
    	return lst_data;
	}

	@Override
	public List<Map<String, Object>> queryProductRanking(String dept_id,String store_no){
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryProductRanking(dept_id,store_no);
    	return lst_data;
	}
	
	@Override
	public Map<String, Object> queryTypeGmv() {
		/**
		 * @author wuxinxin
		 * 2018年7月16日
		 */
		StrategyActivityDao strategyActivityDao = (StrategyActivityDao)SpringHelper.getBean(StrategyActivityDao.class.getName());
		List<Map<String, Object>> lst_data = strategyActivityDao.queryTypeGMV();
		Map<String, Object> result = new HashMap<String, Object>();
		String yestoday = DateUtils.lastDate();
		String selDate = "";
		//商品类
		List productCounts = new ArrayList();
		//服务类
		List serviceCounts = new ArrayList();
		//团购
		List froupCounts = new ArrayList();
		//日期
		List dateMemCounts = new ArrayList();
		try {
			//生成X轴坐标
			dateMemCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int tmp = 0; 
		for(int i = 0; i < lst_data.size(); i++){
			if(i%3==0)productCounts.add(lst_data.get(i).get("cou").toString());
			if(i%3==1)serviceCounts.add(lst_data.get(i).get("cou").toString());
			if(i%3==2)froupCounts.add(lst_data.get(i).get("cou").toString());
		}
        JSONArray jsonproductCounts = (JSONArray) JSONArray.fromObject(productCounts);
        JSONArray jsonserviceCounts = (JSONArray) JSONArray.fromObject(serviceCounts);
        JSONArray jsonfroupCounts = (JSONArray) JSONArray.fromObject(froupCounts);
        JSONArray jsonDateMem = (JSONArray) JSONArray.fromObject(dateMemCounts);
		result.put("jsonproductCounts", jsonproductCounts);
		result.put("jsonserviceCounts", jsonserviceCounts);
		result.put("jsonfroupCounts", jsonfroupCounts);
		result.put("jsonDateMem", jsonDateMem);
    	return result;
		
	}
	
	// 生成X轴坐标
	public List reDate(int dd) throws ParseException {
		DateFormat f = new SimpleDateFormat("MM-dd");

		Date today = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(today);
		c.add(Calendar.DAY_OF_MONTH, -dd);
		List dateX = new ArrayList();
		dateX.add(f.format(c.getTime()));
		for (int i = dd; i > 1; i--) {
			c.add(Calendar.DAY_OF_MONTH, 1);
			dateX.add(f.format(c.getTime()));
		}
		return dateX;
	}
	
}
