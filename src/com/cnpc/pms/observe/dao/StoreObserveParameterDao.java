package com.cnpc.pms.observe.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public interface StoreObserveParameterDao {

    //点击编辑后查询
    public List<Map<String, Object>> queryObserveParameterList(Long store_id, String observe_month);

    public List<Map<String, Object>> queryObserveParameterListByStoreNo(String storeno, String observe_month, String beforMonth);

    public List<Map<String, Object>> queryCityObserveParameterListByStoreNo(String storeno, String observe_month);
}
