package com.guohuai.cms.platform.bankcard;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@lombok.Builder
public class BankCardQueryRep {

	private String oid, bankCode, bankName, peopleBankCode, bankLogo, bankBigLogo, bgColor,
					status, creator, approver, approveRemark;	
	
	private Long withdrawDayLimit, payDayLimit, withdrawOneLimit, payOneLimit, withdrawMoonLimit, payMoonLimit;
	
	private Timestamp approveTime, updateTime, createTime;
}
