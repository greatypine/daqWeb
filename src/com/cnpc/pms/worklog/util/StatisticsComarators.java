package com.cnpc.pms.worklog.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cnpc.pms.worklog.dto.WorkLogStatByBranchOrgDTO;

public class StatisticsComarators implements Comparator<Object>{

	
	public void getComarators(List<WorkLogStatByBranchOrgDTO> list,String sort){
		String str=sort.substring(sort.indexOf(".")+1, sort.indexOf(" "));
		String orderStr=sort.substring(sort.indexOf(" ")+1, sort.length());
		System.out.println(orderStr);
		char[] array = str.toCharArray();  
		array[0] -= 32;  
		System.out.println(String.valueOf(array));  
		String sortStr=String.valueOf(array);
		
		if(sortStr.equals("Id")){
			
		}else if(sortStr.equals("Name")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					return arg0.getName().compareTo(arg1.getName());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("RecordState")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					return arg0.getRecordState().compareTo(arg1.getRecordState());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("OuttimeState")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					return arg0.getOuttimeState().compareTo(arg1.getOuttimeState());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("WorkdayState")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					return arg0.getWorkdayState().compareTo(arg1.getWorkdayState());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("CommitPercenter")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					Double copareFirst=Double.valueOf(arg0.getCommitPercenter().substring(0, arg0.getCommitPercenter().length()-1));
					Double compareSecond=Double.valueOf(arg1.getCommitPercenter().substring(0, arg1.getCommitPercenter().length()-1));
					return copareFirst.compareTo(compareSecond);
					//return arg0.getCommitPercenter().compareTo(arg1.getCommitPercenter());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("OnTimePercenter")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					Double copareFirst=0.0;
					Double compareSecond=0.0;
					if(arg0.getOuttimeState()!=null&&arg1.getOnTimePercenter()!=null){
						copareFirst=Double.valueOf(arg0.getOnTimePercenter().substring(0, arg0.getOnTimePercenter().length()-1));
						compareSecond=Double.valueOf(arg1.getOnTimePercenter().substring(0, arg1.getOnTimePercenter().length()-1));
					}else if(arg0.getOuttimeState()==null&&arg1.getOnTimePercenter()!=null){
						compareSecond=Double.valueOf(arg1.getOnTimePercenter().substring(0, arg1.getOnTimePercenter().length()-1));
					}else if(arg0.getOuttimeState()!=null&&arg1.getOnTimePercenter()==null){
						copareFirst=Double.valueOf(arg0.getOnTimePercenter().substring(0, arg0.getOnTimePercenter().length()-1));
					}else{
						
					}
					return copareFirst.compareTo(compareSecond);

				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else if(sortStr.equals("Hours")){
			Collections.sort(list, new Comparator<WorkLogStatByBranchOrgDTO>() {
				public int compare(WorkLogStatByBranchOrgDTO arg0, WorkLogStatByBranchOrgDTO arg1) {
					return arg0.getHours().compareTo(arg1.getHours());
				}
			});
			if(orderStr.equals("DESC")){//降序
				this.orderByDesc(list);
			}
		}else{
			
		}
	}

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	private void orderByDesc(List<WorkLogStatByBranchOrgDTO> list){
		List<WorkLogStatByBranchOrgDTO> listTemp =new ArrayList<WorkLogStatByBranchOrgDTO>();
		for(WorkLogStatByBranchOrgDTO w:list){//将升序排列的list2的数据暂时储存到listTemp中
			listTemp.add(w);
		}
		list.removeAll(list);//删除list里面所有的数据
		int i;
		for(i=listTemp.size()-1;i>=0;i--){//将listTemp倒叙插入list
			list.add(listTemp.get(i));
		}
	}
}
