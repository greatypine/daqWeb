package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.*;
import com.cnpc.pms.personal.dto.UserProfileDto;
import com.cnpc.pms.personal.entity.DistCityCode;
import com.cnpc.pms.personal.entity.TargetEntry;
import com.cnpc.pms.personal.entity.TargetEntryStore;
import com.cnpc.pms.personal.manager.DistCityCodeManager;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.personal.manager.TargetEntryManager;
import com.cnpc.pms.personal.manager.TargetEntryStoreManager;
import com.cnpc.pms.utils.DateUtils;
import com.lowagie.text.html.simpleparser.IncCell;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.h2.store.Data;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSheetProtection;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TargetEntryStoreManagerImpl extends BizBaseCommonManager implements TargetEntryStoreManager {

	private XSSFCellStyle style_header = null;

	CellStyle cellStyle_common = null;
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
	public Map<String, Object> showTarStoregetData(QueryConditions conditions) {
		TargetEntryStoreDao targetEntryStoreDao = (TargetEntryStoreDao) SpringHelper.getBean(TargetEntryStoreDao.class.getName());
		TargetEntryStoreManager targetEntryStoreManager = (TargetEntryStoreManager) SpringHelper.getBean("targetEntryStoreManager");
		// 查询的数据条件
		StringBuilder sb_where = new StringBuilder();
		// 分页对象
		PageInfo obj_page = conditions.getPageinfo();

		// 返回的对象，包含数据集合、分页对象等
		Map<String, Object> map_result = new HashMap<String, Object>();
		List<Map<String, Object>> mapWhereList = conditions.getConditions();
//		String  channel_where = mapWhereList.get(4).get("value").toString().replace("%","");
		String dept_where = mapWhereList.get(3).get("value").toString().replace("%","");
		String date_where = mapWhereList.get(2).get("value").toString().replace("%","");

		List<Map<String, Object>> datas = targetEntryStoreDao.getTargetEntryStoreData(date_where,dept_where,
				obj_page);
		List<Map<String, Object>> data = targetEntryStoreDao.getTargetEntryStoreList(sb_where.toString(), obj_page);
		List<Map<String, Object>> data1 = targetEntryStoreDao.getTargetEntryStoreList1(sb_where.toString(), obj_page);

		if(datas.size() == 0) {
			if(obj_page.getCurrentPage() == 1){
				for (int i = 0; i < data1.size(); i++) {
					TargetEntryStore targetEntryStore = new TargetEntryStore();
					Map dataRow = data1.get(i);
					targetEntryStore.setBusinessGroup_name(dept_where);
					targetEntryStore.setCity_name(dataRow.get("city_name").toString());
					targetEntryStore.setStore_name(dataRow.get("store_name").toString());
					targetEntryStore.setStore_code(dataRow.get("store_code").toString());
//					targetEntryStore.setChannel_name(channel_where);
					targetEntryStore.setFrame_time(date_where);
					targetEntryStore.setMaori_target(new BigDecimal(0));
					targetEntryStore.setProfit_target(new BigDecimal(0));
					targetEntryStore.setUser_target(new BigDecimal(0));
					targetEntryStoreManager.saveObject(targetEntryStore);
				}
			}
			for(int i = 0; i < data.size(); i++){
				Map dataRow = data.get(i);
				dataRow.put("frame_time",date_where);
//				dataRow.put("channel_name",channel_where);
			}
			map_result.put("data", data);
		}else{
			map_result.put("data", datas);
		}


		System.out.println(sb_where);
		map_result.put("pageinfo", obj_page);
		map_result.put("header", "目标值录入到门店信息");

		return map_result;
	}

	@Override
	public Map<String, Object> exportOrder(TargetEntryStore targetEntryStore){
		TargetEntryStoreDao targetEntryStoreDao = (TargetEntryStoreDao)SpringHelper.getBean(TargetEntryStoreDao.class.getName());
		Map<String, Object> result = new HashMap<String,Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list=targetEntryStoreDao.exportFile(targetEntryStore);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(list!=null&&list.size()>0){//成功返回数据
			String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template";
			String str_web_path = PropertiesUtil.getValue("file.web.root");

			XSSFWorkbook wb = new XSSFWorkbook();
			setCellStyle_common(wb);
			setHeaderStyle(wb);
			XSSFSheet sheet = wb.createSheet("目标值录入");
			XSSFRow row = sheet.createRow(0);
//			setCellValueall(row, 8, "灰色区域不可修改，请填写各门店指标");
			//单元格锁定的样式
			XSSFCellStyle lockstyle = wb.createCellStyle();
			lockstyle.setLocked(true);
			lockstyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			lockstyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			lockstyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			lockstyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			lockstyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			lockstyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			lockstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			lockstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
			lockstyle.setWrapText(true);//设置自动换行

			//单元格不锁定的样式
			XSSFCellStyle unlockstyle = wb.createCellStyle();
			unlockstyle.setLocked(false);
			unlockstyle.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			unlockstyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			unlockstyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			unlockstyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			unlockstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			unlockstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中
			unlockstyle.setWrapText(true);//设置自动换行

			String[] str_headers1 = {"月份","事业群名称","","事业群毛利指标","事业群利润指标","事业群用户指标"};
			String[] headers_value = {targetEntryStore.getFrame_time(),targetEntryStore.getBusinessGroup_name(),"",targetEntryStore.getMaori_target().toString(),targetEntryStore.getProfit_target().toString(),targetEntryStore.getUser_target().toString()};
			row.setHeightInPoints(40);
			for(int i = 0;i < str_headers1.length;i++){
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(getHeaderStyle());
				cell.setCellValue(new XSSFRichTextString(str_headers1[i]));
				sheet.setColumnWidth(i, 20 * 256);
				cell.setCellStyle(lockstyle);
			}
			row = sheet.createRow(1);
			row.setHeightInPoints(30);

			for(int cellIndex = 0;cellIndex < headers_value.length; cellIndex ++) {
				XSSFCell cell =row.createCell(cellIndex);
				setCellValueall(row, cellIndex, headers_value[cellIndex]);
				if(cellIndex>=0 && cellIndex<3){
					cell.setCellStyle(lockstyle);

				}else{
					cell.setCellStyle(unlockstyle);
				}
			}

			//定义表头 以及 要填入的 字段
			String[] str_headers = {"城市名称","门店名称","门店编号","门店毛利指标","门店利润指标","门店用户指标"};
			String[] headers_key = {"city_name","store_name","store_code","maori_target","profit_target","user_target"};
			row = sheet.createRow(2);
//			setCellValueall(row, 8, "填写是请注意，门店指标总和应等于频道指标。");
			row.setHeightInPoints(40);
			for(int i = 0;i < str_headers.length;i++){
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(getHeaderStyle());
				cell.setCellValue(new XSSFRichTextString(str_headers[i]));
				sheet.setColumnWidth(i, 20 * 256);
				cell.setCellStyle(lockstyle);
			}

			for(int i = 0;i < list.size();i++){
				row = sheet.createRow(i+3);
				row.setHeightInPoints(30);
				for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
					XSSFCell cell =row.createCell(cellIndex);
					String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
					setCellValueall(row, cellIndex, value);

					if(cellIndex>=0 && cellIndex<3){
						cell.setCellStyle(lockstyle);

					}else{
						cell.setCellStyle(unlockstyle);
					}
				}
			}
// 设置锁定的单元格为写保护 注释2
			//sheet表加密：等效excel的审阅菜单下的保护工作表
//			sheet.protectSheet(new String("333"));//333是密码
			sheet.getProtect();
			sheet.enableLocking();
			CTSheetProtection sheetProtection = sheet.getCTWorksheet().getSheetProtection();
			sheetProtection.setSelectLockedCells(false);
			sheetProtection.setSelectUnlockedCells(false);
			sheetProtection.setFormatCells(true);
			sheetProtection.setFormatColumns(true);
			sheetProtection.setFormatRows(true);
			sheetProtection.setInsertColumns(true);
			sheetProtection.setInsertRows(false);
			sheetProtection.setInsertHyperlinks(true);
			sheetProtection.setDeleteColumns(true);
			sheetProtection.setDeleteRows(true);
			sheetProtection.setSort(false);
			sheetProtection.setAutoFilter(false);
			sheetProtection.setPivotTables(true);
			sheetProtection.setObjects(true);
			sheetProtection.setScenarios(true);



			File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_targetEntryValueView.xlsx");
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

	/**
	 * 导入
	 */
	@Override
	public String saveTargetEntryStore(List<File> lst_import_excel) throws Exception {
		String rcvmsg = null;
		for(File file_excel : lst_import_excel) {
			//读取excel文件
			InputStream is_excel = new FileInputStream(file_excel);
			//Excel工作簿
			Workbook wb_excel;
			Sheet sheet_data;
			try {
				//解析2003的xls模式的excel
				wb_excel = new HSSFWorkbook(is_excel);
			} catch (Exception e) {
				//如果2003模式解析异常，尝试解析2007模式
				wb_excel = new XSSFWorkbook(file_excel.getAbsolutePath());
			}

			//只读第一个sheet页
			sheet_data = wb_excel.getSheetAt(0);
			int ret = sheet_data.getPhysicalNumberOfRows();
			TargetEntryStoreDao targetEntryStoreDao = (TargetEntryStoreDao) SpringHelper.getBean(TargetEntryStoreDao.class.getName());
			TargetEntryDao targetEntryDao = (TargetEntryDao) SpringHelper.getBean(TargetEntryDao.class.getName());
			Double maori_target_list = 0.0;
			Double profit_target_list = 0.0;
			Double user_target_list  = 0.0;
			Double maori_target = 0.0;
			Double profit_target = 0.0;
			Double user_target = 0.0;
			String frame_time = "";
			String channel_name = "";
			String dept_name = "";
			for(int nRowIndex = 0;nRowIndex < sheet_data.getPhysicalNumberOfRows();nRowIndex++) {
				Row row_human = sheet_data.getRow(nRowIndex);
				int nCellSize = row_human.getPhysicalNumberOfCells();
				if(nCellSize == 6){
					if(nRowIndex == 1){
						for(int nCellIndex = 0;nCellIndex < nCellSize ;nCellIndex ++) {
							String str_value = getCellValue(row_human.getCell(nCellIndex));
							if(nCellIndex == 3){
								if(isNumeric(str_value)){
									if(str_value.contains(".")){
										String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
										if(str_value1.length() > 2){
											rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
											return rcvmsg;
										}
									}
									maori_target_list = Double.valueOf(str_value);
								}else{
									rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
									return rcvmsg;
								}

							}
							if(nCellIndex == 4){
								if(isNumeric(str_value)){
									if(str_value.contains(".")){
										String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
										if(str_value1.length() > 2){
											rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
											return rcvmsg;
										}
									}
									profit_target_list = Double.valueOf(str_value);
								}else{
									rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
									return rcvmsg;
								}
							}
							if(nCellIndex == 5){
								if(str_value.contains(".")){
									String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
									if(str_value1.length() > 2){
										rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
										return rcvmsg;
									}
								}
								if(isNumeric(str_value)){
									user_target_list = Double.valueOf(str_value);
								}else{
									rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
									return rcvmsg;
								}
							}
							if(nCellIndex == 0){
								frame_time = str_value;
							}
							if(nCellIndex == 1){
								dept_name = str_value;
							}
//							if(nCellIndex == 2){
//								channel_name = str_value;
//							}
						}
					}
					if(nRowIndex > 2){
						for(int nCellIndex = 0;nCellIndex < nCellSize ;nCellIndex ++) {
							String str_value = getCellValue(row_human.getCell(nCellIndex));
							if(str_value != null && str_value != "" && str_value != "0"){
								if(nCellIndex == 3){
									if(isNumeric(str_value)){
										if(str_value.contains(".")){
											String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
											if(str_value1.length() > 2){
												rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
												return rcvmsg;
											}
										}
										maori_target =  maori_target + Double.valueOf(str_value);
									}else{
										rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
										return rcvmsg;
									}

								}
								if(nCellIndex == 4){
									if(isNumeric(str_value)){
										if(str_value.contains(".")){
											String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
											if(str_value1.length() > 2){
												rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
												return rcvmsg;
											}
										}
										profit_target = profit_target + Double.valueOf(str_value);
									}else{
										rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
										return rcvmsg;
									}

								}
								if(nCellIndex == 5){
									if(isNumeric(str_value)){
										if(str_value.contains(".")){
											String str_value1 = str_value.substring(str_value.indexOf(".")+1,str_value.length());
											if(str_value1.length() > 2){
												rcvmsg = "导入文件指标请保留到小数点后两位！导入失败！行号：" + (nRowIndex + 1);
												return rcvmsg;
											}
										}
										user_target =  user_target + Double.valueOf(str_value);
									}else{
										rcvmsg = "导入文件指标类型不正确！导入失败！行号：" + (nRowIndex + 1);
										return rcvmsg;
									}
								}
							}
						}
					}
				}else{
					rcvmsg = "导入文件列数不正确！导入失败！行号：" + (nRowIndex + 1);
					return rcvmsg;
				}

			}
			if(ret != 0){
				if(maori_target_list.toString().equals(maori_target.toString()) && profit_target_list.toString().equals(profit_target.toString()) && user_target_list.toString().equals(user_target.toString())){
					for(int nRowIndex = 0;nRowIndex < sheet_data.getPhysicalNumberOfRows();nRowIndex++) {
						Row row_human = sheet_data.getRow(nRowIndex);

						if (row_human == null) {
							rcvmsg = "导入文件格式不正确！导入失败！行号：" + (nRowIndex + 1);
							return rcvmsg;
						}else{
							if(nRowIndex == 1){
								int nCellSize = row_human.getPhysicalNumberOfCells();
								TargetEntry targetEntry = new TargetEntry();
								for(int nCellIndex = 0;nCellIndex < nCellSize ;nCellIndex ++) {
									String str_value = "";
									str_value = getCellValue(row_human.getCell(nCellIndex));
									if(str_value == null){
										str_value = "0";
									}
									if(nCellIndex == 3){
										targetEntry.setMaori_target(new BigDecimal(str_value));
									}
									if(nCellIndex == 4){
										targetEntry.setProfit_target(new BigDecimal(str_value));
									}
									if(nCellIndex == 5){
										targetEntry.setUser_target(new BigDecimal(str_value));
									}
								}
								targetEntry.setFrame_time(frame_time);
								targetEntry.setBusinessGroup_name(dept_name);
//								targetEntry.setChannel_name(channel_name);
								targetEntryDao.updateTargetEntry(targetEntry);
							}
							if(nRowIndex > 2){
								int nCellSize = row_human.getPhysicalNumberOfCells();
								TargetEntryStore targetEntryStore = new TargetEntryStore();
								for(int nCellIndex = 0;nCellIndex < nCellSize ;nCellIndex ++) {
									String str_value = "";
									str_value = getCellValue(row_human.getCell(nCellIndex));
									if(str_value == null){
										str_value = "0";
									}
									if(nCellIndex == 0){
										targetEntryStore.setCity_name(str_value);
									}
									if(nCellIndex == 1){
										targetEntryStore.setStore_name(str_value);
									}
									if(nCellIndex == 2){
										targetEntryStore.setStore_code(str_value);
									}
									if(nCellIndex == 3){
										targetEntryStore.setMaori_target(new BigDecimal(str_value));
									}
									if(nCellIndex == 4){
										targetEntryStore.setProfit_target(new BigDecimal(str_value));
									}
									if(nCellIndex == 5){
										targetEntryStore.setUser_target(new BigDecimal(str_value));
									}

								}
								targetEntryStore.setFrame_time(frame_time);
								targetEntryStore.setBusinessGroup_name(dept_name);
//								targetEntryStore.setChannel_name(channel_name);
								targetEntryStoreDao.updateTargetEntryStore(targetEntryStore);
							}
						}

					}
				}else{
					rcvmsg = "导入失败！请确保填写门店各指标总和等于事业群指标";
					return rcvmsg;
				}
			}else{
				rcvmsg = "导入文件格式不正确！导入失败！";
				return rcvmsg;
			}

		}
		return rcvmsg;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	private String getCellValue(Cell cell) {
		String value;
		if(cell == null){
			return null;
		}
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = cell.getRichStringCellValue().getString().trim();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue() == 0D ? null : String.valueOf(cell.getNumericCellValue());
				if (cell.getCellStyle().getDataFormat() == 177||cell.getCellStyle().getDataFormat() == 58||cell.getCellStyle().getDataFormat() == 31){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(Double.valueOf(value));
					value = sdf.format(date);
					return value;
				}else if(DateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
				}
				if(value != null && value.contains("E")){
					value = new BigDecimal(value).toPlainString();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue()).trim();
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = cell.getCellFormula();
				break;
			default:
				value = "";
		}
		return value;
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
	public TargetEntry saveOrUpdateTargetEntry(TargetEntry targetEntry) {
		TargetEntry saveTargetEntry = null;
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

//	@Override
//	public Map<String, Object> getByTarget(String statistics,String cityname) {
//		Map<String,Object> result = new HashMap<String,Object>();
//		TargetEntryDao targetEntryDao = (TargetEntryDao)SpringHelper.getBean(TargetEntryDao.class.getName());
//		result = targetEntryDao.getStatisticsExist(statistics.replace("/", "-"),cityname);
//		return result;
//	}
}
