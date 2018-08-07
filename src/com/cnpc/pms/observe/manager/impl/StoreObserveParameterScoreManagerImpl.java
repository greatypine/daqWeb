package com.cnpc.pms.observe.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.observe.dao.StoreObserveParameterScoreDao;
import com.cnpc.pms.observe.entity.StoreObserveParameterScore;
import com.cnpc.pms.observe.manager.StoreObserveParameterScoreManager;
import com.cnpc.pms.personal.dao.StorexpandDao;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/6.
 */
public class StoreObserveParameterScoreManagerImpl extends BizBaseCommonManager implements StoreObserveParameterScoreManager{

    @Override
    public Map<String, Object> queryObserveParameter(QueryConditions conditions) {
        Map<String,Object> result = new HashMap<String, Object>();
        StoreObserveParameterScoreDao observeParameterDao = (StoreObserveParameterScoreDao) SpringHelper.getBean(StoreObserveParameterScoreDao.class.getName());
        StorexpandDao storexpandDao = (StorexpandDao) SpringHelper.getBean(StorexpandDao.class.getName());
        // 查询的数据条件
        StringBuilder sb_where = new StringBuilder();
        // 分页对象
        PageInfo obj_page = conditions.getPageinfo();
        // 返回的对象，包含数据集合、分页对象等
        Map<String, Object> map_result = new HashMap<String, Object>();
        List<Map<String, Object>> mapWhereList = conditions.getConditions();
        Map<String, Object> weidu_where = mapWhereList.get(3);
        Map<String, Object> city_where = mapWhereList.get(0);
        Map<String, Object> city_name = mapWhereList.get(2);
        Map<String, Object> employee_id = mapWhereList.get(5);
        if ("store_id".equals(weidu_where.get("key")) && null != weidu_where.get("value")
                && !"".equals(weidu_where.get("value"))) {
            String str = (String) weidu_where.get("value");
            sb_where.append(" AND st.store_id = '").append(weidu_where.get("value")).append("'");
        }
        if ("observe_month".equals(city_where.get("key")) && null != city_where.get("value")
                && !"".equals(city_where.get("value"))) {
            sb_where.append(" AND st.observe_month = '").append(city_where.get("value").toString().replace("%", "")).append("'");
        }
        if ("city_name".equals(city_name.get("key")) && null != city_name.get("value")
                && !"".equals(city_name.get("value"))) {
            sb_where.append(" AND st.city_name = '").append(city_name.get("value").toString().replace("%", "")).append("'");
        }
        if ("employee_id".equals(employee_id.get("key")) && null != employee_id.get("value")
                && !"".equals(employee_id.get("value"))) {
            sb_where.append(" AND ci.pk_userid = '").append(employee_id.get("value").toString().replace("%", "")).append("'");
        }

        System.out.println(sb_where);
        map_result.put("pageinfo", obj_page);
        map_result.put("header", "明查台账录入信息");
        map_result.put("data", observeParameterDao.queryObserveParameterList(sb_where.toString(), obj_page));
        return map_result;
    }

    @Override
    public StoreObserveParameterScore getObserveParameterByStoreAndMonth(Long storeid, String observemonth) {
        List<?> list = this.getList(FilterFactory.getSimpleFilter("store_id= '"+storeid+"' and observe_month = '"+observemonth+"' and status = 0"));
        if (list != null && list.size() > 0) {
            StoreObserveParameterScore observeParameter = (StoreObserveParameterScore) list.get(0);
            return observeParameter;
        }
        return null;
    }

    @Override
    public Map<String,Object> queryExitObserveParameter(Long storeid, String observemonth) {
        Map<String,Object> result = new HashMap<String,Object>();
        List<?> list = this.getList(FilterFactory.getSimpleFilter("store_id= '"+storeid+"' and observe_month = '"+observemonth+"' and status = 0"));
        if (list != null && list.size() > 0) {
            StoreObserveParameterScore observeParameter = (StoreObserveParameterScore) list.get(0);
            result.put("queryObserveParameterList",observeParameter);
        }
        return result;
    }

    @Override
    public Map<String, Object> updateStoreObserveParameterScore(StoreObserveParameterScore observeParameter) {
        Map<String, Object> result = new HashMap<String, Object>();
        StoreObserveParameterScore observeParameter1 = this.getObserveParameterByStoreAndMonth(observeParameter.getStore_id(),observeParameter.getObserve_month());
        try {
            if (observeParameter1 != null) {
                BeanUtils.copyProperties(observeParameter, observeParameter1,
                        new String[] { "id", "version", "create_time", "create_user", "create_user_id" });
                preObject(observeParameter1);
                this.saveObject(observeParameter1);
            } else {
                preObject(observeParameter);
                this.saveObject(observeParameter);
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
