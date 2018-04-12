package com.guohuai.settlement.api.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Order {
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 订单状态
	 */
	private BigDecimal voucherAmount;
	
	
	
	/**
	 * 产品CODE
	 */
	private String code;
	
	/**
	 * 产品简称
	 */
	private String name;
}
