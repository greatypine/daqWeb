package com.cnpc.pms.observe.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.CheckDetails;

import java.util.Map;

/**
 * Created by h on 2018/8/23.
 */
public interface CheckDetailsManager  extends IManager {

    public CheckDetails getCheckDetailsById(Long id);

    public Map<String,Object> saveOrUpdateCheckDetails(ObserveDTO observeDTO);

    public Map<String,Object> getObserveCheckDetailsById(Long id);
}
