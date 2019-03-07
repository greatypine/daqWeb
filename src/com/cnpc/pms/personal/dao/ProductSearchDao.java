package com.cnpc.pms.personal.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;

import java.util.List;
import java.util.Map;

/**
 * @Function: 商品搜索
 * @Author: chenchuang
 * @Date: 2019/3/6 12:26
 */
public interface ProductSearchDao extends IDAO {

    public Map<String, Object> queryProductList(DynamicDto dynamicDto, PageInfo pageInfo);

    public List<Map<String, Object>> exportProductList(DynamicDto dynamicDto);

}
