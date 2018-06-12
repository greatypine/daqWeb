package com.cnpc.pms.personal.entity;

import com.cnpc.pms.base.entity.DataEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 同步线上人员表
 */
@Entity
@Table(name = "t_sync_record_log")
public class SyncRecordLog extends DataEntity {
	private static final long serialVersionUID = 1L;
	// 员工姓名
	@Column(length = 65, name = "name")
	private String name;
	// 员工编号
	@Column(length = 65, name = "num")
	private String num;
	// 单位名称
	@Column(length = 65, name = "org")
	private String org;
	// 部门
	@Column(length = 65, name = "dept")
	private String dept;
	// 职位名称
	@Column(length = 65, name = "post")
	private String post;
	// 员工学历
	@Column(length = 65, name = "edu")
	private String edu;
	// 身份证号
	@Column(length = 65, name = "cardid")
	private String cardid;
	// 移动电话
	@Column(length = 65, name = "phone")
	private String phone;
	// 公司邮箱
	@Column(length = 65, name = "email")
	private String email;
	// 入职时间
	@Column(length = 65, name = "jointime")
	private String jointime;
	// 离职时间
	@Column(length = 65, name = "lefttime")
	private String lefttime;
	// 更新时间
	@Column(length = 65, name = "updatetime")
	private String updatetime;
	
	//员工编号
	@Column(length = 65, name = "employee_no")
	private String employee_no;
	//邀请码
	@Column(length = 65, name = "inviteCode")
	private String inviteCode;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJointime() {
		return jointime;
	}
	public void setJointime(String jointime) {
		this.jointime = jointime;
	}
	public String getLefttime() {
		return lefttime;
	}
	public void setLefttime(String lefttime) {
		this.lefttime = lefttime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
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

}
