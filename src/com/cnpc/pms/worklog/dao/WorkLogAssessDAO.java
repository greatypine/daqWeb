package com.cnpc.pms.worklog.dao;

import java.util.List;

import com.cnpc.pms.base.dao.IDAO;
import com.cnpc.pms.base.paging.impl.PageInfo;
import com.cnpc.pms.worklog.dto.WorkLogAssessSelectDto;
import com.cnpc.pms.worklog.dto.WorkLogAssessStisticDTO;
import com.cnpc.pms.worklog.entity.WorkLogAssess;

public interface WorkLogAssessDAO extends IDAO{
	//完成抽取记录
	public Long completeRandomSelect(WorkLogAssessSelectDto dto);
	//打分时新增或修改WorkLogAssess
	public void saveWorkLogAssess(WorkLogAssess obj);
	//统计打分情况
	public List<WorkLogAssessStisticDTO> getWorkLogAssessStistic(PageInfo pageInfo,String stype,String d1,String d2,Long orgId,String nm);
	//抽取和查询
	public List<WorkLogAssessStisticDTO> getWorkLogAssessSelectAndQuery(PageInfo pageInfo,String stype,String d1,String d2,String orgId,String nm);
	//按维度统计打分情况
	public List<WorkLogAssessStisticDTO> getWorkLogAssessStisticByWd(PageInfo pageInfo,String stype,String d1,String d2,Long orgId,String nm,String wd);
}
