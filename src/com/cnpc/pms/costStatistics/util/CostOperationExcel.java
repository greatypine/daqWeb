package com.cnpc.pms.costStatistics.util;

import com.cnpc.pms.base.common.manager.UtilityManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
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
 * @Description:运营成本
 * @Author: gbl
 * @CreateDate: 2018/9/20 10:09
 */
public class CostOperationExcel {

    // 上传文件本地存储目录
    private static final String UPLOAD_DIRECTORY = "template";

    //数据源
    private List<Map<String,Object>> data;

    public CostOperationExcel(List<Map<String,Object>> data){
        this.data = data;
    }
    //导出的文件标题
    private final String[] header0={"序号","门店编码","门店名称","月度运营费用（元/月）","年运营费用累计（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","年运营累计费用（元）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）","月度运营费用（元/月）"};

    private final String[] header1 = { "日常办公","仓储型星店房租","桶装水","门店保险","电动车维修费用","购物袋","垃圾袋","灭火器","车载包+背包","头盔+护膝+手套","绿植","托盘","仓储物资（周转箱、人字梯、拖车等）","门店营销活动费","门店装修及设备维修","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};//在excel中的第2行每列的参数

    private final String[] headnum0 = { "0,1,0,0","0,1,1,1", "0,1,2,2","0,1,3,3", "0,1,4,4", "0,0,5,19","0,0,20,31"};//对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}

    private final String[] headnum1 = { "1,1,5,5","1,1,6,6","1,1,7,7","1,1,8,8","1,1,9,9","1,1,10,10","1,1,11,11","1,1,12,12","1,1,13,13","1,1,14,14","1,1,15,15","1,1,16,16","1,1,17,17","1,1,18,18","1,1,19,19","1,1,20,20","1,1,21,21","1,1,22,22","1,1,23,23","1,1,24,24","1,1,25,25","1,1,26,26","1,1,27,27","1,1,28,28","1,1,29,29","1,1,30,30","1,1,31,31"};

    private final String[] colName = new String[] { "store_no", "store_name", "month_charge","year_charge", "daily_office", "rent","barrelled_water","store_insurance", "car_maintain", "shopping_bag","garbage_bag","extinguisher", "backpack", "helmet","greenPlants","tray", "storage_materials", "activity_fee","decoration_maintain","month_charge", "month_charge", "month_charge","month_charge","month_charge", "month_charge", "month_charge","month_charge","month_charge", "month_charge", "month_charge","month_charge" };//需要显示在excel中的参数对应的值，因为是用map存的，放的都是对应的key

    private final String[] rowColNum=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA","AB","AC","AD","AE","AF","AG"};

    public Map<String,Object> exportFile(){
        Map<String,Object> result = new HashMap<String,Object>();
        OssRefFileManager ossRefFileManager  = (OssRefFileManager)SpringHelper.getBean("ossRefFileManager");
        UtilityManager utilityManager = (UtilityManager)SpringHelper.getBean("utilityManager");
        String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+ File.separator + UPLOAD_DIRECTORY;
        String fileName_part = utilityManager.getPYIndexStr("运营成本",true);
        // 如果目录不存在则创建
        File uploadDir = new File(str_file_dir_path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }


        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab

        HSSFSheet sheet = workbook.createSheet("运营成本");

        // 设置列宽  （第几列，宽度）
        sheet.setColumnWidth( 0, 1600);
        sheet.setColumnWidth( 1, 4500);
        sheet.setColumnWidth( 2, 5500);
        sheet.setColumnWidth( 3, 7500);
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
        for (int i = 0; i < 32; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header0[i]);

            if(i>18){
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
            //cell.setCellStyle(style);//设置excel中第二行的列边框
            if(i > 1 && i< 32) {
                for (int j = 0; j < header1.length; j++) {
                    cell = row.createCell(j + 5);
                    cell.setCellValue(header1[j]);//给excel中第二行的列赋值
                    if(j>14){
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
                }else if(j>18&&j<=32){
                    cell.setCellStyle(style);
                    if (data==null){
                        cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(data.toString()));
                    }
                }else{

                    cell.setCellStyle(style2);
                    if (data==null){
                        cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Double.parseDouble(data.toString()));
                    }

                }
            }
        }
        row = sheet.createRow(data.size()+1);
        for(int i=0;i<32;i++){
            cell = row.createCell(i);
            cell.setCellStyle(sumstyle);
            if(i<3){
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue("合计");
            }else if(i>19&&i<=31){
                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellFormula("SUM("+rowColNum[i]+3+":"+rowColNum[i]+(data.size()+1)+")");//合计添加公式
            }else{
                cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
            }

        }
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 6));





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
