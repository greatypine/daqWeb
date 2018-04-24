package com.cnpc.pms.workflow.dto;

import com.cnpc.pms.base.dto.PMSDTO;

public class WF1FlowInfoParam extends PMSDTO {
	private String rTableName;
	private Long rId;
	public String getrTableName() {
		return rTableName;
	}
	public void setrTableName(String rTableName) {
		this.rTableName = rTableName;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
}
