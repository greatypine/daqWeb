package com.cnpc.pms.utils;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.utils
 * @Description: 通过上传至oss,导出excel文件
 * @Author: gbl
 * @CreateDate: 2018/8/2 9:49
 */
public class ExportExcelByOssUtil {
    // 上传文件本地存储目录
    private static final String UPLOAD_DIRECTORY = "template";
    //excel sheet 名称
    private String sheetName;
    //数据源
    private List<Map<String,Object>> data;
    //导出的文件标题
    private String[] headers;
    //数据源列名
    private String[] keys;


    private HSSFCellStyle style_header = null;
    private CellStyle cellStyle_common = null;

    public ExportExcelByOssUtil(String sheetName, List<Map<String,Object>> data, String[] headers, String [] keys){
        this.sheetName = sheetName;
        this.data = data;
        this.headers = headers;
        this.keys = keys;

    }


    private HSSFCellStyle getHeaderStyle(){
        return style_header;
    }

    private void setHeaderStyle(HSSFWorkbook wb){

        // 创建单元格样式
        style_header = wb.createCellStyle();
        style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style_header.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
        style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置边框
        style_header.setBottomBorderColor(HSSFColor.BLACK.index);
        style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);

    }

    private void setCellStyle_common(Workbook wb){
        cellStyle_common=wb.createCellStyle();
        cellStyle_common.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        cellStyle_common.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);//垂直居中

    }

    private CellStyle getCellStyle_common() {
        return cellStyle_common;
    }

    public void setCellValue(Row obj_row, int nCellIndex, Object value){
        Cell cell=obj_row.createCell(nCellIndex);
        cell.setCellStyle(getCellStyle_common());
        cell.setCellValue(new HSSFRichTextString(value==null?null:value.toString()));
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    }


    public Map<String,Object> exportFile(){
        Map<String,Object> result = new HashMap<String,Object>();
        OssRefFileManager ossRefFileManager  = (OssRefFileManager)SpringHelper.getBean("ossRefFileManager");
        UtilityManager utilityManager = (UtilityManager)SpringHelper.getBean("utilityManager");
        String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+ File.separator + UPLOAD_DIRECTORY;
        String fileName_part = utilityManager.getPYIndexStr(sheetName,true);
        // 如果目录不存在则创建
        File uploadDir = new File(str_file_dir_path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab

        setCellStyle_common(wb);
        setHeaderStyle(wb);
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow(0);
        String[] str_headers = headers;
        String[] headers_key = keys;
        for(int i = 0;i < str_headers.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(getHeaderStyle());
            cell.setCellValue(new HSSFRichTextString(str_headers[i]));
        }

        for(int i = 0;i < data.size();i++){
            row = sheet.createRow(i+1);
            for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
                setCellValue(row, cellIndex, data.get(i).get(headers_key[cellIndex]));
            }
        }




        FileOutputStream os = null;
        try {
            File file_xls = new File(str_file_dir_path + File.separator +System.currentTimeMillis()+"_"+fileName_part+".xls");
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<"+file_xls.getPath()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if(file_xls.exists()){
                file_xls.delete();
            }
            os = new FileOutputStream(file_xls.getAbsoluteFile());
            wb.write(os);
            String url = ossRefFileManager.uploadOssFile(file_xls,"xls","daqWeb/download/");
            result.put("message","导出成功！");
            result.put("status","success");
			result.put("data",url);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("message","请重新操作！");
            result.put("status","fail");
        } finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
