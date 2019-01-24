package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.dynamic.entity.MassOrderDto;
import com.cnpc.pms.platform.entity.OrderAmountDto;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import java.util.Map;

/**
 * @Function：订单结算
 * @author：liugongting
 *
 * @version V1.0
 */
public interface OrderAmountManager extends IManager {
	/**
	 * 导出订单结算信息
	 * 
	 * @param object
	 * @return
	 */
	Map<String, Object> exportOrder(OrderAmountDto orderAmountDto, TReportFiledown tReportFiledown);

	/**
	 * 查询订单结算信息
	 * 
	 * @param object
	 * @return
	 */
	Map<String, Object> queryOrderAmount(OrderAmountDto orderAmountDto, PageInfo pageInfo);
	Map<String, Object> queryContractById(String contract_id);
}
