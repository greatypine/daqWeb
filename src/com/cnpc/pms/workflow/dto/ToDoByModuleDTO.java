package com.cnpc.pms.workflow.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import com.cnpc.pms.base.dto.PMSDTO;

public class ToDoByModuleDTO extends PMSDTO {
  
  private String id ;
  
  //组织机构ID
  private String orgId;


  
  /*
   * 业务模块名称
   */
  private String moduleName;

  /*
   * 业务模块id
   */
  private Long moduleId;

  /*
   * 业务类型名称
   */
  private String moduleType;

  /*
   * 待办条数
   */
  private Integer docount;

  /*
   * 待办跳转路径
   */
  private String moduleUrl;
  
  /*
   * 待办/已办时间
   */
  private Date dotime;
  
  private Long userId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrgId() {
    return orgId;
  }

  public void setOrgId(String orgId) {
    this.orgId = orgId;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }

  public String getModuleType() {
    return moduleType;
  }

  public void setModuleType(String moduleType) {
    this.moduleType = moduleType;
  }

  public Integer getDocount() {
    return docount;
  }

  public void setDocount(Integer docount) {
    this.docount = docount;
  }

  public String getModuleUrl() {
    return moduleUrl;
  }

  public void setModuleUrl(String moduleUrl) {
    this.moduleUrl = moduleUrl;
  }

  public Date getDotime() {
    return dotime;
  }

  public void setDotime(Date dotime) {
    this.dotime = dotime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
  
}