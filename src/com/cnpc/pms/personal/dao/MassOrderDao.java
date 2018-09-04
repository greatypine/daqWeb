package com.cnpc.pms.personal.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.MassOrderDto;

import java.util.List;
import java.util.Map;

/**
 * @Function：清洗出的订单Dao
 * @author：chenchuang
 * @date:2018年1月9日 下午3:31:04
 *
 * @version V1.0
 */
public interface MassOrderDao extends IDAO{
	
	/**
	 * 通过Citycode查询Cityno
	 * @param cityCode
	 * @return
	 */
	 public Map<String, Object> queryCitynoByCode(String cityCode);

	/**
	 * 查询订单数据列表
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryMassOrder(MassOrderDto massOrderDto,PageInfo pageInfo,String timeFlag);
	
	/**
	 * 导出订单数据列表
	 * @param massOrderDto
	 * @return
	 */
	public List<Map<String, Object>> exportOrder(MassOrderDto massOrderDto,String timeFlag);
	
	/**
	 * 查询退货订单数据列表
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryReturnMassOrder(MassOrderDto massOrderDto,PageInfo pageInfo,String timeFlag);
	
	/**
	 * 导出退货订单数据列表
	 * @param massOrderDto
	 * @param timeFlag
	 * @return
	 */
	public List<Map<String, Object>> exportReturnOrder(MassOrderDto massOrderDto,String timeFlag);
	
	/**
	 * 根据订单号查询订单
	 * @param order_sn
	 * @return
	 */
	public Map<String, Object> queryOrderInfoBySN(String order_sn);
	
	/**
	 * 根据片区编号查询信息
	 * @param area_code
	 * @return
	 */
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn, String timeFlag);
	
	/**
	 * 根据订单号查国安侠信息
	 * @param order_sn
	 * @return
	 */
	public Map<String, Object> queryEmployeeBySN(String order_sn, String timeFlag);
	
	/**
	 * 根据门店编号查Platformid
	 * @param storeno
	 * @return
	 */
	public Map<String, Object> queryPlatformidByCode(String storeno);

	/**

	 * 根据订单sn查询订单明细
	 * @param order_sn
	 * @return
	 */
	public Map<String, Object> queryOrderDetailBySN(String order_sn);

	/* * @Description app 查询门店国安侠的送单量
	 * @author gbl
	 * @date 2018/8/31 15:39
	 **/

	public List<Map<String, Object>> queryEmployeeOrderCountByStore(String storeId);

	/**
	 * @Description  根据订单号查询两个月内的订单信息
	 * @author gbl
	 * @date 2018/9/4 10:57
	 **/
	public Map<String, Object> queryOrderInfoByOrderSN(String order_sn);

	/**
	 * @Description 查询国安侠近两个月的送单
	 * @author gbl
	 * @date 2018/9/4 13:46
	 **/

	public Map<String,Object> queryOrderListOfEmployee(String employeeNo,PageInfo pageInfo);
	
}
