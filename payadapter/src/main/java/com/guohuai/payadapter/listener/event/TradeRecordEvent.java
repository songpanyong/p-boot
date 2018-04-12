package com.guohuai.payadapter.listener.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TradeRecordEvent extends AbsEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3852722941283569498L;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单状态
	 */
	private String status;
	/**
	 * 订单失败原因
	 */
	private String failureDetails;
	/**
	 * 银行返回时间
	 */
	private String bankReturnTime;
	/**
	 * 银行拒付时间
	 */
	private String bankDishonorTime;
	/**
	 * 拒付状态
	 */
	private String dishonorStatus;
	/**
	 * 结算状态
	 */
	private String settlementStatus;
	/**
	 * 预估结算日期
	 */
	private String estimateSettlementDate;
	/**
	 * 结算日期
	 */
	private String settlementTime;
	/**
	 * 净额结算日期
	 */
	private String nettingDate;
	/**
	 * 宝付-订单交易时间
	 */
	private String tradeTime;
	/**
	 * 请求流水号
	 */
	private String requestNo;
}
