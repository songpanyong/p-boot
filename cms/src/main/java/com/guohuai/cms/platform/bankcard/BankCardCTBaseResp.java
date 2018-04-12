package com.guohuai.cms.platform.bankcard;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class BankCardCTBaseResp{

	private String bankCode, bankName, peopleBankCode, bankLogo, bankBigLogo, bgColor;	
	
	private Long withdrawMoonLimit, payMoonLimit, withdrawDayLimit, payDayLimit, withdrawOneLimit, payOneLimit;
}
