package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.costStatistics.dto.CostDto;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:工服年费
 * @Author: gbl
 * @CreateDate: 2018/9/26 18:00
 */
public interface CostUniformManager extends IManager {

    /**
     * @Description 查询功夫你那非
     * @author gbl
     * @date 2018/9/26 18:06
     **/

    public Map<String,Object> queryCostUniform(CostDto costDto);

    public Map<String,Object>  exportCostUniform(CostDto costDto);


    public Map<String,Object> saveCostUniform(List<Map<String,Object>> list);
}
