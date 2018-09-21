package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:装修摊销
 * @Author: gbl
 * @CreateDate: 2018/8/28 15:08
 */
public interface CostRenovationManager extends IManager {

    /**
     * @Description  查询装修摊销
     * @author gbl
     * @date 2018/8/28 16:40
     **/

    public Map<String,Object> queryCostRenovation(CostDto costDto);


   /**
    * @Description 导出装修摊销
    * @author gbl
    * @date 2018/8/28 16:40
    **/


    public Map<String,Object>  exportCostRenovation(CostDto costDto);

    /**
     * @Description 保存装修摊销
     * @author gbl
     * @date 2018/8/22 9:23
     **/

    public Map<String,Object> saveCostRenovation(List<Map<String,Object>> list);
}
