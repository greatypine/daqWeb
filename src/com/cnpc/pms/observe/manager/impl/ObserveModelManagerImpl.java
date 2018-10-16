package com.cnpc.pms.observe.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.observe.dao.ObserveModelDao;
import com.cnpc.pms.observe.dao.StoreObserveParameterScoreDao;
import com.cnpc.pms.observe.dto.ObserveDTO;
import com.cnpc.pms.observe.entity.CheckDetails;
import com.cnpc.pms.observe.entity.ObserveModel;
import com.cnpc.pms.observe.manager.ObserveModelManager;
import com.cnpc.pms.personal.dao.StorexpandDao;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by gaoll on 2018/8/2.
 */
public class ObserveModelManagerImpl extends BizBaseCommonManager implements ObserveModelManager {

    @Override
    public Map<String, Object> getObserveList() {
        Map<String,Object> result = new HashMap<String,Object>();
        ObserveModelDao observeModelDao = (ObserveModelDao)SpringHelper.getBean(ObserveModelDao.class.getName());
        List<Map<String, Object>> observeList = observeModelDao.getObserveList();
        result.put("observeList",observeList);
        return result;
    }

    @Override
    public List<ObserveDTO> getChild() {
        List<ObserveDTO> nodes = new ArrayList<ObserveDTO>();
        ObserveDTO observeDTO= new ObserveDTO();
        observeDTO.setId(null);
        observeDTO.setModel_name("明查管理模块");
        observeDTO.setName("明查管理模块");
        observeDTO.setParent(true);
        nodes.add(observeDTO);
        return nodes;
    }

    @Override
    public List<ObserveDTO> getObserveByModelId(Long id){
        List<ObserveDTO> modules = new ArrayList<ObserveDTO>();
        ObserveModelDao observeModelDao = (ObserveModelDao)SpringHelper.getBean(ObserveModelDao.class.getName());
        if(id == null){
            List<ObserveModel> observeModelList = observeModelDao.getObserveModel();
            for(int i = 0; i < observeModelList.size(); i++){
                ObserveDTO observeDTO = new ObserveDTO();
                observeDTO.setId(observeModelList.get(i).getId());
                observeDTO.setName(observeModelList.get(i).getModel_name());
                modules.add(observeDTO);
            }
        }else{
            List<CheckDetails> observeCheckdetailsList= observeModelDao.getObserveCheckdetailsByModelId(id);
            for(int i = 0; i < observeCheckdetailsList.size(); i++){
                ObserveDTO observeDTO = new ObserveDTO();
                observeDTO.setId(observeCheckdetailsList.get(i).getId());
                observeDTO.setName(observeCheckdetailsList.get(i).getObserve_content());
                modules.add(observeDTO);
            }
        }
        return modules;
    }

    @Override
    public Map<String, Object> queryObserveParameterByModelId(QueryConditions conditions) {
        Map<String,Object> result = new HashMap<String, Object>();
        ObserveModelDao observeModelDao = (ObserveModelDao)SpringHelper.getBean(ObserveModelDao.class.getName());
        StorexpandDao storexpandDao = (StorexpandDao) SpringHelper.getBean(StorexpandDao.class.getName());
        // 查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        // 分页对象
        PageInfo obj_page = conditions.getPageinfo();
        // 返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        List<Map<String, Object>> mapWhereList = conditions.getConditions();
        Map<String, Object> weidu_where = mapWhereList.get(1);
        Map<String, Object> city_where = mapWhereList.get(0);
        Integer status =0;
        long id = 0;
        if ("disabledUser".equals(weidu_where.get("key")) && null != weidu_where.get("value")
                && !"".equals(weidu_where.get("value"))) {
            String str = (String) weidu_where.get("value");
            status = Integer.valueOf(str.replace(",",""));
        }
        if ("orgEntity.id".equals(city_where.get("key")) && null != city_where.get("value") && !"null".equals(city_where.get("value"))
                && !"".equals(city_where.get("value"))) {
            id = Long.valueOf(city_where.get("value")+"");
        }

        System.out.println(sb_where);
        map_result.put("pageinfo", obj_page);
        map_result.put("header", "明查台账录入信息");
        map_result.put("data", observeModelDao.queryObserveParameterList(status,id,obj_page));
        return map_result;
    }

    @Override
    public Map<String, Object> saveorupdateobserveModel(ObserveDTO observeDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            ObserveModel observeModel = this.getModelById(observeDTO.getId());
            if(observeModel != null){
                ObserveModel observeMo = new ObserveModel();
                observeMo.setModel_name(observeDTO.getModel_name());
                observeMo.setOrder_no(observeDTO.getOrder_no());
                observeMo.setStatus(observeDTO.getStatus());
                observeMo.setRemark(observeDTO.getRemark());
                observeMo.setModel_color(observeDTO.getModel_color());
                BeanUtils.copyProperties(observeMo, observeModel,
                        new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
                preObject(observeModel);
                this.saveObject(observeModel);
            }else{
                ObserveModel observeMo = new ObserveModel();
                observeMo.setModel_name(observeDTO.getModel_name());
                observeMo.setOrder_no(observeDTO.getOrder_no());
                observeMo.setStatus(observeDTO.getStatus());
                observeMo.setModel_color(observeDTO.getModel_color());
                observeMo.setRemark(observeDTO.getRemark());
                preObject(observeMo);
                this.saveObject(observeMo);
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

    @Override
    public ObserveModel getModelById(Long id) {
        List<?> list = this.getList(FilterFactory.getSimpleFilter("id= "+id));
        if (list != null && list.size() > 0) {
            ObserveModel observeModel = (ObserveModel) list.get(0);
            return observeModel;
        }
        return null;
    }

}
