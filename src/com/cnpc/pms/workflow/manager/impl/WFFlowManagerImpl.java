package com.cnpc.pms.workflow.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;

import com.cnpc.pms.base.manager.impl.BaseManagerImpl;
import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.impl.Sort;
import com.cnpc.pms.workflow.entity.WFFlow;
import com.cnpc.pms.workflow.entity.WFFlowOrg;
import com.cnpc.pms.workflow.entity.WFFlowStep;
import com.cnpc.pms.workflow.entity.WFFlowStepToPos;
import com.cnpc.pms.workflow.entity.WFFlowTransition;
import com.cnpc.pms.workflow.entity.WFFlowVariable;
import com.cnpc.pms.workflow.manager.WFFlowManager;

public class WFFlowManagerImpl extends BaseManagerImpl implements WFFlowManager {
	/**
	 * 根据id来检索工作流模版的定义对象
	 */
	public WFFlow queryFlow(Long id) {
		WFFlow wFFlow = (WFFlow) super.getObject(id);
		return wFFlow;
	}

	@SuppressWarnings("unchecked")
	/**
	 * 根据ModuleId来检索所有的相关工作流模版定义,<br>
	 * 仅检索状态为1的工作流模版定义<br>
	 */
	public List<WFFlow> getFlowListByModuleId(Long moduleId) {
		// 构建FSP对象,输入检索条件
		FSP fsp = new FSP();
		fsp
				.setUserFilter(FilterFactory.getSimpleFilter("moduleId",
						moduleId).appendAnd(
						FilterFactory.getSimpleFilter("state", new Long(1))));
		// 设置排序条件
		fsp.setSort(new Sort("flowVersion", Sort.DESC).appendSort(new Sort(
				"createTime", Sort.DESC)));
		// 检索数据,获取列表
		List<WFFlow> flowList = (List<WFFlow>) this.getObjects(fsp);
		// 返回列表
		return flowList;
	}

	public WFFlow addFlow(WFFlow obj) {
		WFFlow wFFlow = obj;
		super.saveObject(obj);
		return wFFlow;
	}

	public Boolean deleteFlow(WFFlow obj) {
		super.removeObjectById(obj.getId());
		return true;
	}

	public WFFlow saveFlow(WFFlow obj) {
		WFFlow dbObj = null;
		System.out.println(obj.getId());
		dbObj = (WFFlow) super.getObject(obj.getId());
		System.out.println(dbObj.getId());
		if (dbObj == null) {
			// System.out.println(dbObj.getId());
			dbObj = obj;
		} else {
			System.out.println(dbObj.getId());
			BeanUtils.copyProperties(obj, dbObj, new String[] { "id",
					"version", "varSet", "transitionSet", "varStep",
					"varFlowToOrg" });

		}

		super.saveObject(dbObj);
		return obj;
	}

