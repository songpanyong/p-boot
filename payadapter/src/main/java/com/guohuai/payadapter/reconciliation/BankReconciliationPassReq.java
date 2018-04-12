package com.guohuai.payadapter.reconciliation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 对账req
 * @author chendonghui
 *
 */
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BankReconciliationPassReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 渠道
	 */
	private String channelId; //快付通，为以后扩展预留
	/**
	 * 产品id
	 */
	private String productId; //快付通用
	/**
	 * 订单号
	 */
	private String orderId; //快付通用（用于和支付订单关联对账）
	/**
	 * 交易币种	CNY人民币     USD美元	EUR欧元	HKD港币
	 */
	private String transactionCurrency;
	/**
	 * 交易金额
	 */
	private BigDecimal transactionAmount;
	/**
	 * 付款方银行账号
	 */
	private String paymentBankNo;
	/**
	 * 收款方银行账号
	 */
	private String beneficiaryBankNo;
	/**
	 * 交易状态
	 */
	private String tradStatus;
	/**
	 * 失败详情
	 */
	private String failDetail;
	/**
	 * 错误代码
	 */
	private String errorCode;
	/**
	 * 交易时间
	 */
	private Date  transactionTime;
	/**
	 * 开户日期
	 */
	private Date accountDate;
	/**
	 * 对账状态
	 */
	private String checkMark;
	/**
	 * 用户id
	 */
	private String userOid;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
}
