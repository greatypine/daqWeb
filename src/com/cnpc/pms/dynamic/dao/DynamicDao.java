/**
 * gaobaolei
 */
package com.cnpc.pms.dynamic.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.AbnormalOrderDto;
import com.cnpc.pms.dynamic.entity.DynamicDto;



/**
 * @author gaobaolei
 *  社区动态
 */
public interface DynamicDao extends IDAO{
	
	/**
	 * 
	 * TODO 门店拉新
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getNewaddcus(DynamicDto dd);
	
	/**
	 * 
	 * TODO  国安侠拉新
	 * 2018年1月26日
	 * @author gaobaolei
	 * @param dd
	 * @return
	 */
	public int  getNewaddcusOfGAX(DynamicDto dd);
	
	/**
	 * 
	 * TODO 获取复购客户数量 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getRebuycus(DynamicDto dd);
	
	/**
	 * 
	 * TODO 门店gmv
	 * 2017年7月26日
	 * @author gaobaolei
	 * @edit 2018年1月26日
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public double  getStoretrade(DynamicDto dd);
	
	/**
	 * 
	 * TODO 国安侠GMV 
	 * 2018年1月26日
	 * @author gaobaolei
	 * @param dd
	 * @return
	 */
	public double  getStoretradeOfGAX(DynamicDto dd);
	
	/**
	 * 
	 * TODO 获取送单量
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getSendorders (DynamicDto dd);
	
	/**
	 * 
	 * TODO  获取好评次数
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getRewardtimes(DynamicDto dd);
	
	/**
	 * 
	 * TODO 获取拜访记录 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getRelation(DynamicDto dd);
	
	/**
	 * 
	 * TODO 获取单体画像 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getCustomer(DynamicDto dd);
	
	/**
	 * 
	 * TODO 商业信息 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getBusinessInfo(DynamicDto dd);
	
	/**
	 * 
	 * TODO 查询商业信息 
	 * 2017年8月9日
	 * @author gaobaolei
	 * @param dd
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryBusiness(DynamicDto dd,PageInfo pageInfo);
	
	public List<Map<String, Object>> queryBusiness(DynamicDto dd);
	
	/**
	 * 
	 * TODO 商业楼宇 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public int  getOfficeInfo(DynamicDto dd);
	
	/**
	 * 
	 * TODO  查询写字楼
	 * 2017年8月9日
	 * @author gaobaolei
	 * @param dd
	 * @param pageinfo
	 * @return
	 */
	public Map<String,Object> queryOffice(DynamicDto dd,PageInfo pageinfo);
	
	public List<Map<String,Object>> queryOffice(DynamicDto dd);
	
	/**
	 * 
	 * TODO 查询交易额top10 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>>  getStoretradeList(Long cityId,Long employeeId,Integer year,Integer month,String flag);
	
	/**
	 * 
	 * TODO 查询订单top10 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>>  getStoreOrderList(Long cityId,String employeeId,Integer year,Integer month,String flag);
	
	/**
	 * 
	 * TODO 查询拜访记录top10 
	 * 2017年7月26日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<Map<String, Object>>  getRelationList(Long cityId,String employeeId,Integer year,Integer month,String flag);
	
	/**
	 * 
	 * TODO 获取覆盖的住户 
	 * 2017年7月28日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @return
	 */
	public int getAllHouseAmount(Long cityId,Long employeeId,Integer house_type);
	
	/**
	 * 
	 * TODO 获取覆盖的小区 
	 * 2017年7月28日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @return
	 */
	public int getAllTinyVillageAmount(Long cityId,Long employeeId);
	
	/**
	 * 
	 * TODO 获取覆盖的社区 
	 * 2017年7月28日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @return
	 */
	public int getAllVillageAmount(Long cityId,Long employeeId);
	
	/**
	 * 
	 * TODO 获取覆盖的街道 
	 * 2017年7月28日
	 * @author gaobaolei
	 * @param cityId
	 * @param employeeId
	 * @return
	 */
	public int getAllTownAmount(Long cityId,Long employeeId);
	
	

	/**
     * 
     * TODO 查找异常订单 (分页)
     * 2017年9月19日
     * @author gaobaolei
     * @param param
     * @return
     */
    public Map<String, Object> queryAbnormalOrder(AbnormalOrderDto abnormalOrderDto,PageInfo pageInfo);
    
    
    public List<Map<String, Object>> queryAbnormalOrder(AbnormalOrderDto abnormalOrderDto);
    
    /**
     * 
     * TODO  查找异常订单类型
     * 2017年9月19日
     * @author gaobaolei
     * @return
     */
    public List<Map<String, Object>> queryAbnormalType();
    
