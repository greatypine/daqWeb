package com.cnpc.pms.personal.manager.impl;

import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;
import com.cnpc.pms.personal.entity.StoreDynamic;
import com.cnpc.pms.personal.entity.StoreHistory;
import com.cnpc.pms.personal.manager.StoreHistoryManager;

import java.util.Date;

public class StoreHistoryManagerImpl extends BizBaseCommonManager implements StoreHistoryManager {

	@Override
	public void insertStoreHistory(StoreDynamic storeDynamic) {
		//获取修改的用户
		User sessionUser = null;
		if (null != SessionManager.getUserSession() && null != SessionManager.getUserSession().getSessionData()) {
			sessionUser = (User) SessionManager.getUserSession().getSessionData().get("user");
		}
		StoreHistoryManager storeHistoryManager = (StoreHistoryManager) SpringHelper.getBean("storeHistoryManager");
		StoreHistory storeHistory = new StoreHistory();
		storeHistory.setSuperMicro(storeDynamic.getSuperMicro());
		storeHistory.setEstate(storeDynamic.getEstate());
		storeHistory.setStoretype(storeDynamic.getStoretype());
		storeHistory.setStoretypename(storeDynamic.getStoretypename());
		storeHistory.setOpen_shop_time(storeDynamic.getOpen_shop_time());
		storeHistory.setAddress(storeDynamic.getAddress());
		storeHistory.setMobilephone(storeDynamic.getMobilephone());
		storeHistory.setName(storeDynamic.getName());
		storeHistory.setTown_id(storeDynamic.getTown_id());
		storeHistory.setTown_name(storeDynamic.getTown_name());
		storeHistory.setVillage_id(storeDynamic.getVillage_id());
		storeHistory.setTinyvillage_Id(storeDynamic.getTinyvillage_Id());
		storeHistory.setType(1);
		storeHistory.setCounty_ids(storeDynamic.getCounty_ids());
		storeHistory.setNature(storeDynamic.getNature());
		storeHistory.setTenancyTerm(storeDynamic.getTenancyTerm());
		storeHistory.setRental(storeDynamic.getRental());
		storeHistory.setPayment_method(storeDynamic.getPayment_method());
		storeHistory.setRent_area(storeDynamic.getRent_area());
		storeHistory.setUsable_area(storeDynamic.getUsable_area());
		storeHistory.setIncrease(storeDynamic.getIncrease());
		storeHistory.setRent_free(storeDynamic.getRent_free());
		storeHistory.setTaxes(storeDynamic.getTaxes());
		storeHistory.setAgency_fee(storeDynamic.getAgency_fee());
		storeHistory.setIncrease_fee(storeDynamic.getIncrease_fee());
		storeHistory.setStore_position(storeDynamic.getStore_position());
		storeHistory.setPlace_town_id(storeDynamic.getPlace_town_id());
		storeHistory.setGaode_adCode(storeDynamic.getGaode_adCode());
		storeHistory.setGaode_address(storeDynamic.getGaode_address());
		storeHistory.setGaode_cityCode(storeDynamic.getGaode_cityCode());
		storeHistory.setPlatformid(storeDynamic.getPlatformid());
		storeHistory.setNumber(storeDynamic.getNumber());
		storeHistory.setWhite(storeDynamic.getWhite());
		storeHistory.setPlatformname(storeDynamic.getPlatformname());
		storeHistory.setGaode_provinceCode(storeDynamic.getGaode_provinceCode());
		storeHistory.setRemark(storeDynamic.getRemark());
		storeHistory.setSkid(storeDynamic.getSkid());
		storeHistory.setCounty_id(storeDynamic.getCounty_id());
		storeHistory.setCity_id(storeDynamic.getCity_id());
		storeHistory.setProvince_id(storeDynamic.getProvince_id());
		storeHistory.setCityName(storeDynamic.getCityName());
		storeHistory.setUpdate_user(sessionUser.getName());
		storeHistory.setUpdate_user_id(sessionUser.getId());
		storeHistory.setUpdate_time(new Date());
		storeHistory.setCityNo(storeDynamic.getCityNo());
		storeHistory.setStoreno(storeDynamic.getStoreno());
		storeHistory.setCreate_user(storeDynamic.getCreate_user());
		storeHistory.setCreate_time(storeDynamic.getCreate_time());
		storeHistory.setOrdnumber(storeDynamic.getOrdnumber());
		storeHistory.setFormattype(storeDynamic.getFormattype());
		storeHistory.setStore_id(storeDynamic.getStore_id());
		storeHistoryManager.saveObject(storeHistory);
	}

}
