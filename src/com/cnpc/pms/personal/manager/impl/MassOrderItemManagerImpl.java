package com.cnpc.pms.personal.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.personal.manager.MassOrderItemManager;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.manager.impl.OrderManagerImpl;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.PropertiesValueUtil;

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
//		OrderDao orderDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		MassOrderItemDao orderDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());

		Map<String, Object> result =new HashMap<String,Object>();
		try {
			String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) && DateUtils.compareDate(massOrderDto.getBeginDate(), format.format(new Date()))==0){
				result = orderDao.queryMassOrderItem(massOrderDto, pageInfo,MassOrderDto.TimeFlag.CUR_DAY.code);
			}else if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) &&DateUtils.compareDate(massOrderDto.getBeginDate(),preMonthFirst)>=0){
				result = orderDao.queryMassOrderItem(massOrderDto, pageInfo,MassOrderDto.TimeFlag.LATEST_MONTH.code);
			}else{
				result = orderDao.queryMassOrderItem(massOrderDto, pageInfo,MassOrderDto.TimeFlag.HISTORY_MONTH.code);
			}
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}
	@Override
  	public Map<String, Object> exportOrder(MassOrderItemDto massOrderDto) {
		//OrderDao massOrderItemDao = (OrderDao)SpringHelper.getBean(OrderDao.class.getName());
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		
  		Map<String, Object> result = new HashMap<String,Object>();
  		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
  		try {
			String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) && DateUtils.compareDate(massOrderDto.getBeginDate(), format.format(new Date()))==0){
				list=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.CUR_DAY.code);
			}else if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) && DateUtils.compareDate(massOrderDto.getBeginDate(),preMonthFirst)>=0){
				list=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.LATEST_MONTH.code);
			}else{
				list=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
			}
  		} catch (Exception e) {
  			e.printStackTrace();
  			return null;
  		}
  		if(list!=null&&list.size()>0){//成功返回数据
  			if(list.size()>50000){
  				result.put("message","导出条目过多，请重新筛选条件导出！");
  	  			result.put("status","more");
  	  			return result;
  			}
			String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template";
  			String str_web_path = PropertiesUtil.getValue("file.web.root");

  	        XSSFWorkbook wb = new XSSFWorkbook();   
  	        setCellStyle_common(wb);
  	        setHeaderStyle(wb);
  	        XSSFSheet sheet = wb.createSheet("商品销售数据");
  	        XSSFRow row = sheet.createRow(0);
  	        
  	        //定义表头 以及 要填入的 字段 
  	        String[] str_headers = {"商品名称","商品ID","订单号","下单客户姓名","下单客户电话","预约时间","下单时间","签收时间","单价","签收客户姓名","签收客户电话","片区编号","小区编号","片区A国安侠编号","送单侠姓名",
  	        		"送单侠电话","签收地址","E店名称","门店名称","门店编号","事业群","频道","城市","订单来源","评价信息"};
	        String[] headers_key = {"product_name","product_id","order_sn","customer_name","customer_mobilephone","appointment_start_time","create_time","df_signed_time","original_price","order_customer_name","order_mobilephone","area_code","village_code",
			        		"info_employee_a_no","employee_name","employee_phone","order_address",
			        		"eshop_name","store_name","store_code","dep_name","channel_name","store_city_name","order_source","order_contents"};
  	       
  	        for(int i = 0;i < str_headers.length;i++){
  	            XSSFCell cell = row.createCell(i);
  	            cell.setCellStyle(getHeaderStyle());
  	            cell.setCellValue(new XSSFRichTextString(str_headers[i]));
  	        }
  	        
  	        for(int i = 0;i < list.size();i++){
  	        	 row = sheet.createRow(i+1);
  	             for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
  	            	String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
  	            	if(cellIndex==3 && "normal".equals(massOrderDto.getHidden_flag())){
						if(StringUtils.isNotEmpty(value) && value.length() > 7 ){
							value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
						}
  	  	            }
  	  	            setCellValueall(row, cellIndex, value);
  	             }
  	        }

  			File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_orderitemlist.xlsx");
  			if(file_xls.exists()){
  				file_xls.delete();
  			}
  			FileOutputStream os = null;
			String url = null;
  			try {
  				os = new FileOutputStream(file_xls.getAbsoluteFile());
  				wb.write(os);
				OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
				url = ossRefFileManager.uploadOssFile(file_xls, "xlsx", "daqWeb/download/");
  			}catch (Exception e) {
  				e.printStackTrace();
  			} finally {
  				if(os != null){
  					try {
  						os.close();
  					} catch (IOException e) {
  						e.printStackTrace();
  					}
  				}
  			}

  			result.put("message","导出成功！");
  			result.put("status","success");
			result.put("data", url);
  		}else{
  			result.put("message","请重新操作！");
  			result.put("status","fail");
  		}
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
		Map<String,Object> dailyprofitMap = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dailyprofitMap = massOrderItemDao.queryDailyprofit(dd);
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
        jo.put("order_daily_profit", dailyprofitMap.get("order_profit"));
        json.put(jo);
        return json.toString();
	}
	@Override
	public Map<String, Object> queryMonthprofit(DynamicDto dd) {
		MassOrderItemDao massOrderItemDao = (MassOrderItemDao)SpringHelper.getBean(MassOrderItemDao.class.getName());
		Map<String,Object> dailyprofitMap = null;
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			dailyprofitMap = massOrderItemDao.queryMonthprofit(dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String gmv = this.getMonthprofitData(dd,dd.getMonth(),dailyprofitMap);
		result.put("gmv", gmv);
		return result;
	}
	private String getMonthprofitData(DynamicDto dd,int month,Map<String,Object> dailyprofitMap){
		JSONArray json = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("order_month_profit", dailyprofitMap.get("order_profit"));
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
}