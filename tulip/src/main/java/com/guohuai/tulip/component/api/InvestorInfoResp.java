package com.guohuai.tulip.component.api;

/**
 * 投资者手机号信息
 * @author mr_gu
 *
 */
public class InvestorInfoResp {
	String investorOid;
	String phoneNum;
	String realName;
	
	public InvestorInfoResp(String investorOid, String phoneNum, String realName) {
		super();
		this.investorOid = investorOid;
		this.phoneNum = phoneNum;
		this.realName = realName;
	}
	public String getInvestorOid() {
		return investorOid;
	}
	public void setInvestorOid(String investorOid) {
		this.investorOid = investorOid;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}
