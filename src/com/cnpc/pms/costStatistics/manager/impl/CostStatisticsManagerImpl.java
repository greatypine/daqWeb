package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.manager.CostStatisticsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/9/20 19:26
 */
public class CostStatisticsManagerImpl extends BizBaseCommonManager implements CostStatisticsManager {
    @Override
    public Map<String, Object> selectCostStatistics(QueryConditions queryConditions) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());

        // 查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        // 分页对象
        PageInfo obj_page = queryConditions.getPageinfo();
        // 返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        String cityName="";
        String storeNo="";
        Integer year=null;
        Integer month=null;

        for (Map<String, Object> map_where : queryConditions.getConditions()) {
            if ("cityName".equals(map_where.get("key")) && null != map_where.get("value")
                    && !"".equals(map_where.get("value"))) {
                cityName = map_where.get("value").toString();
            } else if ("storeNo".equals(map_where.get("key")) && null != map_where.get("value")
                    && !"".equals(map_where.get("value"))) {
                storeNo = map_where.get("value").toString();
            } else if ("year".equals(map_where.get("key")) && null != map_where.get("value")
                    && !"".equals(map_where.get("value"))) {
                year = Integer.parseInt(map_where.get("value").toString());;
            } else if ("month".equals(map_where.get("key")) && null != map_where.get("value")
                    && !"".equals(map_where.get("value"))) {
                month = Integer.parseInt(map_where.get("value").toString());
            }

        }
        try {
            map_result.put("data", costStatisticsDao.queryCostStatistic(cityName,storeNo,year,month,obj_page));
        } catch (Exception e) {
            e.printStackTrace();
        }
        map_result.put("pageinfo", obj_page);
        map_result.put("header", "成本录入情况");
        return map_result;
    }
}
