package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.dao.UserAnalysisDao;
import com.cnpc.pms.personal.manager.UserAnalysisManager;

import java.util.List;
import java.util.Map;

/**
 * @Function: 用户总览接口实现
 * @Auther: chenchuang
 * @Date: 2018/9/14 15:52
 */
public class UserAnalysisManagerImpl extends BizBaseCommonManager implements UserAnalysisManager {

    @Override
    public Map<String, Object> queryTotalCustomer(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryTotalCustomer();
    }

    @Override
    public Map<String, Object> queryPayUser(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryPayUser();
    }

    @Override
    public List<Map<String, Object>> queryUsertag(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryUsertag();
    }

    @Override
    public List<Map<String, Object>> querySourceScatter(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.querySourceScatter();
    }

    @Override
    public List<Map<String, Object>> queryCityScatter(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryCityScatter();
    }

    @Override
    public List<Map<String, Object>> queryUnitPrice(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryUnitPrice();
    }

    @Override
    public Map<String, Object> queryUserActivity(String month){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryUserActivity(month);
    }

    @Override
    public List<Map<String, Object>> queryNewCustomer(){
        UserAnalysisDao userOverviewDao = (UserAnalysisDao) SpringHelper.getBean(UserAnalysisDao.class.getName());
        return  userOverviewDao.queryNewCustomer();
    }
}
