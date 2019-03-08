package com.cnpc.pms.dynamic.manager.impl;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.manager.ProductSearchManager;
import com.cnpc.pms.personal.dao.ProductSearchDao;
import com.cnpc.pms.personal.dao.StoreDao;
import com.cnpc.pms.utils.ExportExcelByOssUtil;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function: 商品搜索
 * @Author: chenchuang
 * @Date: 2019/3/5 17:19
 */
public class ProductSearchManagerImpl extends BizBaseCommonManager implements ProductSearchManager {

    public Map<String, Object> queryProductList(DynamicDto dynamicDto, PageInfo pageInfo){

        ProductSearchDao productSearchDao = (ProductSearchDao)SpringHelper.getBean(ProductSearchDao.class.getName());
        Map<String, Object> result =new HashMap<String,Object>();
        try {
            result = productSearchDao.queryProductList(dynamicDto,pageInfo);
            result.put("status","success");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
        }
        return result;

    }

    public Map<String, Object> exportProductList(DynamicDto dynamicDto){
        ProductSearchDao productSearchDao = (ProductSearchDao)SpringHelper.getBean(ProductSearchDao.class.getName());
        Map<String, Object> result = new HashMap<String,Object>();
        List<Map<String, Object>> list = productSearchDao.exportProductList(dynamicDto);

        if(list!=null&&list.size()>0){//成功返回数据
            if(list.size()>50000){
                result.put("message","导出条目过多，请重新筛选条件导出！");
                result.put("status","more");
                return result;
            }
            String[] str_headers = new String[]{"商品名称","门店名称","地址","原价","社员价","库存","折扣"};
            String[] headers_key = new String[]{"content_name","name","address","content_price","member_price","pro_number","rate"};
            ExportExcelByOssUtil eeuo = new ExportExcelByOssUtil("城市单位毛利",list,str_headers,headers_key);
            result = eeuo.exportFile();
        }else{
            result.put("message","请重新操作！");
            result.put("status","fail");
        }
        return result;
    }

}
