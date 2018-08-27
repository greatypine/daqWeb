package com.cnpc.pms.observe.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.CheckDetails;
import com.cnpc.pms.observe.entity.ObserveModel;
import com.cnpc.pms.observe.manager.CheckDetailsManager;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/23.
 */
public class CheckDetailsManagerImpl extends BizBaseCommonManager implements CheckDetailsManager{

    @Override
    public CheckDetails getCheckDetailsById(Long id) {
        List<?> list = this.getList(FilterFactory.getSimpleFilter("id= "+id));
        if (list != null && list.size() > 0) {
            CheckDetails checkDetails = (CheckDetails) list.get(0);
            return checkDetails;
        }
        return null;
    }

    @Override
    public Map<String, Object> saveOrUpdateCheckDetails(ObserveDTO observeDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CheckDetails checkDetails1 = this.getCheckDetailsById(observeDTO.getId());
            if(checkDetails1 != null){
                CheckDetails checkDetails = new CheckDetails();
                checkDetails.setModel_id(observeDTO.getModel_id());
                checkDetails.setOrder_no(observeDTO.getOrder_no());
                checkDetails.setStatus(observeDTO.getStatus());
                checkDetails.setObserve_content(observeDTO.getObserve_content());
                checkDetails.setType(0);
                BeanUtils.copyProperties(checkDetails, checkDetails1,
                        new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
                preObject(checkDetails1);
                this.saveObject(checkDetails1);
            }else{
                CheckDetails checkDetails = new CheckDetails();
                checkDetails.setModel_id(observeDTO.getModel_id());
                checkDetails.setOrder_no(observeDTO.getOrder_no());
                checkDetails.setStatus(observeDTO.getStatus());
                checkDetails.setObserve_content(observeDTO.getObserve_content());
                checkDetails.setType(0);
                preObject(checkDetails);
                this.saveObject(checkDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "-1");
            result.put("message", "update fail");
            return result;
        }

        result.put("code", "1");
        result.put("message", "update success");
        return result;
    }

}
