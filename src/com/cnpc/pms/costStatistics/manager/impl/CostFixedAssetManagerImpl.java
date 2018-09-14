package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.entity.CostFixedAsset;
import com.cnpc.pms.costStatistics.entity.CostRenovation;
import com.cnpc.pms.costStatistics.manager.CostFixedAssetManager;
import com.cnpc.pms.costStatistics.util.CostFixedAssetExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 固定资产
 * @Author: gbl
 * @CreateDate: 2018/9/11 10:44
 */
public class CostFixedAssetManagerImpl extends BizBaseCommonManager implements CostFixedAssetManager {

    @Override
    public Map<String, Object> queryCostFixedAsset(String storeNo, String storeName) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostFixedAsset(storeNo,storeName);
        result.put("fixedAsset",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostFixedAsset(String storeNo, String storeName) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostFixedAsset(storeNo,storeName);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }

        CostFixedAssetExcel costFixedAssetExcel = new CostFixedAssetExcel(list);
        result = costFixedAssetExcel.exportFile();
        return result;

    }

    @Override
    public Map<String, Object> saveCostFixedAsset(List<Map<String, Object>> list) {

        Map<String,Object> result = new HashMap<String,Object>();
        IFilter filter = null;
        List<CostFixedAsset> lst_costFixedAsset=null;
        try {
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    Map<String,Object> obj = list.get(i);
                    String storeNo = obj.get("storeNo")==null?"":String.valueOf(obj.get("storeNo"));
                    String storeName = obj.get("storeName")==null?"":String.valueOf(obj.get("storeName"));

                    Double amortizeMoney = obj.get("amortizeMoney")==null?null:Double.parseDouble(String.valueOf(obj.get("amortizeMoney")));
                    Double total = obj.get("total")==null?null:Double.parseDouble(String.valueOf(obj.get("total")));
                    Double aio = obj.get("aio")==null?null:Double.parseDouble(String.valueOf(obj.get("aio")));
                    Double mobilePhone = obj.get("mobilePhone")==null?null:Double.parseDouble(String.valueOf(obj.get("mobilePhone")));
                    Double iPad = obj.get("iPad")==null?null:Double.parseDouble(String.valueOf(obj.get("iPad")));
                    Double cashRegister = obj.get("cashRegister")==null?null:Double.parseDouble(String.valueOf(obj.get("cashRegister")));
                    Double computer = obj.get("computer")==null?null:Double.parseDouble(String.valueOf(obj.get("computer")));
                    Double scannerGun = obj.get("scannerGun")==null?null:Double.parseDouble(String.valueOf(obj.get("scannerGun")));
                    Double electronicsTotal = obj.get("electronicsTotal")==null?null:Double.parseDouble(String.valueOf(obj.get("electronicsTotal")));
                    Double electronicsAmortize = obj.get("electronicsAmortize")==null?null:Double.parseDouble(String.valueOf(obj.get("electronicsAmortize")));
                    Double electricCars = obj.get("electricCars")==null?null:Double.parseDouble(String.valueOf(obj.get("electricCars")));
                    Double electricCarsAmortize = obj.get("electricCarsAmortize")==null?null:Double.parseDouble(String.valueOf(obj.get("electricCarsAmortize")));
                    Double coldChain = obj.get("coldChain")==null?null:Double.parseDouble(String.valueOf(obj.get("coldChain")));
                    Double safeBox = obj.get("safeBox")==null?null:Double.parseDouble(String.valueOf(obj.get("safeBox")));
                    Double capsuleGoodsShelf = obj.get("capsuleGoodsShelf")==null?null:Double.parseDouble(String.valueOf(obj.get("capsuleGoodsShelf")));
                    Double shoppingGoodsShelf = list.get(i).get("shoppingGoodsShelf")==null?null:Double.parseDouble(String.valueOf(obj.get("shoppingGoodsShelf")));
                    Double machineTotal = obj.get("machineTotal")==null?null:Double.parseDouble(String.valueOf(obj.get("machineTotal")));
                    Double machineAmortize = obj.get("machineAmortize")==null?null:Double.parseDouble(String.valueOf(obj.get("machineAmortize")));
                    filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"'");
                    lst_costFixedAsset = (List<CostFixedAsset>) this.getList(filter);
                    CostFixedAsset cfa = null;
                    if(lst_costFixedAsset!=null&&lst_costFixedAsset.size()>0) {
                        cfa = lst_costFixedAsset.get(0);
                    }else{
                        cfa = new CostFixedAsset();
                    }
                    cfa.setStoreNo(storeNo);
                    cfa.setStoreName(storeName);
                    cfa.setAio(aio);
                    cfa.setAmortizeMoney(amortizeMoney);
                    cfa.setCapsuleGoodsShelf(capsuleGoodsShelf);
                    cfa.setCashRegister(cashRegister);
                    cfa.setColdChain(coldChain);
                    cfa.setComputer(computer);
                    cfa.setElectricCars(electricCars);
                    cfa.setElectricCarsAmortize(electricCarsAmortize);
                    cfa.setElectronicsTotal(electronicsTotal);
                    cfa.setElectronicsAmortize(electronicsAmortize);
                    cfa.setiPad(iPad);
                    cfa.setMachineAmortize(machineAmortize);
                    cfa.setMachineTotal(machineTotal);
                    cfa.setMobilePhone(mobilePhone);
                    cfa.setSafeBox(safeBox);
                    cfa.setTotal(total);
                    cfa.setShoppingGoodsShelf(shoppingGoodsShelf);
                    cfa.setScannerGun(scannerGun);
                    preObject(cfa);
                    saveObject(cfa);
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
