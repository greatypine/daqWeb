package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.query.QueryDefinition;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.query.model.PMSQuery;
import com.cnpc.pms.base.security.SessionManager;
import com.cnpc.pms.base.security.UserSession;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.project.util.ProjectConstants;
import com.cnpc.pms.workflow.dao.ToDoByModuleDAO;
import com.cnpc.pms.workflow.dao.WFInstanceDAO;
import com.cnpc.pms.workflow.dto.ToDoByModuleDTO;
import com.cnpc.pms.workflow.entity.ToDoByModule;
import com.cnpc.pms.workflow.manager.ToDoByModuleManager;

public class ToDoByModuleManagerImpl extends BaseManagerImpl implements ToDoByModuleManager{

	public List<ToDoByModule> getToDoByModuleByUserId() {
		UserSession userSession = SessionManager.getUserSession();
		Map sessionData = userSession.getSessionData();
		Long userid = (Long) sessionData.get("userId");
		WFInstanceDAO instanceDAO = (WFInstanceDAO) SpringHelper.getBean(WFInstanceDAO.class.getName());
		List<ToDoByModule> list=instanceDAO.getToDoByModuleByUserId(userid);
		return list;
	}
	
  public Map<String, Object> getToDoTasks(QueryConditions conditions) {  
    ToDoByModuleDAO dao = (ToDoByModuleDAO) SpringHelper.getBean(ToDoByModuleDAO.class.getName());
    PMSQuery query = QueryDefinition.getQueryById(conditions.getQueryId());
    Map<String, Object> map = new HashMap<String, Object>();

    Map newMap = new HashMap();
    // 以下是query中配置的可用来查询的column的值
    List<Map<String, Object>> values = conditions.getConditions();
    for (Map<String, Object> mp : values) {
      String key = (String) mp.get("key");
      String value = (String) mp.get("value");
      newMap.put(key, value);
    }
    
    // 从session中取出数据权限Map
    String dataPermissions = "";
    UserSession userSession = SessionManager.getUserSession();
    Map sessionData = userSession.getSessionData();
    Map<String, IFilter> dataAcl = (Map<String, IFilter>) sessionData.get("dataACL");
    Set<String> key = dataAcl.keySet();
    for (Iterator<String> it = key.iterator(); it.hasNext();) {
        String s = it.next();
        if (ProjectConstants.PROJECT_RESULTS_DATA_PERMISSIONS.equals(s)) {
          IFilter filter = dataAcl.get(s);
          dataPermissions = filter.getString();
          break;
        }
    }
    // query中的header
    String header = query.getHeader();
    // 分页
    PageInfo pageInfo = conditions.getPageinfo();
    Sort st = conditions.getSortinfo();
    String sort = st.getString();
    //String sort = conditions.getSortinfo().getVariableName();
    pageInfo.getCurrentPage();
    // 起始页
    int start = pageInfo.getStartRowPosition();
    // 当前页查询总数
    int totalSize = pageInfo.getRecordsPerPage();
    // 获得当期条件查询记录总数
    pageInfo.setTotalRecords(dao.getToDoTaskCount(newMap, dataPermissions));
    // 结果集
    List<ToDoByModuleDTO> list = dao.getToDoTaskList(newMap, start, totalSize, sort, dataPermissions);

    // 设置query信息
    map.put("pageinfo", pageInfo);
    map.put("header", header);
    map.put("data", list);
    map.put("StatisticsInfo", "");

    if (conditions.getSortinfo() == null && query.getSort() != null) {
      map.put("sort", query.getSort());
    }
    map.put("sort", conditions.getSortinfo());
    return map;    
  }
  
