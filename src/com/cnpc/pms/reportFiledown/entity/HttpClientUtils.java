package com.cnpc.pms.reportFiledown.entity;

import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.personal.manager.OssRefFileManager;
import com.cnpc.pms.platform.dao.OrderAmountDao;
import com.cnpc.pms.platform.entity.OrderAmountDto;
import com.cnpc.pms.utils.DateUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpClientUtils {

    public static String getPingYin(String src) {

        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else
                    t4 += Character.toString(t1[i]);
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    public void getDataTable( MassOrderDto massOrderDto, String fileName, MassOrderDao massOrderDao,Long id) {
        Map<String, Object> result = new HashMap<String,Object>();
        String url = null;
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        try {
            list=massOrderDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list!=null&&list.size()>0){//成功返回数据
            //定义表头 以及 要填入的 字段
            String[] str_headers = {"订单号","片区编号","小区编号","片区A国安侠编号","用户电话","用户ID","有效金额","交易金额","应付金额","下单时间","预约时间","签收时间","退货时间","完成时间","送单侠姓名","送单侠电话","E店名称","门店名称",
                    "门店编号","实际门店","实际门店编号","事业群","频道","首单频道","首单事业群","城市","是否公海订单","是否异常订单","是否已退款","是否小贷","是否快周边","是否微信礼品卡","是否拉新","是否集采订单","是否开卡礼订单","是否试用礼订单",
                    "是否积分订单","是否221商品类订单","是否221服务类订单","是否221团购订单","是否社员订单","是否过账工资","是否无精确成本","是否A类营销费用","订单来源","销售收入","销售毛利","首单频道毛利","本单频道毛利","结算方式","平台优惠券金额","粮票"};
            String[] headers_key = {"order_sn","area_code","village_code","info_employee_a_no","customer_mobile_phone","customer_id","gmv_price","trading_price","payable_price","create_time","appointment_start_time","sign_time","return_time",
                    "success_time","employee_name","employee_phone","eshop_name","store_name","store_code","real_store_name","real_store_code","department_name","channel_name","first_channel_name","first_dept_name","store_city_name","pubseas_label",
                    "abnormal_label","return_label","loan_label","quick_label","gift_label","customer_isnew_flag","order_tag_b","order_tag_k","order_tag_s","score","order_tag_product","order_tag_service","order_tag_groupon","order_tag_member","pay_label",
                    "no_cost_label","a_fee_label","order_source","order_profit","sale_profit","first_channel_profit","this_channel_profit","contract_method","apportion_coupon","apportion_rebate"};

            String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template/";
            String str_web_path = PropertiesUtil.getValue("file.web.root");
            File file_xls = new File(str_file_dir_path + fileName+".csv");
            if(file_xls.exists()){
                file_xls.delete();
            }
            FileOutputStream os = null;
            try {
                file_xls.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_xls), "GB2312");

                for(String title : str_headers){
                    out.write(title);
                    out.write(",");
                }
                out.write("\r\n");
                for(int i = 0;i < list.size();i++){
                    for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++) {
                        String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
                        if(cellIndex==4 && "normal".equals(massOrderDto.getHidden_flag())){
                            if(com.cnpc.pms.base.file.comm.utils.StringUtils.isNotEmpty(value) && value.length() > 7 ){
                                value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
                            }
                        }
                        if(value.contains(",")){
                            //如果还有双引号，先将双引号转义，避免两边加了双引号后转义错误
                            if(value.contains("\"")){
                                value=value.replace("\"", "\"\"");
                            }
                            //将逗号转义
                            value="\""+value+"\"";

                        }
                        if( value.contains("\n")){

                            value=value.replace("\n", " ");
                            //将逗号转义
                            value="\""+value+"\"";
                        }
                        if(cellIndex == 0 || cellIndex == 2 || cellIndex == 5 ||cellIndex == 9||cellIndex == 10||cellIndex == 11||cellIndex == 12||cellIndex == 13){
                            out.write(value+'\t');
                        }else{
                            out.write(value);
                        }
                        out.write(",");
                        continue;
                    }

                    //写完一行换行
                    out.write("\r\n");

                }
                out.close();

                OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
//                url = ossRefFileManager.uploadOssFile(file_xls, "xlsx", "daqWeb/download/");
                url = ossRefFileManager.uploadOssFileNew(file_xls, "csv", "daqWeb/download/",fileName);

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
            massOrderDao.updataReport(id,url);
        }else{
            massOrderDao.updataReport(id,"");
        }
    }
  //订单结算导出
    public void getAmountOrderTable( OrderAmountDto orderAmountDto, String fileName, OrderAmountDao orderAmountDao,Long id) {
        Map<String, Object> result = new HashMap<String,Object>();
        String url = null;
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
    		try {
    /*			String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
    			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    			if(StringUtils.isNotEmpty(orderAmountDto.getBeginDate()) && DateUtils.compareDate(orderAmountDto.getBeginDate(), format.format(new Date()))==0){
    				list = orderAmountDao.exportOrder(orderAmountDto, "df_mass_order_daily");
    			}else if(StringUtils.isNotEmpty(orderAmountDto.getBeginDate()) && DateUtils.compareDate(orderAmountDto.getBeginDate(),preMonthFirst)>=0){
    				list = orderAmountDao.exportOrder(orderAmountDto,"df_mass_order_monthly");
    			}else{*/
    				list = orderAmountDao.exportOrder(orderAmountDto, "df_mass_order_total");
    		/*	}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list!=null&&list.size()>0){//成功返回数据
            //定义表头 以及 要填入的 字段

            String[] str_headers = {"订单号","结算类型","成功时间","片区编号","小区编号","片区A国安侠编号","用户电话","用户ID","有效金额","交易金额","应付金额","下单时间","预约时间","签收时间","退货时间","送单侠姓名","送单侠电话","E店名称","门店名称",
                    "门店编号","事业群","频道","城市","是否公海订单","是否异常订单","是否已退款","是否小贷","是否快周边","是否微信礼品卡","是否拉新","是否集采订单","是否开卡礼订单","是否试用礼订单",
                    "是否积分订单","是否221商品类订单","是否221服务类订单","是否221团购订单","是否社员订单","是否过账工资","是否无精确成本","是否A类营销费用","订单来源","销售收入","结算方式","平台优惠券金额","粮票"};
            String[] headers_key = {"order_sn","linetype","success_time","area_code","village_code","info_employee_a_no","customer_mobile_phone","customer_id","gmv_price","trading_price","payable_price","create_time","appointment_start_time","sign_time","return_time",
                    "employee_name","employee_phone","eshop_name","store_name","store_code","department_name","channel_name","store_city_name","pubseas_label","abnormal_label","return_label","loan_label","quick_label","gift_label",
                    "customer_isnew_flag","order_tag_b","order_tag_k","order_tag_s","score","order_tag_product","order_tag_service","order_tag_groupon","order_tag_member","pay_label","no_cost_label","a_fee_label",
                    "order_source","order_profit","contract_method","apportion_coupon","apportion_rebate"};
            String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template/";
            String str_web_path = PropertiesUtil.getValue("file.web.root");
            File file_xls = new File(str_file_dir_path + fileName+".csv");
            if(file_xls.exists()){
                file_xls.delete();
            }
            FileOutputStream os = null;
            try {
                file_xls.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_xls), "GB2312");

                for(String title : str_headers){
                    out.write(title);
                    out.write(",");
                }
                out.write("\r\n");
                for(int i = 0;i < list.size();i++){
                    for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++) {
                        String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
                        if(cellIndex==6 && "normal".equals(orderAmountDto.getHidden_flag())){
                            if(com.cnpc.pms.base.file.comm.utils.StringUtils.isNotEmpty(value) && value.length() > 7 ){
                                value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
                            }
                        }
                        if(value.contains(",")){
                            //如果还有双引号，先将双引号转义，避免两边加了双引号后转义错误
                            if(value.contains("\"")){
                                value=value.replace("\"", "\"\"");
                            }
                            //将逗号转义
                            value="\""+value+"\"";

                        }
                        if( value.contains("\n")){

                            value=value.replace("\n", " ");
                            //将逗号转义
                            value="\""+value+"\"";
                        }
                        if(cellIndex == 0 ||cellIndex == 2 || cellIndex == 4 || cellIndex == 7 ||cellIndex == 11||cellIndex == 12||cellIndex == 13||cellIndex == 14){
                            out.write(value+'\t');
                        }else{
                            out.write(value);
                        }
                        out.write(",");
                        continue;
                    }

                    //写完一行换行
                    out.write("\r\n");

                }
                out.close();

                OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
//                url = ossRefFileManager.uploadOssFile(file_xls, "xlsx", "daqWeb/download/");
                url = ossRefFileManager.uploadOssFileNew(file_xls, "csv", "daqWeb/download/",fileName);

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
            orderAmountDao.updataReport(id,url);
        }else {
            orderAmountDao.updataReport(id,"");
        }

    }
    public void getDataTableSPXSDA( MassOrderItemDto massOrderDto, String fileName, MassOrderItemDao massOrderItemDao, Long id) {
        String url = null;
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
        }
        if(list!=null&&list.size()>0){//成功返回数据

            //定义表头 以及 要填入的 字段
            String[] str_headers = {"商品名称","商品ID","订单号","下单客户姓名","下单客户电话","预约时间","下单时间","签收时间","订单评价时间","单价","销售数量","签收客户姓名","签收客户电话","片区编号","小区编号","片区A国安侠编号","送单侠姓名",
                    "送单侠电话","签收地址","E店名称","门店名称","门店编号","事业群","频道","城市","订单来源","商品评价星级","订单配送速度评价星级","订单配送服务评价星级","评价信息","评价和追评的间隔天数","追加评论内容"};
            String[] headers_key = {"product_name","product_id","order_sn","customer_name","customer_mobilephone","appointment_start_time","create_time","df_signed_time","order_commented_time","original_price","quantity","order_customer_name","order_mobilephone","area_code","village_code",
                    "info_employee_a_no","employee_name","employee_phone","order_address",
                    "eshop_name","store_name","store_code","dep_name","channel_name","store_city_name","order_source","star_level","star_level_1","star_level_2","order_contents","next_days","next_contents"};

            String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template/";
            String str_web_path = PropertiesUtil.getValue("file.web.root");
            File file_xls = new File(str_file_dir_path + fileName+".csv");
            if(file_xls.exists()){
                file_xls.delete();
            }
            FileOutputStream os = null;

            try {
                file_xls.createNewFile();
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_xls), "GB2312");
                for(String title : str_headers){
                    out.write(title);
                    out.write(",");
                }
                out.write("\r\n");
                    for(int i = 0;i < list.size();i++){
                        for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++) {
                            String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
                            if(cellIndex==4 && "normal".equals(massOrderDto.getHidden_flag())){
                                if(StringUtils.isNotEmpty(value) && value.length() > 7 ){
                                    value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
                                }
                            }else if(cellIndex==26||cellIndex==27||cellIndex==28||cellIndex==30){
                                if("NULL".equals(value)||"null".equals(value)){
                                    value = "";
                                }
                            }
                            if(value.contains(",")){
                                //如果还有双引号，先将双引号转义，避免两边加了双引号后转义错误
                                if(value.contains("\"")){
                                    value=value.replace("\"", "\"\"");
                                }
                                //将逗号转义
                                value="\""+value+"\"";
                            }
                            if( value.contains("\n")){

                                value=value.replace("\n", " ");
                                //将逗号转义
                                value="\""+value+"\"";
                            }
                            if(cellIndex == 2 ||cellIndex == 6||cellIndex == 7||cellIndex == 8||cellIndex == 5||cellIndex == 14){
                                out.write(value+'\t');
                            }else{
                                out.write(value);
                            }
                            out.write(",");
                            continue;
                        }
                        //写完一行换行
                        out.write("\r\n");


                }
                out.close();

                OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
                url = ossRefFileManager.uploadOssFileNew(file_xls, "csv", "daqWeb/download/",fileName);

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
            massOrderItemDao.updataReport(id,url);
        }else {
            massOrderItemDao.updataReport(id,"");
        }
    }

//    public void getDataTableSPXSDA( MassOrderItemDto massOrderDto, String fileName, MassOrderItemDao massOrderItemDao, Long id) {
//        String url = null;
//        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//        String sql = "";
//        try {
//            String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
//            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//            if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) && DateUtils.compareDate(massOrderDto.getBeginDate(), format.format(new Date()))==0){
////                list=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.CUR_DAY.code);
//            }else if(StringUtils.isNotEmpty(massOrderDto.getBeginDate()) && DateUtils.compareDate(massOrderDto.getBeginDate(),preMonthFirst)>=0){
//                sql=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.LATEST_MONTH.code);
//            }else{
////                list=massOrderItemDao.exportOrder(massOrderDto, MassOrderDto.TimeFlag.HISTORY_MONTH.code);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(sql!=null){//成功返回数据
//            FileOutputStream os = null;
//            Connection conn = null;
//            PreparedStatement ps = null;
//            ResultSet resultSet = null;
//            //定义表头 以及 要填入的 字段
////            String[] str_headers = {"商品名称","商品ID","订单号","下单客户姓名","下单客户电话","预约时间","下单时间","签收时间","订单评价时间","单价","销售数量","签收客户姓名","签收客户电话","片区编号","小区编号","片区A国安侠编号","送单侠姓名",
////                    "送单侠电话","签收地址","E店名称","门店名称","门店编号","事业群","频道","城市","订单来源","商品评价星级","订单配送速度评价星级","订单配送服务评价星级","评价信息","评价和追评的间隔天数","追加评论内容"};
////            String[] headers_key = {"product_name","product_id","order_sn","customer_name","customer_mobilephone","appointment_start_time","create_time","df_signed_time","order_commented_time","original_price","quantity","order_customer_name","order_mobilephone","area_code","village_code",
////                    "info_employee_a_no","employee_name","employee_phone","order_address",
////                    "eshop_name","store_name","store_code","dep_name","channel_name","store_city_name","order_source","star_level","star_level_1","star_level_2","order_contents","next_days","next_contents"};
//
//            String str_file_dir_path = this.getClass().getClassLoader().getResource("../../").getPath()+"template/";
//            String str_web_path = PropertiesUtil.getValue("file.web.root");
//            File file_xls = new File(str_file_dir_path + fileName+".csv");
//            if(file_xls.exists()){
//                file_xls.delete();
//            }
//
//            try {
//                file_xls.createNewFile();
//                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file_xls), "GB2312");
////                for(String title : str_headers){
////                    out.write(title);
////                    out.write(",");
////                }
////                out.write("\r\n");
////                for(int i = 0;i < list.size();i++){
////                    for(int cellIndex = 0;cellIndex < headers_key.length; cellIndex ++) {
////                        String value = String.valueOf(list.get(i).get(headers_key[cellIndex]));
////                        if(cellIndex==4 && "normal".equals(massOrderDto.getHidden_flag())){
////                            if(StringUtils.isNotEmpty(value) && value.length() > 7 ){
////                                value = value.substring(0, 3) + "****" + value.substring(value.length() - 4);
////                            }
////                        }else if(cellIndex==26||cellIndex==27||cellIndex==28||cellIndex==30){
////                            if("NULL".equals(value)||"null".equals(value)){
////                                value = "";
////                            }
////                        }
////                        if(value.contains(",")){
////                            //如果还有双引号，先将双引号转义，避免两边加了双引号后转义错误
////                            if(value.contains("\"")){
////                                value=value.replace("\"", "\"\"");
////                            }
////                            //将逗号转义
////                            value="\""+value+"\"";
////                        }
////                        if( value.contains("\n")){
////
////                            value=value.replace("\n", " ");
////                            //将逗号转义
////                            value="\""+value+"\"";
////                        }
////                        if(cellIndex == 2 ||cellIndex == 6||cellIndex == 7||cellIndex == 8||cellIndex == 5||cellIndex == 14){
////                            out.write(value+'\t');
////                        }else{
////                            out.write(value);
////                        }
////                        out.write(",");
////                        continue;
////                    }
////                    //写完一行换行
////                    out.write("\r\n");
////
////
////                }
////                out.close();
//                conn = HikariGuoanInner.getConnection();
//                ps = conn.prepareStatement(sql);
//                resultSet = ps.executeQuery();
//                CSVWriter csvWriter = new CSVWriter(out);
////            csvWriter.writeAll(head);
//                csvWriter.writeAll(resultSet, true);
//                csvWriter.close();
//                OssRefFileManager ossRefFileManager = (OssRefFileManager) SpringHelper.getBean("ossRefFileManager");
//                url = ossRefFileManager.uploadOssFileNew(file_xls, "csv", "daqWeb/download/",fileName);
//
//            }catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if(os != null){
//                    try {
//                        os.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            massOrderItemDao.updataReport(id,url);
//        }
//    }


    private XSSFCellStyle style_header = null;
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

    CellStyle cellStyle_common = null;
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
