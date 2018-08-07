package com.cnpc.pms.observe.manager.impl;

import com.cnpc.pms.base.file.comm.utils.StringUtils;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.PropertiesUtil;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.observe.dao.ObserveModelDao;
import com.cnpc.pms.observe.dao.StoreObserveParameterDao;
import com.cnpc.pms.observe.dao.StoreObserveParameterScoreDao;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.StoreObserveParameter;
import com.cnpc.pms.observe.entity.StoreObserveParameterScore;
import com.cnpc.pms.observe.manager.StoreObserveParameterManager;
import com.cnpc.pms.observe.manager.StoreObserveParameterScoreManager;
import com.cnpc.pms.personal.dto.StoreDTO;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.personal.manager.StoreManager;
import com.cnpc.pms.utils.DateUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by h on 2018/8/6.
 */
public class StoreObserveParameterManagerImpl extends BizBaseCommonManager implements StoreObserveParameterManager {

   private CellStyle style_header = null;
    private CellStyle cellStyle_common = null;

    @Override
    public Map<String,Object> queryObserveParameterList(ObserveDTO observeDTO){
        Map<String,Object> result = new HashMap<String, Object>();
        ObserveModelDao observeModelDao = (ObserveModelDao)SpringHelper.getBean(ObserveModelDao.class.getName());
        StoreObserveParameterDao storeObserveParameterDao = (StoreObserveParameterDao)SpringHelper.getBean(StoreObserveParameterDao.class.getName());
        String add_or_edit = observeDTO.getAdd_or_edit();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        if(add_or_edit.equals("edit")){
            StoreObserveParameterScoreDao storeObserveParameterScoreDao = (StoreObserveParameterScoreDao)SpringHelper.getBean(StoreObserveParameterScoreDao.class.getName());
            maps = storeObserveParameterDao.queryObserveParameterList(observeDTO.getStore_id(), observeDTO.getObserve_month());
            List<Map<String, Object>> observeScore = storeObserveParameterScoreDao.queryObserveScoreByStoreAndObserveMonth(observeDTO.getStore_id(), observeDTO.getObserve_month());
            result.put("observeScore",observeScore);
        }else if(add_or_edit.equals("add")){
            maps = observeModelDao.getObserveList();
        }
        List<Map<String, Object>> observeModelList = observeModelDao.getObserveModelList();
        result.put("observeParameterList",maps);
        result.put("observeModelList",observeModelList);
        return result;
    }

