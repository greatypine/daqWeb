package com.cnpc.pms.observe.dao;

import com.cnpc.pms.base.paging.impl.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public interface StoreObserveParameterScoreDao {

    public List<Map<String, Object>> queryObserveParameterList(String where, PageInfo pageInfo);

    public List<Map<String, Object>> queryStoreObserveMonth(String employeeId, String city_name);

    public List<Map<String,Object>> queryObserveScoreByStoreAndObserveMonth(Long store_id,String observe_month);

    public List<Map<String, Object>> queryObserveParameterSummaryByCity(String cityname,String store_id,String observe_month,String employeeId);

    public List<String> queryObserveMonthByCity(String cityname,String store_id,String observe_month,String employeeId);
}
