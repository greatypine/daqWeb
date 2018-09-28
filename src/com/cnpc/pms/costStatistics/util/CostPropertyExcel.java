package com.cnpc.pms.costStatistics.util;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.util
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/9/28 13:49
 */
public class CostPropertyExcel {

    // 上传文件本地存储目录
    private static final String UPLOAD_DIRECTORY = "template";

    //数据源
    private List<Map<String,Object>> data;

    public CostPropertyExcel(List<Map<String,Object>> data){
        this.data = data;
    }
    //导出的文件标题
    private final String[] header0={"序号","门店编码","门店名称","物业费（年）","物业期限（月）","物业费摊销"};

    private final String[] colName = new String[] { "store_no", "store_name","property_fee_year","property_deadline","property_fee_month"};

    private final String[] rowColNum=new String[]{"A","B","C","D","E"};

    public Map<String,Object> exportFile(){
        Map<String,Object> result = new HashMap<String,Object>();
        OssRefFileManager ossRefFileManager  = (OssRefFileManager)SpringHelper.getBean("ossRefFileManager");
        UtilityManager utilityManager = (UtilityManager)SpringHelper.getBean("utilityManager");
        String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+ File.separator + UPLOAD_DIRECTORY;
        String fileName_part = utilityManager.getPYIndexStr("物业费",true);
        // 如果目录不存在则创建
        File uploadDir = new File(str_file_dir_path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab

        HSSFSheet sheet = workbook.createSheet("物业费");

        // 设置列宽  （第几列，宽度）
        sheet.setColumnWidth( 0, 1600);
        sheet.setColumnWidth( 1, 4500);
        sheet.setColumnWidth( 2, 5500);
        sheet.setColumnWidth( 3, 7500);
        sheet.setColumnWidth( 4, 4500);
        sheet.setColumnWidth( 5, 4500);




        sheet.setDefaultRowHeight((short)360);//设置行高
        sheet.createFreezePane(3,0,5,0);
        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("宋体");
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headfont.setFontHeightInPoints((short) 14);// 字体大小
        HSSFCellStyle headstyle = workbook.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        headstyle.setLocked(true);

        //右侧单元格样式
        HSSFCellStyle headstyle2 = workbook.createCellStyle();
        headstyle2.setFont(headfont);
        headstyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        headstyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        headstyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        headstyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        headstyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        headstyle2.setFillForegroundColor(IndexedColors.LIME.getIndex());
        headstyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headstyle2.setLocked(true);

        HSSFCellStyle sumstyle = workbook.createCellStyle();
        sumstyle.setFont(headfont);
        sumstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        sumstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        sumstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        sumstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        sumstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        sumstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        sumstyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        sumstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        sumstyle.setLocked(true);

        //固定列样式
        HSSFCellStyle fixedStyle = workbook.createCellStyle();
//        fixedStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
//        fixedStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        fixedStyle.setFont(headfont);
        fixedStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        fixedStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        fixedStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        fixedStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        fixedStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        fixedStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        fixedStyle.setLocked(true);

        // 列名样式
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);// 字体大小
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setFillForegroundColor(IndexedColors.LIME.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setLocked(true);

        // 普通单元格样式（中文）
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short) 12);
        //font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style2.setFont(font2);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        //style2.setFillForegroundColor(IndexedColors.LIME.getIndex());
        //style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setLocked(true);

        // 第一行表头标题
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i <6; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header0[i]);
            cell.setCellStyle(headstyle);
        }



        // 设置列值-内容
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);

            cell.setCellValue(i+1);
            cell.setCellStyle(fixedStyle);
            for (int j = 0; j < colName.length; j++) {
                Map tempmap = (HashMap) data.get(i);
                Object data = tempmap.get(colName[j]);
                cell = row.createCell(j+1);

                if(j<2){
                    cell.setCellStyle(fixedStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(new HSSFRichTextString(data==null?"":data.toString()));
                }else if(j>=2){
                    cell.setCellStyle(style);
                    if (data==null){

                        cell.setCellType(HSSFCell.CELL_TYPE_BLANK);

                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(data.toString()));
                    }
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
            workbook.write(os);
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
