package com.cnpc.pms.platform.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.communeMember.dao.CommuneMemberDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.entity.DistCity;
import com.cnpc.pms.platform.dao.PlatformStoreDao;
import com.cnpc.pms.platform.manager.CommuneMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 社员操作
 * 
 * @author wuxinxin 2018年5月17日
 */
public class CommuneMemberImpl extends BizBaseCommonManager implements CommuneMember {


	@Override
	public Map<String, Object> selectCitys(String dd) {
		/**
		 * @author wuxinxin
		 * 2018年7月19日
		 */
		Map<String, Object> result = new HashMap<String, Object>();
		/*UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		List<DistCity> citys = userManager.getCurrentUserCity();*/
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		StoreDao storeDao = (StoreDao) SpringHelper.getBean(StoreDao.class.getName());
		String cityno = "";
		if(dd!=null&&!dd.substring(0,1).equals("0")){
				cityNO = storeDao.getCityNOOfCityById(Long.parseLong(dd));
				cityno = cityNO.get(0).get("cityno").toString();
			}
			result.put("selcitycode", cityno);
			return result;
		
	}
	
	@Override
	public Map<String, Object> selectAllCm(String dd) {
		/**
		 * @author wuxinxin 2018年5月17日
		 */
		//查询城市id
		
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		StoreDao storeDao = (StoreDao) SpringHelper.getBean(StoreDao.class.getName());
		String cityno = "";
		if(!"0000".equals(dd)) {
			if(dd!=null&&!dd.substring(0,1).equals("0")){
				cityNO = storeDao.getCityNOOfCityById(Long.parseLong(11+""));
				cityno = cityNO.get(0).get("cityno").toString();
				if(cityno.substring(0,2).equals("00")) {
					dd=cityno.replace("00", "0");
				}else {
					dd=cityno;
				}
			}
		}
		
		
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		PlatformStoreDao cmDao = (PlatformStoreDao) SpringHelper.getBean(PlatformStoreDao.class.getName());
		CommuneMemberDao commDao = (CommuneMemberDao) SpringHelper.getBean(CommuneMemberDao.class.getName());
        //查询城市名
        if (!"0000".equals(dd)) {
            if(dd.length()<4){
                cityno="0"+dd;
            }else {
            	cityno=dd;
            }
            List<Map<String, Object>> selCityList = new ArrayList<Map<String, Object>>();
            selCityList = commDao.getSelCity(cityno);
            if (selCityList != null && selCityList.size() > 0) {
                result.put("selcityname", "("+selCityList.get(0).get("cityname")+")");
            }else {
            	result.put("selcityname", "");
            }
        }
		// 查询e店数量---必须
		List<Map<String, Object>> eShopCountList = new ArrayList<Map<String, Object>>();
		eShopCountList = cmDao.getEshopCount(dd);
		if (eShopCountList != null && eShopCountList.size() > 0) {
			result.put("eShopCount", eShopCountList.get(0).get("cou"));
		} else {
			result.put("eShopCount", "0");
		}
		//查询当日成交量、成交额  ----必须
		List<Map<String, Object>> dayDealList = new ArrayList<Map<String, Object>>();
		dayDealList = commDao.getDayDealCount(dd);
		if(dayDealList!= null&&!dayDealList.isEmpty()) {
			result.put("dayDealCount", dayDealList.get(0).get("cou"));
			result.put("dayDealSum", dayDealList.get(0).get("dealsum"));
		}else {
			result.put("dayDealCount", "0");
			result.put("dayDealSum", "0");
		}
		// 查询SKU数量---必须
		List<Map<String, Object>> skuCountList = new ArrayList<Map<String, Object>>();
		skuCountList = cmDao.getGoodsTypeCount(dd);
		if (skuCountList!= null&&!skuCountList.isEmpty()) {
			result.put("skuCount", skuCountList.get(0).get("cou"));
		} else {
			result.put("skuCount", "0");
		}

		// 查询社员总量---必须
		List<Map<String, Object>> cmCountList = new ArrayList<Map<String, Object>>();
		cmCountList = commDao.getAllCount(dd);
		if (cmCountList != null && cmCountList.size() > 0) {
			result.put("cmCount", cmCountList.get(0).get("allCount"));
		} else {
			result.put("cmCount", "0");
		}
		//查询当日新增社员数量---必须
		List<Map<String, Object>> dayAddMemList = commDao.getDayaddMemCount(dd);
		if (dayAddMemList != null && dayAddMemList.size() > 0) {
			result.put("dayAddMem", dayAddMemList.get(0).get("cou"));
		} else {
			result.put("dayAddMem", "0");
		}
		// cmGoodsTurnoverList = cmDao.getCmGoodsTurnover(dd);
		//////////////////////// 分库查询成交信息//////////////////////////////
		/*
		 * //第一步：查询所有社员ids； StringBuffer ids = new StringBuffer(); ids.append("'");
		 * ids.append(commDao.getAllCmIds(dd)); ids.append("'"); //查询id产生的订单条数； //查询成交量
		 * List<Map<String, Object>> cmGoodsDealCountList = new
		 * ArrayList<Map<String,Object>>(); cmGoodsDealCountList =
		 * cmDao.getCmGoodsDealCount(ids.toString());
		 * if(cmGoodsDealCountList!=null&&cmGoodsDealCountList.size()>0){
		 * result.put("cmGoodsDealCount", cmGoodsDealCountList.get(0).get("cou")); }else
		 * { result.put("cmGoodsDealCount", "0"); } //查询成交额 cmGoodsTurnoverList =
		 * cmDao.getCmGoodsTurnover(ids.toString());
		 * if(cmGoodsTurnoverList!=null&&cmGoodsTurnoverList.size()>0){
		 * result.put("cmGoodsTurnover", cmGoodsTurnoverList.get(0).get("cou")); }else {
		 * result.put("cmGoodsTurnover", "0"); }
		 */
		//////////////////////// 分库查询成交信息end//////////////////////////////
		// 查询成交量、查询成交额--必须
		List<Map<String, Object>> cmGoodsDealCountList = new ArrayList<Map<String, Object>>();
		cmGoodsDealCountList = commDao.getCmGoodsDealCount(dd);
		if (cmGoodsDealCountList != null && cmGoodsDealCountList.size() > 0) {
			result.put("cmGoodsDealCount", cmGoodsDealCountList.get(0).get("cou"));
		} else {
			result.put("cmGoodsDealCount", "0");
		}
		// 查询成交额
        List<Map<String, Object>> cmGoodsTurnoverList = new ArrayList<Map<String, Object>>();
        cmGoodsTurnoverList =commDao.getCmGoodsTurnover(dd);
        if (cmGoodsTurnoverList != null && cmGoodsTurnoverList.size() > 0) {
            result.put("cmGoodsTurnover", cmGoodsTurnoverList.get(0).get("cou"));
        } else {
            result.put("cmGoodsTurnover", "0");
        }
		// 查询动销商品--必须
		List<Map<String, Object>> movingPinCountList = new ArrayList<Map<String, Object>>();
		movingPinCountList = commDao.getMovingPinCount(dd);
		if (movingPinCountList != null && movingPinCountList.size() > 0) {
			result.put("movingPinCount", movingPinCountList.get(0).get("cou"));
		} else {
			result.put("movingPinCount", "0");
		}

		// -------------------查询新增量 end----------------//
		

		/**************************************************************
		 ******************** 第二版：社员注册信息统计*************************
		 **************************************************************/
		

		
		
		//2018*-06-12//////////////////////////////////////
		
		
		
		//查询城市非社员成交额
		List<Map<String, Object>> noMemCityList = commDao.getDayOfEshopNmemSumCity(dd);
		List noMemCitySums = new ArrayList();
		if (noMemCityList!=null&&!noMemCityList.isEmpty()) {
			for (int i = 0; i < noMemCityList.size(); i++) {
				noMemCitySums.add(noMemCityList.get(i).get("citypri").toString());
				noMemCitySums.add(noMemCityList.get(i).get("cname").toString());
			}
		}
		JSONArray jsonNoMemCitySums = (JSONArray) JSONArray.fromObject(noMemCitySums);
		result.put("jsonNoMemCitySums", jsonNoMemCitySums);
		
		
		//查询平均客单价,周消费频次----getEshopWeekCount
		List<Map<String, Object>> eshopWeekList = commDao.getEshopWeekCount(dd);
		//平均客单价=周成交额/周订单量
		List eshopWeekDeal = new ArrayList();
		//周消费频次=周订单量/社员总数
		//1.查询活跃社员数
/*		int memWeek = 10;
		List<Map<String, Object>> memWeekList = commDao.getMemweekCount(dd);
		if(memWeekList!=null&&!memWeekList.isEmpty()) { 
			memWeek = Integer.parseInt(memWeekList.get(0).get("memcou").toString());
		}*/
		List eshopWeekCount = new ArrayList();
		int memCou = Integer.parseInt(cmCountList.get(0).get("allCount").toString());
		if (eshopWeekList!=null&&!eshopWeekList.isEmpty()) {
			for (int i = 0; i < eshopWeekList.size(); i++) {
				int eshopCou = Integer.parseInt(eshopWeekList.get(i).get("eweekcou").toString());
				Double eshopGmv = Double.parseDouble(eshopWeekList.get(i).get("eweekgmv").toString());
				if(eshopCou!=0) {
					eshopWeekDeal.add(String.format("%.2f",eshopGmv/eshopCou));
				}else {
					eshopWeekDeal.add("0.0");

				}
				if(memCou!=0){
                    eshopWeekCount.add(String.format("%.2f",Double.parseDouble(eshopCou/memCou+"")));
                }else{
                    eshopWeekCount.add("0");
                }
			}
		}
		JSONArray jsonEshopWeekDeal = (JSONArray) JSONArray.fromObject(eshopWeekDeal);
		JSONArray jsonEshopWeekCou = (JSONArray) JSONArray.fromObject(eshopWeekCount);
		result.put("jsonEshopWeekDeal", jsonEshopWeekDeal);
		result.put("jsonEshopWeekCou", jsonEshopWeekCou);
		
		
		
		// 获取e店社员、非社员总成交额
		List<Map<String, Object>> allEshopSumList = new ArrayList<Map<String, Object>>();
		allEshopSumList = commDao.getAllEshopSum(dd);
		if (allEshopSumList != null && allEshopSumList.size() > 0) {
			result.put("allEshopSum", allEshopSumList.get(0).get("cou"));
		} else {
			result.put("allEshopSum", "0");
		}
		// 获取e店社员成交额
		List<Map<String, Object>> yesEshopSumList = new ArrayList<Map<String, Object>>();
		yesEshopSumList = commDao.getYesEshopSum(dd);
		if (yesEshopSumList != null && yesEshopSumList.size() > 0) {
			result.put("yesEshopSum", yesEshopSumList.get(0).get("cou"));
		} else {
			result.put("yesEshopSum", "0");
		}
		// 获取e店非社员成交额
		List<Map<String, Object>> noEshopSumList = new ArrayList<Map<String, Object>>();
		noEshopSumList = commDao.getNoEshopSum(dd);
		if (noEshopSumList != null && noEshopSumList.size() > 0) {
			result.put("noEshopSum", noEshopSumList.get(0).get("cou"));
		} else {
			result.put("noEshopSum", "0");
		}

		//查询订单时效性


        List selcount = new ArrayList();
		List seltimes = new ArrayList();
		List<Map<String, Object>> orderTimesList = commDao.getTimeDiff(dd);
		long scount0 =0;//提前配送
		long scounthalf =0;//半小时送达
		long scount1 =0;//1小时内
		long scount2 =0;//2小时以内
		long scount4 =0;//2-4小时
		long scount12 =0;//4-12小时
		long scount24 =0;//12-24小时
		long scount25 =0;//大于24小时
		if(orderTimesList!=null&&!orderTimesList.isEmpty()) { 
			for (int i = 0; i < orderTimesList.size(); i++) {
				int stime = Integer.parseInt(orderTimesList.get(i).get("seltimes").toString());
				if(0>stime) {
					scount0 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(0<=stime&&1>stime){
					scounthalf +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(1<=stime&&2>stime){
					scount1 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(2<=stime&&4>stime){
					scount2 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(4<=stime&&8>stime){
					scount4 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(8<=stime&&24>stime){
					scount12 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(24<=stime&&48>stime){
					scount24 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}else if(48<=stime){
					scount25 +=Integer.parseInt(orderTimesList.get(i).get("selcount").toString());
				}
			}
		}
		selcount.add(scount0);
		selcount.add(scounthalf);
		selcount.add(scount1);
		selcount.add(scount2);
		selcount.add(scount4);
		selcount.add(scount12);
		selcount.add(scount24);
		selcount.add(scount25);
		seltimes.add("提前配送");
		seltimes.add("2小时以内");
		seltimes.add("2-4小时");
		seltimes.add("4-12小时");
		seltimes.add("12-24小时");
		seltimes.add("大于24小时");
		JSONArray jsonSelCount = (JSONArray) JSONArray.fromObject(selcount);
		JSONArray jsonSelTimes = (JSONArray) JSONArray.fromObject(seltimes);
		result.put("jsonSelCount", jsonSelCount);
		result.put("jsonSelTimes", jsonSelTimes);
		
		
		//查询24小时内订单成交量
		List<Map<String, Object>> hourCountList = new ArrayList<Map<String, Object>>();
		hourCountList = commDao.getHourCount(dd);
		List hourCounts = new ArrayList();
		List selHours = new ArrayList();
		if (hourCountList != null && !hourCountList.isEmpty()) {
			for (Map<String, Object> hourCount : hourCountList) {
				selHours.add(hourCount.get("seltime").toString());
				hourCounts.add(hourCount.get("cou").toString());
			}
		}
		JSONArray jsonSelHours = (JSONArray) JSONArray.fromObject(selHours);
		JSONArray jsonHourCounts = (JSONArray) JSONArray.fromObject(hourCounts);
		result.put("jsonSelHours", jsonSelHours);
		result.put("jsonHourCounts", jsonHourCounts);
		
		
		
		//查询订单商品分类
		List<Map<String, Object>> orderTypeList = new ArrayList<Map<String, Object>>();
		orderTypeList = commDao.getOrderType(dd);
		List orderTypeNames = new ArrayList();
		List orderTypeSums = new ArrayList();
		if (orderTypeList != null && orderTypeList.size() > 0) {
			for (Map<String, Object> orderType : orderTypeList) {
				orderTypeSums.add(orderType.get("ordertypecou"));
				orderTypeNames.add(orderType.get("ordertypename"));
			}
		} 
		JSONArray jsonOrderTypeNames = (JSONArray) JSONArray.fromObject(orderTypeNames);
		JSONArray jsonOrderTypeSums = (JSONArray) JSONArray.fromObject(orderTypeSums);
		result.put("jsonOrderTypeNames", jsonOrderTypeNames);
		result.put("jsonOrderTypeSums", jsonOrderTypeSums);



		//查询城市当日新增社员数量
        List<Map<String, Object>> cityDayAddList = new ArrayList<Map<String, Object>>();
        cityDayAddList = commDao.getDayCityaddMemCount(dd);
        List cityNames = new ArrayList();
        List cityAdds = new ArrayList();
        for(Map<String, Object> addCity : cityDayAddList){
            if (addCity.get("cityname").toString().contains("黔东南")) {
                cityNames.add("黔东南洲");

            }else{
                cityNames.add(addCity.get("cityname"));
            }
            cityAdds.add(addCity.get("citycou"));
        }
        JSONArray jsonCityNames = (JSONArray) JSONArray.fromObject(cityNames);
        JSONArray jsonCityAdds = (JSONArray) JSONArray.fromObject(cityAdds);
        result.put("jsonCityNames", jsonCityNames);
        result.put("jsonCityAdds", jsonCityAdds);



		List dateXCounts = new ArrayList();
		try {
			//生成X轴坐标
			dateXCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonDateX = (JSONArray) JSONArray.fromObject(dateXCounts);
		result.put("jsonDateX", jsonDateX);
		return result;

	}

	@Override
	public Map<String, Object> selectMeSum(String dd) {
		/**
		 * @author wuxinxin 2018年5月17日
		 */
		
		
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		StoreDao storeDao = (StoreDao) SpringHelper.getBean(StoreDao.class.getName());
		String cityno = "";
		if(!"0000".equals(dd)) {
			if(dd!=null&&!dd.substring(0,1).equals("0")){
				cityNO = storeDao.getCityNOOfCityById(Long.parseLong(dd));
				cityno = cityNO.get(0).get("cityno").toString();
				if(cityno.substring(0,2).equals("00")) {
					dd=cityno.replace("00", "0");
				}
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		PlatformStoreDao cmDao = (PlatformStoreDao) SpringHelper.getBean(PlatformStoreDao.class.getName());
		CommuneMemberDao commDao = (CommuneMemberDao) SpringHelper.getBean(CommuneMemberDao.class.getName());
		
		//查询7日成交额、订单量
		List<Map<String, Object>> day7List = commDao.getDay7DealCount(dd);
		List alldealsums = new ArrayList();
		List alldealcounts = new ArrayList();	
		for (int i = 0; i < day7List.size(); i++) {
				alldealsums.add(Double.valueOf(day7List.get(i).get("alldealsum").toString()).intValue());
				alldealcounts.add(day7List.get(i).get("alldealcount").toString());
		}

		JSONArray jsonAllDealsums = (JSONArray) JSONArray.fromObject(alldealsums);
		JSONArray jsonAllDealCounts = (JSONArray) JSONArray.fromObject(alldealcounts);
		result.put("jsonAllDealsums", jsonAllDealsums);
		result.put("jsonAllDealCounts", jsonAllDealCounts);
		
		// 查询已成交订单量

		// 查询取消订单量
		// 查询7日取消订单走势
		// 查询7日成交订单走势、成交额走势、非社员订单量、非社员成交额
		List<Map<String, Object>> eshopMemList = commDao.getEshopMemCount(dd);
		
		
		
		
		
		List memCounts = new ArrayList();// 社员成交量
		List memSums = new ArrayList();// 社员成交额
		List noMemCounts = new ArrayList();// 非社员成交量
		List noMemSums = new ArrayList();// 非社员成交额
		List eshopCounts = new ArrayList();// 安心合作社成交量
		List eshopSums = new ArrayList();// 安心合作社成交额
		List eshopOneDeal = new ArrayList();// 客单价

		if (eshopMemList != null && !eshopMemList.isEmpty()) {
			for (int i = 0; i < eshopMemList.size(); i++) {
				memCounts.add(Integer.parseInt(eshopMemList.get(i).get("memcount").toString()));
				memSums.add(eshopMemList.get(i).get("memgmv").toString().split("\\.")[0]);
				noMemCounts.add(Integer.parseInt(eshopMemList.get(i).get("nmemcount").toString()));
				noMemSums.add(eshopMemList.get(i).get("nmemgmv").toString().split("\\.")[0]);
				eshopCounts.add(Integer.parseInt(eshopMemList.get(i).get("eshopcou").toString()));
				eshopSums.add(eshopMemList.get(i).get("eshopgmv").toString().split("\\.")[0]);
				int eshopCou = Integer.parseInt(eshopMemList.get(i).get("eshopcou").toString());
				Double eshopGmv = Double.parseDouble(eshopMemList.get(i).get("eshopgmv").toString());
				if (eshopCou != 0) {
					eshopOneDeal.add(String.format("%.2f", eshopGmv / eshopCou));
				} else {
					eshopOneDeal.add("0.0");
				}

			}
		}
		/*
		 * List<Map<String, Object>> memCountList = commDao.getDayOfEshopMemCount(dd);
		 * if (!memCountList.isEmpty()) { for (int i = 0; i < memCountList.size(); i++)
		 * {
		 * memCounts.add(Integer.parseInt(memCountList.get(i).get("dayOfEshopMemCount").
		 * toString())); } }
		 * 
		 * //查询7日社员成交额走势 List<Map<String, Object>> memSumList =
		 * commDao.getDayOfEshopMemSum(dd); if (!memSumList.isEmpty()) { for (int i = 0;
		 * i < memSumList.size(); i++) {
		 * memSums.add(Double.valueOf(memSumList.get(i).get("dayOfEshopMemSum").toString
		 * ()).intValue()); } } //查询7日非社员订单量 List<Map<String, Object>> noMemCountsList =
		 * commDao.getDayOfEshopNmemCount(dd); if (!noMemCountsList.isEmpty()) { for
		 * (int i = 0; i < noMemCountsList.size(); i++) {
		 * noMemCounts.add(Double.valueOf(noMemCountsList.get(i).get(
		 * "dayOfEshopNmemCount").toString()).intValue()); }
		 * 
		 * } //查询7日非社员成交额 List<Map<String, Object>> noMemSumList =
		 * commDao.getDayOfEshopNmemSum(dd); if (!noMemSumList.isEmpty()) { for (int i =
		 * 0; i < noMemSumList.size(); i++) {
		 * noMemSums.add(Double.valueOf(noMemSumList.get(i).get("dayOfEshopNmemSum").
		 * toString()).intValue()); }
		 * 
		 * }
		 */
		JSONArray jsonDayMemCounts = (JSONArray) JSONArray.fromObject(memCounts);
		JSONArray jsonDayMemSums = (JSONArray) JSONArray.fromObject(memSums);
		JSONArray jsonDayNoMemCounts = (JSONArray) JSONArray.fromObject(noMemCounts);
		JSONArray jsonDayNoMemSums = (JSONArray) JSONArray.fromObject(noMemSums);
		JSONArray jsonEshopCounts = (JSONArray) JSONArray.fromObject(eshopCounts);
		JSONArray jsonEshopSums = (JSONArray) JSONArray.fromObject(eshopSums);
		JSONArray jsonEshopOneDeal = (JSONArray) JSONArray.fromObject(eshopOneDeal);
		result.put("jsonEshopOneDeal", jsonEshopOneDeal);
		result.put("jsonEshopCounts", jsonEshopCounts);
		result.put("jsonEshopSums", jsonEshopSums);
		result.put("jsonDayMemCounts", jsonDayMemCounts);
		result.put("jsonDayMemSums", jsonDayMemSums);
		result.put("jsonDayNoMemCounts", jsonDayNoMemCounts);
		result.put("jsonDayNoMemSums", jsonDayNoMemSums);
		
		
		
		
		
		//查询取消订单数
		List<Map<String, Object>> eshopQuitMemList = commDao.getEshopQuitCount(dd);
		List memQuitCounts = new ArrayList();//取消成交量
		if (eshopQuitMemList!=null&&!eshopQuitMemList.isEmpty()) {
			for (int i = 0; i < eshopQuitMemList.size(); i++) {
				memQuitCounts.add(Integer.parseInt(eshopQuitMemList.get(i).get("memcount").toString()));
			}
		}
		JSONArray jsonQuitCounts = (JSONArray) JSONArray.fromObject(memQuitCounts);
		result.put("jsonQuitCounts", jsonQuitCounts);
		
		//按城市查询累计成交额、订单量
		List cityShopCounts = new ArrayList();
		List cityShopNames = new ArrayList();
		List cityShopSums = new ArrayList();
		List<Map<String, Object>> cityshopList = commDao.getEshopNmemCouCity(dd);
		if (cityshopList!=null&&!cityshopList.isEmpty()) {
			for (int i = 0; i < cityshopList.size(); i++) {
				cityShopSums.add(cityshopList.get(i).get("eshopgmv").toString().split("\\.")[0]);
				cityShopCounts.add(cityshopList.get(i).get("eshopcou").toString());
				if("乌兰察布市".equals(cityshopList.get(i).get("cname").toString())) {
					cityShopNames.add("乌兰察布");
				}else {
					cityShopNames.add(cityshopList.get(i).get("cname").toString());
				}
			}
		}
		JSONArray jsonCityShopSums = (JSONArray) JSONArray.fromObject(cityShopSums);
		JSONArray jsonCityShopCounts = (JSONArray) JSONArray.fromObject(cityShopCounts);
		JSONArray jsonCityShopNames = (JSONArray) JSONArray.fromObject(cityShopNames);
		result.put("jsonCityShopSums", jsonCityShopSums);
		result.put("jsonCityShopCounts", jsonCityShopCounts);
		result.put("jsonCityShopNames", jsonCityShopNames);
		//查询当日城市成交额
		List memCityCounts = new ArrayList();
		List<Map<String, Object>> memCityList = commDao.getDayOfEshopMemSumCity(dd);
		if (memCityList!=null&&!memCityList.isEmpty()) {
			for (int i = 0; i < memCityList.size(); i++) {
				memCityCounts.add(memCityList.get(i).get("citypri").toString());
			}
		}
		JSONArray jsonCityMemCounts = (JSONArray) JSONArray.fromObject(memCounts);
		JSONArray jsonCityMemNames = (JSONArray) JSONArray.fromObject(cityShopNames);
		result.put("jsonCityMemCounts", jsonCityMemCounts);
		
		
		
		
		
		
		
		// 查询老用户转社员总量
		List<Map<String, Object>> oldCmCountList = new ArrayList<Map<String, Object>>();
		oldCmCountList = commDao.getOldCount(dd);
		if (oldCmCountList != null && oldCmCountList.size() > 0) {
			result.put("oldCmCount", oldCmCountList.get(0).get("oldCount"));
		} else {
			result.put("oldCmCount", "0");
		}
		// 查询新开社员总量
		List<Map<String, Object>> newCmCountList = new ArrayList<Map<String, Object>>();
		newCmCountList = commDao.getNewCount(dd);

		if (newCmCountList != null && newCmCountList.size() > 0) {
			result.put("newCmCount", newCmCountList.get(0).get("newCount"));
		} else {
			result.put("newCmCount", "0");
		}
		
		// -------------------查询新增量start----------------//
		List newCounts = new ArrayList();
		List allCounts = new ArrayList();
		List dateMemCounts = new ArrayList();

		List<Map<String, Object>> cmAllGrowList = commDao.getCmAllGrow(dd);
		try {
			//生成X轴坐标
			dateMemCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}

        List<Map<String, Object>> cmAllList = commDao.getAllMembers(dd);

        if (cmAllList == null || cmAllList.size() < 2) {
            for (int i = 0; i < 10; i++) {
                // 添加新社员
                newCounts.add(i + i * i);
                // 总社员
                allCounts.add(i + i * i + i + i * i * 2);
            }

        } else {
            for (int i = 0; i < cmAllList.size(); i++) {
                // 添加新增社员
                if (i >= cmAllGrowList.size()) {
                    newCounts.add(0);
                } else {
                    newCounts.add(Integer.parseInt(cmAllGrowList.get(i).get("allcount").toString()));
                }
                // 总社员
                allCounts.add(Integer.parseInt(cmAllList.get(i).get("allcount").toString()));

            }

        }

        JSONArray jsonNew = (JSONArray) JSONArray.fromObject(newCounts);
        JSONArray jsonAllCounts = (JSONArray) JSONArray.fromObject(allCounts);
        JSONArray jsonDateMem = (JSONArray) JSONArray.fromObject(dateMemCounts);
        List dateMonCounts = new ArrayList();
        List newMonCounts = new ArrayList();
        List allMonCounts = new ArrayList();
		try {
			//生成30天X轴坐标Old
            dateMonCounts = reDate(30);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int total = 0;
		//查询30天前累计数据
        List<Map<String, Object>> cmMonGrowList = commDao.getMonGrowMembers(dd);
        List<Map<String, Object>> cmOldAllList = commDao.getOldAllMembers(dd);
        if (cmOldAllList == null || cmOldAllList.size()>0) {
            total = Integer.parseInt(cmOldAllList.get(0).get("oldcount").toString());
        }

		if(cmMonGrowList!=null&&cmMonGrowList.size()>0){
            if(cmMonGrowList.size()==30){
                //30天每天有新增
                for (int i = 0; i < cmMonGrowList.size(); i++) {
                    int temp = Integer.parseInt(cmMonGrowList.get(i).get("newcount").toString());
                    newMonCounts.add(temp);
                    total += temp;
                    allMonCounts.add(total);
                }

            }else{ 
            	int m=0;
            	for (int j = 0;j< dateMonCounts.size();j++) {
            		int temp2=0;
            		if(m<cmMonGrowList.size()) {
                		if( cmMonGrowList.get(m).get("mondate").toString().contains(dateMonCounts.get(j).toString())) {
               			 temp2 = Integer.parseInt(cmMonGrowList.get(m).get("newcount").toString());
                            m++;
                		}
            		}

            		 newMonCounts.add(temp2);
                     total += temp2;
                     allMonCounts.add(total);
            		
				}
                    //当中某天未增加社员
            }
        }

		JSONArray jsonNewMonCounts = (JSONArray) JSONArray.fromObject(newMonCounts);
		JSONArray jsonAllMonCounts = (JSONArray) JSONArray.fromObject(allMonCounts);
		JSONArray jsonMonMem = (JSONArray) JSONArray.fromObject(dateMonCounts);

		result.put("jsonNewMonCounts", jsonNewMonCounts);
		result.put("jsonAllMonCounts", jsonAllMonCounts);
		result.put("growNewCounts", jsonNew);
		result.put("growAllCounts", jsonAllCounts);
		result.put("jsonDateMem", jsonDateMem);
		result.put("jsonDateMon", dateMonCounts);

		// 查询男女比例
				List<Map<String, Object>> cmSexList = new ArrayList<Map<String, Object>>();
				cmSexList = commDao.getCmSexRatios(dd);

				if (cmSexList != null && cmSexList.size() > 0) {
					result.put("mCount", cmSexList.get(0).get("mCount"));
					result.put("fCount", cmSexList.get(0).get("fCount"));
				} else {
					result.put("oldCmCount", "0");
				}
				// 查询男女比例
				List<Map<String, Object>> cmAgeList = new ArrayList<Map<String, Object>>();
				cmAgeList = commDao.getCmAgeRatios(dd);

				if (cmAgeList != null && cmAgeList.size() > 0) {

					result.put("age60", cmAgeList.get(0).get("age60"));
					result.put("age70", cmAgeList.get(0).get("age70"));
					result.put("age80", cmAgeList.get(0).get("age80"));
					result.put("age90", cmAgeList.get(0).get("age90"));
					result.put("age00", cmAgeList.get(0).get("age00"));
					result.put("ageNow", cmAgeList.get(0).get("ageNow"));
				} else {
					result.put("oldCmCount", "0");
				}
				// 查询社员生日分布
				List<Map<String, Object>> birList = commDao.getCmBirthday(dd);// getCmBirthday
				for (int i = 0; i < birList.size(); i++) {
					result.put("birCount" + (i + 1), birList.get(i).get("birCount").toString());
				}
				// 查询社员户籍分布
				List<Map<String, Object>> areaList = commDao.getMembersArea(dd);// getCmBirthday
				if (areaList != null&&areaList.size()>0) {
					result.put("mePro", areaList.get(0).get("mePro").toString());
					result.put("meCount", areaList.get(1).get("meCount").toString());

				}
				// 查询社员粮票剩余前5
				
				List<Map<String, Object>> haveRebateList = cmDao.getHaveRebate(dd);
				List haveRebatePhone = new ArrayList();
				List haveRebateCou = new ArrayList();
				if (haveRebateList!=null&&!haveRebateList.isEmpty()) {

					for (int i = 0; i < haveRebateList.size(); i++) {
						haveRebatePhone.add(haveRebateList.get(i).get("tcphone").toString());
						haveRebateCou.add(haveRebateList.get(i).get("cou").toString());

					}
					JSONArray haveRebatePhones = (JSONArray) JSONArray.fromObject(haveRebatePhone);
					JSONArray haveRebateCous = (JSONArray) JSONArray.fromObject(haveRebateCou);

					result.put("haveRebatePhones", haveRebatePhones);
					result.put("haveRebateCous", haveRebateCous);
				}
				
				// 查询社员省钱情况
				List<Map<String, Object>> allRetrenchList = cmDao.getAllRetrench(dd);
				if (allRetrenchList!=null&&!allRetrenchList.isEmpty()) {
					for (Map<String, Object> allRetrenchmap : allRetrenchList) {
						result.put("sumall", Double.parseDouble(allRetrenchmap.get("sumall").toString()));
						result.put("subprice", Double.parseDouble(allRetrenchmap.get("subprice").toString()));
						result.put("usedrebate", Double.parseDouble(allRetrenchmap.get("usedrebate").toString()));
					}
				}else {
					result.put("sumall", "0");
					result.put("subprice", "0");
					result.put("usedrebate", "0");
				}
				// 查询粮票情况
				List<Map<String, Object>> allRebateList = cmDao.getAllRebate(dd);
				if (allRebateList!=null&&!allRebateList.isEmpty()) {
					for (Map<String, Object> allRebatemap : allRebateList) {
						result.put("sumreall", Double.parseDouble(allRebatemap.get("sumreall").toString()));
						result.put("sumhavere", Double.parseDouble(allRebatemap.get("sumhavere").toString()));
						result.put("sumused", Double.parseDouble(allRebatemap.get("sumused").toString()));
					}
				}else {
					result.put("sumreall", "0");
					result.put("sumhavere", "0");
					result.put("sumused", "0");
				}
				
				// 查询社员省钱排行前5
				List<Map<String, Object>> retrenchList = cmDao.getRetrenchMoney(dd);
				List retrenchPhone = new ArrayList();
				List retrenchCou = new ArrayList();
				if (retrenchList!=null&&!retrenchList.isEmpty()) {

					for (int i = 0; i < retrenchList.size(); i++) {
						retrenchPhone.add(retrenchList.get(i).get("tcphone").toString());
						retrenchCou.add(retrenchList.get(i).get("cou").toString());

					}
					JSONArray retrenchPhones = (JSONArray) JSONArray.fromObject(retrenchPhone);
					JSONArray retrenchCous = (JSONArray) JSONArray.fromObject(retrenchCou);

					result.put("retrenchPhones", retrenchPhones);
					result.put("retrenchCous", retrenchCous);
				}
				
				
				// 查询社员粮票累计前5
				
				List<Map<String, Object>> rebateList = cmDao.getSumRebate(dd);
				List rebatePhone = new ArrayList();
				List rebateCou = new ArrayList();
				if (rebateList!=null&&!rebateList.isEmpty()) {

					for (int i = 0; i < rebateList.size(); i++) {
						rebatePhone.add(rebateList.get(i).get("tcphone").toString());
						rebateCou.add(rebateList.get(i).get("cou").toString());

					}
					JSONArray rebatePhones = (JSONArray) JSONArray.fromObject(rebatePhone);
					JSONArray rebateCous = (JSONArray) JSONArray.fromObject(rebateCou);

					result.put("rebatePhones", rebatePhones);
					result.put("rebateCous", rebateCous);
				}
				// 查询社员粮票使用排行前5
				List<Map<String, Object>> usedRebateList = cmDao.getUsedRebate(dd);
				List usedRebatePhone = new ArrayList();
				List usedRebateCou = new ArrayList();
				if (usedRebateList!=null&&!usedRebateList.isEmpty()) {

					for (int i = 0; i < usedRebateList.size(); i++) {
						usedRebatePhone.add(usedRebateList.get(i).get("tcphone").toString());
						usedRebateCou.add(usedRebateList.get(i).get("cou").toString());

					}
					JSONArray usedRebatePhones = (JSONArray) JSONArray.fromObject(usedRebatePhone);
					JSONArray usedRebateCous = (JSONArray) JSONArray.fromObject(usedRebateCou);

					result.put("usedRebatePhones", usedRebatePhones);
					result.put("usedRebateCous", usedRebateCous);
				}
				
				List cityCounts = new ArrayList();
				List cityCouCounts = new ArrayList();
				List cityMonCouCounts = new ArrayList();
				// 查询本月之前注册城市分布
				List<Map<String, Object>> cityList = commDao.getCmRegistCity(dd);
				// 查询当月注册城市分布
				List<Map<String, Object>> cityMonthList = commDao.getCmRegistMonthCity(dd);
				if (cityList != null) {

					for (int i = 0; i < cityList.size(); i++) {
						if (cityList.get(i).get("cityname").toString().contains("黔东南")) {
							cityCounts.add("黔东南苗族\n侗族自治州 ");
						} else {
							cityCounts.add(cityList.get(i).get("cityname").toString());
						}
						cityCouCounts.add(Integer.parseInt(cityList.get(i).get("cou").toString()));
						cityMonCouCounts.add(Integer.parseInt(cityMonthList.get(i).get("cou").toString()));
					}
					JSONArray jsonCity = (JSONArray) JSONArray.fromObject(cityCounts);
					JSONArray jsonCityCou = (JSONArray) JSONArray.fromObject(cityCouCounts);
					JSONArray jsonCityMonCou = (JSONArray) JSONArray.fromObject(cityMonCouCounts);

					result.put("reCityname", jsonCity);
					result.put("reCityCou", jsonCityCou);
					result.put("reCityMonCou", jsonCityMonCou);
				}
				// 按城市查询总分布量
				List<Map<String, Object>> cityAllList = commDao.getAllCmRegistCity(dd);
				List cityAllCounts = new ArrayList();
				List cityCountsList = new ArrayList();
				if (cityAllList != null) {

					for (int i = 0; i < cityAllList.size(); i++) {
						/*
						 * if(cityList.get(i).get("cityname").toString().contains("黔东南")) {
						 * cityAllCounts.add("黔东南"); }else { }
						 */
						cityAllCounts.add(cityAllList.get(i).get("cityname").toString());
						cityCountsList.add(Integer.parseInt(cityAllList.get(i).get("cou").toString()));
					}
					JSONArray jsonCityC = (JSONArray) JSONArray.fromObject(cityAllCounts);
					JSONArray jsonCityAllCou = (JSONArray) JSONArray.fromObject(cityCountsList);

					result.put("jsonCityC", jsonCityC);
					result.put("jsonCityAllCou", jsonCityAllCou);
				}

				// 查询最受欢迎商品

				List<Map<String, Object>> hotProList = commDao.getHotProduct(dd);
				List hotCounts = new ArrayList();
				List hotSellDur = new ArrayList();
				List hotName = new ArrayList();
				if (hotProList != null && hotProList.size() > 0) {

					for (int i = 0; i < hotProList.size(); i++) {
						/*
						 * if(cityList.get(i).get("cityname").toString().contains("黔东南")) {
						 * cityAllCounts.add("黔东南"); }else { }
						 */
						hotName.add(hotProList.get(i).get("pname").toString());
						hotCounts.add(Integer.parseInt(hotProList.get(i).get("cou").toString()));
						if (Integer.parseInt(hotProList.get(i).get("selldur").toString()) > 100) {
							hotSellDur.add(Integer.parseInt(hotProList.get(i).get("selldur").toString()) / 30 + "个月+");
						} else {
							hotSellDur.add(Integer.parseInt(hotProList.get(i).get("selldur").toString()) + "天");
						}
					}
					JSONArray hotProName = (JSONArray) JSONArray.fromObject(hotName);
					JSONArray hotProCount = (JSONArray) JSONArray.fromObject(hotCounts);
					JSONArray hotProSellDur = (JSONArray) JSONArray.fromObject(hotSellDur);

					result.put("hotProName", hotProName);
					result.put("hotProCount", hotProCount);
					result.put("hotProSellDur", hotProSellDur);
				}
				// 查询无人问津商品
				List<Map<String, Object>> coolProList = commDao.getCoolProduct(dd);
				List coolCounts = new ArrayList();
				List coolSellDur = new ArrayList();
				List coolName = new ArrayList();
				if (coolProList != null && coolProList.size() > 0) {

					for (int i = 0; i < coolProList.size(); i++) {
						/*
						 * if(cityList.get(i).get("cityname").toString().contains("黔东南")) {
						 * cityAllCounts.add("黔东南"); }else { }
						 */
						coolName.add(coolProList.get(i).get("pname").toString());
						coolCounts.add(Integer.parseInt(coolProList.get(i).get("cou").toString()));
						if (Integer.parseInt(coolProList.get(i).get("selldur").toString()) > 100) {
							coolSellDur.add(Integer.parseInt(coolProList.get(i).get("selldur").toString()) / 30 + "个月+");
						} else {
							coolSellDur.add(Integer.parseInt(coolProList.get(i).get("selldur").toString()) + "天");
						}

					}
					JSONArray coolProName = (JSONArray) JSONArray.fromObject(coolName);
					JSONArray coolProSellDur = (JSONArray) JSONArray.fromObject(coolSellDur);
					JSONArray coolProCount = (JSONArray) JSONArray.fromObject(coolCounts);

					result.put("coolProName", coolProName);
					result.put("coolProCount", coolProCount);
					result.put("coolProSellDur", coolProSellDur);
				}
				// 查询e店情况
				List<Map<String, Object>> eshopList = commDao.getEshopSell(dd);
				List eCounts = new ArrayList();
				List eSums = new ArrayList();
				List eNames = new ArrayList();
				if (eshopList!=null&&!eshopList.isEmpty()) {

					for (int i = 0; i < eshopList.size(); i++) {
						eNames.add(eshopList.get(i).get("ename").toString());
						// 转换订单量
						if (Double.parseDouble(eshopList.get(i).get("sellcou").toString()) > 10000) {
							eCounts.add(toBigMoney(eshopList.get(i).get("sellcou").toString()));
						} else {
							eCounts.add(Double.parseDouble(eshopList.get(i).get("sellcou").toString()));
						}
						// 转换成交额
						if (Double.parseDouble(eshopList.get(i).get("sellsum").toString()) > 10000) {
							eSums.add(toBigMoney(eshopList.get(i).get("sellsum").toString()));
						} else {
							eSums.add(Double.parseDouble(eshopList.get(i).get("sellsum").toString()));
						}

					}
					JSONArray eshopName = (JSONArray) JSONArray.fromObject(eNames);
					JSONArray eshopCount = (JSONArray) JSONArray.fromObject(eCounts);
					JSONArray eshopSum = (JSONArray) JSONArray.fromObject(eSums);

					result.put("eshopName", eshopName);
					result.put("eshopCount", eshopCount);
					result.put("eshopSum", eshopSum);
				}
				
		return result;

	}

	@Override
	public Map<String, Object> selectMeOrder(String dd) {
		/**
		 * @author wuxinxin
		 * 查询订单相关信息
		 * 2018年6月12日
		 */
		//查询城市id
		
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		StoreDao storeDao = (StoreDao) SpringHelper.getBean(StoreDao.class.getName());
		String cityno = "";
		if(!"0000".equals(dd)) {
			if(dd!=null&&!dd.substring(0,1).equals("0")){
				cityNO = storeDao.getCityNOOfCityById(Long.parseLong(dd));
				cityno = cityNO.get(0).get("cityno").toString();
				if(cityno.substring(0,2).equals("00")) {
					dd=cityno.replace("00", "0");
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		PlatformStoreDao cmDao = (PlatformStoreDao) SpringHelper.getBean(PlatformStoreDao.class.getName());
		CommuneMemberDao commDao = (CommuneMemberDao) SpringHelper.getBean(CommuneMemberDao.class.getName());
		//查询已成交订单量

		//查询取消订单量
		//查询7日取消订单走势
		//查询7日成交订单走势、成交额走势、非社员订单量、非社员成交额
		List<Map<String, Object>> eshopMemList = commDao.getEshopMemCount(dd);
		List memCounts = new ArrayList();//社员成交量
		List memSums = new ArrayList();//社员成交额
		List noMemCounts = new ArrayList();//非社员成交量
		List noMemSums = new ArrayList();//非社员成交额
		List eshopCounts = new ArrayList();//安心合作社成交量
		List eshopSums = new ArrayList();//安心合作社成交额
		if (eshopMemList!=null&&!eshopMemList.isEmpty()) {
			for (int i = 0; i < eshopMemList.size(); i++) {
				eshopCounts.add(Integer.parseInt(eshopMemList.get(i).get("eshopcou").toString()));
			}
		}
		//查询取消订单数
		
		/*List<Map<String, Object>> memCountList = commDao.getDayOfEshopMemCount(dd);
		if (!memCountList.isEmpty()) {
			for (int i = 0; i < memCountList.size(); i++) {
				memCounts.add(Integer.parseInt(memCountList.get(i).get("dayOfEshopMemCount").toString()));
			}
		}
		
		//查询7日社员成交额走势
		List<Map<String, Object>> memSumList = commDao.getDayOfEshopMemSum(dd);
		if (!memSumList.isEmpty()) {
			for (int i = 0; i < memSumList.size(); i++) {
				memSums.add(Double.valueOf(memSumList.get(i).get("dayOfEshopMemSum").toString()).intValue());
			}
		}
		//查询7日非社员订单量
		List<Map<String, Object>> noMemCountsList = commDao.getDayOfEshopNmemCount(dd);
		if (!noMemCountsList.isEmpty()) {
			for (int i = 0; i < noMemCountsList.size(); i++) {
				noMemCounts.add(Double.valueOf(noMemCountsList.get(i).get("dayOfEshopNmemCount").toString()).intValue());
			}
			
		}
		//查询7日非社员成交额
		List<Map<String, Object>> noMemSumList = commDao.getDayOfEshopNmemSum(dd);
		if (!noMemSumList.isEmpty()) {
			for (int i = 0; i < noMemSumList.size(); i++) {
				noMemSums.add(Double.valueOf(noMemSumList.get(i).get("dayOfEshopNmemSum").toString()).intValue());
			}
			
		}*/
		JSONArray jsonDayMemCounts = (JSONArray) JSONArray.fromObject(memCounts);
		JSONArray jsonDayMemSums = (JSONArray) JSONArray.fromObject(memSums);
		JSONArray jsonDayNoMemCounts = (JSONArray) JSONArray.fromObject(noMemCounts);
		JSONArray jsonDayNoMemSums = (JSONArray) JSONArray.fromObject(noMemSums);
		List dateXCounts = new ArrayList();
		try {
			dateXCounts = reDate(7);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONArray jsonDateMem = (JSONArray) JSONArray.fromObject(dateXCounts);
		JSONArray jsonEshopCounts = (JSONArray) JSONArray.fromObject(eshopCounts);
		result.put("jsonEshopCounts", jsonEshopCounts);
		result.put("jsonDateMem", jsonDateMem);
		result.put("jsonDayMemCounts", jsonDayMemCounts);
		result.put("jsonDayMemSums", jsonDayMemSums);
		result.put("jsonDayNoMemCounts", jsonDayNoMemCounts);
		result.put("jsonDayNoMemSums", jsonDayNoMemSums);
		
		//查询当日城市成交额
		List memCityCounts = new ArrayList();
		List memCityNames = new ArrayList();
		List<Map<String, Object>> memCityList = commDao.getDayOfEshopMemSumCity(dd);
		if (memCityList!=null&&!memCityList.isEmpty()) {
			for (int i = 0; i < memCityList.size(); i++) {
				memCityCounts.add(memCityList.get(i).get("citypri").toString());
				memCityNames.add(memCityList.get(i).get("cname").toString());
			}
		}
		JSONArray jsonCityMemCounts = (JSONArray) JSONArray.fromObject(memCounts);
		JSONArray jsonCityMemNames = (JSONArray) JSONArray.fromObject(memCityNames);
		result.put("jsonCityMemCounts", jsonCityMemCounts);
		result.put("jsonCityMemNames", jsonCityMemNames);
		//查询城市非社员成交额
		List<Map<String, Object>> noMemCityList = commDao.getDayOfEshopNmemSumCity(dd);
		List noMemCitySums = new ArrayList();
		if (noMemCityList!=null&&!noMemCityList.isEmpty()) {
			for (int i = 0; i < noMemCityList.size(); i++) {
				noMemCitySums.add(noMemCityList.get(i).get("citypri").toString());
				noMemCitySums.add(noMemCityList.get(i).get("cname").toString());
			}
		}
		JSONArray jsonNoMemCitySums = (JSONArray) JSONArray.fromObject(noMemCitySums);
		result.put("jsonNoMemCitySums", jsonNoMemCitySums);
		
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

	/**
	 * 成交额、订单量单位转换 
	 * 
	 * @author wuxinxin
	 */
	public static String toBigMoney(String money) {
		BigDecimal bigDecimal = new BigDecimal(money);
		String pic = "万";
		// 转换为万元（除以10000）
		BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
		// 转换为亿元
		if (decimal.compareTo(new BigDecimal("10000")) > 0) {
			decimal = bigDecimal.divide(new BigDecimal("10000"));
			pic = "亿";
		}
		// 保留两位小数
		DecimalFormat formater = new DecimalFormat("0.00");
		// 四舍五入
		// formater.setRoundingMode(RoundingMode.HALF_UP); // 5000008.89
		// formater.setRoundingMode(RoundingMode.HALF_DOWN);// 5000008.89
		// formater.setRoundingMode(RoundingMode.HALF_EVEN);
		formater.setRoundingMode(RoundingMode.DOWN);

		// 格式化完成之后得出结果
		String remoney = formater.format(decimal) + pic;
		return remoney;
	}

	@Override
	public Map<String, Object> selectMeCityOrder(String cityCode) {
		// TODO Auto-generated method stub
		/**
		 * @author wuxinxin
		 * 2018年6月12日
		 */
		 return null;
		
	}

    @Override
    public Map<String, Object> getCityCode(String dd) {
        Map<String, Object> result = new HashMap<String, Object>();
        CommuneMemberDao commDao = (CommuneMemberDao) SpringHelper.getBean(CommuneMemberDao.class.getName());
        List<Map<String, Object>> cityList = commDao.getCityNoName(dd);
        List cityCode = new ArrayList();
        List cityName = new ArrayList();
        if (cityList != null && cityList.size() > 0) {

            for (int i = 0; i < cityList.size(); i++) {

                cityCode.add(cityList.get(i).get("cityno").toString());
                if (cityList.get(i).get("cityname").toString().contains("黔东南")) {
                    cityName.add("黔东南");
                } else {
                    cityName.add(cityList.get(i).get("cityname").toString());
                }
            }
            JSONArray jsonCityName = (JSONArray) JSONArray.fromObject(cityName);
            JSONArray jsonCityCode = (JSONArray) JSONArray.fromObject(cityCode);

            result.put("jsonCityCode", jsonCityCode);
            result.put("jsonCityName", jsonCityName);
        }
        return result;
    }

	@Override
	public Map<String, Object> selectDayAllCm(String dd) {
		Map<String, Object> result = new HashMap<String, Object>();
		CommuneMemberDao commDao = (CommuneMemberDao) SpringHelper.getBean(CommuneMemberDao.class.getName());

		// 查询当日成交量、成交额 ----必须
		List<Map<String, Object>> dayDealList = new ArrayList<Map<String, Object>>();
		dayDealList = commDao.getDayDealCount(dd);
		if (dayDealList != null && !dayDealList.isEmpty()) {
			result.put("dayDealCount", dayDealList.get(0).get("cou"));
			result.put("dayDealSum", dayDealList.get(0).get("dealsum"));
		} else {
			result.put("dayDealCount", "0");
			result.put("dayDealSum", "0");
		}

		// 查询当日新增社员数量---必须
		List<Map<String, Object>> dayAddMemList = commDao.getDayaddMemCount(dd);
		if (dayAddMemList != null && dayAddMemList.size() > 0) {
			result.put("dayAddMem", dayAddMemList.get(0).get("cou"));
		} else {
			result.put("dayAddMem", "0");
		}
		if ("0000".equals(dd)) {

			// 查询城市当日新增社员数量
			List<Map<String, Object>> cityDayAddList = new ArrayList<Map<String, Object>>();
			cityDayAddList = commDao.getDayCityaddMemCount(dd);
			List cityNames = new ArrayList();
			List cityAdds = new ArrayList();
			for (Map<String, Object> addCity : cityDayAddList) {
				if (addCity.get("cityname").toString().contains("黔东南")) {
					cityNames.add("黔东南洲");

				} else {
					cityNames.add(addCity.get("cityname"));
				}
				cityAdds.add(addCity.get("citycou"));
			}
			JSONArray jsonCityNames = (JSONArray) JSONArray.fromObject(cityNames);
			JSONArray jsonCityAdds = (JSONArray) JSONArray.fromObject(cityAdds);
			result.put("jsonCityNames", jsonCityNames);
			result.put("jsonCityAdds", jsonCityAdds);
		}
		return result;
	}


}
