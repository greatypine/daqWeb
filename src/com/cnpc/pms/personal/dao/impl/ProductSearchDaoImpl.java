package com.cnpc.pms.personal.dao.impl;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.personal.dao.ProductSearchDao;
import com.cnpc.pms.utils.ImpalaUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Function: 商品搜索
 * @Author: chenchuang
 * @Date: 2019/3/6 12:27
 */
public class ProductSearchDaoImpl extends BaseDAOHibernate implements ProductSearchDao {

    public Map<String, Object> queryProductList(DynamicDto dynamicDto, PageInfo pageInfo){
        String sql = "select t2.content_name,t3.name,ifnull(t3.address,'') as address,t2.content_price,t2.member_price ,t1.pro_number," +
                "case when (t2.content_price is null or t2.content_price=0)  then 0.0 else dround(ifnull(t2.member_price,0)/ifnull(t2.content_price,0)*10,1) end as rate " +
                "from gemini.t_inventory t1,gemini.t_product t2,gemini.t_store t3 where t1.pro_id=t2.id and t1.store_id=t3.id and t1.pro_number>0 and t2.member_price is not null  ";
        if(StringUtils.isNotEmpty(dynamicDto.getCityName())){
            sql = sql + "and LPAD(t3.city_code,4,'0') = '"+dynamicDto.getCityName().trim()+"' ";
        }
        if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
            sql = sql + "and t3.code = '"+dynamicDto.getStoreNo().trim()+"' ";
        }
        if(StringUtils.isNotEmpty(dynamicDto.getSearchstr())){
            sql = sql + "and t2.content_name like '%"+dynamicDto.getSearchstr().trim()+"%' ";
        }
        if(dynamicDto.getTarget()!=10){
            sql = sql + "having rate<= "+dynamicDto.getTarget();
        }
        sql = sql + " order by t2.member_price asc,t1.pro_number desc ";

        String sql_count = "SELECT COUNT(1) as total FROM (" + sql + ") T";

        int startData = (pageInfo.getCurrentPage() - 1) * pageInfo.getRecordsPerPage();
        int recordsPerPage = pageInfo.getRecordsPerPage();
        sql = sql + " LIMIT " + recordsPerPage + " offset " + startData;
        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);

        String total = "0";
        List<Map<String,Object>> resultCount = ImpalaUtil.executeGuoan(sql_count);
        if(resultCount !=null && resultCount.size()>0 ){
            total = String.valueOf(resultCount.get(0).get("total"));
        }

        pageInfo.setTotalRecords(Integer.valueOf(total.toString()));
        Map<String, Object> map_result = new HashMap<String, Object>();
        Integer total_pages = (pageInfo.getTotalRecords() - 1) / pageInfo.getRecordsPerPage() + 1;
        map_result.put("pageinfo", pageInfo);
        map_result.put("data", list);
        map_result.put("total_pages", total_pages);
        return map_result;
    }

    public List<Map<String, Object>> exportProductList(DynamicDto dynamicDto){
        String sql = "select t2.content_name,t3.name,ifnull(t3.address,'') as address,t2.content_price,t2.member_price ,t1.pro_number," +
                "case when (t2.content_price is null or t2.content_price=0)  then 0.0 else dround(ifnull(t2.member_price,0)/ifnull(t2.content_price,0)*10,1) end as rate " +
                "from gemini.t_inventory t1,gemini.t_product t2,gemini.t_store t3 where t1.pro_id=t2.id and t1.store_id=t3.id and t1.pro_number>0 and t2.member_price is not null  ";
        if(StringUtils.isNotEmpty(dynamicDto.getCityName())){
            sql = sql + "and LPAD(t3.city_code,4,'0') = '"+dynamicDto.getCityName().trim()+"' ";
        }
        if(StringUtils.isNotEmpty(dynamicDto.getStoreNo())){
            sql = sql + "and t3.code = '"+dynamicDto.getStoreNo().trim()+"' ";
        }
        if(StringUtils.isNotEmpty(dynamicDto.getSearchstr())){
            sql = sql + "and t2.content_name like '%"+dynamicDto.getSearchstr().trim()+"%' ";
        }
        if(dynamicDto.getTarget()!=10){
            sql = sql + "having rate<= "+dynamicDto.getTarget();
        }
        sql = sql + " order by t2.member_price asc,t1.pro_number desc ";

        List<Map<String,Object>> list = ImpalaUtil.executeGuoan(sql);
        return list;
    }

}
