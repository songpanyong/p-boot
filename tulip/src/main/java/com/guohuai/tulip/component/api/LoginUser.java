package com.guohuai.tulip.component.api;

public class LoginUser {
	String userAcc;//:18301401831,
	String userPwd;//:123456,
	String platform;//:pc
	
	public LoginUser(String userAcc, String userPwd, String platform) {
		super();
		this.userAcc = userAcc;
		this.userPwd = userPwd;
		this.platform = platform;
	}
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	
}
