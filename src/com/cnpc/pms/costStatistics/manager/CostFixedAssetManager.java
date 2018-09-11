package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;

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
}
