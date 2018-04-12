package com.guohuai.account.api.response.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ProductAccountDto implements Serializable {
	private static final long serialVersionUID = 3276888243860900289L;

	/**
	 * 账户号
	 */
	private String accountNo;
	/**
	 * 产品专户名称
	 */
	private String accountName;
	/**
	 * 开户时间
	 */
	private String openTime;
	/**
	 * 关联产品
	 */
	private String relationProduct;
	/**
	 * 关联产品名称
	 */
	private String relationProductName;
	/**
	 * 账户余额
	 */
	private BigDecimal balance;
	/**
	 * 授信额度
	 */
	private BigDecimal lineOfCredit;
	/**
	 * 
	 */
	private BigDecimal availableCredit;
	/**
	 * 是否为默认产品户
	 */
	private String isDefault;
	
	public ProductAccountDto(ProductAccountDto dto){
		if(dto.balance.compareTo(dto.lineOfCredit)>0){
			this.availableCredit = dto.lineOfCredit;
		}else{
			this.availableCredit = dto.balance;
		}
	}
}