	public String validation(Long id) {
		WFFlow wfFlow = (WFFlow) super.getObject(id);
		Set<WFFlowVariable> varSet = wfFlow.getVarSet();
		Set<WFFlowTransition> transitionSet = wfFlow.getTransitionSet();
		Set<WFFlowStep> varStep = wfFlow.getVarStep();
		Set<WFFlowOrg> varFlowToOrg = wfFlow.getVarFlowToOrg();
		String Msg = "";
		Long beginId = 0l;
		Long endId = 0l;
		Long transId = 0l;// 用于记录为正确配置的流程转换的ID;
		List<Long> list1 = new ArrayList();
		List<Long> list2 = new ArrayList();
		List<Long> list3 = new ArrayList();
		List<String> list4 = new ArrayList();
		List<String> list5 = new ArrayList();
		int i = 0, j = 0, k = 0;// i用于记录开始步骤的数量，j用于记录执行步骤的数量，k用于记录结束步骤的数量
		// a用于记录是否有可用部门，0没有1有。
		// b和c分别用于记录流程定义的参数的code和name是否重复，0表示没有定义，1表示没有重复，大于1表示重复
		// d用于记录有多少个步骤有对应的岗位，当每个步骤都有对应的岗位时，将e置为1
		// f用于记录流程转换中的BeginId或者EndId是否为空，若流程转换中的BeginId或者EndId为空，则将f置为1。0表示都不为空
		// g用于记录是否所有的流程步骤都配置了转换，若都配置了则将g置为1。
		// h用于记录开始步骤出现在BeginId里面的次数，l用于记录结束步骤在EndId里面的次数。
		// m用于记录执行步骤是否既在BeginId里面又在EndId里面，若是则将m置为1.
		// n用于记录转换条件是否在定义的参数中
		// p用于记录是否所有的流程转换都合法
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0, l = 0, m = 0, n = 0, p = 0;

		// 开始步骤只能有一个，执行可以是多个，结束步骤只能有一个。
		for (WFFlowStep wfStep : varStep) {
			// System.out.println(wfStep.getStepType());
			if (wfStep.getStepType().equals(new Long(0))) {// 0代表开始步骤
				i++;
			}
			if (wfStep.getStepType().equals(new Long(1))) {// 1代表执行步骤
				j++;
			}
			if (wfStep.getStepType().equals(new Long(2))) {// 2代表结束步骤
				k++;
			}
		}
		// 定义的参数的Code和Name不能重复
		for (WFFlowVariable wfArgs : varSet) {
			String code = wfArgs.getVariableCode();
			String name = wfArgs.getVariableName();
			for (WFFlowVariable wfArgs1 : varSet) {
				// System.out.println(wfArgs1.getVariableCode());
				// System.out.println(wfArgs1.getVariableName());
				if (wfArgs1.getVariableCode() == code) {
					b++;
				}
				if (wfArgs1.getVariableName() == name) {
					c++;
				}
			}
			list4.add(wfArgs.getVariableCode());
		}
		if (b == varSet.size() && c == varSet.size()) {
			b = 1;
			c = 1;
		}
		// 可用部门必须有
		if (varFlowToOrg.size() > 0) {
			a = 1;
		}
		// 每个步骤必须有对应的岗位
		for (WFFlowStep wfStep : varStep) {
			Set<WFFlowStepToPos> varStepToPos = wfStep.getVarStepToPos();
			if (varStepToPos.size() > 0) {
				d++;
			}
		}
		if (d == varStep.size()) {
			e = 1;
		}
		// 流程转换中BeginId和EndId不能有空,若流程转换中的BeginId或者EndId为空，则将f置为1。0表示都不为空
		for (WFFlowTransition wfTransition : transitionSet) {
			if (wfTransition.getBeginId() == null
					|| wfTransition.getEndId() == null) {
				f = 1;
				break;
			}
		}
		// 是否为所有的流程步骤都配置了转换,若都配置了,则将g置为1.
		for (WFFlowStep wfStep1 : varStep) {
			// System.out.println(varStep.size());
			Set<WFFlowTransition> varFlowTransition = wfStep1
					.getVarFlowTransition();
			// System.out.println(wfStep1.getId());
			if (varFlowTransition.size() > 0) {
				g++;
			}
		}
		if (g == (varStep.size() - 1)) {// 结束节点不配置转换所以是varStep.size()-1。
			g = 1;
		}
		// 获取开始步骤和结束的ID,判断是否开始步骤的id只出现在BeginId里面，结束步骤的id只出现在EndId里面，且只出现一次
		for (WFFlowStep wfStep2 : varStep) {
			if (wfStep2.getStepType().equals(new Long(0))) {// 0是开始步骤
				beginId = wfStep2.getId();
			}
			if (wfStep2.getStepType().equals(new Long(1))) {// 1是执行步骤
				list1.add(wfStep2.getId());
			}
			if (wfStep2.getStepType().equals(new Long(2))) {// 2是结束步骤
				endId = wfStep2.getId();
			}
		}
		// System.out.println(beginId);
		// System.out.println(endId);
		for (WFFlowTransition wfTransition1 : transitionSet) {
			list2.add(wfTransition1.getBeginId());
			list3.add(wfTransition1.getEndId());
			// System.out.println(beginId);
			// System.out.println(wfTransition1.getBeginId());
			// System.out.println(endId);
			// System.out.println(wfTransition1.getEndId());
			if (wfTransition1.getBeginId().equals(new Long(beginId))) {
				h++;
			}
			if (wfTransition1.getEndId().equals(new Long(endId))) {
				l++;
			}
		}
		// list1里面存的是执行步骤的id,list2里面存的是流程转换里面的BeginId,list3里面存放的是流程转换里面的EndId,执行步骤的Id必须既包含在BeginId里面又包含在EndId里面
		for (Long obj : list1) {
			if (list2.contains(obj) && list3.contains(obj)) {
				m = 1;
				break;
			}
		}
		// 表示流程步骤包含一个开始步骤，至少一个执行步骤和一个结束步骤
		if (i != 1 || j < 1 || k != 1) {
			if (i > 1 && j >= 1 && k == 1) {
				Msg = "流程步骤错误，存在多个开始步骤";
				return Msg;
			} else if (i < 1 && j >= 1 && k == 1) {
				Msg = "流程步骤错误，没有开始步骤";
				return Msg;
			} else if (i == 1 && j < 1 && k == 1) {
				Msg = "流程步骤错误，没有执行步骤";
				return Msg;
			} else if (i == 1 && j >= 1 && k > 1) {
				Msg = "流程步骤错误，存在多个结束步骤";
				return Msg;
			} else if (i == 1 && j >= 1 && k < 1) {
				Msg = "流程步骤错误，没有结束步骤";
				return Msg;
			} else if (i < 1 && j >= 1 && k < 1) {
				Msg = "流程步骤错误，没有开始和结束步骤";
				return Msg;
			} else if (i > 1 && j >= 1 && k > 1) {
				Msg = "流程步骤错误，存在多个开始和结束步骤";
				return Msg;
			}
		}
		if (a != 1) {
			Msg = "该流程无可用部门";
			return Msg;
		}
		if (b != 1 || c != 1) {
			if (b == 0 && c == 0) {
				Msg = "没有定义参数";
				return Msg;
			} else {
				Msg = "定义的参数的Code或Name有重复";
				return Msg;
			}
		}
		if (e != 1) {
			Msg = "并非所有的步骤都有对应的岗位";
			return Msg;
		}
		if (f != 0) {
			Msg = "流程转换的BeginId或者EndId存在null值";
			return Msg;
		}
		if (g != 1) {
			Msg = "某些流程步骤未配置转换";
			return Msg;
		}
		if (h != 1 || l != 1 || m != 1) {
			Msg = "流程转换时存在BeginId或者EndId配置不正确";
			return Msg;
		}
		for (WFFlowTransition wfTransition3 : transitionSet) {
			n = 0;
			if (wfTransition3.getIsDefault() == 0) {// 该转换为默认转换不用考虑转换条件
				// System.out.println(wfTransition3.getId());
				Msg = "Id为" + wfTransition3.getId() + "的流程转换配置正确";
				p++;
			} else {// 该流程转换为不默认转换，需要考虑转换条件是否在定义的参数里面
				String str = wfTransition3.getCondition();
				Pattern pat = Pattern.compile("@(.*?)[=><]");
				Matcher mat = pat.matcher(str);
				while (mat.find()) {
					// System.out.println(mat.group(1));
					list5.add(mat.group(1));
				}
				for (String str1 : list5) {
					if (list4.contains(str1)) {
						n++;
					}
				}
				// System.out.println(wfTransition3.getId());
				if (n > 0) {
					Msg = "Id为" + wfTransition3.getId() + "的流程转换配置正确";
					p++;
				} else {
					transId = wfTransition3.getId();
					Msg = "Id为" + wfTransition3.getId() + "的流程转换条件不在定义的参数中";
					break;
				}
			}
		}
		if (p == transitionSet.size()) {
			Msg = "所有的流程转换都配置正确";
			// System.out.println(Msg);
			return Msg;
		} else {
			Msg = "存在Id为" + transId + "的流程转换配置不正确";
			return Msg;
		}
	}

