package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostGWE;
import com.cnpc.pms.costStatistics.manager.CostGWEManager;
import com.cnpc.pms.costStatistics.util.CostGWEExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 水电费
 * @Author: gbl
 * @CreateDate: 2018/9/14 10:11
 */
public class CostGWEManagerImpl extends BizBaseCommonManager implements CostGWEManager {

    @Override
    public Map<String, Object> saveCostGWE(List<Map<String, Object>> list) {

        Map<String,Object> result = new HashMap<String,Object>();
        IFilter filter = null;
        List<CostGWE> lst_costGWE=null;
        try {
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    Map<String,Object> obj = list.get(i);
                    String storeNo = obj.get("storeNo")==null?"":String.valueOf(obj.get("storeNo"));
                    String storeName = obj.get("storeName")==null?"":String.valueOf(obj.get("storeName"));
                    String cityName = obj.get("cityName")==null?"":String.valueOf(obj.get("cityName"));


                    Integer year = obj.get("year")==null?null:Integer.parseInt(String.valueOf(obj.get("year")));
                    Integer month = obj.get("month")==null?null:Integer.parseInt(String.valueOf(obj.get("month")));
                    Double electricityFee = obj.get("electricityFee")==null?null:Double.parseDouble(String.valueOf(obj.get("electricityFee")));
                    Double waterFee = obj.get("waterFee")==null?null:Double.parseDouble(String.valueOf(obj.get("waterFee")));

                    filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year+" and month="+month);
                    lst_costGWE = (List<CostGWE>) this.getList(filter);
                    CostGWE gwe = null;
                    if(lst_costGWE!=null&&lst_costGWE.size()>0) {
                        gwe = lst_costGWE.get(0);
                    }else{
                        gwe = new CostGWE();
                    }
                    gwe.setStoreNo(storeNo);
                    gwe.setCityName(cityName);
                    gwe.setStoreName(storeName);
                    gwe.setYear(year);
                    gwe.setMonth(month);
                    gwe.setElectricityFee(electricityFee);
                    gwe.setWaterFee(waterFee);
                    preObject(gwe);
                    saveObject(gwe);
                }
            }
            result.put("status","success");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status","success");
        }

        return result;
    }

    @Override
    public Map<String, Object> queryCostGWE(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostGWE(costDto);
        result.put("gwe",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostGWE(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostGWE(costDto);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }

        CostGWEExcel costOperationExcel = new CostGWEExcel(list);
        result = costOperationExcel.exportFile();
        return result;
    }


}
