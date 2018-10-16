package com.cnpc.pms.personal.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;

/**
 * @Function：清洗出的订单详情Dao
 * @author：zhangli
 * @date:2018年9月20日 下午17:54:04
 *
 * @version V1.0
 */
public interface MassOrderItemDao extends IDAO{
	
	/**
	 * 通过Citycode查询Cityno
	 * @param cityCode
	 * @return
	 */
	 public Map<String, Object> queryCitynoByCode(String cityCode);
	 /**
		 * 根据订单号查国安侠信息
		 * @param order_sn
		 * @return
	*/
  public Map<String, Object> queryEmployeeBySN(String order_sn);
  	/**
	 * 根据片区编号查询信息
	 * @param area_code
	 * @param order_sn
	 * @param timeFlag
	 * @return
	 */
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String timeFlag);
	/**
	 * 查询订单数据列表
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto,PageInfo pageInfo,String timeFlag);
	
	/**
	 * 导出订单数据列表
	 * @param massOrderDto
	 * @return
	 */
	public List<Map<String, Object>> exportOrder(MassOrderItemDto massOrderDto,String timeFlag);
	
	/**
	 * 根据门店编号查Platformid
	 * @param storeno
	 * @return
	 */
	public Map<String, Object> queryPlatformidByCode(String storeno);
	/**
	 * 查询当日实时毛利(全国/省/市切换)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryDailyprofit(DynamicDto dd);
	/**
	 * 查询当月实时毛利(全国/省/市切换)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryMonthprofit(DynamicDto dd);
	
	
}
