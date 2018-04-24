package com.cnpc.pms.workflow.dao;

import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.workflow.dto.ToDoByModule;
import com.cnpc.pms.workflow.entity.FinishedByModule;

/**
 * 通用的流程组件DAO
 * 
 * @author jrn
 * 
 */
public interface WFInstanceDAO extends IDAO {
	/**
	 * 判定是否满足条件
	 * 
	 * @param condition
	 * @return
	 */
	public boolean isMeetCondition(String condition);

	/**
	 * 根据用户ID和业务模块ID获取用户待办列表
	 * 
	 * @param personId
	 * @param moduleId
	 * @return
	 */
	public List getToDoList(Long personId, Long moduleId);

	/**
	 * 根据用户ID获取用户待办列表
	 * 
	 * @param personId
	 * @return
	 */
	public List getAllToDoList(Long personId);

	/**
	 * 根据实例id逻辑删除其下的组件
	 * 
	 * @param instanceid
	 */
	public void deleteInstanceById(Long instanceid);

	/**
	 * 根据模块类型统计待办信息
	 * 
	 * @param userId
	 * @param pageInfo
	 * @return
	 */
	public List<ToDoByModule> getToDoByModule(Long userId, PageInfo pageInfo);

	/**
	 * 根据模块类型统计已办信息
	 * 
	 * @param userId
	 * @param pageInfo
	 * @return
	 */
	public List<ToDoByModule> getDoneByModule(Long userId, PageInfo pageInfo);
	/**
	 * 根据用户id查出前10条待办数据
	 * @param userId
	 * @return
	 */
	public List<com.cnpc.pms.workflow.entity.ToDoByModule> getToDoByModuleByUserId(Long userId);
	/**
	 * 根据用户id查出前10条已办数据
	 * @param userId
	 * @return
	 */
	public List<com.cnpc.pms.workflow.entity.DoneByModule> getDoneByModuleByUserId(Long userId);

	/**
	 * 根据用户id查出前10条已办数据
	 * @param userId
	 * @return
	 */
	public List<FinishedByModule> getFinishByModuleByUserId(Long userId);
}
