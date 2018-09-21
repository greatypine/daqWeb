package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:运营成本
 * @Author: gbl
 * @CreateDate: 2018/9/19 19:36
 */
public interface CostOperationManager extends IManager {

    /**
     * @Description 查询运营成本
     * @author gbl
     * @date 2018/8/13 13:06
     **/

    public Map<String,Object> queryCostOperation(CostDto costDto);

    /**
     * @Description 导出运营成本
     * @author gbl
     * @date 2018/8/16 13:32
     **/

    public Map<String,Object> exportCostOperation(CostDto costDto);

    /**
     * @Description 保存运营成本
     * @author gbl
     * @date 2018/8/20 13:54
     **/

    public Map<String,Object> saveCostOperation(List<Map<String,Object>> list);
}
