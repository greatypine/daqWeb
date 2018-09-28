package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostRentContract;
import com.cnpc.pms.costStatistics.manager.CostRentContractManager;
import com.cnpc.pms.costStatistics.util.CostRentContractExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/9/18 10:53
 */
public class CostRentContractManagerImpl extends BizBaseCommonManager implements CostRentContractManager {
    @Override
    public Map<String, Object> queryCostRentContract(CostDto costDto) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRentContract(costDto);
        result.put("rentContract",list);
        return result;
    }

    @Override
    public Map<String, Object> saveCostRentContract(List<Map<String, Object>> list) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            Map<String,Object> map  = null;
            IFilter filter = null;
            List<CostRentContract> lst_costRentContract=null;
            for(int i=0;i<list.size();i++){
                String cityName = list.get(i).get("cityName")==null?"":String.valueOf(list.get(i).get("cityName"));
                String storeNo = list.get(i).get("storeNo")==null?"":String.valueOf(list.get(i).get("storeNo"));
                String storeName = list.get(i).get("storeName")==null?"":String.valueOf(list.get(i).get("storeName"));
                Double rentalMonth = list.get(i).get("rentalMonth")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("rentalMonth")));
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
                String freeLeaseStartDate = list.get(i).get("freeLeaseStartDate")==null?null:String.valueOf(list.get(i).get("freeLeaseStartDate"));
                String leaseStartDate = list.get(i).get("leaseStartDate")==null?null:String.valueOf(list.get(i).get("leaseStartDate"));
                String leaseStopDate = list.get(i).get("leaseStopDate")==null?null:String.valueOf(list.get(i).get("leaseStopDate"));
                filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and expiration_contract=0");
                lst_costRentContract = (List<CostRentContract>) this.getList(filter);

                if (lst_costRentContract != null && lst_costRentContract.size() > 0) {
                    CostRentContract crc = lst_costRentContract.get(0);
                    crc.setCityName(cityName);
                    crc.setStoreNo(storeNo);
                    crc.setStoreName(storeName);
                    crc.setContractGrandTotal(contractGrandTotal);
                    crc.setRentalMonth(rentalMonth);
                    crc.setFirstYearRent(firstYearRent);
                    crc.setSecondYearRent(secondYearRent);
                    crc.setThirtYearRent(thirtYearRent);
                    crc.setFourthYearRent(fourthYearRent);
                    crc.setFifthYearRent(fifthYearRent);
                    crc.setAgencyFee(agencyFee);
                    crc.setDeposit(deposit);
                    crc.setStructureAcreage(structureAcreage);
                    crc.setLeaseUnitPrice(leaseUnitPrice);
                    crc.setLeaseStartDate(leaseStartDate);
                    crc.setLeaseStopDate(leaseStopDate);
                    crc.setFreeLeaseStartDate(freeLeaseStartDate);
                    preObject(crc);
                    saveObject(crc);
                }else{
                    CostRentContract crc = new CostRentContract();
                    crc.setCityName(cityName);
                    crc.setStoreNo(storeNo);
                    crc.setStoreName(storeName);
                    crc.setContractGrandTotal(contractGrandTotal);
                    crc.setRentalMonth(rentalMonth);
                    crc.setFirstYearRent(firstYearRent);
                    crc.setSecondYearRent(secondYearRent);
                    crc.setThirtYearRent(thirtYearRent);
                    crc.setFourthYearRent(fourthYearRent);
                    crc.setFifthYearRent(fifthYearRent);
                    crc.setAgencyFee(agencyFee);
                    crc.setDeposit(deposit);
                    crc.setStructureAcreage(structureAcreage);
                    crc.setLeaseUnitPrice(leaseUnitPrice);
                    crc.setLeaseStartDate(leaseStartDate);
                    crc.setLeaseStopDate(leaseStopDate);
                    crc.setFreeLeaseStartDate(freeLeaseStartDate);
                    crc.setExpirationContract(0);
                    preObject(crc);
                    saveObject(crc);
                }


            }
            result.put("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
        }

        return result;
    }

    @Override
    public Map<String, Object> exportCostRentContract(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRentContract(costDto);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostRentContractExcel costRentContractExcel = new CostRentContractExcel(list);
        result = costRentContractExcel.exportFile();
        return result;
    }
}
