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
	 * 查询社员属性信息
	 * @return
	 */
	public Map<String, Object> selectAllCm(String dd);
	/**
	 * 查询社员属性信息
	 * @return
	 */
	public Map<String, Object> selectMeSum(String dd);
	
}