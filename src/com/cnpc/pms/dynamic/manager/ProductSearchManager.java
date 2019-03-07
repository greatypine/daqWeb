package com.cnpc.pms.dynamic.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;

import java.util.List;
import java.util.Map;

/**
 * @Function: 商品搜索
 * @Author: chenchuang
 * @Date: 2019/3/5 17:16
 */
public interface ProductSearchManager extends IManager {

    public Map<String, Object> queryProductList(DynamicDto dynamicDto, PageInfo pageInfo);

    public Map<String, Object> exportProductList(DynamicDto dynamicDto);

}
