package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.DoneByModule;
import com.cnpc.pms.workflow.entity.ToDoByModule;

public interface DoneByModuleManager extends IManager{
    public List<DoneByModule> getDoneByModuleByUserId();
}
