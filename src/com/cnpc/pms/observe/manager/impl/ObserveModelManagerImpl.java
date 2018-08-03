package com.cnpc.pms.observe.manager.impl;

import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.manager.ObserveModelManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gaoll on 2018/8/2.
 */
public class ObserveModelManagerImpl extends BizBaseCommonManager implements ObserveModelManager {

    @Override
    public List<ObserveDTO> getChild() {
        List<ObserveDTO> nodes = new ArrayList<ObserveDTO>();
        ObserveDTO observeDTO= new ObserveDTO();
        observeDTO.setId(null);
        observeDTO.setModel_name("明查管理模块");
        observeDTO.setParent(true);
        nodes.add(observeDTO);
        return nodes;
    }
}
