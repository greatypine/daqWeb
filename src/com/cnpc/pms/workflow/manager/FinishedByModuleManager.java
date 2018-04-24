package com.cnpc.pms.workflow.manager;

import java.util.List;

import com.cnpc.pms.base.manager.IManager;
import com.cnpc.pms.workflow.entity.DoneByModule;
import com.cnpc.pms.workflow.entity.FinishedByModule;
import com.cnpc.pms.workflow.entity.ToDoByModule;

public interface FinishedByModuleManager extends IManager{
    public List<FinishedByModule> getFinishedByModuleByUserId();
}
