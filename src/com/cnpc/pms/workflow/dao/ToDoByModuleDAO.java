package com.cnpc.pms.workflow.dao;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.workflow.dto.ToDoByModuleDTO;

public interface ToDoByModuleDAO extends IDAO {
  
  public List<ToDoByModuleDTO> getToDoTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions);  
  public int getToDoTaskCount(Map<String,Object> findMap, String dataPermissions);
  
  public List<ToDoByModuleDTO> getDoneTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions);  
  public int getDoneTaskCount(Map<String,Object> findMap, String dataPermissions);

  public List<ToDoByModuleDTO> getFinishedTaskList(Map<String,Object> findMap, int startResult, int maxResult, String sort, String dataPermissions);  
  public int getFinishedTaskCount(Map<String,Object> findMap, String dataPermissions);
  
}