    @Override
    public Map<String, Object> saveObserveParameter(List<Map<String, Object>> StoreObserveParameterlist, StoreObserveParameterScore storeObserveParameterScore) {
        Map<String,Object> result = new HashMap<String, Object>();
        Long store_id = storeObserveParameterScore.getStore_id();
        StoreManager storeManager = (StoreManager)SpringHelper.getBean("storeManager");
        StoreObserveParameterScoreManager storeObserveParameterScoreManager = (StoreObserveParameterScoreManager)SpringHelper.getBean("storeObserveParameterScoreManager");
        Store findStore = storeManager.findStore(store_id);
        String storeno = findStore.getStoreno();
        String cityName = findStore.getCityName();
        try {
            if(StoreObserveParameterlist.size()>0 && storeObserveParameterScore != null){
                StoreObserveParameter storeObserveParameter = null;
                for(int i = 0; i < StoreObserveParameterlist.size(); i++){
                    storeObserveParameter = new StoreObserveParameter();
                    storeObserveParameter.setStoreno(storeno);
                    Map<String, Object> stringObjectMap = StoreObserveParameterlist.get(i);
                    Long check_details_id =  Long.parseLong(stringObjectMap.get("id").toString());
                    String observe_month = storeObserveParameterScore.getObserve_month();
                    storeObserveParameter.setStore_id(storeObserveParameterScore.getStore_id());
                    storeObserveParameter.setObserve_month(storeObserveParameterScore.getObserve_month());
                    storeObserveParameter.setContent_score(stringObjectMap.get("content_score") == null ? "":stringObjectMap.get("content_score").toString());
                    storeObserveParameter.setCheck_details_id(Long.parseLong(stringObjectMap.get("id").toString()));
                    storeObserveParameter.setPoints_deduction_description(stringObjectMap.get("points_deduction_description") == null ? "":stringObjectMap.get("points_deduction_description").toString());
                    storeObserveParameter.setScore_empno_empname(stringObjectMap.get("score_empno_empname") == null ? "":stringObjectMap.get("score_empno_empname").toString());
                    StoreObserveParameter storeObserveParameter1 = this.getObserveParameterByStoreAndMonthAndDetailsId(store_id, observe_month, check_details_id);
                    if(storeObserveParameter1 != null){
                        BeanUtils.copyProperties(storeObserveParameter, storeObserveParameter1,
                                new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
                        preObject(storeObserveParameter1);
                        this.saveObject(storeObserveParameter1);
                    }else{
                        preObject(storeObserveParameter);
                        this.saveObject(storeObserveParameter);
                    }
                }
                storeObserveParameterScore.setStoreno(storeno);
                storeObserveParameterScore.setCity_name(cityName);
                storeObserveParameterScoreManager.updateStoreObserveParameterScore(storeObserveParameterScore);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "-1");
            result.put("message", "update fail");
            return result;
        }

        result.put("code", "1");
        result.put("message", "update success");
        return result;
    }

    @Override
    public StoreObserveParameter getObserveParameterByStoreAndMonthAndDetailsId(Long storeid, String observemonth, Long check_details_id) {
        List<?> list = this.getList(FilterFactory.getSimpleFilter("store_id= '"+storeid+"' and observe_month = '"+observemonth+"' and check_details_id = "+ check_details_id));
        if (list != null && list.size() > 0) {
            StoreObserveParameter observeParameter = (StoreObserveParameter) list.get(0);
            return observeParameter;
        }
        return null;
    }

    private CellStyle getHeaderStyle(){
        return style_header;
    }

    private void setHeaderStyle(Workbook wb){

        // 创建单元格样式
        style_header = wb.createCellStyle();
        style_header.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style_header.setAlignment(CellStyle.ALIGN_CENTER);
        style_header.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style_header.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        style_header.setFillPattern(CellStyle.SOLID_FOREGROUND);

        // 设置边框
        style_header.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style_header.setBorderBottom(CellStyle.BORDER_THIN);
        style_header.setBorderLeft(CellStyle.BORDER_THIN);
        style_header.setBorderRight(CellStyle.BORDER_THIN);
        style_header.setBorderTop(CellStyle.BORDER_THIN);

    }

    private void setCellStyle_common(Workbook wb){
        cellStyle_common=wb.createCellStyle();
        cellStyle_common.setAlignment(CellStyle.ALIGN_CENTER); // 水平居中
        cellStyle_common.setVerticalAlignment(CellStyle.VERTICAL_TOP);//垂直居中

    }

    private CellStyle getCellStyle_common() {
        return cellStyle_common;
    }

    public void setCellValue(Row obj_row, int nCellIndex, Object value){
        Cell cell=obj_row.createCell(nCellIndex);
        cell.setCellStyle(getCellStyle_common());
        cell.setCellValue(new XSSFRichTextString(value==null?null:value.toString()));
        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
    }

    @Override
    public Map<String, Object> queryObserveParameterSummaryByCity(String cityname,String store_id,String observe_month,String employeeId) {
        Map<String,Object> result = new HashMap<String, Object>();
        StoreObserveParameterScoreDao observeParameterDao = (StoreObserveParameterScoreDao)SpringHelper.getBean(StoreObserveParameterScoreDao.class.getName());
        List<String> queryObserveMonthByCity = observeParameterDao.queryObserveMonthByCity(cityname,store_id,observe_month,employeeId);
        List<Map<String,Object>> queryObserveParameterSummaryByCity = observeParameterDao.queryObserveParameterSummaryByCity(cityname,store_id,observe_month,employeeId);
        result.put("queryObserveMonth", queryObserveMonthByCity);
        result.put("queryObserveParameterSummary", queryObserveParameterSummaryByCity);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> exportObserveParamterSummary(StoreDTO storeDTO) {
        Map<String,Object> result  = new HashMap<String,Object>();
        String cityname = storeDTO.getCityname();
        String store_id = storeDTO.getStore_id();
        String observe_month = storeDTO.getObserve_month();
        String beforeMonth = DateUtils.getBeforeMonth(observe_month, -1);
        String employeeId = storeDTO.getEmployeeId();
        Map<String,Object> map  = this.queryObserveParameterSummaryByCity(cityname,store_id,observe_month,employeeId);
        List<String> storenameList = new ArrayList<>();
        List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("queryObserveParameterSummary");
        List<String> listmonth = (List<String>)map.get("queryObserveMonth");
        List<Map<String,Object>> storeInfoList = new ArrayList<Map<String,Object>>();
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        for(int i = 0; i<list.size(); i++){
            Map<String, Object> mapobj = list.get(i);
            String storename = mapobj.get("store_name").toString();
            if(!storenameList.contains(storename)){
                storenameList.add(storename);
                Map<String,Object> storeInfo = new HashMap<String,Object>();
                storeInfo.put("storename", mapobj.get("store_name"));
                storeInfo.put("storeno", mapobj.get("storeno"));
                storeInfo.put("rmname", mapobj.get("rmname"));
                storeInfo.put("skname", mapobj.get("skname"));
                storeInfo.put("observe_date", mapobj.get("observe_date"));
                storeInfo.put("observe_person", mapobj.get("observe_person"));
                storeInfoList.add(storeInfo);
            }
            int index = storenameList.indexOf(storename);
            Map<String, Object> map2 = storeInfoList.get(index);
            map2.put(mapobj.get("observe_month").toString(), mapobj.get("points_combined"));
            map2.put(mapobj.get("observe_month").toString()+"quest", mapobj.get("observe_question_number"));
        }
        String str_file_dir_path = PropertiesUtil.getValue("file.root");
        String str_web_path = PropertiesUtil.getValue("file.web.root");

        XSSFWorkbook wb = new XSSFWorkbook();
        // 创建Excel的工作sheet,对应到一个excel文档的tab
        setCellStyle_common(wb);
        setHeaderStyle(wb);
        XSSFSheet sheet = wb.createSheet();
        wb.setSheetName(0," 门店明查台账汇总");
        XSSFRow row0 = sheet.createRow(0);//第一行
        XSSFRow row1 = sheet.createRow(1);//第二行
        XSSFRow row2 = sheet.createRow(2);//第三行
        //获得明查检查项列表详情；分为本月与上月
        StoreObserveParameterDao storeObserveParameterDaoImp = (StoreObserveParameterDao)SpringHelper.getBean(StoreObserveParameterDao.class.getName());
        ObserveModelDao observeModelDao = (ObserveModelDao)SpringHelper.getBean(ObserveModelDao.class.getName());
        List<String> observeContentList = observeModelDao.getObserveContentList();
        String[] observeContent = StringUtils.join(observeContentList.toArray(),",").split(",");
        String[] observeContentpre = Arrays.copyOf(observeContent, observeContent.length-1);
        observeContentpre[observeContentpre.length-1] = "岛屿";

        //获得哪个月份有数据作为表头
        String join1 = StringUtils.join(listmonth.toArray(), "月份得分,")+"月份得分";
        String replace1 = join1.replace("-", "年");
        String join2 = StringUtils.join(listmonth.toArray(), "月份问题数量,")+"月份问题数量";
        String replace2 = join2.replace("-", "年");
        //基本信息表头
        String[] str_headers = {"门店编号","门店名称","店长姓名","运营经理","检查日期","检查人"};
        String[] arr1 = replace1.split(",");
        String[] arr3 = replace2.split(",");
        String[] addAll = ArrayUtils.addAll(str_headers, arr1);
        //基本信息表头+获得哪个月份有数据作为表头
        String[] addAll_add = ArrayUtils.addAll(addAll,arr3);
        //基本信息key
        String[] headers_key = {"storeno","storename","skname","rmname","observe_date","observe_person"};
        String[] array1 = StringUtils.join(listmonth.toArray(),",").split(",");
        String array2 = StringUtils.join(listmonth.toArray(), "quest,")+"quest";
        String[] array3 = array2.split(",");
        String[] addAll_key = ArrayUtils.addAll(headers_key, array1);
        //基本信息key+获得哪个月份有数据作key
        String[] addAll2_key_add = ArrayUtils.addAll(addAll_key, array3);

        //第1行表头
        String[] addAll_add_pre = ArrayUtils.addAll(addAll_add,observeContentpre);
        String[] addAll2_header = ArrayUtils.addAll(addAll_add_pre,observeContent);
        String city = "";
        if(cityname != null && !"null".equals(cityname)){
            city = cityname;
        }
        addAll2_header[0] = observe_month+city+"社区门店明查问题汇总统计表(大表)（B表）";
        addAll2_header[addAll_add.length] = beforeMonth + "未整改问题";
        addAll2_header[addAll_add_pre.length] = observe_month + "新出现问题";
        addAll2_header[addAll2_header.length-2] = "严查专项/特殊检查";
        //第二行表头
        String[] addAll2 = ArrayUtils.addAll(addAll_add_pre,observeContent);
        addAll2[0] = "";
        //第三行表头
        //第二行表头
        String[] addAll3 = ArrayUtils.addAll(addAll_add_pre,observeContent);
        //第一行合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,addAll_add.length-1));
        sheet.addMergedRegion(new CellRangeAddress(0,0,addAll_add.length,addAll_add.length+observeContentpre.length-1));
        sheet.addMergedRegion(new CellRangeAddress(0,0,addAll_add.length+observeContentpre.length,addAll_add.length+observeContentpre.length+observeContent.length-3));
        sheet.addMergedRegion(new CellRangeAddress(0,0,addAll_add.length+observeContentpre.length+observeContent.length-2,addAll_add.length+observeContentpre.length+observeContent.length-1));
        //第二行合并单元格
        List<Map<String, Object>> observeModelList = observeModelDao.getObserveModelList();
        int end_length = 0;
        for(int z = 0; z < observeModelList.size()-2; z++){
            Integer count = Integer.parseInt(observeModelList.get(z).get("count").toString());
            if(z == 0){
                sheet.addMergedRegion(new CellRangeAddress(1,1,0,addAll_add.length-1));
                end_length = addAll_add.length;
                sheet.addMergedRegion(new CellRangeAddress(1,1,end_length,count+addAll_add.length-1));
                addAll2[end_length] = observeModelList.get(z).get("model_name").toString();
                end_length = count+addAll_add.length;
            }else{
                sheet.addMergedRegion(new CellRangeAddress(1,1,end_length,end_length+count-1));
                addAll2[end_length] = observeModelList.get(z).get("model_name").toString();
                end_length = end_length+count;
            }
        }
        end_length = end_length+1;
        for(int z = 0; z < observeModelList.size(); z++){
            Integer count = Integer.parseInt(observeModelList.get(z).get("count").toString());
                sheet.addMergedRegion(new CellRangeAddress(1,1,end_length,end_length+count-1));
                addAll2[end_length] = observeModelList.get(z).get("model_name").toString();
                end_length = end_length+count;
        }
        //第一行
        for(int i = 0;i < addAll2_header.length;i++){
            XSSFCell cell = row0.createCell(i);
            cell.setCellStyle(getHeaderStyle());
            cell.setCellValue(new XSSFRichTextString(addAll2_header[i]));
        }
        //第二行
        for(int i = 0;i < addAll2.length;i++){
            XSSFCell cell = row1.createCell(i);
            cell.setCellStyle(getHeaderStyle());
            cell.setCellValue(new XSSFRichTextString(addAll2[i]));
        }
        //第三行
        for(int i = 0;i < addAll3.length;i++){
            if(i>5){
                sheet.setColumnWidth(i, 20 * 256);
            }
            XSSFCell cell = row2.createCell(i);
            cell.setCellStyle(getHeaderStyle());
            cell.setCellValue(new XSSFRichTextString(addAll3[i]));
        }

        //正文内容
        for(int i = 2;i <= storeInfoList.size()+1;i++){
            row2 = sheet.createRow(i+1);
            for(int cellIndex = 0;cellIndex < addAll2_key_add.length; cellIndex ++){
                setCellValue(row2, cellIndex, storeInfoList.get(i-2).get(addAll2_key_add[cellIndex]));
            }
            String storeno = storeInfoList.get(i-2).get("storeno").toString();
            List<Map<String, Object>> maps_pre = storeObserveParameterDaoImp.queryObserveParameterListByStoreNo(storeno,observe_month, beforeMonth);
            for(int x = addAll2_key_add.length; x < maps_pre.size()+addAll2_key_add.length-2; x++){
                int index = x-addAll2_key_add.length;
                Object obj =(maps_pre.get(index).get("content_score_pre")== null || maps_pre.get(index).get("content_score_pre").equals(""))?null:"1";
                setCellValue(row2, x,obj);
            }
            setCellValue(row2, addAll2_key_add.length+maps_pre.size()-2,"岛屿");
            for(int x = addAll2_key_add.length+maps_pre.size()-1; x < maps_pre.size()+addAll2_key_add.length+maps_pre.size()-3; x++){
                int index = x-addAll2_key_add.length-maps_pre.size()+1;
                Object obj =(maps_pre.get(index).get("content_score")== null || maps_pre.get(index).get("content_score").equals(""))?null:"1";
                setCellValue(row2, x,obj);
            }
            short num = row2.getLastCellNum();
            List<Map<String, Object>> maps = storeObserveParameterDaoImp.queryCityObserveParameterListByStoreNo(storeno, observe_month);
            for(int x = num; x <num+maps.size(); x++){
                int index = x-num;
                Object obj =(maps.get(index).get("content_score")== null || maps.get(index).get("content_score").equals(""))?null:"1";
                setCellValue(row2, x,obj);
            }

        }

        File file_xls = new File(str_file_dir_path + File.separator+System.currentTimeMillis()+"observe.xlsx");
        if(file_xls.exists()){
            file_xls.delete();
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file_xls.getAbsoluteFile());
            wb.write(os);
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
        //return file_xls;
        result.put("message","导出成功！");
        result.put("status","success");
        result.put("data", str_web_path.concat(file_xls.getName()));
        return result;
    }
}
