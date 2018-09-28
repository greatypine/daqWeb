package com.cnpc.pms.costStatistics.manager.impl;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.costStatistics.dao.CostStatisticsDao;
import com.cnpc.pms.costStatistics.dto.CostDto;
import com.cnpc.pms.costStatistics.entity.CostLabor;
import com.cnpc.pms.costStatistics.entity.CostUniform;
import com.cnpc.pms.costStatistics.manager.CostUniformManager;
import com.cnpc.pms.costStatistics.util.CostUniformExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: daqWeb
 * @Package: com.cnpc.pms.costStatistics.manager.impl
 * @Description: 工服年费
 * @Author: gbl
 * @CreateDate: 2018/9/26 18:01
 */
public class CostUniformManagerImpl extends BizBaseCommonManager implements CostUniformManager {
    @Override
    public Map<String, Object> queryCostUniform(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostUniform(costDto);
        result.put("uniform",list);
        return result;
    }

    @Override
    public Map<String, Object> exportCostUniform(CostDto costDto) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,Object>> list = costStatisticsDao.queryCostUniform(costDto);
        if(list==null||list.size()==0){
            result.put("message","没有符合条件的数据！");
            result.put("status","null");
            return result;
        }

        CostUniformExcel costUniformExcel = new CostUniformExcel(list);
        result = costUniformExcel.exportFile();
        return result;
    }

    @Override
    public Map<String, Object> saveCostUniform(List<Map<String, Object>> list) {
        CostStatisticsDao costStatisticsDao = (CostStatisticsDao) SpringHelper.getBean(CostStatisticsDao.class.getName());
        Map<String,Object> result = new HashMap<String,Object>();
        try {

            Map<String,Object> map  = null;
            IFilter filter = null;
            List<CostUniform> lst_costUniform=null;
            for(int i=0;i<list.size();i++){
                String cityName = list.get(i).get("cityName")==null?"":String.valueOf(list.get(i).get("cityName"));
                String storeNo = list.get(i).get("storeNo")==null?"":String.valueOf(list.get(i).get("storeNo"));
                String storeName = list.get(i).get("storeName")==null?"":String.valueOf(list.get(i).get("storeName"));
                Integer year  = list.get(i).get("year")==null?0:Integer.parseInt(list.get(i).get("year").toString());
                Double uniformAmortize = list.get(i).get("uniformAmortize")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("uniformAmortize")));
                Double uniformCharge = list.get(i).get("uniformCharge")==null?null:Double.parseDouble(String.valueOf(list.get(i).get("uniformCharge")));
                filter = FilterFactory.getSimpleFilter("storeNo='"+storeNo+"' and year="+year);
                lst_costUniform = (List<CostUniform>) this.getList(filter);

                if (lst_costUniform != null && lst_costUniform.size() > 0) {
                    CostUniform cu = lst_costUniform.get(0);
                    cu.setUniformAmortize(uniformAmortize);
                    cu.setUniformCharge(uniformCharge);
                    preObject(cu);
                    saveObject(cu);
                }else{
                    CostUniform cu = new CostUniform();
                    cu.setUniformAmortize(uniformAmortize);
                    cu.setUniformCharge(uniformCharge);
                    cu.setCityName(cityName);
                    cu.setStoreNo(storeNo);
                    cu.setStoreName(storeName);
                    cu.setYear(year);
                    preObject(cu);
                    saveObject(cu);
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