    /**
     * 
     * TODO 查询异常订单类型是否存在 
     * 2017年10月18日
     * @author gaobaolei
     * @param descrip
     * @return
     */
    public List<Map<String, Object>> queryAbnormalType(String descrip);
    
    /**
     * 
     * TODO 根据订单号查询异常订单 
     * 2017年9月21日
     * @author gaobaolei
     * @param ordersn
     * @return
     */
    public List<Map<String, Object>> queryAbnormalByOrderSn(String ordersn);
    
    /**
     * 
     * TODO 查找员工管理的城市 
     * 2017年9月27日
     * @author gaobaolei
     * @param userid
     * @param name
     * @return
     */
    public List<Map<String, Object>> queryCityByUser(Integer target,Long userid,String name);
    
    /***
     * 
     * TODO  查询各门店事业部的服务专员交易额
     * 2017年10月24日
     * @author gaobaolei
     * @param storeno
     * @return
     */
    public List<Map<String, Object>>  queryStoreTradeOfDept(DynamicDto dynamicDto);
    
    
    /***
     * 
     * TODO  查询城市上个月GMV排名-带分页
     * 2017年12月14日
     * @author zhangli
     * @param dd
     * @return
     */
	public Map<String, Object> getLastMonthGMVCityRankingTop10(DynamicDto dd,PageInfo pageInfo);
	
	 /***
     * 
     * TODO  查询门店上个月GMV排名
     * 2017年12月14日
     * @author zhangli
     * @param dd
     * @return
     */
	public Map<String, Object> getLastMonthGMVStoreRankingTop10(DynamicDto dd,PageInfo pageInfo);
    /**
     * 
     * TODO 查询已开店的所有城市 
     * 2017年12月13日
     * @author gaobaolei
     * @return
     */
    public List<Map<String, Object>> selectAllCity();
    /**
     * 总部查看数据：按事业部交易额排名
     */
    public Map<String, Object>  queryTradeByDepName(DynamicDto dynamicDto,PageInfo pageInfo);
    /**
     * 总部查看数据：按频道交易额排名
     */
    public Map<String, Object>  queryTradeByChannelName(DynamicDto dynamicDto,PageInfo pageInfo);
    /**
     * 总部查看数据：按频道订单量排名
     */
    public Map<String, Object>  queryOrderCountByChannelName(DynamicDto dynamicDto,PageInfo pageInfo);
    
    /**
     * 查询城市个数
     */
    /**
     * 
     * TODO update
     * 2017年12月27日
     * @author gaobaolei 
     * @param dd
     * @return
     */
	public List<Map<String, Object>> findCityCount(DynamicDto dd);

	/**
     * 查询门店个数
     */
	/**
	 * 
	 * TODO update 
	 * 2017年12月27日
	 * @author gaobaolei
	 * @param dd
	 * @return
	 */
	/**
	 * 
	 * TODO update 
	 * 2017年12月27日
	 * @author gaobaolei
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> findStoreCount(DynamicDto dd);
	/**
     * 查询门店人数
     */
	public List<Map<String, Object>> findStoreKeeperCount(DynamicDto dd);
	/**
     * 查询国安侠人数
     */
	Map<String, Object> findGaxCount(DynamicDto dd,PageInfo pageInfo);
	/**
     * 查询门店在全国的排名
     */
	public List<Map<String, Object>> getLastMonthGMVStoreChinaRanking(DynamicDto dd);

	/**
	 * 查询全国门店订单数排名
	 */
	public Map<String, Object> CityOrderRankingTop10(DynamicDto dd,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 查询门店各个片区的GMV 
	 * 2017年12月19日
	 * @author gaobaolei
	 * @param storeNo
	 * @return
	 */
	public Map<String, Object> selectAreaRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo); 
	
	/**
	 * 
	 * TODO 查询门店各个事业部的GMV 
	 * 2017年12月19日
	 * @author gaobaolei
	 * @param storeNo
	 * @return
	 */
	public List<Map<String, Object>> selectDeptRankingOfStore(DynamicDto dynamicDto);
	
