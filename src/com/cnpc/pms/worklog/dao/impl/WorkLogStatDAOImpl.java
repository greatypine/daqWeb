package com.cnpc.pms.worklog.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.impl.PageInfo;

import com.cnpc.pms.worklog.dao.WorkLogStatDAO;
import com.cnpc.pms.worklog.dto.WorkLogStatByBranchOrgDTO;
import com.cnpc.pms.worklog.entity.WorkLogAssess;
import com.cnpc.pms.bizbase.rbac.orgview.entity.PurStruOrg;
import com.cnpc.pms.bizbase.rbac.usermanage.entity.User;

/**
 * 工作日志统计所使用的DAO方法的实现
 * 
 * @author liujunsong
 * 
 */
public class WorkLogStatDAOImpl extends BaseDAOHibernate implements
		WorkLogStatDAO {

	String view_user = "VIEW_WORKLOG_BIZBASE_USER"; // 用户的所需信息的视图

	// 用用户日历表进行扩展,扩展一个从日志表中获取的工作小时数的字段
	String VIEW_WORKLOG_USER_CALENDAR = "(select a.Id,a.calendardate,a.personid,a.recordstate,a.workdaystate,a.ontimestate,"
		+"sum(case when a.recordstate=1 then b.hours else 0 end) hours "
			+ " from tb_worklog_user_calendar a "
			+ " left join tb_worklog b "
			+ " on to_char(a.calendardate,'yyyy-mm-dd') = to_char(b.logdate,'yyyy-mm-dd') "
			+ " and a.personid = b.userid "
			+ " group by a.Id,a.calendardate,a.personid,a.recordstate,a.workdaystate,a.ontimestate )";

	/**
	 * 按照分院进行工作日志的统计算法
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param jobType
	 * @return
	 */
	public List getUserStatByBranch(String beginDate, String endDate,
			Long jobType) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id2 ID,u.name2 NAME,sum(log.recordstate) RECORDSTATE,"
						+ "sum(case when log.ontimestate=0 then 1 else 0 end ) OUTTIMESTATE,"
						+ "sum(log.workdaystate) WORKDAYSTATE,"
						+ "(case when sum(log.workdaystate)>0 then sum(log.recordstate)/sum(log.workdaystate) else null end) COMMITRATE, "
						+ "(case when sum(log.recordstate)>0 then sum(log.ontimestate)/sum(log.recordstate) else null end) ONTIMERATE, "
						+ "sum(log.hours) HOURS"
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join "
						+ this.VIEW_WORKLOG_USER_CALENDAR
						+ " log on log.personid = u.id "
						// 忽略TB_WORKLOG,不再直接进行关联
						// +
						// " left join TB_WORKLOG log2 on to_char(log2.logdate,'yyyy-mm-dd') = to_char(calendardate,'yyyy-mm-dd')"
						// + " and log2.userid = u.id"
						// 只考虑开始日期,结束日期之内的用户日历数据
						+ " where to_char(calendardate,'yyyy-mm-dd')>= :beginDate and to_char(calendardate,'yyyy-mm-dd')<= :endDate"
						// 只考虑用户设置的日志开始填报时间和日志结束填报时间之间的数据进行统计
						+ " and to_char(calendardate,'yyyy-mm-dd')>= to_char(u.startlogdate,'yyyy-mm-dd') "
						+ " and to_char(calendardate,'yyyy-mm-dd')<= to_char(u.endlogdate,'yyyy-mm-dd') "
						+ " and u.id2>0");
		// 增加jobType的判断条件
		System.out.println(jobType);
		if (jobType == null || jobType.equals(-1L)) {
			// Do Nothing
		} else {
			strSql.append(" and u.jobType = " + jobType.toString());
		}

		// 增加orderno3的限定条件,仅处理orderno3>0的,其余的忽略掉
		// orderno3实际对应在logorderno字段上面
		strSql.append(" and u.orderno3>0");

		// group by 方式
		strSql.append(" group by u.id2,u.name2,u.orderno2");
		// 人员排序方式
		strSql.append(" order by u.orderno2");

		_log(strSql.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());

		query.setString("beginDate", beginDate); // 开始日期
		query.setString("endDate", endDate); // 结束日期

		// step3:检索得到一个计算结果
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogStatByBranchOrgDTO> retList = new ArrayList<WorkLogStatByBranchOrgDTO>();
		for (Map map1 : list1) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map1);
			retList.add(dto);
		}
		// Step5:计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : retList) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(Double.valueOf(totaldto.getHours().toString().substring(0, totaldto.getHours().toString().indexOf(".")+2))
					+ Double.valueOf(dto.getHours().toString().substring(0, dto.getHours().toString().indexOf(".")+2)));
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");
		}
		retList.add(totaldto);
		return retList;

	}

	/**
	 * 按照所进行工作日志的统计算法
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param jobType
	 *            用工类型
	 * @param branchId
	 *            分院Id
	 * @return
	 */
	public List getUserStatByOrg(String beginDate, String endDate,
			Long jobType, Long branchId) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id3 ID,u.name3 NAME,sum(log.recordstate) RECORDSTATE,"
						+ "sum(case when log.ontimestate=0 then 1 else 0 end ) OUTTIMESTATE,"
						+ "sum(log.workdaystate) WORKDAYSTATE,"
						+ "(case when sum(log.workdaystate)>0 then sum(log.recordstate)/sum(log.workdaystate) else null end) COMMITRATE, "
						+ "(case when sum(log.recordstate)>0 then sum(log.ontimestate)/sum(log.recordstate) else null end) ONTIMERATE, "
						+ "sum(log.hours) HOURS"
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join "
						+ this.VIEW_WORKLOG_USER_CALENDAR
						+ " log on log.personid = u.id "
						// 不再需要关联TB_WORKLOG 表
						// +
						// " left join TB_WORKLOG log2 on to_char(log2.logdate,'yyyy-mm-dd') = to_char(calendardate,'yyyy-mm-dd')"
						// + " and log2.userid = u.id"
						+ " where to_char(calendardate,'yyyy-mm-dd')>= :beginDate and to_char(calendardate,'yyyy-mm-dd')<= :endDate"
						// 只考虑用户设置的日志开始填报时间和日志结束填报时间之间的数据进行统计
						+ " and to_char(calendardate,'yyyy-mm-dd')>= to_char(u.startlogdate,'yyyy-mm-dd') "
						+ " and to_char(calendardate,'yyyy-mm-dd')<= to_char(u.endlogdate,'yyyy-mm-dd') "
						+ " and u.id3>0" + " and u.id2=" + branchId);
		// 增加jobType的判断条件
		if (jobType == null || jobType.equals(-1L)) {
			// Do Nothing
		} else {
			strSql.append(" and u.jobType = " + jobType.toString());
		}
		// 增加orderno3的限定条件,仅处理orderno3>0的,其余的忽略掉
		// orderno3实际对应在logorderno字段上面
		strSql.append(" and u.orderno3>0");

		// group by 方式
		strSql.append(" group by u.id3,u.name3,u.orderno3");
		// 人员排序方式
		strSql.append(" order by u.orderno3");

		_log(strSql.toString());

		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql.toString());

		query.setString("beginDate", beginDate); // 开始日期
		query.setString("endDate", endDate); // 结束日期

		// step3:检索得到一个计算结果
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogStatByBranchOrgDTO> retList = new ArrayList<WorkLogStatByBranchOrgDTO>();
		for (Map map1 : list1) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map1);
			retList.add(dto);
		}
		// Step5:计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : retList) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(Double.valueOf(totaldto.getHours().toString().substring(0, totaldto.getHours().toString().indexOf(".")+2))
					+ Double.valueOf(dto.getHours().toString().substring(0, dto.getHours().toString().indexOf(".")+2)));
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		retList.add(totaldto);
		return retList;

	}

	/**
	 * 按照所进行工作日志的统计算法<br>
	 * 按照人员进行统计时,要考虑分页的问题
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param jobType
	 *            用工类型
	 * @param orgId
	 *            所Id
	 * @return
	 */
	public List getUserStatByPerson(PageInfo pageInfo, String beginDate,
			String endDate, Long jobType, Long orgId) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,sum(log.recordstate) RECORDSTATE,"
						+ "sum(case when log.ontimestate=0 then 1 else 0 end ) OUTTIMESTATE,"
						+ "sum(log.workdaystate) WORKDAYSTATE,"
						+ "(case when sum(log.workdaystate)>0 then sum(log.recordstate)/sum(log.workdaystate) else null end) COMMITRATE, "
						+ "(case when sum(log.recordstate)>0 then sum(log.ontimestate)/sum(log.recordstate) else null end) ONTIMERATE, "
						+ "sum(log.hours) HOURS"
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join "
						+ this.VIEW_WORKLOG_USER_CALENDAR
						+ " log on log.personid = u.id "
						// TB_WORKLOG 不再需要
						// +
						// " left join TB_WORKLOG log2 on to_char(log2.logdate,'yyyy-mm-dd') = to_char(calendardate,'yyyy-mm-dd')"
						// + " and log2.userid = u.id"
						+ " where to_char(calendardate,'yyyy-mm-dd')>= :beginDate and to_char(calendardate,'yyyy-mm-dd')<= :endDate"
						// 只考虑用户设置的日志开始填报时间和日志结束填报时间之间的数据进行统计
						+ " and to_char(calendardate,'yyyy-mm-dd')>= to_char(u.startlogdate,'yyyy-mm-dd') "
						+ " and to_char(calendardate,'yyyy-mm-dd')<= to_char(u.endlogdate,'yyyy-mm-dd') "
						+ " and u.id3=" + orgId);
		// 增加jobType的判断条件
		if (jobType == null || jobType.equals(-1L)) {
			// Do Nothing
		} else {
			strSql.append(" and u.jobType = " + jobType.toString());
		}

		// 增加orderno3的限定条件,仅处理orderno3>0的,其余的忽略掉
		// orderno3实际对应在logorderno字段上面
		strSql.append(" and u.orderno3>0");

		// group by 方式
		strSql.append(" group by u.id,u.name,u.orderno");
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
		query.setString("endDate", endDate); // 结束日期

		query2.setString("beginDate", beginDate); // 开始日期
		query2.setString("endDate", endDate); // 结束日期

		// 初始化pageInfo
		// this._initPageInfoByQuery(query2, pageInfo);

		// step3:检索得到一个计算结果
		// list1是全部结果,list2是分页以后的结果
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list1.size());

		List<Map> list2 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogStatByBranchOrgDTO> retList1 = new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> retList2 = new ArrayList<WorkLogStatByBranchOrgDTO>();

		for (Map map1 : list1) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map1);
			retList1.add(dto);
		}
		for (Map map2 : list2) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map2);
			retList2.add(dto);
		}
		// Step5:计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : retList1) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(Double.valueOf(totaldto.getHours().toString().substring(0, totaldto.getHours().toString().indexOf(".")+2))
					+ Double.valueOf(dto.getHours().toString().substring(0, dto.getHours().toString().indexOf(".")+2)));
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		retList2.add(totaldto);
		return retList2;

	}

	/**
	 * 按照室进行工作日志的统计算法<br>
	 * 按照人员进行统计时,要考虑分页的问题
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param jobType
	 *            用工类型
	 * @param orgId
	 *            所Id
	 * @return
	 */
	public List getUserStatByPerson2(PageInfo pageInfo, String beginDate,
			String endDate, Long jobType, Long orgId) {
		StringBuffer strSql = new StringBuffer();
		// step1:判断数据的有效性

		// step2:将用户视图,用户日历表,用户关注表进行关联.
		strSql
				.append("select u.id ID,u.name NAME,sum(log.recordstate) RECORDSTATE,"
						+ "sum(case when log.ontimestate=0 then 1 else 0 end ) OUTTIMESTATE,"
						+ "sum(log.workdaystate) WORKDAYSTATE,"
						+ "(case when sum(log.workdaystate)>0 then sum(log.recordstate)/sum(log.workdaystate) else null end) COMMITRATE, "
						+ "(case when sum(log.recordstate)>0 then sum(log.ontimestate)/sum(log.recordstate) else null end) ONTIMERATE, "
						+ "sum(log.hours) HOURS"
						+ " from "
						+ this.view_user
						+ " u "
						+ " left join "
						+ this.VIEW_WORKLOG_USER_CALENDAR
						+ " log on log.personid = u.id "
						// TB_WORKLOG不再需要
						// +
						// " left join TB_WORKLOG log2 on to_char(log2.logdate,'yyyy-mm-dd') = to_char(calendardate,'yyyy-mm-dd')"
						// + " and log2.userid = u.id"
						+ " where to_char(calendardate,'yyyy-mm-dd')>= :beginDate and to_char(calendardate,'yyyy-mm-dd')<= :endDate"
						// 只考虑用户设置的日志开始填报时间和日志结束填报时间之间的数据进行统计
						+ " and to_char(calendardate,'yyyy-mm-dd')>= to_char(u.startlogdate,'yyyy-mm-dd') "
						+ " and to_char(calendardate,'yyyy-mm-dd')<= to_char(u.endlogdate,'yyyy-mm-dd') "
						+ " and u.id4=" + orgId);
		// 增加jobType的判断条件
		if (jobType == null || jobType.equals(-1L)) {
			// Do Nothing
		} else {
			strSql.append(" and u.jobType = " + jobType.toString());
		}

		// 增加orderno3的限定条件,仅处理orderno3>0的,其余的忽略掉
		// orderno3实际对应在logorderno字段上面
		strSql.append(" and u.orderno3>0");

		// group by 方式
		strSql.append(" group by u.id,u.name,u.orderno");
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
		query.setString("endDate", endDate); // 结束日期

		query2.setString("beginDate", beginDate); // 开始日期
		query2.setString("endDate", endDate); // 结束日期

		// 初始化pageInfo
		// this._initPageInfoByQuery(query2, pageInfo);

		// step3:检索得到一个计算结果
		// list1是全部结果,list2是分页以后的结果
		List<Map> list1 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		pageInfo.setTotalRecords(list1.size());

		List<Map> list2 = query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
				pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
				.setMaxResults(pageInfo.getRecordsPerPage()).list();

		// step4:把这个计算结果转换成DTO返回
		List<WorkLogStatByBranchOrgDTO> retList1 = new ArrayList<WorkLogStatByBranchOrgDTO>();
		List<WorkLogStatByBranchOrgDTO> retList2 = new ArrayList<WorkLogStatByBranchOrgDTO>();

		for (Map map1 : list1) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map1);
			retList1.add(dto);
		}
		for (Map map2 : list2) {
			WorkLogStatByBranchOrgDTO dto = this._convertMapToBranchDTO(map2);
			retList2.add(dto);
		}
		// Step5:计算合计值
		WorkLogStatByBranchOrgDTO totaldto = new WorkLogStatByBranchOrgDTO();
		totaldto.setName("合计");
		for (WorkLogStatByBranchOrgDTO dto : retList1) {
			totaldto.setRecordState(totaldto.getRecordState()
					+ dto.getRecordState());
			totaldto.setOuttimeState(totaldto.getOuttimeState()
					+ dto.getOuttimeState());
			totaldto.setWorkdayState(totaldto.getWorkdayState()
					+ dto.getWorkdayState());
			totaldto.setHours(Double.valueOf(totaldto.getHours().toString().substring(0, totaldto.getHours().toString().indexOf(".")+2))
					+ Double.valueOf(dto.getHours().toString().substring(0, dto.getHours().toString().indexOf(".")+2)));
		}
		// 计算比率
		if (totaldto.getRecordState().longValue() > 0) {
			// 及时率
			double d1 = totaldto.getRecordState() - totaldto.getOuttimeState();
			totaldto.setOnTimeRate(d1 / (0.0 + totaldto.getRecordState()));
			totaldto
					.setOnTimePercenter((int) (totaldto.getOnTimeRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		// 上报率
		if (totaldto.getWorkdayState().longValue() > 0) {
			totaldto.setCommitRate((0.0 + totaldto.getRecordState())
					/ (0.0 + totaldto.getWorkdayState()));
			totaldto
					.setCommitPercenter((int) (totaldto.getCommitRate() * 10000 + 0.5)
							/ 100.0 + "%");

		}
		retList2.add(totaldto);
		return retList2;

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
	private WorkLogStatByBranchOrgDTO _convertMapToBranchDTO(Map map1) {
		WorkLogStatByBranchOrgDTO dto = new WorkLogStatByBranchOrgDTO();
		if (map1.get("ID") != null) {
			dto.setId(Long.parseLong(map1.get("ID").toString()));
		}
		if (map1.get("NAME") != null) {
			dto.setName(map1.get("NAME").toString());
		}
		if (map1.get("RECORDSTATE") != null) {
			dto.setRecordState(Long.parseLong(map1.get("RECORDSTATE")
					.toString()));
		}
		if (map1.get("OUTTIMESTATE") != null) {
			dto.setOuttimeState(Long.parseLong(map1.get("OUTTIMESTATE")
					.toString()));
		}
		if (map1.get("WORKDAYSTATE") != null) {
			dto.setWorkdayState(Long.parseLong(map1.get("WORKDAYSTATE")
					.toString()));
		}
		if (map1.get("COMMITRATE") != null) {
			dto
					.setCommitRate(Double.valueOf(map1.get("COMMITRATE")
							.toString()));
			dto.setCommitPercenter((int) (Double.valueOf(map1.get("COMMITRATE")
					.toString()) * 10000 + 0.5)
					/ 100.0 + "%");
		} else {
			dto.setCommitPercenter("0.00%");

		}
		if (map1.get("ONTIMERATE") != null) {
			dto
					.setOnTimeRate(Double.valueOf(map1.get("ONTIMERATE")
							.toString()));
			dto.setOnTimePercenter((int) (Double.valueOf(map1.get("ONTIMERATE")
					.toString()) * 10000 + 0.5)
					/ 100.0 + "%");

		}
		if (map1.get("HOURS") != null) {
			dto.setHours(Double.valueOf(map1.get("HOURS").toString().substring(0,map1.get("HOURS").toString().indexOf(".")+2)));

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
	 * 获取分院的列表，数据组装到一个WorkLogStatByBranchOrgDTO里面返回
	 */
	public List<PurStruOrg> getBranchList() {
		String strSql = "select * from tb_bizbase_psorg where entityorgflag=1 "
				+ " order by orderno";
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql);
		query.addEntity(PurStruOrg.class);
		List<PurStruOrg> list1 = query.list();
		return list1;
	}

	/**
	 * 根据某个分院,来检索所有的下属所的Id, 但仅检索logorderno>0的数据
	 * 
	 * @param branchId
	 * @return
	 */
	public List<PurStruOrg> getDeptList(Long branchId) {
		String strSql = " select * from tb_bizbase_psorg "
				+ " where entityorgflag=2  and parent_id=" + branchId
				+ " and logorderno>0" + " order by logorderno ";
		SQLQuery query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(strSql);
		query.addEntity(PurStruOrg.class);
		List<PurStruOrg> list1 = query.list();
		return list1;
	}
	public List<User> getUserList(Long suoOrShiId,PageInfo pageInfo){
		StringBuffer strSql = new StringBuffer();
		strSql.append(" select ID,NAME from view_worklog_bizbase_user "
			+ " where  id3=:suoOrShiId or id4=:suoOrShiId"
			+ " order by orderno ");
	SQLQuery query = getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createSQLQuery(strSql.toString());
	query.setLong("suoOrShiId", suoOrShiId);
	List<Map> listTotal = query.setResultTransformer(
			Transformers.ALIAS_TO_ENTITY_MAP).list();
	System.out.println(listTotal.size());
	pageInfo.setTotalRecords(listTotal.size());
	List<Map> list = query.setResultTransformer(
			Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
	
	List<User> list1 = new ArrayList();
	for(Map map:list){
		User user=new User();
		if(map.get("ID")!=null){
			user.setId(Long.parseLong(map.get("ID").toString()));
		}
		if(map.get("NAME")!=null){
			user.setName(map.get("NAME").toString());
		}
		list1.add(user);
	}
	return list1;
	}
	public List<User> getUserListByJobType(Long suoOrShiId,Long jobType,PageInfo pageInfo){
		StringBuffer strSql = new StringBuffer();
		if(jobType.equals(-1L)){
			strSql.append(" select ID,NAME from view_worklog_bizbase_user "
					+ " where  id3=:suoOrShiId or id4=:suoOrShiId "
					+ " order by orderno ");
		}else{
			strSql.append(" select ID,NAME from view_worklog_bizbase_user "
					+ " where  (id3=:suoOrShiId or id4=:suoOrShiId) and jobtype=:jobType "
					+ " order by orderno ");
		}
	//System.out.println(strSql.toString());
	SQLQuery query = getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createSQLQuery(strSql.toString());
	if(jobType.equals(-1L)){
		query.setLong("suoOrShiId", suoOrShiId);

	}else{
		query.setLong("suoOrShiId", suoOrShiId);
		query.setLong("jobType", jobType);

	}
	List<Map> listTotal = query.setResultTransformer(
			Transformers.ALIAS_TO_ENTITY_MAP).list();
	System.out.println(listTotal.size());
	pageInfo.setTotalRecords(listTotal.size());
	List<Map> list = query.setResultTransformer(
			Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(
					pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
	
	List<User> list1 = new ArrayList();
	for(Map map:list){
		User user=new User();
		if(map.get("ID")!=null){
			user.setId(Long.parseLong(map.get("ID").toString()));
		}
		if(map.get("NAME")!=null){
			user.setName(map.get("NAME").toString());
		}
		list1.add(user);
	}
	return list1;
		
	}
}
