package com.cnpc.pms.costStatistics.util;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.util
 * @Description:导出人工成本模板
 * @Author: gbl
 * @CreateDate: 2018/8/16 13:35
 */
public class CostLaborExcel {

    // 上传文件本地存储目录
    private static final String UPLOAD_DIRECTORY = "template";

    //数据源
    private List<Map<String,Object>> data;

    public CostLaborExcel(List<Map<String,Object>> data){
        this.data = data;
    }
    //导出的文件标题
    private final String[] header0={"序号","门店编码","门店名称","1月","1月","1月","1月","2月","2月","2月","2月","3月","3月","3月","3月","4月","4月","4月","4月","5月","5月","5月","5月","6月","6月","6月","6月","7月","7月","7月","7月","8月","8月","8月","8月","9月","9月","9月","9月","10月","10月","10月","10月","11月","11月","11月","11月","12月","12月","12月","12月","工服年费"};

    private final String[] header1 = { "员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）","员工薪酬","工服摊销","住宿星店房租","小计（元）"};//在excel中的第2行每列的参数

    private final String[] headnum0 = { "0,1,0,0","0,1,1,1", "0,1,2,2","0,0,3,6", "0,0,7,10", "0,0,11,14","0,0,15,18","0,0,19,22","0,0,23,26","0,0,27,30","0,0,31,34","0,0,35,38","0,0,39,42","0,0,43,46","0,0,47,50","0,1,51,51"};//对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}

    private final String[] headnum1 = { "1,1,3,3","1,1,4,4","1,1,5,5","1,1,6,6","1,1,7,7","1,1,8,8","1,1,9,9","1,1,10,10","1,1,11,11","1,1,12,12","1,1,13,13","1,1,14,14","1,1,15,15","1,1,16,16","1,1,17,17","1,1,18,18","1,1,19,19","1,1,20,20","1,1,21,21","1,1,22,22","1,1,23,23","1,1,24,24","1,1,25,25","1,1,26,26","1,1,27,27","1,1,28,28","1,1,29,29","1,1,30,30","1,1,31,31","1,1,32,32","1,1,33,33","1,1,34,34","1,1,35,35","1,1,36,36","1,1,37,37","1,1,38,38","1,1,39,39","1,1,40,40","1,1,41,41","1,1,42,42","1,1,43,43","1,1,44,44","1,1,45,45","1,1,46,46","1,1,47,47","1,1,48,48","1,1,49,49","1,1,50,50","1,1,51,51"};

    private final String[] colName = new String[] { "storeNo", "storeName", "emolument1","uniform_amortize1", "accommodation1", "subtotal1","emolument2","uniform_amortize2", "accommodation2", "subtotal2","emolument3","uniform_amortize3", "accommodation3", "subtotal3","emolument4","uniform_amortize4", "accommodation4", "subtotal4","emolument5","uniform_amortize5", "accommodation5", "subtotal5","emolument6","uniform_amortize6", "accommodation6", "subtotal6","emolument7","uniform_amortize7", "accommodation7", "subtotal7","emolument8","uniform_amortize8", "accommodation8", "subtotal8","emolument9","uniform_amortize9", "accommodation9", "subtotal9",
                                      "emolument10","uniform_amortize10", "accommodation10", "subtotal10","emolument11","uniform_amortize11", "accommodation11", "subtotal11","emolument12","uniform_amortize12", "accommodation12", "subtotal12","uniform_charge"};//需要显示在excel中的参数对应的值，因为是用map存的，放的都是对应的key

    private final String[] rowColNum=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU","AV","AW","AX","AY","AZ"};



