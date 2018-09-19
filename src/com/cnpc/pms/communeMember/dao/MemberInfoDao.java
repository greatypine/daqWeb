
package com.cnpc.pms.communeMember.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.MemberDataDto;

import java.util.List;
import java.util.Map;

/**
 * 社员信息操作dao
 * 
 * @author wuxinxin 2018年5月17日
 */
public interface MemberInfoDao extends IDAO {

    /**
     * TODO 获取社员来源：APP,WEB,微信...
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getMemFrom(String dd);

    /**
     * TODO 获取社员注册城市
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getRegistCity(String dd);

    /**
     * TODO 按月获取社员成交额
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getMemGmvByMon(String dd);

    /**
     * TODO 按月获取非社员成交额
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getNmemGmvByMon(String dd);

    /**
     * TODO 按月获取社员成交订单量
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getMemOrderCount(String dd);

    /**
     * TODO 按月获取非社员成交订单量
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getNmemOrderCount(String dd);

    /**
     * TODO 统计社员活跃度 1次  2次   3次及以上
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getMemByTimes(String dd);

    /**
     * TODO 统计非社员活跃度 1次  2次   3次及以上
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getNmemByTimes(String dd);


    /**
     * TODO 统计社员新增情况
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getMemNew(String dd);

      /**
     * TODO 查询有效社员
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getValidMem(String dd);

    /**
     * TODO 查询大客户社员
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getBigMem(String dd);

    /**
     * TODO 查询试用社员
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getTryMem(String dd);

    /**
     * TODO 查询3个月未消费社员总数
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getNoExpeMem(String dd);

    /**
     * TODO 查询3个月消费1次社员总数
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getExpeOneMem(String dd);

    /**
     * TODO 查询3个月消费2次及以上社员总数
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getExpeTwoMem(String dd);

    /**
     * TODO 查询3个月消费2次及以上社员总数
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getCountAll(String dd);



}
