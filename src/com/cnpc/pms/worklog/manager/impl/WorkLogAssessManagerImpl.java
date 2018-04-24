package com.cnpc.pms.worklog.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.rbac.orgview.dto.PurStruOrgDTO;
import com.cnpc.pms.bizbase.rbac.orgview.manager.PurStruOrgManager;
import com.cnpc.pms.worklog.dao.WorkLogAssessDAO;
import com.cnpc.pms.worklog.dto.WorkLogAssessMultiEditDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessSelectDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessStatDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessStisticDTO;
import com.cnpc.pms.worklog.entity.WorkLogAssess;
import com.cnpc.pms.worklog.manager.WorkLogAssessManager;

public class WorkLogAssessManagerImpl extends BaseManagerImpl implements
		WorkLogAssessManager {
	public WorkLogAssessDAO getDao(){
		return (WorkLogAssessDAO)super.getDao();
	}

	/**
	 * 根据给定的DTO对象,从工作日志用户日历表中,抽取指定比例的数据到评分记录表中去<br>
	 * 插入数据时要进行数据校验,同一用户,同一日期仅抽取一次,不重复抽取<br>
	 * 抽取以后要进行抽取数据的判断,如果判断是未填写日志,则自动打分为F<br>
	 * 数据抽取的算法如下: <br>
	 * 1.首先修改Tb_Worklog_User_Calendar数据表,设置其中的随机数字段. <br>
	 * 1.1 update tb_worklog_user_calendar set selectrandom = 10000*
	 * DBMS_RANDOM.VALUE();isSelect =0;
	 * 这个SQL命令要设置操作的数据范围,避免过长时间的操作,如开始时间,结束时间,所选部门范围.<br>
	 * 2.然后根据要抽取的百分比,例如是20%,来设定是否选中<br>
	 * 2.2 update tb_worklog_user_calendar set isselect=1 where selectrandom<2000;<br>
	 * 这样就在用户日历表中设置了本次要抽取的数据范围。<br>
	 * 3.然后编写SQL命令来完成实际的数据抽取。数据抽取时要避免重复抽取。
	 * insert into tb_worklog_assess() 
	 * select persionid, calendardate from tb_worklog_user_calendar
	 * where isselect=1 
	 * and personid||to_char(calendardate) not in(select personid||to_char(selectdata) from tb_worklog_assess);
	 * 4. 完成抽取工作。
	 * 
	 * @param dto
	 * @return 实际完成的抽取记录数
	 * @throws Exception
	 */
	public Long addWorkLogAssess(WorkLogAssessSelectDto dto) throws Exception {
		return this.getDao().completeRandomSelect(dto);
	}

	/**
	 * 更新指定打分记录的打分情况，打分记录容许多次修改。<br>
	 * 如果是系统自动打分为F，则不可修改。<br>
	 * 支持批量打分，可以通过在前台多次循环调用后台此方法来实现。<br>
	 * 更新失败，抛出异常。
	 * 
	 * @param workAssess
	 * @return
	 * @throws Exception
	 */
	public WorkLogAssess saveWorkLogAssess(WorkLogAssess workAssess)
			throws Exception {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		System.out.println(sessionData.get("userId"));
		workAssess.setOperId((Long) sessionData.get("userId"));
		this.getDao().saveWorkLogAssess(workAssess);
		return workAssess;
	}

	/**
	 * 一次更新多条打分记录情况,由前台构建相应的对象传入进行处理.
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long saveMultiWorkLogAssess(WorkLogAssessMultiEditDto dto) throws Exception{
		return 0L;
	}
	
	
	/**
	 * 根据统计条件,对工作日志的打分情况进行统计计算展示。
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List statWorkLogAssess(WorkLogAssessStatDto dto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public Map<String, Object> getWorkLogAssessStatistics(QueryConditions condition){
		WorkLogAssessDAO statisticsDAO =(WorkLogAssessDAO)SpringHelper.getBean(WorkLogAssessDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String beginDate=null;
		String endDate=null;
		Long orgId=null;
		String wd=null;
		String stype=null;
		String name=null;
		for(Map<String, Object> condition1 : condition.getConditions()){
			System.out.println(condition1.get("value"));
			if("beginDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				beginDate = (String)condition1.get("value");
			}else if("endDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				endDate=(String)condition1.get("value");
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
					orgId =Long.parseLong(condition1.get("value").toString());
				
			}else if("searchType".equals(condition1.get("key"))&&condition1.get("value")!=null){
				stype =condition1.get("value").toString();
				
			}else{
				
			}
		}
		PurStruOrgManager pmanager =(PurStruOrgManager)SpringHelper.getBean("purStruOrgManager");
		PurStruOrgDTO obj = (PurStruOrgDTO)pmanager.getPurOrgDTOById(orgId);
		System.out.println(obj.getEntityOrgFlag());
		if(obj.getEntityOrgFlag().equals("0")){
			wd="ID1";
			name="NAME1";
		}else if(obj.getEntityOrgFlag().equals("1")){
			wd="ID2";
			name="NAME2";
		}else if(obj.getEntityOrgFlag().equals("2")){
			wd="ID3";
			name="NAME3";
		}else if(obj.getEntityOrgFlag().equals("4")){
			name="NAME4";
		}else{
			log.error("未选择单位");
		}
		List<WorkLogAssessStisticDTO> list =new ArrayList<WorkLogAssessStisticDTO>();
		list=statisticsDAO.getWorkLogAssessStistic(pageInfo, stype, beginDate, endDate, orgId,name);
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogAssessSelectAndQuery(QueryConditions condition){
		WorkLogAssessDAO statisticsDAO =(WorkLogAssessDAO)SpringHelper.getBean(WorkLogAssessDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String beginDate=null;
		String endDate=null;
		String orgIds=null;
		String wd=null;
		String stype=null;
		String name=null;
		for(Map<String, Object> condition1 : condition.getConditions()){
			System.out.println(condition1.get("value"));
			if("beginDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				beginDate = (String)condition1.get("value");
			}else if("endDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				endDate=(String)condition1.get("value");
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
					orgIds =condition1.get("value").toString();
				
			}else if("searchType".equals(condition1.get("key"))&&condition1.get("value")!=null){
				stype =condition1.get("value").toString();
				
			}else{
				
			}
		}
		name="NAME3";
		List<WorkLogAssessStisticDTO> list =new ArrayList<WorkLogAssessStisticDTO>();
		list=statisticsDAO.getWorkLogAssessSelectAndQuery(pageInfo, stype, beginDate, endDate, orgIds,name);
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
	public Map<String, Object> getWorkLogAssessStatisticsByWd(QueryConditions condition){
		WorkLogAssessDAO statisticsDAO =(WorkLogAssessDAO)SpringHelper.getBean(WorkLogAssessDAO.class.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String beginDate=null;
		String endDate=null;
		Long orgId=null;
		String wd=null;
		String stype=null;
		String name=null;
		for(Map<String, Object> condition1 : condition.getConditions()){
			System.out.println(condition1.get("value"));
			if("beginDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				beginDate = (String)condition1.get("value");
			}else if("endDate".equals(condition1.get("key"))&&condition1.get("value")!=null){
				endDate=(String)condition1.get("value");
			}else if("orgId".equals(condition1.get("key"))&&condition1.get("value")!=null){
					orgId =Long.parseLong(condition1.get("value").toString());
				
			}else if("searchType".equals(condition1.get("key"))&&condition1.get("value")!=null){
				stype =condition1.get("value").toString();
				
			}else if("tjwd".equals(condition1.get("key"))&&condition1.get("value")!=null){
				wd =condition1.get("value").toString();
				
			}
		}
		if(wd.equals(",,id4,")){
			wd="ID4";
			name="NAME4";
		}else if(wd.equals(",id3,,")){
			wd="ID3";
			name="NAME3";
		}else if(wd.equals("id2,,,")){
			wd="ID2";
			name="NAME2";
		}else if(wd.equals(",,,userId")){
			wd="USERID";
			name="USERNAME";
		}
		List<WorkLogAssessStisticDTO> list =new ArrayList<WorkLogAssessStisticDTO>();
		list=statisticsDAO.getWorkLogAssessStisticByWd(pageInfo, stype, beginDate, endDate, orgId,name,wd);
		map.put("pageinfo", pageInfo);
		map.put("header", "");
		map.put("data", list);
		return map;
	}
}
