package com.guohuai.payadapter.listener.event;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderEvent extends AbsEvent {
	private static final long serialVersionUID = 3496543965923807341L;
	
	/**
	 * userOid
	 */
	private String userOid;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 支付单号
	 */
	private String payNo;
	/**
	 * 定单来源
	 */
	private String sourceType;
	/**
	 * 单据状态
	 */
	private String status;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	private String bankName;
	/**
	 * 支行名称
	 */
	private String branchBankName;
	private String cardNo;
	private String realName;
	/**
	 * 银行行别
	 */
	private String bankTypeCode;
	/**
	 * 账户 ID
	 */
	private String custAccountId;
	/**
	 * 协议号
	 */
	private String treatyNo;
	
	/**
	 * 收款人开户行行号
	 */
	private String InAcctBankNode;
	
	/**
	 * 查询交易订单结果状态返回的时间
	 */
	private String bankReturnTime;
	
	private String merchantId;
	
	private String productId;
	
	/**
	 * 银行返回流水号
	 */
	private String hostFlowNo;
	
	/**
	 * 平安银行用
	 * 加急标志
	 */
	private String emergencyMark;
	
	/**
	 *  平安银行用
	 * 跨行标志
	 */
	private String crossFlag;
	
	/**
	 * 同城异地标记
	 */
	private String distanceMark;
	/**
	 * 平安银行用
	 * 平台账户
	 */
	private String platformAccount;
	/**
	 * 平台户名
	 */
	private String platformName;
	/**
	 * 付款人地址
	 */
	private String payAddress;
	
	/**
	 * 标志
	 * 前段：1；后端：2
	 */
	private String launchplatform;
	
	
	/**
	 * 收款账户银行开户省代码或省名称
	 */
	private String inAcctProvinceCode;
	
	/**
	 * 收款账户开户市
	 * 
	 */
	private String inAcctCityName;
	
	/**
	 * 金运通用
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 金运通用
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 绑卡标识号--宝付
	 */
	private String bindId;
	
	/**
	 * 宝付业务流水号--用于支付推进
	 */
	private String businessNo;
	/**
      * 先锋支付，支付流水
      */
     private String outPaymentId;
     /***
      * 先锋支付，交易流水
      */
     private String outTradeNo;
     /**
      * 用户类型1、对公2、对私
      */
     private String accountType;
}
