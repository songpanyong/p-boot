package com.guohuai.settlement.api.request;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class WithdrawBankOrderSyncReq {
	/**
	 * 支付流水号
	 */
	private String orderCode;

	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;

	/**
	 * 手续费
	 */
	private BigDecimal fee;

	/**
	 * 订单状态 paySuccess("1"), payFailed("2");
	 */
	private String orderStatus;

	/**
	 * 用户类型 INVESTOR("T1"), SPV("T2"), PLATFORM("T3");
	 */
	private String userType;

	/**
	 * 订单时间
	 */
	private String orderTime;

	/**
	 * 交易完成时间
	 */
	private String completeTime;

	/**
	 * 发行人
	 */
	private String investorOid;

	/**
	 * 支付流水号
	 */
	private String payNo;

	/**
	 * 支付通道
	 */
	private String channelNo;
}