package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.manager.MassOrderManager;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.manager.impl.OrderManagerImpl;
import com.cnpc.pms.reportFiledown.entity.ExportRunableDDDA;
import com.cnpc.pms.reportFiledown.entity.HttpClientUtils;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.PropertiesValueUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MassOrderManagerImpl extends BizBaseCommonManager implements MassOrderManager {

	PropertiesValueUtil propertiesValueUtil = null;

    private XSSFCellStyle style_header = null;

	private static Log logger = LogFactory.getLog(OrderManagerImpl.class);
    
    /**
     * excel单元格公共样式
     */
    CellStyle cellStyle_common = null;
    
    @Override
    public Map<String, Object> queryCitynoByCode(String cityCode){
    	MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
    	Map<String,Object> order_obj = massOrderDao.queryCitynoByCode(cityCode);
    	order_obj.put("cityno", order_obj.get("cityno"));
    	
    	return order_obj;
    }
	
	@Override
	public Map<String, Object> queryMassOrder(MassOrderDto massOrderDto, PageInfo pageInfo) {
		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());

		Map<String, Object> result =new HashMap<String,Object>();
		try {
			result = massOrderDao.queryMassOrder(massOrderDto, pageInfo, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}

	
	@Override
  	public Map<String, Object> exportOrder(MassOrderDto massOrderDto, TReportFiledown tReportFiledown) {
		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
//		TReportFiledownManagerDao tReportFiledownDao = (TReportFiledownManagerDao)SpringHelper.getBean(TReportFiledownManagerDao.class.getName());
		Map<String, Object> result = new HashMap<String,Object>();
		HttpClientUtils httpClientUtils = new HttpClientUtils();
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		String username = (String) sessionData.get("userCode");
		tReportFiledown.setCreate_time(new Date());
		tReportFiledown.setUsername(username);
		tReportFiledown.setDownTimes(0);
		tReportFiledown.setTableLogic("DDDA");
		String fileName =  tReportFiledown.getFilename();
		fileName = httpClientUtils.getPingYin(fileName);
		tReportFiledown.setFilename(fileName);
		tReportFiledown.setUrl("/" + fileName);
		tReportFiledown.setMark1("0");
		tReportFiledown.setDescription("personal");
		saveObject(tReportFiledown);

		String starts = "DDDA";
		ExportRunableDDDA s1 = new ExportRunableDDDA(starts, fileName, null, massOrderDto, false,tReportFiledown,massOrderDao);
		Thread t1 = new Thread(s1);
		t1.start();

		result.put("message","导出成功！");
		result.put("status","success");
		return result;

  	}
	
	@Override
	public Map<String, Object> queryReturnMassOrder(MassOrderDto massOrderDto, PageInfo pageInfo) {
		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());

		Map<String, Object> result =new HashMap<String,Object>();
		try {
			result = massOrderDao.queryReturnMassOrder(massOrderDto, pageInfo);
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}
	
	@Override
  	public Map<String, Object> exportReturnOrder(MassOrderDto massOrderDto) {
		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
		
  		Map<String, Object> result = new HashMap<String,Object>();
  		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
  		try {
			list=massOrderDao.exportReturnOrder(massOrderDto);
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
  	        XSSFSheet sheet = wb.createSheet("退货订单数据");
  	        XSSFRow row = sheet.createRow(0);
  	        
  	        //定义表头 以及 要填入的 字段 
  	        String[] str_headers = {"订单号","片区A国安侠编号","用户电话","有效金额","交易金额","应付金额","退款金额","下单时间","预约时间","签收时间","退货时间","送单侠姓名","送单侠电话","E店名称","门店名称","门店编号","真实门店","真实门店编号",
  	        		"事业群","频道","首单频道","城市","是否公海订单","是否异常订单","是否已退款","是否小贷","是否快周边","是否微信礼品卡","是否拉新","是否集采订单","是开卡礼采订单","是否试用礼订单","是否积分订单","销售收入","结算方式","平台优惠券金额","粮票"};
  	        String[] headers_key = {"order_sn","info_employee_a_no","customer_mobile_phone","gmv_price","trading_price","payable_price","returned_amount","create_time","appointment_start_time","sign_time","return_time",
  	        		"employee_name","employee_phone","eshop_name","store_name","store_code","real_store_name","real_store_code","department_name","channel_name","first_channel_name","store_city_name","pubseas_label","abnormal_label",
  	        		"return_label","loan_label","quick_label","gift_label","customer_isnew_flag","order_tag_b","order_tag_k","order_tag_s","score","order_profit","contract_method","apportion_coupon","apportion_rebate"};
  	       
  	        for(int i = 0;i < str_headers.length;i++){
  	            XSSFCell cell = row.createCell(i);
  	            cell.setCellStyle(getHeaderStyle());
  	            cell.setCellValue(new XSSFRichTextString(str_headers[i]));
  	        }
  	        
  	        for(int i = 0;i < list.size();i++){
  	        	 row = sheet.createRow(i+1);
  	             for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
  	            	String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
  	            	if(cellIndex==2 && "normal".equals(massOrderDto.getHidden_flag())){
  	            		if(StringUtils.isNotEmpty(value) && value.length() > 7 ){
							value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
						}
  	  	            }
  	  	            setCellValueall(row, cellIndex, value);
  	             }
  	        }

  			File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_returnorderlist.xlsx");
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
    public Map<String, Object> queryOrderInfoBySN(String order_sn){
    	MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
    	Map<String,Object> order_obj = massOrderDao.queryOrderInfoBySN(order_sn);
    	order_obj.put("create_time", order_obj.get("create_time"));
    	
    	return order_obj;
    }
    
    @Override
    public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn, String beginDate){
    	MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
    	OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());
    	
    	Map<String,Object> order_obj =  new HashMap<String,Object>();
		order_obj =  massOrderDao.queryAreaDetailByCode(area_code,order_sn);
    	Map<String,Object> position_obj = massOrderDao.queryPositionByOrdersn(order_sn);
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
    public Map<String, Object> queryEmployeeBySN(String order_sn, String beginDate){
    	MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
    	
    	Map<String,Object> order_obj = new HashMap<String,Object>();
    	String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if(StringUtils.isNotEmpty(beginDate) && DateUtils.compareDate(beginDate, format.format(new Date()))==0){
			order_obj = massOrderDao.queryEmployeeBySN(order_sn,MassOrderDto.TimeFlag.CUR_DAY.code);
		}else if(StringUtils.isNotEmpty(beginDate) && DateUtils.compareDate(beginDate,preMonthFirst)>=0){
			order_obj =  massOrderDao.queryEmployeeBySN(order_sn, MassOrderDto.TimeFlag.LATEST_MONTH.code);
		}else{
			order_obj =  massOrderDao.queryEmployeeBySN(order_sn, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
		}
    	
    	return order_obj;
    }
    
    @Override
    public Map<String, Object> queryContractById(String contract_id){
    	Map<String,Object> order_obj =  new HashMap<String,Object>();
    	OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());
    	Map<String,Object> contract_obj = orderDao.queryContractById(contract_id);
		order_obj.put("method",contract_obj.get("method"));
    	
    	return order_obj;
    }

    @Override
	public Map<String, Object> queryOrderDetailBySN(String order_sn){

		logger.info("查询订单明细信息开始:"+order_sn);

		MassOrderDao massOrderDao = (MassOrderDao)SpringHelper.getBean(MassOrderDao.class.getName());
		Map<String,Object> order_obj = massOrderDao.queryOrderDetailBySN(order_sn);
		String order_id = order_obj.get("id")==null?"":order_obj.get("id").toString();

		OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());

		Map<String,Object> order_name_obj = orderDao.queryOrderProductName(order_id);
		if(order_name_obj==null){
			order_obj.put("eshop_pro_name","");
		}else{
			order_obj.put("eshop_pro_name",order_name_obj.get("eshop_pro_name"));
		}
		List<Map<String, Object>> item_list = orderDao.queryOrderItemInfoById(order_id);
		order_obj.put("item_list", item_list);
		order_obj.put("create_time", order_obj.get("create_time"));

		logger.info("查询订单明细信息结束:"+order_sn);

		return order_obj;
	}

	@Override
	public Map<String, Object> queryOrderInfoByOrderSN(String order_sn) {
		MassOrderDao massOrderDao = (MassOrderDao) SpringHelper.getBean(MassOrderDao.class.getName());
		Map<String,Object> order_obj = new HashMap<String,Object>();
		try {
			order_obj = massOrderDao.queryOrderInfoByOrderSN(order_sn);
			String order_id = order_obj.get("id")==null?"":order_obj.get("id").toString();
			order_obj.put("create_time", order_obj.get("create_time"));
		}catch (Exception e){
			e.printStackTrace();
			logger.info("根据订单sn编号查询近两月内的订单信息:"+order_sn,e);
		}

		return order_obj;
	}

	@Override
	public Map<String, Object> queryOrderListByEmployeeNo(String employee_no, PageInfo pageInfo) {
		MassOrderDao massOrderDao = (MassOrderDao) SpringHelper.getBean(MassOrderDao.class.getName());
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			result = massOrderDao.queryOrderListOfEmployee(employee_no, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("国安侠个人动态送单：",e);
			return result;
		}
		return result;

	}
}
