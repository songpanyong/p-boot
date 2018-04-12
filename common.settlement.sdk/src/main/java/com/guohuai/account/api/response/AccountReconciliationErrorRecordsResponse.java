package com.guohuai.account.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 三方对账文件异常订单返回参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountReconciliationErrorRecordsResponse extends BaseResponse {
	private static final long serialVersionUID = 2881088815134273026L;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 支付流水号
	 */
	private String payNo;
	/**
	 * 三方订单号
	 */
	private String outsideOrderNo;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 三方订单状态
	 */
	private String outsideOrderStatus;
	/**
	 * 订单金额
	 */
	private BigDecimal amount;
	/**
	 * 三方订单金额
	 */
	private BigDecimal outsideAmount;
	/**
	 * 用户ID
	 */
	private String userOid;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户手机号
	 */
	private String userPhone;
	/**
	 * 渠道No
	 */
	private String channelNo;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 异常类型
	 */
	private String errorType;
	/**
	 * 异常处理状态
	 */
	private String errorStatus;
	/**
	 * 异常处理结果
	 */
	private String errorResult;
	/**
	 * 是否有申购记录，Y有N无
	 */
	private String applyRecord;
	/**
	 * 订单时间
	 */
	private Date orderTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 商户号
	 */
	private String memberId;
}