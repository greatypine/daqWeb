package com.cnpc.pms.observe.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.observe.dto.ObserveDTO;

import java.util.List;

/**
 * Created by gaoll on 2018/8/2.
 */
public interface ObserveModelManager extends IManager {

    public List<ObserveDTO> getChild();

}
