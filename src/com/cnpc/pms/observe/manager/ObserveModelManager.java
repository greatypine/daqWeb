package com.cnpc.pms.observe.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.ObserveModel;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoll on 2018/8/2.
 */
public interface ObserveModelManager extends IManager {

    public List<ObserveDTO> getChild();

    public Map<String,Object> getObserveList();

    public List<ObserveDTO> getObserveByModelId(Long id);

    public Map<String, Object> queryObserveParameterByModelId(QueryConditions conditions);

    public Map<String,Object> saveorupdateobserveModel(ObserveDTO observeDTO);

    public ObserveModel getModelById(Long id);


}
