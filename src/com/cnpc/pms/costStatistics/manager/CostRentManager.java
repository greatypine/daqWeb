package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description: 租金成本
 * @Author: gbl
 * @CreateDate: 2018/8/21 17:12
 */
public interface CostRentManager extends IManager {

    /**
     * @Description 查询租金成本
     * @author gbl
     * @date 2018/8/22 9:14
     **/

    public Map<String,Object>  queryCostRent(CostDto costDto);


    /**
     * @Description 导出租金成本
     * @author gbl
     * @date 2018/8/22 9:24
     **/

    public Map<String,Object>  exportCostRent(CostDto costDto);

    /**
     * @Description 导出物业费用
     * @author gbl
     * @date 2018/9/28 13:48
     **/

    public Map<String,Object> exportCostProperty(CostDto costDto);

    /**
     * @Description 保存租金成本
     * @author gbl
     * @date 2018/8/22 9:23
     **/

    public Map<String,Object> saveCostRent(List<Map<String,Object>> list);
}
