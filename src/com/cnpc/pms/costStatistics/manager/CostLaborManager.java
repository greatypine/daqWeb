package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostLabor;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:人工成本
 * @Author: gbl
 * @CreateDate: 2018/8/20 14:17
 */
public interface CostLaborManager extends IManager {

    /**
     * @Description 查询人工成本
     * @author gbl
     * @date 2018/8/13 13:06
     **/

    public Map<String,Object> queryCostLabor(CostDto costDto);

    /**
     * @Description 导出人工成本
     * @author gbl
     * @date 2018/8/16 13:32
     **/

    public Map<String,Object> exportCostLabor(CostDto costDto);

    /**
     * @Description 保存人工成本
     * @author gbl
     * @date 2018/8/20 13:54
     **/

    public Map<String,Object> saveCostLabor(List<Map<String,Object>> list);
}
