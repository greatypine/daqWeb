package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
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
    public Map<String, Object> queryCostRent(String storeNo, String storeName,Integer year) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRent(storeNo,storeName,year);
        result.put("rent",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostRent(String storeNo, String storeName, Integer year) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRent(storeNo,storeName,year);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostRentExcel costRentExcel = new CostRentExcel(list);
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
                String storeNo = list.get(i).get("storeNo")==null?"":String.valueOf(list.get(i).get("storeNo"));
                String storeName = list.get(i).get("storeName")==null?"":String.valueOf(list.get(i).get("storeName"));
                String addr = list.get(i).get("addr")==null?"":String.valueOf(list.get(i).get("addr"));
                Double contractGrandTotal = list.get(i).get("contractGrandTotal")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("contractGrandTotal")));
                Double firstYearRent = list.get(i).get("firstYearRent")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("firstYearRent")));
                Double secondYearRent = list.get(i).get("secondYearRent")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("secondYearRent")));
                Double thirtYearRent = list.get(i).get("thirtYearRent")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("thirtYearRent")));
                Double fourthYearRent = list.get(i).get("fourthYearRent")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("fourthYearRent")));
                Double fifthYearRent = list.get(i).get("fifthYearRent")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("fifthYearRent")));
                Double structureAcreage = list.get(i).get("structureAcreage")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("structureAcreage")));
                Double leaseUnitPrice = list.get(i).get("leaseUnitPrice")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("leaseUnitPrice")));
                Double deposit = list.get(i).get("deposit")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("deposit")));
                Double agencyFee = list.get(i).get("agencyFee")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("agencyFee")));
                Double propertyFee = list.get(i).get("propertyFee")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("propertyFee")));
                Integer propertyDeadline = list.get(i).get("propertyDeadline")==null?null:Integer.parseInt(String.valueOf(list.get(i).get("propertyDeadline")));
                String freeLeaseStartDate = list.get(i).get("freeLeaseStartDate")==null?null:String.valueOf(list.get(i).get("freeLeaseStartDate"));
                String leaseStartDate = list.get(i).get("leaseStartDate")==null?null:String.valueOf(list.get(i).get("leaseStartDate"));
                String leaseStopDate = list.get(i).get("leaseStopDate")==null?null:String.valueOf(list.get(i).get("leaseStopDate"));
                Integer year = list.get(i).get("year")==null?null:Integer.parseInt(String.valueOf(list.get(i).get("year")));
                filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year);
                lst_costRent = (List<CostRent>) this.getList(filter);

                if (lst_costRent != null && lst_costRent.size() > 0) {
                    CostRent cr = lst_costRent.get(i);
                    cr.setStoreNo(storeNo);
                    cr.setStoreName(storeName);
                    cr.setAddr(addr);
                    cr.setContractGrandTotal(contractGrandTotal);
                    cr.setFirstYearRent(firstYearRent);
                    cr.setSecondYearRent(secondYearRent);
                    cr.setThirtYearRent(thirtYearRent);
                    cr.setFourthYearRent(fourthYearRent);
                    cr.setFifthYearRent(fifthYearRent);
                    cr.setAgencyFee(agencyFee);
                    cr.setDeposit(deposit);
                    cr.setStructureAcreage(structureAcreage);
                    cr.setPropertyDeadline(propertyDeadline);
                    cr.setLeaseUnitPrice(leaseUnitPrice);
                    cr.setPropertyFee(propertyFee);
                    cr.setLeaseStartDate(leaseStartDate);
                    cr.setLeaseStopDate(leaseStopDate);
                    cr.setFreeLeaseStartDate(freeLeaseStartDate);
                    preObject(cr);
                    saveObject(cr);
                }else{
                    CostRent cr = new CostRent();
                    cr.setStoreNo(storeNo);
                    cr.setStoreName(storeName);
                    cr.setAddr(addr);
                    cr.setYear(year);
                    cr.setContractGrandTotal(contractGrandTotal);
                    cr.setFirstYearRent(firstYearRent);
                    cr.setSecondYearRent(secondYearRent);
                    cr.setThirtYearRent(thirtYearRent);
                    cr.setFourthYearRent(fourthYearRent);
                    cr.setFifthYearRent(fifthYearRent);
                    cr.setAgencyFee(agencyFee);
                    cr.setDeposit(deposit);
                    cr.setStructureAcreage(structureAcreage);
                    cr.setPropertyDeadline(propertyDeadline);
                    cr.setLeaseUnitPrice(leaseUnitPrice);
                    cr.setPropertyFee(propertyFee);
                    cr.setLeaseStartDate(leaseStartDate);
                    cr.setLeaseStopDate(leaseStopDate);
                    cr.setFreeLeaseStartDate(freeLeaseStartDate);
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
