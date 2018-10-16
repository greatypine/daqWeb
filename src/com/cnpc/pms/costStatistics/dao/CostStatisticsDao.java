package com.cnpc.pms.costStatistics.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostGWE;
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

    public List<Map<String,Object>> queryCostLabor(CostDto costDto);

    /**
     * @Description 导出人工成本数据
     * @author gbl
     * @date 2018/9/17 14:45
     **/

    public List<Map<String,Object>> exportCostLabor(CostDto costDto);

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

    public List<Map<String,Object>> queryCostRent(CostDto costDto);

    /**
     * @Description 导出租金成本
     * @author gbl
     * @date 2018/9/19 11:29
     **/

    public List<Map<String,Object>> exportCostRent(CostDto costDto);




    /**
     * @Description 查询装修摊销
     * @author gbl
     * @date 2018/8/28 15:17
     **/

    public List<Map<String,Object>> queryCostRenovation(CostDto costDto);


    /**
     * @Description 查询固定资产
     * @author gbl
     * @date 2018/9/11 10:29
     **/

    public List<Map<String,Object>> queryCostFixedAsset(CostDto costDto);

    /**
     * @Description 查询门店租赁合同信息
     * @author gbl
     * @date 2018/9/18 11:02
     **/

    public List<Map<String,Object>> queryCostRentContract(CostDto costDto);

    /**
     * @Description  查询水电费
     * @author gbl
     * @date 2018/9/19 16:36
     **/

    public List<Map<String,Object>> queryCostGWE(CostDto costDto);

    /**
     * @Description 导出水电费
     * @author gbl
     * @date 2018/9/19 18:53
     **/

    public List<Map<String,Object>> exportCostGWE(CostDto costDto);

    /**
     * @Description 查询运营成本
     * @author gbl
     * @date 2018/9/20 10:04
     **/

    public List<Map<String,Object>> queryCostOperation(CostDto costDto);

    /**
     * @Description 查询成本录入情况
     * @author gbl
     * @date 2018/9/20 19:17
     **/

    public List<Map<String,Object>> queryCostStatistic(String cityName,String storeNo,Integer year,Integer month,PageInfo pageInfo);

    /**
     * @Description 查询工服年费
     * @author gbl
     * @date 2018/9/26 18:10
     **/

    public List<Map<String,Object>> queryCostUniform(CostDto costDto);

    /**
     * @Description 获取成本报表数据
     * @author gbl
     * @date 2018/10/16 16:43
     **/

    public List<Map<String,Object>> getCostStatisticsStatement(CostDto costDto);

}
