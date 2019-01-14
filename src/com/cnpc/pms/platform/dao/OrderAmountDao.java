package com.cnpc.pms.platform.dao;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.OrderAmountDto;
import com.cnpc.pms.reportFiledown.entity.TReportFiledown;

import java.util.List;
import java.util.Map;

/**
 */
public interface OrderAmountDao {
		/**
		 * 导出订单金额结算信息
		 * 
		 * @param object
		 * @return
		 */
	 	List<Map<String, Object>> exportOrder(OrderAmountDto orderAmountDto, String tableName);

		/**
		 * 查询订单金额结算信息
		 * 
		 * @param object
		 * @return
		 */
		Map<String, Object> queryOrderAmount(OrderAmountDto orderAmountDto, PageInfo pageInfo,String tableName);
		
		void updataReport(Long id , String url);
}
