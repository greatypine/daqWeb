package com.cnpc.pms.costStatistics.dao;

import com.cnpc.pms.base.dao.IDAO;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.dao
 * @Description:成本控制
 * @Author: gbl
 * @CreateDate: 2018/8/13 11:19
 */
public interface CostStatisticsDao extends IDAO {

    /**
     * @Description 查询人工成本
     * @author gbl
     * @date 2018/8/13 11:27
     **/

    public List<Map<String,Object>> queryCostLabor(String storeNo,String storeName,Integer year,Integer month);
}
