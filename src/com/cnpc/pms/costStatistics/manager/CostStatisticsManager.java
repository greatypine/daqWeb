package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;

import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description: 成本控制
 * @Author: gbl
 * @CreateDate: 2018/8/13 11:21
 */
public interface CostStatisticsManager extends IManager {

    /**
     * @Description 查询人工成本
     * @author gbl
     * @date 2018/8/13 13:06
     **/

    public Map<String,Object> queryCostLabor(String storeNo,String storeName,Integer year,Integer month);

    /**
     * @Description 导出人工成本
     * @author gbl
     * @date 2018/8/16 13:32
     **/

    public Map<String,Object> exportCostLabor(String storeNo,String storeName,Integer year,Integer month);
}
