package com.cnpc.pms.observe.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.StoreObserveParameter;
import com.cnpc.pms.observe.entity.StoreObserveParameterScore;
import com.cnpc.pms.personal.dto.StoreDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public interface StoreObserveParameterManager extends IManager {

    //点击编辑或添加后进行的操作
    public Map<String,Object> queryObserveParameterList(ObserveDTO observeDTO);

    public Map<String, Object> saveObserveParameter(List<Map<String,Object>> StoreObserveParameterlist, StoreObserveParameterScore storeObserveParameterScore);

    public StoreObserveParameter getObserveParameterByStoreAndMonthAndDetailsId(Long storeid, String observemonth, Long check_details_id);

    public Map<String, Object> queryObserveParameterSummaryByCity(String cityname,String store_id,String observe_month,String employeeId);

    public Map<String,Object> exportObserveParamterSummary(StoreDTO storeDTO);
}
