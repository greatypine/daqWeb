package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.workflow.entity.WFFlowVariable;
import com.cnpc.pms.workflow.entity.WFInstanceVariable;
import com.cnpc.pms.workflow.manager.WFInstanceVariableManager;

public class WFInstanceVariableManagerImpl extends BaseManagerImpl implements
		WFInstanceVariableManager {

	public WFInstanceVariable getInstanceVarByCode(String code, Long flowInstanceId) {
		// TODO Auto-generated method stub
		WFInstanceVariable var = (WFInstanceVariable) this.getUniqueObject(FilterFactory
				.getSimpleFilter("variableCode='" + code
						+ "' and flowInstanceId=" + flowInstanceId));
		return var;
	}

	public WFInstanceVariable addInstanceVar(WFInstanceVariable var) {
		// TODO Auto-generated method stub
		this.saveObject(var);
		return var;
	}

	public void saveInstanceVarList(Long flowInstanceId,
			Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			WFInstanceVariable var = getInstanceVarByCode(entry.getKey(),
					flowInstanceId);
			if (var != null) {
				var.setValue(entry.getValue());
				this.saveObject(var);
			}
		}

	}

	/*
	 * @SuppressWarnings("unchecked") public Map<String, String>
	 * getInstanceVarListByFlowId(Long flowInstanceId) { Map<String, String>
	 * parMap = new HashMap<String, String>(); List<WFInstanceVar> varList =
	 * (List<WFInstanceVar>) this
	 * .getObjects(FilterFactory.getSimpleFilter("flowInstanceId",
	 * flowInstanceId)); for (WFInstanceVar var : varList) { if (null !=
	 * var.getValue()) parMap.put(var.getVariableCode(), var.getValue()); else
	 * parMap.put(var.getVariableCode(), var.getDefaultValue()); } return
	 * parMap; }
	 */

	public List<WFInstanceVariable> copyInstanceVar(List<WFFlowVariable> argList,
			Map<String, String> args, Long flowinstanceid) {
		List<WFInstanceVariable> list = new ArrayList<WFInstanceVariable>();
		for (WFFlowVariable arg : argList) {
			WFInstanceVariable instanceVar = new WFInstanceVariable();
			BeanUtils.copyProperties(arg, instanceVar, new String[] { "id",
					"version" });
			instanceVar.setFlowInstanceId(flowinstanceid);
			instanceVar.setVariableName(args.get(arg.getVariableCode()));
			instanceVar.setIsDel(0L);
			list.add(addInstanceVar(instanceVar));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<WFInstanceVariable> getListByInstanceId(Long flowInstanceId) {
		FSP fsp = new FSP();
		fsp.setUserFilter(FilterFactory.getSimpleFilter("flowInstanceId",
				flowInstanceId).appendAnd(
				FilterFactory.getSimpleFilter("isDel", new Long(0))));
		return (List<WFInstanceVariable>) this.getObjects(fsp);
	}
}
