package com.cnpc.pms.utils;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.utils.excel.MergedRegionParam;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    private Object[] headers;
    //数据源列名
    private Object[] keys;
    //导出的文件合并标题（支持多级合并）
    private List<List<MergedRegionParam>> params = new ArrayList<>();

    private HSSFCellStyle style_header = null;
    private CellStyle cellStyle_common = null;

    public ExportExcelByOssUtil(String sheetName, List<Map<String,Object>> data, Object[] headers,  Object[] keys){
        this.sheetName = sheetName;
        this.data = data;
        this.headers = headers;
        this.keys = keys;
    }

    public ExportExcelByOssUtil(String sheetName, List<Map<String,Object>> data, String[] headers, String [] keys, List<List<MergedRegionParam>> params){
        this.sheetName = sheetName;
        this.data = data;
        this.headers = headers;
        this.keys = keys;
        this.params = params;
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

    public void setCellNumberValue(Row obj_row,HSSFCellStyle contextstyle, int nCellIndex, Object value){
        Cell cell=obj_row.createCell(nCellIndex);
        cell.setCellStyle(getCellStyle_common());
        cell.setCellStyle(contextstyle);
        cell.setCellValue(Double.parseDouble(value.toString()));
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
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
        Object[] str_headers = headers;
        Object[] headers_key = keys;

        int rowNum = 0;

        HSSFRow row = null;
        if(params != null && params.size() > 0) {//不设置合并params值的话会跳过此步骤，不影响现有逻辑
            for(List<MergedRegionParam> paramList: params){
                row = sheet.createRow(rowNum);
                for(int i = 0;i < str_headers.length;i++){
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(getHeaderStyle());
                }
                for(MergedRegionParam param : paramList){
                    findIndex(param);
                    row.getCell(param.getI()).setCellValue(new HSSFRichTextString(param.getName()));
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, param.getI(), param.getJ()));
                }
                rowNum++;
            }
        }

        row = sheet.createRow(rowNum);

        for(int i = 0;i < str_headers.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(getHeaderStyle());
            cell.setCellValue(new HSSFRichTextString(str_headers[i].toString()));
        }

        for(int i = 0;i < data.size();i++){
            row = sheet.createRow(i+rowNum+1);
            for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++){
                Boolean isNum = false;//data是否为数值型

                if (data.get(i).get(headers_key[cellIndex]) != null && !"".equals(data.get(i).get(headers_key[cellIndex]))) {
                    //判断data是否为数值型
                    isNum = data.get(i).get(headers_key[cellIndex]).toString().matches("^(-?\\d+)(\\.\\d+)?$");
                }

                if(isNum){
                    if(headers_key[cellIndex].equals("mobilephone")||headers_key[cellIndex].equals("invitecode")||headers_key[cellIndex].equals("opencard_time")){
                        setCellValue(row, cellIndex, data.get(i).get(headers_key[cellIndex]));
                    }else{
                        HSSFDataFormat df = wb.createDataFormat(); // 此处设置数据格式
                        HSSFCellStyle contextstyle =wb.createCellStyle();
                        contextstyle.setDataFormat(df.getBuiltinFormat("#,##0.00"));//保留两位小数点
                        setCellNumberValue(row,contextstyle, cellIndex, data.get(i).get(headers_key[cellIndex]));
                    }
                }else{
                    setCellValue(row, cellIndex, data.get(i).get(headers_key[cellIndex]));
                }
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

    /**
     * 查找合并单元格所在的索引位置
     * @param param
     */
    public void findIndex(MergedRegionParam param){
        int i=0;
        int j=0;
        for(int k=0;i<headers.length;k++){
            if(headers[k].equals(param.getStart())){
                i = k;
            }
            if(headers[k].equals(param.getEnd())){
                j = k;
                break;
            }
        }
        param.setI(i);
        param.setJ(j);
    }

}
