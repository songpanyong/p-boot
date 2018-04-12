package com.guohuai.account.api.response.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AccOrderDto implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8666467440964411539L;
	// 订单状态1-成功，0-失败
	public static final String ORDERSTATUS_SUCC = "1";
	public static final String ORDERSTATUS_FAIL = "0";

	private String requestNo;
	private String systemSource; // 来源系统类型
	private String orderNo; // 来源系统单据号
	private String userOid; // 用户ID
	private String orderType; // 申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
	private String relationProductNo; // 关联产品编号
	private String relationProductName; // 关联产品名称
	private BigDecimal balance; // 单据金额
	private String orderStatus; // 订单状态
	private String orderDesc; // 订单描述
	private String inputAccountNo; // 入账账户，根据单据类型，做转账时用
	private String outpuptAccountNo; // 出账账户，根据单据类型，做转账时用
	private String submitTime; // 单据时间
	private String receiveTime; // 系统接收时间
	private String businessStatus; // 业务系统对账状态
	private String financeStatus; // 账务系统对账状态
	private String remark; // 订单描述
	private String createTime;
	private String updateTime;
	private String phone;
}