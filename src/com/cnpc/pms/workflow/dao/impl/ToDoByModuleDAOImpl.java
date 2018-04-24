package com.cnpc.pms.workflow.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.dao.hibernate.BaseDAOHibernate;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.bizbase.util.DictUtil;
import com.cnpc.pms.workflow.dao.ToDoByModuleDAO;
import com.cnpc.pms.workflow.dto.ToDoByModuleDTO;

public class ToDoByModuleDAOImpl extends BaseDAOHibernate implements  ToDoByModuleDAO {
  
  public List<ToDoByModuleDTO> getToDoTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions) {
    String sql = "select * from (select '1' as orgid,(m.id + t.userid) as id,\n" +
            "       count(*) as docount,\n" + 
            "       d.dicttext as moduleName,\n" + 
            "       m.id as moduleid,\n" + 
            "       m.name as moduleType,\n" + 
            "       m.urlstr as moduleUrl,\n" + 
            "       max(t.laststepdate) as dotime,\n" + 
            "       t.userid as userid\n" + 
            "  from view_wf_todoview_alltype t\n" +
            "  inner join wf_module m on t.moduleid = m.id "; 
    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
      sql+=" and (t.orgid in("+orgIds+") or t.orgid is null)";
    }
    
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId;    
    sql+= "  inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
          "  group by m.id, d.dicttext, m.name, m.urlstr, t.userid "+ 
          " ) t1";
    sort = sort.replaceAll("_default_", "t1");
    sql += " order by " + sort;
    
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    
    sqlQuery.setFirstResult(startResult);
    sqlQuery.setMaxResults(maxResult);
    sqlQuery.addScalar("orgid", Hibernate.STRING);
    sqlQuery.addScalar("id", Hibernate.STRING);
    sqlQuery.addScalar("docount", Hibernate.INTEGER);
    sqlQuery.addScalar("moduleName", Hibernate.STRING);
    sqlQuery.addScalar("moduleid", Hibernate.LONG);
    sqlQuery.addScalar("moduleType", Hibernate.STRING);
    sqlQuery.addScalar("moduleUrl", Hibernate.STRING);
    sqlQuery.addScalar("dotime", Hibernate.DATE);
    sqlQuery.addScalar("userid", Hibernate.LONG); 
    
    List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    ArrayList<ToDoByModuleDTO> dtoList=new ArrayList<ToDoByModuleDTO>();
    if (list != null && list.size() != 0) {
      for (int i = 0; i < list.size(); i++) {
        Map doto = list.get(i);
        ToDoByModuleDTO tmpDto=new ToDoByModuleDTO();
        tmpDto.setId((String)doto.get("id"));
        tmpDto.setOrgId((String)doto.get("orgid"));
        tmpDto.setDocount((Integer)doto.get("docount"));
        tmpDto.setModuleId((Long)doto.get("moduleid"));
        tmpDto.setModuleName((String)doto.get("moduleName"));
        tmpDto.setModuleType((String)doto.get("moduleType"));
        tmpDto.setModuleUrl((String)doto.get("moduleUrl"));
        tmpDto.setDotime((Date)doto.get("dotime"));
        tmpDto.setUserId((Long)doto.get("userid"));
        dtoList.add(tmpDto);
      }
    }    
    return dtoList;
  }  

  public int getToDoTaskCount(Map<String,Object> findMap, String dataPermissions) {
    String sql = "select count(1) rowcount from (select '1' as orgid,(m.id + t.userid) as id,\n" +
        "       count(*) as docount,\n" + 
        "       d.dicttext as moduleName,\n" + 
        "       m.id as moduleid,\n" + 
        "       m.name as moduleType,\n" + 
        "       m.urlstr as moduleUrl,\n" + 
        "       max(t.laststepdate) as dotime,\n" + 
        "       t.userid as userid\n" + 
        "  from view_wf_todoview_alltype t\n" +
        "  inner join wf_module m on t.moduleid = m.id "; 

    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
      sql+=" and (t.orgid in("+orgIds+") or t.orgid is null)";
    }
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId;    
    sql+= "  inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
          "  group by m.id, d.dicttext, m.name, m.urlstr, t.userid ";
    sql+=") ttt";
    
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    sqlQuery.addScalar("rowcount", Hibernate.INTEGER);
    Integer dataRows = (Integer) sqlQuery.uniqueResult();
    return dataRows.intValue();
  }
  
  public List<ToDoByModuleDTO> getDoneTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions) {
    String sql= "select * from (select '1' as orgid, (m.id + t.userid) as id,\n" +
                "       count(*) as docount,\n" + 
                "       d.dicttext as moduleName,\n" + 
                "       m.id as moduleid,\n" + 
                "       m.name as moduleType,\n" + 
                "       m.urlstr2 as moduleUrl,\n" + 
                "       max(t.opertime) as dotime,\n" + 
                "       t.userid as userid\n" + 
                " from view_wf_doneview_alltype t\n" + 
                " inner join wf_module m on t.moduleid = m.id \n" ;
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId ;    
     
    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
      sql+=" and (t.orgid in("+orgIds+") or t.orgid is null)";
    }
    sql+= " inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
          " group by m.id, d.dicttext, m.name, m.urlstr2, t.userid ) tl";

    sort = sort.replaceAll("_default_", "tl");
    sql += " order by " + sort;
    
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    
    sqlQuery.setFirstResult(startResult);
    sqlQuery.setMaxResults(maxResult);
    sqlQuery.addScalar("orgid", Hibernate.STRING);
    sqlQuery.addScalar("id", Hibernate.STRING);
    sqlQuery.addScalar("docount", Hibernate.INTEGER);
    sqlQuery.addScalar("moduleName", Hibernate.STRING);
    sqlQuery.addScalar("moduleid", Hibernate.LONG);
    sqlQuery.addScalar("moduleType", Hibernate.STRING);
    sqlQuery.addScalar("moduleUrl", Hibernate.STRING);
    sqlQuery.addScalar("dotime", Hibernate.DATE);
    sqlQuery.addScalar("userid", Hibernate.LONG); 
    
    List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    ArrayList<ToDoByModuleDTO> dtoList=new ArrayList<ToDoByModuleDTO>();
    if (list != null && list.size() != 0) {
      for (int i = 0; i < list.size(); i++) {
        Map doto = list.get(i);
        ToDoByModuleDTO tmpDto=new ToDoByModuleDTO();
        tmpDto.setId((String)doto.get("id"));
        tmpDto.setOrgId((String)doto.get("orgid"));
        tmpDto.setDocount((Integer)doto.get("docount"));
        tmpDto.setModuleId((Long)doto.get("moduleid"));
        tmpDto.setModuleName((String)doto.get("moduleName"));
        tmpDto.setModuleType((String)doto.get("moduleType"));
        tmpDto.setModuleUrl((String)doto.get("moduleUrl"));
        tmpDto.setDotime((Date)doto.get("dotime"));
        tmpDto.setUserId((Long)doto.get("userid"));
        dtoList.add(tmpDto);
      }
    }    
    return dtoList;
  }  

  public int getDoneTaskCount(Map<String,Object> findMap, String dataPermissions) {
    String sql= "select count(1) as rowcount from (select '1' as orgid, (m.id + t.userid) as id,\n" +
        "       count(*) as docount,\n" + 
        "       d.dicttext as moduleName,\n" + 
        "       m.id as moduleid,\n" + 
        "       m.name as moduleType,\n" + 
        "       m.urlstr2 as moduleUrl,\n" + 
        "       max(t.opertime) as dotime,\n" + 
        "       t.userid as userid\n" + 
        " from view_wf_doneview_alltype t\n" + 
        " inner join wf_module m on t.moduleid = m.id \n" ;
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId;    
    
    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
    sql+=" and (t.orgid in ("+orgIds+") or t.orgid is null)";
    }
    sql+= " inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
      " group by m.id, d.dicttext, m.name, m.urlstr2, t.userid ) tl";
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    sqlQuery.addScalar("rowcount", Hibernate.INTEGER);
    Integer dataRows = (Integer) sqlQuery.uniqueResult();
    return dataRows.intValue();
  }  
  
  public List<ToDoByModuleDTO> getFinishedTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions) {
    String sql= "select * from (select '1' as orgid, (m.id + t.userid) as id,\n" +
                "       count(*) as docount,\n" + 
                "       d.dicttext as moduleName,\n" + 
                "       m.id as moduleid,\n" + 
                "       m.name as moduleType,\n" + 
                "       m.finishedurl as moduleUrl,\n" + 
                "       max(t.opertime) as dotime,\n" + 
                "       t.userid as userid\n" + 
                " from view_wf_doneview_alltype t\n" + 
                " inner join wf_module m on t.moduleid = m.id \n" ;
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId ; 
    sql+=" and t.state = 1 "; //此条件代表已完结
     
    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
      sql+=" and (t.orgid in ("+orgIds+") or t.orgid is null)";
    }
    sql+= " inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
          " group by m.id, d.dicttext, m.name, m.finishedurl, t.userid ) tl";

    sort = sort.replaceAll("_default_", "tl");
    sql += " order by " + sort;
    
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    
    sqlQuery.setFirstResult(startResult);
    sqlQuery.setMaxResults(maxResult);
    sqlQuery.addScalar("orgid", Hibernate.STRING);
    sqlQuery.addScalar("id", Hibernate.STRING);
    sqlQuery.addScalar("docount", Hibernate.INTEGER);
    sqlQuery.addScalar("moduleName", Hibernate.STRING);
    sqlQuery.addScalar("moduleid", Hibernate.LONG);
    sqlQuery.addScalar("moduleType", Hibernate.STRING);
    sqlQuery.addScalar("moduleUrl", Hibernate.STRING);
    sqlQuery.addScalar("dotime", Hibernate.DATE);
    sqlQuery.addScalar("userid", Hibernate.LONG); 
    
    List<Map> list = sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    ArrayList<ToDoByModuleDTO> dtoList=new ArrayList<ToDoByModuleDTO>();
    if (list != null && list.size() != 0) {
      for (int i = 0; i < list.size(); i++) {
        Map doto = list.get(i);
        ToDoByModuleDTO tmpDto=new ToDoByModuleDTO();
        tmpDto.setId((String)doto.get("id"));
        tmpDto.setOrgId((String)doto.get("orgid"));
        tmpDto.setDocount((Integer)doto.get("docount"));
        tmpDto.setModuleId((Long)doto.get("moduleid"));
        tmpDto.setModuleName((String)doto.get("moduleName"));
        tmpDto.setModuleType((String)doto.get("moduleType"));
        tmpDto.setModuleUrl((String)doto.get("moduleUrl"));
        tmpDto.setDotime((Date)doto.get("dotime"));
        tmpDto.setUserId((Long)doto.get("userid"));
        dtoList.add(tmpDto);
      }
    }    
    return dtoList;
  }  

  public int getFinishedTaskCount(Map<String,Object> findMap, String dataPermissions) {
    String sql= "select count(1) as rowcount from (select '1' as orgid, (m.id + t.userid) as id,\n" +
        "       count(*) as docount,\n" + 
        "       d.dicttext as moduleName,\n" + 
        "       m.id as moduleid,\n" + 
        "       m.name as moduleType,\n" + 
        "       m.finishedurl as moduleUrl,\n" + 
        "       max(t.opertime) as dotime,\n" + 
        "       t.userid as userid\n" + 
        " from view_wf_doneview_alltype t\n" + 
        " inner join wf_module m on t.moduleid = m.id \n" ;
    String userId=(String)findMap.get("userId");
    sql+=" and t.userid="+userId; 
    sql+=" and t.state = 1 "; //此条件代表已完结
    
    String orgIds=(String)findMap.get("orgIds");  
    if (StringUtils.isNotEmpty(orgIds)) {
    sql+=" and (t.orgid in ("+orgIds+") or t.orgid is null)";
    }
    sql+= " inner join dict_wf_module_type d on m.moduletype = d.dictcode\n" + 
      " group by m.id, d.dicttext, m.name, m.finishedurl, t.userid ) tl";
    Session session = this.getSession();    
    SQLQuery sqlQuery = session.createSQLQuery(sql);
    sqlQuery.addScalar("rowcount", Hibernate.INTEGER);
    Integer dataRows = (Integer) sqlQuery.uniqueResult();
    return dataRows.intValue();
  }    
  
}