  public Map<String, Object> getDoneTasks(QueryConditions conditions) {  
    ToDoByModuleDAO dao = (ToDoByModuleDAO) SpringHelper.getBean(ToDoByModuleDAO.class.getName());
    PMSQuery query = QueryDefinition.getQueryById(conditions.getQueryId());
    Map<String, Object> map = new HashMap<String, Object>();

    Map newMap = new HashMap();
    // 以下是query中配置的可用来查询的column的值
    List<Map<String, Object>> values = conditions.getConditions();
    for (Map<String, Object> mp : values) {
      String key = (String) mp.get("key");
      String value = (String) mp.get("value");
      newMap.put(key, value);
    }
    
    // 从session中取出数据权限Map
    String dataPermissions = "";
    UserSession userSession = SessionManager.getUserSession();
    Map sessionData = userSession.getSessionData();
    Map<String, IFilter> dataAcl = (Map<String, IFilter>) sessionData.get("dataACL");
    Set<String> key = dataAcl.keySet();
    for (Iterator<String> it = key.iterator(); it.hasNext();) {
        String s = it.next();
        if (ProjectConstants.PROJECT_RESULTS_DATA_PERMISSIONS.equals(s)) {
          IFilter filter = dataAcl.get(s);
          dataPermissions = filter.getString();
          break;
        }
    }
    // query中的header
    String header = query.getHeader();
    // 分页
    PageInfo pageInfo = conditions.getPageinfo();
    Sort st = conditions.getSortinfo();
    String sort = st.getString();
    //String sort = conditions.getSortinfo().getVariableName();
    pageInfo.getCurrentPage();
    // 起始页
    int start = pageInfo.getStartRowPosition();
    // 当前页查询总数
    int totalSize = pageInfo.getRecordsPerPage();
    // 获得当期条件查询记录总数
    pageInfo.setTotalRecords(dao.getDoneTaskCount(newMap, dataPermissions));
    // 结果集
    List<ToDoByModuleDTO> list = dao.getDoneTaskList(newMap, start, totalSize, sort, dataPermissions);

    // 设置query信息
    map.put("pageinfo", pageInfo);
    map.put("header", header);
    map.put("data", list);
    map.put("StatisticsInfo", "");

    if (conditions.getSortinfo() == null && query.getSort() != null) {
      map.put("sort", query.getSort());
    }
    map.put("sort", conditions.getSortinfo());
    return map;    
  } 
  
  public Map<String, Object> getFinishedTasks(QueryConditions conditions) {  
    ToDoByModuleDAO dao = (ToDoByModuleDAO) SpringHelper.getBean(ToDoByModuleDAO.class.getName());
    PMSQuery query = QueryDefinition.getQueryById(conditions.getQueryId());
    Map<String, Object> map = new HashMap<String, Object>();

    Map newMap = new HashMap();
    // 以下是query中配置的可用来查询的column的值
    List<Map<String, Object>> values = conditions.getConditions();
    for (Map<String, Object> mp : values) {
      String key = (String) mp.get("key");
      String value = (String) mp.get("value");
      newMap.put(key, value);
    }
    
    // 从session中取出数据权限Map
    String dataPermissions = "";
    UserSession userSession = SessionManager.getUserSession();
    Map sessionData = userSession.getSessionData();
    Map<String, IFilter> dataAcl = (Map<String, IFilter>) sessionData.get("dataACL");
    Set<String> key = dataAcl.keySet();
    for (Iterator<String> it = key.iterator(); it.hasNext();) {
        String s = it.next();
        if (ProjectConstants.PROJECT_RESULTS_DATA_PERMISSIONS.equals(s)) {
          IFilter filter = dataAcl.get(s);
          dataPermissions = filter.getString();
          break;
        }
    }
    // query中的header
    String header = query.getHeader();
    // 分页
    PageInfo pageInfo = conditions.getPageinfo();
    Sort st = conditions.getSortinfo();
    String sort = st.getString();
    //String sort = conditions.getSortinfo().getVariableName();
    pageInfo.getCurrentPage();
    // 起始页
    int start = pageInfo.getStartRowPosition();
    // 当前页查询总数
    int totalSize = pageInfo.getRecordsPerPage();
    // 获得当期条件查询记录总数
    pageInfo.setTotalRecords(dao.getFinishedTaskCount(newMap, dataPermissions));
    // 结果集
    List<ToDoByModuleDTO> list = dao.getFinishedTaskList(newMap, start, totalSize, sort, dataPermissions);

    // 设置query信息
    map.put("pageinfo", pageInfo);
    map.put("header", header);
    map.put("data", list);
    map.put("StatisticsInfo", "");

    if (conditions.getSortinfo() == null && query.getSort() != null) {
      map.put("sort", query.getSort());
    }
    map.put("sort", conditions.getSortinfo());
    return map;    
  }  

}
