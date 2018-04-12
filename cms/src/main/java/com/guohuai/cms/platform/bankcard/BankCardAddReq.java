package com.guohuai.cms.platform.bankcard;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BankCardAddReq {
	
	private String oid;
	
	@NotBlank(message = "银行编号不能为空！")
	@Length(max = 30, message = "银行编号长度不能超过30（包含）！")
	private String bankCode;
	
	@NotBlank(message = "银行名称不能为空！")
	@Length(max = 30, message = "银行名称长度不能超过30（包含）！")
	private String bankName;
	
	@NotBlank(message = "人行代码不能为空！")
	@Length(max = 30, message = "人行代码长度不能超过30（包含）！")
	private String peopleBankCode;
	
	@NotBlank(message = "银行logo不能为空！")
	private String bankLogo;
	
	@NotBlank(message = "银行大logo不能为空！")
	private String bankBigLogo;
	
	@NotBlank(message = "背景颜色不能为空！")	
	private String bgColor;
	
//	@NotBlank(message = "单月提现限额不能为空！")
	private Long withdrawMoonLimit;
	
//	@NotBlank(message = "单月支付限额不能为空！")
	private Long payMoonLimit;
	
//	@NotBlank(message = "单日提现限额不能为空！")
	private Long withdrawDayLimit;
	
//	@NotBlank(message = "单日支付限额不能为空！")
	private Long payDayLimit;
	
//	@NotBlank(message = "单笔提现限额不能为空！")
	private Long withdrawOneLimit;
	
//	@NotBlank(message = "单笔支付限额不能为空！")
	private Long payOneLimit;
}
