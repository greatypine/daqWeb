package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_online_humanresources")
public class OnLineHumanresources extends DataEntity{
	
	/**
	 * 城市 
	 */
	@Column(length = 65,name="citySelect")
	private String citySelect;
	/**
	 * 所属机构
	 */
	@Column(length = 65, name = "orgname")
	private String orgname;
	
	/**
	 * 部门
	 */
	@Column(length = 45, name = "deptname")
	private String deptname;
	
	/**
	 * 姓名
	 */
	@Column(length = 45, name = "name")
	private String name;
	
	
	/**
	 * 联系方式
	 */
	@Column(length = 65, name = "phone")
	private String phone;
	
	/**
	 * 身份证号
	 */
	@Column(length = 65, name = "cardnumber")
	private String cardnumber;
	
	
	/**
	 * 工号
	 */
	@Column(length = 45, name = "work_no")
	private String work_no;
	
	
	/**
	 * 员工编号
	 */
	@Column(length = 45, name = "employee_no")
	private String employee_no;
	
	
	/**
	 * 邀请码
	 */
	@Column(length = 65,name="inviteCode")
	private String inviteCode;
	
	// 离职时间
	@Column(length = 65, name = "lefttime")
	private String lefttime;
	
	//角色组ID
	@Column(length = 65,name = "groupcode")
	private String groupcode;
	
	//角色组名称 
	@Column(length = 65, name = "groupname")
	private String groupname;


	public String getCitySelect() {
		return citySelect;
	}


	public void setCitySelect(String citySelect) {
		this.citySelect = citySelect;
	}


	public String getOrgname() {
		return orgname;
	}


	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}


	public String getDeptname() {
		return deptname;
	}


	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getCardnumber() {
		return cardnumber;
	}


	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}


	public String getWork_no() {
		return work_no;
	}


	public void setWork_no(String work_no) {
		this.work_no = work_no;
	}


	public String getEmployee_no() {
		return employee_no;
	}


	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}


	public String getInviteCode() {
		return inviteCode;
	}


	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}


	public String getLefttime() {
		return lefttime;
	}


	public void setLefttime(String lefttime) {
		this.lefttime = lefttime;
	}


	public String getGroupcode() {
		return groupcode;
	}


	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}


	public String getGroupname() {
		return groupname;
	}


	public void setGroupname(String groupname) {
		this.groupname = groupname;
	} 
	
	
	
	
}