	public Boolean deleteFlowById(Long id) {
		// System.out.println(id);
		super.removeObjectById(id);
		return null;
	}

	public Set<WFFlowOrg> getWFFlowOrgListById(Long id) {
		// TODO Auto-generated method stub
		WFFlow wfFlow = (WFFlow) super.getObject(id);
		Set<WFFlowOrg> sWf = wfFlow.getVarFlowToOrg();
		return sWf;
	}

	// public String saveWorkFlow(WorkFlowDTO workFlowDTO) {
	// //新建流程模板
	// WFFlow wfFlow = new WFFlow();
	// Flow flow = workFlowDTO.getFlow();
	// wfFlow.setFlowName(flow.getName());
	// this.saveObject(wfFlow);
	//		
	// //流程步骤
	// List<State> states = workFlowDTO.getStates();
	// WFFlowStepManager stepManager =
	// (WFFlowStepManager)SpringHelper.getBean("WFFlowStepManager");
	// Map<String, Long> stepMap = new HashMap<String, Long>();
	// for(State st:states) {
	// WFFlowStep step = new WFFlowStep();
	// step.setFlowId(wfFlow.getId());
	// //步骤类型
	// String type = st.getType();
	// if("start".equals(type)) {
	// step.setStepType(0L);
	// }else if("task".equals(type)) {
	// step.setStepType(1L);
	// }else if("end".equals(type)) {
	// step.setStepType(2L);
	// }
	// step.setStepName(st.getStatename());
	// stepManager.saveObject(step);
	// stepMap.put(st.getId(), step.getId());
	// }
	// //流程转换
	// WFFlowTransitionManager transitionManager =
	// (WFFlowTransitionManager)SpringHelper.getBean("WFFlowTransitionManager");
	// List<Path> paths = workFlowDTO.getPaths();
	// for(Path pa:paths) {
	// WFFlowTransition transition = new WFFlowTransition();
	// transition.setIsDefault(1L);//暂定都为默认转换
	// transition.setBeginId(stepMap.get(pa.getFrom()));
	// transition.setEndId(stepMap.get(pa.getTo()));
	// transition.setCondition(pa.getCondition());
	// transitionManager.saveObject(transition);
	// }
	//		
	// return null;
	// }
}
