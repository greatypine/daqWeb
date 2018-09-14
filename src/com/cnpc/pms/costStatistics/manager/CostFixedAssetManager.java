package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description: 固定资产
 * @Author: gbl
 * @CreateDate: 2018/9/11 10:42
 */
public interface CostFixedAssetManager extends IManager {

    /**
     * @Description 查询固定资产
     * @author gbl
     * @date 2018/9/11 10:45
     **/

    public Map<String, Object> queryCostFixedAsset(String storeNo, String storeName);

    /**
     * @Description 导出固定资产
     * @author gbl
     * @date 2018/9/13 14:49
     **/

    public Map<String, Object> exportCostFixedAsset(String storeNo, String storeName);

    /**
     * @Description 保存固定资产
     * @author gbl
     * @date 2018/9/13 14:49
     **/

    public  Map<String,Object>  saveCostFixedAsset(List<Map<String,Object>> list);
}
