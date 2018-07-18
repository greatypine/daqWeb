package com.cnpc.pms.personal.dao;

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
public interface EmployeeMoreInfoDao extends IDAO {

    /**
     * @Description 查询国安侠信息
     * @author gbl
     * @date 2018/7/10 14:44
     **/

    public List<Map<String,Object>> queryHumanresource();

    /**
     * @Description  查询店长
     * @author gbl
     * @date 2018/7/10 14:45
     **/

    public List<Map<String,Object>> queryStoreKepeer();

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

}
