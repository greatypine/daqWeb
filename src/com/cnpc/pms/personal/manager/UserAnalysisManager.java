package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

/**
 * @Function: 用户总览接口
 * @Auther: chenchuang
 * @Date: 2018/9/14 15:48
 */
public interface UserAnalysisManager {

    /**
     * 统计用户注册总人数
     */
    public Map<String, Object> queryTotalCustomer();

    /**
     * 统计消费人数（1次/2次以上（含））
     */
    public Map<String, Object> queryPayUser();

    /**
     * 统计用户标签（无效/待确认/绝对真实）
     */
    public List<Map<String, Object>> queryUsertag();

    /**
     * 统计用户来源分布
     */
    public List<Map<String, Object>> querySourceScatter();

    /**
     * 统计用户城市分布
     */
    public List<Map<String, Object>> queryCityScatter();

    /**
     * 统计客单价
     */
    public List<Map<String, Object>> queryUnitPrice();

    /**
     * 统计用户活跃度（最近1个月/3个月）
     */
    public Map<String, Object> queryUserActivity(String month);

    /**
     * 统计新增用户（按月）
     * @return
     */
    public List<Map<String, Object>> queryNewCustomer();

}
