package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.entity.CostRenovation;
import com.cnpc.pms.costStatistics.manager.CostRenovationManager;
import com.cnpc.pms.costStatistics.util.CostRenovationExcel;
import com.cnpc.pms.costStatistics.util.CostRentExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 装修摊销
 * @Author: gbl
 * @CreateDate: 2018/8/28 15:09
 */
public class CostRenovationManagerImpl extends BizBaseCommonManager implements CostRenovationManager {
    @Override
    public Map<String, Object> queryCostRenovation(String storeNo, String storeName) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRenovation(storeNo,storeName);
        result.put("renovation",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostRenovation(String storeNo, String storeName) {

        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostRenovation(storeNo,storeName);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }
        CostRenovationExcel costRenovationExcel = new CostRenovationExcel(list);
        result = costRenovationExcel.exportFile();
        return result;

    }

    @Override
    public Map<String, Object> saveCostRenovation(List<Map<String, Object>> list) {

        Map<String,Object> result = new HashMap<String,Object>();
        IFilter filter = null;
        List<CostRenovation> lst_costRenovation=null;
        try {
            if(list!=null&&list.size()>0){
                for(int i=0;i<list.size();i++){
                    Map<String,Object> obj = list.get(i);
                    String storeNo = obj.get("storeNo")==null?"":String.valueOf(obj.get("storeNo"));
                    String storeName = obj.get("storeName")==null?"":String.valueOf(obj.get("storeName"));

                    String decorationCompany = obj.get("decorationCompany")==null?"":String.valueOf(obj.get("decorationCompany"));
                    Double structureAcreage = obj.get("structureAcreage")==null?null:Double.parseDouble(String.valueOf(obj.get("structureAcreage")));
                    Double renovationUnitPrice = obj.get("renovationUnitPrice")==null?null:Double.parseDouble(String.valueOf(obj.get("renovationUnitPrice")));
                    Double decorateCost = obj.get("decorateCost")==null?null:Double.parseDouble(String.valueOf(obj.get("decorateCost")));
                    Double businessScreen = obj.get("businessScreen")==null?null:Double.parseDouble(String.valueOf(obj.get("businessScreen")));
                    Double furniture = obj.get("furniture")==null?null:Double.parseDouble(String.valueOf(obj.get("furniture")));
                    Double lightBox = obj.get("lightBox")==null?null:Double.parseDouble(String.valueOf(obj.get("lightBox")));
                    Double processManage = obj.get("processManage")==null?null:Double.parseDouble(String.valueOf(obj.get("processManage")));
                    Double processManageSurcharge = obj.get("processManageSurcharge")==null?null:Double.parseDouble(String.valueOf(obj.get("processManageSurcharge")));
                    Double airConditioner = obj.get("airConditioner")==null?null:Double.parseDouble(String.valueOf(obj.get("airConditioner")));
                    Double airConditionerSurcharge = obj.get("airConditionerSurcharge")==null?null:Double.parseDouble(String.valueOf(obj.get("airConditionerSurcharge")));
                    Double design = obj.get("design")==null?null:Double.parseDouble(String.valueOf(obj.get("design")));
                    Double total = obj.get("total")==null?null:Double.parseDouble(String.valueOf(obj.get("total")));
                    Integer amortizeMonth = obj.get("amortizeMonth")==null?null:Integer.parseInt(String.valueOf(obj.get("amortizeMonth")));
                    Double amortizeMoney = obj.get("amortizeMoney")==null?null:Double.parseDouble(String.valueOf(obj.get("amortizeMoney")));
                    String completedDate = obj.get("completedDate")==null?null:String.valueOf(obj.get("completedDate"));
                    String contractDate = list.get(i).get("contractDate")==null?null:String.valueOf(list.get(i).get("contractDate"));
                    filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"'");
                    lst_costRenovation = (List<CostRenovation>) this.getList(filter);
                    CostRenovation cr = null;
                    if(lst_costRenovation!=null&&lst_costRenovation.size()>0) {
                        cr = lst_costRenovation.get(0);
                    }else{
                        cr = new CostRenovation();
                    }
                    cr.setStoreNo(storeNo);
                    cr.setStoreName(storeName);
                    cr.setDecorationCompany(decorationCompany);
                    cr.setStructureAcreage(structureAcreage);
                    cr.setRenovationUnitPrice(renovationUnitPrice);
                    cr.setDecorateCost(decorateCost);
                    cr.setBusinessScreen(businessScreen);
                    cr.setFurniture(furniture);
                    cr.setLightBox(lightBox);
                    cr.setProcessManage(processManage);
                    cr.setProcessManageSurcharge(processManageSurcharge);
                    cr.setAirConditioner(airConditioner);
                    cr.setAirConditionerSurcharge(airConditionerSurcharge);
                    cr.setDesign(design);
                    cr.setTotal(total);
                    cr.setAmortizeMonth(amortizeMonth);
                    cr.setAmortizeMoney(amortizeMoney);
                    cr.setCompletedDate(completedDate);
                    cr.setContractDate(contractDate);
                    preObject(cr);
                    saveObject(cr);
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
