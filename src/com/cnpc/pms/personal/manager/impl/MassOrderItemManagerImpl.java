package com.cnpc.pms.personal.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.reportFiledown.entity.ExportRunableSPXSDA;
import com.cnpc.pms.reportFiledown.entity.HttpClientUtils;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.personal.manager.MassOrderItemManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.manager.impl.OrderManagerImpl;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.PropertiesValueUtil;
import com.cnpc.pms.utils.TypeChangeUtil;

public class MassOrderItemManagerImpl extends BizBaseCommonManager implements MassOrderItemManager {

	PropertiesValueUtil propertiesValueUtil = null;

    private XSSFCellStyle style_header = null;

	private static Log logger = LogFactory.getLog(OrderManagerImpl.class);


    /**
     * excel单元格公共样式
     */
    CellStyle cellStyle_common = null;
    
    @Override
    public Map<String, Object> queryCitynoByCode(String cityCode){
    	MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
    	Map<String,Object> order_obj = massOrderItemDao.queryCitynoByCode(cityCode);
    	order_obj.put("cityno", order_obj.get("cityno"));
    	
    	return order_obj;
    }
    @Override
    public Map<String, Object> queryEmployeeBySN(String order_sn){
    	MassOrderItemDao massOrderDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
    	
    	Map<String,Object> order_obj = new HashMap<String,Object>();
		order_obj =  massOrderDao.queryEmployeeBySN(order_sn);
    	
    	return order_obj;
    }
    @Override
    public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String beginDate){
    	MassOrderItemDao massOrderDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
    	OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());
    	
    	Map<String,Object> order_obj =  new HashMap<String,Object>();
    	String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    	if(StringUtils.isNotEmpty(beginDate) && DateUtils.compareDate(beginDate, format.format(new Date()))==0){
			order_obj = massOrderDao.queryAreaDetailByCode(area_code,order_sn,MassOrderDto.TimeFlag.CUR_DAY.code);
		}else if(StringUtils.isNotEmpty(beginDate) && DateUtils.compareDate(beginDate,preMonthFirst)>=0){
			order_obj =  massOrderDao.queryAreaDetailByCode(area_code,order_sn, MassOrderDto.TimeFlag.LATEST_MONTH.code);
		}else{
			order_obj =  massOrderDao.queryAreaDetailByCode(area_code,order_sn, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
		}
    	
    	Map<String,Object> position_obj = orderDao.queryPositionByOrdersn(order_sn);
    	String latitude = "";
    	String longitude ="";
		if (position_obj != null) {
			latitude =  (String) position_obj.get("latitude");
			longitude = (String) position_obj.get("longitude");
    	}
		order_obj.put("latitude", latitude);
		order_obj.put("longitude",longitude);
    	
    	return order_obj;
    }
	@Override
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto, PageInfo pageInfo) {
		MassOrderItemDao orderDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());

		Map<String, Object> result =new HashMap<String,Object>();
		try {
			result = orderDao.queryMassOrderItem(massOrderDto, pageInfo);
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}
	@Override
  	public Map<String, Object> exportOrder(MassOrderItemDto massOrderDto, TReportFiledown tReportFiledown) {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		
  		Map<String, Object> result = new HashMap<String,Object>();

		HttpClientUtils httpClientUtils = new HttpClientUtils();
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		String username = (String) sessionData.get("userCode");
		tReportFiledown.setCreate_time(new Date());
		tReportFiledown.setUsername(username);
		tReportFiledown.setDownTimes(0);
		tReportFiledown.setTableLogic("SPXSDA");
		String fileName =  tReportFiledown.getFilename();
		fileName = httpClientUtils.getPingYin(fileName);
		tReportFiledown.setFilename(fileName);
		tReportFiledown.setUrl("/" + fileName);
		tReportFiledown.setMark1("0");
		saveObject(tReportFiledown);

		ExportRunableSPXSDA s1 = new ExportRunableSPXSDA(fileName, null, massOrderDto, false,tReportFiledown,massOrderItemDao);
		Thread t1 = new Thread(s1);
		t1.start();

		result.put("message","导出成功！");
		result.put("status","success");
  		return result;
  	}
	private XSSFCellStyle getHeaderStyle(){
		return style_header;
	}

	private void setHeaderStyle(XSSFWorkbook wb){

		// 创建单元格样式
		style_header = wb.createCellStyle();
		style_header.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		style_header.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style_header.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
		//style_header.setBottomBorderColor(HSSFColor.BLACK.index);
		style_header.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		style_header.setBorderRight(XSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(XSSFCellStyle.BORDER_THIN);

	}

	public void setCellValueall(Row obj_row, int nCellIndex, Object value){
		Cell cell=obj_row.createCell(nCellIndex);
		cell.setCellStyle(getCellStyle_common());
		cell.setCellValue(new XSSFRichTextString(value==null?null:value.toString()));
		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	}
	
	public CellStyle getCellStyle_common(){
        return cellStyle_common;
    }
    
    public void setCellStyle_common(Workbook wb){
        cellStyle_common=wb.createCellStyle();
        cellStyle_common.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle_common.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        cellStyle_common.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        cellStyle_common.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle_common.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle_common.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
        cellStyle_common.setWrapText(true);//设置自动换行
    }
	@Override
	public Map<String, Object> queryDailyprofit(DynamicDto dd) {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		Long city_id = dd.getCityId();
		String province_id = dd.getProvinceId();
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> provinceNO = new ArrayList<Map<String,Object>>();
		if(city_id!=null){
			cityNO = storeDao.getCityNOOfCityById(city_id);
		}
		if(province_id!=null&&province_id!=""){
			provinceNO = storeDao.getProvinceNOOfCSZJ(province_id);
		}
		Map<String,Object> dailyprofitMap = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dailyprofitMap = massOrderItemDao.queryDailyprofit(dd,cityNO,provinceNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String gmv = this.getDailyprofitData(dd,dd.getMonth(),dailyprofitMap);
		result.put("gmv", gmv);
		return result;
	}
	private String getDailyprofitData(DynamicDto dd,int month,Map<String,Object> dailyprofitMap){
		JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        List<Map<String, Object>> orderProfitList = (List<Map<String, Object>>) dailyprofitMap.get("gmv");
        if(orderProfitList!=null&&orderProfitList.size()>0){
        	jo.put("order_daily_profit", orderProfitList.get(0).get("order_profit"));
        }else{
        	jo.put("order_daily_profit", 0);
        }
        json.put(jo);
        return json.toString();
	}
	@Override
	public Map<String, Object> queryMonthprofit(DynamicDto dd) {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		Long city_id = dd.getCityId();
		String province_id = dd.getProvinceId();
		String beginDate = DateUtils.getCurrMonthFirstDate("YYYY-MM-dd");
		String endDate = DateUtils.getCurrMonthLastDate("YYYY-MM-dd");
		dd.setBeginDate(beginDate);
		dd.setEndDate(endDate);
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> provinceNO = new ArrayList<Map<String,Object>>();
		if(city_id!=null){
			cityNO = storeDao.getCityNOOfCityById(city_id);
		}
		if(province_id!=null&&province_id!=""){
			provinceNO = storeDao.getProvinceNOOfCSZJ(province_id);
		}
		Map<String,Object> dailyprofitMap = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dailyprofitMap = massOrderItemDao.queryMonthprofit(dd,cityNO,provinceNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String gmv = this.getMonthprofitData(dd,dd.getMonth(),dailyprofitMap);
		result.put("gmv", gmv);
		return result;
	}
	@Override
	public Map<String, Object> queryYesterdayprofit(DynamicDto dd) {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		String beginDate = DateUtils.lastDate();
	    String endDate = DateUtils.lastDate();
	    dd.setBeginDate(beginDate);
	    dd.setEndDate(endDate);
		Long city_id = dd.getCityId();
		String province_id = dd.getProvinceId();
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> provinceNO = new ArrayList<Map<String,Object>>();
		if(city_id!=null){
			cityNO = storeDao.getCityNOOfCityById(city_id);
		}
		if(province_id!=null&&province_id!=""){
			provinceNO = storeDao.getProvinceNOOfCSZJ(province_id);
		}
		Map<String,Object> dailyprofitMap = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dailyprofitMap = massOrderItemDao.queryYesterdayprofit(dd,cityNO,provinceNO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String gmv = this.getYesterdayprofitData(dd,dd.getMonth(),dailyprofitMap);
		result.put("gmv", gmv);
		return result;
	}
	private String getMonthprofitData(DynamicDto dd,int month,Map<String,Object> dailyprofitMap){
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> orderProfitList = (List<Map<String, Object>>) dailyprofitMap.get("gmv");
		if(orderProfitList!=null&&orderProfitList.size()>0){
			jo.put("ims_profit", orderProfitList.get(0).get("ims_profit"));
			jo.put("return_profit", orderProfitList.get(0).get("return_profit"));
			jo.put("order_fee", orderProfitList.get(0).get("order_fee"));
			jo.put("total_profit", orderProfitList.get(0).get("total_profit"));
			jo.put("baosun", orderProfitList.get(0).get("baosun"));
			jo.put("pankui", orderProfitList.get(0).get("pankui"));
			jo.put("platform_profit", orderProfitList.get(0).get("platform_profit"));
		}else{
			jo.put("ims_profit", 0);
			jo.put("return_profit", 0);
			jo.put("order_fee", 0);
			jo.put("total_profit", 0);
			jo.put("baosun", 0);
			jo.put("pankui", 0);
			jo.put("platform_profit", 0);
		}
		json.put(jo);
		return json.toString();
	}
	private String getYesterdayprofitData(DynamicDto dd,int month,Map<String,Object> dailyprofitMap){
		JSONArray json = new JSONArray();
		JSONObject jo = new JSONObject();
		List<Map<String, Object>> orderProfitList = (List<Map<String, Object>>) dailyprofitMap.get("gmv");
		if(orderProfitList!=null&&orderProfitList.size()>0){
			jo.put("ims_profit", orderProfitList.get(0).get("ims_profit"));
			jo.put("return_profit", orderProfitList.get(0).get("return_profit"));
			jo.put("order_fee", orderProfitList.get(0).get("order_fee"));
			jo.put("total_profit", orderProfitList.get(0).get("total_profit"));
			jo.put("platform_profit", orderProfitList.get(0).get("platform_profit"));
		}else{
			jo.put("ims_profit", 0);
			jo.put("return_profit", 0);
			jo.put("order_fee", 0);
			jo.put("total_profit", 0);
			jo.put("platform_profit", 0);
		}
		json.put(jo);
		return json.toString();
	}
	@Override
	public Map<String, Object> queryOrderDetailBySN(String order_sn,String product_id) {
		logger.info("查询订单明细该产品信息开始:"+order_sn);

		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
		Map<String,Object> order_obj = massOrderDao.queryOrderDetailBySN(order_sn);
		String order_id = order_obj.get("id")==null?"":order_obj.get("id").toString();

		OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());

		List<Map<String, Object>> item_list = orderDao.queryOrderItemInfoByIdAndProid(order_id,product_id);
		order_obj.put("item_list", item_list);

		logger.info("查询订单明细该产品结束:"+order_sn);

		return order_obj;
	}
	@Override
	public Map<String, Object> getProfitRangeForWeek(DynamicDto dd) {
		Map<String, Object>  result = new HashedMap();
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> provinceNO = new ArrayList<Map<String,Object>>();
		Long city_id = dd.getCityId();
		String province_id = dd.getProvinceId();
		try{
			if(city_id!=null){
				cityNO = storeDao.getCityNOOfCityById(city_id);
			}
			if(province_id!=null&&province_id!=""){
				provinceNO = storeDao.getProvinceNOOfCSZJ(province_id);
			}
			String cur = com.cnpc.pms.base.file.comm.utils.DateUtil.curDate();
			String curDate = DateUtils.lastDate();
			String beginDate = DateUtils.getBeforeDate(cur,-7);
			dd.setBeginDate(beginDate);
			dd.setEndDate(curDate);
			Map<String, Object> customerOrderRate = massOrderItemDao.getProfitRangeForWeek(dd,cityNO,provinceNO);
			List<Map<String,Object>> lst_data = (List<Map<String, Object>>) customerOrderRate.get("lst_data");
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
			String maxDateStr = curDate;    
		    String minDateStr = "";    
		    Calendar calc =Calendar.getInstance();      
	         for(int i=0;i<7;i++){  
	        	 calc.setTime(sdf.parse(maxDateStr));    
	             calc.add(calc.DATE, -i);    
	             Date minDate = calc.getTime();    
	             minDateStr = sdf.format(minDate);   
	           	 Map<String,Object> lst_map = new HashMap<String, Object>();
	           	 lst_map.put("week_date", minDateStr.substring((minDateStr.indexOf("-")+1),minDateStr.length()));
	           	 lst_map.put("platform_profit", 0);
	           	 lst_map.put("ims_profit", 0);
	           	 lst_map.put("order_fee", 0);
	           	 lst_map.put("total_profit", 0);
	           	 lst_map.put("return_profit", 0);
				 for(int j=0;j<lst_data.size();j++){
	    			Map<String,Object> lst_map_week = lst_data.get(j);
	    			String dateStr = String.valueOf(lst_map_week.get("week_date"));
	    			if(minDateStr.contains(dateStr)){
	    				lst_data.remove(j);
	    				String week_date = String.valueOf(lst_map_week.get("week_date"));
	    				lst_map.put("week_date", week_date);
	    				lst_map.put("platform_profit", lst_map_week.get("platform_profit"));
	    				lst_map.put("ims_profit", lst_map_week.get("ims_profit"));
	    				lst_map.put("order_fee", lst_map_week.get("order_fee"));
	    				lst_map.put("total_profit", lst_map_week.get("total_profit"));
	    				lst_map.put("return_profit", lst_map_week.get("return_profit"));
	    				
	    			}
				}
				lst_data.add(lst_map);
	         }
			result.put("lst_data", lst_data);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<Store> queryAllStore() {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		List<Store> listStore = new ArrayList<Store>();
		try {
			List<Map<String, Object>> allStoreList = massOrderItemDao.findAllStore();
			for (Map<String, Object> map : allStoreList) {
				Store store = (Store) TypeChangeUtil.mapToObject(map, Store.class);
				listStore.add(store);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listStore;
	}
	@Override
	public Map<String, Object> queryDayGMVUserMemberProfit(DynamicDto dd,PageInfo pageInfo) {
		Map<String, Object>  result = new HashedMap();
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		String cityNO = dd.getSearchstr();
		try{
			Map<String, Object> dayGMVUserMemberProfitList = massOrderItemDao.queryDayGMVUserMemberProfit(dd,cityNO,pageInfo);
			result.put("cityDayGMVUserMemberProfit", dayGMVUserMemberProfitList);
			result.put("status","success");
		}catch(Exception e){
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}
	@Override
	public Map<String, Object> getProfitRangeForStoreWeek(DynamicDto dd) {
		Map<String, Object>  result = new HashedMap();
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		StoreDao storeDao = (StoreDao)SpringHelper.getBean(StoreDao.class.getName());
		List<Map<String, Object>> cityNO = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> provinceNO = new ArrayList<Map<String,Object>>();
		Long city_id = dd.getCityId();
		String province_id = dd.getProvinceId();
		try{
			if(city_id!=null){
				cityNO = storeDao.getCityNOOfCityById(city_id);
			}
			if(province_id!=null&&province_id!=""){
				provinceNO = storeDao.getProvinceNOOfCSZJ(province_id);
			}
			String cur = com.cnpc.pms.base.file.comm.utils.DateUtil.curDate();
			String curDate = DateUtils.lastDate();
			String beginDate = DateUtils.getBeforeDate(cur,-7);
			dd.setBeginDate(beginDate);
			dd.setEndDate(curDate);
			Map<String, Object> customerOrderRate = massOrderItemDao.getProfitRangeForStoreWeek(dd,cityNO,provinceNO);
			List<Map<String,Object>> lst_data = (List<Map<String, Object>>) customerOrderRate.get("lst_data");
			result.put("lst_data", lst_data);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}