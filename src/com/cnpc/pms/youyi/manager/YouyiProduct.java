package com.cnpc.pms.youyi.manager;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.MemberDataDto;

import java.util.Map;


/**
 * @author wuxinxin
 * 社员相关操作
 * 2018-05-17
 */
public interface YouyiProduct extends IManager{

	/**
	 * //查询总的商品数  在线商品数  动销商品数   新上商品数
	 * @return
	 */
	public Map<String, Object> selectYouyiSku(String dd);
	/**
	 * 查询动销数、在售商品数/每月
	 * @return
	 */
	public Map<String, Object> selectMovingByMonth(String dd);
	/**
	 * 查询城市动销率
	 * @return
	 */
	public Map<String, Object> selectCityMoving(String dd);
	/**
	 * 查询频道动销数、在售商品数/每月
	 */
	public Map<String, Object> selectChannelMoving(String cityCode);
	/**
	 * 查询频道商品销量
	 */
	public Map<String, Object> selectChannelSell(String cityCode);
	
	/**
	 * 查询商品销量top10
	 * @author wuxinxin
	 */
	public Map<String, Object> selectSkuTop(String dd);

    /**
     * 查询商品销量top10
     * @author wuxinxin
     */
    public Map<String, Object> selectYouyiGmv(String dd);

}