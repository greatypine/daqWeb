package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.UserProfileDao;
import com.cnpc.pms.personal.dto.UserProfileDto;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.personal.manager.UserProfileManager;
import com.cnpc.pms.utils.PropertiesValueUtil;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function：用户档案Manager实现
 * @author：chenchuang
 * @date:2018年3月8日 下午3:25:35
 *
 * @version V1.0
 */
public class UserProfileManagerImpl extends BizBaseCommonManager implements UserProfileManager {
	
	PropertiesValueUtil propertiesValueUtil = null;

    private XSSFCellStyle style_header = null;
    
    CellStyle cellStyle_common = null;

	@Override
	public Map<String, Object> queryUserProfile(UserProfileDto userProfile,PageInfo pageInfo){
		UserProfileDao userProfileDao = (UserProfileDao)SpringHelper.getBean(UserProfileDao.class.getName());
		Map<String, Object> result =new HashMap<String,Object>();
		try {
			result = userProfileDao.queryUserProfile(userProfile, pageInfo);
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
	}
	
	@Override
	public Map<String, Object> exportUserProfile(UserProfileDto userProfile){
		UserProfileDao userProfileDao = (UserProfileDao)SpringHelper.getBean(UserProfileDao.class.getName());
		Map<String, Object> result = new HashMap<String,Object>();
  		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
  		try {
			list=userProfileDao.exportUserProfile(userProfile);
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
  	        XSSFSheet sheet = wb.createSheet("订单数据");
  	        XSSFRow row = sheet.createRow(0);
  	        
  	        //定义表头 以及 要填入的 字段 
  	        String[] str_headers = {"用户姓名","用户电话","用户来源","用户ID","历史消费金额","历史消费次数","最大交易金额","客单价","首次消费时间","最后消费时间","注册时间","沉默时间（天）","消费片区","有无画像","是否集采用户","是否合作社社员","是否沉默超30","是否沉默超60"};
  	        String[] headers_key = {"customer_name","customer_phone","customer_source","customer_id","trading_price_sum","order_count","trading_price_max","cus_sigle_price","first_order_time","last_order_time","regist_time","slient_time","area_code","user_model","is_b_tag","is_v_tag","is_thirty_tag","is_sixty_tag"};
  	       
  	        for(int i = 0;i < str_headers.length;i++){
  	            XSSFCell cell = row.createCell(i);
  	            cell.setCellStyle(getHeaderStyle());
  	            cell.setCellValue(new XSSFRichTextString(str_headers[i]));
  	        }
  	        for(int i = 0;i < list.size();i++){
  	        	 row = sheet.createRow(i+1);
  	             for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
  	            	String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
  	            	if(cellIndex==1 && "normal".equals(userProfile.getHidden_flag())){
						if(StringUtils.isNotEmpty(value) && value.length() > 7 ){
							value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
						}
  	  	            }
  	  	            setCellValueall(row, cellIndex, value);
  	             }
  	        }

  			File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_userprofilelist.xls");
  			if(file_xls.exists()){
  				file_xls.delete();
  			}
  			FileOutputStream os = null;
			String url = null;
  			try {
  				os = new FileOutputStream(file_xls.getAbsoluteFile());
  				wb.write(os);
				OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
				url = ossRefFileManager.uploadOssFile(file_xls, "xls", "daqWeb/download/");
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
	
	public List<Map<String, Object>> queryDetailByCusId(String customer_id){
		UserProfileDao userProfileDao = (UserProfileDao)SpringHelper.getBean(UserProfileDao.class.getName());
		List<Map<String, Object>> lst_data = userProfileDao.queryDetailByCusId(customer_id);
		String area_code = "";
		String employee_a_no = "";
		String tiny_village_code = "";
		for(Map<String, Object> map : lst_data){
			String order_sn = (String) map.get("order_sn");
			Map<String, Object> order_obj = userProfileDao.queryOrderDetailBySn(order_sn);
			if (order_obj != null) {
				area_code = order_obj.get("area_code") == null ? "" : (String)order_obj.get("area_code");
				employee_a_no = order_obj.get("employee_a_no") == null ? "" : (String)order_obj.get("employee_a_no");
				tiny_village_code = order_obj.get("tiny_village_code") == null ? "" : (String)order_obj.get("tiny_village_code");
			}
			map.put("area_code",area_code);
			map.put("employee_a_no", employee_a_no);
			map.put("tiny_village_code",tiny_village_code);
		}
		return lst_data;
	}
	
	public Map<String, Object> queryRecentlyOrder(String customer_id){
		UserProfileDao userProfileDao = (UserProfileDao)SpringHelper.getBean(UserProfileDao.class.getName());
		List<Map<String, Object>> lst_data = userProfileDao.queryRecentlyOrder(customer_id);
		Map<String,Object> map_result = new HashMap<String,Object>();
		map_result.put("data", lst_data);
		return map_result;
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
