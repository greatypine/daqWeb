package com.cnpc.pms.platform.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.MemberDataDto;

import java.util.Map;


/**
 * @author wuxinxin
 * 社员相关操作
 * 2018-05-17
 */
public interface MemberInfo extends IManager{

	/**
	 * 查询社员数量
	 * @return
	 */
	public Map<String, Object> selectAllCm(String dd);
	/**
	 * 查询社员Gmv,单量
	 * @return
	 */
	public Map<String, Object> selectCmGmv(String dd);

    /**
	 * 查询社员来源
	 * @return
	 */
	public Map<String, Object> selectMemFrom(String dd);

    /**
	 * 查询社员注册城市分布
	 * @return
	 */
	public Map<String, Object> selectRegistCity(String dd);


    /**
     * 查询社员消费频次
     * @return
     */
    public Map<String, Object> selectMemShoping(String dd);

    /**
     * 查询非社员消费频次
     * @return
     */
    public Map<String, Object> selectNmemShoping(String dd);

     /**
     * 查询社员订单量、GMV分布
     * @return
     */
    public Map<String, Object> selectMemNoMem(String dd);


}