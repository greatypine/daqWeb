package com.cnpc.pms.worklog.dao;

import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;


/**
 * 日志统计使用的DAO对象
 * 
 * @author liujunsong
 * 
 */
public interface WorkLogStatDAO extends IDAO {
	/**
	 * 按照分院统计
	 * @param beginDate
	 * @param endDate
	 * @param jobType
	 * @return
	 */
	public List getUserStatByBranch(String beginDate, String endDate,
			Long jobType);
	/**
	 * 按照所统计
	 * @param beginDate
	 * @param endDate
	 * @param jobType
	 * @param branchId
	 * @return
	 */
	public List getUserStatByOrg(String beginDate, String endDate,
			Long jobType, Long branchId);
	/**
	 * 按照人员进行统计(针对一个所的人员)
	 * @param pageInfo
	 * @param beginDate
	 * @param endDate
	 * @param jobType
	 * @param orgId
	 * @return
	 */
	public List getUserStatByPerson(PageInfo pageInfo,String beginDate, String endDate,
			Long jobType, Long orgId);
	
	/**
	 * 按照人员进行统计(针对一个室的人员)<br>
	 * 由室主任来使用
	 * @param pageInfo
	 * @param beginDate
	 * @param endDate
	 * @param jobType
	 * @param orgId
	 * @return
	 */
	public List getUserStatByPerson2(PageInfo pageInfo,String beginDate, String endDate,
			Long jobType, Long orgId);
	
	public List<PurStruOrg> getBranchList() ;
	
	public List<PurStruOrg> getDeptList(Long branchId) ;
	
	public List<User> getUserList(Long suoOrShiId,PageInfo pageInfo);
	public List<User> getUserListByJobType(Long suoOrShiId,Long jobType,PageInfo pageInfo);
}
