package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostOperation;
import com.cnpc.pms.costStatistics.manager.CostOperationManager;
import com.cnpc.pms.costStatistics.util.CostOperationExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description:运营成本
 * @Author: gbl
 * @CreateDate: 2018/9/19 19:39
 */
public class CostOperationManagerImpl extends BizBaseCommonManager implements CostOperationManager {

    @Override
    public Map<String, Object> queryCostOperation(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostOperation(costDto);
        result.put("operation",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostOperation(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostOperation(costDto);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }

        CostOperationExcel costOperationExcel = new CostOperationExcel(list);
        result = costOperationExcel.exportFile();
        return result;

    }

    @Override
    public Map<String, Object> saveCostOperation(List<Map<String, Object>> list) {
        Map<String,Object> result = new HashMap<String,Object>();
        IFilter filter = null;
        List<CostOperation> lst_costOperation=null;
        try {
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    Map<String,Object> obj = list.get(i);
                    String storeNo = obj.get("storeNo")==null?"":String.valueOf(obj.get("storeNo"));
                    String storeName = obj.get("storeName")==null?"":String.valueOf(obj.get("storeName"));
                    String cityName = obj.get("cityName")==null?"":String.valueOf(obj.get("cityName"));

                    Double dailyOffice = obj.get("dailyOffice")==null?null:Double.parseDouble(String.valueOf(obj.get("dailyOffice")));
                    Double rent = obj.get("rent")==null?null:Double.parseDouble(String.valueOf(obj.get("rent")));
                    Double barrelledWater = obj.get("barrelledWater")==null?null:Double.parseDouble(String.valueOf(obj.get("barrelledWater")));
                    Double storeInsurance = obj.get("storeInsurance")==null?null:Double.parseDouble(String.valueOf(obj.get("storeInsurance")));
                    Double carMaintain = obj.get("carMaintain")==null?null:Double.parseDouble(String.valueOf(obj.get("carMaintain")));
                    Double shoppingBag = obj.get("shoppingBag")==null?null:Double.parseDouble(String.valueOf(obj.get("shoppingBag")));
                    Double garbageBag = obj.get("garbageBag")==null?null:Double.parseDouble(String.valueOf(obj.get("garbageBag")));
                    Double extinguisher = obj.get("extinguisher")==null?null:Double.parseDouble(String.valueOf(obj.get("extinguisher")));
                    Double backpack = obj.get("backpack")==null?null:Double.parseDouble(String.valueOf(obj.get("backpack")));
                    Double helmet = obj.get("helmet")==null?null:Double.parseDouble(String.valueOf(obj.get("helmet")));
                    Double greenPlants = obj.get("greenPlants")==null?null:Double.parseDouble(String.valueOf(obj.get("greenPlants")));
                    Double tray = obj.get("tray")==null?null:Double.parseDouble(String.valueOf(obj.get("tray")));
                    Double storageMaterials = obj.get("storageMaterials")==null?null:Double.parseDouble(String.valueOf(obj.get("storageMaterials")));
                    Double activityFee = obj.get("activityFee")==null?null:Double.parseDouble(String.valueOf(obj.get("activityFee")));
                    Double decorationMaintain = obj.get("decorationMaintain")==null?null:Double.parseDouble(String.valueOf(obj.get("decorationMaintain")));
                    Double yearCharge = list.get(i).get("yearCharge")==null?null:Double.parseDouble(String.valueOf(obj.get("yearCharge")));
                    Double monthCharge = obj.get("monthCharge")==null?null:Double.parseDouble(String.valueOf(obj.get("monthCharge")));
                    Integer year = obj.get("year")==null?null:Integer.parseInt(String.valueOf(obj.get("year")));
                    filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year);
                    lst_costOperation = (List<CostOperation>) this.getList(filter);
                    CostOperation co = null;
                    if(lst_costOperation!=null&&lst_costOperation.size()>0) {
                        co = lst_costOperation.get(0);
                    }else{
                        co = new CostOperation();
                    }
                    co.setCityName(cityName);
                    co.setStoreNo(storeNo);
                    co.setStoreName(storeName);
                    co.setDailyOffice(dailyOffice);
                    co.setRent(rent);
                    co.setBarrelledWater(barrelledWater);
                    co.setStoreInsurance(storeInsurance);
                    co.setCarMaintain(carMaintain);
                    co.setShoppingBag(shoppingBag);
                    co.setGarbageBag(garbageBag);
                    co.setExtinguisher(extinguisher);
                    co.setBackpack(backpack);
                    co.setHelmet(helmet);
                    co.setGreenPlants(greenPlants);
                    co.setTray(tray);
                    co.setStorageMaterials(storageMaterials);
                    co.setActivityFee(activityFee);
                    co.setDecorationMaintain(decorationMaintain);
                    co.setYearCharge(yearCharge);
                    co.setMonthCharge(monthCharge);
                    co.setYear(year);
                    preObject(co);
                    saveObject(co);
                }
            }
            result.put("status","success");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.put("status","success");
        }

        return result;
    }
}
