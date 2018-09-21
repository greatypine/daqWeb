package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;

import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/9/20 19:25
 */
public interface CostStatisticsManager extends IManager {

    /**
     * @Description 查询成本录入
     * @author gbl
     * @date 2018/9/20 19:25
     **/

    public Map<String, Object> selectCostStatistics(QueryConditions queryConditions);
}
