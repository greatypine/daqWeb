package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:门店租金
 * @Author: gbl
 * @CreateDate: 2018/9/18 10:50
 */
public interface CostRentContractManager extends IManager {

    /**
     * @Description 查询租金
     * @author gbl
     * @date 2018/8/22 9:14
     **/

    public Map<String,Object> queryCostRentContract(CostDto costDto);




    /**
     * @Description 保存租金
     * @author gbl
     * @date 2018/8/22 9:23
     **/

    public Map<String,Object> saveCostRentContract(List<Map<String,Object>> list);
}
