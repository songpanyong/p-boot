package com.guohuai.account.api.request.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AccountOrderDto implements Serializable {
	
	/**
	* @Fields serialVersionUID : TODO
	*/
	private static final long serialVersionUID = 1060337252245115642L;
	
	private String orderNo; //来源系统单据号
	private String userOid; //收款人用户ID
	private String orderType; //申购:01、赎回:02、派息:03、赠送体验金:04、体验金到期:05
	private BigDecimal balance;	//单据金额
	private BigDecimal voucher;//代金券
	private BigDecimal fee=BigDecimal.ZERO;//手续费
	private String orderDesc; //订单描述
	private String submitTime; //单据时间
	private String remark;//备注
	private String userType;//用户类型
}
