package com.cnpc.pms.workflow.manager;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.workflow.entity.ToDoByModule;

public interface ToDoByModuleManager extends IManager{
    public List<ToDoByModule> getToDoByModuleByUserId();
    public Map<String, Object> getToDoTasks(QueryConditions conditions);  
    public Map<String, Object> getDoneTasks(QueryConditions conditions);
    public Map<String, Object> getFinishedTasks(QueryConditions conditions);     
}
