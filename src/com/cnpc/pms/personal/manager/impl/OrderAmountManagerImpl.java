package com.cnpc.pms.personal.manager.impl;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.personal.dao.MassOrderDao;
import com.cnpc.pms.personal.dao.MassOrderItemDao;
import com.cnpc.pms.personal.manager.OrderAmountManager;
import com.cnpc.pms.platform.dao.OrderAmountDao;
import com.cnpc.pms.platform.dao.OrderDao;
import com.cnpc.pms.platform.entity.OrderAmountDto;
import com.cnpc.pms.reportFiledown.entity.ExportRunableDDDA;
import com.cnpc.pms.reportFiledown.entity.ExportRunableDDJEJS;
import com.cnpc.pms.reportFiledown.entity.HttpClientUtils;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;
import com.cnpc.pms.utils.DateUtils;
import com.cnpc.pms.utils.ImpalaUtil;

public class OrderAmountManagerImpl extends BizBaseCommonManager implements OrderAmountManager{
  	public Map<String, Object> exportOrder(OrderAmountDto orderAmountDto, TReportFiledown tReportFiledown) {
  		OrderAmountDao orderAmountDao = (OrderAmountDao)SpringHelper.getBean(OrderAmountDao.class.getName());
  		int total = orderAmountDao.getTotal(orderAmountDto);
  		Map<String, Object> result = new HashMap<String,Object>();
  		if (total>0&&total<=30000) {
  			HttpClientUtils httpClientUtils = new HttpClientUtils();
  			UserSession userSession = SessionManager.getUserSession();
  			Map sessionData = userSession.getSessionData();
  			String username = (String) sessionData.get("userCode");
  			tReportFiledown.setCreate_time(new Date());
  			tReportFiledown.setUsername(username);
  			tReportFiledown.setDownTimes(0);
  			tReportFiledown.setTableLogic("DDJEJS");
  			String fileName =  tReportFiledown.getFilename();
  			fileName = httpClientUtils.getPingYin(fileName);
  			tReportFiledown.setFilename(fileName);
  			tReportFiledown.setUrl("/" + fileName);
  			tReportFiledown.setMark1("0");
  			saveObject(tReportFiledown);
  			String starts = "DDJEJS";
  			ExportRunableDDJEJS s1=new ExportRunableDDJEJS(starts, fileName, null, orderAmountDto, false,tReportFiledown,orderAmountDao);
  			Thread t1 = new Thread(s1);
  			t1.start();
  			result.put("message","导出成功！");
  			result.put("status","success");	
		}else if (total>30000) {
			result.put("message","导出失败！");
  			result.put("status","more");		
		}
  		return result;
  	}

	@Override
	public Map<String, Object> queryOrderAmount(OrderAmountDto orderAmountDto, PageInfo pageInfo) {
		// TODO Auto-generated method stub
  		OrderAmountDao orderAmountDao = (OrderAmountDao)SpringHelper.getBean(OrderAmountDao.class.getName());
		Map<String, Object> result = new HashMap<String,Object>();
		try {
/*			String preMonthFirst = DateUtils.getPreMonthFirstDay(new Date()); //上月1号
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");*/
	/*		if(StringUtils.isNotEmpty(orderAmountDto.getBeginDate()) && DateUtils.compareDate(orderAmountDto.getBeginDate(), format.format(new Date()))==0){
				result = orderAmountDao.queryOrderAmount(orderAmountDto, pageInfo, "df_mass_order_daily");
			}else if(StringUtils.isNotEmpty(orderAmountDto.getBeginDate()) && DateUtils.compareDate(orderAmountDto.getBeginDate(),preMonthFirst)>=0){
				result = orderAmountDao.queryOrderAmount(orderAmountDto, pageInfo,"df_mass_order_monthly");
			}else{*/
				result = orderAmountDao.queryOrderAmount(orderAmountDto, pageInfo, "df_mass_order_total");
			/*}*/
			result.put("status","success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status","fail");
		}
		return result;
		
	}
    @Override
    public Map<String, Object> queryContractById(String contract_id){
    	Map<String,Object> order_obj =  new HashMap<String,Object>();
    	if (contract_id!=null&&"".equals(contract_id)) {
    		OrderDao orderDao = (OrderDao) SpringHelper.getBean(OrderDao.class.getName());
        	Map<String,Object> contract_obj = orderDao.queryContractById(contract_id);
    		order_obj.put("method",contract_obj.get("method"));
		}
    	return order_obj;
    }
	

}