	/**
	 * 
	 * TODO 查询门店各个渠道的GMV 
	 * 2017年12月19日
	 * @author gaobaolei
	 * @param storeNo
	 * @return
	 */
	public Map<String, Object> selectChannelRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo);
	/**
	 * 
	 * TODO 查询当月累计营业额
	 * 2017年12月20日
	 * @author caops
	 * @param storeNo
	 * @return
	 */
	public Map<String, Object> queryTradeSumByMonth(DynamicDto dynamicDto);
	

	
	/**
	 * 
	 * TODO 查询国安侠的GMV
	 * 2017年12月20日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> selectEmployeeRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 查询门店事业部服务专员gmv 
	 * 2017年12月21日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> selectDeptServerRanking(DynamicDto dynamicDto);
	
	/**
	 * 
	 * TODO 查询片区订单量 
	 * 2017年12月21日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> selectAreaOrderRanking(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 查询频道订单量
	 * TODO  
	 * 2017年12月21日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> selectChannelOrderRanking(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 查询门店交易额
	 * 2017年12月21日
	 * @author gaobaolei
	 * @param dynamicDao
	 * @return
	 */
	public List<Map<String, Object>> selectGMVOfStore(DynamicDto dynamicDto);

	/**
	 * 
	 * TODO 查询当月国安侠Gmv
	 * 2017年12月20日
	 * @author caops
	 * @param storeNo
	 * @return
	 */
	public Map<String, Object> queryAreaTradeByEmp(DynamicDto dynamicDto,PageInfo pageInfo);
	/**
	 * 
	 * TODO 查询当月服务专员Gmv
	 * 2017年12月20日
	 * @author caops
	 * @param storeNo
	 * @return
	 */
	public List<Map<String, Object>> queryServerTradeByEmp(DynamicDto dynamicDto);
	/**
	 * 
	 * TODO 查询历史累计营业额
	 * 2017年12月20日
	 * @author caops
	 * @param storeNo
	 * @return
	 */
	public Map<String, Object> queryTradeSumOfHistory(DynamicDto dynamicDto);
	
	/**
	 * 
	 * TODO 查询门店了历史交易额 
	 * 2017年12月22日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @return
	 */
	public List<Map<String, Object>> selectHistoryGMVOfStore(DynamicDto dynamicDto);
	
	/**
	 * 
	* @Title: queryProductCityOrder 
	* @Description: 从daqWeb中ds_product_city表获取数据
	* @param @param dynamicDto
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public Map<String, Object> queryProductCityOrder(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 门店GMV （新）
	 * 2018年1月24日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> storeGmv(DynamicDto dynamicDto,PageInfo pageInfo);

	
	/**
	 * 
	 * TODO 国安侠（片区）GMV （新）
	 * 2018年1月24日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> employeeOfAreaGmv(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 门店拉新（新） 
	 * 2018年1月24日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public  Map<String, Object> storeNewaddcus(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 国安侠（片区）拉新 （新） 
	 * 2018年1月24日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public  Map<String, Object> employeeOfAreaNewaddcus(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 获取国安侠拉新（每月） 
	 * 2018年1月29日
	 * @author gaobaolei
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public  Map<String, Object> getGaxOfAreaNewaddcus(DynamicDto dynamicDto,PageInfo pageInfo);
	
	/**
	 * 通过genmini里store_id查询daqWeb中store_id
	 * @param storeId
	 * @return
	 */
	public List<Map<String, Object>> queryPlatformidByStoreId(String storeId);
	
	/**
	 * 通过genmini里store_id查询daqWeb中store_id
	 * @param storeId
	 * @return
	 */
	public List<Map<String, Object>> queryAllCityCode();
	
	
	/**
	 * 查询所有的事业群
	 * @return
	 */
	public List<Map<String, Object>> queryCityAllDept();
	
	
	/**
	 * 查询当月成交用户量
	 * @param dd
	 * @return
	 */
	List<Map<String, Object>> queryMonthCustomerCount(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);

	/**
	 * 查询本周每日用户量
	 * @param cityNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> queryCustomerCountByTime(DynamicDto dd);
	/**
	 * 查询指定时间内每日营业额
	 * @param beginData
	 * @param endData
	 * @return
	 */
	Map<String, Object> queryTurnoverByTime(String cityNo,String beginData,String endData);
	/**
	 * 查询本月累计用户量(按照城市进行分组)
	 * @param dd
	 * @param cityNO
	 * @param provinceNO
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> queryMonthCustomerCountGroup(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO, String key);

	/**
	 * 查询某年累计营业额
	 * @param dd
	 * @param cityNO
	 * @param provinceNO
	 * @param string
	 * @return
	 */
	public List<Map<String, Object>> queryYearSumGMV(DynamicDto dd,String cityId,String provinceId, String string);
	/**
	 * 查询本月每日拉新用户量
	 * @param dd
	 * @param cityNO
	 * @param provinceNO
	 * @return
	 */
	public Map<String, Object> queryNewMonthUserCount(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	/**
	 * 查询本周每日复购率,拉新用户量,消费用户量
	 * @param dd
	 * @return
	 */
	public Map<String, Object> getWeekCustomerOrderRate(DynamicDto dd);
	
	/**
	 * 查询历史成交用户量
	 * @param dd
	 * @param cityNO
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> queryHistoryCustomerCount(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);
	
	/**
	 * 
	 * TODO 查询国安侠送单量 
	 * 2018年4月3日
	 * @author gaobaolei
	 * @param dd
	 * @return
	 */
	public Map<String,Object> queryEmployeeSendorders(DynamicDto dd,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 查询商业群的gmv 
	 * 2018年4月8日
	 * @author gaobaolei
	 * @param dd
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryDeptGmv(DynamicDto dd,PageInfo pageInfo);
	
	/**
	 * 
	 * TODO 查询事业群的用户 
	 * 2018年4月8日
	 * @author gaobaolei
	 * @param dd
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryDeptConsumer(DynamicDto dd,PageInfo pageInfo);

	/**
	 * 查询所有开通门店的省
	 * @return
	 */
	public List<Map<String, Object>> queryAllOpenProvinces();

	/**
	 * 查询所有的省
	 * @return
	 */
	public List<Map<String, Object>> queryAllProvinces();
	/**
	 * 通过城市名称查询城市
	 * @return
	 */
	public List<Map<String, Object>> queryCityByName(String cityname);

	/**
	 * 
	 * TODO 查询国安侠平均消费用户数 
	 * 2018年4月24日
	 * @author gaoll
	 * @param number
	 * @return
	 */
	public Map<String, Object> queryEmployeeAvgCustomer(Integer number);
	/**
	 * 查询某月当天的用户量和上个月当天用户量和订单量
	 * <(如果当天的日大于上个月的月末时间,就按照月末那天进行计算)>
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> queryMonthAndLastMonthTodayCustomerOrderCount(
			DynamicDto dd);
	/**
	 * 
	 * 查询当日的所有门店的成交额 
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> getDailyNowStoreOrderOfCurDay(DynamicDto dd);
	/**
	 * 查询当日实时累计用户量
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> getDailyNowUserOfCurDay(DynamicDto dd);

	/**
	 * 获得最新的订单所在的城市
	 * @return
	 */
	public List<Map<String, Object>> getDailyFirstOrderCity();
	
	/**
	 * 查询近七日GMV走势图
	 * @param dd
	 * @param provinceNO 
	 * @param cityNO 
	 * @return
	 */
	public Map<String, Object> getCityGMVRangeForWeek(DynamicDto dd, List<Map<String, Object>> cityNO, 
			List<Map<String, Object>> provinceNO);

	/**
	 * 获取门店种类的个数,分城市,省
	 * @param dd
	 * @return
	 */
	public List<Map<String, Object>> getStoreKindCountByCityAndProvince(DynamicDto dd);
	/**
	 * 查询近30日GMV走势图
	 * @param dd
	 * @param provinceNO 
	 * @param cityNO 
	 * @return
	 */
	public Map<String, Object> getCityGMVRangeForMonth(DynamicDto dd,
			List<Map<String, Object>> cityNO,
			List<Map<String, Object>> provinceNO);
	/**
	 * 查询已开店的所有城市
	 * @return
	 */
	public List<Map<String, Object>> selectAllCitySort(DynamicDto dd);

	/**
	 * 分页查询门店社员
	 * 2018年5月21日
	 * @author gaoll
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getStoreMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo);
	
	/**
	 * 分页查询城市社员
	 * 2018年7月20日
	 * @author gaoll
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getCityMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo);

	/**
	 * 分页查询门店社员
	 * 2018年8月8日
	 * @author gaoll
	 * @param dynamicDto
	 * @return
	 */
	public Map<String, Object> getStoreTryMember(DynamicDto dynamicDto,String cityNo, PageInfo pageInfo);
	
	/**
	 * 根据用户id查询用户所管理的城市
	 * @param parseLong
	 * @return
	 */
	public List<Map<String, Object>> queryDistCityListByUserId(long parseLong);
	
	/**
	 * 
	* @Title: queryMemberInvitation  
	* @Description: TODO 查询社员邀请情况
	* 2018年5月28日
	* @param @param dynamicDto
	* @param @param pageInfo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public Map<String,Object> queryMemberInvitation(DynamicDto dynamicDto,PageInfo pageInfo);

	/**
	 * 以周三为节点，获得近六周时间轴
	 * 2018年6月8日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> getsixWeekDate();
	
	/**
	 * 近六个月消费用户数
	 * 2018年6月14日
	 * @author gaoll
	 * @return
	 */
	public List<Map<String,Object>> getsixMonthCustomer();
	
	/**
	 * 近六个月全国消费用户数
	 * 2018年6月14日
	 * @author  gaoll
	 * @return
	 */
	public List<Map<String,Object>> getsixMonthAllCustomer();

	/**
	 * 查询国安侠负责片区的交易各个频道占比
	 * @author gbl
	 * @param beginDate
	 * @param endDate
	 * @param areaCode
	 * @return
	 */
	public List<Map<String,Object>> selectAreaDealOfEmployeeByChannel(String beginDate,String endDate,String areaCode);

	/**
	 * 查询国安侠负责片区的交易客户消费次数
	 * @author gbl
	 * @param beginDate
	 * @param endDate
	 * @param areaCode
	 * @return
	 */
	public List<Map<String,Object>> selectAreaDealOfEmployeeByConsum(String beginDate,String endDate,String areaCode);

	/**
	 * 查询国安侠的GMV
	 * @author gbl
	 * @param year
	 * @param month
	 * @param employeeNo
	 * @return
	 */
	public List<Map<String,Object>> selectGMVOfEmployee(Integer year,Integer month,String employeeNo);



	/**
	 * @Description 查询国安侠用户
	 * @author gbl
	 * @date 2018/7/3 10:16
	 **/

	public List<Map<String,Object>> selectCustomerOfEmployee(Integer year,Integer month,String employeeNo);
	/**
	 * 
	* @Title: selectOrderOfEmployee  
	* @Description: TODO 查询国安侠订单
	* 2018年7月9日
	* @param @param year
	* @param @param month
	* @param @param employeeNo
	* @param @return      
	* @return List<Map<String,Object>> 
	* @throws
	* @author gbl
	 */
	public List<Map<String,Object>> selectOrderOfEmployee(Integer year,Integer month,String employeeNo);

	/**
	 * @Description  查询门店近七日的社员新增
	 * @author gbl
	 * @date 2018/7/11 14:34
	 **/

	public List<Map<String,Object>> queryLastSevenDayCommunityMembersOfStore(String storeId);
	
	/**
	 * 查询近七日221GMV走势图
	 * @author zhangli
	 * 2018年7月11日
	 * @param dd
	 * @param provinceNO 
	 * @param cityNO 
	 * @return
	 */
	public Map<String, Object> getTwoTwoOneGMVRangeForWeek(DynamicDto dd,List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO);

	/**
	 * @Description 门店221gmv
	 * @author gbl
	 * @date 2018/7/28 15:50
	 **/

	public Map<String, Object> getStoreGmv_221(DynamicDto dynamicDto,PageInfo pageInfo);

	/**
	 * @Description
	 * @author gbl
	 * @date 2018/7/28 16:08
	 **/

	public Map<String,Object> getEmployeeGmv_221(DynamicDto dynamicDto,PageInfo pageInfo);
	/**
	 * 大客户社员统计
	 * @author gaoll
	 * 2018年7月11日
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
    Map<String,Object> getCustomerMember(DynamicDto dynamicDto, PageInfo pageInfo);


	/**
	 *
	 * TODO 查询门店E店gmv
	 * 2017年12月20日
	 * @author gaobaolei
	 * @param platformId
	 * @return
	 */
	public Map<String, Object> selectEStoreRankingOfStore(DynamicDto dynamicDto,PageInfo pageInfo);


	/**
	 *
	 * TODO 查询门店E店gmv
	 * 2018年9月25日
	 * @author gaoll
	 * @param dynamicDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> getUserBehaviorByLog(DynamicDto dynamicDto,String cityNo,PageInfo pageInfo);


    /**
     *
     * TODO 国安侠（片区）GMV （新）
     * 2018年1月24日
     * @author gaobaolei
     * @param dynamicDto
     * @param pageInfo
     * @return
     */
    public Map<String, Object> employeeOfMaoli(DynamicDto dynamicDto,PageInfo pageInfo);

    /**
	 * 查询daqWeb库门店用户量-原来在gemini
	 * @param dd
	 * @param pageInfo
	 * @return
	 */
	Map<String, Object> queryStoreCustmerCount(DynamicDto dd, List<Map<String, Object>> cityNO,List<Map<String, Object>> provinceNO,PageInfo pageInfo);


    /**
     * @Description 查询事业群GMV
     * @author gbl
     * @date 2018/11/30 14:42
     **/

    public Map<String,Object> queryDeptGMVByImpala(DynamicDto dynamicDto,PageInfo pageInfo);

    /**
     * @Description 查询事业群用户
     * @author gbl
     * @date 2018/12/1 16:13
     **/

	public Map<String,Object> queryDeptConsumerByImpala(DynamicDto dynamicDto,PageInfo pageInfo);

}
