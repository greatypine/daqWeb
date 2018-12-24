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
	 * 查询订单数据列表
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryMassOrderItem(MassOrderItemDto massOrderDto,PageInfo pageInfo);
	
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
	public Map<String, Object> queryDailyprofit(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	/**
	 * 查询昨日毛利(全国/省/市切换)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryYesterdayprofit(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	/**
	 * 查询当月实时毛利(全国/省/市切换)
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryMonthprofit(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	/**
	 * 查询近七日毛利走势图
	 * @author zhangli
	 * 2018年10月21日
	 * @param dd
	 * @param provinceNO 
	 * @param cityNO 
	 * @return
	 */
	public Map<String, Object> getProfitRangeForWeek(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);

	public void updataReport(Long id ,String url);
	/**
	 * 查询片区某月消费超10元用户量
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryAreaUserByAreaCode(DynamicDto dd);
	/**
	 * 查询门店某月消费超10元用户量
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryAreaUserByStoreNo(DynamicDto dd);
	/**
	 * 查询片区某月开卡数
	 * @param dd
	 * @return
	 */
	public Map<String, Object> queryAreaOpenCardByAreaCode(DynamicDto dd);
	/**
	 * 查询店长某月开卡数
	 * @param dd
	 * @param employee_no
	 * @return
	 */
	public Map<String, Object> queryAreaOpenCardByStoreKeeperNo(DynamicDto dd,String employee_no);
	/**
	 * 查询推荐产品列表
	 * @param dd
	 * @param pageInfo
	 * @param platformid
	 * @return
	 */
	public Map<String, Object> queryRecommendUser(PageInfo pageInfo, String employee_no);
	/**
	 * 查询所有的门店
	 * @return
	 */
	public List<Map<String, Object>> findAllStore();
	/**
	 * 查询城市每天GMV&毛利&消费用户数&消费社员数&注册用户数
	 * @param dd
	 * @param cityNO
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryDayGMVUserMemberProfit(DynamicDto dd,String cityNO,PageInfo pageInfo);
	/**
	 * 查询门店近7日毛利订单量散点图
	 * @param dd
	 * @param cityNO
	 * @param provinceNO
	 * @return
	 */
	public Map<String, Object> getProfitRangeForStoreWeek(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	
}
