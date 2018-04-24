package com.cnpc.pms.worklog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cnpc.pms.base.entity.AlternativeDS;
import com.cnpc.pms.base.entity.IEntity;

@Entity
@Table(name = "view_worklog_bizbase_user")

public class WorkLogViewBizbaseUser implements IEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4492233660164522172L;
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "zw")
	private String zw;

	@Column(name = "zc")
	private String zc;

	@Column(name = "orderno")
	private Long orderNo;

	@Column(name = "jobType")
	private Long jobType;

	@Column(name = "startLogDate")
	private Date startLogDate;

	@Column(name = "endLogDate")
	private Date endLogDate;

	@Column(name = "id1")
	private Long id1;

	@Column(name = "name1")
	private String name1;

	@Column(name = "id2")
	private Long id2;

	@Column(name = "name2")
	private String name2;

	@Column(name = "orderno2")
	private Long orderno2;

	@Column(name = "id3")
	private Long id3;

	@Column(name = "name3")
	private String name3;

	@Column(name = "orderno3")
	private Long orderno3;

	@Column(name = "id4")
	private Long id4;

	@Column(name = "name4")
	private String name4;

	@Column(name = "posId")
	private Long posId;

	@Column(name = "posName")
	private String posName;

	@Column(name = "posCode")
	private String posCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getJobType() {
		return jobType;
	}

	public void setJobType(Long jobType) {
		this.jobType = jobType;
	}

	public Date getStartLogDate() {
		return startLogDate;
	}

	public void setStartLogDate(Date startLogDate) {
		this.startLogDate = startLogDate;
	}

	public Date getEndLogDate() {
		return endLogDate;
	}

	public void setEndLogDate(Date endLogDate) {
		this.endLogDate = endLogDate;
	}

	public Long getId1() {
		return id1;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public Long getId2() {
		return id2;
	}

	public void setId2(Long id2) {
		this.id2 = id2;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public Long getOrderno2() {
		return orderno2;
	}

	public void setOrderno2(Long orderno2) {
		this.orderno2 = orderno2;
	}

	public Long getId3() {
		return id3;
	}

	public void setId3(Long id3) {
		this.id3 = id3;
	}

	public String getName3() {
		return name3;
	}

	public void setName3(String name3) {
		this.name3 = name3;
	}

	public Long getOrderno3() {
		return orderno3;
	}

	public void setOrderno3(Long orderno3) {
		this.orderno3 = orderno3;
	}

	public Long getId4() {
		return id4;
	}

	public void setId4(Long id4) {
		this.id4 = id4;
	}

	public String getName4() {
		return name4;
	}

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
