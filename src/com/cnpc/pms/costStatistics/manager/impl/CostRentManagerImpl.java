package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import com.cnpc.pms.costStatistics.entity.CostRent;
import com.cnpc.pms.costStatistics.manager.CostRentManager;
import com.cnpc.pms.costStatistics.util.CostLaborExcel;
import com.cnpc.pms.costStatistics.util.CostRentExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/8/21 17:16
 */
public class CostRentManagerImpl extends BizBaseCommonManager implements CostRentManager {


    @Override
    public Map<String, Object> queryCostRent(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRent(costDto);
        result.put("rent",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostRent(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.exportCostRent(costDto);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostRentExcel costRentExcel = new CostRentExcel(list,costDto.getYear().toString());
        result = costRentExcel.exportFile();
        return result;
    }

    @Override
    public Map<String, Object> saveCostRent(List<Map<String, Object>> list) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            Map<String,Object> map  = null;
            IFilter filter = null;
            List<CostRent> lst_costRent=null;
            for(int i=0;i<list.size();i++){
                String cityName = list.get(i).get("cityName")==null?"":String.valueOf(list.get(i).get("cityName"));
                String storeNo = list.get(i).get("storeNo")==null?"":String.valueOf(list.get(i).get("storeNo"));
                String storeName = list.get(i).get("storeName")==null?"":String.valueOf(list.get(i).get("storeName"));
                String addr = list.get(i).get("addr")==null?"":String.valueOf(list.get(i).get("addr"));

                Double propertyFeeYear = list.get(i).get("propertyFeeYear")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("propertyFeeYear")));
                Integer propertyDeadline = list.get(i).get("propertyDeadline")==null?null:Integer.parseInt(String.valueOf(list.get(i).get("propertyDeadline")));
                Double propertyFeeMonth = list.get(i).get("propertyFeeMonth")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("propertyFeeMonth")));
                Double rentalMonth = list.get(i).get("rentalMonth")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("rentalMonth")));
                Double costMonth = list.get(i).get("costMonth")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("costMonth")));
                Integer year = list.get(i).get("year")==null?null:Integer.parseInt(String.valueOf(list.get(i).get("year")));
                Integer month = list.get(i).get("month")==null?null:Integer.parseInt(String.valueOf(list.get(i).get("month")));
                filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year+" and month="+month);
                lst_costRent = (List<CostRent>) this.getList(filter);

                if (lst_costRent != null && lst_costRent.size() > 0) {
                    CostRent cr = lst_costRent.get(i);
                    cr.setCityName(cityName);
                    cr.setStoreNo(storeNo);
                    cr.setStoreName(storeName);
                    cr.setPropertyDeadline(propertyDeadline);
                    cr.setPropertyFeeYear(propertyFeeYear);
                    cr.setPropertyFeeMonth(propertyFeeMonth);
                    cr.setYear(year);
                    preObject(cr);
                    saveObject(cr);
                }else{
                    CostRent cr = new CostRent();
                    cr.setCityName(cityName);
                    cr.setStoreNo(storeNo);
                    cr.setStoreName(storeName);
                    cr.setPropertyDeadline(propertyDeadline);
                    cr.setPropertyFeeYear(propertyFeeYear);
                    cr.setPropertyFeeMonth(propertyFeeMonth);
                    cr.setYear(year);
                    preObject(cr);
                    saveObject(cr);
                }

            }
            result.put("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
        }

        return result;
    }
}
