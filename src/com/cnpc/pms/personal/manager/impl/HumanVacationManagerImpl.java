package com.cnpc.pms.personal.manager.impl;

import java.util.List;
import java.util.Map;

import com.cnpc.pms.base.paging.FSP;
import com.cnpc.pms.base.paging.FilterFactory;
import com.cnpc.pms.base.paging.IFilter;
import com.cnpc.pms.base.paging.ISort;
import com.cnpc.pms.base.paging.SortFactory;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.base.query.json.QueryConditions;
import com.cnpc.pms.base.util.SpringHelper;
import com.cnpc.pms.bizbase.common.manager.BizBaseCommonManager;
import com.cnpc.pms.bizbase.rbac.usermanage.manager.UserManager;
import com.cnpc.pms.personal.entity.HumanVacation;
import com.cnpc.pms.personal.manager.HumanVacationManager;

@SuppressWarnings("all")
public class HumanVacationManagerImpl extends BizBaseCommonManager implements HumanVacationManager {
    
    /**
     * 查询列表 
     */
    @Override
    public Map<String, Object> queryMyProcessList(QueryConditions condition) {
    	Map<String,Object> returnMap = new java.util.HashMap<String, Object>();
		PageInfo pageInfo = condition.getPageinfo();
		String employee_name = null;
		for(Map<String, Object> map : condition.getConditions()){
			if("employee_name".equals(map.get("key"))&&map.get("value")!=null){//查询条件
				employee_name = map.get("value").toString();
			}
		}
		List<?> lst_data = null;
		FSP fsp = new FSP();
		fsp.setSort(SortFactory.createSort("id", ISort.DESC));
		StringBuffer sbfCondition = new StringBuffer(); 
		
		//这里根据当前登录人 过滤 显示所能看到的数据 
		sbfCondition.append(" 1=1 ");
		
		//根据登录角色过滤显示
		UserManager userManager = (UserManager) SpringHelper.getBean("userManager");
		String userGroupCode = userManager.getCurrentUserDTO().getUsergroup().getCode();
		
		if(employee_name!=null&&employee_name.length()>0) {
			sbfCondition.append(" and employee_name like '%"+employee_name+"%'");	

		}

		
		/*if(userGroupCode!=null&&userGroupCode.length()>0) {
			if(userGroupCode.equals("GAX")) {
				sbfCondition.append(" and employee_no='"+userManager.getCurrentUserDTO().getEmployeeId()+"'");	
			}
			if(userGroupCode.equals("DZ")) {
				sbfCondition.append(" and store_id ="+userManager.getCurrentUserDTO().getStore_id());
			}
		}else {
			sbfCondition.append(" and 1=1 ");
		}*/
		
		
		/*if(vacation_type!=null&&vacation_type.length()>0) {
			sbfCondition.append(" and vacation_type = '"+vacation_type+"' ");
		}*/
		
		IFilter iFilter =FilterFactory.getSimpleFilter(sbfCondition.toString());
		fsp.setPage(pageInfo);
		fsp.setUserFilter(iFilter);
		lst_data = this.getList(fsp);
		returnMap.put("pageinfo", pageInfo);
		returnMap.put("header", "");
		returnMap.put("data", lst_data);
		return returnMap;
	}
	    
	    
    
    
    
    @Override
	public HumanVacation queryHumanVacationInfo(Long id) {
		HumanVacation humanVacation = (HumanVacation) this.getObject(id);
		String processInstanceId=humanVacation.getProcessInstanceId();
		/*List<Comment> comments = findCommentByProcessId(processInstanceId);
		humanVacation.setProcesslog(comments);*/
		return humanVacation;
	}
    
	    
}
