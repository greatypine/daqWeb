package com.cnpc.pms.observe.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.observe.entity.StoreObserveParameterScore;

import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public interface StoreObserveParameterScoreManager  extends IManager {

    public Map<String, Object> queryObserveParameter(QueryConditions conditions);

    public StoreObserveParameterScore getObserveParameterByStoreAndMonth(Long storeid, String observemonth);

    public Map<String,Object> queryExitObserveParameter(Long storeid, String observemonth);

    public Map<String, Object> updateStoreObserveParameterScore(StoreObserveParameterScore observeParameter);

    public Map<String,Object> deleteStoreObserve(Long storeid, String observemonth);
}
