package com.cnpc.pms.personal.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.personal.entity.Customer;
import com.cnpc.pms.personal.entity.CustomerData;
import com.cnpc.pms.personal.entity.CustomerHealth;
import com.cnpc.pms.personal.entity.CustomerOperateRecord;
import com.cnpc.pms.personal.manager.CustomerDataManager;
import com.cnpc.pms.personal.manager.CustomerDataTemporaryManager;
import com.cnpc.pms.personal.manager.CustomerHealthManager;
import com.cnpc.pms.personal.manager.CustomerManager;
import com.cnpc.pms.personal.manager.CustomerOperateRecordManager;

public class CustomerHealthManagerImpl extends BizBaseCommonManager implements CustomerHealthManager {

	@Override
	public CustomerHealth saveCustomerHealth(CustomerHealth customerHealth) {
	
		return null;
	}

	@Override
	public  CustomerHealth getCustomerHealthByCustomerId(Long customerId) {
		List<?> list = getList(FilterFactory.getSimpleFilter("customer_id",customerId));
		if(list!=null&&list.size()>0){
			return (CustomerHealth)list.get(0);
		}
		return null;
	}

	
}
