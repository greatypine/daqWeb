package com.cnpc.pms.workflow.manager.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.workflow.entity.WF1FlowMapping;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowInstance;
import com.cnpc.pms.workflow.entity.WFModule;
import com.cnpc.pms.workflow.manager.WF1FlowMappingManager;
import com.cnpc.pms.workflow.manager.WFFlowInstanceManager;
import com.cnpc.pms.workflow.manager.WFFlowManager;
import com.cnpc.pms.workflow.manager.WFModuleManager;
import com.cnpc.pms.workflow.util.WFManagerConst;
import com.cnpc.pms.workflow.util.WFManagerHelper;

public class WF1FlowMappingManagerImpl extends BaseManagerImpl implements
		WF1FlowMappingManager {

	/**
	 * 根据给定的表名和Id号来检索对应的数据
	 * @param rtable
	 * @param rId
	 * @return
	 */
	public WF1FlowMapping getWF1FlowMapping(String rTableName,Long rId){
		//Step1:构建过滤器
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("rTableName", rTableName)
				.appendAnd(FilterFactory.getSimpleFilter("rId", rId)));
		
		//Step2:检索数据,生成列表
		List<WF1FlowMapping> list = (List<WF1FlowMapping>)super.getList(fsp);
		if (list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 更新或者保存一个新的记录
	 * @param mapping
	 * @return
	 */
	public WF1FlowMapping saveWF1FlowMapping(WF1FlowMapping mapping){
		WF1FlowMapping m1=new WF1FlowMapping();
		if(mapping.getId()!=null && mapping.getId()>0){
			m1 = (WF1FlowMapping)super.getObject(mapping.getId());
		}
		m1.setrTableName(mapping.getrTableName());
		m1.setrId(mapping.getrId());
		m1.setModuleId(mapping.getModuleId());
		m1.setDeptId(mapping.getDeptId());
		m1.setSheetId(mapping.getSheetId());
		m1.setSheetName(mapping.getSheetName());
		m1.setFlowInstanceId(mapping.getFlowInstanceId());
		
		super.save(m1);
		return m1;
	}
	
	/**
	 * 检索数据
	 * @param id
	 * @return
	 */
	public WF1FlowMapping queryWF1FlowMapping(Long id){
		return (WF1FlowMapping)super.getObject(id);
	}
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	public Long deleteWF1FlowMapping(Long id){
		super.removeObjectById(id);
		return 0L;
	}
}
