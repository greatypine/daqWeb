package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.manager.CostRenovationManager;
import com.cnpc.pms.costStatistics.util.CostRenovationExcel;
import com.cnpc.pms.costStatistics.util.CostRentExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 装修摊销
 * @Author: gbl
 * @CreateDate: 2018/8/28 15:09
 */
public class CostRenovationManagerImpl extends BizBaseCommonManager implements CostRenovationManager {
    @Override
    public Map<String, Object> queryCostRenovation(String storeNo, String storeName) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRenovation(storeNo,storeName);
        result.put("renovation",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostRenovation(String storeNo, String storeName) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRenovation(storeNo,storeName);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostRenovationExcel costRenovationExcel = new CostRenovationExcel(list);
        result = costRenovationExcel.exportFile();
        return result;

    }

    @Override
    public Map<String, Object> saveCostRenovation(List<Map<String, Object>> list) {
        return null;
    }
}
