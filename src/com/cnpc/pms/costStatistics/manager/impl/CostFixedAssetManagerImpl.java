package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.manager.CostFixedAssetManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 固定资产
 * @Author: gbl
 * @CreateDate: 2018/9/11 10:44
 */
public class CostFixedAssetManagerImpl extends BizBaseCommonManager implements CostFixedAssetManager {

    @Override
    public Map<String, Object> queryCostFixedAsset(String storeNo, String storeName) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostFixedAsset(storeNo,storeName);
        result.put("fixedAsset",list);
        return result;
    }
}
