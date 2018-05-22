package com.cnpc.pms.platform.manager;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.cnpc.pms.base.manager.IManager;

import ar.com.fdvs.dj.domain.constants.Page;


/**
 * @author wuxinxin
 * 社员相关操作
 * 2018-05-17
 */
public interface CommuneMember extends IManager{

	/**
	 * 查询办理社员得所有用户数
	 * @return
	 */
	public Map<String, Object> selectAllCm(String dd);
	/**
	 * 查询新办理社员用户数
	 * @return
	 */
	public Map<String, Object> selectNewCm(String dd);
	/**
	 * 查询老用户办理社员用户数
	 * @return
	 */
	public Map<String, Object> selectOldCm(String dd);
	
	/**
	 *
	 * TODO 获取E店数量
	 * @author wuxinxin
	 * */
   public Map<String, Object> getEshopCount(String dd);
   /**
    * TODO 获取商品种类SKU数量
    * @author wuxinxin
    */
   public Map<String, Object> getGoodsTypeCount(String dd);
   /**
    * TODO 社员商品成交量
    * @author wuxinxin
    */
   public Map<String, Object> getCmGoodsDealCount(String dd);
   /**
    * TODO 社员商品成交额
    * @author wuxinxin
    */
   public Map<String, Object> getCmGoodsTurnover(String dd);
    
    /**
     * TODO 获取男女比例
     * @author wuxinxin
     */
    public Map<String,Object> getCmSexRatios(String dd);
    /**
     * TODO 获取年龄段情况
     * @author wuxinxin
     */
    public Map<String,Object> getCmAgeRatios(String dd);
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
}