package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;

import com.cnpc.pms.worklog.dao.WorkLogQueryDAO;

import com.cnpc.pms.worklog.dto.WorkLogQueryUserDTO;
import com.cnpc.pms.worklog.entity.WorkLogUserCalendar;
import com.cnpc.pms.worklog.entity.WorkLogViewBizbaseUser;
import com.cnpc.pms.worklog.exception.WorkLogException;

/**
 * 工作日志查询所使用的DAO方法的实现（不针对我的工作日志）
 * 
 * @author liujunsong
 * 
 */
public class WorkLogQueryDAOImpl extends BaseDAOHibernate implements
		WorkLogQueryDAO {
	private String POS_SZ = "SZ"; // 所长的岗位编码
	private String POS_FSZ = "FSZ";// 副所长的岗位编码
	private String POS_SBZR = "SBZR"; // 所办主任
	private String POS_KYMS = "KYMS"; // 科研秘书
	private String POS_KYSZ = "KYSZ"; // 科研秘书

	private String POS_SZR = "PTRY";// 室主任
	private String POS_SFZR = "SFZR";// 室主任

	/**
	 * --step1:创建一个用户视图 --这一步如果不做的话，整个程序就显得太过复杂了。 create or replace view
	 * view_worlog_bizbase_user as select u.id,u.name,u.zw
	 * ,u.zc,u.orderno,v.id1,v.name1,v.id2,v.name2,v.id3,v.name3,
	 * v.id4,v.name4,p.id as posid,p.name as posname from tb_bizbase_user
	 * u,tb_bizbase_cnpcposition p, (select id,name,entityorgflag,id as id1,name
	 * as name1,0 as id2,'' as name2,0 as id3,'' as name3,0 as id4,'' as name4
	 * from tb_bizbase_psorg t where entityorgflag='0' union select
	 * a.id,a.name,a.entityorgflag,b.id as id1,b.name,a.id as id2,a.name,0 as
	 * id3,'',0 as id4,'' from tb_bizbase_psorg a, tb_bizbase_psorg b where
	 * a.entityorgflag='1' and a.parent_id = b.id union select
	 * a.id,a.name,a.entityorgflag,c.id as id1,c.name,b.id as id2,b.name,a.id as
	 * id3,a.name,0 as id4,'' from tb_bizbase_psorg a, tb_bizbase_psorg
	 * b,tb_bizbase_psorg c where a.entityorgflag='2' and a.parent_id = b.id and
	 * b.parent_id=c.id union select a.id,a.name,a.entityorgflag,d.id as
	 * id1,d.name,c.id as id2,c.name,b.id as id3,b.name,a.id as id4,a.name from
	 * tb_bizbase_psorg a, tb_bizbase_psorg b,tb_bizbase_psorg
	 * c,tb_bizbase_psorg d where a.entityorgflag='3' and a.parent_id = b.id and
	 * b.parent_id=c.id and c.parent_id=d.id) v where u.pk_org =v.id and
	 * u.pk_position = p.id;
	 */
	// 先构建一个用户视图,这个视图包括如下字段:
	// id,编号
	// name,名称
	// zw,职务
	// zc,职称
	// orderno,排序号
	// id3,所编号
	// name,所名称

	String view_user = "VIEW_WORKLOG_BIZBASE_USER"; // 用户的所需信息的视图

	/**
	 * 用户获取所有关注用户DTO的列表信息<br>
	 * 这一功能是机关用户，所领导，室主任，项目经理公用的。<br>
	 * 选择用户的标准是是否在关注表中，与时间范围，机构范围均无关<br>
	 * 需要关联三张表：用户日历表，用户表，用户关注表来取数据。<br>
	 * 表与表之间的关联均为强关联。<br>
	 * 
	 * @param userId
	 *            当前用户Id
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @parem userName 用于检索用户的模糊查询条件
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByFollow(
			PageInfo pageInfo, Long userId, String beginDate, String endDate,
			String userName) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性
		if (userId == null) {
			return new ArrayList<WorkLogQueryUserDTO>();
		}
		// TODO:验证开始日期,结束日期的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,u.zc ZC,u.zw ZW,u.name3 NAME3,max(log.recordstate) RECORDSTATE,count(follow.id) ISFOLLOW  "
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join TB_WORKLOG_USER_CALENDAR log on log.personid = u.id "
						+ " left join TB_workLOG_FOLLOW follow on  follow.followid = u.id "
						+ " where to_char(calendardate,'yyyy-mm-dd')= :beginDate "
						+ " and follow.userid=:userid ");// 不限定机构条件
		// 判断是否限定了人员姓名
		if (userName != null && userName.length() > 0) {
			strSql.append(" and u.name like :userName ");
		}
		// group by 方式
		strSql.append(" group by u.id,u.name,u.zc,u.zw,u.name3,u.orderno");
		// 人员排序方式
		strSql.append(" order by u.orderno");

		// 构建获取总数的strSql2
		String strSql2 = "Select count(*) TOTAL from (" + strSql + ")";
		_log(strSql.toString());
		_log(strSql2.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		SQLQuery query2 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql2.toString());

		query.setString("beginDate", beginDate); // 开始日期
		// query.setString("endDate", endDate); // 结束日期
		query.setLong("userid", userId); // 当前用户

		query2.setString("beginDate", beginDate); // 开始日期
		// query2.setString("endDate", endDate); // 结束日期
		query2.setLong("userid", userId); // 当前用户

		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query.setString("userName", "%" + userName + "%");
		}
		// 针对Query2设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query2.setString("userName", "%" + userName + "%");
		}

		// 初始化pageInfo对象
		// this._initPageInfoByQuery(query2, pageInfo);

		// step3:检索得到一个计算结果
		List<Map> list2 = (List<Map>) query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list2.size());
		List<Map> list1 = (List<Map>) query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogQueryUserDTO> retList = new ArrayList<WorkLogQueryUserDTO>();
		for (Map map1 : list1) {
			WorkLogQueryUserDTO dto = this._convertMapToDTO(map1);
			retList.add(dto);
		}
		return retList;
	}

	// 不同用户采用不同的检索条件来进行检索
	// 科研管理处：选择机构 byOrgId()
	// 所领导：指定本所byOrgId()，或者选择项目相关 ByProject()
	// 室领导：本室+项目相关
	// 项目经理：项目相关byProject();

	/**
	 * 指定单一机构选择来查询该机构所有用户，<br>
	 * 也可能会同时输入用户名称来做模糊匹配<br>
	 * 适用于：科研管理处，所领导。<br>
	 * 机构的选择或者默认由前面的调用程序来实现<br>
	 * 
	 * @param pageInfo
	 *            分页对象
	 * @param beginDate
	 *            开始日期,格式yyyymmdd
	 * @param endDate
	 *            结束日期,格式yyyymmdd
	 * @param orgId
	 *            选定的机构Id,代表所Id
	 * @param userId
	 *            当前用户的Id,用来判断是否关注
	 * @param userName
	 *            设定的人员姓名过滤器,采用模糊查询
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByOrgId(
			PageInfo pageInfo, String beginDate, String endDate, String orgId,
			Long userId, String userName) {
		StringBuffer strSql = new StringBuffer();
		System.out.println(orgId);
		// step1:判断数据的有效性
		if (orgId == null) {
			return new ArrayList<WorkLogQueryUserDTO>();
		}
		// TODO:验证开始日期,结束日期的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,u.zc ZC,u.zw ZW,u.name3 NAME3,max(log.recordstate) RECORDSTATE,count(follow.id) ISFOLLOW  "
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join TB_WORKLOG_USER_CALENDAR log on log.personid = u.id "
						+ " left join TB_workLOG_FOLLOW follow on follow.userid=:userid and follow.followid = u.id "
						+ " where to_char(calendardate,'yyyy-mm-dd')= :beginDate "
						+ " and u.id3 in (" + orgId + ")");
		// 判断是否限定了人员姓名
		if (userName != null && userName.length() > 0) {
			strSql.append(" and u.name like :userName ");
		}
		// group by 方式
		strSql.append(" group by u.id,u.name,u.zc,u.zw,u.name3,u.orderno");
		// 人员排序方式
		strSql.append(" order by u.orderno");

		// 构建获取总数的strSql2
		String strSql2 = "Select count(*) T1 from (" + strSql + ")";

		_log(strSql.toString());
		_log(strSql2.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		SQLQuery query2 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql2.toString());
		System.out.println(orgId);
		// query.setString("orgId", orgId); // 所选项
		query.setString("beginDate", beginDate); // 开始日期
		// query.setString("endDate", endDate); // 结束日期
		query.setLong("userid", userId); // 当前用户

		// query2.setString("orgId", orgId); // 所选项
		query2.setString("beginDate", beginDate); // 开始日期
		// query2.setString("endDate", endDate); // 结束日期
		query2.setLong("userid", userId); // 当前用户

		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query.setString("userName", "%" + userName + "%");
		}

		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query2.setString("userName", "%" + userName + "%");
		}

		// step3:检索得到一个计算结果
		List<Map> list2 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list2.size());
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogQueryUserDTO> retList = new ArrayList<WorkLogQueryUserDTO>();
		System.out.println(list1.size());
		for (Map map1 : list1) {
			WorkLogQueryUserDTO dto = this._convertMapToDTO(map1);
			retList.add(dto);
		}
		return retList;
	}

	/**
	 * 按照项目相关来检索相关人员，<br>
	 * 也可能会同时输入用户名称来做模糊匹配<br>
	 * 适用于：所领导。<br>
	 * 机构的选择或者默认由前面的调用程序来实现<br>
	 * 
	 * @param pageInfo
	 *            分页对象
	 * @param beginDate
	 *            开始日期,格式yyyymmdd
	 * @param endDate
	 *            结束日期,格式yyyymmdd
	 * @param orgId
	 *            选定的机构Id,代表所Id
	 * @param userId
	 *            当前用户的Id,用来判断是否关注
	 * @param userName
	 *            设定的人员姓名过滤器,采用模糊查询
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByProject(
			PageInfo pageInfo, String beginDate, String endDate, Long userId,
			String userName) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性

		// TODO:验证开始日期,结束日期的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,u.zc ZC,u.zw ZW,u.name3 NAME3,max(log.recordstate) RECORDSTATE,count(follow.id) ISFOLLOW  "
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join TB_WORKLOG_USER_CALENDAR log on log.personid = u.id "
						+ " left join TB_workLOG_FOLLOW follow on follow.userid=:userid and follow.followid = u.id "
						+ " where to_char(calendardate,'yyyy-mm-dd')= :beginDate "
						+ "  ");
		// 增加项目相关也就是抄送人员的判断
		strSql.append(" and instr(log.touserids,:userid2)>0");
		// 判断是否限定了人员姓名
		if (userName != null && userName.length() > 0) {
			strSql.append(" and u.name like :userName ");
		}
		// group by 方式
		strSql.append(" group by u.id,u.name,u.zc,u.zw,u.name3,u.orderno");
		// 人员排序方式
		strSql.append(" order by u.orderno");

		String strSql2 = "Select count(*) T1 from (" + strSql + ")";

		_log(strSql.toString());
		_log(strSql2.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		SQLQuery query2 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql2.toString());
		System.out.println(beginDate);
		System.out.println(endDate);
		System.out.println(userId);
		query.setString("beginDate", beginDate); // 开始日期
		// query.setString("endDate", endDate); // 结束日期
		query.setLong("userid", userId);
		query.setString("userid2", "," + userId + ","); // 当前用户,用来选择

		query2.setString("beginDate", beginDate); // 开始日期
		// query2.setString("endDate", endDate); // 结束日期
		query2.setLong("userid", userId);
		query2.setString("userid2", "," + userId + ","); // 当前用户,用来选择

		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query.setString("userName", "%" + userName + "%");
		}
		if (userName != null && userName.length() > 0) {
			query2.setString("userName", "%" + userName + "%");
		}

		// 初始化pageInfo对象
		// this._initPageInfoByQuery(query2, pageInfo);

		// step3:检索得到一个计算结果
		List<Map> list2 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list2.size());
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();
		System.out.println(list1.size());
		// step4:把这个计算结果转换成DTO返回
		List<WorkLogQueryUserDTO> retList = new ArrayList<WorkLogQueryUserDTO>();
		for (Map map1 : list1) {
			WorkLogQueryUserDTO dto = this._convertMapToDTO(map1);
			retList.add(dto);
		}
		return retList;
	}

	/**
	 * 室主任来选择使用本室与项目相关<br>
	 * 本室的机构编号由前面调用者来给定<br>
	 * 
	 * @param pageInfo
	 *            分页对象
	 * @param beginDate
	 *            开始日期,格式yyyymmdd
	 * @param endDate
	 *            结束日期,格式yyyymmdd
	 * @param orgId
	 *            选定的机构Id,代表室Id
	 * @param userId
	 *            当前用户的Id,用来判断是否关注
	 * @param userName
	 *            设定的人员姓名过滤器,采用模糊查询
	 * @return
	 */
	public List<WorkLogQueryUserDTO> getQueryUserDTOListByOrgIdAndProject(
			PageInfo pageInfo, String beginDate, String endDate, Long orgId,
			Long userId, String userName) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性
		if (orgId == null) {
			return new ArrayList<WorkLogQueryUserDTO>();
		}
		// TODO:验证开始日期,结束日期的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,u.zc ZC,u.zw ZW,u.name3 NAME3,max(log.recordstate) RECORDSTATE,count(follow.id) ISFOLLOW  "
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join TB_WORKLOG_USER_CALENDAR log on log.personid = u.id "
						+ " left join TB_workLOG_FOLLOW follow on follow.userid=:userid and follow.followid = u.id "
						+ " where to_char(calendardate,'yyyy-mm-dd')= :beginDate "
						+ "");
		// 增加新的判断条件
		// 或者是本室人员,或者是项目相关
		strSql
				.append(" and ( u.id4 = :orgId OR  instr(log.touserids,:userid2)>0 ) ");
		// 判断是否限定了人员姓名
		if (userName != null && userName.length() > 0) {
			strSql.append(" and u.name like :userName ");
		}
		// group by 方式
		strSql.append(" group by u.id,u.name,u.zc,u.zw,u.name3,u.orderno");
		// 人员排序方式
		strSql.append(" order by u.orderno");

		String strSql2 = "Select count(*) TOTAL from (" + strSql + ")";

		_log(strSql.toString());
		_log(strSql2.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		SQLQuery query2 = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql2.toString());

		query.setLong("orgId", orgId); // 室选项
		query.setString("beginDate", beginDate); // 开始日期
		// query.setString("endDate", endDate); // 结束日期
		query.setLong("userid", userId); // 当前用户
		query.setString("userid2", "," + userId + ","); // 当前用户前后加上逗号分割

		query2.setLong("orgId", orgId); // 室选项
		query2.setString("beginDate", beginDate); // 开始日期
		// query2.setString("endDate", endDate); // 结束日期
		query2.setLong("userid", userId); // 当前用户
		query2.setString("userid2", "," + userId + ","); // 当前用户前后加上逗号分割

		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query.setString("userName", "%" + userName + "%");
		}
		// 设定人员选择条件
		if (userName != null && userName.length() > 0) {
			query2.setString("userName", "%" + userName + "%");
		}

		// 初始化pageInfo
		// this._initPageInfoByQuery(query2, pageInfo);

		// step3:检索得到一个计算结果
		List<Map> list2 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list2.size());
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogQueryUserDTO> retList = new ArrayList<WorkLogQueryUserDTO>();
		System.out.println(list1.size());
		for (Map map1 : list1) {
			WorkLogQueryUserDTO dto = this._convertMapToDTO(map1);
			retList.add(dto);
		}
		return retList;
	}

	/**
	 * 打印输出SQL命令，仅测试使用
	 * 
	 * @param s
	 */
	private void _log(String s) {
		System.out.println("sql:");
		System.out.println(s);
	}

	/**
	 * 从结果集合中返回的Map转换成一个标准的DTO对象
	 */
	private WorkLogQueryUserDTO _convertMapToDTO(Map map1) {
		WorkLogQueryUserDTO dto = new WorkLogQueryUserDTO();
		if (map1.get("ID") != null) {
			dto.setUserId(Long.parseLong(map1.get("ID").toString()));
		}
		if (map1.get("NAME") != null) {
			dto.setUserName(map1.get("NAME").toString());

		}
		if (map1.get("NAME3") != null) {
			dto.setOrgName(map1.get("NAME3").toString());

		}
		if (map1.get("ZC") != null) {
			System.out.println(map1.get("ZC").toString());
			dto.setUserZc(map1.get("ZC").toString());

		}
		if (map1.get("ZW") != null) {
			System.out.println(map1.get("ZW").toString());
			dto.setUserZw(map1.get("ZW").toString());

		}
		if (map1.get("RECORDSTATE") != null) {
			dto.setRecordState(Long.parseLong(map1.get("RECORDSTATE")
					.toString()));

		}

		if (dto.getRecordState() != null
				&& dto.getRecordState().longValue() >= 1L) {
			dto.setRecordState(1L); // 已填报
			dto.setRecordStateName("是");
		} else {
			dto.setRecordState(0L); // 未填报
			dto.setRecordStateName("否");
		}
		if (map1.get("ISFOLLOW") != null) {
			dto.setIsFollow(Long.parseLong(map1.get("ISFOLLOW").toString()));
		}

		// 如果是否关注超过了1,则仅仅设置为1
		if (dto.getIsFollow() != null && dto.getIsFollow().longValue() >= 1L) {
			dto.setIsFollow(1L); // 已关注
			dto.setIsFollowName("是");
		} else {
			dto.setIsFollow(0L); // 未关注
			dto.setIsFollowName("否");
		}
		return dto;
	}

	/**
	 * 根据构建的Query对象,检索得到总记录数,来初始化pageInfo对象
	 * 
	 * @param query2
	 *            query2只返回一个数据TOTAL
	 * @param pageInfo
	 */
	private void _initPageInfoByQuery(SQLQuery query2, PageInfo pageInfo) {
		List<Map> list2 = (List<Map>) query2.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		// 设置pageinfo的最大值
		if (list2.size() > 0) {
			Map map1 = (Map) list2.get(0);
			Long total = (Long) map1.get("TOTAL");
			pageInfo.setTotalRecords((int) total.longValue());
		} else {
			pageInfo.setTotalRecords(0);
		}
	}

	/**
	 * 按照当前操作用户,要查看用户,查看类型,查看日期来判断是否提交日志 也可能用户已经提交日志,但由于查看权限的控制,当前用户仍然不可见
	 * 
	 * @param currUserId
	 *            当前用户
	 * @param userId
	 *            要查看用户
	 * @param operType
	 *            查看类型0L查询入口1L统计入口
	 * @param logDate
	 *            要查看的日志日期
	 * @return 1L 有日志，可以查看 0L 无日志，或者无权查看
	 */
	public Long isLogged(Long currUserId, Long userId, Long operType,
			Date logDate) {
		// Step1:判断参数之有效性
		if (operType == null) {
			throw new WorkLogException("调用参数错误:operType 不可为空!");
		}
		// Step2:判断用户是否已提交日志
		String strSql = "select * from tb_worklog_user_calendar a"
				+ " where to_char(a.calendardate,'yyyy-mm-dd')=to_char(:logdate,'yyyy-mm-dd') "
				+ " and to_char(a.personid)=:personid "
				+ " and a.recordstate=1";
		System.out.println(strSql);
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		query.setDate("logdate", logDate);
		query.setLong("personid", userId);
		query.addEntity(WorkLogUserCalendar.class);
		List<WorkLogUserCalendar> list = query.list();
		// 如果用户未提交日志,则直接返回0L
		if (list.size() == 0) {
			return 0L;
		}
		WorkLogUserCalendar usercalendar = (list.get(0));
		// Step3:在用户已提交日志的情况下,判断用户日志的可见范围
		// 在统计入口状态,仅判断指定用户是否提交了日志
		if (operType.equals(1L)) {
			return 1L;
		} else if (operType.equals(0L)) {
			// 系统管理员默认可用
			if (currUserId.equals(9999L) || currUserId.equals(99999L)) {
				return 1L;
			}
			// 先把两个用户都查询出来,如果用户不存在,抛出异常
			WorkLogViewBizbaseUser currUser = this.getViewUser(currUserId);
			WorkLogViewBizbaseUser user = this.getViewUser(userId);

			if (currUser == null) {
				throw new WorkLogException("调用参数错误:当前用户不存在!" + currUserId);
			}
			if (user == null) {
				throw new WorkLogException("调用参数错误:用户不存在!" + userId);
			}

			// 如果当前用户是机关用户,则通过
			if (currUser.getId3().equals(0L)) {
				return 1L;
			} else {
				// 如果当前用户是所用户,则先判断是否抄送
				// 如果本日日志已经抄送,则通过.
				if (usercalendar.getToUserIds() != null
						&& usercalendar.getToUserIds().indexOf(
								"," + currUserId + ",") >= 0) {
					return 1L;
				}
				// 其他情况下,判断当前用户的组织机构可见维度
				// 不可跨所来查看
				if (!(currUser.getId3().equals(user.getId3()))) {
					return 0L;
				}

				// 如果当前用户角色是所长/副所长/所办主任/科研秘书,则全所可见,通过
				if (currUser.getPosCode() != null) {
					if (currUser.getPosCode().equals(this.POS_SZ)
							|| currUser.getPosCode().equals(this.POS_FSZ)
							|| currUser.getPosCode().equals(this.POS_SBZR)
							|| currUser.getPosCode().equals(this.POS_KYSZ)
							|| currUser.getPosCode().equals(this.POS_KYMS)) {
						return 1L;
					}
				}

				// 如果当前角色是室主任/室副主任
				// 如果在本室内,则通过
				if (currUser.getPosCode() != null) {

					if (currUser.getPosCode().equals(POS_SZR)
							|| currUser.getPosCode().equals(POS_SFZR)) {
						if (currUser.getId4().equals(user.getId4())) {
							return 1L;
						}
					}
				}

				// 其余情况下,说明日志不可见
				return 0L;
			}
		} else {
			throw new WorkLogException("参数错误,operType=" + operType);
		}
	}

	/**
	 * 检索用户视图对象
	 * 
	 * @param id
	 * @return
	 */
	private WorkLogViewBizbaseUser getViewUser(Long id) {
		String strSql = "Select * from view_worklog_bizbase_user"
				+ " where id=" + id;
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());
		query.addEntity(WorkLogViewBizbaseUser.class);
		List<WorkLogViewBizbaseUser> list = query.list();
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
