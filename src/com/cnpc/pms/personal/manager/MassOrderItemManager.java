package com.cnpc.pms.personal.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.DynamicDto;
import com.cnpc.pms.dynamic.entity.MassOrderItemDto;
import com.cnpc.pms.personal.entity.Store;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

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
	public Map<String, Object> exportOrder(MassOrderItemDto massOrderDto, TReportFiledown tReportFiledown);
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
	 * 查询昨日毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryYesterdayprofit(DynamicDto dd);
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
	/**
	 * 查询所有门店
	 * @return
	 */
	public List<Store> queryAllStore();
	/**
	 * 查询城市每天GMV&毛利&消费用户数&消费社员数&注册用户数
	 * &累计注册用户数&新增社员数&累计社员数
	 * @param dd
	 * @param pageInfo
	 * @param cityNO
	 * @return
	 */
	public Map<String, Object> queryDayGMVUserMemberProfit(DynamicDto dd,PageInfo pageInfo);
	/**
     * 查询门店近7日毛利订单量散点图
     * 2018年12月19日
     * @author zhangli
     * @param dd
     * @return
     */
	public Map<String, Object> getProfitRangeForStoreWeek(DynamicDto dd);
	/**
	 * 查询昨日门店毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryYesterdayprofitForStore(DynamicDto dd);
	/**
	 * 查询近7日门店毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryprofitForStoreSevenDay(DynamicDto dd);
	/**
	 * 查询近30日门店毛利
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryprofitForStoreThirtyday(DynamicDto dd);
	/**
	 * 查询昨日门店销售商品排名
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getYesterdayStoreProduct(DynamicDto dd);
	/**
	 * 查询近(7天)门店销售商品排名
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getStoreProductSevenDay(DynamicDto dd);
	/**
	 * 查询近(30天)门店销售商品排名
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getStoreProductThirtyDay(DynamicDto dd);
	
	/**
	 * 查询昨日门店销售商品排名列表(带分页)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getProductYesteryRank(DynamicDto dd,PageInfo pageInfo);
	/**
	 * 查询近(7天)门店销售商品排名(带分页)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getProductSevendayRank(DynamicDto dd,PageInfo pageInfo);
	/**
	 * 查询近(30天)门店销售商品排名(带分页)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getProductthirtydayRank(DynamicDto dd,PageInfo pageInfo);
	/**
	 * 查询昨日门店开卡社员数
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getStoreYesterdayMember(DynamicDto dynamicDto);
	/**
	 * 查询近7日门店开卡社员数
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getStoreSevendayMember(DynamicDto dynamicDto);
	/**
	 * 查询近30日门店开卡社员数
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getStoreThirtydayMember(DynamicDto dynamicDto);
	
}
