package com.guohuai.operate.api.objs.admin;

import com.guohuai.operate.api.objs.BaseObj;

public class AdminObj extends BaseObj {

	private static final long serialVersionUID = -3605455615673097416L;

	public static final String STATUS_WAITCERT = "WAITCERT";
	public static final String STATUS_WAITAUDIT = "WAITAUDIT";
	public static final String STATUS_MOREINFO = "MOREINFO";
	public static final String STATUS_VALID = "VALID";
	public static final String STATUS_INVALID = "INVALID";
	public static final String STATUS_FREEZE = "FREEZE";
	public static final String STATUS_EXPIRED = "EXPIRED";

	private String oid;
	private String sn;
	private String account;
	private String name;
	private String email;
	private String phone;
	private String resources;
	private String comment;
	private String status;
	private String loginIp;
	private String loginTime;

	private String[] roles;
	private String[] auths;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String[] getAuths() {
		return auths;
	}

	public void setAuths(String[] auths) {
		this.auths = auths;
	}

}
