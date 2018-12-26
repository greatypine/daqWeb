package com.cnpc.pms.personal.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.personal.entity.CustomerData;
import com.cnpc.pms.personal.entity.CustomerHealth;

public interface CustomerHealthManager extends IManager{
	
	CustomerHealth saveCustomerHealth(CustomerHealth customerHealth);
	
	CustomerHealth getCustomerHealthByCustomerId(Long customerId);

}
