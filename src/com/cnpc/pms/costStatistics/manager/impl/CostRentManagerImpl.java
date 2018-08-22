package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.manager.CostRentManager;
import com.cnpc.pms.costStatistics.util.CostLaborExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/8/21 17:16
 */
public class CostRentManagerImpl extends BizBaseCommonManager implements CostRentManager {


    @Override
    public Map<String, Object> queryCostRent(String storeNo, String storeName,Integer year) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRent(storeNo,storeName,year);
        result.put("rent",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostRent(String storeNo, String storeName, Integer year) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRent(storeNo,storeName,year);
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
    public Map<String, Object> saveCostRent(List<Map<String, Object>> list) {
        return null;
    }
}
