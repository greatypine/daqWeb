package com.cnpc.pms.costStatistics.manager;

import com.cnpc.pms.base.manager.IManager;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager
 * @Description:水电费
 * @Author: gbl
 * @CreateDate: 2018/9/14 10:08
 */
public interface CostGWEManager extends IManager {

    /**
     * @Description 水电费保存
     * @author gbl
     * @date 2018/9/14 10:10
     **/

    public Map<String,Object> saveCostGWE(List<Map<String,Object>> list);
}
