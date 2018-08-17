package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.manager.CostStatisticsManager;
import com.cnpc.pms.costStatistics.util.CostLaborExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:成本控制
 * @Author: gbl
 * @CreateDate: 2018/8/13 11:22
 */
public class CostStatisticsManagerImpl extends BizBaseCommonManager implements CostStatisticsManager {
    @Override
    public Map<String, Object> queryCostLabor(String storeNo,String storeName,Integer year,Integer month) {
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
}
