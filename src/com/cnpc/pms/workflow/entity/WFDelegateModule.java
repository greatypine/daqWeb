package com.cnpc.pms.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.PMSAuditEntity;

@Entity
@Table(name = "WF_DelegateModule")

public class WFDelegateModule extends PMSAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097799302999188826L;
	@Column(name = "delegateId")
	private long delegateId;
//	@Column(name = "moduleId")
//	private long moduleId;

	//修改为ManyToOne关联方式,关联到WF_Module表
	@ManyToOne
	@JoinColumn(name="moduleId")
	private WFModule module;
	
	public WFModule getModule() {
		return module;
	}

	public void setModule(WFModule module) {
		this.module = module;
	}

	public long getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(long delegateId) {
		this.delegateId = delegateId;
	}

//	public long getModuleId() {
//		return moduleId;
//	}
//
//	public void setModuleId(long moduleId) {
//		this.moduleId = moduleId;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
