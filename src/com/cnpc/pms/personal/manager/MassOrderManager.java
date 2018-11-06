package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import java.util.Map;

/**
 * @Function：订单数据
 * @author：chenchuang
 * @date:2018年1月9日 下午2:35:41
 *
 * @version V1.0
 */
public interface MassOrderManager extends IManager {
	
	/**
	 * 通过城市code查询城市No
	 * @return
	 */
	public Map<String, Object> queryCitynoByCode(String cityCode);

	/**
	 * 查询订单信息
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryMassOrder(MassOrderDto massOrderDto,PageInfo pageInfo);
	
	/**
	 * 导出订单信息
	 * @param massOrderDto
	 * @return
	 */
	public Map<String, Object> exportOrder(MassOrderDto massOrderDto, TReportFiledown tReportFiledown);
	
	/**
	 * 查询退货订单信息
	 * @param massOrderDto
	 * @param pageInfo
	 * @return
	 */
	public Map<String, Object> queryReturnMassOrder(MassOrderDto massOrderDto,PageInfo pageInfo);
	
	/**
	 * 导出退货订单信息
	 * @param massOrderDto
	 * @return
	 */
	public Map<String, Object> exportReturnOrder(MassOrderDto massOrderDto);
	
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
	public Map<String, Object> queryAreaDetailByCode(String area_code, String order_sn,String beginDate);
	
	/**
	 * 根据订单号查询国安侠信息
	 * @param order_sn
	 * @return
	 */
	public Map<String, Object> queryEmployeeBySN(String order_sn,String beginDate);
	
	/**
	 * 根据ID查询E店合同信息
	 * @param contract_id
	 * @return
	 */
	public Map<String, Object> queryContractById(String contract_id);

	/**
	 * 根据订单sn查询订单明细
	 * @aram order_sn
	 * @return
	 */
	public Map<String, Object> queryOrderDetailBySN(String order_sn);

	/**
	 * @Description 根据订单号查询两个月内的订单信息
	 * @author gbl
	 * @date 2018/9/4 11:05
	 **/
	public Map<String, Object> queryOrderInfoByOrderSN(String order_sn);

	/**
	 * @Description 查询近两月的国安侠送单
	 * @author gbl
	 * @date 2018/9/4 13:56
	 **/

	public Map<String, Object> queryOrderListByEmployeeNo(String employee_no,PageInfo pageInfo);
}
