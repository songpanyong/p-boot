package com.guohuai.rules.fact;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestorOrder {
	
	/**
	 * 用户oid.
	 */
	String oid;

	/**
	 * 产品OID.
	 */
	String productOid;
	
	/**
	 * 订单时间, 结算成功时间.
	 */
	Date orderTime, settlementTime;
	
	/**
	 * 订单金额.
	 */
	BigDecimal amount;
}
