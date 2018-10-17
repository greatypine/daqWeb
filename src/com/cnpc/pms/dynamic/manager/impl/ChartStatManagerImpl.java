package com.cnpc.pms.dynamic.manager.impl;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.dao.ChartStatDao;
import com.cnpc.pms.dynamic.entity.ChartStatDto;
import com.cnpc.pms.dynamic.manager.ChartStatManager;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.manager.impl.OrderManagerImpl;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.PropertiesValueUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartStatManagerImpl extends BizBaseCommonManager implements ChartStatManager {

	PropertiesValueUtil propertiesValueUtil = null;

	private XSSFCellStyle style_header = null;

	private static Log logger = LogFactory.getLog(OrderManagerImpl.class);

	/**
	 * excel单元格公共样式
	 */
	CellStyle cellStyle_common = null;

	@Override
	public List<Map<String, Object>> queryContainsStoreDistCityList(){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());
		List<Map<String, Object>> lst_data = chartStatDao.queryContainsStoreDistCityList();
    	return lst_data;
	}
	
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
	public Map<String, Object> exportTurnover(ChartStatDto csd){
		ChartStatDao chartStatDao = (ChartStatDao)SpringHelper.getBean(ChartStatDao.class.getName());

		Map<String, Object> result = new HashMap<String,Object>();

		XSSFWorkbook wb = new XSSFWorkbook();
		setCellStyle_common(wb);
		setHeaderStyle(wb);

		List<Map<String, Object>> monthList = chartStatDao.queryTurnoverByMonth(csd);
		List<Map<String, Object>> weekList = chartStatDao.queryTurnoverByWeek(csd);
		List<Map<String, Object>> dayList = chartStatDao.queryTurnoverByDay(csd);
		List<Map<String, Object>> hourList = chartStatDao.queryTurnoverByHour(csd);
		if(monthList.isEmpty() && weekList.isEmpty() && dayList.isEmpty() && hourList.isEmpty()){
			return result;
		}
		if(monthList.size()>50000 || weekList.size()>50000 || dayList.size()>50000 || hourList.size()>50000){
			result.put("message","导出条目过多，请重新筛选条件导出！");
			result.put("status","more");
			return result;
		}

		String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template";
		String str_web_path = PropertiesUtil.getValue("file.web.root");

		//定义表头 以及 要填入的 字段
		String[] str_headers = {"时间","营业额"};
		String[] headers_key1 = {"month_time","month_amount"};

		XSSFSheet sheet1 = wb.createSheet("月");
		XSSFRow row1 = sheet1.createRow(0);
		for(int i = 0;i < str_headers.length;i++){
			XSSFCell cell = row1.createCell(i);
			cell.setCellStyle(getHeaderStyle());
			cell.setCellValue(new XSSFRichTextString(str_headers[i]));
		}
		for(int i = 0;i < monthList.size();i++){
			row1 = sheet1.createRow(i+1);
			for(int cellIndex = 0;cellIndex < headers_key1.length; cellIndex ++){
				String value = String.valueOf(monthList.get(i).get(headers_key1[cellIndex]));
				setCellValueall(row1, cellIndex, value);
			}
		}

		SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd");

		XSSFSheet sheet2 = wb.createSheet("周");
		XSSFRow row2 = sheet2.createRow(0);

		List weekTimes = getDateByWeek(); //部分时间周期无数据的处理
		List<Map<String, Object>> weekData= new ArrayList<Map<String, Object>>(weekTimes.size());
		for(int i=0;i<weekTimes.size();i++){
			Map temp = new HashMap();
			temp.put("week_time",weekTimes.get(i));
			temp.put("week_amount","0");
			weekData.add(temp);
		}
		for(int i=0;i<weekList.size();i++){
			String time = aDate.format(weekList.get(i).get("week_time"));
			Map map = new HashMap();
			map.put("week_time",time);
			map.put("week_amount","0");
			int index = weekData.indexOf(map);
			if(index>-1){
				Map temp = weekData.get(index);
				temp.put("week_amount",weekList.get(i).get("week_amount").toString());
			}
		}
		String[] headers_key2 = {"week_time","week_amount"};
		for(int i = 0;i < str_headers.length;i++){
			XSSFCell cell = row2.createCell(i);
			cell.setCellStyle(getHeaderStyle());
			cell.setCellValue(new XSSFRichTextString(str_headers[i]));
		}
		for(int i = 0;i < weekData.size();i++){
			row2 = sheet2.createRow(i+1);
			for(int cellIndex = 0;cellIndex < headers_key2.length; cellIndex ++){
				String value = String.valueOf(weekData.get(i).get(headers_key2[cellIndex]));
				setCellValueall(row2, cellIndex, value);
			}
		}

		XSSFSheet sheet3 = wb.createSheet("日");
		XSSFRow row3 = sheet3.createRow(0);

		List dayTimes = DateUtils.getDateByDay(); //部分时间周期无数据的处理
		List<Map<String, Object>> dayData= new ArrayList<Map<String, Object>>(dayTimes.size());
		for(int i=0;i<dayTimes.size();i++){
			Map temp = new HashMap();
			temp.put("day_time",dayTimes.get(i));
			temp.put("turnover","0");
			dayData.add(temp);
		}
		for(int i=0;i<dayList.size();i++){
			String time = aDate.format(dayList.get(i).get("day_time"));
			Map map = new HashMap();
			map.put("day_time",time);
			map.put("turnover","0");
			int index = dayData.indexOf(map);
			if(index>-1){
				Map temp = dayData.get(index);
				temp.put("turnover",dayList.get(i).get("turnover").toString());
			}
		}

		String[] headers_key3 = {"day_time","turnover"};
		for(int i = 0;i < str_headers.length;i++){
			XSSFCell cell = row3.createCell(i);
			cell.setCellStyle(getHeaderStyle());
			cell.setCellValue(new XSSFRichTextString(str_headers[i]));
		}
		for(int i = 0;i < dayData.size();i++){
			row3 = sheet3.createRow(i+1);
			for(int cellIndex = 0;cellIndex < headers_key3.length; cellIndex ++){
				String value = String.valueOf(dayData.get(i).get(headers_key3[cellIndex]));
				setCellValueall(row3, cellIndex, value);
			}
		}

		XSSFSheet sheet4 = wb.createSheet("时");
		XSSFRow row4 = sheet4.createRow(0);
		String[] headers_key4 = {"time","turnover"};
		for(int i = 0;i < str_headers.length;i++){
			XSSFCell cell = row4.createCell(i);
			cell.setCellStyle(getHeaderStyle());
			cell.setCellValue(new XSSFRichTextString(str_headers[i]));
		}
		for(int i = 0;i < hourList.size();i++){
			row4 = sheet4.createRow(i+1);
			for(int cellIndex = 0;cellIndex < headers_key4.length; cellIndex ++){
				String value = String.valueOf(hourList.get(i).get(headers_key4[cellIndex]));
				setCellValueall(row4, cellIndex, value);
			}
		}

		File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_datalist.xlsx");
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
		return result;
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

}
