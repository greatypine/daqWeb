package com.cnpc.pms.workflow.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.dto.ToDoByModule;
import com.cnpc.pms.workflow.entity.DoneByModule;
import com.cnpc.pms.workflow.entity.FinishedByModule;

public class WFInstanceDAOImpl extends BaseDAOHibernate implements
		WFInstanceDAO {
	/**
	 * 检索用户在某模块下的待办
	 */
	public static final String TODO_QUERY_MODULE = "select us.pk_org,step.issameorg,step.TODOOPER,pos.posid,wf.* from wf_flowinstance wf"
			+ " inner join wf_stepinstance step on wf.currentstepid=step.id"
			+ " inner join wf_instancesteptopos pos on step.id=pos.stepid"
			+ " inner join tb_bizbase_user us on us.pk_position=pos.posid"
			+ " where us.id=:personId and wf.state=0 and wf.moduleid=:moduleId order by wf.Laststepdate desc";
	/**
	 * 检索用户的待办
	 */
	public static final String TODO_QUERY_ALL = "select us.pk_org,step.issameorg,step.TODOOPER,pos.posid,wf.* from wf_flowinstance wf"
			+ " inner join wf_stepinstance step on wf.currentstepid=step.id"
			+ " inner join wf_instancesteptopos pos on step.id=pos.stepid"
			+ " inner join tb_bizbase_user us on us.pk_position=pos.posid"
			+ " where us.id=:personId and wf.state=0 order by wf.Laststepdate desc";

	public boolean isMeetCondition(String condition) {
		final String sql = "select 1 as condition from dual where " + condition;
		Session session = this.getSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		sqlQuery.addScalar("condition", Hibernate.LONG);
		try {
			Long result = (Long) sqlQuery.uniqueResult();
			if (result == null)
				return false;
			else
				return true;
		} catch (Exception ex) {
			log.error("流程条件缺少参数:" + condition, ex);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List getToDoList(Long personId, Long moduleId) {
		SQLQuery toDoQuery = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(TODO_QUERY_MODULE);
		toDoQuery.setLong("personId", personId);
		toDoQuery.setLong("moduleId", moduleId);
		List<Map> list = toDoQuery.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Map map : list) {
			System.out.println(map.get("TODOOPER") + "...");
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getAllToDoList(Long personId) {
		SQLQuery toDoQuery = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(TODO_QUERY_ALL);
		toDoQuery.setLong("personId", personId);
		List<Map> list = toDoQuery.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Map map : list) {
			System.out.println(map.get("TODOOPER") + "...all");
		}
		return list;
	}
	
	/**
	 * 逻辑删除流程实例已经执行部分的记录,包括变量,步骤,待办提示.
	 */
	public void deleteInstanceById(Long instanceid) {
		Session session = this.getSession();
		String varsql = "update wf_instancevariable set isdel=1 where flowinstanceid=?"; // 逻辑删除参数表
		String stepsql = "update wf_stepinstance set isdel=1 where flowinstanceid=?"; // 逻辑删除步骤表
		String todosql = "update wf_todomemo set state=1 where flowinstanceid=?"; // 将待办消息设为已读

		session.createSQLQuery(varsql).setParameters(
				new Object[] { instanceid }, new Type[] { Hibernate.LONG })
				.executeUpdate();
		session.createSQLQuery(stepsql).setParameters(
				new Object[] { instanceid }, new Type[] { Hibernate.LONG })
				.executeUpdate();
		session.createSQLQuery(todosql).setParameters(
				new Object[] { instanceid }, new Type[] { Hibernate.LONG })
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<ToDoByModule> getToDoByModule(Long userId, PageInfo pageInfo) {
		Session session = this.getSession();
		// String sql =
		// "select count(*),l1.name as name1,m.id,l2.name as name2 from wf_view_newtodoview t inner join wf_module m on t.moduleid=m.id inner join wf_module_level1 l1 "
		// +
		// "on m.level1id=l1.id inner join wf_module_level2 l2 on m.level2id=l2.id where t.userId=? group by l1.name,m.id,l2.name";
		// String countsql =
		// "select count(*)from (select m.id from view_wftodoview t inner join wf_module m on t.moduleid=m.id where t.userId=? group by m.id)";
		// String numsql =
		// "select count(*) from wf_view_newtodoview t where t.userId=?";
		String sql = "select count(*),d.dicttext as name1,m.id,m.name as name2,m.urlstr as moduleUrl,max(t.laststepdate) as dotime from view_wf_todoview_alltype t inner join wf_module m on " +
				"t.moduleid=m.id inner join dict_wf_module_type d on m.moduletype=d.dictcode where t.userId=? group by d.dicttext,m.id,m.name,m.urlstr";
		String countsql = "select count(*) from (select m.id from view_wf_todoview_alltype t inner join wf_module m on t.moduleid=m.id where t.userId=? group by m.id)";
		String numsql = "select count(*) from view_wf_todoview_alltype t where t.userId=?";

		List<ToDoByModule> resultList = new ArrayList<ToDoByModule>();
		// 查询待办总条数
		Integer count = ((BigDecimal) session.createSQLQuery(numsql)
				.setParameters(new Object[] { userId },
						new Type[] { Hibernate.LONG }).uniqueResult())
				.intValue();
		Integer total = ((BigDecimal) session.createSQLQuery(countsql)
				.setParameters(new Object[] { userId },
						new Type[] { Hibernate.LONG }).uniqueResult())
				.intValue();
		pageInfo.setTotalRecords(total);

		// 若待办数大于0则分类统计
		if (count > 0) {
			List<Object[]> objList = session.createSQLQuery(sql).setParameters(
					new Object[] { userId }, new Type[] { Hibernate.LONG })
					.setFirstResult(
							pageInfo.getRecordsPerPage()
									* (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();

			for (Object[] obj : objList) {
				ToDoByModule todo = new ToDoByModule();
				todo.setCount(((BigDecimal) obj[0]).intValue());
				todo.setModuleLevel1Name((String) obj[1]);
				todo.setModuleId(((BigDecimal) obj[2]).longValue());
				todo.setModuleLevel2Name((String) obj[3]);
				todo.setModuleUrl((String) obj[4]);
				todo.setDotime((Date)obj[5]);
				todo.setPercentage(todo.getCount() * 100 / count + "%");
				resultList.add(todo);
			}
		}
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ToDoByModule> getDoneByModule(Long userId, PageInfo pageInfo) {
		Session session = this.getSession();
		String sql = "select count(*),d.dicttext as name1,m.id,m.name as name2,m.urlstr2 as moduleUrl,max(t.opertime) as dotime from view_wf_doneview_alltype t inner join wf_module m on " +
				"t.moduleid=m.id inner join dict_wf_module_type d on m.moduletype=d.dictcode where t.userId=? group by d.dicttext,m.id,m.name,m.urlstr2";
		String countsql = "select count(*) from (select m.id from view_wf_doneview_alltype t inner join wf_module m on t.moduleid=m.id where t.userId=? group by m.id)";
		String numsql = "select count(*) from view_wf_doneview_alltype t where t.userId=?";

		List<ToDoByModule> resultList = new ArrayList<ToDoByModule>();
		// 查询待办总条数
		Integer count = ((BigDecimal) session.createSQLQuery(numsql).setParameters(new Object[] { userId },
						new Type[] { Hibernate.LONG }).uniqueResult()).intValue();
		Integer total = ((BigDecimal) session.createSQLQuery(countsql).setParameters(new Object[] { userId },
						new Type[] { Hibernate.LONG }).uniqueResult()).intValue();
		pageInfo.setTotalRecords(total);

		// 若待办数大于0则分类统计
		if (count > 0) {
			List<Object[]> objList = session.createSQLQuery(sql).setParameters(
					new Object[] { userId }, new Type[] { Hibernate.LONG }).setFirstResult(pageInfo.getRecordsPerPage() * (pageInfo.getCurrentPage() - 1))
					.setMaxResults(pageInfo.getRecordsPerPage()).list();
			for (Object[] obj : objList) {
				ToDoByModule todo = new ToDoByModule();
				todo.setCount(((BigDecimal) obj[0]).intValue());
				todo.setModuleLevel1Name((String) obj[1]);
				todo.setModuleId(((BigDecimal) obj[2]).longValue());
				todo.setModuleLevel2Name((String) obj[3]);
				todo.setModuleUrl((String) obj[4]);
				todo.setDotime((Date)obj[5]);
				todo.setPercentage(todo.getCount() * 100 / count + "%");
				resultList.add(todo);
			}
		}
		return resultList;
	}
	public List<com.cnpc.pms.workflow.entity.ToDoByModule> getToDoByModuleByUserId(Long userId) {
		// TODO Auto-generated method stub
		String hql = "from ToDoByModule t where t.userId = ? order by t.dotime desc";
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<com.cnpc.pms.workflow.entity.ToDoByModule> list = query.setParameters(new Object[] {userId}, new Type[] {Hibernate.LONG}).list();
		return list;
	}

	public List<DoneByModule> getDoneByModuleByUserId(Long userId) {
		// TODO Auto-generated method stub
		String hql = "from DoneByModule t where t.userId = ? order by t.dotime desc";
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<com.cnpc.pms.workflow.entity.DoneByModule> list = query.setParameters(new Object[] {userId}, new Type[] {Hibernate.LONG}).list();
		return list;
	}

	public List<FinishedByModule> getFinishByModuleByUserId(Long userId) {
		// TODO Auto-generated method stub
		String hql = "from FinishedByModule t where t.userId = ? order by t.dotime desc";
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<FinishedByModule> list = query.setParameters(new Object[] {userId}, new Type[] {Hibernate.LONG}).list();
		return list;
	}
}
