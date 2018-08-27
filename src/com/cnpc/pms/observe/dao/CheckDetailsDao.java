package com.cnpc.pms.observe.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by h on 2018/8/23.
 */
public interface CheckDetailsDao {

    public List<Map<String,Object>> getCheckDetailsById(Long id);
}
