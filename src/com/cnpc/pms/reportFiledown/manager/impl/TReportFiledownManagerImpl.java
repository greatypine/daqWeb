package com.cnpc.pms.reportFiledown.manager.impl;

import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.reportFiledown.dao.TReportFiledownManagerDao;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;
import com.cnpc.pms.reportFiledown.manager.TreportFiledownManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TReportFiledownManagerImpl extends BizBaseCommonManager implements TreportFiledownManager {
    @Override
    public List<TReportFiledown> selectReportFileDowns(String tableLogic) {
        UserSession userSession = SessionManager.getUserSession();
        Map sessionData = userSession.getSessionData();
        String username = (String) sessionData.get("userCode");
        TReportFiledownManagerDao tReportFiledownDao = (TReportFiledownManagerDao) SpringHelper.getBean(TReportFiledownManagerDao.class.getName());
        List<TReportFiledown> list=tReportFiledownDao.getReportDown(username,tableLogic);
        return list;
    }

    @Override
    public List<TReportFiledown> selectReportFileDownsList() {
        UserSession userSession = SessionManager.getUserSession();
        Map sessionData = userSession.getSessionData();
        String username = (String) sessionData.get("userCode");
        TReportFiledownManagerDao tReportFiledownDao = (TReportFiledownManagerDao) SpringHelper.getBean(TReportFiledownManagerDao.class.getName());
        List<TReportFiledown> list=tReportFiledownDao.getReportDownList(username);
        return list;
    }
    @Override
    public void updateReportFileDowns(TReportFiledown tReportFiledown){
        tReportFiledown.setDownTimes(tReportFiledown.getDownTimes() + 1);
        tReportFiledown.setDownTime(new Date());
        TReportFiledownManagerDao tReportFiledownDao = (TReportFiledownManagerDao) SpringHelper.getBean(TReportFiledownManagerDao.class.getName());
        tReportFiledownDao.getUpdateReportFileDowns(tReportFiledown);
    }

    @Override
    public void deleteByFiledown(int id) {
        TReportFiledownManagerDao tReportFiledownDao = (TReportFiledownManagerDao) SpringHelper.getBean(TReportFiledownManagerDao.class.getName());
        tReportFiledownDao.getDeleteFileDowns(id);
    }

}
