package com.cnpc.pms.observe.dao;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.observe.entity.CheckDetails;
import com.cnpc.pms.observe.entity.ObserveModel;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/5.
 */
public interface ObserveModelDao {

    public List<Map<String,Object>>  getObserveList();

    public List<Map<String, Object>> getObserveModelList();

    public List<String> getObserveContentList();

    public List<ObserveModel> getObserveModel();

    public List<Map<String, Object>> getObserveModelListByEdit(Long store_id,String observe_month);

    public List<CheckDetails> getObserveCheckdetailsByModelId(Long model_id);

    public List<Map<String, Object>> queryObserveParameterList(Integer status,Long id, PageInfo pageInfo);
}
