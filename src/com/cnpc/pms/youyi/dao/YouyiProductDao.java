
package com.cnpc.pms.youyi.dao;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.platform.entity.MemberDataDto;

import java.util.List;
import java.util.Map;

/**
 * 社员操作dao
 * 
 * @author wuxinxin 2018年9月10日
 */
public interface YouyiProductDao extends IDAO {

	/**
	 * TODO 查询国安优易SKU数
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getYouyiSku(String dd);

    /**
     * TODO 查询在线SKU数
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getonlineSku(String dd);

	/**
	 * TODO 按月查询动销数
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getMovingByMonth(String dd);

	/**
	 * TODO 查询城市动销数
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getCityMoving(String dd);

	/**
	 * TODO 查询总动销数
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getMovingAll(String dd);

	/**
	 * TODO 查询频道动销率
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getChannelMoving(String dd);

	/**
	 * TODO 查询频道销量
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getChannelSell(String dd);

	/**
	 * TODO 查询SKU前十
	 * 
	 * @author wuxinxin
	 */
	public List<Map<String, Object>> getSkuTop(String dd);

    /**
     * TODO 查询GMV
     *
     * @author wuxinxin
     */
    public List<Map<String, Object>> getYouyiGmv(String dd);

}
