package com.cnpc.pms.worklog.dao;

import java.util.Date;
import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.worklog.dto.WorkLogQueryUserDTO;


/**
 * 日志查询使用的DAO对象（不针对我的工作日志）
 * 
 * @author liujunsong
 * 
 */
public interface WorkLogQueryDAO extends IDAO {
	/**
	 * 检索关注人员
	 * @param pageInfo
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @param userName
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByFollow(
			PageInfo pageInfo, Long userId, String beginDate, String endDate,
			String userName);
	
	/**
	 * 按所来检索人员
	 * @param pageInfo
	 * @param beginDate
	 * @param endDate
	 * @param orgId
	 * @param userId
	 * @param userName
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByOrgId(
			PageInfo pageInfo, String beginDate, String endDate, String orgId,
			Long userId, String userName);
	/**
	 * 按项目相关检索人员
	 * @param pageInfo
	 * @param beginDate
	 * @param endDate
	 * @param userId
	 * @param userName
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByProject(
			PageInfo pageInfo, String beginDate, String endDate, Long userId,
			String userName);
	
	/**
	 * 按室和项目相关来检索人员
	 * @param pageInfo
	 * @param beginDate
	 * @param endDate
	 * @param orgId
	 * @param userId
	 * @param userName
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByOrgIdAndProject(
			PageInfo pageInfo, String beginDate, String endDate, Long orgId,
			Long userId, String userName);
	
	//--------------- 下面是用于查看具体人员日志的通用处理方法 -------------------//
	
	/**
	 * 按照当前操作用户,要查看用户,查看类型,查看日期来判断是否提交日志
	 * 也可能用户已经提交日志,但由于查看权限的控制,当前用户仍然不可见
	 * @param currUserId 当前用户
	 * @param userId 要查看用户
	 * @param operType 查看类型0查询入口1统计入口
	 * @param logDate	要查看的日志日期
	 * @return 1L 有日志，可以查看
	 *         0L 无日志，或者无权查看
	 */
	public Long isLogged(Long currUserId,Long userId,Long operType,Date logDate);
	

}
