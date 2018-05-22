package com.cnpc.pms.platform.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.personal.dto.StoreDTO;
import com.cnpc.pms.platform.entity.PlatformStore;

public interface PlatformStoreDao {
	List<PlatformStore> getPlatformStoreList(String where, PageInfo pageInfo)  ;
	Map<String, Object> getPlatformStoreInfoList(String where, PageInfo pageInfo);
	//获取数据的总条数
	int getPlatformStoreCount(String where);
	/**
	 * 
	 * TODO 通过门店编号查询门店国安侠
	 * 2017年12月14日
	 * @author gaoll
	 * @param storeNo
	 * @return
	 */
	List<Map<String,Object>> getEmployeeByStore(String storeNo,Boolean isRealtime);
	
	/**
	 * 
	 * TODO 通过门店编号查询门店国安侠
	 * 2017年3月22日
	 * @author gaoll
	 * @param city_code 
	 */
	List<Map<String,Object>> getEmployeeByCity(String city_code,Boolean isRealtime);
	
	/**
	 * 
	 * TODO 通过员工编号查询国安侠
	 * 2018年1月3日
	 * @author gaoll
	 * @param storeNo
	 * @return
	 */
	List<Map<String,Object>> getEmployeeByEmployeeNo(String employeeNo,Boolean isRealtime);
	
	/**
	 * 
	 * TODO 通过员工编号查询国安侠是否在线
	 * 2017年1月3日
	 * @author gaoll
	 * @param storeNo
	 * @return
	 */
	Map<String,Object> getEmployeeStatus(String employeeNo);
	
	
//------------------------wuxinxin 2018-05-18   start----------------------//	
	/**
	 * 查询办理社员得所有用户数
	 * @return
	 */
	public List<Map<String, Object>> selectAllCm(String dd);
	/**
	 * 查询新办理社员用户数
	 * @return
	 */
	public List<Map<String, Object>> selectNewCm(String dd);
	/**
	 * 查询老用户办理社员用户数
	 * @return
	 */
	public List<Map<String, Object>> selectOldCm(String dd);
	
	/**
	 *
	 * TODO 获取E店数量
	 * @author wuxinxin
	 * */
   public List<Map<String, Object>> getEshopCount(String dd);
   /**
    * TODO 获取商品种类SKU数量
    * @author wuxinxin
    */
   public List<Map<String, Object>> getGoodsTypeCount(String dd);
   /**
    * TODO 社员商品成交量
    * @author wuxinxin
    */
   public List<Map<String, Object>> getCmGoodsDealCount(String dd);
   /**
    * TODO 社员商品成交额
    * @author wuxinxin
    */
   public List<Map<String, Object>> getCmGoodsTurnover(String dd);
   
   /**
    * TODO 获取男女比例
    * @author wuxinxin
    */
   public List<Map<String, Object>> getCmSexRatios(String dd);
   /**
    * TODO 获取年龄段情况
    * @author wuxinxin
    */
   public List<Map<String, Object>> getCmAgeRatios(String dd);
   /**
    * TODO 获取生日礼信息
    * @author wuxinxin
    */
   public Map<String,Object> getCmBirthday(String dd);
   /**
    * TODO 获取社员增长情况
    * @author wuxinxin
    */
   public Map<String,Object> getCmGrow(String dd);
   //-------------------wuxinxin         end------------------------//
	
	
}
