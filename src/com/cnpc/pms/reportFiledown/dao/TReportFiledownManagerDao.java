
package com.cnpc.pms.reportFiledown.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import java.util.List;
import java.util.Map;

/**
 * 用户行为dao
 * 
 * @author shijunhui 2018年9月25日
 */
public interface TReportFiledownManagerDao extends IDAO {
    public List<TReportFiledown> getReportDown(String username,String tableLogic);

    public void getUpdateReportFileDowns(TReportFiledown tReportFiledown);

}
