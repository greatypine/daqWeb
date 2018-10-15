package com.cnpc.pms.userBehavior.manager.impl;

import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.userBehavior.dao.UserBehaviorDao;
import com.cnpc.pms.userBehavior.manager.UserBehavior;
import java.util.*;

/**
 * 用户行为操作
 * 
 * @author shijunhui 2018年9月25日
 */
public class UserBehaviorImpl extends BizBaseCommonManager implements UserBehavior {



    @Override
    public Map<String, Object> selectUserBrhaviorList(String startTime,String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> cmSexList = userBehaviorDao.getUserBehaviorList(startTime,endTime);
        result.put("data",cmSexList);
        return result;
    }

    @Override
    public Map<String, Object> selectByCityList(String startTime,String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> cityList = userBehaviorDao.getByCityList(startTime,endTime);
        result.put("data",cityList);
        return result;
    }

    @Override
    public Map<String, Object> selectByChannel(String startTime,String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> ChannelList = userBehaviorDao.getByChannelList(startTime,endTime);
        result.put("data",ChannelList);
        return result;
    }

    @Override
    public Map<String, Object> selectUserShoppingCart(String startTime, String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();

        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> shoppingCartList = userBehaviorDao.getUserShoppingCartList(startTime,endTime);
        result.put("data",shoppingCartList);
        return result;
    }

    @Override
    public Map<String, Object> selectUserShoppingCartAvg(String startTime, String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> paymentList = userBehaviorDao.getUserShoppingCartAvg(startTime,endTime);
        result.put("data",paymentList);
        return result;
    }

    @Override
    public Map<String, Object> selectUserShoppingCartDetails(String startTime, String endTime) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserBehaviorDao userBehaviorDao = (UserBehaviorDao) SpringHelper.getBean(UserBehaviorDao.class.getName());
        List<Map<String, Object>> detailsList = userBehaviorDao.getUserShoppingCartDetails(startTime,endTime);
        result.put("data",detailsList);
        return result;
    }
}
