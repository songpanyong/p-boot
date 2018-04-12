package com.guohuai.operate.api.objs.admin.log;

import com.guohuai.operate.api.objs.admin.AdminObj;

public class AdminLogObj extends AdminObj  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1648157200409470045L;
	public static final String TYPE_REGIST = "REGIST";
	public static final String TYPE_FREEZE = "FREEZE";
	public static final String TYPE_UNFREEZE = "UNFREEZE";
	public static final String TYPE_LOGIN = "LOGIN";
	public static final String TYPE_LOGOUT = "LOGOUT";
	public static final String TYPE_RESETPWD = "RESETPWD";
	public static final String TYPE_GENPWD = "GENPWD"; 
	public static final String TYPE_OPERATION="OPERATION";

	private String aid;

	private String adminId;

	private String type;

	private String operator;
	private String operateIp;
	private String updateTime;
	private String createTime;
	private String content;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperateIp() {
		return operateIp;
	}
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
