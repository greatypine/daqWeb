package com.cnpc.pms.personal.manager;

import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;

/**
 * @Function：订单详情数据
 * @author：zhangli
 * @date:2018年9月17日 上午10:09:20
 *
 * @version V1.0
 */
public interface MassOrderItemManager extends IManager {
	
	/**
	 * 通过城市code查询城市No
	 * @return
	 */
	public Map<String, Object> queryCitynoByCode(String cityCode);
	
	/**
	 * 根据片区编号查询信息
	 * @param area_code
	 * @return
	 */
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String beginDate);
	
	/**
	 * 根据订单号查询国安侠信息
	 * @param order_sn
	 * @return
	 */
	public Map<String, Object> queryEmployeeBySN(String order_sn);

	/**
	 * 查询订单信息
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto,PageInfo pageInfo);
	
	/**
	 * 导出订单信息
	 * @param massOrderDto
	 * @return
	 */
	public Map<String, Object> exportOrder(MassOrderItemDto massOrderDto);
	/**
	 * 查询当日实时毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryDailyprofit(DynamicDto dd);
	/**
	 * 查询当月实时毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryMonthprofit(DynamicDto dd);
	/**
	 * 查询历史毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryHistoryprofit(DynamicDto dd);
	/**
	 * 根据订单sn查询订单该产品明细
	 * @param order_sn
	 * @param product_id
	 * @return
	 */
	public Map<String, Object> queryOrderDetailBySN(String order_sn,String product_id);
	/**
     * 查询毛利近七日走势图
     * 2018年10月21日
     * @author zhangli
     * @param dd
     * @return
     */
	public Map<String, Object> getProfitRangeForWeek(DynamicDto dd);
}
