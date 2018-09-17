package com.cnpc.pms.employeeMoreInfo.dao;

import com.cnpc.pms.base.dao.IDAO;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.personal.dao
 * @Description: 员工更多信息dao
 * @Author: gbl
 * @CreateDate: 2018/7/10 14:34
 */
public interface EmployeeMoreInfoDao{

    /**
     * @Description 查询国安侠信息
     * @author gbl
     * @date 2018/7/10 14:44
     **/

    public List<Map<String,Object>> queryValidHumanresource();

    /**
     * @Description  查询店长
     * @author gbl
     * @date 2018/7/10 14:45
     **/

    public List<Map<String,Object>> selectValidStoreKepeer();

    /**
     * @Description  更新国安侠工龄
     * @author gbl
     * @date 2018/7/10 16:27
     **/

    public void updateEmployeeWorkingAge(String employeeNo,Integer year,Integer month,Integer day);

    /**
     * @Description
     * @author gbl
     * @date 2018/7/13 9:46
     **/

    public List<Map<String,Object>> queryEmployeeMoveDistance(String employeeNo);

    /**
     * @Description 国安侠人均日工作时间（时间截止到上月）
     * @author gaoll
     * @date 2018/9/12 9:46
     **/
    public List<Map<String,Object>> queryAvgWorkTime();

    /**
     * @Description 国安侠人均日单量( 时间截止到上月)
     * @author gaoll
     * @date 2018/9/12 9:46
     **/
    public List<Map<String,Object>> queryAvgSentOrder();


    /**
     * @Description 国安侠人均日里程数（时间截止到上月）
     * @author gaoll
     * @date 2018/9/12 9:46
     **/
    public List<Map<String,Object>> queryAvgMileAge();

    /**
     * @Description 国安侠人均日单量分布图（时间截止到上月）
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryAvgSendOrderDistribution();

    /**
     * @Description 国安侠人均月单量走势图（时间截止到上月）
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryAvgSendOrderMonthTrend();

    /**
     * @Description 国安侠人均日单量各城市统计（时间截止到上月）
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryAvgSendOrderGroupByCity();

    /**
     * @Description 实时判断门店国安侠满编门店数量
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryEmpAtAnalysis();

    /**
     * @Description 查询2018年每月人员编制走势图（分为满编，缺编，超编）
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryEmpAtAnalysisByMonth(String status);

    /**
     * @Description 查询经营月店数量
     * @author gaoll
     * @date 2018/9/13 9:46
     **/
    public List<Map<String,Object>> queryYuedianCount();


    /**
     * @Description 查询进驻国安侠的门店数量
     * @author gaoll
     * @date 2018/9/17 9:46
     **/
    public Integer queryStoreCountHavingGAX();


}
