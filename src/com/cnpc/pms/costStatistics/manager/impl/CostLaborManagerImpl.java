package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import com.cnpc.pms.costStatistics.manager.CostLaborManager;
import com.cnpc.pms.costStatistics.util.CostLaborExcel;
import com.cnpc.pms.slice.entity.AreaInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 人工成本
 * @Author: gbl
 * @CreateDate: 2018/8/20 14:17
 */
public class CostLaborManagerImpl extends BizBaseCommonManager implements CostLaborManager {

    @Override
    public Map<String, Object> queryCostLabor(String storeNo, String storeName, Integer year, Integer month) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostLabor(storeNo,storeName,year,month);
        result.put("labor",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostLabor(String storeNo, String storeName, Integer year, Integer month) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostLabor(storeNo,storeName,year,month);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostLaborExcel costLaborExcel = new CostLaborExcel(list);
        result = costLaborExcel.exportFile();
        return result;
    }

    @Override
    public Map<String, Object> saveCostLabor(List<Map<String,Object>> list) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            Map<String,Object> map  = null;
            IFilter filter = null;
            List<CostLabor> lst_costLabor=null;
            for(int i=0;i<list.size();i++){
                String storeNo = list.get(i).get("storeNo")==null?"":String.valueOf(list.get(i).get("storeNo"));
                String storeName = list.get(i).get("storeName")==null?"":String.valueOf(list.get(i).get("storeName"));
                Integer year  = list.get(i).get("year")==null?0:Integer.parseInt(list.get(i).get("year").toString());
                Integer month = list.get(i).get("month")==null?0:Integer.parseInt(list.get(i).get("month").toString());
                Double  emolument = list.get(i).get("emolument")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("emolument")));
                Double uniformAmortize = list.get(i).get("uniformAmortize")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("uniformAmortize")));
                Double accommodation = list.get(i).get("accommodation")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("accommodation")));
                Double subtotal = list.get(i).get("subtotal")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("subtotal")));

                filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year+" and month="+month);
                lst_costLabor = (List<CostLabor>) this.getList(filter);

                if (lst_costLabor != null && lst_costLabor.size() > 0) {
                   CostLabor cl = lst_costLabor.get(i);
                   cl.setEmolument(emolument);
                   cl.setUniformAmortize(uniformAmortize);
                   cl.setAccommodation(accommodation);
                   cl.setSubtotal(subtotal);
                   preObject(cl);
                   saveObject(cl);
                }else{
                    CostLabor cl = new CostLabor();
                    cl.setStoreNo(storeNo);
                    cl.setStoreName(storeName);
                    cl.setYear(year);
                    cl.setMonth(month);
                    cl.setEmolument(emolument);
                    cl.setUniformAmortize(uniformAmortize);
                    cl.setAccommodation(accommodation);
                    cl.setSubtotal(subtotal);
                    preObject(cl);
                    saveObject(cl);
                }


            }
            result.put("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
        }

        return result;
    }

}
