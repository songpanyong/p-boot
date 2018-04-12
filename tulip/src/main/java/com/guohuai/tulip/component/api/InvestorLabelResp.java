package com.guohuai.tulip.component.api;

/**
 * 投资者 群组返回值
 * @author mr_gu
 *
 */
public class InvestorLabelResp {
	String oid;//投资人组oid

	String labelCode;//投资人组code
	
	String labelName;//投资人组名称
	
	public InvestorLabelResp(){
		
	}

	public InvestorLabelResp(String oid, String labelCode, String labelName) {
		super();
		this.oid = oid;
		this.labelCode = labelCode;
		this.labelName = labelName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	
}
