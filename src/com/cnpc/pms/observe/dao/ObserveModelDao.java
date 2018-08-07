package com.cnpc.pms.observe.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/5.
 */
public interface ObserveModelDao {

    public List<Map<String,Object>>  getObserveList();

    public List<Map<String, Object>> getObserveModelList();

    public List<String> getObserveContentList();
}
