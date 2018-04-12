package com.guohuai.settlement.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 充值确认对账
 * @author hans
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepositConfirmResponse extends BaseResponse {
	private static final long serialVersionUID = -222765746204581456L;

	/**
	 * 会员ID
	 */
	private String userOid;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 支付状态 S成功 F失败
	 */
	private String status;
}