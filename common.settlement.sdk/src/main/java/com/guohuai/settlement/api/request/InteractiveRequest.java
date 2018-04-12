package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName: InteractiveRequest
 * @Description: 业务结算交互request
 */
@Data
public class InteractiveRequest implements Serializable {
	private static final long serialVersionUID = -112765746294580725L;

	/**
	 * 会员ID
	 */
	private String userOid;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 银行卡号
	 */
	private String bankCardNo;

	/**
	 * 用户类型
	 */
	private String userType;// 投资人T1,发行人T2
}