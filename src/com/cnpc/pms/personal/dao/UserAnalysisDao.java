package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

/**
 * @Function: 用户总览Dao
 * @Auther: chenchuang
 * @Date: 2018/9/14 16:16
 */
public interface UserAnalysisDao {

    public Map<String, Object> queryTotalCustomer();

    public Map<String, Object> queryPayUser();

    public List<Map<String, Object>> queryUsertag();

    public List<Map<String, Object>> querySourceScatter();

    public List<Map<String, Object>> queryCityScatter();

    public List<Map<String, Object>> queryUnitPrice();

    public Map<String, Object> queryUserActivity(String month);

    public List<Map<String, Object>> queryNewCustomer();

}