    public Map<String,Object> exportFile(){
        Map<String,Object> result = new HashMap<String,Object>();
        OssRefFileManager ossRefFileManager  = (OssRefFileManager)SpringHelper.getBean("ossRefFileManager");
        UtilityManager utilityManager = (UtilityManager)SpringHelper.getBean("utilityManager");
        String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+ File.separator + UPLOAD_DIRECTORY;
        String fileName_part = utilityManager.getPYIndexStr("人工成本",true);
        // 如果目录不存在则创建
        File uploadDir = new File(str_file_dir_path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab

        HSSFSheet sheet = workbook.createSheet("人工成本");

        // 设置列宽  （第几列，宽度）
        sheet.setColumnWidth( 0, 1600);
        sheet.setColumnWidth( 1, 4500);
        sheet.setColumnWidth( 2, 5500);
        sheet.setColumnWidth( 3, 4500);
        sheet.setColumnWidth( 4, 4500);
        sheet.setColumnWidth( 5, 4500);
        sheet.setColumnWidth( 6, 4500);
        sheet.setColumnWidth( 7, 4500);
        sheet.setColumnWidth( 8, 4500);
        sheet.setColumnWidth( 9, 4500);
        sheet.setColumnWidth( 10, 4500);
        sheet.setColumnWidth( 11, 4500);
        sheet.setColumnWidth( 12, 4500);
        sheet.setColumnWidth( 13, 4500);
        sheet.setColumnWidth( 14, 4500);
        sheet.setColumnWidth( 15, 4500);
        sheet.setColumnWidth( 16, 4500);
        sheet.setColumnWidth( 17, 4500);
        sheet.setColumnWidth( 18, 4500);
        sheet.setColumnWidth( 19, 4500);
        sheet.setColumnWidth( 20, 4500);
        sheet.setColumnWidth( 21, 4500);
        sheet.setColumnWidth( 22, 4500);
        sheet.setColumnWidth( 23, 4500);
        sheet.setColumnWidth( 24, 4500);
        sheet.setColumnWidth( 25, 4500);
        sheet.setColumnWidth( 26, 4500);
        sheet.setColumnWidth( 27, 4500);
        sheet.setColumnWidth( 28, 4500);
        sheet.setColumnWidth( 29, 4500);
        sheet.setColumnWidth( 30, 4500);
        sheet.setColumnWidth( 31, 4500);
        sheet.setColumnWidth( 32, 4500);
        sheet.setColumnWidth( 33, 4500);
        sheet.setColumnWidth( 34, 4500);
        sheet.setColumnWidth( 35, 4500);
        sheet.setColumnWidth( 36, 4500);
        sheet.setColumnWidth( 37, 4500);
        sheet.setColumnWidth( 38, 4500);
        sheet.setColumnWidth( 39, 4500);
        sheet.setColumnWidth( 40, 4500);
        sheet.setColumnWidth( 41, 4500);
        sheet.setColumnWidth( 42, 4500);
        sheet.setColumnWidth( 43, 4500);
        sheet.setColumnWidth( 44, 4500);
        sheet.setColumnWidth( 45, 4500);
        sheet.setColumnWidth( 46, 4500);
        sheet.setColumnWidth( 47, 4500);
        sheet.setColumnWidth( 48, 4500);
        sheet.setColumnWidth( 49, 4500);
        sheet.setColumnWidth( 50, 4500);
        sheet.setColumnWidth( 51, 4500);
        sheet.setColumnWidth( 52, 4500);

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
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style2.setFont(font2);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style2.setFillForegroundColor(IndexedColors.LIME.getIndex());
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setLocked(true);

        // 第一行表头标题
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0; i < 52; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header0[i]);

            if(i>2){
                cell.setCellStyle(headstyle2);
            }else{
                cell.setCellStyle(headstyle);
            }
        }
        //动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,startcol, overcol));

        }
        //设置合并单元格的参数并初始化带边框的表头（这样做可以避免因为合并单元格后有的单元格的边框显示不出来）
        row = sheet.createRow(1);//因为下标从0开始，所以这里表示的是excel中的第二行
        for (int i = 0; i < header0.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(style);//设置excel中第二行的列边框
            if(i > 1 && i< 51) {
                for (int j = 0; j < header1.length; j++) {
                    cell = row.createCell(j + 3);
                    cell.setCellValue(header1[j]);//给excel中第二行的列赋值
                    if(i>2){
                        cell.setCellStyle(headstyle2);
                    }else{
                        cell.setCellStyle(headstyle);
                    }
                }
            }
        }
        //动态合并单元格
        for (int i = 0; i < headnum1.length; i++) {
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow,
                    startcol, overcol));
        }

       // 设置列值-内容
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i + 2);
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
                }else{


                    if(colName[j].indexOf("subtotal")>=0){//小计（元）添加公式
                        cell.setCellStyle(style2);
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellFormula(rowColNum[j]+(i+3)+"+"+rowColNum[j-1]+(i+3)+"+"+rowColNum[j-2]+(i+3));

                    }else{
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
        }
        row = sheet.createRow(data.size()+2);
        for(int i=0;i<52;i++){
            cell = row.createCell(i);
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellStyle(sumstyle);
            if(i<3){
                cell.setCellValue("合计");
            }else{
                cell.setCellFormula("SUM("+rowColNum[i]+3+":"+rowColNum[i]+(data.size()+1)+")");//合计添加公式
            }

        }
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 2));





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
