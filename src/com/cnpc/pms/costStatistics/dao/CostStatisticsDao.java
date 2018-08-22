package com.cnpc.pms.costStatistics.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import com.cnpc.pms.costStatistics.entity.CostRent;

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

    /**
     * @Description 查询某个门店某年某月的人工成本
     * @author gbl
     * @date 2018/8/20 14:01
     **/

    public List<Map<String,Object>> selectCostLabor(String storeNo,Integer year,Integer month);

    /**
     * @Description 更新人工成本
     * @author gbl
     * @date 2018/8/20 14:10
     **/

    public void updateCostLabor(CostLabor costLabor);


    /**
     * @Description 查询租金成本
     * @author gbl
     * @date 2018/8/21 17:31
     **/

    public List<Map<String,Object>> queryCostRent(String storeNo,String storeName,Integer year);


    /**
     * @Description 查询某个店某年某月的租金成本
     * @author gbl
     * @date 2018/8/21 17:31
     **/

    public List<Map<String,Object>> selectCostRent(String storeNo,Integer year);




}
