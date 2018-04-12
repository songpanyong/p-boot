package com.guohuai.account.api.response.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @ClassName: PlatformReservedAccountDTO
 * @Description: 平台备付金户信息
 * @author chendonghui
 * @date 2018年2月3日 下午1:32:45
 *
 */
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PlatformReservedAccountDto implements Serializable {
	
	private static final long serialVersionUID = -6531650051698663597L;
	/**
	 * 备付金账号 
	 */
	private String accountNo;
	/**
	 * 备付金账户名称 
	 */
	private String accountName;
	/**
	 * 可用余额 
	 */
	private BigDecimal availableBalanceAmount = BigDecimal.ZERO;
	/**
	 * 授信额度 
	 */
	private BigDecimal lineOfCreditAmount = BigDecimal.ZERO;
	/**
	 * 转账金额 
	 */
	private BigDecimal transferAmount = BigDecimal.ZERO;
	/**
	 * 红包出金总额 
	 */
	private BigDecimal redPacketsAmount = BigDecimal.ZERO;
	/**
	 * 代金券出金总额 
	 */
	private BigDecimal couponAmount = BigDecimal.ZERO;
	/**
	 * 加息券出金总额 
	 */
	private BigDecimal rateCouponAmount = BigDecimal.ZERO;
	/**
	 * 体验金出金总额 
	 */
	private BigDecimal tasteCouponAmount = BigDecimal.ZERO;
	/**
	 * 返佣出金总额 
	 */
	private BigDecimal rebateAmount = BigDecimal.ZERO;
}